// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static edu.wpi.first.units.Units.Degrees;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.Constants;

public class InAndOut extends Command {
  Intake intake;
  Shooter shooter;

  boolean isInPosition = false;

  /** Creates a new InAndOut. */
  public InAndOut(Intake intake, Shooter shooter) {
    this.intake = intake;
    this.shooter = shooter;

    addRequirements(intake, shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!intake.hasPiece()){
      isInPosition = intake.setLifterAngle(Constants.Intake.Physical.kGroundAngle.in(Degrees));
      intake.setIntakeIn();
      
    }
    if(intake.hasPiece()){
        isInPosition = intake.setLifterAngle(Constants.Intake.Physical.kShooterAngle.in(Degrees));
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if (intake.hasPiece() && Math.abs(intake.getLifterAngle() - Constants.Intake.Physical.kShooterAngle.in(Degrees)) < 5){
      shooter.shootSpeaker();
      intake.giveToShooter();
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
