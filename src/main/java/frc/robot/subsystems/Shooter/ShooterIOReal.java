package frc.robot.subsystems.Shooter;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Constants;
import lib.team3526.constants.PIDFConstants;
import lib.team3526.control.LazyCANSparkMax;

public class ShooterIOReal implements ShooterIO {
    LazyCANSparkMax leftMotor;
    LazyCANSparkMax rightMotor;

    SparkPIDController leftPID;
    SparkPIDController rightPID;

    RelativeEncoder leftEncoder;
    RelativeEncoder rightEncoder;

    double leftTargetRpm;
    double rightTargetRpm;

    public ShooterIOReal() {
        this.leftMotor = new LazyCANSparkMax(Constants.Shooter.kLeftShooterMotorID, MotorType.kBrushless);
        this.rightMotor = new LazyCANSparkMax(Constants.Shooter.kRightShooterMotorID, MotorType.kBrushless);

        this.leftPID = leftMotor.getPIDController();
        PIDFConstants.applyToSparkPIDController(leftPID, Constants.Shooter.kShooterPIDConstants);

        this.rightPID = rightMotor.getPIDController();
        PIDFConstants.applyToSparkPIDController(rightPID, Constants.Shooter.kShooterPIDConstants);

        this.leftEncoder = leftMotor.getEncoder();
        this.leftEncoder.setVelocityConversionFactor(Constants.Shooter.kShooter_RpmToMechanismRpm);

        this.rightEncoder = rightMotor.getEncoder();
        this.rightEncoder.setVelocityConversionFactor(Constants.Shooter.kShooter_RpmToMechanismRpm);
    }

    public void setLeftMotor(double speed) {
        this.leftTargetRpm = 0;
        leftMotor.set(speed);
    }

    public void setRightMotor(double speed) {
        this.rightTargetRpm = 0;
        rightMotor.set(speed);
    }

    public void set(double leftSpeed, double rightSpeed) {
        setLeftMotor(leftSpeed);
        setRightMotor(rightSpeed);
    }

    public void set(double speed) {
        set(speed, speed);
    }

    public void setLeftMotorRpm(double rpm) {
        this.leftTargetRpm = rpm;
        leftPID.setReference(rpm, ControlType.kVelocity);
    }

    public void setRightMotorRpm(double rpm) {
        this.rightTargetRpm = rpm;
        rightPID.setReference(rpm, ControlType.kVelocity);
    }

    public void setRpm(double leftRpm, double rightRpm) {
        setLeftMotorRpm(leftRpm);
        setRightMotorRpm(rightRpm);
    }

    public void setRpm(double rpm) {
        setRpm(rpm, rpm);
    }

    public double getLeftMotorRpm() {
        return leftEncoder.getVelocity();
    }

    public double getRightMotorRpm() {
        return rightEncoder.getVelocity();
    }

    public double getLeftMotorPercentage() {
        return leftMotor.getAppliedOutput();
    }

    public double getRightMotorPercentage() {
        return rightMotor.getAppliedOutput();
    }

    public void updateInputs(ShooterIOInputs inputs) {
        inputs.leftPercentage = getLeftMotorPercentage();
        inputs.leftRpm = getLeftMotorRpm();
        inputs.leftTargetRpm = leftTargetRpm;
        inputs.rightPercentage = getRightMotorPercentage();
        inputs.rightRpm = getRightMotorRpm();
        inputs.rightTargetRpm = rightTargetRpm;
    }
}
