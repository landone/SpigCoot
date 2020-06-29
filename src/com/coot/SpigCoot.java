package com.coot;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class SpigCoot extends JavaPlugin implements Listener {
	
	Logger log = this.getLogger();
	World skyblock;
	World skynether;
	SkyblockGenerator skyGen = new SkyblockGenerator();
	SkynetherGenerator skynethGen = new SkynetherGenerator();
	
	@Override
	public void onEnable() {
		
		this.getServer().getPluginManager().registerEvents(this, this);
		skyblock = skyGen.createWorld();
		skynether = skynethGen.createWorld();
	
	}
	
	@Override
	public void onDisable() {
		
		
		
	}
	
	private boolean deleteWorld(File path) {
	      if(path.exists()) {
	          File files[] = path.listFiles();
	          for(int i=0; i<files.length; i++) {
	              if(files[i].isDirectory()) {
	                  deleteWorld(files[i]);
	              } else {
	                  files[i].delete();
	              }
	          }
	      }
	      return(path.delete());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			return false;
		}
		
		Player player = (Player)sender;
		
		switch(label) {
		case "sky":
			if (player.isOp() && args.length == 1 && args[0].equalsIgnoreCase("reset")) {
				player.sendMessage(ChatColor.DARK_PURPLE + "Recreating skyblock worlds...");
				
				/*File path = skyblock.getWorldFolder();
				skyblock = null;
				this.getServer().unloadWorld("skyblock", true);
				deleteWorld(path);
				skyblock = skyGen.createWorld();
				skyGen.regenerateChunk(skyblock, 0, 0);*/
				
				File path = skynether.getWorldFolder();
				skynether = null;
				this.getServer().unloadWorld("skynether", true);
				deleteWorld(path);
				skynether = skynethGen.createWorld();
				
				player.sendMessage(ChatColor.LIGHT_PURPLE + "Skyblock worlds reset.");
			}
			player.teleport(skyblock.getSpawnLocation(), TeleportCause.COMMAND);
			break;
		}
		
		return true;
		
	}
	
	@EventHandler
	public void OnPlayerPortal(PlayerPortalEvent event) {
		
		Location from = event.getFrom();
		Location to = event.getTo();
		
		if (from.getWorld() == skyblock) {
			
			to.setWorld(skynether);
			event.setTo(to);
			
		}
		else if (from.getWorld() == skynether) {
			
			to.setWorld(skyblock);
			event.setTo(to);
			
		}
		
	}
	
}
