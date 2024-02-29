package frc.robot.subsystems.Climber;

import static edu.wpi.first.units.Units.Centimeters;

import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Measure;

public class ClimberIOSim implements ClimberIO {
    double extension = 0;

    public Measure<Distance> getExtension() { return Centimeters.of(this.extension); }
    public double getCurrent() { return 0; }
    public void resetEncoder() {}

    public void set(double speed) {};

    public void setClimberUp() {
        this.extension += 1;
    }

    public void setClimberDown() {
        this.extension -= 1;
    }

    public void setClimberHold() {}

    public void stop() {}

    public void updateInputs(ClimberIOInputs inputs) {}
    public void periodic() {}
}
