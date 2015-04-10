package me.supernova1992.ApplicationPlugin;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener{

	private ApplicationPlugin plugin;
	public PlayerListener(ApplicationPlugin plugin){
			
			plugin.getServer().getPluginManager().registerEvents(this, plugin);
			
			this.plugin = plugin;
	}
	
	@EventHandler
	public void onLogin(PlayerJoinEvent e){
		
		 UUID uuid  = e.getPlayer().getUniqueId();
		 
		 Player player = e.getPlayer();
		 
		 final String UUIDStr = uuid.toString();
		
		if(!(player.hasPermission("modifyworld.*"))){
			
			
			 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,new Runnable(){
				public void run(){

					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "title "+UUIDStr+" title \" \"");
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "title "+UUIDStr+" subtitle \"Please apply by using /apply \"");
					
					Bukkit.getServer().getLogger().info("Application Advertisement sent!");
				}
			},40);
			
						
			
			
		}
		
		
		
	}
	
}
