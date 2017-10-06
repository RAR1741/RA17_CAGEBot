package org.redalert1741.cageBot;

import com.ctre.CANTalon;

public class TankDrive
{
	private CANTalon l1, l2, l3, r1, r2, r3;
	
	public TankDrive(int l1, int l2, int l3, int r1, int r2, int r3)
	{
		this.l1 = new CANTalon(l1);
		this.l2 = new CANTalon(l2);
		this.l3 = new CANTalon(l3);
		this.r1 = new CANTalon(r1);
		this.r2 = new CANTalon(r2);
		this.r3 = new CANTalon(r3);
		this.l1.reverseOutput(true);
		this.l2.reverseOutput(true);
		this.l3.reverseOutput(true);
	}
	
	private void driveLeft(double val)
	{
		l1.set(val);
		l2.set(val);
		l3.set(val);
	}
	
	private void driveRight(double val)
	{
		r1.set(val);
		r2.set(val);
		r3.set(val);
	}
	
	public void arcadeDrive(double x, double y)
	{
		driveLeft(y+x);
		driveRight(y-x);
	}
}
