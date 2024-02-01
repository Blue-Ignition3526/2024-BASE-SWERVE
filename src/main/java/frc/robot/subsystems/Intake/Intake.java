package frc.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  private final IntakeIO io;
  private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();

  public Intake(IntakeIO io) {
    this.io = io;
  }

  public void setIntakeOut() {
    io.setIntakeOut();
  }

  public void setIntakeIn() {
    io.setIntakeIn();
  }

  public void setIntakeSpeed(double speed) {
    io.setIntakeSpeed(speed);
  }

  public void setIntakeHold() {
    io.setIntakeHold();
  }

  public void setIntakeSpeedRpm(double rpm) {
    io.setIntakeSpeedRpm(rpm);
  }

  public void stopIntake() {
    io.stopIntake();
  }

  public double getIntakeSpeed() {
    return io.getIntakeSpeed();
  }

  public void setLifterAngle(double angleDeg) {
    io.setLifterAngle(angleDeg);
  }

  public double getLifterAngle() {
    return io.getLifterAngle();
  }

  public boolean hasPiece() {
    return io.hasPiece();
  }

  @Override
  public void periodic() {
    io.periodic();
    io.updateInputs(inputs);
  }
}
