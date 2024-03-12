package frc.robot;

import org.littletonrobotics.junction.Logger;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.BasicAutos;
import frc.robot.commands.DefaultLedState;
import frc.robot.commands.Climbers.ClimbersDown;
import frc.robot.commands.Climbers.ClimbersUp;
import frc.robot.commands.Intake.IntakeIn;
import frc.robot.commands.Intake.IntakeOut;
import frc.robot.commands.Intake.LifterAmp;
import frc.robot.commands.Intake.LifterFloor;
import frc.robot.commands.Intake.LifterShooter;
import frc.robot.commands.Intake.PickUpPiece;
import frc.robot.commands.Intake.ShootAmp;
import frc.robot.commands.Shooter.Shoot;
import frc.robot.commands.Shooter.SpinShooter;
import frc.robot.commands.SwerveDrive.DriveSwerve;
import frc.robot.commands.SwerveDrive.ZeroHeading;
import frc.robot.subsystems.Climber.Climber;
import frc.robot.subsystems.Climber.ClimberIOReal;
import frc.robot.subsystems.Climber.ClimberIOSim;
import frc.robot.subsystems.Gyro.Gyro;
import frc.robot.subsystems.Gyro.GyroIOPigeon;
import frc.robot.subsystems.Gyro.GyroIOSim;
import frc.robot.subsystems.IntakeLifter.IntakeLifter;
import frc.robot.subsystems.IntakeLifter.IntakeLifterIOReal;
import frc.robot.subsystems.IntakeLifter.IntakeLifterIOSim;
import frc.robot.subsystems.IntakeRollers.IntakeRollers;
import frc.robot.subsystems.IntakeRollers.IntakeRollersIOReal;
import frc.robot.subsystems.IntakeRollers.IntakeRollersIOSim;
import frc.robot.subsystems.Leds.Leds;
import frc.robot.subsystems.Leds.LedsIOReal;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Shooter.ShooterIOReal;
import frc.robot.subsystems.Shooter.ShooterIOSim;
import frc.robot.subsystems.SwerveDrive.SwerveDrive;
import frc.robot.subsystems.SwerveDrive.SwerveDriveIOReal;
import frc.robot.subsystems.SwerveModule.SwerveModule;
import lib.team3526.commands.RunForCommand;
import lib.team3526.driveControl.CustomController;
import frc.robot.subsystems.SwerveModule.SwerveModuleIOReal;
import java.util.HashMap;

public class RobotContainer {
  // * Controller
  private final CustomController m_driverControllerCustom;

  // *  Swerve Modules
  private final SwerveModule m_frontLeft;
  private final SwerveModule m_frontRight;
  private final SwerveModule m_backLeft;
  private final SwerveModule m_backRight;
  
  // * Gyroscope
  private final Gyro m_gyro;
  
  // * Swerve Drive
  private final SwerveDrive m_swerveDrive;

  // * Intake
  private final IntakeLifter m_intake;
  private final IntakeRollers m_rollers;

  // * Shooter
  private final Shooter m_shooter;

  // * Climbers
  private final Climber m_leftClimber;
  private final Climber m_rightClimber;

  // * LEDs
  private final Leds m_leds;

  // * Autonomous Chooser
  SendableChooser<Command> autonomousChooser;

