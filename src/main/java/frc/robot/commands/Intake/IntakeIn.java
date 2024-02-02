package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake.Intake;

public class IntakeIn extends Command {
  private Intake intake;

  public IntakeIn(Intake intake) {
    this.intake = intake;
    addRequirements(intake);
  }
  
  @Override
  public void initialize() {}
  
  @Override
  public void execute() {
    intake.setIntakeBrake();
    intake.setIntakeIn();
  }
  
  @Override
  public void end(boolean interrupted) {
    intake.setIntakeHold();
  }
  
  @Override
  public boolean isFinished() {
    return intake.hasPiece();
  }
}
