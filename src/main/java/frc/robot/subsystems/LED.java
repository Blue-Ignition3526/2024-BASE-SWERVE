// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.Consumer;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import lib.team3526.utils.LEDOptions;

public class LED extends SubsystemBase {
  public AddressableLED leds;
  public AddressableLEDBuffer data;
  private Consumer<AddressableLEDBuffer> animation = null;

  public LED(LEDOptions options) {
    this.leds = new AddressableLED(options.getChannel());
    this.data = new AddressableLEDBuffer(options.getLength());
    this.leds.setLength(data.getLength());
    this.leds.setData(data);
    this.leds.start();
  }

  public void setDefaultAnimation(Consumer<AddressableLEDBuffer> animation) {
    this.animation = animation;
  }

  public void stopDefaultAnimation() {
    this.animation = null;
  }

  @Override
  public void periodic() {
    if (this.animation != null) this.animation.accept(data);
    this.leds.setData(data);
  }
}
