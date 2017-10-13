package org.redalert1741.cageBot;

import edu.wpi.first.wpilibj.Solenoid;
import com.ctre.CANTalon;

public class GearPlacer 
{
	Solenoid liftL, liftR, pushL, pushR;
	private CANTalon in;
	
	public GearPlacer(int liftL, int liftR, int pushL, int pushR, int intake)
	{
		this.in = new CANTalon(intake);
		this.liftL = new Solenoid(liftL);
		this.liftR = new Solenoid(liftR);
		this.pushL = new Solenoid(pushL);
		this.pushR = new Solenoid(pushR);
	}
	
	public void liftUp()
	{
		liftL.set(false);
		liftR.set(false);
	}
	
	public void liftDown()
	{
		liftL.set(true);
		liftR.set(true);
	}
	
	public void push()
	{
		pushL.set(true);
		pushR.set(true);
	}
	
	public void hold()
	{
		pushL.set(true);
		pushR.set(true);
	}
	
	public void setIntake(double x)
	{
		in.set(x);
	}
}
