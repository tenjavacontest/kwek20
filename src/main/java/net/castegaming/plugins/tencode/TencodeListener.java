package net.castegaming.plugins.tencode;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class TencodeListener implements Listener{

	private Tencode plugin;
	
	public TencodeListener(Tencode plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void login(PlayerLoginEvent e){
		
	}

}
