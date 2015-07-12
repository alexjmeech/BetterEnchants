package me.AlexTheCoder.BetterEnchants.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.AlexTheCoder.BetterEnchants.Main;
import me.AlexTheCoder.BetterEnchants.API.CrystalAPI;
import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.util.EnchantUtil;

import org.bukkit.Bukkit;
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
		List<String> args = new ArrayList<String>(Arrays.asList(fullcommand.split(" ")));
		if(args.get(0).equalsIgnoreCase("/addenchant")) {
			if(p.hasPermission("BetterEnchants.command.addenchant")) {
				args.remove(args.get(0));
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
					if(!EnchantUtil.addEnchant(p.getItemInHand(), EnchantAPI.getRegisteredEnchant(enchantName), level, p)) {
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
		if(args.get(0).equalsIgnoreCase("/getcrystal")) {
			if(p.hasPermission("BetterEnchants.command.getcrystal")) {
				args.remove(args.get(0));
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
					p.sendMessage(ChatColor.GREEN + "Crystal given!");
					CrystalAPI.giveCrystal(p, EnchantAPI.getRegisteredEnchant(enchantName), level);
					event.setCancelled(true);
					return;
				}else{
					p.sendMessage(ChatColor.RED + "Usage: /getcrystal <EnchantName> <Level>");
					event.setCancelled(true);
					return;
				}
			}
		}
		if(args.get(0).equalsIgnoreCase("/givecrystal")) {
			if(p.hasPermission("BetterEnchants.command.givecrystal")) {
				args.remove(args.get(0));
				if(args.size() >= 3) {
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
					if(Bukkit.getPlayer(args.get(2)) == null) {
						p.sendMessage(ChatColor.RED + "That player is not online!");
						event.setCancelled(true);
						return;
					}
					Player t = Bukkit.getPlayer(args.get(2));
					p.sendMessage(ChatColor.GREEN + "Crystal given to " + t.getName() + "!");
					t.sendMessage(ChatColor.GREEN + "You were given a crystal by " + p.getName() + "!");
					CrystalAPI.giveCrystal(t, EnchantAPI.getRegisteredEnchant(enchantName), level);
					event.setCancelled(true);
					return;
				}else{
					p.sendMessage(ChatColor.RED + "Usage: /givecrystal <EnchantName> <Level> <Name>");
					event.setCancelled(true);
					return;
				}
			}
		}
		if(args.get(0).equalsIgnoreCase("/betterenchants") || args.get(0).equalsIgnoreCase("/be")) {
			p.sendMessage(ChatColor.GREEN + "[BetterEnchants version " + Main.instance.getDescription().getVersion() + " by AlexTheCoder]");
			if(p.hasPermission("BetterEnchants.command.addenchant")) p.sendMessage(ChatColor.GOLD + "/addenchant <EnchantName> <Level> - Adds a custom enchantment to the item you are holding!");
			if(p.hasPermission("BetterEnchants.command.getcrystal")) p.sendMessage(ChatColor.GOLD + "/getcrystal <EnchantName> <Level> - Gives you an enchanted crystal!");
			if(p.hasPermission("BetterEnchants.command.givecrystal")) p.sendMessage(ChatColor.GOLD + "/givecrystal <EnchantName> <Level> <Name> - Gives a player an enchanted crystal!");
			event.setCancelled(true);
			return;
		}
	}

}
