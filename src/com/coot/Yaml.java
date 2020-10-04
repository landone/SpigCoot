package com.coot;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Yaml {
	
	public FileConfiguration config;
	private final String EXTENSION = ".yml";
	private File file;
	
	public Yaml(String name, JavaPlugin plugin) {
		
		plugin.getDataFolder().mkdir();
		String path = plugin.getDataFolder() + File.separator + name + EXTENSION;
		file = new File(path);
		config = YamlConfiguration.loadConfiguration(file);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				plugin.getLogger().warning("Error path: " + path);
				e.printStackTrace();
			}
		}
		
	}
	
	public boolean save() {
		
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	}
	
}
