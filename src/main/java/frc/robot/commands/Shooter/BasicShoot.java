package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.Command;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeRollers.IntakeRollers;
import frc.robot.subsystems.Shooter.Shooter;

public class BasicShoot extends Command {

  Shooter m_shooter;
  IntakeRollers rollers;
  private Timer timer = new Timer();

  public BasicShoot(Shooter shooter, IntakeRollers rollers) {
    this.m_shooter = shooter;
    this.rollers = rollers;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {
    this.m_shooter.set(-1);
    
  }

  @Override
  public void end(boolean interrupted) {
    this.m_shooter.stop();
  }

  @Override
  public boolean isFinished() {
    return timer.get() > Constants.Shooter.kMaxShootTime;
  }
}
