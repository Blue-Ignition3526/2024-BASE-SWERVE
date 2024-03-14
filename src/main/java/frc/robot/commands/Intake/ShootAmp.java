package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeLifter.IntakeLifter;
import frc.robot.subsystems.IntakeRollers.IntakeRollers;
import frc.robot.subsystems.Leds;

public class ShootAmp extends Command {

  private final IntakeRollers rollers;
  private final IntakeLifter lifter;
  private final Leds leds;
  private final Timer timer = new Timer();

  public ShootAmp(IntakeRollers rollers, IntakeLifter lifter, Leds leds) {
    this.rollers = rollers;
    this.lifter = lifter;
    this.leds = leds;
    addRequirements(rollers, lifter, leds);
  }

  @Override
  public void initialize() {
    this.leds.setLeds("#ad03fc");
  }

  @Override
  public void execute() {
    this.lifter.setLifterAngle(Constants.Intake.Physical.kAmplifierAngle);
  }

  @Override
  public void end(boolean interrupted) {
    this.timer.reset();
    this.timer.start();
    while(timer.get() < 0.5) this.rollers.setRollersOut();
    this.rollers.stop();
    this.timer.stop();
    this.lifter.setLifterAngle(Constants.Intake.Physical.kShooterAngle);
    this.leds.turnOff();
  }

  @Override
  public boolean isFinished() {
    return (this.lifter.getLifterAngleRadians()>1.20);
  }
}
