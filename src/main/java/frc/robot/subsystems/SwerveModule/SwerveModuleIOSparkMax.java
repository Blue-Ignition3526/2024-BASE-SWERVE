package frc.robot.subsystems.SwerveModule;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.MagnetSensorConfigs;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.signals.AbsoluteSensorRangeValue;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Measure;
import frc.robot.Constants;
import lib.team3526.PIDFConstants;
import lib.team3526.SwerveModuleOptions;
import static edu.wpi.first.units.Units.*;

public class SwerveModuleIOSparkMax implements SwerveModuleIO {
    private SwerveModuleOptions options;

    private final CANSparkMax driveMotor;
    private final CANSparkMax turningMotor;

    private final RelativeEncoder driveEncoder;
    private final RelativeEncoder turningEncoder;

    private final SparkPIDController drivePID;
    private final SparkPIDController turningPID;

    private final CANcoder absoluteEncoder;
    private final StatusSignal<Double> absoluteEncoderSignal;

    private SwerveModuleState state = new SwerveModuleState();

    public SwerveModuleIOSparkMax(SwerveModuleOptions options) {
        // Store the options
        this.options = options;

        // Create the motors
        this.driveMotor = new CANSparkMax(options.driveMotorID, MotorType.kBrushless);
        this.turningMotor = new CANSparkMax(options.turningMotorID, MotorType.kBrushless);

        // Get and configure the encoders
        this.driveEncoder = this.driveMotor.getEncoder();
        this.driveEncoder.setPositionConversionFactor(Constants.SwerveDrive.PhysicalModel.kDriveEncoder_RotationToMeter); 
        this.driveEncoder.setVelocityConversionFactor(Constants.SwerveDrive.PhysicalModel.kDriveEncoder_RPMToMeterPerSecond);

        this.turningEncoder = this.turningMotor.getEncoder();
        this.turningEncoder.setPositionConversionFactor(Constants.SwerveDrive.PhysicalModel.kTurningEncoder_RotationToRadian); 
        this.turningEncoder.setVelocityConversionFactor(Constants.SwerveDrive.PhysicalModel.kTurningEncoder_RPMToRadianPerSecond);

        // Configure the PID controllers
        this.drivePID = this.driveMotor.getPIDController();
        PIDFConstants.applyToSparkPIDController(this.drivePID, Constants.SwerveDrive.SwerveModules.kDrivePIDConstants);

        this.turningPID = this.turningMotor.getPIDController();
        PIDFConstants.applyToSparkPIDController(this.turningPID, Constants.SwerveDrive.SwerveModules.kTurningPIDConstants);
        this.turningPID.setPositionPIDWrappingMinInput(0);
        this.turningPID.setPositionPIDWrappingMaxInput(2 * Math.PI);
        this.turningPID.setPositionPIDWrappingEnabled(true);

        // Configure the absolute encoder
        this.absoluteEncoder = new CANcoder(options.absoluteEncoderID);
        this.absoluteEncoder.getConfigurator().apply(
            new MagnetSensorConfigs()
            .withMagnetOffset(this.options.getOffsetRad()  / (2 * Math.PI))
            .withAbsoluteSensorRange(AbsoluteSensorRangeValue.Unsigned_0To1)
        );
        this.absoluteEncoderSignal = this.absoluteEncoder.getPosition();

        // Reset the encoders
        resetEncoders();
    }

    public Measure<Angle> getAbsoluteEncoderPosition() {
        return Radians.of((this.absoluteEncoderSignal.getValue() * 2 * Math.PI) % (2 * Math.PI));
    }

    public void resetDriveEncoder() {
        this.driveEncoder.setPosition(0);
    }

    public void resetTurningEncoder() {
        this.turningEncoder.setPosition(getAbsoluteEncoderPosition().in(Radians));
    }
    
    public void resetEncoders() {
        resetDriveEncoder();
        resetTurningEncoder();
    }

    public Measure<Angle> getAngle() {
        return Radians.of(this.turningEncoder.getPosition() % (2 * Math.PI));
    }

    public void setState(SwerveModuleState state) {
        if (Math.abs(state.speedMetersPerSecond) < 0.001) {
            stop();
            return;
        }

        state = SwerveModuleState.optimize(state, Rotation2d.fromRadians(getAngle().in(Radians)));

        this.state = state;

        drivePID.setReference(state.speedMetersPerSecond, ControlType.kVelocity);
        turningPID.setReference(state.angle.getRadians(), ControlType.kPosition);
    }

    public void stop() {
        drivePID.setReference(0, ControlType.kVelocity);
        turningPID.setReference(0, ControlType.kVelocity);
    }

    public SwerveModuleState getState() {
        return this.state;
    }

    public SwerveModuleState getRealState() {
        return new SwerveModuleState(
            this.driveEncoder.getVelocity(),
            Rotation2d.fromRadians(this.getAngle().in(Radians))
        );
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(
            this.driveEncoder.getPosition(),
            Rotation2d.fromRadians(this.getAngle().in(Radians))
        );
    }

    public void updateInputs(SwerveModuleIOInputs inputs) {
        inputs.angle = this.getAngle().in(Radians);
        inputs.speed = this.driveEncoder.getVelocity();

        inputs.targetAngle = this.state.angle.getRadians();
        inputs.targetSpeed = this.state.speedMetersPerSecond;
    }
}
