// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climbers;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Climber.Climber;

public class ClimberFullyDown extends Command {
  Climber climber;
  Timer timer = new Timer();

  public ClimberFullyDown(Climber climber) {
    this.climber = climber;
    addRequirements(climber);
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {
    this.climber.setClimberDown();
  }

  @Override
  public void end(boolean interrupted) {
    this.climber.stop();
    this.climber.resetEncoder();
  }

  @Override
  public boolean isFinished() {
    return climber.getCurrent() >= Constants.Climber.kMaxCurrent || timer.get() >= Constants.Climber.kMaxTimeToFullyDown;
  }
}
