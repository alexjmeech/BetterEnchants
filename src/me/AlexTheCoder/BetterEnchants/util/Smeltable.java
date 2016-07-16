package me.alexthecoder.betterenchants.util;

import java.util.HashMap;

import org.bukkit.Material;

public enum Smeltable
{
	//Pickaxe
	COBBLESTONE(Material.COBBLESTONE, Material.STONE, 0, 0),
	IRON_ORE(Material.IRON_ORE, Material.IRON_INGOT, 4, 0),
	GOLD_ORE(Material.GOLD_ORE, Material.GOLD_INGOT, 7, 0),
	NETHERRACK(Material.NETHERRACK, Material.NETHER_BRICK_ITEM, 2, 0),
	;
  
	private static final HashMap<Material, Smeltable> MaterialToSmeltableMapping = new HashMap<>();
	private Material _from;
	private Material _to;
	private int _dropExperience;
	private int _data;
  
	private Smeltable(Material from, Material to, int dropExperience, int data)
	{
		_from = from;
		_to = to;
		_dropExperience = dropExperience;
		_data = data;
	}
  
	public Material getFrom()
	{
		return _from;
	}
  
	public Material getTo()
	{
		return _to;
	}
	
	public short getToData()
	{
		return (short) _data;
	}
  
	public int getDropExperience()
	{
		return _dropExperience;
	}
  
	public static Smeltable fromMaterial(Material mat)
	{
		if (MaterialToSmeltableMapping.isEmpty())
		{
			initMapping();
		}
		return MaterialToSmeltableMapping.get(mat);
	}

	private static void initMapping()
	{
		MaterialToSmeltableMapping.clear();
		for (Smeltable s : values())
		{
			MaterialToSmeltableMapping.put(s._from, s);
		}
	}
}