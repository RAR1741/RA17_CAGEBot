package org.redalert1741.cageBot.auto.move;

import java.util.Map;

import org.redalert1741.cageBot.TankDrive;
import org.redalert1741.robotBase.auto.core.AutoMoveMove;

public class DriveArcadeMove implements AutoMoveMove
{
	double x, y;
	TankDrive drive;
	
	public DriveArcadeMove(TankDrive drive)
	{
		this.drive = drive;
	}

	@Override
	public void setArgs(Map<String, String> args)
	{
		x = Double.parseDouble(args.get("x"));
		y = Double.parseDouble(args.get("y"));
	}

	@Override
	public void start() {}

	@Override
	public void run()
	{
		//System.out.println("arcade drive (" + x + "," + y + ")");
		drive.arcadeDrive(x, y);
	}

	@Override
	public void stop()
	{
		//System.out.println("drive stop");
		drive.arcadeDrive(0, 0);
	}
}
