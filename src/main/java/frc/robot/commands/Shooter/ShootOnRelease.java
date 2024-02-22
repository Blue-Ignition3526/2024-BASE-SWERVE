// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Shooter.Shooter;

public class ShootOnRelease extends Command {

  Shooter m_Shooter;
  Intake m_Intake;
  Timer m_timer = new Timer();

  public ShootOnRelease(Shooter shooter, Intake intake) {
    this.m_Shooter = shooter;
    this.m_Intake = intake;
    addRequirements(shooter, intake);
  }

  @Override
  public void initialize() {
    this.m_timer.reset();
    this.m_timer.start();
  }

  @Override
  public void execute() {
    this.m_Shooter.set(-1);
    this.m_Intake.giveToShooter();
  }

  @Override
  public void end(boolean interrupted) {
    this.m_Shooter.stop();
    this.m_Intake.stopIntake();
  }

  @Override
  public boolean isFinished() {
    if (this.m_timer.get()>1.0) {
      return true;
    }
    return false;
  }
}
