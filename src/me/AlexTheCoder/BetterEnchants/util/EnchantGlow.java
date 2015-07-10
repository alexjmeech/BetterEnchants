package me.AlexTheCoder.BetterEnchants.util;

import java.lang.reflect.Field;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;

public class EnchantGlow extends EnchantmentWrapper {
	private static Enchantment glow;

	public EnchantGlow(int id) {
		super(id);
	}

	@Override
	public boolean canEnchantItem(ItemStack item) {
		return true;
	}

	@Override
	public boolean conflictsWith(Enchantment other) {
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		return null;
	}

	@Override
	public int getMaxLevel() {
		return 10;
	}

	@Override
	public String getName() {
		return "Glow";
	}

	@Override
	public int getStartLevel() {
		return 1;
	}
	
	public static void init() {
		if(glow != null) return;
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		glow = new EnchantGlow(367);
		try {
			Enchantment.registerEnchantment(glow);
		} catch (IllegalArgumentException e) {
			//e.printStackTrace();
		}
	}

	public static Enchantment getGlow() {
		if (glow != null) {
			return glow;
		}
		
		init();

		return glow;
	}

	public static void addGlow(ItemStack item) {
		Enchantment glow = getGlow();
		if(!item.containsEnchantment(glow))
			item.addEnchantment(glow, 1);
	}
}