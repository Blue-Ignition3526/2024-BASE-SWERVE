// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Vision;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {
  VisionIO io;
  /** Creates a new Vision. */
  public Vision(VisionIO io) {
    this.io = io;
  }

  @Override
  public void periodic() {
    io.periodic();
    io.updateInputs(null);
    // This method will be called once per scheduler run
  }

  public Pose3d getEstimatedPose() {
    return io.getEstimatedPose();
  }
}
