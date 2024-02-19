package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter.Shooter;

public class BasicShoot extends Command {

  Shooter m_shooter;
  private Timer timer = new Timer();

  public BasicShoot(Shooter shooter) {
    this.m_shooter = shooter;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {
    this.m_shooter.set(-0.7);
  }

  @Override
  public void end(boolean interrupted) {
    this.m_shooter.set(0);
  }

  @Override
  public boolean isFinished() {
    return timer.get() > Constants.Shooter.kMaxShootTime;
  }
}
