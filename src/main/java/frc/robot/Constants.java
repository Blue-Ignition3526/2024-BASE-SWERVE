package frc.robot;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Velocity;
import lib.team3526.constants.PIDFConstants;
import lib.team3526.utils.SwerveChassis;
import lib.team3526.utils.SwerveModuleOptions;

import static edu.wpi.first.units.Units.*;

import com.pathplanner.lib.util.PIDConstants;

public final class Constants {
    public static final class SwerveDrive {
        public static final int kGyroCanID = 34;

        public static final double kJoystickDeadband = 0.05;
        //! Physical model of the robot
        public static final class PhysicalModel {
            //! MAX DISPLACEMENT SPEED (and acceleration)
            public static final Measure<Velocity<Distance>> kMaxSpeed = MetersPerSecond.of(20);
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
            public static final Measure<Distance> kTrackWidth = Inches.of(30);
            public static final Measure<Distance> kWheelBase = Inches.of(30);
    
            // Create a kinematics instance with the positions of the swerve modules
            public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(SwerveChassis.sizeToModulePositions(kTrackWidth.in(Meters), kWheelBase.in(Meters)));

            // Rotation lock PIDF Constants
            public static final PIDFConstants kHeadingControllerPIDConstants = new PIDFConstants(0.1, 0.0, 0.0);
        }

        //! Swerve modules configuration
        public static final class SwerveModules {
            //! PID
            public static final PIDFConstants kTurningPIDConstants = new PIDFConstants(0.5);

            //! Global offset
            public static final Measure<Angle> kGlobalOffset = Degrees.of(0);

            public static final SwerveModuleOptions kFrontLeftOptions = new SwerveModuleOptions()
                .setOffsetDeg(110.03906249999999)
                .setAbsoluteEncoderInverted(true)
                .setAbsoluteEncoderID(11)
                .setDriveMotorID(22)
                .setTurningMotorID(21)
                .setDriveMotorInverted(true)
                .setTurningMotorInverted(true)
                .setName("Front Left");

            public static final SwerveModuleOptions kFrontRightOptions = new SwerveModuleOptions()
                .setOffsetDeg(131.92382812500009)
                .setAbsoluteEncoderInverted(true)
                .setAbsoluteEncoderID(12)
                .setDriveMotorID(24)
                .setTurningMotorID(23)
                .setDriveMotorInverted(false)
                .setTurningMotorInverted(true)
                .setName("Front Right");

            public static final SwerveModuleOptions kBackLeftOptions = new SwerveModuleOptions()
                .setOffsetDeg(234.58007812500009)
                .setAbsoluteEncoderInverted(true)
                .setAbsoluteEncoderID(13)
                .setDriveMotorID(26)
                .setTurningMotorID(25)
                .setDriveMotorInverted(true)
                .setTurningMotorInverted(true)
                .setName("Back Left");

            public static final SwerveModuleOptions kBackRightOptions = new SwerveModuleOptions()
                .setOffsetDeg(12.041015625000094)
                .setAbsoluteEncoderInverted(true)
                .setAbsoluteEncoderID(14)
                .setDriveMotorID(28)
                .setTurningMotorID(27)
                .setDriveMotorInverted(false)
                .setTurningMotorInverted(true)
                .setName("Back Right");
        }

        //! AUTONOMOUS 
        public static final class Autonomous {
            public static final PIDConstants kTranslatePIDConstants = new PIDConstants(0.1, 0.0, 0.0);
            public static final PIDConstants kRotatePIDConstants = new PIDConstants(0.1, 0.0, 0.0);
            public static final Measure<Velocity<Distance>> kMaxSpeedMetersPerSecond = MetersPerSecond.of(1);
        }
    }

    //! VISION
    public static final class Vision {
        AprilTagFieldLayout kAprilTagFieldLayout = AprilTagFields.k2024Crescendo.loadAprilTagLayoutField();
    }

    //! INTAKE
    public static final class Intake {
        // Speeds
        public static final double kIntakeOutSpeed = -0.2;
        public static final double kIntakeInSpeed = 0.25;
        public static final double kIntakeHoldSpeed = 0.02;
        public static final double kGiveToShooterSpeed = -0.2;
        
        // Intake motor config
        public static final int kintakeMotorID = 36;
        public static final PIDFConstants kIntakePIDConstants = new PIDFConstants(0.1, 0.0, 0.0);
        public static final double kHasPieceCurrentThreshold = 20;
        public static final double kHasPieceTimeThreshold = 0.2;

        // Intake motor rpm conversion
        public static final double kIntake_RpmToMechanismRpm = 1 / 4;

        // Lifter motor config
        public static final int kLifterMotorID = 37;
        public static final PIDFConstants kLifterPIDConstants = new PIDFConstants(0.1, 0.0, 0.0);

        // Lifter encoder config
        public static final double kLifter_RotationToDegrees = 1 / 16 * 360;

        // Intake times
        public static final double kMaxOuttakeTime = 3;
        public static final double kMaxIntakeTime = 6;

        public static final class Physical {
            public static final Measure<Angle> kLifterMaxHeight = Degrees.of(180);
            public static final Measure<Angle> kLifterMinHeight = Degrees.of(0);

            public static final Measure<Angle> kShooterAngle = Degrees.of(180);
            public static final Measure<Angle> kGroundAngle = Degrees.of(0);
        }
    }

    //! SHOOTER
    public static final class Shooter {
        // Shooter motor config
        public static final int kLeftShooterMotorID = 30;
        public static final int kRightShooterMotorID = 31;
        public static final PIDFConstants kShooterPIDConstants = new PIDFConstants(0.1, 0.0, 0.0);

        // Shooter speed
        public static final double kShooterSpeed = -1;

        // Shooter motor rpm conversion
        public static final double kShooter_RpmToMechanismRpm = 3 / 1;

        // Shooter speeds
        public static final Measure<Velocity<Angle>> kShooterSpeakerSpeed = RPM.of(70);
        public static final Measure<Velocity<Angle>> kTakeFromHumanPlayer = RPM.of(-5);

        // Shooter motor time
        public static final double kMaxShootTime = 4;
    }

    //! CLIMBER
    public static final class Climber {
        // Climber motor config
        public static final int kLeftClimberMotorID = 32;
        public static final int kRightClimberMotorID = 33;
        public static final PIDFConstants kClimberPIDConstants = new PIDFConstants(0.1, 0.0, 0.0);

        // Climber speed
        public static final double kClimberUpSpeed = 0.15;
        public static final double kClimberDownSpeed = -kClimberUpSpeed;

        // Climber motor rpm conversion
        // public static final double kClimber_RpmToMechanismRpm = 1 / 1;
    }
}
