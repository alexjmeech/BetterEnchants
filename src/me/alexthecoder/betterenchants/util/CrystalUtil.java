package me.alexthecoder.betterenchants.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.alexthecoder.betterenchants.api.CrystalAPI;
import me.alexthecoder.betterenchants.api.CustomEnchant;
import me.alexthecoder.betterenchants.api.EnchantAPI;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CrystalUtil
{	
	public static final String CrystalName = ChatColor.GOLD + "" + ChatColor.ITALIC + "Enchanted Crystal";
	
	public static boolean upgradeLevel(ItemStack crystal, CustomEnchant enchantment, int amount)
	{
		if (!isCrystal(crystal))
		{
			return false;
		}
		if (!getEnchantsFromCrystal(crystal).containsKey(enchantment))
		{
			return false;
		}
		if (getEnchantsFromCrystal(crystal).get(enchantment) + amount > enchantment.getMaxLevel())
		{
			return false;
		}
		
		int current = getEnchantsFromCrystal(crystal).get(enchantment);
		Map<CustomEnchant, Integer> enchants = getEnchantsFromCrystal(crystal);
		enchants.remove(enchantment);
		enchants.put(enchantment, current + 1);
		ItemStack c = CrystalAPI.createCrystalItem(enchants, (int)(getSuccessPercentage(crystal) * 100));
		crystal.setItemMeta(c.getItemMeta());
		return true;
	}
	
	public static boolean setSuccessPercentage(ItemStack crystal, int amount)
	{
		if (!isCrystal(crystal))
		{
			return false;
		}
		int current = (int)(getSuccessPercentage(crystal) * 100);
		if (current == amount)
		{
			return false;
		}
		if (amount > 100 || amount < 0)
		{
			return false;
		}
		
		ItemStack c = CrystalAPI.createCrystalItem(getEnchantsFromCrystal(crystal), amount);
		crystal.setItemMeta(c.getItemMeta());
		return true;
	}
	
	public static boolean isCrystal(ItemStack itm)
	{
		if (itm == null)
		{
			return false;
		}
		if (!itm.getType().equals(Material.NETHER_STAR))
		{
			return false;
		}
		if (!itm.hasItemMeta())
		{
			return false;
		}
		if (!itm.getItemMeta().hasDisplayName())
		{
			return false;
		}
		if (!itm.getItemMeta().getDisplayName().equals(CrystalName))
		{
			return false;
		}
		return true;
	}
	
	public static Double getSuccessPercentage(ItemStack item)
	{
		if (!isCrystal(item))
		{
			return 0D;
		}
		if (!item.hasItemMeta())
		{
			return 0D;
		}
		if (!item.getItemMeta().hasLore())
		{
			return 0D;
		}
		
		for (String l : item.getItemMeta().getLore())
		{
			if (!l.contains("Enchantment Success"))
			{
				continue;
			}
			
			String lo = ChatColor.stripColor(l.replace("Enchantment Success Chance: ", "").replace("%", ""));
			Integer i = Integer.parseInt(lo);
			return i/100D;
		}
		
		return 0D;
	}
	
	public static Map<CustomEnchant, Integer> getEnchantsFromCrystal(ItemStack itm)
	{
		if (!isCrystal(itm))
		{
			return null;
		}
		Map<CustomEnchant, Integer> crystalEnchants = new HashMap<>();
		if ((itm != null) && (itm.hasItemMeta()) && (itm.getItemMeta().hasLore()))
		{
			for (String lore : itm.getItemMeta().getLore())
			{
				if (lore.startsWith(EnchantUtil.EnchantStart))
				{
					List<String> split = Arrays.asList(ChatColor.stripColor(lore).split(" "));
					
					String enchantName = FormatUtil.implode(split.subList(0, split.size() - 1), "_");
					
					int level = EnchantUtil.getArabic(split.get(split.size() - 1));
					if (level != -1)
					{
						CustomEnchant enchant = EnchantAPI.getRegisteredEnchant(enchantName);
						if (enchant != null)
						{
							crystalEnchants.put(enchant, Integer.valueOf(level));
						}
					}
				}
			}
		}
		return crystalEnchants;
	}
}