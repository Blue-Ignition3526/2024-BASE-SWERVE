package lib.team3526;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LoggedPIDController extends PIDController {
    public LoggedPIDController(String name, double kP, double kI, double kD) {
        super(kP, kI, kD);
        SmartDashboard.putData(name, this);
    }
}
