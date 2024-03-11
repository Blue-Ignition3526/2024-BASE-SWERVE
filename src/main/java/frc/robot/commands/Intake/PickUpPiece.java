package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeLifter.IntakeLifter;
import frc.robot.subsystems.IntakeRollers.IntakeRollers;
import frc.robot.subsystems.Leds.Leds;

public class PickUpPiece extends Command {
  private IntakeRollers rollers;
  private IntakeLifter lifter;
  private Leds leds;

  public PickUpPiece(IntakeRollers rollers, IntakeLifter lifter, Leds leds) {
    this.rollers = rollers;
    this.lifter = lifter;
    this.leds = leds;
    addRequirements(rollers, lifter, leds);
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    this.lifter.setLifterAngle(Constants.Intake.Physical.kGroundAngle);
    this.rollers.setRollersIn();
    this.leds.blinkLeds("#fc8b00");
  }

  @Override
  public void end(boolean interrupted) {
    this.lifter.setLifterAngle(Constants.Intake.Physical.kShooterAngle);
    this.rollers.stop();
    this.leds.setLeds("#00ff00");
  }

  @Override
  public boolean isFinished() {
    return this.rollers.hasPiece();
  }
}
