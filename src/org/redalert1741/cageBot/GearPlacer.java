package org.redalert1741.cageBot;

import org.redalert1741.robotBase.config.Config;
import org.redalert1741.robotBase.config.Configurable;
import org.redalert1741.robotBase.logging.DataLogger;
import org.redalert1741.robotBase.logging.Loggable;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;

public class GearPlacer implements Configurable, Loggable
{
	boolean liftOn, pushOn;
	Solenoid liftL, liftR, pushL, pushR;
	private Spark in;
	/**
	 * Four solenoids for the pneumatic gear placer + one motor for the intake
	 * @param liftL {@link Solenoid} for lift
	 * @param liftR {@link Solenoid} for lift
	 * @param pushL {@link Solenoid} for push
	 * @param pushR {@link Solenoid} for push
	 * @param intake PWM ID for intake {@link Spark}
	 */
	public GearPlacer(int liftL, int liftR, int pushL, int pushR, int intake)
	{
		this.in = new Spark(intake);
		this.liftL = new Solenoid(liftL);
		this.liftR = new Solenoid(liftR);
		this.pushL = new Solenoid(pushL);
		this.pushR = new Solenoid(pushR);
	}
	
	public void liftUp()
	{
		liftL.set(liftOn);
		liftR.set(liftOn);
	}
	
	public void liftDown()
	{
		liftL.set(!liftOn);
		liftR.set(!liftOn);
	}
	
	public void push()
	{
		pushL.set(pushOn);
		pushR.set(pushOn);
	}
	
	public void hold()
	{
		pushL.set(!pushOn);
		pushR.set(!pushOn);
	}
	
	public void setIntake(double x)
	{
		in.set(x);
	}

	@Override
	public void reloadConfig()
	{
		liftOn = Config.getSetting("liftOn", false);
		pushOn = Config.getSetting("pushOn", true);
	}

	@Override
	public void setupLogging(DataLogger logger)
	{
		logger.addAttribute("liftState");
		logger.addAttribute("pushState");
		logger.addAttribute("intakeSpeed");
	}

	@Override
	public void log(DataLogger logger)
	{
		logger.log("liftState", liftL.get());
		logger.log("pushState", pushR.get());
		logger.log("intakeSpeed", in.get());
	}
}
