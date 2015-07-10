package me.AlexTheCoder.BetterEnchants.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.AlexTheCoder.BetterEnchants.API.CustomEnchant;
import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CrystalUtil {
	
	public static String crystalName = ChatColor.GOLD + "" + ChatColor.ITALIC + "Enchanted Crystal";
	
	public static boolean isCrystal(ItemStack itm) {
		if (itm == null) {
			return false;
		}
		if (!itm.getType().equals(Material.NETHER_STAR)) {
			return false;
		}
		if (!itm.hasItemMeta()) {
			return false;
		}
		if (!itm.getItemMeta().hasDisplayName()) {
			return false;
		}
		if (!itm.getItemMeta().getDisplayName().equals(crystalName)) {
			return false;
		}
		return true;
	}
	
	public static Map<CustomEnchant, Integer> getEnchantsFromCrystal(ItemStack itm) {
		if (!isCrystal(itm)) {
			return null;
		}
		Map<CustomEnchant, Integer> crystalEnchants = new HashMap<CustomEnchant, Integer>();
		if ((itm != null) && (itm.hasItemMeta()) && (itm.getItemMeta().hasLore())) {
			for (String lore : itm.getItemMeta().getLore()) {
				if (lore.startsWith(EnchantUtil.enchantStart)) {
					List<String> split = Arrays.asList(ChatColor.stripColor(lore).split(" "));
					
					String enchantName = FormatUtil.implode(split.subList(0, split.size() - 1), "_");
					
					int level = EnchantUtil.getLevel((String)split.get(split.size() - 1));
					if (level != -1) {
						CustomEnchant enchant = EnchantAPI.getRegisteredEnchant(enchantName);
						if (enchant != null) {
							crystalEnchants.put(enchant, Integer.valueOf(level));
						}
					}
				}
			}
		}
		return crystalEnchants;
	}

}
