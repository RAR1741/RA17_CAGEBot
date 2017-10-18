package org.redalert1741.robotBase.auto.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the entirety of an Autonomous program, consisting of a set of {@link AutoMove moves} to be run one after the other. 
 * @see AutoFactory
 */
public class Autonomous
{
	private List<AutoMove> moves;
	private List<AutoMove> active;
	private int i;
	
	public Autonomous(List<AutoMove> m)
	{
		moves = m;
		i = 0;
		active = new ArrayList<>();
	}
	
	/**
	 * Runs the autonomous. Meant to be run iteratively.
	 */
	public void run()
	{
		if(i == moves.size())
		{
			return;
		}
		while(moves.get(i).isAsync())
		{
			active.add(moves.get(i));
			active.get(active.size()-1).start();
			i++;
		}
		for(int k = 0; k < active.size(); k++)
		{
			active.get(k).run();
			if(active.get(k).isFinshed())
			{
				if(k-1 == active.size())
				{
					active.add(moves.get(i));
					active.get(active.size()-1).start();
					i++;
				}
				active.remove(k);
				k--;
			}
		}
	}
}
