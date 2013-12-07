package net.castegaming.plugins.visionist.commands;

import net.castegaming.plugins.visionist.Visionist;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


public class CommandHandler {

	private Visionist plugin;
	
	public CommandHandler(Visionist plugin) {
		this.plugin = plugin;
	}
	
	public boolean handle(CommandSender sender, Command command, String[] args){
		
		CommandBase cmd = null;
		String label = command.getLabel();
		
		try {
			if (label.equalsIgnoreCase("visionist")){
				cmd = new Help(sender, command, args);
			} else if (label.equalsIgnoreCase("command2")){
				
			} else {
				cmd = new Help(sender, command, args);
			}
		} catch (NoConsoleException e){
			sender.sendMessage(plugin.prefix + "You must be the console to execute this command!");
			return true;
		}
		
		if (cmd != null){
			return cmd.handle();
		} else {
			return false;
		}
	}

}
