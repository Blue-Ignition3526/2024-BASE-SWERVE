package frc.robot.subsystems.Climber;

import org.littletonrobotics.junction.AutoLog;

public interface ClimberIO {
    @AutoLog
    class ClimberIOInputs {
        double speed;
        double current;
    }

    void set(double speed);

    void setClimberUp();
    void setClimberDown();

    void stop();

    void updateInouts(ClimberIOInputs inputs);
    public default void periodic() {};
}
