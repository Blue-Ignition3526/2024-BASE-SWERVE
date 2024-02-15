// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.Gyro.Gyro;
import frc.robot.subsystems.SwerveDrive.SwerveDrive;
import frc.robot.subsystems.Vision.LimelightIO;
import frc.robot.subsystems.Vision.PhotonIO;
import frc.robot.subsystems.Vision.Vision;

public class PoseEstimatorSubsystem extends SubsystemBase {
  static final int LIMELIGHT = 0;
  static final int PHOTONVISION = 1;

  SwerveDrivePoseEstimator poseEstimator;
  Map<String, Integer> camParams;
  SwerveDrive swerve;
  Gyro gyro;

  Vision[] cams;

  public PoseEstimatorSubsystem(Map<String, Integer> camParams, SwerveDrive swerve, Gyro gyro) {
    this.camParams = camParams;
    this.swerve = swerve;
    this.gyro = gyro;

    poseEstimator = new SwerveDrivePoseEstimator(Constants.SwerveDrive.PhysicalModel.kDriveKinematics, gyro.getRotation2d(), swerve.getModulePositions(), Constants.Field.kInitialPoseMeters);

    cams = new Vision[camParams.size()];
  
    
    // loop through the dictionary and create the cameras
    int i = 0;
    for (Map.Entry<String, Integer> entry : camParams.entrySet()) {
      String key = entry.getKey();
      Integer value = entry.getValue();
      if (value == LIMELIGHT) {
        cams[i] = new Vision(new LimelightIO(key));
      } else if (value == PHOTONVISION) {
        cams[i] = new Vision(new PhotonIO(key));
      }
      i++;
    }
  }

  public Pose2d getEstimatedPose() {
    return poseEstimator.getEstimatedPosition();
  }


  //* https://github.com/STMARobotics/frc-7028-2023/blob/5916bb426b97f10e17d9dfd5ec6c3b6fda49a7ce/src/main/java/frc/robot/subsystems/PoseEstimatorSubsystem.java
  /**
   * Resets the current pose to the specified pose. This should ONLY be called
   * when the robot's position on the field is known, like at the beginning of
   * a match.
   * @param newPose new pose
   */
  public void setCurrentPose(Pose2d newPose) {
    poseEstimator.resetPosition(
      gyro.getRotation2d(),
      swerve.getModulePositions(),
      newPose);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    poseEstimator.update(gyro.getRotation2d(), swerve.getModulePositions());
    
    for (Vision cam : cams) {
      poseEstimator.addVisionMeasurement(cam.getEstimatedPose(), cam.getTimestampSeconds());
    }

    Logger.recordOutput("PoseEstimator/EstimatedPose", poseEstimator.getEstimatedPosition());
  }
}
