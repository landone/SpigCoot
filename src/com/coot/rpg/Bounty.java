package com.coot.rpg;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import com.coot.Module;
import com.coot.SpigCoot;

public class Bounty extends Module {

	private Sound BOUNTY_SOUND = Sound.BLOCK_NOTE_BLOCK_BELL;
	private Random rand = new Random();
	private CoinBank bank;
	private long maxBounty = 3;
	
	public Bounty(SpigCoot plugin) {
		super(plugin);
	}
	
	@Override
	public void onEnable() {
		
		bank = SpigCoot.plugin.bank;
		
	}

	@Override
	public void onDisable() {
		
	}
	
	@EventHandler
	public void OnEntityDie(EntityDeathEvent event) {
		
		LivingEntity victim = event.getEntity();
		Player killer = victim.getKiller();
		
		if (killer != null && victim.getType() != EntityType.PLAYER) {
			
			killer.playSound(victim.getLocation(), BOUNTY_SOUND, 1.0f, 1.0f);
			long bounty = Math.abs(rand.nextLong()) % maxBounty + 1;
			killer.sendMessage("Received " + ChatColor.GOLD + bounty + " gold");
			bank.add(killer, bounty);
			
		}
		
	}

	@Override
	public void onCommand(Player sender, String cmd, String[] args) {
		//Nothing
	}
	
}
