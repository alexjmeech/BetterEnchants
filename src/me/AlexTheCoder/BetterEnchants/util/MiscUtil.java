package me.AlexTheCoder.BetterEnchants.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.inventory.ItemStack;

public class MiscUtil {
	
	public static void applyDamage(ItemStack itm) {
		boolean damage = true;
		if ((itm.containsEnchantment(Enchantment.DURABILITY)) && (new Random().nextInt(100) >= 100 / (itm.getEnchantmentLevel(Enchantment.DURABILITY) + 1))) {
			damage = false;
		}
		if (!damage) {
			return;
		}
		itm.setDurability((short)(itm.getDurability() + 1));
	}
	
	public static void dropExpNaturally(Location loc, int amount) {
		while (amount > 0) {
			int rand = new Random().nextInt(3) + 1;
			if (rand > amount) {
				rand = amount;
			}
			ExperienceOrb orb = (ExperienceOrb)loc.getWorld().spawnEntity(loc.add((new Random().nextBoolean() ? -1 : 1) * new Random().nextDouble(), (new Random().nextBoolean() ? -0.5D : 0.5D) * new Random().nextDouble(), (new Random().nextBoolean() ? -1 : 1) * new Random().nextDouble()), EntityType.EXPERIENCE_ORB);
			orb.setExperience(rand);
			amount -= rand;
		}
	}
	
	public static int getRandomInt(int min, int max) {
		if(min >= max) return min;
		
		List<Integer> numbers = new ArrayList<Integer>();
		for(int i = min; i <= max; i++) {
			numbers.add(i);
		}
		
		return numbers.get(new Random().nextInt(numbers.size() + 1));
	}
	
	public static boolean willOccur(Double percentage) {
		return new Random().nextDouble() <= (percentage/100);
	}

}
