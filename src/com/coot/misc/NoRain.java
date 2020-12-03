package com.coot.misc;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.WeatherChangeEvent;

import com.coot.Module;
import com.coot.SpigCoot;

public class NoRain extends Module {

	public NoRain(SpigCoot plugin) {
		super(plugin);
	}

	@Override
	public void onEnable() {
		
	}

	@Override
	public void onDisable() {
		
	}

	@Override
	public void onCommand(Player sender, String cmd, String[] args) {
		
	}
	
	@EventHandler
	public void onRain(WeatherChangeEvent event) {
		
		World world = event.getWorld();
		if (!world.isClearWeather()) {
			world.setClearWeatherDuration(0);
		}
		
	}
	
	
	
}
