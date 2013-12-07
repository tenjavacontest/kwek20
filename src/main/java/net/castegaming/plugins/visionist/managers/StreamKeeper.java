/**
 * 
 */
package net.castegaming.plugins.visionist.managers;

import java.util.LinkedList;

import net.castegaming.plugins.visionist.Visionist;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Brord
 *
 */
public class StreamKeeper implements Listener{

	public LinkedList<Stream> streams;
	
	private int runnable = -1;
	
	/**
	 * 
	 */
	public StreamKeeper() {
		streams = new LinkedList<Stream>();
	}
	
	public void start(){
		runnable = new BukkitRunnable(){

			@Override
			public void run() {
				
				for (Stream s : streams){
					s.playStream();
				}
			}
			
		}.runTaskTimer(Visionist.getInstance(), 0, 5).getTaskId();
	}
	
	public void stop(){
		Bukkit.getScheduler().cancelTask(runnable);
	}


}
