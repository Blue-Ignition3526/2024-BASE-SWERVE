package frc.robot.subsystems.Intake;

import org.littletonrobotics.junction.Logger;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Constants;
import lib.team3526.constants.PIDFConstants;
import lib.team3526.control.LazyCANSparkMax;

public class IntakeIOReal implements IntakeIO {
    private final LazyCANSparkMax lifterMotor;
    private final RelativeEncoder lifterMotorEncoder;
    private final SparkPIDController lifterMotorPID;

    private final LazyCANSparkMax intakeMotor;
    private final RelativeEncoder intakeMotorEncoder;
    private final SparkPIDController intakeMotorPID;

    private boolean hasPiece = false;
    private boolean isIntaking = false;

    public IntakeIOReal() {
        this.lifterMotor = new LazyCANSparkMax(Constants.Intake.kLifterMotorID, MotorType.kBrushless);
        this.lifterMotorEncoder = this.lifterMotor.getEncoder();
        this.lifterMotorEncoder.setPositionConversionFactor(Constants.Intake.kLifter_RotationToDegrees);
        this.lifterMotorPID = this.lifterMotor.getPIDController();
        PIDFConstants.applyToSparkPIDController(lifterMotorPID, Constants.Intake.kLifterPIDConstants);

        this.intakeMotor = new LazyCANSparkMax(Constants.Intake.kintakeMotorID, MotorType.kBrushless);
        this.intakeMotorEncoder = this.intakeMotor.getEncoder();
        this.intakeMotorPID = this.intakeMotor.getPIDController();
        PIDFConstants.applyToSparkPIDController(intakeMotorPID, Constants.Intake.kIntakePIDConstants);

        this.intakeMotor.setIdleMode(IdleMode.kCoast);
    }

    public void setIntakeOut() {
        this.setIntakeSpeed(Constants.Intake.kIntakeOutSpeed);
    }

    public void setIntakeIn() {
        this.setIntakeSpeed(Constants.Intake.kIntakeInSpeed);
    }

    public void setIntakeHold() {
        this.setIntakeSpeed(Constants.Intake.kIntakeHoldSpeed);
    }

    public void stopIntake() {
        this.setIntakeSpeed(0);
    }

    public void setIntakeSpeed(double speed) {
        this.isIntaking = speed > 0;
        if (speed < 0) this.hasPiece = false;
        this.intakeMotor.set(speed);
    }

    public void setIntakeSpeedRpm(double speed) {
        this.isIntaking = speed > 0;
        if (speed < 0) this.hasPiece = false;
        this.intakeMotorPID.setReference(speed, ControlType.kVelocity);
    }

    public void setLifterAngle(double angleDeg) {
        this.lifterMotorPID.setReference(angleDeg, ControlType.kPosition);
    }

    public void setHasPiece(boolean hasPiece) {
        this.hasPiece = hasPiece;
    }

    public double getLifterAngle() {
        return this.lifterMotorEncoder.getPosition();
    }

    public double getIntakeSpeed() {
        return this.intakeMotorEncoder.getVelocity();
    }

    public boolean hasPiece() {
        return this.hasPiece;
    }

    public void periodic() {
        this.hasPiece = this.hasPiece || (this.isIntaking && this.intakeMotor.getOutputCurrent() > Constants.Intake.kHasPieceCurrentThreshold);

        Logger.recordOutput("Intake/HasPiece", this.hasPiece);
        Logger.recordOutput("Intake/Current", this.intakeMotor.getOutputCurrent());
    }

    public void updateInputs(IntakeIOInputs inputs) {
        inputs.hasPiece = this.hasPiece;
        inputs.intakeCurrent = this.intakeMotor.getOutputCurrent();
        inputs.intakeSpeed = this.intakeMotor.getAppliedOutput();
        inputs.lifterAngle = this.lifterMotorEncoder.getPosition();
    }
}
