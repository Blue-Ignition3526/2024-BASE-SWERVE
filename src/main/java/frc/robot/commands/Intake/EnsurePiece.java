package frc.robot.commands.Intake;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.IntakeRollers.IntakeRollers;
import lib.team3526.utils.LimelightLED;

public class EnsurePiece extends Command {
  IntakeRollers rollers;
  Timer timer = new Timer();
  Supplier<Boolean> stopAutonomous;

  public EnsurePiece(IntakeRollers rollers, Supplier<Boolean> stopAutonomous) {
    this.rollers = rollers;
    this.stopAutonomous = stopAutonomous;
    addRequirements(rollers);
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {
    if (!rollers.hasPiece()) {
      rollers.setRollersIn();
    } else {
      rollers.stop();
    }
  }

  @Override
  public void end(boolean interrupted) {
    if (rollers.hasPiece()) LimelightLED.blinkLeds(Constants.Vision.kLimelightName);
    // TODO: find HOW TO STOP A COMMAND 😭
    rollers.stop();
  }

  @Override
  public boolean isFinished() {
    return rollers.hasPiece() || timer.get() > 0.5;
  }
}
