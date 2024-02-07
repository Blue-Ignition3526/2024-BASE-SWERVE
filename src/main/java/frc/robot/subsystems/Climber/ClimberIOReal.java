package frc.robot.subsystems.Climber;

import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Constants;
import lib.team3526.control.LazyCANSparkMax;

public class ClimberIOReal implements ClimberIO {
    LazyCANSparkMax leftClimberMotor;
    LazyCANSparkMax rightClimberMotor;

    public ClimberIOReal() {
        leftClimberMotor = new LazyCANSparkMax(Constants.Climber.kLeftClimberMotorID, MotorType.kBrushless);
        rightClimberMotor = new LazyCANSparkMax(Constants.Climber.kRightClimberMotorID, MotorType.kBrushless);
    }

    public void setLeftClimberSpeed(double speed) {
        leftClimberMotor.set(speed);
    }

    public void setRightClimberSpeed(double speed) {
        rightClimberMotor.set(speed);
    }

    public void set(double leftSpeed, double rightSpeed) {
        setLeftClimberSpeed(leftSpeed);
        setRightClimberSpeed(rightSpeed);
    }

    public void set(double speed) {
        set(speed, speed);
    }

    public void setLeftClimberUp() {
        setLeftClimberSpeed(Constants.Climber.kClimberUpSpeed);
    }

    public void setLeftClimberDown() {
        setLeftClimberSpeed(Constants.Climber.kClimberDownSpeed);
    }

    public void setRightClimberUp() {
        setRightClimberSpeed(Constants.Climber.kClimberUpSpeed);
    }

    public void setRightClimberDown() {
        setRightClimberSpeed(Constants.Climber.kClimberDownSpeed);
    }

    public void stopLeftClimber() {
        setLeftClimberSpeed(0);
    }

    public void stopRightClimber() {
        setRightClimberSpeed(0);
    }

    public void stop() {
        stopLeftClimber();
        stopRightClimber();
    }

    public void setClimberUp() {
        set(Constants.Climber.kClimberUpSpeed);
    }

    public void setClimberDown() {
        set(Constants.Climber.kClimberDownSpeed);
    }

    public void updateInouts(ClimberIOInputs inputs) {
        inputs.leftClimberSpeed = leftClimberMotor.get();
        inputs.rightClimberSpeed = rightClimberMotor.get();
    }
}
