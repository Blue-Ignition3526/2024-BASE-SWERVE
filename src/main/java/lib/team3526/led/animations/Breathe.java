package lib.team3526.led.animations;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;

public class Breathe {
    int r;
    int g;
    int b;
    double duration;

    public Breathe(int r, int g, int b, double duration) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.duration = duration;
    }

    public void provider(AddressableLEDBuffer data) {
        double timeFraction = 1 - (Timer.getFPGATimestamp() % duration);
        for (int i = 0; i < data.getLength(); i++) data.setRGB(
            i,
            (int)Math.floor(r * timeFraction),
            (int)Math.floor(g * timeFraction),
            (int)Math.floor(b * timeFraction)
        );
    }
}
