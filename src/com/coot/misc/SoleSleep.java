package com.coot.misc;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBedEnterEvent;

import com.coot.Module;
import com.coot.SpigCoot;

public class SoleSleep extends Module {
	
	private final long NIGHT = 12541;
	private final long MORNING = 23458;

	public SoleSleep(SpigCoot plugin) {
		super(plugin);
	}

	@Override
	public void onEnable() {}

	@Override
	public void onDisable() {}

	@Override
	public void onCommand(Player sender, String cmd, String[] args) {}
	
	@EventHandler
	public void onSleep(PlayerBedEnterEvent event) {
		
		World world = event.getPlayer().getWorld();
		long time = world.getTime();
		
		if (world.isThundering() || (time >= NIGHT && time <= MORNING)) {
			
			world.setTime(0);
			
		}
		
	}

}
