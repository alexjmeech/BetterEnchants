package me.alexthecoder.betterenchants.enchant;

import java.util.List;
import java.util.Random;

import me.alexthecoder.betterenchants.api.CustomEnchant;
import me.alexthecoder.betterenchants.api.EnchantmentCategory;
import me.alexthecoder.betterenchants.api.InfusionBlockBreakEvent;
import me.alexthecoder.betterenchants.config.HandleActive;
import me.alexthecoder.betterenchants.util.BlockUtil;
import me.alexthecoder.betterenchants.util.EnchantUtil;
import me.alexthecoder.betterenchants.util.MiscUtil;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.sk89q.worldguard.bukkit.WGBukkit;

public class InfusionEnchant extends CustomEnchant
{
	private static final Integer LEVEL_MULT = HandleActive.getInstance().getInteger(StockEnchant.INFUSION, "LevelMult");
	
	public InfusionEnchant()
	{
		super("Infusion", EnchantmentCategory.MYTHIC, 3, "_PICKAXE _SPADE", "SILK_TOUCH", "");
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event)
	{
		if (event instanceof InfusionBlockBreakEvent)
		{
			return;
		}
		if (event.getPlayer().getInventory().getItemInMainHand() == null || event.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR)
		{
			return;
		}
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE)
		{
			return;
		}
		if (event.getPlayer().isSneaking())
		{
			return;
		}
		if (!event.getPlayer().hasMetadata("LAST_BLOCK_FACE_INTERACT"))
		{
			return;
		}
		Player player = event.getPlayer();
		if (EnchantUtil.hasEnchant(player.getInventory().getItemInMainHand(), this))
		{
			event.setCancelled(true);
			int level = EnchantUtil.getLevel(player.getInventory().getItemInMainHand(), getName());
			BlockFace face = BlockFace.valueOf(player.getMetadata("LAST_BLOCK_FACE_INTERACT").get(0).asString());
			
			List<Block> blocks = BlockUtil.getSquare(event.getBlock(), face, level * LEVEL_MULT);
			if (blocks.isEmpty())
			{
				blocks.add(event.getBlock());
			}
			for (Block block : blocks)
			{
				if (block == null)
				{
					continue;
				}
				
				if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard") && !WGBukkit.getPlugin().canBuild(player, block))
				{
					continue;
				}
				
				if (block.getType() != Material.AIR && block.getType() != Material.BEDROCK && block.getType() != Material.STATIONARY_LAVA && block.getType() != Material.STATIONARY_WATER && block.getType() != Material.WATER && block.getType() != Material.LAVA && areSame(event.getBlock().getType(), block.getType()))
				{
					InfusionBlockBreakEvent iEvent = new InfusionBlockBreakEvent(player, block, player.getInventory().getItemInMainHand());
					Bukkit.getPluginManager().callEvent(iEvent);
					if (iEvent.isCancelled())
					{
						return;
					}
					handleNormalFunctions(player, player.getInventory().getItemInMainHand(), block);
					block.breakNaturally();
				}
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onTagBlockFace(PlayerInteractEvent event)
	{
		if (!event.hasBlock())
		{
			return;
		}
		if (!event.hasItem())
		{
			return;
		}
		if (!EnchantUtil.hasEnchant(event.getItem(), this))
		{
			return;
		}
		if (event.getPlayer().isSneaking() || event.getPlayer().getGameMode() == GameMode.CREATIVE)
		{
			return;
		}
		if (event.getAction() != Action.LEFT_CLICK_BLOCK)
		{
			return;
		}
		
		event.getPlayer().removeMetadata("LAST_BLOCK_FACE_INTERACT", Bukkit.getPluginManager().getPlugin("BetterEnchants"));
		event.getPlayer().setMetadata("LAST_BLOCK_FACE_INTERACT", new FixedMetadataValue(Bukkit.getPluginManager().getPlugin("BetterEnchants"), event.getBlockFace().toString()));
	}
	
	private void handleNormalFunctions(Player p, ItemStack item, Block b)
	{
		MiscUtil.applyDamage(p, item);
		
		if (MiscUtil.shouldDropXp(b.getType()))
		{
			Integer xp = new Random().nextInt(10);
			
			if(xp > 0)
			{
				MiscUtil.dropExpNaturally(b.getLocation(), xp);
			}
		}
	}
	
	private static boolean areSame(Material one, Material two)
	{
		if (one == Material.AIR || two == Material.AIR)
		{
			return true;
		}
		
		return one == two;
	}
}