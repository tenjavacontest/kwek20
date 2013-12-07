/**
 * 
 */
package net.castegaming.plugins.visionist.commands;

import net.castegaming.plugins.visionist.Visionist;
import net.castegaming.plugins.visionist.types.Stream;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * @author Brord
 * {@link Command} to make a stream of blocks, a waterfall for example
 */
public class CreateStream extends IngameCommand {
	
	/**
	 * @throws NotIngameException 
	 * 
	 */
	public CreateStream(CommandSender sender, Command command, String[] args) throws NotIngameException {
		super(sender, command, args);
	}

	@Override
	public boolean handle() {
		Player p = getPlayer();
		
		if (args.length < 1){
			return  false;
		}
		
		Location l = p.getLocation();
		
		Material type;
		try {
			type = Material.valueOf(args[0].toUpperCase());
		} catch (IllegalArgumentException e){
			Visionist.msg(sender, "Please define a valid material name!");
			return true;
		}
		
		int amount = 1;
		byte b = 0;
		
		if (args.length > 1){
			try {
				if((amount = Integer.parseInt(args[1])) < 0) amount = 1;
			} catch (NumberFormatException e){
				//amount default 1
			}
			if (args.length > 2){
				try {
					if((b = Byte.parseByte(args[2])) < 0) b = 0;
				} catch (NumberFormatException e){
					//no byte needed, just for handyness
				}
			}
		}
		
		@SuppressWarnings("deprecation")
		Location to = p.getTargetBlock(null, 20).getLocation();
		Vector v = to.toVector().subtract(p.getLocation().toVector()).normalize();
		
		Visionist.getInstance().getStreamKeeper().addNewStream(new Stream(type, l, amount, b, v));
		msg("Added a new Stream!");
		return true;
	}

}
