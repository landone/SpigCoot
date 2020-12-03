package com.coot.teleport;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.coot.Module;
import com.coot.SpigCoot;
import com.coot.Yaml;

public class Homes extends Module implements TabCompleter {

	private final String FILE_NAME = "homes";
	
	private Yaml yml;
	private HashMap<String, Location> map = new HashMap<String, Location>();
	
	public Homes(SpigCoot plugin) {
		super(plugin);
	}

	@Override
	public void onEnable() {
		
		commands.add("home");
		SpigCoot.plugin.getCommand("home").setTabCompleter(this);
		commands.add("sethome");
		
		yml = new Yaml(FILE_NAME, SpigCoot.plugin);
		for (String id : yml.config.getKeys(false)) {
			
			UUID worldID = UUID.fromString(yml.config.getString(id + ".world"));
			World world = SpigCoot.plugin.getServer().getWorld(worldID);
			Vector pos = yml.config.getVector(id + ".pos");
			Location loc = new Location(world, pos.getX(), pos.getY(), pos.getZ());
			loc.setPitch((float)yml.config.getDouble(id + ".pitch"));
			loc.setYaw((float)yml.config.getDouble(id + ".yaw"));
			
			map.put(id, loc);
			
		}
		
	}

	@Override
	public void onDisable() {
		
		for (Entry<String, Location> ent : map.entrySet()) {
			
			String id = ent.getKey();
			Location loc = ent.getValue();
			yml.config.set(id + ".world", loc.getWorld().getUID().toString());
			yml.config.set(id + ".pos", loc.toVector());
			yml.config.set(id + ".pitch", loc.getPitch());
			yml.config.set(id + ".yaw", loc.getYaw());
			
		}
		
		yml.save();
		
	}

	@Override
	public void onCommand(Player sender, String cmd, String[] args) {
		
		if (cmd.contentEquals("home")) {
			
			Location loc = null;
			
			if (args.length >= 1) {
				
				loc = map.get(args[0]);
				if (loc == null) {
					sender.sendMessage(ChatColor.DARK_GRAY + "Home for \"" + args[0] + "\" not found.");
					return;
				}
				
			}
			else {
				
				loc = map.get(sender.getDisplayName());
				if (loc == null) {
					sender.sendMessage(ChatColor.DARK_GRAY + "Home not set.");
					return;
				}
				
			}
			
			SpigCoot.plugin.teleTrack.tp(sender, loc);
			
		}
		else if (cmd.contentEquals("sethome")) {
			
			map.put(sender.getDisplayName(), sender.getLocation());
			sender.sendMessage("Home set.");
			
		}
		
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		
		List<String> list = new LinkedList<String>();
		
		if (alias.contentEquals("home") && args.length == 1) {
			
			for (String s : map.keySet()) {
				
				if (s.startsWith(args[0])) {
					list.add(s);
				}
				
			}
			
		}
		
		return list;
		
	}

}
