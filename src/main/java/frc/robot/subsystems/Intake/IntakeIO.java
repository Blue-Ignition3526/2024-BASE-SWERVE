package frc.robot.subsystems.Intake;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Velocity;

public interface IntakeIO {
    @AutoLog
    class IntakeIOInputs {
        double intakeSpeed;
        double intakeCurrent;

        double lifterAngle;

        boolean hasPiece;
        Measure<Angle> desiredAngle;
    }

    public void setIntakeOut();
    public void setIntakeIn();
    public void giveToShooter();

    public void setIntakeSpeed(double speed);
    public void setIntakeSpeedRpm(Measure<Velocity<Angle>> rpm);

    public void setIntakeHold();
    public void stopIntake();

    public void setIntakeCoast();
    public void setIntakeBrake();

    public double getIntakeSpeed();

    public void setLifterAngle(Measure<Angle> angleDeg);
    public double getLifterAngle();
    
    public boolean hasPiece();

    public void updateInputs(IntakeIOInputs inputs);
    public default void periodic() {};
}
