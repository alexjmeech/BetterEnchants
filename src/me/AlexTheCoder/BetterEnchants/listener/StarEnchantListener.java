package me.AlexTheCoder.BetterEnchants.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.AlexTheCoder.BetterEnchants.API.CrystalAPI;
import me.AlexTheCoder.BetterEnchants.API.CustomEnchant;
import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.util.CrystalUtil;
import me.AlexTheCoder.BetterEnchants.util.MiscUtil;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class StarEnchantListener implements Listener {
	
	@EventHandler(ignoreCancelled = true)
	public void onEnchantNetherStar(PlayerInteractEvent event) {
		if(event.getClickedBlock() == null) return;
		if(event.getClickedBlock().getType() != Material.ENCHANTMENT_TABLE) return;
		if(event.getItem() == null) return;
		ItemStack item = event.getItem();
		Player player = event.getPlayer();
		if(item.getType() != Material.NETHER_STAR) return;
		if(CrystalUtil.isCrystal(item)) return;
		if(player.getLevel() < 30) {
			player.sendMessage(ChatColor.RED + "You require at least 30 levels to attempt an enchant!");
			event.setCancelled(true);
			return;
		}
		List<CustomEnchant> enchants = new ArrayList<CustomEnchant>();
		for(CustomEnchant e : EnchantAPI.getRegisteredEnchantments()) {
			if(player.getLevel() >= e.getBaseXpLevel() * e.getMaxLevel()) enchants.add(e);
		}
		if(enchants.size() <= 0) {
			event.getPlayer().sendMessage(ChatColor.RED + "There are no registered enchantments!");
			event.setCancelled(true);
			return;
		}
		int i = new Random().nextInt(enchants.size() + 1);
		CustomEnchant e = enchants.get(i);
		int level = MiscUtil.getRandomInt(1, e.getMaxLevel());
		int xpCost = e.getBaseXpLevel() * level;
		
		if(item.getAmount() > 1) {
			item.setAmount(item.getAmount() - 1);
		}else{
			player.getInventory().remove(item);
		}
		
		ItemStack crystal = CrystalAPI.createCrystalItem(e, level);
		
		if(MiscUtil.hasOpenSlot(player.getInventory())) {
			player.getInventory().addItem(crystal);
			player.updateInventory();
		}else{
			player.getWorld().dropItem(player.getLocation(), crystal);
		}
		player.setLevel(player.getLevel() - xpCost);
		event.setCancelled(true);
	}

}
