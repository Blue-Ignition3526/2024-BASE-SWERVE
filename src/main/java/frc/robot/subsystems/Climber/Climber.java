package frc.robot.subsystems.Climber;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  ClimberIO io;
  ClimberIOInputsAutoLogged inputs = new ClimberIOInputsAutoLogged();

  public Climber(ClimberIO io) {
    this.io = io;
  }

  /**
   * Set the speed of the climber motors
   * @param speed
   */
  public void set(double speed) {
    io.set(speed);
  }

  /**
   * Set the speed of the left and right climber motors
   * @param leftSpeed
   * @param rightSpeed
   */
  public void set(double leftSpeed, double rightSpeed) {
    io.set(leftSpeed, rightSpeed);
  }

  /**
   * Set the speed of the left climber motor
   * @param speed
   */
  public void setLeftClimberUp() {
    io.setLeftClimberUp();
  }

  /**
   * Set the speed of the left climber motor
   */
  public void setLeftClimberDown() {
    io.setLeftClimberDown();
  }

  /**
   * Set the speed of the right climber motor
   */
  public void setRightClimberUp() {
    io.setRightClimberUp();
  }

  /**
   * Set the speed of the right climber motor
   */
  public void setRightClimberDown() {
    io.setRightClimberDown();
  }

  /**
   * Stop the left climber motor
   */
  public void stopLeftClimber() {
    io.stopLeftClimber();
  }

  /**
   * Stop the right climber motor
   */
  public void stopRightClimber() {
    io.stopRightClimber();
  }

  /**
   * Stop the climber motors
   */
  public void stop() {
    io.stop();
  }

  @Override
  public void periodic() {
    io.periodic();
    io.updateInouts(inputs);
  }
}
