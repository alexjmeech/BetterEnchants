package me.AlexTheCoder.BetterEnchants.API;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import me.AlexTheCoder.BetterEnchants.util.CrystalUtil;
import me.AlexTheCoder.BetterEnchants.util.EnchantUtil;
import me.AlexTheCoder.BetterEnchants.util.FormatUtil;
import me.AlexTheCoder.BetterEnchants.util.RomanNumeral;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CrystalAPI {
	
	public static void giveCrystal(Player p, CustomEnchant enchant, int level) {
		ItemStack crystal = createCrystalItem(enchant, level);
	    p.getInventory().addItem(crystal);
	    p.updateInventory();
	}
	
	public static void giveCrystal(Player p, Map<CustomEnchant, Integer> enchants) {
		ItemStack crystal = createCrystalItem(enchants);
		p.getInventory().addItem(crystal);
		p.updateInventory();
	}
	
	public static ItemStack createCrystalItem(CustomEnchant enchant, int level) {
		ItemStack itm = new ItemStack(Material.NETHER_STAR);
		ItemMeta meta = itm.getItemMeta();
		meta.setDisplayName(getCrystalName());
	    List<String> lore = new ArrayList<String>();
	    if(level > enchant.getMaxLevel()) level = enchant.getMaxLevel();
	    lore.add(FormatUtil.format(EnchantUtil.enchantStart + enchant.getName() + " " + new RomanNumeral(level).toString(), new Object[0]));
	    Collections.sort(lore);
	    meta.setLore(lore);
	    itm.setItemMeta(meta);
	    return itm;
	}
	
	public static ItemStack createCrystalItem(Map<CustomEnchant, Integer> enchants) {
		ItemStack itm = new ItemStack(Material.NETHER_STAR);
		ItemMeta meta = itm.getItemMeta();
		meta.setDisplayName(getCrystalName());
	    List<String> lore = new ArrayList<String>();
	    for(CustomEnchant enchant : enchants.keySet()) {
	    	int level = enchants.get(enchant);
	    	if(level > enchant.getMaxLevel()) level = enchant.getMaxLevel();
	    	lore.add(FormatUtil.format(EnchantUtil.enchantStart + enchant.getName() + " " + new RomanNumeral(level).toString(), new Object[0]));
	    }
	    Collections.sort(lore);
	    meta.setLore(lore);
	    itm.setItemMeta(meta);
	    return itm;
	}
	
	public static String getCrystalName() {
		return CrystalUtil.crystalName;
	}

}
