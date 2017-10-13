package org.redalert1741.cageBot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.XboxController;

public class Robot extends IterativeRobot
{
	public XboxController driver, operator;
	
	@Override
	public void robotInit()
	{
		driver = new XboxController(0);
		operator = new XboxController(1);
	}

	@Override
	public void autonomousInit()
	{

	}

	@Override
	public void autonomousPeriodic()
	{

	}

	@Override
	public void teleopPeriodic()
	{
		double x = driver.getX(Hand.kRight);
		double y = driver.getY(Hand.kLeft);
		// do drive
		
		
	}

	@Override
	public void testPeriodic()
	{

	}
	
	public double deadband(double x)
	{
		if (x >= 0.05) 
		{
			return 1/0.95 * x - 0.05;
		}
		else if (0.05 > Math.abs(x))
		{
			return 0;
		}
		else if (x <= -0.05)
		{
			return -1/0.95 * x + 0.05;
		}
		else
		{
			return 0;
		}
	}
 }

