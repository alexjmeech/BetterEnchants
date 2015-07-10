package me.AlexTheCoder.BetterEnchants.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.AlexTheCoder.BetterEnchants.Main;
import me.AlexTheCoder.BetterEnchants.API.CrystalAPI;
import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.util.EnchantUtil;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {
	
	@EventHandler(ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		String fullcommand = event.getMessage();
		Player p = event.getPlayer();
		if(fullcommand.contains("/addenchant")) {
			if(p.hasPermission("BetterEnchants.command.addenchant")) {
				List<String> args = new ArrayList<String>(Arrays.asList(fullcommand.split(" ")));
				if(!args.get(0).equalsIgnoreCase("/addenchant")) return;
				args.remove("/addenchant");
				if(args.size() >= 2) {
					String enchantName = args.get(0);
					Integer level;
					try{
						level = Integer.parseInt(args.get(1));
					}catch(Exception e) {
						p.sendMessage(ChatColor.RED + args.get(1) + " is not a valid number!");
						event.setCancelled(true);
						return;
					}
					if((p.getItemInHand() == null) || (p.getItemInHand().getType() == Material.AIR)) {
						p.sendMessage(ChatColor.RED + "You are not holding an item!");
						event.setCancelled(true);
						return;
					}
					if(EnchantAPI.getRegisteredEnchant(enchantName) == null) {
						p.sendMessage(ChatColor.RED + "That enchantment does not exist!");
						event.setCancelled(true);
						return;
					}
					if(level < 1) {
						p.sendMessage(ChatColor.RED + "The level cannot be below 1!");
						event.setCancelled(true);
						return;
					}
					if(!EnchantUtil.addEnchant(event.getPlayer().getItemInHand(), EnchantAPI.getRegisteredEnchant(enchantName), level, event.getPlayer())) {
						p.sendMessage(ChatColor.RED + "Either the item you are holding is incompatible with that enchantment or an existing enchantment on the item conflicts with that enchantment!");
						event.setCancelled(true);
						return;
					}else{
						p.sendMessage(ChatColor.GREEN + "Enchantment successfully applied!");
						event.setCancelled(true);
						return;
					}
				}else{
					p.sendMessage(ChatColor.RED + "Usage: /addenchant <EnchantName> <Level>");
					event.setCancelled(true);
					return;
				}
			}
		}
		if(fullcommand.contains("/getcrystal")) {
			if(p.hasPermission("BetterEnchants.command.getcrystal")) {
				List<String> args = new ArrayList<String>(Arrays.asList(fullcommand.split(" ")));
				if(!args.get(0).equalsIgnoreCase("/getcrystal")) return;
				args.remove("/getcrystal");
				if(args.size() >= 2) {
					String enchantName = args.get(0);
					Integer level;
					try{
						level = Integer.parseInt(args.get(1));
					}catch(Exception e) {
						p.sendMessage(ChatColor.RED + args.get(1) + " is not a valid number!");
						event.setCancelled(true);
						return;
					}
					if(EnchantAPI.getRegisteredEnchant(enchantName) == null) {
						p.sendMessage(ChatColor.RED + "That enchantment does not exist!");
						event.setCancelled(true);
						return;
					}
					if(level < 1) {
						p.sendMessage(ChatColor.RED + "The level cannot be below 1!");
						event.setCancelled(true);
						return;
					}
					p.sendMessage(ChatColor.GREEN + "Enchantment successfully applied!");
					CrystalAPI.giveCrystal(event.getPlayer(), EnchantAPI.getRegisteredEnchant(enchantName), level);
					event.setCancelled(true);
					return;
				}else{
					p.sendMessage(ChatColor.RED + "Usage: /getcrystal <EnchantName> <Level>");
					event.setCancelled(true);
					return;
				}
			}
		}
		if(fullcommand.equalsIgnoreCase("/betterenchants") || fullcommand.contains("/betterenchants ")) {
			p.sendMessage(ChatColor.GREEN + "[BetterEnchants version " + Main.instance.getDescription().getVersion() + " by AlexTheCoder]");
			if(p.hasPermission("BetterEnchants.command.addenchant")) p.sendMessage(ChatColor.GOLD + "/addenchant <EnchantName> <Level> - Adds a custom enchantment to the item you are holding!");
			if(p.hasPermission("BetterEnchants.command.getcrystal")) p.sendMessage(ChatColor.GOLD + "/getcrystal <EnchantName> <Level> - Gives you an enchanted crystal!");
			event.setCancelled(true);
			return;
		}
	}

}
