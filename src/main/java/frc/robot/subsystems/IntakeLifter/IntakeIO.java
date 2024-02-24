package frc.robot.subsystems.IntakeLifter;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Velocity;

public interface IntakeIO {
    @AutoLog
    class IntakeIOInputs {
        double lifterAngle;
        Measure<Angle> desiredAngle;
    }

    public void setLifterSpeed(double speed);
    public void setLifterAngle(Measure<Angle> angleDeg);
    public double getLifterAngleRadians();
    public void stopLifter();

    public void updateInputs(IntakeIOInputs inputs);
    public default void periodic() {};
}
