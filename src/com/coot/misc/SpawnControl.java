package com.coot.misc;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.coot.Module;
import com.coot.SpigCoot;

public class SpawnControl extends Module {
	
	private World mainWorld = null;

	public SpawnControl(SpigCoot plugin) {
		super(plugin);
	}

	@Override
	public void onEnable() {
		
		commands.add("setspawn");
		commands.add("spawn");
		
	}

	@Override
	public void onDisable() {
		
	}

	@Override
	public void onCommand(Player sender, String cmd, String[] args) {
		
		if (cmd.contentEquals("spawn")) {
			
			if (mainWorld == null) {
				mainWorld = SpigCoot.plugin.getServer().getWorlds().get(0);
			}
			SpigCoot.plugin.teleTrack.tp(sender, mainWorld.getSpawnLocation());
			
		}
		else if (cmd.contentEquals("setspawn")) {
			
			if (!sender.isOp()) {
				sender.sendMessage(ChatColor.DARK_GRAY + "You do not have permission to set spawn.");
				return;
			}
			
			sender.getWorld().setSpawnLocation(sender.getLocation());
			sender.sendMessage(ChatColor.GREEN + "Spawn set.");
			
		}
		
	}

}
