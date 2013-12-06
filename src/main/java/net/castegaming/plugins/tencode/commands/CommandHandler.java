package net.castegaming.plugins.tencode.commands;

import net.castegaming.plugins.tencode.Tencode;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


public class CommandHandler {

	private Tencode plugin;
	
	public CommandHandler(Tencode plugin) {
		this.plugin = plugin;
	}
	
	public boolean handle(CommandSender sender, Command command, String[] args){
		
		CommandBase cmd = null;
		String label = command.getLabel();
		
		try {
			if (label.equalsIgnoreCase("sellgem")){
				cmd = new Cmd(sender, command, args);
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
