package frc.robot.commands.Climber;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Climber.Climber;

public class Climb extends Command {
  Climber climber;
  Supplier<Boolean> left;
  Supplier<Boolean> right;
  
  public Climb(Climber climber, Supplier<Boolean> left, Supplier<Boolean> right) {
    this.climber = climber;
    this.left = left;
    this.right = right;
    addRequirements(climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (left.get()) climber.setLeftClimberDown();
    if (right.get()) climber.setRightClimberDown();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    climber.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
