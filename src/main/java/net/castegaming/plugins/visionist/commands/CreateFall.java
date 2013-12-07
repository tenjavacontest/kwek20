/**
 * 
 */
package net.castegaming.plugins.visionist.commands;

import net.castegaming.plugins.visionist.Visionist;

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
		
		Material type;
		try {
			type = Material.valueOf(args[0].toUpperCase());
		} catch (IllegalArgumentException e){
			Visionist.msg(sender, "Please define a valid material name!");
		}
		
		
		return true;
	}

}
