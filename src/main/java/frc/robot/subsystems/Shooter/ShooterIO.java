package frc.robot.subsystems.Shooter;

import org.littletonrobotics.junction.AutoLog;

public interface ShooterIO {
    @AutoLog
    class ShooterIOInputs {
        double leftRpm;
        double leftPercentage;
        double leftTargetRpm;
        
        double rightRpm;
        double rightPercentage;
        double rightTargetRpm;
    }

    public void setLeftMotor(double speed);
    public void setRightMotor(double speed);
    public void set(double leftSpeed, double rightSpeed);
    public void set(double speed);

    public void shootSpeaker();

    public void setLeftMotorRpm(double rpm);
    public void setRightMotorRpm(double rpm);
    public void setRpm(double leftRpm, double rightRpm);
    public void setRpm(double rpm);

    public double getLeftMotorRpm();
    public double getRightMotorRpm();

    public double getLeftMotorPercentage();
    public double getRightMotorPercentage();

    public void updateInputs(ShooterIOInputs inputs);
    public default void periodic() {};
}
