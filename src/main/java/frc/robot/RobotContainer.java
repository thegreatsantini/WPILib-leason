package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.RunArmFor;
import frc.robot.subsystems.ArmSubsystem;

public class RobotContainer {
  private final ArmSubsystem arm = new ArmSubsystem();
  private final CommandXboxController driver = new CommandXboxController(OIConstants.kDriverPort);

  public RobotContainer() {
    // When nobody else is using the arm, this runs. Watch what gravity does.
    arm.setDefaultCommand(arm.run(arm::stop));
    configureBindings();
  }

  private void configureBindings() {
    // A = up for 1 second, B = down for 1 second
    driver.a().onTrue(new RunArmFor(arm, ArmConstants.kSpeed, 1.0));
    driver.b().onTrue(new RunArmFor(arm, -ArmConstants.kSpeed, 1.0));

    // Y = up only while held (inline command, no class needed)
    driver.y().whileTrue(arm.run(() -> arm.setSpeed(ArmConstants.kSpeed)));

    // STRETCH: uncomment once MoveArmTo exists
    // driver.x().onTrue(new MoveArmTo(arm, 45.0));
  }

  public Command getAutonomousCommand() {
    return Commands.none();
  }
}
