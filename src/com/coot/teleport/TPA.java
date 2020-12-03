package com.coot.teleport;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import com.coot.Module;
import com.coot.SpigCoot;

import net.md_5.bungee.api.ChatColor;

public class TPA extends Module implements TabCompleter{
	
	private HashMap<Player, Player> map = new HashMap<Player, Player>();

	public TPA(SpigCoot plugin) {
		super(plugin);
	}

	@Override
	public void onEnable() {
		
		commands.add("tpa");
		SpigCoot.plugin.getCommand("tpa").setTabCompleter(this);
		commands.add("tpaccept");
		
	}

	@Override
	public void onDisable() {
		
	}

	@Override
	public void onCommand(Player sender, String cmd, String[] args) {
		
		if (cmd.contentEquals("tpa")) {
			
			if (args.length != 1) {
				sender.sendMessage(ChatColor.GRAY + "Usage: /tpa {name}");
				return;
			}
			
			Player target = null;
			for (Player p : SpigCoot.plugin.getServer().getOnlinePlayers()) {
				if (p.getDisplayName().contentEquals(args[0])) {
					target = p;
					break;
				}
			}
			
			if (target == null) {
				sender.sendMessage(ChatColor.GRAY + "Player \"" + args[0] + "\" not found.");
				return;
			}
			
			if (target == sender) {
				sender.sendMessage(ChatColor.GRAY + "You may not request yourself.");
				return;
			}
			
			target.sendMessage(ChatColor.DARK_PURPLE + "Teleport request from " + ChatColor.ITALIC + sender.getDisplayName()
					+ ChatColor.RESET + ChatColor.DARK_PURPLE + ". Type /tpaccept to accept.");
			sender.sendMessage(ChatColor.DARK_PURPLE + "Teleport request sent.");
			map.put(target, sender);
			
		}
		else if (cmd.contentEquals("tpaccept")) {
			
			Player target = map.get(sender);
			if (target == null) {
				sender.sendMessage(ChatColor.DARK_GRAY + "No teleport requests found.");
				return;
			}
			
			target.sendMessage(ChatColor.DARK_PURPLE + "Teleport request " + ChatColor.BOLD + "ACCEPTED");
			sender.sendMessage(ChatColor.DARK_PURPLE + "Accepted " + ChatColor.ITALIC + target.getDisplayName() +
					ChatColor.RESET + ChatColor.DARK_PURPLE + "'s teleport request.");
			map.remove(sender);
			SpigCoot.plugin.teleTrack.tp(target, sender.getLocation());
			
		}
		
	}
	
	@EventHandler
	public void OnDisconnect(PlayerQuitEvent event) {
		
		//Forget tpa requests for players who leave
		map.remove(event.getPlayer());
		
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		
		List<String> list = new LinkedList<String>();
		
		if (args.length == 1) {
			String name = sender.getName();
			for (Player player : SpigCoot.plugin.getServer().getOnlinePlayers()) {
				if (!player.getDisplayName().contentEquals(name)) {
					if (name.startsWith(args[0])) {
						list.add(player.getDisplayName());
					}
				}
			}
		}
		
		return list;
		
	}

}
