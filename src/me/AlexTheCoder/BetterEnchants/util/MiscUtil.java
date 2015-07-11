package me.AlexTheCoder.BetterEnchants.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MiscUtil {
	
	private static Material[] xpBlocks = {Material.REDSTONE_ORE, Material.COAL_ORE, Material.DIAMOND_ORE, Material.QUARTZ_ORE, Material.LAPIS_ORE, Material.EMERALD_ORE};
	
	public static void applyDamage(Player p, ItemStack itm) {
		boolean damage = true;
		if ((itm.containsEnchantment(Enchantment.DURABILITY)) && (new Random().nextInt(100) >= 100 / (itm.getEnchantmentLevel(Enchantment.DURABILITY) + 1))) {
			damage = false;
		}
		if (!damage) {
			return;
		}
		itm.setDurability((short)(itm.getDurability() + 1));
		if(itm.getDurability() > itm.getType().getMaxDurability()) p.getInventory().remove(itm);
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
		try{
			for(int i = min; i <= max; i++) {
				numbers.add(i);
			}
			
			return numbers.get(new Random().nextInt(numbers.size() + 1));
		}catch(Exception e) {
			return min;
		}
	}
	
	public static boolean willOccur(Double percentage) {
		return new Random().nextDouble() <= (percentage/100);
	}
	
	public static boolean shouldDropXp(Material m) {
		return Arrays.asList(xpBlocks).contains(m);
	}
	
	public static boolean hasOpenSlot(Inventory inv) {
		int open = 0;
		for(int i=0; i< inv.getSize(); i++){
			if(inv.getItem(i) == null){
				open = open + 1;
			}
		}
		
		if(open <= 0) {
			return false;
		}else{
			return true;
		}
	}

}
