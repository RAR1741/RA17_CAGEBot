package org.redalert1741.cageBot;

import edu.wpi.first.wpilibj.Solenoid;

public class GearPlacer 
{
	Solenoid liftL, liftR, pushL, pushR;
	
	public GearPlacer(int liftL, int liftR, int pushL, int pushR)
	{
		this.liftL = new Solenoid(liftL);
		this.liftR = new Solenoid(liftR);
		this.pushL = new Solenoid(pushL);
		this.pushR = new Solenoid(pushR);
	}
}
