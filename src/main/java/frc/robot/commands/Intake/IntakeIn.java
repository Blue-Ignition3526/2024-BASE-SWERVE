package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Intake.Intake;

public class IntakeIn extends Command {
  private Timer timer = new Timer();
  private Intake intake;

  public IntakeIn(Intake intake) {
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
    intake.setIntakeBrake();
    intake.setIntakeIn();
  }
  
  @Override
  public void end(boolean interrupted) {
    if (intake.hasPiece()) intake.setIntakeHold();
    else intake.stopIntake();
  }
  
  @Override
  public boolean isFinished() {
    return intake.hasPiece() || timer.get() > Constants.Intake.kMaxIntakeTime;
  }
}
