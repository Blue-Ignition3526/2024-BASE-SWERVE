package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeRollers.IntakeRollers;
import frc.robot.subsystems.Leds;
import frc.robot.subsystems.Shooter.Shooter;

public class Shoot extends Command {
  private final Shooter shooter;
  private final IntakeRollers rollers;
  private final Leds leds;
  private final Timer timer = new Timer();

  public Shoot(Shooter shooter, IntakeRollers rollers, Leds leds) {
    this.shooter = shooter;
    this.rollers = rollers;
    this.leds = leds;
    addRequirements(shooter, rollers, leds);
  }

  @Override
  public void initialize() {
    this.timer.reset();
    this.timer.start();
  }

  @Override
  public void execute() {
    this.shooter.shootSpeaker();
    this.rollers.giveToShooter();
    this.leds.setLeds("#0000ff");
  }

  @Override
  public void end(boolean interrupted) {
    this.shooter.stop();
    this.rollers.stop();
    this.timer.stop();
    this.leds.turnOff();
  }

  @Override
  public boolean isFinished() {
    return (this.timer.get() > 1.0);
  }
}
