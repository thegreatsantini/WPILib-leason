package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;

/** Run the arm at a speed for a number of seconds, then stop. */
public class RunArmFor extends Command {
  private final ArmSubsystem arm;
  private final double speed;
  private final double seconds;
  private final Timer timer = new Timer();

  public RunArmFor(ArmSubsystem arm, double speed, double seconds) {
    this.arm = arm;
    this.speed = speed;
    this.seconds = seconds;
    // TODO 1: Tell the scheduler this command needs the arm.
    //         (Hint: addRequirements(...))
  }

  /** Runs ONCE when the command starts. */
  @Override
  public void initialize() {
    // TODO 2: Start the timer from zero. (Hint: timer.restart())
  }

  /** Runs every 20 ms while the command is active. */
  @Override
  public void execute() {
    // TODO 3: Make the arm move at `speed`.
  }

  /** Runs ONCE when the command ends, whether it finished or got interrupted. */
  @Override
  public void end(boolean interrupted) {
    // TODO 4: Stop the arm.
    // Bonus: System.out.println("RunArmFor ended. Interrupted? " + interrupted);
  }

  /** Checked every 20 ms. Return true when the job is done. */
  @Override
  public boolean isFinished() {
    // TODO 5: Return true once `seconds` have passed. (Hint: timer.hasElapsed(...))
    return false;
  }
}
