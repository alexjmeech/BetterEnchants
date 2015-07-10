package me.AlexTheCoder.BetterEnchants.enchant;

import java.util.Random;

import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.util.MiscUtil;
import me.AlexTheCoder.BetterEnchants.util.Smeltable;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BlazingTouchHandler {
	
	public BlazingTouchHandler(Player p, Block b, int level, ItemStack i) {
		if (p.getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}
		if (p.isSneaking()) {
			return;
		}
		Smeltable smeltable = Smeltable.fromMaterial(b.getType());
		if (smeltable == null) {
			b.breakNaturally();
			return;
		}
		if ((level == -1) || (level > EnchantAPI.getRegisteredEnchant("Blazing Touch").getMaxLevel())) {
			return;
		}
		
		b.setType(Material.AIR);
		MiscUtil.applyDamage(i);
		
		if(i.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
			if(i.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 2) {
				int maxDrops = i.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) + level;
				ItemStack item = new ItemStack(smeltable.getTo(), MiscUtil.getRandomInt(1, maxDrops));
				item.setDurability(smeltable.getToData());
				b.getWorld().dropItemNaturally(b.getLocation(), item);
			}
		}else{
			ItemStack item = new ItemStack(smeltable.getTo());
			item.setDurability(smeltable.getToData());
			b.getWorld().dropItemNaturally(b.getLocation(), item);
		}
		if (smeltable.getDropExperience() > 0) {
			MiscUtil.dropExpNaturally(b.getLocation(), new Random().nextInt(smeltable.getDropExperience() + 1));
		}
		p.playSound(p.getLocation(), Sound.FIRE, 1.0F, 1.0F);
	}

}
