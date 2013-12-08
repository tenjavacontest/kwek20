/**
 * 
 */
package net.castegaming.plugins.visionist;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.castegaming.plugins.visionist.commands.CommandHandler;
import net.castegaming.plugins.visionist.managers.StreamKeeper;
import net.castegaming.plugins.visionist.types.Stream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

/**
 * @author Brord
 *
 */
public class Visionist extends JavaPlugin {
		
	private static Visionist plugin;
	public static String prefix = ChatColor.MAGIC + "|" + ChatColor.GRAY + "[" + ChatColor.GOLD + "Visionist" + ChatColor.GRAY + "]" + ChatColor.MAGIC + "| " + ChatColor.RESET;
	private CommandHandler handler;
	private StreamKeeper keeper;
	
	@Override
	public void onEnable(){
		super.onEnable();
		log("Loading Visionist...");
		
		plugin = this;
		saveDefaultConfig();
		checkConfig();
		loadconfig();
		
		handler = new CommandHandler(this);
		
		Bukkit.getServer().getPluginManager().registerEvents(new VisionistListener(this), this);
		loadStreams();
		keeper.start();
		
		log("Loading done!");
	}
	
	/**
	 * Loads all the constants from config to our class
	 */
	public void loadconfig() {
		Consts.MAX_DISTANCE = getConfig().getInt("max_dist", 20);
		Consts.MIN_HEIGHT = getConfig().getInt("min_height", 20);
	}

	/**
	 * Loads all the streams from the config
	 */
	public void loadStreams() {
		Bukkit.getServer().getPluginManager().registerEvents(keeper = new StreamKeeper(), this);
		
		YamlConfiguration streams = getFile("streams.yml");
		keeper.clean();
		for (String s : streams.getKeys(false)){
			Material m = Material.valueOf(streams.getString(s + ".material"));
			if (m == null) continue;

			World w;
			if ((w = Bukkit.getServer().getWorld(streams.getString(s + ".world"))) == null) continue;
			
			List<Integer> coords = streams.getIntegerList(s + ".location");
			if (coords == null || coords.size() < 3) continue;

			Location l = new Location(w, coords.get(0), coords.get(1), coords.get(2));
			
			coords =  streams.getIntegerList(s + ".vector");
			Vector v;
			if (coords == null || coords.size() < 3) v = new Vector();
			else v = new Vector(coords.get(0), coords.get(1), coords.get(2));
			
			log("Added stream with material " + m.name());
			Stream stream = new Stream(m , l, streams.getInt(s + ".amount", 1), (byte)streams.getInt(s + ".byte", 0), v);
			if (!streams.getBoolean(s + ".enabled", true)){
				stream.disable();
			}
			
			keeper.addStream(stream);
		}
	}

	@Override
	public void onDisable(){
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return handler.handle(sender, command, args);
	}
	
	/**
	 * Checks the config for possible mistakes or needed updates.
	 */
	public void checkConfig(){
		saveDefaultConfig();
		
		String[] files = new String[]{"streams.yml", "structures.yml"};
		for (String filename : files){
			if (!new File(getDataFolder() + File.separator + filename).exists()){
				new File(getDataFolder() + File.separator + filename).mkdir();
			}
		}
		
		YamlConfiguration config = YamlConfiguration.loadConfiguration(getResource("config.yml"));
		YamlConfiguration fconfig = YamlConfiguration.loadConfiguration(new File(getDataFolder() + File.separator + "config.yml"));
		for (String key : config.getKeys(true)){
			if (!fconfig.contains(key)){
				getConfig().set(key, config.get(key));
				log("Added the config value: " + key);
				saveConfig();
			}
		}
	}
	
	/**
	 * Logs to the console, in colors if possible
	 * @param string the {@link String} to log
	 */
	public static void log(String string) {
		try{
			Bukkit.getServer().getConsoleSender().sendMessage(prefix + string);
		} catch (Exception e){
			System.out.println("[Visionist] " + string);
		}
	}
	
	/**
	 * Returns the name prefix used in this plugin
	 * @return a {@link String}
	 */
	public String getprefix() {
		return prefix;
	}

	/**
	 * @return the instance
	 */
	public static Visionist getInstance() {
		return plugin;
	}

	/**
	 * @param sender the receiver of this msg
	 * @param string the message
	 */
	public static void msg(CommandSender sender, String string) {
		if (sender instanceof Player && sender != null){
			((Player)sender).sendMessage(prefix + string);
		} else {
			log(string);
		}
	}

	/**
	 * Returns the {@link StreamKeeper} handler.
	 * @return the {@link StreamKeeper}
	 */
	public StreamKeeper getStreamKeeper() {
		return keeper;
	}

	/**
	 * @param string
	 * @return
	 */
	public static YamlConfiguration getFile(String name) {
		if (!(name.endsWith(".yml"))) name += ".yml";
		return YamlConfiguration.loadConfiguration(new File(Visionist.getInstance().getDataFolder() + File.separator + name));
	}

	/**
	 * @param string
	 */
	public static void saveFile(YamlConfiguration c, String name) {
		if (!(name.endsWith(".yml"))) name += ".yml";
		try {
			c.save(Visionist.getInstance().getDataFolder() + File.separator + name);
		} catch (IOException e) {
			log("Could not save " + name + "!!! Is the disk full?");
		}
	}
}
