/**
 * 
 */
package net.castegaming.plugins.visionist.commands;

import net.castegaming.plugins.visionist.Visionist;
import net.castegaming.plugins.visionist.managers.Stream;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Brord
 * {@link Command} to make a stream of blocks, a waterfall for example
 */
public class CreateFall extends IngameCommand {
	
	/**
	 * @throws NotIngameException 
	 * 
	 */
	public CreateFall(CommandSender sender, Command command, String[] args) throws NotIngameException {
		super(sender, command, args);
	}

	@Override
	public boolean handle() {
		Player p = getPlayer();
		
		if (args.length < 3){
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
		
		int amount;
		try {
			if((amount = Integer.parseInt(args[1])) < 0) amount = 1;
		} catch (NumberFormatException e){
			amount = 1;
			//amount default 1
		}
		
		byte b;
		try {
			if((b = Byte.parseByte(args[2])) < 0) b = 0;
		} catch (NumberFormatException e){
			b = 0;
			//no byte needed, just for handyness
		}
		
		Visionist.getInstance().getStreamKeeper().addStream(new Stream(type, l, amount, b));
		return true;
	}

}
