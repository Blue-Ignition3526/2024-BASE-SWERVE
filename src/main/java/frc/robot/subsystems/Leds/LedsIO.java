package frc.robot.subsystems.Leds;

import org.littletonrobotics.junction.AutoLog;

import lib.team3526.led.LEDStrip;

public interface LedsIO {
    @AutoLog
    class LedsIOInputs {

    }

    void setLeds(String hex);
    void setLeds(int r, int g, int b);

    void setLedSegment(LEDStrip strip, String hex);
    void setLedSegment(LEDStrip strip, int r, int g, int b);

    void blinkLeds(String hex);
    void blinkLeds(int r, int g, int b);

    void blinkLedSegment(LEDStrip strip, String hex, int speed);
    void blinkLedSegment(LEDStrip strip, int r, int g, int b, int speed);

    void turnOff();

    public void updateInputs(LedsIOInputs inputs);
    default public void periodic() {};
}
