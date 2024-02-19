package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.SwerveDrive.SwerveDrive;
import frc.robot.commands.Intake.IntakeIn;
import frc.robot.commands.Intake.IntakeOut;
import frc.robot.commands.Intake.LifterAmp;
import frc.robot.commands.Intake.LifterShooter;
import frc.robot.commands.SwerveDrive.XFormation;
import frc.robot.commands.SwerveDrive.ZeroHeading;
import frc.robot.subsystems.Gyro.Gyro;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Shooter.Shooter;
import lib.team3526.commands.LogCommand;
import lib.team3526.commands.RunForCommand;

public class SelfTest extends SequentialCommandGroup {
  public SelfTest(Gyro gyro, SwerveDrive swerveDrive, Intake intake, Shooter shooter) {
    addCommands(
      new LogCommand("Running self Test..."),

      new LogCommand("Resetting Gyroscope"),
      new ZeroHeading(swerveDrive),

      new LogCommand("Testing Wheel orientations..."),
      new LogCommand("X Formation, holding for 2 seconds..."),
      new XFormation(swerveDrive),

      new WaitCommand(2),

      new LogCommand("Dropping intake..."),
      new LifterAmp(intake),

      new LogCommand("Running intake..."),
      new RunForCommand(new IntakeIn(intake), 0.25),
      new WaitCommand(0.25),
      new RunForCommand(new IntakeOut(intake), 0.25),
      
      new WaitCommand(0.5),

      new LogCommand("Raising intake..."),
      new LifterShooter(intake),

      new LogCommand("Testing shooter..."),
      new ParallelCommandGroup(new Shoot(shooter, intake), new SequentialCommandGroup(new RunForCommand(new IntakeOut(intake), 0.25)))
    );
  }
}
