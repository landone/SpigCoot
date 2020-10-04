package com.coot.rpg;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.coot.Module;
import com.coot.SpigCoot;
import com.coot.Yaml;

public class CoinBank extends Module {

	private final String FILE_NAME = "coinBank";
	
	private Yaml yml;
	private HashMap<UUID, Long> map = new HashMap<UUID, Long>();
	
	public CoinBank(SpigCoot plugin) {
		super(plugin);
	}
	
	@Override
	public void onEnable() {
		
		commands.add("bank");
		yml = new Yaml(FILE_NAME, SpigCoot.plugin);
		
		//Load currently online players in case of reload
		for (Player player : SpigCoot.plugin.getServer().getOnlinePlayers()) {
			UUID id = player.getUniqueId();
			map.put(id, yml.config.getLong(id.toString()));
		}
		
	}

	@Override
	public void onDisable() {
		
		save();
		
	}
	
	public void add(Player player, long amount) {
		set(player, get(player) + amount);
	}
	
	public void set(Player player, long value) {
		map.put(player.getUniqueId(), value);
	}
	
	public long get(Player player) {
		return map.get(player.getUniqueId());
	}
	
	//Load value from config
	@EventHandler
	public void OnConnect(PlayerJoinEvent event) {
		
		UUID id = event.getPlayer().getUniqueId();
		map.put(id, yml.config.getLong(id.toString()));
		
	}
	
	//Save value to config
	@EventHandler
	public void OnDisconnect(PlayerQuitEvent event) {
		
		UUID id = event.getPlayer().getUniqueId();
		yml.config.set(id.toString(), map.get(id));
		map.remove(id);
		
	}
	
	//Save to file
	public boolean save() {
		
		for (Entry<UUID, Long> ent : map.entrySet()) {
			yml.config.set(ent.getKey().toString(), ent.getValue());
		}
		return yml.save();
		
	}

	@Override
	public void onCommand(Player sender, String cmd, String[] args) {
		
		sender.sendMessage("Bank balance: " + ChatColor.GOLD + get(sender) + " coins");
		
	}
	
}
