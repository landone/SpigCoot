package com.coot;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigCoot extends JavaPlugin {
	
	Logger log = this.getLogger();
	World skyblock;
	SkyblockGenerator skyGen = new SkyblockGenerator();
	
	@Override
	public void onEnable() {
		
		skyblock = skyGen.createWorld();
	
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
				File path = skyblock.getWorldFolder();
				skyblock = null;
				this.getServer().unloadWorld("skyblock", true);
				deleteWorld(path);
				skyblock = skyGen.createWorld();
			}
			player.teleport(skyblock.getSpawnLocation(), TeleportCause.COMMAND);
			break;
		}
		
		return true;
		
	}
	
}
