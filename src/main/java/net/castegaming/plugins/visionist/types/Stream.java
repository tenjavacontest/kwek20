/**
 * 
 */
package net.castegaming.plugins.visionist.types;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import net.castegaming.plugins.visionist.Consts;
import net.castegaming.plugins.visionist.Visionist;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import sun.awt.windows.ThemeReader;

import com.avaje.ebean.enhance.asm.commons.Method;

/**
 * @author Brord
 *
 */
public class Stream {
	
	private List<UUID> uuids;
	
	private Material m;
	private Location l;
	private int amount = 1;
	private byte b = 0;
	
	private boolean enabled = true;
	private boolean removal = false;

	private Vector vector;

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
		this(m, l, amount, b, new Vector(0, 0 ,0));
	}
	
	/**
	 * @param type
	 * @param l2
	 * @param amount2
	 * @param b2
	 * @param vector
	 */
	public Stream(Material m, Location l, int amount, byte b, Vector vector) {
		this.m = m;
		this.l = l;
		this.amount = amount;
		this.b = b;
		this.vector = vector;
		enabled = true;
		uuids = new LinkedList<UUID>();
	}

	/**
	 * Play this {@link Stream}, only if there are {@link Player}s nearby
	 */
	public void playStream() {
		if (!enabled) return;
		
		for (Player p : l.getWorld().getEntitiesByClass(Player.class)){
			if (p.getLocation().distanceSquared(l) < (Consts.MAX_DISTANCE*Consts.MAX_DISTANCE)){
				play();
				break;
			}
		}
	}
	
	/**
	 * disables this {@link Stream}
	 */
	public void disable(){
		enabled = false;
	}
	
	/**
	 * Enables this {@link Stream}
	 */
	public void enable(){
		enabled = true;
	}
	
	/**
	 * Toggles vetween the state of this stream, on/off
	 */
	public void toggleState(){
		enabled = !enabled;
	}
	
	/**
	 * internal {@link Method} used to spawn a {@link FallingBlock}
	 */
	private void play(){
		if (amount == 1){
			spawnOne().setVelocity(vector);
		} else {
			for (double i = -(2*Math.PI); i < 2*Math.PI; i+=Math.toRadians(360/amount)){
				spawnOne().setVelocity(new Vector(Math.cos(i), vector.getY(), Math.sin(i)));
			}
		}
	}
	
	private FallingBlock spawnOne(){
		FallingBlock b = l.getWorld().spawnFallingBlock(l, m.getId(), this.b);
		b.setMetadata("Visionist", new FixedMetadataValue(Visionist.getInstance(), "stream"));
		uuids.add(b.getUniqueId());
		return (b);
	}

	/**
	 * @return
	 */
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return m;
	}
	
	public Location getLocation(){
		return l;
	}
	
	public byte getByte(){
		return b;
	}
	
	public int getAmount(){
		return amount;
	}
	
	/**
	 * @param uniqueId
	 * @return
	 */
	public boolean removeUUID(UUID uniqueId){
		return uuids.remove(uniqueId);
	}

	/**
	 * 
	 */
	public void remove() {
		removal  = true;
	}
	
	public boolean needsRemove(){
		return removal;
	}

	/**
	 * @return the used vector
	 */
	public Vector getVector() {
		return vector;
	}
}
