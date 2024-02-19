package frc.robot.subsystems.Intake;

import static edu.wpi.first.units.Units.RPM;

import org.littletonrobotics.junction.Logger;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Velocity;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import frc.robot.Constants;
import lib.team3526.constants.PIDFConstants;
import lib.team3526.control.LazyCANSparkMax;

public class IntakeIOReal implements IntakeIO {
    private final LazyCANSparkMax lifterMotor;
    private final PIDController lifterMotorPID;
    private final DutyCycleEncoder lifterEncoder;

    private final LazyCANSparkMax intakeMotor;
    private final SparkPIDController intakeMotorPID;
    private final RelativeEncoder intakeMotorEncoder;

    private boolean hasPiece = false;
    private boolean isIntaking = false;

    public IntakeIOReal() {
        this.lifterMotor = new LazyCANSparkMax(Constants.Intake.kLifterMotorID, MotorType.kBrushless);
        this.lifterMotorPID = Constants.Intake.kLifterPIDController;
        this.lifterEncoder = new DutyCycleEncoder(Constants.Intake.kLifterEncoderPort);
        this.lifterEncoder.setPositionOffset(Constants.Intake.kLifterEncoderOffset);

        this.intakeMotor = new LazyCANSparkMax(Constants.Intake.kintakeMotorID, MotorType.kBrushless);
        this.intakeMotorPID = this.intakeMotor.getPIDController();
        PIDFConstants.applyToSparkPIDController(intakeMotorPID, Constants.Intake.kIntakePIDConstants);
        this.intakeMotorEncoder = this.intakeMotor.getEncoder();
    }

///////////////////////////// ROLLERS /////////////////////////////

    public void setIntakeOut() {
        this.setIntakeSpeedRpm(Constants.Intake.kIntakeOutSpeedRpm);
    }

    public void setIntakeIn() {
        this.setIntakeSpeedRpm(Constants.Intake.kIntakeInSpeedRpm);
    }

    public void giveToShooter() {
        this.setIntakeSpeedRpm(Constants.Intake.kGiveToShooterSpeedRpm);
    }

    public void setIntakeHold() {
        this.setIntakeSpeedRpm(Constants.Intake.kIntakeHoldSpeedRpm);
    }

    public void stopIntake() {
        this.setIntakeSpeed(0);
    }

    public void setIntakeSpeed(double speed) {
        this.isIntaking = speed > 0;
        if (speed < 0) this.hasPiece = false;
        this.intakeMotor.set(speed);
    }

    public void setIntakeSpeedRpm(Measure<Velocity<Angle>> rpm) {
        this.isIntaking = rpm.in(RPM) > 0;
        if (rpm.in(RPM) < 0) this.hasPiece = false;
        this.intakeMotorPID.setReference(rpm.in(RPM), ControlType.kVelocity);
    }

    public void setIntakeCoast() {
        Logger.recordOutput("Intake/Brake", false);
        this.intakeMotor.setIdleMode(IdleMode.kCoast);
    }

    public void setIntakeBrake() {
        Logger.recordOutput("Intake/Brake", true);
        this.intakeMotor.setIdleMode(IdleMode.kBrake);
    }

///////////////////////////// LIFTER /////////////////////////////

    /**
     * @param angleDeg The angle to set the lifter to
     * @return true if the lifter is within 5 degrees of the target angle
     */
    public boolean setLifterAngle(double angleDeg) {
        this.lifterMotor.set(this.lifterMotorPID.calculate(this.lifterEncoder.getAbsolutePosition(), angleDeg));
        return Math.abs(this.lifterEncoder.getAbsolutePosition() - angleDeg) < 5;
    }

    public void setHasPiece(boolean hasPiece) {
        this.hasPiece = hasPiece;
    }

    public double getLifterAngle() {
        return this.lifterEncoder.getAbsolutePosition();
    }

    public double getIntakeSpeed() {
        return this.intakeMotorEncoder.getVelocity();
    }

    public boolean hasPiece() {
        return this.hasPiece;
    }



    public void periodic() {
        
        Logger.recordOutput("Intake/Current", this.intakeMotor.getOutputCurrent());
        Logger.recordOutput("Intake/Speed", this.getIntakeSpeed());
        Logger.recordOutput("Intake/LifterAngle", this.getLifterAngle());
    }

    public void updateInputs(IntakeIOInputs inputs) {
        inputs.hasPiece = this.hasPiece;
        inputs.intakeCurrent = this.intakeMotor.getOutputCurrent();
        inputs.intakeSpeed = this.intakeMotor.getAppliedOutput();
        inputs.lifterAngle = this.lifterEncoder.getAbsolutePosition();
    }
}
