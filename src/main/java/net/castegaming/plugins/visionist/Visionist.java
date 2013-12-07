/**
 * 
 */
package net.castegaming.plugins.visionist;

import java.io.File;
import java.util.List;

import net.castegaming.plugins.visionist.commands.CommandHandler;
import net.castegaming.plugins.visionist.managers.Stream;
import net.castegaming.plugins.visionist.managers.StreamKeeper;

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
		plugin = this;
		saveDefaultConfig();
		checkConfig();
		handler = new CommandHandler(this);
		
		
		loadStreams();
		keeper.start();
	}
	
	/**
	 * 
	 */
	private void loadStreams() {
		Bukkit.getServer().getPluginManager().registerEvents(keeper = new StreamKeeper(), this);
		
		YamlConfiguration streams = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "streams.yml"));
		for (String s : streams.getKeys(false)){
			Material m = Material.valueOf(streams.getString("material"));
			if (m == null) continue;
			
			World w;
			if ((w = Bukkit.getServer().getWorld(streams.getString(s + "world"))) == null) continue;
			
			List<Integer> coords = streams.getIntegerList(s + "location");
			if (coords == null || coords.size() < 3) continue;
			
			Location l = new Location(w, coords.get(0), coords.get(1), coords.get(2));
			
			new Stream(m , l, streams.getInt(s + "amount", 1), (byte)streams.getInt(s + "byte", 0));
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
	 * @return the isntance
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
}
