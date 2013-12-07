/**
 * 
 */
package net.castegaming.plugins.visionist.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * @author Brord
 * {@link Command} to make a stream of blocks, a waterfall for example
 */
public class CreateFall extends CommandBase {

	/**
	 * @throws NotIngameException 
	 * 
	 */
	public CreateFall(CommandSender sender, Command command, String[] args) throws NotIngameException {
		super(sender, command, args);
		if (!(sender instanceof Player)){
			throw new NotIngameException();
		}
	}

	@Override
	public boolean handle() {
		if (args.length < 3){
			return false;
		}
		
		Player p = Bukkit.getServer().getPlayer(args[0]);
		if (p == null){
			return false;
		}
		
		int amount;
		try {
			amount = Integer.parseInt(args[1]);
		} catch (NumberFormatException e){
			return false;
		}
		
		return true;
	}

}
