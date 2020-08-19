package com.coot;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class SpigCoot extends JavaPlugin implements Listener {
	
	Logger log = this.getLogger();
	World skyblock;
	//World skynether;
	SkyblockGenerator skyGen = new SkyblockGenerator();
	SkynetherGenerator skynethGen = new SkynetherGenerator();
	
	Enchantment[] enchants = new Enchantment[1];
	
	
	@Override
	public void onEnable() {
		
		this.getServer().getPluginManager().registerEvents(this, this);
		
		enchants[0] = new AdamantEnchant(this);
		registerEnchantments(enchants);
		
		skyblock = skyGen.createWorld();
		//skynether = skynethGen.createWorld();
	
	}
	
	@Override
	public void onDisable() {
		
		unregisterEnchantments(enchants);
		
	}
	
	public static boolean registerEnchantments(Enchantment[] ench) {
		
		try {
			
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
			for (int i = 0; i < ench.length; i++) {
				Enchantment.registerEnchantment(ench[i]);
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	}
	
	public static void unregisterEnchantments(Enchantment[] ench) {
		
		try {
			
			Field keyField = Enchantment.class.getDeclaredField("byKey");
			keyField.setAccessible(true);
			@SuppressWarnings("unchecked")
			HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) keyField.get(null);
			
			Field nameField = Enchantment.class.getDeclaredField("byName");
			nameField.setAccessible(true);
			@SuppressWarnings("unchecked")
			HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) nameField.get(null);
			
			for (int i = 0; i < ench.length; i++) {
				
				if (byKey.containsKey(ench[i].getKey())) {
					byKey.remove(ench[i].getKey());
				}
				
				if (byName.containsKey(ench[i].getName())) {
					byName.remove(ench[i].getName());
				}
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
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
				
				/*File path = skynether.getWorldFolder();
				skynether = null;
				this.getServer().unloadWorld("skynether", true);
				deleteWorld(path);
				skynether = skynethGen.createWorld();*/
				
				player.sendMessage(ChatColor.LIGHT_PURPLE + "Skyblock worlds reset.");
			}
			player.teleport(skyblock.getSpawnLocation(), TeleportCause.COMMAND);
			break;
			
		case "test":
			if (player.isOp()) {
				ItemStack item = player.getItemInHand();
				item.addUnsafeEnchantment(enchants[0], 0);
				ItemMeta meta = item.getItemMeta();
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(ChatColor.LIGHT_PURPLE + "Adamant");
				if (meta.getLore() != null) {
					lore.addAll(meta.getLore());
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "Enchanted");
				player.playSound(player.getLocation(), Sound.ENTITY_WANDERING_TRADER_DRINK_POTION, 1.0f, 1.0f);
			}
			break;
		}
		
		return true;
		
	}
	
	@EventHandler
	public void OnPlayerPortal(PlayerPortalEvent event) {
		
		/*Location from = event.getFrom();
		Location to = event.getTo();
		
		if (from.getWorld() == skyblock) {
			
			to.setWorld(skynether);
			event.setTo(to);
			
		}
		else if (from.getWorld() == skynether) {
			
			to.setWorld(skyblock);
			event.setTo(to);
			
		}*/
		
	}
	
}
