package frc.robot.subsystems;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;

/**
 * The arm OWNS its motor. Nobody else touches it.
 * Commands ask the arm to do things through its public methods.
 */
public class ArmSubsystem extends SubsystemBase {
  // --- Hardware (private: only the arm can touch this) ---
  private final PWMSparkMax motor = new PWMSparkMax(ArmConstants.kMotorPwmPort);

  // --- Simulation: a fake physics arm so we don't need the robot ---
  private final SingleJointedArmSim armSim =
      new SingleJointedArmSim(
          DCMotor.getNEO(1),
          ArmConstants.kGearing,
          SingleJointedArmSim.estimateMOI(ArmConstants.kArmLengthMeters, ArmConstants.kArmMassKg),
          ArmConstants.kArmLengthMeters,
          Units.degreesToRadians(ArmConstants.kMinAngleDeg),
          Units.degreesToRadians(ArmConstants.kMaxAngleDeg),
          true, // gravity is real, even in sim
          Units.degreesToRadians(ArmConstants.kMinAngleDeg));

  // --- Visualization: shows up in the Sim GUI / Glass under "Arm" ---
  private final Mechanism2d mech = new Mechanism2d(3, 3);
  private final MechanismRoot2d pivot = mech.getRoot("Pivot", 1.5, 1.5);
  private final MechanismLigament2d armLine =
      pivot.append(
          new MechanismLigament2d("Arm", 1.0, ArmConstants.kMinAngleDeg, 6, new Color8Bit(255, 140, 0)));

  public ArmSubsystem() {
    SmartDashboard.putData("Arm", mech);
  }

  // --- The public "buttons" on this class ---
  public void setSpeed(double speed) {
    motor.set(speed);
  }

  public void stop() {
    motor.set(0);
  }

  /** In sim this comes from physics. On the real robot it would come from an encoder (L7). */
  public double getAngleDegrees() {
    return Units.radiansToDegrees(armSim.getAngleRads());
  }

  @Override
  public void periodic() {
    // Runs every 20 ms, real or sim
    armLine.setAngle(getAngleDegrees());
    SmartDashboard.putNumber("Arm/AngleDeg", getAngleDegrees());
    SmartDashboard.putNumber("Arm/MotorOutput", motor.get());
  }

  @Override
  public void simulationPeriodic() {
    // Runs every 20 ms in sim only
    armSim.setInput(motor.get() * RobotController.getBatteryVoltage());
    armSim.update(0.020);
  }
}
