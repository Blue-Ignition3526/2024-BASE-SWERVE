package frc.robot;

import org.littletonrobotics.junction.Logger;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.BasicAutos;
import frc.robot.commands.Climb;
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
import frc.robot.commands.Shooter.BasicShoot;
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


  private final CustomController m_driverControllerCustom;

  private final SwerveModule m_frontLeft;
  private final SwerveModule m_frontRight;
  private final SwerveModule m_backLeft;
  private final SwerveModule m_backRight;
  
  private final SwerveDrive m_swerveDrive;
  private final Gyro m_gyro;
  private final IntakeLifter m_intake;
  private final IntakeRollers m_Rollers;
  private final Shooter m_shooter;
  private final Climber m_leftClimber;
  private final Climber m_rightClimber;

  private final Leds m_leds;

  SendableChooser<Command> autonomousChooser;
  SendableChooser<Command> basicAutonomousChooser;


  public RobotContainer() {
    if (Robot.isReal()) {
      // Create all real swerve modules and initialize
      this.m_frontLeft = new SwerveModule(new SwerveModuleIOReal(Constants.SwerveDrive.SwerveModules.kFrontLeftOptions));
      this.m_frontRight = new SwerveModule(new SwerveModuleIOReal(Constants.SwerveDrive.SwerveModules.kFrontRightOptions));
      this.m_backLeft = new SwerveModule(new SwerveModuleIOReal(Constants.SwerveDrive.SwerveModules.kBackLeftOptions));
      this.m_backRight = new SwerveModule(new SwerveModuleIOReal(Constants.SwerveDrive.SwerveModules.kBackRightOptions));

      // Create the real swerve drive and initialize
      this.m_gyro = new Gyro(new GyroIOPigeon(Constants.SwerveDrive.kGyroDevice));
      this.m_swerveDrive = new SwerveDrive(new SwerveDriveIOReal(m_frontLeft, m_frontRight, m_backLeft, m_backRight, m_gyro));
      this.m_intake =  new IntakeLifter(new IntakeLifterIOReal());
      this.m_Rollers = new IntakeRollers(new IntakeRollersIOReal());
      this.m_shooter = new Shooter(new ShooterIOReal());
      this.m_leftClimber = new Climber(new ClimberIOReal(Constants.Climber.kLeftClimberMotorID, "LeftClimber"));
      this.m_rightClimber = new Climber(new ClimberIOReal(Constants.Climber.
      kRightClimberMotorID, "RightClimber"));

      this.m_leds = new Leds(new LedsIOReal(Constants.CANdle.kCANdle));
      this.m_leds.turnOff();

      // this.m_poseEstimator = new PoseEstimatorSubsystem(new Vision[] {
      //   new Vision(new LimelightIO("limelight"))
      // }, m_swerveDrive, m_gyro);

      Logger.recordMetadata("Robot", "Real");
    } else {
      // Create all simulated swerve modules and initialize
      this.m_frontLeft = null;
      this.m_frontRight = null;
      this.m_backLeft = null;
      this.m_backRight = null;

      // Create the simulated swerve drive and initialize
      this.m_gyro = new Gyro(new GyroIOSim());
      this.m_swerveDrive = null;
      this.m_intake = new IntakeLifter(new IntakeLifterIOSim());
      this.m_Rollers = new IntakeRollers(new IntakeRollersIOSim());
      this.m_shooter = new Shooter(new ShooterIOSim());
      this.m_leftClimber = new Climber(new ClimberIOSim());
      this.m_rightClimber = new Climber(new ClimberIOSim());

      this.m_leds = null;
      // this.m_poseEstimator = null;

      Logger.recordMetadata("Robot", "Sim");
    }

    // boolean isXbox = DriverStation.getJoystickIsXbox(0);
    boolean isXbox = true;
    SmartDashboard.putBoolean("Controller/IsXbox", isXbox);
    this.m_driverControllerCustom = new CustomController(0, CustomController.CustomControllerType.PS5, CustomController.CustomJoystickCurve.LINEAR);

    // Register the named commands for autonomous
    NamedCommands.registerCommands(new HashMap<String, Command>() {{
      put("IntakeIn", new RunForCommand(new IntakeIn(m_Rollers), 1));
      put("IntakeOut", new RunForCommand(new IntakeOut(m_Rollers), 0.25));

      put("BasicShoot", new RunForCommand(new BasicShoot(m_shooter, m_Rollers), 2));


      put("ClimbUp", new RunForCommand(new Climb(m_rightClimber, () -> true), 1));
      put("ClimbDown", new RunForCommand(new Climb(m_leftClimber, () -> false), 1));

      put("Wait", new WaitCommand(1));
    }});
 
    SmartDashboard.putData(new ZeroHeading(m_swerveDrive));
    

    SendableChooser<Command> autonomousChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Autonomous", autonomousChooser);
    this.autonomousChooser = autonomousChooser;

    SendableChooser<Command> basicAutonomousChooser = new SendableChooser<Command>();
    basicAutonomousChooser.setDefaultOption("Do Nothing", new WaitCommand(1));
    basicAutonomousChooser.addOption("Shoot", BasicAutos.shoot(m_shooter, m_Rollers));
    basicAutonomousChooser.addOption("Leave Community", BasicAutos.leaveCommunity(m_swerveDrive));
    basicAutonomousChooser.addOption("Shoot and Leave", BasicAutos.shootAndLeave(m_swerveDrive, m_shooter, m_Rollers));
    SmartDashboard.putData("Basic Autonomous", basicAutonomousChooser);
    this.basicAutonomousChooser = basicAutonomousChooser;

    configureBindings();
  }

  private void configureBindings() {
    // Set the default command for the swerve drive
    this.m_swerveDrive.setDefaultCommand(new DriveSwerve(
        m_swerveDrive,
        () -> -this.m_driverControllerCustom.getLeftY(),
        () -> this.m_driverControllerCustom.getLeftX(),
        () -> this.m_driverControllerCustom.getRightX(),
        () -> !this.m_driverControllerCustom.topButton().getAsBoolean(),
        () -> this.m_driverControllerCustom.leftButton().getAsBoolean()
      )
    );

    this.m_leds.setDefaultCommand(new DefaultLedState(m_leds));

    this.m_driverControllerCustom.bottomButton().toggleOnTrue(new PickUpPiece(this.m_Rollers, this.m_intake, this.m_leds));

    this.m_driverControllerCustom.rightTrigger().whileTrue(new SpinShooter(this.m_shooter, this.m_leds));
      this.m_driverControllerCustom.rightTrigger().onFalse(new Shoot(this.m_shooter, this.m_Rollers, this.m_leds));

    this.m_driverControllerCustom.leftTrigger().whileTrue(new IntakeOut(m_Rollers));

    this.m_driverControllerCustom.povLeft().toggleOnTrue(new ShootAmp(this.m_Rollers, this.m_intake, this.m_leds));

    this.m_driverControllerCustom.rightBumper().whileTrue(new LifterFloor(this.m_intake));
    this.m_driverControllerCustom.leftBumper().whileTrue(new LifterShooter(this.m_intake));

    this.m_driverControllerCustom.povUp().whileTrue(new ClimbersUp(this.m_leftClimber, this.m_rightClimber));
    this.m_driverControllerCustom.povDown().whileTrue(new ClimbersDown(this.m_leftClimber, this.m_rightClimber));

    this.m_driverControllerCustom.povRight().whileTrue(new BasicShoot(m_shooter, m_Rollers));
  }

  public Command getAutonomousCommand() {
    return this.basicAutonomousChooser.getSelected();
  };
}
