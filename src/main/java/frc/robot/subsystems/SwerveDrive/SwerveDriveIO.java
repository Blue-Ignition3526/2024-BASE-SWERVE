package frc.robot.subsystems.SwerveDrive;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public interface SwerveDriveIO {
    @AutoLog
    class SwerveDriveIOInputs {
        double heading;

        double xSpeed;
        double ySpeed;
        double rotSpeed;
    }

    public void configureAutoBuilder(SwerveDrive swerveDrive);

    public Rotation2d getHeading();
    public void zeroHeading();

    public void resetPose();
    public Pose2d getPose();
    public void resetOdometry(Pose2d pose);

    public ChassisSpeeds getRobotRelativeChassisSpeeds();

    public SwerveModuleState[] getModuleTargetStates();
    public SwerveModuleState[] getModuleRealStates();
    public SwerveModulePosition[] getModulePositions();

    public void setModuleStates(SwerveModuleState[] states);
    
    public void drive(ChassisSpeeds speeds);
    
    public void driveFieldRelative(double xSpeed, double ySpeed, double rotSpeed);
    public void driveFieldRelative(ChassisSpeeds speeds);

    public void driveRobotRelative(double xSpeed, double ySpeed, double rotSpeed);
    public void driveRobotRelative(ChassisSpeeds speeds);

    public void stop();

    public void xFormation();

    public  void resetTurningEncoders();
    public  void resetDriveEncoders();
    public void resetEncoders();

    public void setVisionPose();

    public default void updateInputs(SwerveDriveIOInputs inputs) {};
    public default void periodic() {}
}
