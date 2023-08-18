package swervelib.encoders;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;

import edu.wpi.first.wpilibj.DriverStation;
import swervelib.motors.SwerveMotor;

/**
 * SparkMax absolute encoder, attached through the data port.
 */
public class SparkMaxEncoderSwerve extends SwerveAbsoluteEncoder {

  /**
   * The {@link AbsoluteEncoder} representing the duty cycle encoder attached to
   * the SparkMax.
   */
  public RelativeEncoder encoder;

  /**
   * Create the {@link AbsoluteEncoder} object as a duty cycle. from the
   * {@link CANSparkMax} motor.
   *
   * @param motor Motor to create the encoder from.
   */
  public SparkMaxEncoderSwerve(SwerveMotor motor) {
    if (motor.getMotor() instanceof CANSparkMax) {
      encoder = ((CANSparkMax) motor.getMotor()).getAlternateEncoder(4096);
    } else {
      throw new RuntimeException("Motor given to instantiate SparkMaxEncoder is not a CANSparkMax");
    }
  }

  /**
   * Reset the encoder to factory defaults.
   */
  @Override
  public void factoryDefault() {
    // Do nothing
  }

  /**
   * Clear sticky faults on the encoder.
   */
  @Override
  public void clearStickyFaults() {
    // Do nothing
  }

  /**
   * Configure the absolute encoder to read from [0, 360) per second.
   *
   * @param inverted Whether the encoder is inverted.
   */
  @Override
  public void configure(boolean inverted) {
    encoder.setInverted(inverted);
  }

  /**
   * Get the absolute position of the encoder.
   *
   * @return Absolute position in degrees from [0, 360).
   */
  @Override
  public double getAbsolutePosition() {
    var i = encoder.getPosition(); // returns a number from (-inf, inf)
    // if i is negative, that means it isn't in our pre-conversion range of [0, 1)
    // so we add whatever the rounded negative value is to i to get it in the range
    // if (i < 0) {
    // i += Math.ceil(i);
    // } else if (i >= 1) {
    // i -= Math.floor(i);
    // }
    i = i % 1;
    if (i < 0) {
      i += 1;
    }
    return i * 360;
  }

  /**
   * Get the instantiated absolute encoder Object.
   *
   * @return Absolute encoder object.
   */
  @Override
  public Object getAbsoluteEncoder() {
    return encoder;
  }
}
