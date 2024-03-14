package frc.robot.subsystems.SwerveModule;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Measure;
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

  public Measure<Angle> getAbsoluteEncoderPosition() {
    return io.getAbsoluteEncoderPosition();
  }
  /**
   * Resets the turn encoder (APPLIES ABSOLUTE ENCODER POSITION)
   */
  public void resetTurningEncoder() {
    io.resetTurningEncoder();
  }

  /**
   * Resets the drive encoder (SETS TO 0 DISTANCE)
   */
  public void resetDriveEncoder() {
    io.resetDriveEncoder();
  }

  /**
   * Resets the turn and drive encoders (APPLIES ABSOLUTE ENCODER POSITION)
   */
  public void resetEncoders() {
    io.resetEncoders();
  }

  public Measure<Angle> getAngle() {
    return io.getAngle();
  }

  /**
   * Sets the state of the swerve module (Speed and angle).
   * (Updates the motors with PID)
   * @param state SwerveModuleState
   */
  public void setTargetState(SwerveModuleState state) {
    io.setTargetState(state);
  }

  /**
   * Sets the state of the swerve module (Speed and angle) (forcefully or not).
   * (Updates the motors with PID)
   * @param state SwerveModuleState
   * @param force boolean
   */
  public void setTargetState(SwerveModuleState state, boolean force) {
    io.setTargetState(state, force);
  }
   /**
   * Stops the swerve module.
   */
  public void stop() {
    io.stop();
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
  public SwerveModuleState getTargetState() {
    return io.getTargetState();
  }
  /**
   * Gets the position of the swerve module on the field.
   * @return SwerveModulePosition position
   */
  public SwerveModulePosition getPosition() {
    return io.getPosition();
  }
  
  @Override
  public void periodic() {
    io.periodic();
    io.updateInputs(inputs);
  }
}
