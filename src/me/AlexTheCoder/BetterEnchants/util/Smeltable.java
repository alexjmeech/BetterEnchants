package me.AlexTheCoder.BetterEnchants.util;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Material;

public enum Smeltable{
	//Pickaxe
	COBBLESTONE(Material.COBBLESTONE, Material.STONE, 0, 0),
	IRON_ORE(Material.IRON_ORE, Material.IRON_INGOT, 4, 0),
	GOLD_INGOT(Material.GOLD_ORE, Material.GOLD_INGOT, 7, 0),
	NETHERRACK(Material.NETHERRACK, Material.NETHER_BRICK_ITEM, 2, 0),
	;
  
	private static ConcurrentHashMap<Material, Smeltable> materialToSmeltableMapping;
	private Material from;
	private Material to;
	private int dropExperience;
	private int data;
  
	private Smeltable(Material from, Material to, int dropExperience, int data) {
		this.from = from;
		this.to = to;
		this.dropExperience = dropExperience;
		this.data = data;
	}
  
	public Material getFrom() {
		return this.from;
	}
  
	public Material getTo() {
		return this.to;
	}
	
	public short getToData() {
		return (short) this.data;
	}
  
	public int getDropExperience() {
		return this.dropExperience;
	}
  
	public static Smeltable fromMaterial(Material mat) {
		if (materialToSmeltableMapping == null) {
			initMapping();
		}
		return (Smeltable)materialToSmeltableMapping.get(mat);
	}

	private static void initMapping() {
		materialToSmeltableMapping = new ConcurrentHashMap<Material, Smeltable>();
		for (Smeltable s : values()) {
			materialToSmeltableMapping.put(s.from, s);
		}
	}
}