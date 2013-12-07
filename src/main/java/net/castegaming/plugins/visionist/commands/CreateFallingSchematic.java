/**
 * 
 */
package net.castegaming.plugins.visionist.commands;

import java.util.HashMap;
import java.util.List;

import net.castegaming.plugins.visionist.Visionist;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.Vector;

/**
 * @author Brord
 *
 */
public class CreateFallingSchematic extends IngameCommand {
	
	public static HashMap<String, Location> pos1 = new HashMap<String, Location>();
	public static HashMap<String, Location> pos2 = new HashMap<String, Location>();

	/**
	 * @param sender
	 * @param command
	 * @param args
	 * @throws NotIngameException
	 */
	public CreateFallingSchematic(CommandSender sender, Command command, String[] args) throws NotIngameException {
		super(sender, command, args);
	}

	/**
	 * @see net.castegaming.plugins.visionist.commands.CommandBase#handle()
	 **/
	@Override
	public boolean handle() {
		String name = getPlayer().getName();
		if (args.length > 0){
			if (args[0].equalsIgnoreCase("pos1")){
				pos1.put(name, getPlayer().getLocation());
			} else if (args[0].equalsIgnoreCase("pos2")){
				pos2.put(name, getPlayer().getLocation());
			} else if (args[0].equalsIgnoreCase("create")){
				if (args.length > 1){
					if (pos1.containsKey(name) && pos2.containsKey(name)){
						YamlConfiguration structure = Visionist.getFile("structures");
						if (structure.contains(args[1])){
							msg("That name is allready in use! remove it first using /removestructure");
						} else {
						
							//if 1 and 2 are reversed, swap em back
							if (pos1.get(name).getBlockX() > pos2.get(name).getBlockX() && pos1.get(name).getBlockY() > pos2.get(name).getBlockY() && pos1.get(name).getBlockZ() > pos2.get(name).getBlockZ()){
								Location temp = pos1.get(name);
								pos1.put(name, pos2.get(name));
								pos2.put(name, temp);
							}
							
							int startx = getPlayer().getLocation().getBlockX();
							int starty = getPlayer().getLocation().getBlockY();
							int startz = getPlayer().getLocation().getBlockZ();
							World w = getPlayer().getWorld();
							
							HashMap<Vector, Block> blocks = new HashMap<Vector, Block>();;
							
							//this assumes pos1 is less in x, y and z then pos2
							for (int x = pos1.get(name).getBlockX(); x <= pos2.get(name).getBlockX(); x++){
								for (int y = pos1.get(name).getBlockY(); x <= pos2.get(name).getBlockY(); x++){
									for (int z = pos1.get(name).getBlockZ(); x <= pos2.get(name).getBlockZ(); x++){
										blocks.put(new Vector(x - startx, y-starty, z-startz), w.getBlockAt(x, y, z));
									}
								}
							}
							
							
							
							for (Vector v : blocks.keySet()){
								
							}
						}
					} else {
						msg("You forgot to add pos1 or pos2!");
						options();
					}
				} else {
					msg("Please define a name!");
				}
			} else {
				options();
			}
		} else {
			options();
		}
		return false;
	}
	
	private void options(){
		msg(ChatColor.DARK_RED + "Possible options: pos1, pos2, create [name]");
		msg("Make sure pos1 has a " + ChatColor.DARK_RED + ChatColor.BOLD + "LESSER" + ChatColor.RESET + " x, y and z then pos2!");
	}

}
