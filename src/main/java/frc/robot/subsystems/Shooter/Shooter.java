package frc.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  ShooterIOInputsAutoLogged inputs = new ShooterIOInputsAutoLogged();
  ShooterIO io;

  public Shooter(ShooterIO io) {
    this.io = io;
  }

  public void setLeftMotor(double speed) {
    io.setLeftMotor(speed);
  }

  public void setRightMotor(double speed) {
    io.setRightMotor(speed);
  }

  public void set(double leftSpeed, double rightSpeed) {
    io.set(leftSpeed, rightSpeed);
  }

  public void set(double speed) {
    io.set(speed);
  }

  public void setLeftMotorRpm(double rpm) {
    io.setLeftMotorRpm(rpm);
  }

  public void setRightMotorRpm(double rpm) {
    io.setRightMotorRpm(rpm);
  }

  public void setRpm(double leftRpm, double rightRpm) {
    io.setRpm(leftRpm, rightRpm);
  }

  public void setRpm(double rpm) {
    io.setRpm(rpm);
  }

  public double getLeftMotorRpm() {
    return io.getLeftMotorRpm();
  }

  public double getRightMotorRpm() {
    return io.getRightMotorRpm();
  }

  public double getLeftMotorPercentage() {
    return io.getLeftMotorPercentage();
  }

  public double getRightMotorPercentage() {
    return io.getRightMotorPercentage();
  }

  @Override
  public void periodic() {
    io.periodic();
    io.updateInputs(inputs);
  }
}
