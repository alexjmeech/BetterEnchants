package me.alexthecoder.betterenchants.listener;

import java.util.List;
import java.util.Random;

import me.alexthecoder.betterenchants.api.CrystalAPI;
import me.alexthecoder.betterenchants.api.CustomEnchant;
import me.alexthecoder.betterenchants.api.EnchantAPI;
import me.alexthecoder.betterenchants.api.EnchantmentCategory;
import me.alexthecoder.betterenchants.util.CrystalUtil;
import me.alexthecoder.betterenchants.util.MiscUtil;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

public class StarEnchantListener implements Listener
{
	@EventHandler(ignoreCancelled = true)
	public void onEnchantNetherStar(PlayerInteractEvent event)
	{
		if (event.getClickedBlock() == null)
		{
			return;
		}
		if (event.getClickedBlock().getType() != Material.ENCHANTMENT_TABLE)
		{
			return;
		}
		if (event.getItem() == null)
		{
			return;
		}
		ItemStack item = event.getItem();
		Player player = event.getPlayer();
		if (item.getType() != Material.NETHER_STAR)
		{
			return;
		}
		if (CrystalUtil.isCrystal(item))
		{
			return;
		}
		List<EnchantmentCategory> available = Lists.newArrayList();
		for (EnchantmentCategory cat : EnchantmentCategory.values())
		{
			if (player.getLevel() >= cat.getXPCost())
			{
				available.add(cat);
			}
		}
		if (available.isEmpty())
		{
			player.sendMessage(ChatColor.RED + "You do not have a high enough xp level to enchant that!");
			event.setCancelled(true);
			return;
		}
		List<CustomEnchant> possible = Lists.newArrayList();
		EnchantmentCategory category = available.get(new Random().nextInt(available.size()));
		for (CustomEnchant ench : EnchantAPI.getRegisteredEnchantments())
		{
			if (ench.getCategory() == category)
			{
				possible.add(ench);
			}
		}
		if (possible.size() <= 0)
		{
			event.getPlayer().sendMessage(ChatColor.RED + "No enchantment was found!");
			event.setCancelled(true);
			return;
		}
		CustomEnchant e = possible.get(new Random().nextInt(possible.size()));
		int level = MiscUtil.getRandomInt(1, e.getMaxLevel());
		int success = new Random().nextInt(101);
		
		if (item.getAmount() > 1)
		{
			item.setAmount(item.getAmount() - 1);
		}
		else
		{
			player.getInventory().remove(item);
		}
		
		ItemStack crystal = CrystalAPI.createCrystalItem(e, level, success);
		
		if (MiscUtil.hasOpenSlot(player.getInventory()))
		{
			player.getInventory().addItem(crystal);
			player.updateInventory();
		}
		else
		{
			player.getWorld().dropItem(player.getLocation(), crystal);
		}
		player.setLevel(player.getLevel() - category.getXPCost());
		event.setCancelled(true);
	}
}