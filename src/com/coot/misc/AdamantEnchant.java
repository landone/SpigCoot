package com.coot.misc;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class AdamantEnchant extends Enchantment implements Listener {

	public AdamantEnchant(JavaPlugin plugin) {
		
		super(new NamespacedKey(plugin, "spigcoot_adamant"));
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		
	}
	
	@EventHandler
	public void OnPlayerItemDamage(PlayerItemDamageEvent event) {
		
		if (event.getItem().getEnchantments().containsKey(Enchantment.getByKey(this.getKey()))) {
			
			event.setCancelled(true);
			
		}
		
	}

	@Override
	public boolean canEnchantItem(ItemStack arg0) {
		return true;
	}

	@Override
	public boolean conflictsWith(Enchantment arg0) {
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.TOOL;
	}

	@Override
	public int getMaxLevel() {
		return 0;
	}

	@Override
	public String getName() {
		return "Adamant";
	}

	@Override
	public int getStartLevel() {
		return 0;
	}

	@Override
	public boolean isCursed() {
		return false;
	}

	@Override
	public boolean isTreasure() {
		return false;
	}
	
	
	
}
