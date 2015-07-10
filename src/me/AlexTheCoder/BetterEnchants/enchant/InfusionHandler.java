package me.AlexTheCoder.BetterEnchants.enchant;

import java.util.List;
import java.util.Random;

import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.util.BlockUtil;
import me.AlexTheCoder.BetterEnchants.util.EnchantUtil;
import me.AlexTheCoder.BetterEnchants.util.MiscUtil;
import me.AlexTheCoder.BetterEnchants.util.YawUtil;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class InfusionHandler {
	
	public InfusionHandler(Player p, BlockBreakEvent e, int level, ItemStack i, boolean blazingTouch) {
		if (p.getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}
		if(p.isSneaking()) return;
		if ((level == -1) || (level > EnchantAPI.getRegisteredEnchant("Infusion").getMaxLevel())) {
			return;
		}
		
		List<Block> blocks = BlockUtil.getSquare(e.getBlock(), YawUtil.yawToDirection(p.getLocation().getYaw(), true));
		if(blazingTouch) e.setCancelled(true);
		
		if(blocks.isEmpty()) {
			if(blazingTouch) {
				new BlazingTouchHandler(p, e.getBlock(), EnchantUtil.getLevel(i, "Blazing Touch"), i);
				e.setCancelled(true);
			} else {
				e.getBlock().breakNaturally();
			}
		}else{
			for(Block b : blocks) {
				if(b != null) {
					if(!b.getType().equals(Material.AIR) && !b.getType().equals(Material.BEDROCK) && !b.getType().equals(Material.STATIONARY_LAVA) && !b.getType().equals(Material.STATIONARY_WATER) && !b.getType().equals(Material.WATER) && !b.getType().equals(Material.LAVA)) {
						if(blazingTouch) {
							new BlazingTouchHandler(p, b, EnchantUtil.getLevel(i, "Blazing Touch"), i);
							e.setCancelled(true);
						} else {
							b.breakNaturally();
						}
					}
				}
			}
		}
		
		MiscUtil.applyDamage(i);
		
		Integer xp = new Random().nextInt(10);
		
		if(xp > 0) {
			MiscUtil.dropExpNaturally(e.getBlock().getLocation(), xp);
		}
		
	}

}
