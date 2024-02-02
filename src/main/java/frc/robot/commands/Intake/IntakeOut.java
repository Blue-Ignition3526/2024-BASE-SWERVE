package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Intake.Intake;

public class IntakeOut extends Command {
  private Timer timer = new Timer();
  private Intake intake;

  public IntakeOut(Intake intake) {
    this.intake = intake;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {
    intake.setIntakeOut();
  }

  @Override
  public void end(boolean interrupted) {
    intake.setIntakeCoast();
    intake.stopIntake();
  }

  @Override
  public boolean isFinished() {
    return timer.get() > Constants.Intake.kMaxOuttakeTime;
  }
}
