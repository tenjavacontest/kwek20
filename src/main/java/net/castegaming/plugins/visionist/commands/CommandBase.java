package net.castegaming.plugins.visionist.commands;

import net.castegaming.plugins.visionist.Visionist;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class CommandBase {

	CommandSender sender;
	Command cmd;
	String[] args;
	
	/**
	 * base {@link Command}, needed for every {@link Visionist} {@link Command}
	 * @param sender the {@link CommandSender}
	 * @param command the {@link Command} itself
	 * @param args the arguments typed by the user
	 */
	public CommandBase(CommandSender sender, Command command, String[] args) {
		this.sender = sender;
		this.cmd = command;
		this.args = args;
	}
	
	/**
	 * This is the handle for a {@link Command}
	 * @return <code>true</code> if it was successful, otherwise <code>false</code>
	 */
	public abstract boolean handle();
	

}
