package me.alexthecoder.betterenchants.api;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import me.alexthecoder.betterenchants.util.CrystalUtil;
import me.alexthecoder.betterenchants.util.EnchantUtil;
import me.alexthecoder.betterenchants.util.FormatUtil;
import me.alexthecoder.betterenchants.util.RomanNumeral;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;

public class CrystalAPI
{
	public static void giveCrystal(Player p, CustomEnchant enchant, int level, int successPercent)
	{
		ItemStack crystal = createCrystalItem(enchant, level, successPercent);
		p.getInventory().addItem(crystal);
		p.updateInventory();
	}

	public static void giveCrystal(Player p, Map<CustomEnchant, Integer> enchants, int successPercent)
	{
		ItemStack crystal = createCrystalItem(enchants, successPercent);
		p.getInventory().addItem(crystal);
		p.updateInventory();
	}

	public static ItemStack createCrystalItem(CustomEnchant enchant, int level, int successPercent)
	{
		ItemStack itm = new ItemStack(Material.NETHER_STAR);
		ItemMeta meta = itm.getItemMeta();
		meta.setDisplayName(getCrystalName());
		List<String> lore = Lists.newArrayList();
		level = Math.max(1, Math.min(enchant.getMaxLevel(), level));
		lore.add(FormatUtil.format(EnchantUtil.EnchantStart + enchant.getCategory().getColor() + enchant.getName() + " " + new RomanNumeral(level).toString(), new Object[0]));
		Collections.sort(lore);
		lore.add(ChatColor.GOLD + "Enchantment Success Chance: " + successPercent + "%");
		meta.setLore(lore);
		itm.setItemMeta(meta);
		return itm;
	}

	public static ItemStack createCrystalItem(Map<CustomEnchant, Integer> enchants, int successPercent)
	{
		ItemStack itm = new ItemStack(Material.NETHER_STAR);
		ItemMeta meta = itm.getItemMeta();
		meta.setDisplayName(getCrystalName());
		List<String> lore = Lists.newArrayList();
		for (CustomEnchant enchant : enchants.keySet())
		{
			int level = Math.max(1, Math.min(enchant.getMaxLevel(), enchants.get(enchant)));
			lore.add(FormatUtil.format(EnchantUtil.EnchantStart + enchant.getCategory().getColor() + enchant.getName() + " " + new RomanNumeral(level).toString(), new Object[0]));
		}
		Collections.sort(lore);
		lore.add(ChatColor.GOLD + "Enchantment Success Chance: " + successPercent + "%");
		meta.setLore(lore);
		itm.setItemMeta(meta);
		return itm;
	}

	public static String getCrystalName()
	{
		return CrystalUtil.CrystalName;
	}
}