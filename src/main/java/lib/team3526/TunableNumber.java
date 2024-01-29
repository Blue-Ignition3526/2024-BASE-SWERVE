package lib.team3526;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TunableNumber {
    private double value;
    private String name;
    private double initialValue;
    private double lastRead;

    public TunableNumber(String name, double initialValue) {
        this.name = name;
        this.initialValue = initialValue;
        this.value = initialValue;
        this.lastRead = Timer.getFPGATimestamp();
        SmartDashboard.putNumber(name, initialValue);

    }

    public TunableNumber(String name) {
        this(name, 0);
    }

    public void update() {
        double newValue = SmartDashboard.getNumber(name, initialValue);
        if (newValue != value) {
            value = newValue;
            lastRead = Timer.getFPGATimestamp();
        }
    }

    public double getValue() {
        if (Timer.getFPGATimestamp() - lastRead > 0.1) update();
        return value;
    }

    public void setValue(double value) {
        this.value = value;
        SmartDashboard.putNumber(name, value);
    }
}
