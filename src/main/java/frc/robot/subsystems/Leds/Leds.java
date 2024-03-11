package frc.robot.subsystems.Leds;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import lib.team3526.led.LEDStrip;

public class Leds extends SubsystemBase {
  public final LedsIO io;
  public final LedsIOInputsAutoLogged inputs = new LedsIOInputsAutoLogged();

  public Leds(LedsIO io) {
    this.io = io;
  }

  public void setLeds(String hex) {
    io.setLeds(hex);
  }

  public void setLeds(int r, int g, int b) {
    io.setLeds(r, g, b);
  }

  public void setLedSegment(LEDStrip strip, String hex) {
    io.setLedSegment(strip, hex);
  }

  public void setLedSegment(LEDStrip strip, int r, int g, int b) {
    io.setLedSegment(strip, r, g, b);
  }

  public void blinkLeds(String hex) {
    io.blinkLeds(hex);
  }

  public void blinkLeds(int r, int g, int b) {
    io.blinkLeds(r, g, b);
  }

  public void blinkLedSegment(LEDStrip strip, String hex, int speed) {
    io.blinkLedSegment(strip, hex, speed);
  }

  public void blinkLedSegment(LEDStrip strip, int r, int g, int b, int speed) {
    io.blinkLedSegment(strip, r, g, b, speed);
  }

  public void turnOff() {
    io.turnOff();
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    io.periodic();
  }
}
