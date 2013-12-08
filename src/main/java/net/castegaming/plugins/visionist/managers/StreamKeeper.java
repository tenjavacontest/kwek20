/**
 * 
 */
package net.castegaming.plugins.visionist.managers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.castegaming.plugins.visionist.Visionist;
import net.castegaming.plugins.visionist.types.Stream;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;


/**
 * @author Brord
 *
 */
public class StreamKeeper implements Listener{

	/**
	 * List of streams currently existing for this keeper
	 */
	private LinkedList<Stream> streams;
	
	/**
	 * The scheduler id for the {@link BukkitScheduler}
	 */
	private int runnable = -1;
	
	/**
	 * Create a new StreamKeeper. There should never be more then one tho ;)
	 */
	public StreamKeeper() {
		streams = new LinkedList<Stream>();
	}
	
	@EventHandler
	public void blockfall(EntityChangeBlockEvent e){
		for (Stream s : streams){
			if (s.removeUUID(e.getEntity().getUniqueId())){
				e.setCancelled(true);
				break;
			}
		}
	}
	
	@EventHandler
	public void onItemspawn(ItemSpawnEvent ev){
		List<Entity> ents = ev.getEntity().getNearbyEntities(2, 2, 2);
		for (Entity e : ents){
			if (e.hasMetadata("Visionist")){
				ev.setCancelled(true);
			}
		}
	}
	
	/**
	 * Starts the {@link StreamKeeper}, which plays all the {@link Stream}s
	 */
	public void start(){
		runnable = new BukkitRunnable(){

			@Override
			public void run() {
				List<Stream> stream = streams;
				for (Stream s : stream){
					if (s.needsRemove()){
						final Stream removeme = s;
						new BukkitRunnable(){
							@Override
							public void run() {
								streams.remove(removeme);
							}
						}.runTaskLater(Visionist.getInstance(), 20*60);
						
					} else {
						s.playStream();
					}
				}
			}
			
		}.runTaskTimer(Visionist.getInstance(), 1, 1).getTaskId();
	}
	
	/**
	 * Stop the {@link StreamKeeper} from playing
	 */
	public void stop(){
		Bukkit.getScheduler().cancelTask(runnable);
	}
	
	/**
	 * Add a {@link Stream} to the {@link List}\
	 * @param stream the {@link Stream} to add
	 */
	public void addStream(Stream stream) {
		streams.add(stream);
	}

	/**
	 * Add a new {@link Stream}, this will also add it to the {@link YamlConfiguration}
	 * @param stream the {@link Stream} to add
	 */
	public void addNewStream(Stream stream) {
		YamlConfiguration streams = Visionist.getFile("streams");
		//streams cannot be null, since we check before we are loaded
		
		int size = streams.getKeys(false).size()+1;
		streams.set(size + ".material", stream.getMaterial().name());
		streams.set(size + ".byte", stream.getByte());
		streams.set(size + ".amount", stream.getAmount());
		streams.set(size + ".world", stream.getLocation().getWorld().getName());
		streams.set(size + ".location", new LinkedList<Integer>(
				Arrays.asList(
						new Integer[]{stream.getLocation().getBlockX(), stream.getLocation().getBlockY(), stream.getLocation().getBlockZ()}
				)));
		streams.set(size + ".vector", new LinkedList<Integer>(
				Arrays.asList(
						new Integer[]{stream.getVector().getBlockX(), stream.getVector().getBlockY(), stream.getVector().getBlockZ()}
				)));
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

	/**
	 * Cleans all the streams from the list
	 */
	public void clean() {
		streams = new LinkedList<Stream>();
	}
}
