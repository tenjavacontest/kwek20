/**
 * 
 */
package net.castegaming.plugins.visionist.commands;

import java.util.HashMap;

import net.castegaming.plugins.visionist.Consts;
import net.castegaming.plugins.visionist.Visionist;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

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
			YamlConfiguration structures = Visionist.getFile("strucutes");
			if (structures.contains(args[0])){
				Location l = getPlayer().getLocation();
				Location temploc = null;
				
				HashMap<Location, Block> blocks = new HashMap<Location, Block>();
				
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
					blocks.put(temploc, (Block) structures.get(args[0] + "." + s));
					//casting woo
				}
				
				for (Location temp : blocks.keySet()){
					if (!(emptySpace(temp).getBlockY()-temp.getBlockY() >= Consts.MIN_HEIGHT)){
						//not enough space :(
						msg("Please make sure there is atleast " + Consts.MIN_HEIGHT + " of free space above you.");
						return true;
					}
				}
				
				for (Location temp : blocks.keySet()){
					l.getWorld().spawnFallingBlock(temp, blocks.get(temp).getTypeId(), blocks.get(temp).getData());
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
		msg(Visionist.getFile("strucutes").getKeys(false).toArray(new String[0]).toString().replaceFirst("[", "").replaceFirst("]", ""));
	}
}
