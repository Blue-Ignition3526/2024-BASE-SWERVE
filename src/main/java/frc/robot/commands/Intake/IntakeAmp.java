package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Intake.Intake;

public class IntakeAmp extends Command {
  private Timer timer = new Timer();
  private Intake intake;

  public IntakeAmp(Intake intake) {
    this.intake = intake;
    addRequirements(intake);
  }
  
  @Override
  public void initialize() {
    timer.reset();
    timer.start();

    intake.setIntakeOut();
  }
  
  @Override
  public void execute() {
  }
  
  @Override
  public void end(boolean interrupted) {
    intake.stopIntake();
  }
  
  @Override
  public boolean isFinished() {
    return false;
  }
}
