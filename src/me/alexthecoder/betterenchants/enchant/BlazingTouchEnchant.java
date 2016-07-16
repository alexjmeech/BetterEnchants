package me.alexthecoder.betterenchants.enchant;

import java.util.Random;

import me.alexthecoder.betterenchants.api.CustomEnchant;
import me.alexthecoder.betterenchants.api.EnchantAPI;
import me.alexthecoder.betterenchants.api.EnchantmentCategory;
import me.alexthecoder.betterenchants.util.EnchantUtil;
import me.alexthecoder.betterenchants.util.MiscUtil;
import me.alexthecoder.betterenchants.util.Smeltable;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlazingTouchEnchant extends CustomEnchant
{
	public BlazingTouchEnchant()
	{
		super("Blazing Touch", EnchantmentCategory.MYTHIC, 3, "_PICKAXE", "SILK_TOUCH", "");
	}

	private void handleBlaze(Player player, Block block, ItemStack pick)
	{
		int level = EnchantUtil.getLevel(pick, getName());
		Smeltable smeltable = Smeltable.fromMaterial(block.getType());

		block.setType(Material.AIR);
		MiscUtil.applyDamage(player, pick);

		if (pick.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS))
		{
			if (pick.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 2)
			{
				int maxDrops = pick.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) + level;
				ItemStack drop = new ItemStack(smeltable.getTo(), MiscUtil.getRandomInt(1, maxDrops));
				drop.setDurability(smeltable.getToData());
				block.getWorld().dropItemNaturally(block.getLocation(), drop);
			}
		}
		else
		{
			ItemStack drop = new ItemStack(smeltable.getTo());
			drop.setDurability(smeltable.getToData());
			block.getWorld().dropItemNaturally(block.getLocation(), drop);
		}
		if (smeltable.getDropExperience() > 0)
		{
			MiscUtil.dropExpNaturally(block.getLocation(), new Random().nextInt(smeltable.getDropExperience() + 1));
		}
		player.playSound(block.getLocation(), Sound.ITEM_FIRECHARGE_USE, 1.0F, 1.0F);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event)
	{
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
		if (EnchantUtil.hasEnchant(event.getPlayer().getInventory().getItemInMainHand(), EnchantAPI.getRegisteredEnchant("Infusion")))
		{
			return;
		}
		if (Smeltable.fromMaterial(event.getBlock().getType()) == null)
		{
			return;
		}
		Player player = event.getPlayer();
		ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
		if (EnchantUtil.hasEnchant(item, this))
		{
			event.setCancelled(true);
			handleBlaze(player, event.getBlock(), item);
		}
	}
}