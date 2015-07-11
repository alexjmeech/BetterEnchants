package me.AlexTheCoder.BetterEnchants.API;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class CustomEnchant {
	
	private String name;
	private Integer maxlevel, baseXpLevel;
	private List<String> enchantables;
	private List<String> conflicts, vanillaconflicts;
	
	public CustomEnchant(String name, Integer maxLevel, String enchantableItems, String vanillaConflicts, String betterConflicts, int baseXpLevel) {
		this.name = name;
		this.maxlevel = maxLevel;
		this.enchantables = Arrays.asList(enchantableItems.split(" "));
		this.conflicts = Arrays.asList(betterConflicts.split(" "));
		this.vanillaconflicts = Arrays.asList(vanillaConflicts.split(" "));
		this.baseXpLevel = baseXpLevel;
	}
	
	public String getName() {
		return this.name;
	}
  
	public int getMaxLevel() {
		return this.maxlevel;
	}
	
	public boolean isEnchantable(Material mat) {
		for (String enchantable : this.enchantables) {
			if (mat.name().contains(enchantable)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean willConflict(CustomEnchant e) {
		if(this.conflicts.isEmpty()) return false;
		for(String conflicts : this.conflicts) {
			if(conflicts.contains(e.getName())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean willConflict(Enchantment e) {
		if(this.vanillaconflicts.isEmpty()) return false;
		for(String conflicts : this.vanillaconflicts) {
			if(conflicts.contains(e.getName())) {
				return true;
			}
		}
		return false;
	}
	
	public int getBaseXpLevel() {
		return this.baseXpLevel;
	}

}
