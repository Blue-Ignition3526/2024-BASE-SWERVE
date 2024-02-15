package frc.robot.subsystems.Vision;

import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.LimelightHelpers;

import org.littletonrobotics.junction.Logger;

public class LimelightIO implements VisionIO {
    String name;

    public LimelightIO(String name) {
        this.name = name;
    }

    public Pose2d getEstimatedPose() {
        return LimelightHelpers.getBotPose3d(name).toPose2d();
    }

    public double getTimestampSeconds() {
        return LimelightHelpers.getLatency_Pipeline(name);
    }

    public void updateInputs(VisionIOInputs inputs) {
        inputs.targetX = LimelightHelpers.getTX(name);
        inputs.targetY = LimelightHelpers.getTY(name);
        inputs.targetArea = LimelightHelpers.getTA(name);
        inputs.targetID = LimelightHelpers.getFiducialID(name);
    }

    public void periodic() {
        Logger.recordOutput("Vision/" + name + "/EstimatedPose", getEstimatedPose());
    }
}
