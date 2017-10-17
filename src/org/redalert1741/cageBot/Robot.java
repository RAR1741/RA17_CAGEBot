package org.redalert1741.cageBot;

import edu.wpi.first.wpilibj.GenericHID.Hand;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.redalert1741.robotBase.config.Config;
import org.redalert1741.robotBase.logging.DataLogger;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.XboxController;

public class Robot extends IterativeRobot
{
	public XboxController driver, operator;
	DataLogger logger;
	
	@Override
	public void robotInit()
	{
		driver = new XboxController(0);
		operator = new XboxController(1);
		logger = new DataLogger();
		Config.loadFromFile("/home/lvuser/config.txt");
	}

	@Override
	public void autonomousInit()
	{
		startLogging("auto");
	}

	@Override
	public void autonomousPeriodic()
	{

	}
	
	@Override
	public void teleopInit()
	{
		startLogging("teleop");
	}

	@Override
	public void teleopPeriodic()
	{
		double x = driver.getX(Hand.kRight);
		double y = driver.getY(Hand.kLeft);
		// do drive
		
		logger.log();
	}
	
	@Override
	public void testInit()
	{
		Config.reloadConfig();
		startLogging("test");
	}

	@Override
	public void testPeriodic()
	{

	}
	
	public double deadband(double x)
	{
		if (x >= 0.05) 
			return 1/0.95 * x - 0.05;
		else if (0.05 > Math.abs(x))
			return 0;
		else if (x <= -0.05)
			return -1/0.95 * x + 0.05;
		else
			return 0;
	}
	
	public void setupLogging()
	{
		logger.setupLoggables();
	}
	
	public void startLogging(String mode)
	{
		String dir = "/home/lvuers/logs";
		new File(dir).mkdirs();
		TimeZone tz = TimeZone.getTimeZone("EST");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		df.setTimeZone(tz);
		logger.open(dir + "/log-" + df.format(new Date()) + "_" + mode + ".log");
	}
 }

