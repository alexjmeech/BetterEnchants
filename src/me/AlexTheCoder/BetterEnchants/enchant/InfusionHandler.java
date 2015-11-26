package me.AlexTheCoder.BetterEnchants.enchant;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.util.BlockUtil;
import me.AlexTheCoder.BetterEnchants.util.EnchantUtil;
import me.AlexTheCoder.BetterEnchants.util.MiscUtil;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldguard.bukkit.WGBukkit;

public class InfusionHandler {
	
	private static ConcurrentHashMap<UUID, BlockFace> loggedBlockFaces = new ConcurrentHashMap<UUID, BlockFace>();
	
	public InfusionHandler(Player p, BlockBreakEvent e, int level, ItemStack i, boolean blazingTouch) {
		if (p.getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}
		if(p.isSneaking()) return;
		if ((level == -1) || (level > EnchantAPI.getRegisteredEnchant("Infusion").getMaxLevel())) {
			return;
		}
		
		List<Block> blocks = BlockUtil.getSquare(e.getBlock(), loggedBlockFaces.get(p.getUniqueId()), level);
		Material oldMaterial = e.getBlock().getType();
		
		if(blocks.isEmpty()) {
			if(blazingTouch) {
				new BlazingTouchHandler(p, e.getBlock(), EnchantUtil.getLevel(i, "Blazing Touch"), i);
				if((e.getBlock() == null) || (e.getBlock().getType() == Material.AIR))e.setCancelled(true);
			} else {
				handleNormalFunctions(p, i, e.getBlock());
				e.getBlock().breakNaturally();
			}
		}else{
			for(Block b : blocks) {
				if(b != null) {
					if (!WGBukkit.getPlugin().canBuild(p, b))
						continue;
					
					if(!b.getType().equals(Material.AIR) && !b.getType().equals(Material.BEDROCK) && !b.getType().equals(Material.STATIONARY_LAVA) && !b.getType().equals(Material.STATIONARY_WATER) && !b.getType().equals(Material.WATER) && !b.getType().equals(Material.LAVA) && areSame(oldMaterial, b.getType())) {
						if(blazingTouch) {
							new BlazingTouchHandler(p, b, EnchantUtil.getLevel(i, "Blazing Touch"), i);
							if((b == null) || (b.getType() == Material.AIR))e.setCancelled(true);
						} else {
							handleNormalFunctions(p, i, b);
							b.breakNaturally();
						}
					}
				}
			}
			blocks.clear();
			blocks = null;
		}
	}
	
	private static void handleNormalFunctions(Player p, ItemStack item, Block b) {
		MiscUtil.applyDamage(p, item);
		
		if(MiscUtil.shouldDropXp(b.getType())) {
			Integer xp = new Random().nextInt(10);
			
			if(xp > 0) {
				MiscUtil.dropExpNaturally(b.getLocation(), xp);
			}
		}
	}
	
	private static boolean areSame(Material one, Material two) {
		if(one == Material.AIR) return true;
		if(two == Material.AIR) return true;
		return one.ordinal() == two.ordinal();
	}
	
	public static void updateSavedBlockFace(Player p, BlockFace b) {
		loggedBlockFaces.put(p.getUniqueId(), b);
	}

}
