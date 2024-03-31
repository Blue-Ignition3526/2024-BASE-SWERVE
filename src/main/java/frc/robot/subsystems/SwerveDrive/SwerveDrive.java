// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.SwerveDrive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveDrive extends SubsystemBase {
  SwerveDriveIO io;
  SwerveDriveIOInputsAutoLogged inputs = new SwerveDriveIOInputsAutoLogged();

  public SwerveDrive(SwerveDriveIO io) {
    this.io = io;
    io.configureAutoBuilder(this);
  }

  /**
   * Get the current heading of the robot.
   * @return The current heading of the robot.
   */
  public Rotation2d getHeading() {
    return io.getHeading();
  }
  /**
   * Zero the heading (yaw measurement) of the gyroscope.
   */
  public void zeroHeading() {
    io.zeroHeading();
  }

  public void resetPose(){
    io.resetPose();
  }
  public Pose2d getPose(){
    return io.getPose();
  }
  public void resetOdometry(Pose2d pose){
    io.resetOdometry(pose);
  }

  public ChassisSpeeds getRobotRelativeChassisSpeeds(){
    return io.getRobotRelativeChassisSpeeds();
  }
  /**
   * Get all the modules' states.
   * @return The modules' states.
   */
  public SwerveModuleState[] getModuleTargetStates() {
    return io.getModuleTargetStates();
  }

  /**
   * Get all the modules' real states.
   * @return The modules' real states.
   */
  public SwerveModuleState[] getModuleRealStates() {
    return io.getModuleRealStates();
  }

  /**
   * Get all the modules' positions.
   * @return The modules' positions.
   */
  public SwerveModulePosition[] getModulePositions() {
    return io.getModulePositions();
  }

  public void setModuleStates(SwerveModuleState[] states){
    io.setModuleStates(states);
  }
  
  /**
   * Drive the robot at a given robot relative speed.
   * @param speeds
   */
  public void drive(ChassisSpeeds speeds) {
    io.drive(speeds);
  }
  /**
   * Drive the robot at a given field relative speed.
   * @param xSpeed The speed that the robot should drive in the x direction.
   * @param ySpeed The speed that the robot should drive in the y direction.
   * @param rotSpeed The speed that the robot should rotate.
   */
  public void driveFieldRelative(double xSpeed, double ySpeed, double rotSpeed) {
    io.driveFieldRelative(xSpeed, ySpeed, rotSpeed);
  }

  /**
   * Drive the robot at a given field relative speed.
   * @param speeds
   */
  public void driveFieldRelative(ChassisSpeeds speeds) {
    io.driveFieldRelative(speeds);
  }
  /**
   * Drive the robot at a given robot relative speed.
   * @param xSpeed The speed that the robot should drive in the x direction.
   * @param ySpeed The speed that the robot should drive in the y direction.
   * @param rotSpeed The speed that the robot should rotate.
   */
  public void driveRobotRelative(double xSpeed, double ySpeed, double rotSpeed) {
    io.driveRobotRelative(xSpeed, ySpeed, rotSpeed);
  }

  /**
   * Drive the robot at a given robot relative speed.
   * @param speeds
   */
  public void driveRobotRelative(ChassisSpeeds speeds) {
    io.driveRobotRelative(speeds);
  }
  /**
   * Stop all modules
   */
  public void stop() {
    io.stop();
  }
  /**
   * Set the swerve module states to an X pattern.
   * Makes the robot hard to move
   * @param states
   */
  public void xFormation() {
    io.xFormation();
  }


  /**
   * Resets the turning encoders of all the modules.
   */
  public void resetTurningEncoders() {
    io.resetTurningEncoders();
  }

  /**
   * Resets the drive encoders of all the modules.
   */
  public void resetDriveEncoders() {
    io.resetDriveEncoders();
  }

  /**
   * Resets the turning and drive encoders of all the modules.
   */
  public void resetEncoders() {
    io.resetEncoders();
  }

  public void setVisionPose() {
    io.setVisionPose();
  }
  @Override
  public void periodic() {
    io.periodic();
    io.updateInputs(inputs);
  }
}
