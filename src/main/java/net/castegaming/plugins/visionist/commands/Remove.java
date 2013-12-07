/**
 * 
 */
package net.castegaming.plugins.visionist.commands;

import java.util.List;

import net.castegaming.plugins.visionist.Visionist;
import net.castegaming.plugins.visionist.managers.Stream;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * @author Brord
 *
 */
public class Remove extends IngameCommand {

	/**
	 * @param sender
	 * @param command
	 * @param args
	 * @throws NotIngameException
	 */
	public Remove(CommandSender sender, Command command, String[] args) throws NotIngameException {
		super(sender, command, args);
	}

	/** 
	 *  @see net.castegaming.plugins.visionist.commands.CommandBase#handle()
	 **/
	@Override
	public boolean handle() {
		Location l = getPlayer().getLocation();
		
		List<Stream> streams = Visionist.getInstance().getStreamKeeper().getStreams();
		
		int id = 1;
		int currentMax = 1;
		double distance = Double.MAX_VALUE;
		Stream closest = null;
		for (Stream s : streams){
			if (l.distanceSquared(s.getLocation()) < distance){
				distance = l.distanceSquared(s.getLocation());
				closest = s;
				currentMax = id;
			} 
			id++;
		}
		
		if (closest != null){
			closest.disable();
			closest.remove();
			YamlConfiguration c = Visionist.getFile("streams");
			c.set(currentMax + "", null);
			Visionist.saveFile(c, "streams");
			
			msg("Removed your closest stream!");
			return true;
		}
		return false;
	}

}
