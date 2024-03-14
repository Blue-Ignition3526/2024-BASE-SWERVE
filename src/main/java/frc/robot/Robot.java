package frc.robot;

import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.urcl.URCL;
import com.ctre.phoenix6.SignalLogger;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends LoggedRobot {
  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;

  @Override
  public void robotInit() {
    // * RobotContainer
    m_robotContainer = new RobotContainer();

    // * AdvantageKit Config
    Logger.addDataReceiver(new NT4Publisher());
    if (Constants.Logging.kDebug) Logger.registerURCL(URCL.startExternal());
    Logger.start();

    // * DISABLE LIVE WINDOW
    LiveWindow.disableAllTelemetry();

    // * DISABLE PHOENIX LOGGING
    SignalLogger.stop();
    SignalLogger.enableAutoLogging(false);

    // Limelight port forwarding over USB
    for (int port = 5800; port <= 5807; port++) PortForwarder.add(port, "limelight.local", port);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {
    // TODO: Add climber hold
  }

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();
    if (m_autonomousCommand != null) m_autonomousCommand.schedule();
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) m_autonomousCommand.cancel();
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
