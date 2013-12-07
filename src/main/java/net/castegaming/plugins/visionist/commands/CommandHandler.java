package net.castegaming.plugins.visionist.commands;

import java.util.Arrays;
import java.util.logging.Level;

import net.castegaming.plugins.visionist.Visionist;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
		
		if (label.startsWith("visionist") || label.equalsIgnoreCase("v")){
			if (args.length > 0){
				label = args[0];
				try {
					command = Bukkit.getServer().getPluginCommand(label);
				} catch(Exception e){
					Visionist.log("An error occured while handling command " + label + "(" + command.getName() + ") Exception:" + e.getClass().toString() + " " + e.getMessage());
					//whoups command didnt exist!
				}
				
				if (args.length > 1){
					args = Arrays.copyOfRange(args, 1, args.length);
				} else {
					args = new String[0];
				}
			} else {
				Visionist.msg(sender, ChatColor.GRAY + "---------------" + Visionist.prefix + ChatColor.GRAY + "---------------");
				Visionist.msg(sender, ChatColor.GOLD + "Welcome to " + ChatColor.DARK_RED + "Visionist" + ChatColor.DARK_GREEN + "!");
				Visionist.msg(sender, ChatColor.GOLD + "Visionist is a plugin to spice up your server with fancy visuals!");
				Visionist.msg(sender, ChatColor.GOLD + "For a list of all our commands, typ /help Visionist");
				Visionist.msg(sender, ChatColor.GOLD + "Version: " + Visionist.getInstance().getDescription().getVersion());
				Visionist.msg(sender, ChatColor.GOLD + "Author: " + Visionist.getInstance().getDescription().getAuthors());
				return true;
			}
		}
		
		try {
			if (label.equalsIgnoreCase("createstream")){
				cmd = new CreateStream(sender, command, args);
			} else if (label.equalsIgnoreCase("disablestream")){
				cmd = new Disable(sender, command, args);
			} else if (label.equalsIgnoreCase("enablestream")){
				cmd = new Enable(sender, command, args);
			} else if (label.equalsIgnoreCase("removestream")){
				cmd = new Remove(sender, command, args);
			} else if (label.equalsIgnoreCase("createstructure")){
				cmd = new CreateFallingSchematic(sender, command, args);
			} else if (label.equalsIgnoreCase("fallingstructure")){
				cmd = new UseFallingSchematic(sender, command, args);
			} else {
				cmd = new CreateStream(sender, command, args);
			}
		} catch (NotIngameException e){
			Visionist.msg(sender, "You must be ingame to execute this command!");
			return true;
		}
		
		if (cmd != null){
			if (!cmd.handle()){
				Visionist.msg(sender, command.getUsage());
			}
			return true;
		} else {
			return false;
		}
	}

}
