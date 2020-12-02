package com.coot.teleport;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.util.Vector;

import com.coot.Module;
import com.coot.SpigCoot;
import com.coot.Yaml;

public class TeleTracker extends Module {
	
	private final String FILE_NAME = "tpback";
	
	private Yaml yml;
	private HashMap<Player, Location> map = new HashMap<Player, Location>();

	public TeleTracker(SpigCoot plugin) {
		super(plugin);
	}

	@Override
	public void onEnable() {
		
		commands.add("back");
		yml = new Yaml(FILE_NAME, SpigCoot.plugin);
		
	}

	@Override
	public void onDisable() {
		
		//Record all players to yml config
		for (Player p : SpigCoot.plugin.getServer().getOnlinePlayers()) {
			savePlayer(p);
		}
		//Save config to file
		yml.save();
		
	}

	@Override
	public void onCommand(Player sender, String cmd, String[] args) {
		
		if (cmd.contentEquals("back")) {
			
			//Teleport to previous location
			tp(sender, map.get(sender));
			
		}
		
	}
	
	public void tp(Player player, Location loc) {
		
		if (loc == null) {
			return;
		}
		//Remember player's previous location
		map.put(player, player.getLocation());
		player.teleport(loc, TeleportCause.PLUGIN);
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		String id = player.getUniqueId().toString();
		//If back location saved
		if (yml.config.contains(id, false)) {
			
			//Obtain back location from config
			UUID worldID = UUID.fromString(yml.config.getString(id + ".world"));
			World world = SpigCoot.plugin.getServer().getWorld(worldID);
			Vector pos = yml.config.getVector(id + ".pos");
			Location loc = new Location(world, pos.getX(), pos.getY(), pos.getZ());
			loc.setPitch((float)yml.config.getDouble(id + ".pitch"));
			loc.setYaw((float)yml.config.getDouble(id + ".yaw"));
			//Place location in immediate memory
			map.put(player, loc);
			
		}
		
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		
		Player player = event.getPlayer();
		savePlayer(player);
		
		//Remove from immediate memory
		map.remove(player);
		
	}
	
	private void savePlayer(Player player) {
		
		Location loc = map.get(player);
		if (loc == null) {
			return;
		}
		String id = player.getUniqueId().toString();
		yml.config.set(id + ".world", loc.getWorld().getUID().toString());
		yml.config.set(id + ".pos", loc.toVector());
		yml.config.set(id + ".pitch", loc.getPitch());
		yml.config.set(id + ".yaw", loc.getYaw());
		
	}

}
