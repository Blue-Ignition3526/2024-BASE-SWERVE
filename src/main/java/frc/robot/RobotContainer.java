// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.DriveSwerve;
import frc.robot.subsystems.Gyro.Gyro;
import frc.robot.subsystems.Gyro.GyroIOPigeon;
import frc.robot.subsystems.SwerveDrive.SwerveDrive;
import frc.robot.subsystems.SwerveDrive.SwerveDriveIOReal;
import frc.robot.subsystems.SwerveModule.SwerveModule;
import frc.robot.subsystems.SwerveModule.SwerveModuleIOSim;
import frc.robot.subsystems.SwerveModule.SwerveModuleIOSparkMax;


public class RobotContainer {
  private final CommandXboxController m_driverController = new CommandXboxController(0);

  SwerveModule m_frontLeft;
  SwerveModule m_frontRight;
  SwerveModule m_backLeft;
  SwerveModule m_backRight;
  
  SwerveDrive m_swerveDrive;

  public RobotContainer() {
    if (Robot.isReal()) {
      // Create all swerve modules and initialize
      this.m_frontLeft = new SwerveModule(new SwerveModuleIOSparkMax(Constants.SwerveDrive.SwerveModules.kFrontLeftOptions));
      this.m_frontRight = new SwerveModule(new SwerveModuleIOSparkMax(Constants.SwerveDrive.SwerveModules.kFrontRightOptions));
      this.m_backLeft = new SwerveModule(new SwerveModuleIOSparkMax(Constants.SwerveDrive.SwerveModules.kBackLeftOptions));
      this.m_backRight = new SwerveModule(new SwerveModuleIOSparkMax(Constants.SwerveDrive.SwerveModules.kBackRightOptions));

      // Create the swerve drive and initialize
      this.m_swerveDrive = new SwerveDrive(
        new SwerveDriveIOReal(m_frontLeft, m_frontRight, m_backLeft, m_backRight,
        new Gyro(new GyroIOPigeon(34)))
      );

      Logger.recordMetadata("Robot", "Real");
    } else {
      // Create all swerve modules and initialize
      // this.m_frontLeft = new SwerveModule(new SwerveModuleIOSim(Constants.Swerve.Motors.kFrontLeftVars));
      // this.m_frontRight = new SwerveModule(new SwerveModuleIOSim(Constants.Swerve.Motors.kFrontRightVars));
      // this.m_backLeft = new SwerveModule(new SwerveModuleIOSim(Constants.Swerve.Motors.kBackLeftVars));
      // this.m_backRight = new SwerveModule(new SwerveModuleIOSim(Constants.Swerve.Motors.kBackRightVars));

      // // Create the swerve drive and initialize
      // this.m_swerveDrive = new SwerveDrive(new SwerveDriveIOSim(m_frontLeft, m_frontRight, m_backLeft, m_backRight));

      // // Create a new intake
      // this.m_intake = new Intake(new IntakeIOSim());

      Logger.recordMetadata("Robot", "Sim");
    }

    configureBindings();
  }

  private void configureBindings() {
    m_swerveDrive.setDefaultCommand(new DriveSwerve(
        m_swerveDrive,
        () -> m_driverController.getLeftY(),
        () -> -m_driverController.getLeftX(),
        () -> -m_driverController.getRightX(),
        () -> true
      )
    );
  }

  public Command getAutonomousCommand() {
    return new WaitCommand(1);
  }
}
