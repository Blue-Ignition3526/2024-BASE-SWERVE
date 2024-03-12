package lib.team3526.utils;

import frc.robot.Constants;
import frc.robot.LimelightHelpers;

public class LimelightLED {
    /**
     * Blink the LEDs on the limelight for a given duration
     * @param limelightName
     */
    public static final void blinkLeds(String limelightName) {
        blinkLeds(limelightName, 0.5);
    }

    /**
     * Blink the LEDs on the limelight for a given duration
     * @param limelightName
     * @param duration
     */
    public static final void blinkLeds(String limelightName, double duration) {
        Thread thread = new Thread(() -> {
            LimelightHelpers.setLEDMode_ForceBlink(Constants.Vision.kLimelightName);
            try {Thread.sleep((int)duration * 1000);} catch(Exception e) {}
            LimelightHelpers.setLEDMode_ForceOff(Constants.Vision.kLimelightName);
        });
        thread.start();
        try {thread.join();} catch(Exception e) {}
    }
}
