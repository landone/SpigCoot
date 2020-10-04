package com.coot;

import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigCoot extends JavaPlugin implements Listener {
	
	Logger log = this.getLogger();
	CoinBank bank;
	
	
	@Override
	public void onEnable() {
		
		this.getServer().getPluginManager().registerEvents(this, this);
		bank = new CoinBank(this);
	
	}
	
	@Override
	public void onDisable() {
		
		bank.save();
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			return false;
		}
		
		Player player = (Player)sender;
		
		switch(label) {
		case "bank":
			player.sendMessage("Bank balance: " + ChatColor.GOLD + bank.get(player) + " coins");
			break;
		}
		
		return true;
		
	}
	
	@EventHandler
	public void OnEntityDie(EntityDeathEvent event) {
		
		LivingEntity victim = event.getEntity();
		Player killer = victim.getKiller();
		if (killer != null && victim.getType() != EntityType.PLAYER) {
			
			killer.playSound(victim.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f);
			Random rand = new Random();
			long bounty = Math.abs(rand.nextLong()) % 3 + 1;
			killer.sendMessage("Received " + ChatColor.GOLD + bounty + " gold");
			bank.add(killer, bounty);
			
		}
		
	}
	
}
