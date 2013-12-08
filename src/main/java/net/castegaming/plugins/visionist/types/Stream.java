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
import org.bukkit.entity.FallingSand;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import com.avaje.ebean.enhance.asm.commons.Method;
import com.sun.org.apache.xpath.internal.operations.And;

/**
 * A {@link Stream} of blocks!
 * @author Brord
 *
 */
public class Stream {
	
	/**
	 * List of spawned {@link FallingSand}
	 */
	private List<UUID> uuids;
	
	/**
	 * The {@link Material} used
	 */
	private Material m;
	
	/**
	 * The {@link Location} for this {@link Stream} to start
	 */
	private Location l;
	
	/**
	 * The amount of Streams.
	 */
	private int amount = 1;
	
	/**
	 * The byte used for the block
	 */
	private byte b = 0;
	
	/**
	 * Defines if this stream is enabled
	 */
	private boolean enabled = true;
	
	/**
	 * Defines if this stream is marked for removal
	 */
	private boolean removal = false;

	/**
	 * The {@link Vector} used when spawning {@link FallingSand}
	 */
	private Vector vector;

	/**
	 * Create 1 stream, byte 0, no {@link Vector}
	 * @param m
	 * @param l
	 */
	public Stream(Material m, Location l) {
		this(m, l ,1, (byte) 0);
	}
	
	/**
	 * Create 1 stream, no {@link Vector}
	 * @param m
	 * @param l
	 * @param b
	 */
	public Stream(Material m, Location l, byte b) {
		this(m, l ,1, b);
	}
	
	/**
	 * Create multiple streams, byte 0, no {@link Vector}
	 * @param m
	 * @param l
	 * @param amount
	 */
	public Stream(Material m, Location l, int amount) {
		this(m,l, amount, (byte)0);
	}
	
	/**
	 * Create multiple streams, no {@link Vector}
	 * @param m
	 * @param l
	 * @param amount
	 * @param b
	 */
	public Stream(Material m, Location l, int amount, byte b) {
		this(m, l, amount, b, new Vector(0, 0 ,0));
	}
	
	/**
	 * Create streams with a vector, byte {@link And} amount defined
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
	
	/**
	 * Spawn one block
	 * @return the Spawned {@link FallingBlock}
	 */
	private FallingBlock spawnOne(){
		FallingBlock b = l.getWorld().spawnFallingBlock(l, m.getId(), this.b);
		b.setMetadata("Visionist", new FixedMetadataValue(Visionist.getInstance(), "stream"));
		uuids.add(b.getUniqueId());
		return (b);
	}

	/**
	 * Returns the {@link Material}
	 * @return the {@link Material}
	 */
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return m;
	}
	
	/**
	 * Returns the {@link Location}
	 * @return a {@link Location}
	 */
	public Location getLocation(){
		return l;
	}
	
	/**
	 * Returns the {@link Byte} used
	 * @return a {@link Byte}
	 */
	public byte getByte(){
		return b;
	}
	
	/**
	 * Returns the amount used
	 * @return an {@link Integer}
	 */
	public int getAmount(){
		return amount;
	}
	
	/**
	 * Removes a {@link UUID} from the list
	 * @param uniqueId
	 * @return if it was successfull
	 */
	public boolean removeUUID(UUID uniqueId){
		return uuids.remove(uniqueId);
	}

	/**
	 * Mark this {@link Stream} for removal
	 */
	public void remove() {
		removal  = true;
	}
	
	/**
	 * Returns if this {@link Stream} needs removal
	 * @return a {@link Boolean}
	 */
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
