package lib.team3526.led;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AddressableLEDStrip extends SubsystemBase {
  public AddressableLED leds;
  public AddressableLEDBuffer data;
  public AddressableLEDSegment[] segments;
  private int channel;
  private int length = 0;

  public AddressableLEDStrip(int channel, AddressableLEDSegment... segments) {
    this.channel = channel;
    this.segments = segments;
    for (AddressableLEDSegment segment : segments) this.length += segment.getLength();
    this.leds = new AddressableLED(channel);
    this.data = new AddressableLEDBuffer(this.length);
    this.leds.setLength(data.getLength());
    this.leds.setData(data);
    this.leds.start();
  }

  @Override
  public void periodic() {
    for (AddressableLEDSegment segment : segments) {
      segment.update();
      for (int i = 0; i < segment.getLength(); i++) data.setLED(i, segment.data.getLED(i));
    }
    this.leds.setData(data);
  }
}
