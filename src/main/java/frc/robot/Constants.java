package frc.robot;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Velocity;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import lib.team3526.constants.PIDFConstants;
import lib.team3526.utils.CTRECANDevice;
import lib.team3526.utils.SwerveModuleOptions;

import static edu.wpi.first.units.Units.*;

import com.pathplanner.lib.util.PIDConstants;

public final class Constants {
    public static final class SwerveDrive {
        public static final CTRECANDevice kGyroDevice = new CTRECANDevice(34, "*");

        public static final double kJoystickDeadband = 0.1;
        //! Physical model of the robot
        public static final class PhysicalModel {
            //! MAX DISPLACEMENT SPEED (and acceleration)
            public static final Measure<Velocity<Distance>> kMaxSpeed = MetersPerSecond.of(10);
            public static final Measure<Velocity<Velocity<Distance>>> kMaxAcceleration = MetersPerSecond.per(Second).of(kMaxSpeed.in(MetersPerSecond));

            //! MAX ROTATIONAL SPEED (and acceleration)
            public static final Measure<Velocity<Angle>> kMaxAngularSpeed = RadiansPerSecond.of(3 * (2 * Math.PI));
            public static final Measure<Velocity<Velocity<Angle>>> kMaxAngularAcceleration = RadiansPerSecond.per(Second).of(kMaxAngularSpeed.in(RadiansPerSecond));

            // Drive wheel diameter
            public static final Measure<Distance> kWheelDiameter = Inches.of(4);

            // Gear ratios
            public static final double kDriveMotorGearRatio = 1.0 / 6.12; // 6.12:1 Drive
            public static final double kTurningMotorGearRatio = 1.0 / 12.8; // 12.8:1 Steering

            // Conversion factors (Drive Motor)
            public static final double kDriveEncoder_RotationToMeter = kDriveMotorGearRatio * kWheelDiameter.in(Meters) * 2 * Math.PI;
            public static final double kDriveEncoder_RPMToMeterPerSecond = kDriveEncoder_RotationToMeter / 60.0;

            // Conversion factors (Turning Motor)
            public static final double kTurningEncoder_RotationToRadian = kTurningMotorGearRatio * 2.0 * Math.PI;
            public static final double kTurningEncoder_RPMToRadianPerSecond = kTurningEncoder_RotationToRadian / 60.0;

            // Robot Without bumpers measures
            public static final Measure<Distance> kTrackWidth = Inches.of(23.08);
            public static final Measure<Distance> kWheelBase = Inches.of(22.64);
    
            // Create a kinematics instance with the positions of the swerve modules
            public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
                new Translation2d(kWheelBase.in(Meters) / 2, -kTrackWidth.in(Meters) / 2),
                new Translation2d(kWheelBase.in(Meters) / 2, kTrackWidth.in(Meters) / 2),
                new Translation2d(-kWheelBase.in(Meters) / 2, -kTrackWidth.in(Meters) / 2),
                new Translation2d(-kWheelBase.in(Meters) / 2, kTrackWidth.in(Meters) / 2)
            );

            // Rotation lock PIDF Constants
            public static final PIDFConstants kHeadingControllerPIDConstants = new PIDFConstants(0.1, 0.0, 0.0);

            // Rotational inertia constants
            public static final double kRobotMassKg = 46;
        }

        //! Swerve modules configuration
        public static final class SwerveModules {
            //! PID
            public static final PIDFConstants kTurningPIDConstants = new PIDFConstants(0.5);

            //! Global offset
            public static final Measure<Angle> kGlobalOffset = Degrees.of(0);

            public static final SwerveModuleOptions kFrontLeftOptions = new SwerveModuleOptions()
                .setOffsetDeg(0)
                .setAbsoluteEncoderInverted(true)
                .setAbsoluteEncoderCANDevice(new CTRECANDevice(11, "*"))
                .setDriveMotorID(22)
                .setTurningMotorID(21)
                .setDriveMotorInverted(true)
                .setTurningMotorInverted(false)
                .setName("Front Left");

            public static final SwerveModuleOptions kFrontRightOptions = new SwerveModuleOptions()
                .setOffsetDeg(0)
                .setAbsoluteEncoderInverted(true)
                .setAbsoluteEncoderCANDevice(new CTRECANDevice(12, "*"))
                .setDriveMotorID(24)
                .setTurningMotorID(23)
                .setDriveMotorInverted(true)
                .setTurningMotorInverted(false)
                .setName("Front Right");

            public static final SwerveModuleOptions kBackLeftOptions = new SwerveModuleOptions()
                .setOffsetDeg(0)
                .setAbsoluteEncoderInverted(true)
                .setAbsoluteEncoderCANDevice(new CTRECANDevice(13, "*"))
                .setDriveMotorID(26)
                .setTurningMotorID(25)
                .setDriveMotorInverted(true)
                .setTurningMotorInverted(false)
                .setName("Back Left");

            public static final SwerveModuleOptions kBackRightOptions = new SwerveModuleOptions()
                .setOffsetDeg(0)
                .setAbsoluteEncoderInverted(true)
                .setAbsoluteEncoderCANDevice(new CTRECANDevice(14, "*"))
                .setDriveMotorID(28)
                .setTurningMotorID(27)
                .setDriveMotorInverted(true)
                .setTurningMotorInverted(false)
                .setName("Back Right");
        }

