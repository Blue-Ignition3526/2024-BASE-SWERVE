package frc.robot.subsystems.Climber;

import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Constants;
import lib.team3526.control.LazyCANSparkMax;

public class ClimberIOReal implements ClimberIO {
    LazyCANSparkMax climberMotor;

    public ClimberIOReal(int motorID) {
        this.climberMotor = new LazyCANSparkMax(motorID, MotorType.kBrushless);
    }

    public void set(double speed) {
        climberMotor.set(speed);
    }

    public void setClimberUp() {
        climberMotor.set(Constants.Climber.kClimberUpSpeed);
    }

    public void setClimberDown() {
        climberMotor.set(Constants.Climber.kClimberDownSpeed);
    }

    public void stop() {
        climberMotor.set(0);
    }

    public void updateInouts(ClimberIOInputs inputs) {
        inputs.speed = climberMotor.get();
        inputs.current = climberMotor.getOutputCurrent();
    }
}
