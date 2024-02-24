package frc.robot;

import org.littletonrobotics.junction.Logger;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Climb;
import frc.robot.commands.Intake.IntakeIn;
import frc.robot.commands.Intake.IntakeOut;
import frc.robot.commands.Intake.LifterDown;
import frc.robot.commands.Intake.LifterUp;
import frc.robot.commands.Shooter.BasicShoot;
import frc.robot.commands.SwerveDrive.DriveSwerve;
import frc.robot.commands.SwerveDrive.ZeroHeading;
import frc.robot.subsystems.PoseEstimatorSubsystem;
import frc.robot.subsystems.Climber.Climber;
import frc.robot.subsystems.Climber.ClimberIOReal;
import frc.robot.subsystems.Gyro.Gyro;
import frc.robot.subsystems.Gyro.GyroIOPigeon;
import frc.robot.subsystems.IntakeLifter.IntakeLifter;
import frc.robot.subsystems.IntakeLifter.IntakeLifterIOReal;
import frc.robot.subsystems.IntakeRollers.IntakeRollers;
import frc.robot.subsystems.IntakeRollers.IntakeRollersIOReal;
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
  private final IntakeLifter m_intake;
  private final IntakeRollers m_Rollers;
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
      this.m_intake =  new IntakeLifter(new IntakeLifterIOReal());
      this.m_Rollers = new IntakeRollers(new IntakeRollersIOReal());
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
      this.m_intake = new IntakeLifter(null);
      this.m_Rollers = new IntakeRollers(new IntakeRollersIOReal());
      this.m_shooter = new Shooter(null);
      this.m_leftClimber = new Climber(null);
      this.m_rightClimber = new Climber(null);
      this.m_poseEstimator = null;

      Logger.recordMetadata("Robot", "Sim");

    }

    this.m_driverControllerCustom = new CustomController(0, CustomController.CustomControllerType.PS5, CustomController.CustomJoystickCurve.LINEAR);

    SmartDashboard.putData(new ZeroHeading(m_swerveDrive));

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

    /* this.m_driverControllerCustom.leftTrigger().whileTrue(new DriveSwerve(
        m_swerveDrive,
        () -> 0.7,
        () -> 0.0,
        () -> 0.0,
        () -> false
    )); */

    
    this.m_driverControllerCustom.leftButton().toggleOnTrue(new IntakeIn(m_Rollers));
    this.m_driverControllerCustom.topButton().whileTrue(new IntakeOut(m_Rollers));

    this.m_driverControllerCustom.rightTrigger().whileTrue(new BasicShoot(m_shooter));
    this.m_driverControllerCustom.leftTrigger().whileTrue(new IntakeOut(m_Rollers));

    this.m_driverControllerCustom.rightBumper().whileTrue(new LifterUp(m_intake));

    this.m_driverControllerCustom.leftBumper().whileTrue(new LifterDown(m_intake));

    this.m_driverControllerCustom.povUp().whileTrue(new Climb(m_rightClimber, () -> true));
    this.m_driverControllerCustom.povUp().whileTrue(new Climb(m_leftClimber, () -> true));


    this.m_driverControllerCustom.povDown().whileTrue(new Climb(m_rightClimber, () -> false));
    this.m_driverControllerCustom.povDown().whileTrue(new Climb(m_leftClimber, () -> false));
  }

  public Command getAutonomousCommand() {
    return new WaitCommand(1);
  }
}
