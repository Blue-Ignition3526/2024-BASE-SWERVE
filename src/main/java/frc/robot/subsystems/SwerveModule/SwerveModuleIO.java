package frc.robot.subsystems.SwerveModule;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Measure;

public interface SwerveModuleIO {
    @AutoLog
    class SwerveModuleIOInputs {
        public double speed;
        public double angle;

        public double targetSpeed;
        public double targetAngle;

        public double distance;
    }
    
    
    Measure<Angle> getAbsoluteEncoderPosition();
    default void resetDriveEncoder() {};
    default void resetTurningEncoder() {};
    default void resetEncoders() {};
    
    Measure<Angle> getAngle();

    void setTargetState(SwerveModuleState state, boolean force);
    void setTargetState(SwerveModuleState state);
    void stop();
    
    SwerveModuleState getTargetState();
    SwerveModuleState getRealState();
    SwerveModulePosition getPosition();

    String getName();

    default void updateInputs(SwerveModuleIOInputs inputs) {};
    default void periodic() {};
}
