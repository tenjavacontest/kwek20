/**
 * 
 */
package net.castegaming.plugins.visionist.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.castegaming.plugins.visionist.Consts;
import net.castegaming.plugins.visionist.Visionist;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.FallingBlock;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Brord
 *
 */
public class UseFallingSchematic extends IngameCommand {

	/**
	 * @param sender
	 * @param command
	 * @param args
	 * @throws NotIngameException
	 */
	public UseFallingSchematic(CommandSender sender, Command command,
			String[] args) throws NotIngameException {
		super(sender, command, args);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see net.castegaming.plugins.visionist.commands.CommandBase#handle()
	 **/
	@Override
	public boolean handle() {
		if (args.length > 0){
			YamlConfiguration structures = Visionist.getFile("structures");
			if (structures.contains(args[0])){
				Location l = getPlayer().getLocation();
				Location temploc = null;
				
				HashMap<Location, int[]> blocks = new HashMap<Location, int[]>();
				
				int[] coords;
				String[] scoords;
				
				for (String s : structures.getConfigurationSection(args[0]).getKeys(false)){
					//format: x_y_z: craftblock
					scoords = s.split("_");
					if (scoords.length < 3){
						msg("Something went wrong with this structure! Contact the server admin with error code #958.");
						return true;
					}
					
					coords = new int[3];
					try {
						for (int i = 0; i < 3; i++){
							coords[i] = Integer.parseInt(scoords[i]);
						}
					} catch (NumberFormatException e){
						msg("Something went wrong with this structure! Contact the server admin with error code #453.");
						return true;
					}
					
					temploc = new Location(l.getWorld(), l.getBlockX() + coords[0], l.getBlockY() + coords[1], l.getBlockZ() + coords[2]);
					int[] stat = new int[2];
					stat[0] = structures.getInt(args[0] + "." +  s + ".type", 1);
					stat[1] = structures.getInt(args[0] + "." + s + ".data", 0);
					blocks.put(temploc, stat);
					//casting woo
				}
				
				for (Location temp : blocks.keySet()){
					double tempy = temp.getY();
					if (!(emptySpace(temp.clone()).getY()-tempy >= Consts.MIN_HEIGHT)){
						//not enough space :(
						msg("Please make sure there is atleast " + Consts.MIN_HEIGHT + " of free space above you.");
						return true;
					}
				}
				
				final HashMap<Location, int[]> fblocks = blocks;
				int i=0;
				for (final Location temp : fblocks.keySet()){
					new BukkitRunnable(){
	
						@Override
						public void run() {
							if (temp != null && fblocks.get(temp) != null){
								int id = fblocks.get(temp)[0];
								byte b = (byte) fblocks.get(temp)[1];
								
								FallingBlock f = temp.getWorld().spawnFallingBlock(temp.add(new Location(temp.getWorld(), 0, Consts.MIN_HEIGHT, 0)), id, b);
								f.setMetadata("Visionist", new FixedMetadataValue(Visionist.getInstance(), "fallingschematic"));
							}
						}
						
					}.runTaskLater(Visionist.getInstance(), i*5);
					i++;
				}
				
				msg("Sucesfully spawned your structure!");
			} else {
				msg("That structure doesn't exist! pick one of these: ");
				printStructureOptions();
			}
			
			return true;
		} else {
			return false;
		}
	}
	
	private Location emptySpace(Location l){
		if (l != null){
			Location locabove = l.add(0, 1, 0);
			if (locabove.getBlock().getType().equals(Material.AIR)){
				locabove = emptySpace(locabove);
			}
			return locabove;
		} else {
			return null;
		}
	}
	
	private void printStructureOptions(){
		//Set<String> strings = Visionist.getFile("structures").getKeys(true);
		//msg(Arrays.toString(strings.toArray(new String[strings.size()])) + " " + strings.size());
		msg(Arrays.toString(Visionist.getFile("structures").getKeys(false).toArray(new String[0])).replace("[", "").replace("]", ""));
	}
}
