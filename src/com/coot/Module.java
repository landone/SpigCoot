package com.coot;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class Module implements Listener {
	
	protected List<String> commands = new ArrayList<String>();
	
	public Module(SpigCoot plugin) {
		
		plugin.modules.add(this);
		
	}
	
	public void addCommands() {
		
		for (String cmd : commands) {
			SpigCoot.plugin.commands.put(cmd.toLowerCase(), this);
		}
		
	}
	
	public abstract void onEnable();
	public abstract void onDisable();
	public abstract void onCommand(Player sender, String cmd, String[] args);
	
}
