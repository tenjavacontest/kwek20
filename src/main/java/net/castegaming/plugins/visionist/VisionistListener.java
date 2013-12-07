package net.castegaming.plugins.visionist;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class VisionistListener implements Listener{

	private Visionist plugin;
	
	public VisionistListener(Visionist plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void login(PlayerLoginEvent e){
		
	}

}
