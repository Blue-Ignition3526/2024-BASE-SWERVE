package lib.team3526.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

public class RunForCommand extends Command {
  Command command;
  Timer timer = new Timer();
  double duration;

  public RunForCommand(Command command, double duration) {
    this.command = command;
    this.duration = duration;
  }

  @Override
  public void initialize() {
    command.initialize();
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {
    while (timer.get() < duration) command.schedule();
  }

  @Override
  public void end(boolean interrupted) {
    command.end(interrupted);
  }

  @Override
  public boolean isFinished() {
    return timer.get() >= duration;
  }
}
