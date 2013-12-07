/**
 * 
 */
package net.castegaming.plugins.visionist.managers;

import net.castegaming.plugins.visionist.Consts;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * @author Brord
 *
 */
public class Stream {
	
	private Material m;
	private Location l;
	private int amount = 1;
	private byte b = 0;

	/**
	 * 
	 */
	public Stream(Material m, Location l) {
		this(m, l ,1, (byte) 0);
	}
	
	/**
	 * 
	 */
	public Stream(Material m, Location l, byte b) {
		this(m, l ,1, b);
	}
	
	/**
	 * 
	 */
	public Stream(Material m, Location l, int amount) {
		this(m,l, amount, (byte)0);
	}
	
	public Stream(Material m, Location l, int amount, byte b) {
		this.m = m;
		this.l = l;
		this.amount = amount;
		this.b = b;
	}
	
	/**
	 * 
	 */
	public void playStream() {
		for (Player p : l.getWorld().getEntitiesByClass(Player.class)){
			if (p.getLocation().distanceSquared(l) < Consts.MAX_DISTANCE){
				play();
				break;
			}
		}
	}
	
	private void play(){
		if (amount == 1) spawnOne();
	}
	
	private void spawnOne(){
		l.getWorld().spawnFallingBlock(l, m.getId(), b);
	}
}
