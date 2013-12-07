/**
 * 
 */
package net.castegaming.plugins.visionist.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * @author Brord
 *
 */
public class Disable extends IngameCommand {

	/**
	 * @param sender
	 * @param command
	 * @param args
	 * @throws NotIngameException
	 */
	public Disable(CommandSender sender, Command command, String[] args)
			throws NotIngameException {
		super(sender, command, args);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see net.castegaming.plugins.visionist.commands.CommandBase#handle()
	 */
	@Override
	public boolean handle() {
		// TODO Auto-generated method stub
		return false;
	}

}
