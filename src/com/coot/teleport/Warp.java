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
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.util.Vector;

import com.coot.Module;
import com.coot.SpigCoot;
import com.coot.Yaml;

public class Warp extends Module implements TabCompleter {
	
	private final String FILE_NAME = "warps";
	
	private Yaml yml;
	private HashMap<String, Location> map = new HashMap<String, Location>();

	public Warp(SpigCoot plugin) {
		super(plugin);
	}

	@Override
	public void onEnable() {
		
		commands.add("warp");
		commands.add("setwarp");
		commands.add("remwarp");
		for (String s : commands) {
			SpigCoot.plugin.getCommand(s).setTabCompleter(this);
		}
		yml = new Yaml(FILE_NAME, SpigCoot.plugin);
		
		//Load warps from file
		for (String str : yml.config.getKeys(false)) {
			
			UUID worldID = UUID.fromString(yml.config.getString(str + ".world"));
			World world = SpigCoot.plugin.getServer().getWorld(worldID);
			Vector pos = yml.config.getVector(str + ".pos");
			Location loc = new Location(world, pos.getX(), pos.getY(), pos.getZ());
			loc.setPitch((float)yml.config.getDouble(str + ".pitch"));
			loc.setYaw((float)yml.config.getDouble(str + ".yaw"));
			
			map.put(str, loc);
			
		}
		
	}

	@Override
	public void onDisable() {
		
		//Save warps to file
		for (Entry<String, Location> ent : map.entrySet()) {
			
			String str = ent.getKey();
			Location loc = ent.getValue();
			yml.config.set(str + ".world", loc.getWorld().getUID().toString());
			yml.config.set(str + ".pos", loc.toVector());
			yml.config.set(str + ".pitch", loc.getPitch());
			yml.config.set(str + ".yaw", loc.getYaw());
			
		}
		yml.save();
		
	}

	@Override
	public void onCommand(Player sender, String cmd, String[] args) {
		
		if (cmd.contentEquals("setwarp")) {
			
			if (!sender.isOp()) {
				sender.sendMessage(ChatColor.DARK_GRAY + "You do not have permission to set a warp.");
				return;
			}
			
			if (args.length != 1) {
				sender.sendMessage(ChatColor.DARK_GRAY + "Usage: /setwarp {name}");
				return;
			}
			
			map.put(args[0], sender.getLocation());
			sender.sendMessage(ChatColor.GREEN + "Warp created");
			
		}
		else if (cmd.contentEquals("warp")){
			
			if (args.length != 1) {
				sender.sendMessage(ChatColor.DARK_GRAY + "Usage: /warp {name}");
				return;
			}
			
			Location loc = map.get(args[0]);
			if (loc != null) {
				sender.teleport(loc, TeleportCause.PLUGIN);
			}
			else {
				sender.sendMessage(ChatColor.DARK_GRAY + "Warp \"" + args[0] + "\" not found.");
			}
			
		}
		else if (cmd.contentEquals("remwarp")) {
			
			if (args.length != 1) {
				sender.sendMessage(ChatColor.DARK_GRAY + "Usage /remwarp {name}");
				return;
			}
			
			if (map.get(args[0]) != null) {
				yml.config.set(args[0], null);
				map.remove(args[0]);
				sender.sendMessage(ChatColor.GREEN + "Warp \"" + ChatColor.DARK_GREEN + args[0] + ChatColor.GREEN + "\" removed.");
			}
			else {
				sender.sendMessage(ChatColor.DARK_GRAY + "Warp \"" + args[0] + "\" not found.");
			}
			
		}
		
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		
		List<String> list = new LinkedList<String>();
		
		if (args.length == 1) {
			
			for (String str : map.keySet()) {
				if (str.startsWith(args[0])) {
					list.add(str);
				}
			}
			
		}
		
		return list;
		
	}

}