  public RobotContainer() {
    // Create controller
    this.m_driverControllerCustom = new CustomController(0, CustomController.CustomControllerType.PS5, CustomController.CustomJoystickCurve.LINEAR);

    if (Robot.isReal()) {
      // Swerve Module creation
      this.m_frontLeft = new SwerveModule(new SwerveModuleIOReal(Constants.SwerveDrive.SwerveModules.kFrontLeftOptions));
      this.m_frontRight = new SwerveModule(new SwerveModuleIOReal(Constants.SwerveDrive.SwerveModules.kFrontRightOptions));
      this.m_backLeft = new SwerveModule(new SwerveModuleIOReal(Constants.SwerveDrive.SwerveModules.kBackLeftOptions));
      this.m_backRight = new SwerveModule(new SwerveModuleIOReal(Constants.SwerveDrive.SwerveModules.kBackRightOptions));

      // Gyroscope
      this.m_gyro = new Gyro(new GyroIOPigeon(Constants.SwerveDrive.kGyroDevice));

      // Swerve Drive
      this.m_swerveDrive = new SwerveDrive(new SwerveDriveIOReal(m_frontLeft, m_frontRight, m_backLeft, m_backRight, m_gyro));

      // Intake
      this.m_intake =  new IntakeLifter(new IntakeLifterIOReal());
      this.m_rollers = new IntakeRollers(new IntakeRollersIOReal());

      // Shooter
      this.m_shooter = new Shooter(new ShooterIOReal());

      // Climbers
      this.m_leftClimber = new Climber(new ClimberIOReal(Constants.Climber.kLeftClimberMotorID, "LeftClimber"));
      this.m_rightClimber = new Climber(new ClimberIOReal(Constants.Climber.kRightClimberMotorID, "RightClimber"));

      // LEDs
      this.m_leds = new Leds(new LedsIOReal(Constants.CANdle.kCANdle));
      this.m_leds.turnOff();

      // Metadata
      Logger.recordMetadata("Robot", "Real");
    } else {
      // Swerve Module creation
      this.m_frontLeft = null;
      this.m_frontRight = null;
      this.m_backLeft = null;
      this.m_backRight = null;

      // Gyroscope
      this.m_gyro = new Gyro(new GyroIOSim());

      // Swerve Drive
      this.m_swerveDrive = null;

      // Intake
      this.m_intake = new IntakeLifter(new IntakeLifterIOSim());
      this.m_rollers = new IntakeRollers(new IntakeRollersIOSim());

      // Shooter
      this.m_shooter = new Shooter(new ShooterIOSim());

      // Climbers
      this.m_leftClimber = new Climber(new ClimberIOSim());
      this.m_rightClimber = new Climber(new ClimberIOSim());

      // LEDs
      this.m_leds = null;

      // Metadata
      Logger.recordMetadata("Robot", "Sim");
    }

    // Register the named commands for autonomous
    NamedCommands.registerCommands(new HashMap<String, Command>() {{
      put("IntakeIn", new RunForCommand(new IntakeIn(m_rollers), 1));
      put("IntakeOut", new RunForCommand(new IntakeOut(m_rollers), 0.25));

      put("Shoot", new RunForCommand(new Shoot(m_shooter, m_rollers, m_leds), 0.5));

      put("LifterFloor", new RunForCommand(new LifterFloor(m_intake), 0.5));
      put("LifterShooter", new RunForCommand(new LifterShooter(m_intake), 0.5));
    }});
 
    // Add commands to SmartDashboard
    SmartDashboard.putData(new ZeroHeading(m_swerveDrive));

    // Autonomous chooser
    this.autonomousChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Autonomous", this.autonomousChooser);

    // Configure the button bindings
    configureBindings();
  }

  private void configureBindings() {
    // Set the default command for the swerve drive
    this.m_swerveDrive.setDefaultCommand(new DriveSwerve(
        m_swerveDrive,
        () -> -this.m_driverControllerCustom.getLeftY(),
        () -> -this.m_driverControllerCustom.getLeftX(),
        () -> -this.m_driverControllerCustom.getRightX(),
        () -> !this.m_driverControllerCustom.topButton().getAsBoolean(),
        () -> this.m_driverControllerCustom.leftButton().getAsBoolean()
      )
    );

    this.m_leds.setDefaultCommand(new DefaultLedState(m_leds));

    this.m_driverControllerCustom.bottomButton().toggleOnTrue(new PickUpPiece(this.m_rollers, this.m_intake, this.m_leds));

    this.m_driverControllerCustom.rightTrigger().whileTrue(new SpinShooter(this.m_shooter, this.m_leds));
      this.m_driverControllerCustom.rightTrigger().onFalse(new Shoot(this.m_shooter, this.m_rollers, this.m_leds));
    this.m_driverControllerCustom.povLeft().toggleOnTrue(new ShootAmp(this.m_rollers, this.m_intake, this.m_leds));

    this.m_driverControllerCustom.rightBumper().whileTrue(new LifterFloor(this.m_intake));
    this.m_driverControllerCustom.leftBumper().whileTrue(new LifterShooter(this.m_intake));
    this.m_driverControllerCustom.povRight().whileTrue(new LifterAmp(this.m_intake));

    this.m_driverControllerCustom.povUp().whileTrue(new ClimbersUp(this.m_leftClimber, this.m_rightClimber));
    this.m_driverControllerCustom.povDown().whileTrue(new ClimbersDown(this.m_leftClimber, this.m_rightClimber));
  }

  public Command getAutonomousCommand() {
    return this.autonomousChooser.getSelected();
  };
}
