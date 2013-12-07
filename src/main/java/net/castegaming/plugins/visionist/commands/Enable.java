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
public class Enable extends IngameCommand {

	/**
	 * @param sender
	 * @param command
	 * @param args
	 * @throws NotIngameException
	 */
	public Enable(CommandSender sender, Command command, String[] args)
			throws NotIngameException {
		super(sender, command, args);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see net.castegaming.plugins.visionist.commands.CommandBase#handle()
	 **/
	@Override
	public boolean handle() {
		Location l = getPlayer().getLocation();
		
		List<Stream> streams = Visionist.getInstance().getStreamKeeper().getStreams();
		
		int i = 1;
		int closestid = 1;
		
		double distance = Double.MAX_VALUE;
		Stream closest = null;
		for (Stream s : streams){
			if (l.distanceSquared(s.getLocation()) < distance){
				distance = l.distanceSquared(s.getLocation());
				closest = s;
				closestid = i;
			}
			i++;
		}
		
		if (closest != null){
			YamlConfiguration c = Visionist.getFile("streams");
			c.set(closestid + ".enabled", true);
			Visionist.saveFile(c, "streams");
			
			closest.enable();
			msg("Enabled your closest stream!");
			return true;
		}
		return false;
	}

}
