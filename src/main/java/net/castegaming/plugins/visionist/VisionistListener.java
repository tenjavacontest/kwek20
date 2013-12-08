package net.castegaming.plugins.visionist;

import java.util.List;

import net.castegaming.plugins.visionist.types.Stream;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class VisionistListener implements Listener{

	private Visionist plugin;
	
	public VisionistListener(Visionist plugin) {
		this.plugin = plugin;
	}
	
//	@EventHandler
//	public void blockfall(final EntityChangeBlockEvent e){
//		if (e.getEntity().hasMetadata("Visionist")){
//			if (e.getTo().equals(Material.BEDROCK)){
//				new BukkitRunnable(){
//
//					@Override
//					public void run() {
//						e.getBlock().setType(Material.AIR);
//					}
//					
//				}.runTaskLater(plugin, 20*10);
//			}
//		}
//	}
	
//	@EventHandler
//	public void onItemspawn(ItemSpawnEvent ev){
//		List<Entity> ents = ev.getEntity().getNearbyEntities(2, 2, 2);
//		for (Entity e : ents){
//			if (e.hasMetadata("Visionist") && e instanceof FallingBlock){
//				ev.getLocation().getWorld().getBlockAt(ev.getLocation().add(new Vector(0, 1, 0))).setType(((FallingBlock) e).getMaterial());
//				ev.setCancelled(true);
//			}
//		}
//	}

}
