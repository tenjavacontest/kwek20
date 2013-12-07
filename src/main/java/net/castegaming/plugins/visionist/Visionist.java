/**
 * 
 */
package net.castegaming.plugins.visionist;

import java.io.File;

import net.castegaming.plugins.visionist.commands.CommandHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
	
	
	@Override
	public void onEnable(){
		super.onEnable();
		plugin = this;
		saveDefaultConfig();
		checkConfig();
		handler = new CommandHandler(this);
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
