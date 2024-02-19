package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.Intake.IntakeOut;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Shooter.Shooter;
import lib.team3526.commands.RunForCommand;

public class OnReleaseShoot extends Command {

  Shooter m_shooter;
  Intake m_intake;

  public OnReleaseShoot(Shooter shooter, Intake intake) {
    this.m_shooter = shooter;
    this.m_intake = intake;
    addRequirements(shooter, intake);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    new BasicShoot(m_shooter);
  }

  @Override
  public void end(boolean interrupted) {
    new RunForCommand(new ParallelCommandGroup(new BasicShoot(this.m_shooter), new IntakeOut(this.m_intake)), 1);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
