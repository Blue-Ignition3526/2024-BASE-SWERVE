package frc.robot.subsystems.Climber;

import static edu.wpi.first.units.Units.Centimeters;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Measure;
import frc.robot.Constants;
import lib.team3526.control.LazyCANSparkMax;

public class ClimberIOReal implements ClimberIO {
    LazyCANSparkMax climberMotor;
    RelativeEncoder climberEncoder;

    public ClimberIOReal(int motorID) {
        this.climberMotor = new LazyCANSparkMax(motorID, MotorType.kBrushless);
        this.climberEncoder = this.climberMotor.getEncoder();
        this.climberEncoder.setPositionConversionFactor(Constants.Climber.kClimber_RotationToCentimeters);
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

    public Measure<Distance> getExtension() {
        return Centimeters.of(this.climberEncoder.getPosition());
    }

    public double getCurrent() {
        return climberMotor.getOutputCurrent();
    }

    public double resetEncoder() {
        climberEncoder.setPosition(0);
        return climberEncoder.getPosition();
    }

    public void updateInputs(ClimberIOInputs inputs) {
        inputs.speed = climberMotor.get();
        inputs.current = climberMotor.getOutputCurrent();
    }
}
