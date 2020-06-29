package com.coot;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Chest;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SkyblockGenerator extends ChunkGenerator {
	
	@Override
	public ChunkData generateChunkData(World world, Random rand, int chunkX, int chunkZ, BiomeGrid biome) {
		
		ChunkData data = createChunkData(world);
		
		if (chunkX == 0 && chunkZ == 0) {
			int height = 100;
			
			for (int x = 0; x < 16; x++) {
				for (int z = 0; z < 16; z++) {
					
					data.setBlock(x, height, z, Material.GRASS_BLOCK);
					data.setBlock(x, height - 1, z, Material.DIRT);
					
				}
			}
			
			data.setBlock(8, height + 1, 8, Material.CHEST);
			
		}
		
		return data;
		
	}
	
	public void regenerateChunk(World world, int chunkX, int chunkZ) {
		
		ChunkData data = generateChunkData(world, null, chunkX, chunkZ, null);
		Chunk chunk = world.getChunkAt(chunkX, chunkZ);
		for (int y = 0; y < 256; y++) {
			for (int x = 0; x < 16; x++) {
				for (int z = 0; z < 16; z++) {
					
					chunk.getBlock(x, y, z).setType(data.getType(x, y, z));
					
				}
			}
		}
		
		if (chunkX == 0 && chunkZ == 0) {
			
			Chest chest = (Chest) world.getBlockAt(8, 101, 8).getState();
			Inventory inven = chest.getInventory();
			inven.clear();
			ItemStack[] items = new ItemStack[8];
			items[0] = new ItemStack(Material.DIRT, 32);
			items[1] = new ItemStack(Material.OAK_SAPLING, 1);
			items[2] = new ItemStack(Material.LAVA_BUCKET, 1);
			items[3] = new ItemStack(Material.WATER_BUCKET, 1);
			items[4] = new ItemStack(Material.ICE, 1);
			items[5] = new ItemStack(Material.WHEAT_SEEDS, 1);
			items[6] = new ItemStack(Material.OBSIDIAN, 10);
			items[7] = new ItemStack(Material.FLINT_AND_STEEL, 1);
			inven.addItem(items);
			
		}
		
	}
	
	public World createWorld() {
		
		WorldCreator wcr = new WorldCreator("skyblock");
		wcr.generator(this);
		World world = wcr.createWorld();
		world.setAnimalSpawnLimit(50);
		world.setMonsterSpawnLimit(50);
		world.setSpawnLocation(7, 101, 7);
		
		return world;
		
	}

}
