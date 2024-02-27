package lib.team3526.led.animations;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class Rainbow implements LEDAnimationBase {
    double duration;

    public Rainbow(double duration) {
        this.duration = duration;
    }

    public void provider(AddressableLEDBuffer data) {
        double timeFraction = (Timer.getFPGATimestamp() % duration) / duration;
        for (int i = 0; i < data.getLength(); i++) data.setHSV(i, (int)(timeFraction * 255), 255, 255);
    }
}
