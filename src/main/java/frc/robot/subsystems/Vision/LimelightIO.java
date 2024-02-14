package frc.robot.subsystems.Vision;

import edu.wpi.first.math.geometry.Pose3d;
import frc.robot.LimelightHelpers;

import static frc.robot.Constants.Vision.*;

import org.littletonrobotics.junction.Logger;

public class LimelightIO implements VisionIO {
    public LimelightIO() {
    }

    public Pose3d getEstimatedPose() {
        return LimelightHelpers.getBotPose3d(kLimelightName);
    }

    public double getTimestampSeconds() {
        return LimelightHelpers.getLatency_Pipeline(kLimelightName);
    }

    public void updateInputs(VisionIOInputs inputs) {
        inputs.targetX = LimelightHelpers.getTX(kLimelightName);
        inputs.targetY = LimelightHelpers.getTY(kLimelightName);
        inputs.targetArea = LimelightHelpers.getTA(kLimelightName);
        inputs.targetID = LimelightHelpers.getFiducialID(kLimelightName);
    }

    public void periodic() {
        Logger.recordOutput("Vision/" + kLimelightName + "/EstimatedPose", getEstimatedPose());
    }
}
