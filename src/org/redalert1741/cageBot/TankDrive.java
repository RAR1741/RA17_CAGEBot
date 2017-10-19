package org.redalert1741.cageBot;

import org.redalert1741.robotBase.config.Config;
import org.redalert1741.robotBase.config.Configurable;
import org.redalert1741.robotBase.logging.DataLogger;
import org.redalert1741.robotBase.logging.Loggable;

import com.ctre.CANTalon;

public class TankDrive implements Configurable, Loggable
{
	private CANTalon l1, l2, l3, r1, r2, r3;
	boolean leftInvert;
	public TankDrive(int l1, int l2, int l3, int r1, int r2, int r3)
	{
		this.l1 = new CANTalon(l1);
		this.l2 = new CANTalon(l2);
		this.l3 = new CANTalon(l3);
		this.r1 = new CANTalon(r1);
		this.r2 = new CANTalon(r2);
		this.r3 = new CANTalon(r3);
		this.l1.reverseOutput(leftInvert);
		this.l2.reverseOutput(leftInvert);
		this.l3.reverseOutput(leftInvert);
		this.r1.reverseOutput(!leftInvert);
		this.r2.reverseOutput(!leftInvert);
		this.r3.reverseOutput(!leftInvert);
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

	@Override
	public void setupLogging(DataLogger logger)
	{
		logger.addAttribute("leftSpeed");
		logger.addAttribute("rightSpeed");
		logger.addAttribute("l1current");
		logger.addAttribute("l2current");
		logger.addAttribute("l3current");
		logger.addAttribute("r1current");
		logger.addAttribute("r2current");
		logger.addAttribute("r3current");
	}

	@Override
	public void log(DataLogger logger)
	{
		logger.log("leftSpeed", l2.get());
		logger.log("rightSpeed", r2.get());
		logger.log("l1current", l1.getOutputCurrent());
		logger.log("l2current", l2.getOutputCurrent());
		logger.log("l3current", l3.getOutputCurrent());
		logger.log("r1current", r1.getOutputCurrent());
		logger.log("r2current", r2.getOutputCurrent());
		logger.log("r3current", r3.getOutputCurrent());
	}

	@Override
	public void reloadConfig()
	{
		leftInvert = Config.getSetting("leftInvert", true);
	}
}
