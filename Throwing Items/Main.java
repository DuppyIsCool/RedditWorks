package me.DuppyIsCool.MyPlugin;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	@Override
	public void onEnable() {
		
		Plugin.plugin = this;
		
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Enabling Throw Item");
		
		//Create EventManger class object
		EventManager events = new EventManager();
		
		//Register eventmanager
		getServer().getPluginManager().registerEvents(events, this);
		
	}
	
	@Override
	public void onDisable() {
		getServer().getConsoleSender().sendMessage(ChatColor.RED + "Disabling Throw Item");
	}
}
