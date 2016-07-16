package me.alexthecoder.betterenchants.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import me.alexthecoder.betterenchants.BEPermission;
import me.alexthecoder.betterenchants.api.CustomEnchant;
import me.alexthecoder.betterenchants.api.EnchantAPI;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;

public class EnchantUtil
{
	public static final String EnchantStart = ChatColor.GRAY.toString();
	
	public static boolean hasEnchant(ItemStack itm, CustomEnchant enchant)
	{
		return hasEnchant(itm, enchant.getName());
	}
	
	public static boolean isCustomEnchanted(ItemStack i)
	{
		for (CustomEnchant e : EnchantAPI.getRegisteredEnchantments())
		{
			if (hasEnchant(i, e.getName()))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasEnchant(ItemStack itm, String enchant)
	{
		return getLevel(itm, enchant) >= 1;
	}
  
	public static boolean hasEnchantAndLevel(ItemStack itm, CustomEnchant enchant, int level)
	{
		return (hasEnchant(itm, enchant.getName()) && (getLevel(itm, enchant.getName()) == level));
	}
	
	public static boolean hasArmorEnchant(ItemStack[] armor, CustomEnchant enchant, int piecesNeeded)
	{
		int pieces = 0;
		ItemStack[] armors = armor;
		for (int i = 0; i < armor.length; i++)
		{
			ItemStack itm = armors[i];

			int level = EnchantUtil.getLevel(itm, enchant.getName());
			if ((level != -1) && (level <= enchant.getMaxLevel()))
			{
				pieces++;
			}
		}
		return pieces >= piecesNeeded;
	}
  
	public static int getLevel(ItemStack itm, String enchant)
	{
		if ((itm != null) && (itm.hasItemMeta()) && (itm.getItemMeta().hasLore()))
		{
			for (String lore : itm.getItemMeta().getLore())
			{
				if (lore.startsWith(EnchantStart))
				{
					List<String> split = Arrays.asList(ChatColor.stripColor(lore).split(" "));
          
					String enchantName = FormatUtil.implode(split.subList(0, split.size() - 1), "_");
					if (enchantName.equalsIgnoreCase(enchant.replaceAll(" ", "_")))
					{
						int level = getArabic(split.get(split.size() - 1));
						return level;
					}
				}
			}
		}
		
		return -1;
	}
  
	public static int getArabic(String numeral)
	{
		int level = -1;
		try
		{
			level = new RomanNumeral(numeral).toInt();
		}
		catch (Exception e) {}
		return level;
	}
	
	public static List<CustomEnchant> getEnchants(ItemStack item)
	{
		List<CustomEnchant> enchants = Lists.newArrayList();
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore()))
		{
			for (String lore : item.getItemMeta().getLore())
			{
				if (lore.startsWith(EnchantStart))
				{
					List<String> split = Arrays.asList(ChatColor.stripColor(lore).split(" "));
					
					String enchantName = FormatUtil.implode(split.subList(0, split.size() - 1), "_");
					CustomEnchant e = EnchantAPI.getRegisteredEnchant(enchantName);
					if (e != null)
					{
						enchants.add(e);
					}
				}
			}
		}
		return enchants;
	}
	
	public static boolean addEnchant(ItemStack item, CustomEnchant enchant, int level, Player p)
	{
		if (enchant == null)
		{
			return false;
		}
		ItemMeta meta = item.getItemMeta();
		if (level > enchant.getMaxLevel())
		{
			level = enchant.getMaxLevel();
		}
		if (level <= 0)
		{
			return false;
		}
		for (CustomEnchant e : getEnchants(item))
		{
			if (p != null)
			{
				if (e.willConflict(enchant) && !p.hasPermission(BEPermission.BYPASS_CONFLICTS.getPermission()))
				{
					return false;
				}
				if (enchant.willConflict(e) && !p.hasPermission(BEPermission.BYPASS_CONFLICTS.getPermission()))
				{
					return false;
				}
			}
		}
		for (Enchantment ve : item.getEnchantments().keySet())
		{
			if (p != null)
			{
				if (enchant.willConflict(ve) && !p.hasPermission(BEPermission.BYPASS_CONFLICTS.getPermission()))
				{
					return false;
				}
			}
		}
		if (!enchant.isEnchantable(item.getType()))
		{
			return false;
		}
		
		if (hasEnchant(item, enchant))
		{
			List<String> lores = Lists.newArrayList();
			for(String s : meta.getLore())
			{
				lores.add(s);
			}
			String m = EnchantStart + enchant.getCategory().getColor() + enchant.getName() + " " + new RomanNumeral(getLevel(item, enchant.getName())).toString();
			lores.remove(m);
			meta.setLore(lores);
		}
		
		List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<String>();
		lore.add(EnchantStart + enchant.getCategory().getColor() + enchant.getName() + " " + new RomanNumeral(level).toString());
		Collections.sort(lore);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return true;
	}
	
	public static int getHighestLevelofArmorEnchant(ItemStack[] armor, CustomEnchant enchant)
	{
		int level = 0;
		ItemStack[] armors = armor;
		int amount = armor.length;
		for (int i = 0; i < amount; i++)
		{
			ItemStack itm = armors[i];
			level = Math.min(Math.max(EnchantUtil.getLevel(itm, enchant.getName()), level), enchant.getMaxLevel());
		}
		return level;
	}
}