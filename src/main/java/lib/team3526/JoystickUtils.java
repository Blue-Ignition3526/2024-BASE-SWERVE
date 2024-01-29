package lib.team3526;

public class JoystickUtils {
    public static double applyExpo(double input, double expo) {
        return Math.signum(input) * Math.pow(Math.abs(input), expo);
    }
}
