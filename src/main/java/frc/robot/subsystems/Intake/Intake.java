package frc.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  private final IntakeIO io;
  private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();

  public Intake(IntakeIO io) {
    this.io = io;
  }

  /**
   * Sets the intake to intake out speed
   */
  public void setIntakeOut() {
    io.setIntakeOut();
  }

  /**
   * Sets the intake to intake in speed
   */
  public void setIntakeIn() {
    io.setIntakeIn();
  }

  /**
   * Sets the speed of the intake to give to the shooter
   */
  public void giveToShooter() {
    io.giveToShooter();
  }

  /**
   * Sets the intake to a specific speed
   * @param speed the speed to set the intake to
   */
  public void setIntakeSpeed(double speed) {
    io.setIntakeSpeed(speed);
  }

  /**
   * Sets the intake to hold speed
   */
  public void setIntakeHold() {
    io.setIntakeHold();
  }

  /**
   * Sets the intake to a specific speed in RPM
   * @param rpm the speed to set the intake to
   */
  public void setIntakeSpeedRpm(double rpm) {
    io.setIntakeSpeedRpm(rpm);
  }

  /**
   * Stops the intake
   */
  public void stopIntake() {
    io.stopIntake();
  }

  /**
   * Gets the current speed of the intake
   * @return the current speed of the intake
   */
  public double getIntakeSpeed() {
    return io.getIntakeSpeed();
  }

  /**
   * Sets the angle of the lifter
   * @param angleDeg the angle to set the lifter to
   */
  public boolean setLifterAngle(double angleDeg) {
    return io.setLifterAngle(angleDeg);
  }

  /**
   * Gets the angle of the lifter
   * @return the angle of the lifter
   */
  public double getLifterAngle() {
    return io.getLifterAngle();
  }

  /**
   * Sets whether the intake has a piece
   * @param hasPiece whether the intake has a piece
   */
  public boolean hasPiece() {
    return io.hasPiece();
  }
  
  /**
   * Sets the intake to coast mode
   */
  public void setIntakeCoast() {
    io.setIntakeCoast();
  }

  /**
   * Sets the intake to brake mode
   */
  public void setIntakeBrake() {
    io.setIntakeBrake();
  }

  @Override
  public void periodic() {
    io.periodic();
    io.updateInputs(inputs);
  }
}
