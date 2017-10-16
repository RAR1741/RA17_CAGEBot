package org.redalert1741.cageBot;

import org.redalert1741.robotBase.config.Config;
import org.redalert1741.robotBase.config.Configurable;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;

public class GearPlacer implements Configurable
{
	boolean liftOn, pushOn;
	Solenoid liftL, liftR, pushL, pushR;
	private Spark in;
	
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
}
