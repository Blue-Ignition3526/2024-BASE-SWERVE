package frc.robot.subsystems.Intake;

import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO {
    @AutoLog
    class IntakeIOInputs {
        double intakeSpeed;
        double intakeCurrent;

        double lifterAngle;

        boolean hasPiece;
    }

    public void setIntakeOut();
    public void setIntakeIn();
    public void giveToShooter();

    public void setIntakeSpeed(double speed);
    public void setIntakeSpeedRpm(double rpm);

    public void setIntakeHold();
    public void stopIntake();

    public void setIntakeCoast();
    public void setIntakeBrake();

    public double getIntakeSpeed();

    public boolean setLifterAngle(double angleDeg);
    public double getLifterAngle();
    
    public boolean hasPiece();

    public void updateInputs(IntakeIOInputs inputs);
    public default void periodic() {};
}
