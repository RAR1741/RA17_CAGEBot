package org.redalert1741.cageBot;

import edu.wpi.first.wpilibj.GenericHID.Hand;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.redalert1741.cageBot.auto.move.DriveArcadeMove;
import org.redalert1741.robotBase.auto.core.*;
import org.redalert1741.robotBase.auto.end.*;
import org.redalert1741.robotBase.config.Config;
import org.redalert1741.robotBase.logging.DataLogger;
import org.redalert1741.robotBase.logging.Loggable;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.XboxController;

public class Robot extends IterativeRobot implements Loggable
{
	XboxController driver, operator;
	DataLogger logger;
	TankDrive drive;
	Climber climber;
	GearPlacer placer;
	Autonomous auto;
	PowerDistributionPanel pdp;
	
	static enum DriveMode { fast, normal, slow }

	@Override
	public void robotInit()
	{
		driver = new XboxController(0);
		operator = new XboxController(1);
		logger = new DataLogger();
		drive = new TankDrive(1,2,3,4,5,6);
		climber = new Climber(1,2);
		placer = new GearPlacer(0,1,2,3,0);
		pdp = new PowerDistributionPanel(60);
		
		//config
		Config.addConfigurable(drive);
		Config.addConfigurable(placer);
		Config.loadFromFile("/home/lvuser/config.txt");
		Config.reloadConfig();
		
		//auto
		AutoFactory.addMoveEnd("timed", () -> new TimedEnd());
		AutoFactory.addMoveMove("arcadeDrive", () -> new DriveArcadeMove(drive));
	}

	@Override
	public void autonomousInit()
	{
		startLogging("auto");
		auto = new JsonAutoFactory().makeAuto("/home/lvuser/autos/" + Config.getSetting("auto", "none_auto") + ".json");
		auto.start();
	}

	@Override
	public void autonomousPeriodic()
	{
		auto.run();
	}
	
	DriveMode mode;
	
	@Override
	public void teleopInit()
	{
		startLogging("teleop");
		mode = DriveMode.normal;
	}
	
	@Override
	public void teleopPeriodic()
	{
		double x = driver.getX(Hand.kRight);
		double y = driver.getY(Hand.kLeft);
		switch (driver.getPOV())
		{
		case 0:
			mode = DriveMode.fast;
			break;
		case 180:
			mode = DriveMode.normal;
			break;
		case 270:
			mode = DriveMode.slow;
		}
		double boost = (mode == DriveMode.normal ? 0.6 : (mode == DriveMode.fast ? 1 : 0.4));
		drive.arcadeDrive(deadband(x) * boost, -deadband(y) * boost);
		
		climber.climb(driver.getTriggerAxis(Hand.kRight));
		
		if (driver.getBumper(Hand.kRight))
			placer.push();
		else
			placer.hold();
		
		if(driver.getBumper(Hand.kLeft))
			placer.liftUp();
		else
			placer.liftDown();
		
		placer.setIntake((driver.getBButton() ? 1 : -1) * driver.getTriggerAxis(Hand.kLeft));
		
		logger.log();
		logger.writeLine();
	}
	
	@Override
	public void testInit()
	{
		Config.loadFromFile("/home/lvuser/config.txt");
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
			return 1/0.95 * x + 0.05;
		else
			return 0;
	}
	
	public void setupLogging()
	{
		logger.addLoggable(drive);
		logger.addLoggable(climber);
		logger.addLoggable(placer);
		logger.addLoggable(this);
		logger.setupLoggables();
		logger.writeAttributes();
	}
	
	public void startLogging(String mode)
	{
		String dir = "/home/lvuser/logs";
		new File(dir).mkdirs();
		TimeZone tz = TimeZone.getTimeZone("EST");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		df.setTimeZone(tz);
		logger.open(dir + "/log-" + df.format(new Date()) + "_" + mode + ".csv");
		setupLogging();
	}
	
	@Override
	public void disabledInit()
	{
		logger.close();
	}

	@Override
	public void setupLogging(DataLogger logger)
	{
		logger.addAttribute("PDPvoltage");
		logger.addAttribute("PDPcurrent");
	}

	@Override
	public void log(DataLogger logger)
	{
		logger.log("PDPvoltage", pdp.getVoltage());
		logger.log("PDPcurrent", pdp.getTotalCurrent());
	}
 }

