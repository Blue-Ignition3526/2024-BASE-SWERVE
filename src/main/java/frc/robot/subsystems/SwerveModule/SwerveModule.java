package frc.robot.subsystems.SwerveModule;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveModule extends SubsystemBase {
  private SwerveModuleIOInputsAutoLogged inputs = new SwerveModuleIOInputsAutoLogged();
  private SwerveModuleIO io;

  /**
   * Creates a new SwerveModule with the provided IO.
   * @param io SwerveModuleIO implementation
   */
  public SwerveModule(SwerveModuleIO io) {
    this.io = io;
  }

  /**
   * Sets the state of the swerve module (Speed and angle).
   * (Updates the motors with PID)
   * @param state SwerveModuleState
   */
  public void setState(SwerveModuleState state) {
    io.setState(state);
  }

  /**
   * Stops the swerve module.
   */
  public void stop() {
    io.stop();
  }

  /**
   * Gets the position of the swerve module on the field.
   * @return SwerveModulePosition position
   */
  public SwerveModulePosition getPosition() {
    return io.getPosition();
  }

  /**
   * Gets the **REAL** state of the swerve module (real Speed and angle).
   * @return SwerveModuleState real state
   */
  public SwerveModuleState getRealState() {
    return io.getRealState();
  }

  /**
   * Gets the target state of the swerve module (Speed and angle).
   * @return SwerveModuleState target state 
   */
  public SwerveModuleState getState() {
    return io.getState();
  }  

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    io.periodic();

    Logger.recordOutput("SwerveDrive/" + io.getName() + "/RealState", io.getRealState());
    Logger.recordOutput("SwerveDrive/" + io.getName() + "/TargetState", io.getState());
    Logger.recordOutput("SwerveDrive/" + io.getName() + "/Position", io.getPosition());
  }
}
