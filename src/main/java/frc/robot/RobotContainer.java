package frc.robot;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.Shoot;
import frc.robot.commands.Climb;
import frc.robot.commands.Intake.IntakeIn;
import frc.robot.commands.Intake.IntakeOut;
import frc.robot.commands.Intake.LifterAmp;
import frc.robot.commands.Intake.LifterShooter;
import frc.robot.commands.Shooter.OnReleaseShoot;
import frc.robot.commands.SwerveDrive.DriveSwerve;
import frc.robot.commands.SwerveDrive.ZeroHeading;
import frc.robot.subsystems.PoseEstimatorSubsystem;
import frc.robot.subsystems.Climber.Climber;
import frc.robot.subsystems.Climber.ClimberIOReal;
import frc.robot.subsystems.Gyro.Gyro;
import frc.robot.subsystems.Gyro.GyroIOPigeon;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Intake.IntakeIOReal;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Shooter.ShooterIOReal;
import frc.robot.subsystems.SwerveDrive.SwerveDrive;
import frc.robot.subsystems.SwerveDrive.SwerveDriveIOReal;
import frc.robot.subsystems.SwerveDrive.SwerveDriveIOSim;
import frc.robot.subsystems.SwerveModule.SwerveModule;
import frc.robot.subsystems.SwerveModule.SwerveModuleIOSim;
import lib.team3526.driveControl.CustomController;
import frc.robot.subsystems.Vision.LimelightIO;
import frc.robot.subsystems.Vision.Vision;
import frc.robot.subsystems.SwerveModule.SwerveModuleIOReal;


public class RobotContainer {


  private final CustomController m_driverControllerCustom;

  private final SwerveModule m_frontLeft;
  private final SwerveModule m_frontRight;
  private final SwerveModule m_backLeft;
  private final SwerveModule m_backRight;
  
  private final SwerveDrive m_swerveDrive;
  private final Gyro m_gyro;
  private final Intake m_intake;
  private final Shooter m_shooter;
  private final Climber m_leftClimber;
  private final Climber m_rightClimber;
  private final PoseEstimatorSubsystem m_poseEstimator;

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
      this.m_intake =  new Intake(new IntakeIOReal());
      this.m_shooter = new Shooter(new ShooterIOReal());
      this.m_leftClimber = new Climber(new ClimberIOReal(Constants.Climber.kLeftClimberMotorID, "LeftClimber"));
      this.m_rightClimber = new Climber(new ClimberIOReal(Constants.Climber.kRightClimberMotorID, "RightClimber"));
      this.m_poseEstimator = new PoseEstimatorSubsystem(new Vision[] {
        new Vision(new LimelightIO("limelight"))
      }, m_swerveDrive, m_gyro);

      Logger.recordMetadata("Robot", "Real");
    } else {
      // Create all simulated swerve modules and initialize
      this.m_frontLeft = new SwerveModule(new SwerveModuleIOSim(Constants.SwerveDrive.SwerveModules.kFrontLeftOptions));
      this.m_frontRight = new SwerveModule(new SwerveModuleIOSim(Constants.SwerveDrive.SwerveModules.kFrontRightOptions));
      this.m_backLeft = new SwerveModule(new SwerveModuleIOSim(Constants.SwerveDrive.SwerveModules.kBackLeftOptions));
      this.m_backRight = new SwerveModule(new SwerveModuleIOSim(Constants.SwerveDrive.SwerveModules.kBackRightOptions));

      // Create the simulated swerve drive and initialize
      this.m_gyro = new Gyro(null);
      this.m_swerveDrive = new SwerveDrive(new SwerveDriveIOSim(m_frontLeft, m_frontRight, m_backLeft, m_backRight));
      this.m_intake = new Intake(null);
      this.m_shooter = new Shooter(null);
      this.m_leftClimber = new Climber(null);
      this.m_rightClimber = new Climber(null);
      this.m_poseEstimator = null;

      Logger.recordMetadata("Robot", "Sim");

    }

    this.m_driverControllerCustom = new CustomController(0, CustomController.CustomControllerType.PS5, CustomController.CustomJoystickCurve.CUBIC);

    configureBindings();
  }

  private void configureBindings() {
    // Set the default command for the swerve drive
    this.m_swerveDrive.setDefaultCommand(new DriveSwerve(
        m_swerveDrive,
        () -> this.m_driverControllerCustom.getLeftY(),
        () -> -this.m_driverControllerCustom.getLeftX(),
        () -> -this.m_driverControllerCustom.getRightX(),
        () -> !this.m_driverControllerCustom.bottomButton().getAsBoolean()
      )
    );

    this.m_driverControllerCustom.leftTrigger().whileTrue(new DriveSwerve(
        m_swerveDrive,
        () -> 0.7,
        () -> 0.0,
        () -> 0.0,
        () -> false
    ));

    
    this.m_driverControllerCustom.leftButton().toggleOnTrue(new IntakeIn(m_intake));
    this.m_driverControllerCustom.topButton().whileTrue(new IntakeOut(m_intake));

    this.m_driverControllerCustom.rightTrigger().whileTrue(new OnReleaseShoot(m_shooter, m_intake));

    this.m_driverControllerCustom.leftBumper().whileTrue(new Climb(m_leftClimber, () -> !this.m_driverControllerCustom.bottomButton().getAsBoolean()));
    this.m_driverControllerCustom.rightBumper().whileTrue(new Climb(m_rightClimber, () -> !this.m_driverControllerCustom.bottomButton().getAsBoolean()));

    this.m_driverControllerCustom.povUp().whileTrue(new LifterShooter(m_intake));
  }

  public Command getAutonomousCommand() {
    return new WaitCommand(1);
  }
}
