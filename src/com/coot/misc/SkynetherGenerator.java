package com.coot.misc;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;

public class SkynetherGenerator extends ChunkGenerator {
	
	private int intBetween(Random rand, int min, int max) {
		
		return (Math.abs(rand.nextInt()) % (max - min + 1)) + min;
		
	}
	
	/* This function is necessary because original setRegion() is ineffective */ 
	private void setRegion(ChunkData data, int x1, int y1, int z1, int x2, int y2, int z2, Material mat) {
		
		for (int x = x1; x <= x2; x++) {
			for (int y = y1; y <= y2; y++) {
				for (int z = z1; z <= z2; z++) {
					data.setBlock(x, y, z, mat);
				}
			}
		}
		
	}
	
	@Override
	public ChunkData generateChunkData(World world, Random rand, int chunkX, int chunkZ, BiomeGrid biomeGrid) {
		
		ChunkData data = createChunkData(world);
		Biome biome = biomeGrid.getBiome(0, 0, 0);
		int height = intBetween(rand, 90, 110);
		Material main = Material.NETHERRACK;
		Material alt = Material.NETHER_GOLD_ORE;
		Material tert = Material.GLOWSTONE;
		
		if (biome == Biome.NETHER_WASTES) {
			if (intBetween(rand, 1, 30) != 1) {
				return data;
			}
		}
		else if (intBetween(rand, 1, 5) != 1) {
			return data;
		}
		
		if (biome == Biome.SOUL_SAND_VALLEY) {
			main = Material.SOUL_SOIL;
			alt = Material.SOUL_SAND;
			tert = Material.NETHER_BRICKS;
		}
		else if (biome == Biome.CRIMSON_FOREST) {
			main = Material.CRIMSON_NYLIUM;
			alt = Material.NETHER_BRICKS;
			tert = Material.NETHER_WART_BLOCK;
		}
		else if (biome == Biome.WARPED_FOREST) {
			main = Material.WARPED_NYLIUM;
			alt = Material.GOLD_ORE;
			tert = Material.BLACKSTONE;
		}
		else if (biome == Biome.BASALT_DELTAS) {
			main = Material.BASALT;
			alt = Material.LAVA;
			tert = Material.BLACKSTONE;
		}
		
		setRegion(data, 7, height, 7, 9, height, 9, main);
		setRegion(data, 6, height + 1, 6, 10, height + 1, 10, main);
		setRegion(data, 5, height + 2, 5, 11, height + 2, 11, main);
		for (int x = 6; x <= 10; x++) {
			for (int z = 6; z <= 10; z++) {
				if (intBetween(rand, 1, 3) == 1) {
					data.setBlock(x, height + 2, z, alt);
				}
			}
		}
		if (intBetween(rand, 1, 3) == 1) {
			setRegion(data, 8, height + 3, 8, 8, height + 8, 8, tert);
			data.setBlock(8, height + 9, 8, Material.LAVA);
		}
		
		return data;
		
	}
	
	public World createWorld() {
		
		WorldCreator wcr = new WorldCreator("skynether");
		wcr.generator(this);
		wcr.environment(Environment.NETHER);
		World world = wcr.createWorld();
		
		return world;
		
	}
	
}
