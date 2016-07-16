package me.alexthecoder.betterenchants.api;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;

public abstract class CustomEnchant implements Listener
{
	private String _name;
	private EnchantmentCategory _category;
	private int _maxLevel;
	private List<String> _enchantables;
	private List<String> _conflicts, _vanillaconflicts;
	
	public CustomEnchant(String name, EnchantmentCategory category, int maxLevel, String enchantableItems, String vanillaConflicts, String betterConflicts)
	{
		_name = name;
		_category = category;
		_maxLevel = maxLevel;
		_enchantables = Arrays.asList(enchantableItems.split(" "));
		_conflicts = Arrays.asList(betterConflicts.split(" "));
		_vanillaconflicts = Arrays.asList(vanillaConflicts.split(" "));
		Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("BetterEnchants"));
	}
	
	public String getName()
	{
		return _name;
	}
	
	public EnchantmentCategory getCategory()
	{
		return _category;
	}
  
	public int getMaxLevel()
	{
		return _maxLevel;
	}
	
	public boolean isEnchantable(Material mat)
	{
		for (String enchantable : _enchantables)
		{
			if (mat.name().contains(enchantable))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean willConflict(CustomEnchant e)
	{
		if (_conflicts.isEmpty())
			return false;
		for (String conflicts : _conflicts)
		{
			if (conflicts.contains(e.getName()))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean willConflict(Enchantment e)
	{
		if (_vanillaconflicts.isEmpty())
			return false;
		for (String conflicts : _vanillaconflicts)
		{
			if (conflicts.contains(e.getName()))
			{
				return true;
			}
		}
		return false;
	}
}