package frc.robot.subsystems.SwerveModule;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public interface SwerveModuleIO {
    @AutoLog
    class SwerveModuleIOInputs {
        public double speed;
        public double angle;

        public double targetSpeed;
        public double targetAngle;
    }

    void setState(SwerveModuleState state);
    void stop();

    SwerveModulePosition getPosition();
    SwerveModuleState getRealState();
    SwerveModuleState getState();

    void updateInputs(SwerveModuleIOInputs inputs);
    default void periodic() {};
}