        //! AUTONOMOUS 
        public static final class Autonomous {
            public static final PIDConstants kTranslatePIDConstants = new PIDConstants(0.1, 0.0, 0.0);
            public static final PIDConstants kRotatePIDConstants = new PIDConstants(0.1, 0.0, 0.0);
            public static final Measure<Velocity<Distance>> kMaxSpeedMetersPerSecond = MetersPerSecond.of(1);
        }
    }

    //! FIELD
    public static final class Field {
        public static final Pose2d kInitialPoseMeters = new Pose2d(new Translation2d(0, 0), new Rotation2d(0, 0));
    }

    //! VISION
    public static final class Vision {
        public static final AprilTagFieldLayout kAprilTagFieldLayout = AprilTagFields.k2024Crescendo.loadAprilTagLayoutField();
        public static final Transform3d kCameraPose = new Transform3d(new Translation3d(0.5, 0.5, 0.3), new Rotation3d(0, -2, 0));
    }

    //* INTAKE
    public static final class Intake {

    //////////////////////////////////////////////// ROLLERS ////////////////////////////////////////////////

        // Intake motor config
        public static final int kintakeMotorID = 36;
        public static final PIDFConstants kIntakePIDConstants = new PIDFConstants(0.00001, 0.0001, 0.0,0.0001);
        public static final double kHasPieceCurrentThreshold = 20;
        public static final double kHasPieceTimeThreshold = 0.2;

        // Intake gear ratio
        public static final double kIntakeRollersGearRatio = 5.0/1.0;

        // Speeds
        public static final double kIntakeOutSpeed = -1250.0;
        public static final Measure<Velocity<Angle>> kIntakeOutSpeedRpm = RPM.of(kIntakeOutSpeed*kIntakeRollersGearRatio);
        public static final double kIntakeInSpeed = 1000.0;
        public static final Measure<Velocity<Angle>> kIntakeInSpeedRpm = RPM.of(kIntakeInSpeed*kIntakeRollersGearRatio);
        public static final double kIntakeHoldSpeed = 50.0;
        public static final Measure<Velocity<Angle>> kIntakeHoldSpeedRpm = RPM.of(kIntakeHoldSpeed*kIntakeRollersGearRatio);
        public static final double kGiveToShooterSpeed = -1000.0;
        public static final Measure<Velocity<Angle>> kGiveToShooterSpeedRpm = RPM.of(kGiveToShooterSpeed*kIntakeRollersGearRatio);

        // Lifter encoder 
        public static final int kLifterEncoderPort = 0;
        public static final double kLifterEncoderOffset = 0.3626;

        // Intake times
        public static final double kMaxOuttakeTime = 3;
        public static final double kMaxIntakeTime = 6;
        
    ///////////////////////////////////////////////// LIFTER /////////////////////////////////////////////////
        
        // Lifter motor config
        public static final int kLifterMotorID = 37;
        public static final ArmFeedforward kLifterFeedforward = new ArmFeedforward(0.0, 1.5, 0.2);
        public static final Constraints kLifterConstraints = new Constraints(2, 2);
        public static final ProfiledPIDController kLifterPIDController = new ProfiledPIDController(0.0, 0.0, 0.0, kLifterConstraints);
        public static final double kLifterFeedForward = 5;

        public static final class Physical {
            public static final Measure<Angle> kLifterMaxHeight = Radians.of((37/36)*Math.PI);
            public static final Measure<Angle> kLifterMinHeight = Radians.of(0);

            public static final Measure<Angle> kShooterAngle = Radians.of(0);
            public static final Measure<Angle> kAmplifierAngle = Radians.of((7/18)*Math.PI);
            public static final Measure<Angle> kGroundAngle = Degrees.of(180);
        }
    }

    //! SHOOTER
    public static final class Shooter {
        // Shooter motor config
        public static final int kLeftShooterMotorID = 30;
        public static final int kRightShooterMotorID = 31;
        public static final PIDFConstants kShooterPIDConstants = new PIDFConstants(0.0, 0.0, 0.0);

        // Shooter speed
        public static final double kShooterSpeed = 5000;
        // Shooter motor rpm conversion
        public static final double kShooterGearRatio = 1.0/1.0;

        // Shooter speeds
        public static final Measure<Velocity<Angle>> kShooterSpeakerSpeed = RPM.of(kShooterSpeed*kShooterGearRatio);
        public static final Measure<Velocity<Angle>> kTakeFromHumanPlayer = RPM.of(-5);

        // Shooter motor time
        public static final double kMaxShootTime = 4;
    }

    //! CLIMBER
    public static final class Climber {
        // Climber motor config
        public static final int kLeftClimberMotorID = 33;
        public static final int kRightClimberMotorID = 32;
        // public static final PIDFConstants kClimberPIDConstants = new PIDFConstants(0.1, 0.0, 0.0);

        // Climber speed
        public static final double kClimberUpSpeed = 0.75;
        public static final double kClimberDownSpeed = -0.5;
        public static final double kClimberHoldSpeed = kClimberDownSpeed / 2;

        // Climber motor rpm conversion
        public static final double kClimber_RotationToCentimeters = 1 / 16 / 3 / Centimeters.of(31).in(Centimeters);

        // Max current (Used for reseting the climber)
        public static final double kMaxCurrent = 35;
    }
}
