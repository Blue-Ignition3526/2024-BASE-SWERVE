package lib.team3526.led.animations;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class ShootingStar implements LEDAnimationBase {
    int r;
    int g;
    int b;
    int trailLength;
    double duration;

    public ShootingStar(int r, int g, int b, int trailLength, double duration) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.trailLength = trailLength;
        this.duration = duration;
    }

    public void provider(AddressableLEDBuffer data) {
        double timeFraction = (Timer.getFPGATimestamp() % duration) / duration;
        int trailStart = (int)(timeFraction * data.getLength());

        for (int i = 0; i < data.getLength(); i++) {
            if (i == trailStart) data.setRGB(i, r, g, b);
            
        }
    }
}
