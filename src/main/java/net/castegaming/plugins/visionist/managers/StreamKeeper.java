/**
 * 
 */
package net.castegaming.plugins.visionist.managers;

import java.util.LinkedList;
import java.util.List;

import net.castegaming.plugins.visionist.Visionist;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Brord
 *
 */
public class StreamKeeper implements Listener{

	private LinkedList<Stream> streams;
	
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

	/**
	 * @param stream
	 */
	public void addStream(Stream stream) {
		YamlConfiguration streams = Visionist.getFile("streams");
		//streams cannot be null, since we check before we are loaded
		
		int size = streams.getKeys(false).size()+1;
		streams.set(size + "material", stream.getMaterial());
		streams.set(size + "byte", stream.getByte());
		streams.set(size + "amount", stream.getAmount());
		streams.set(size + "world", stream.getLocation().getWorld().getName());
		streams.set(size + "location", new int[]{stream.getLocation().getBlockX(), stream.getLocation().getBlockY(), stream.getLocation().getBlockZ()});
		
		Visionist.saveFile(streams, "streams");
		
		this.streams.add(stream);
	}
	
	/**
	 * Returns all the current steams
	 * @return a {@link List} of the current {@link Stream}s
	 */
	public List<Stream> getStreams(){
		return streams;
	}
}
