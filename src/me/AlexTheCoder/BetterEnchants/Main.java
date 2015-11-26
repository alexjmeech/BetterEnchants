package me.AlexTheCoder.BetterEnchants;

import me.AlexTheCoder.BetterEnchants.API.CrystalAPI;
import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.crystal.CrystalEnchantListener;
import me.AlexTheCoder.BetterEnchants.listener.ArmorEffectListener;
import me.AlexTheCoder.BetterEnchants.listener.AuthorCreditListener;
import me.AlexTheCoder.BetterEnchants.listener.CombatTagListener;
import me.AlexTheCoder.BetterEnchants.listener.CrystalSafetyListener;
import me.AlexTheCoder.BetterEnchants.listener.EnchantListener;
import me.AlexTheCoder.BetterEnchants.listener.StarEnchantListener;
import me.AlexTheCoder.BetterEnchants.util.EnchantGlow;
import me.AlexTheCoder.BetterEnchants.util.EnchantUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static Main instance;
	
	public static boolean projectKorra = false;
	public static boolean worldGuard = false;
	
	@Override
	public void onEnable() {
		instance = this;
		EnchantAPI.initialize(this);
		EnchantGlow.init();
		BetterEnchantsCommand.init(this);
		getServer().getPluginManager().registerEvents(new CombatTagListener(), this);
		getServer().getPluginManager().registerEvents(new EnchantListener(), this);
		getServer().getPluginManager().registerEvents(new ArmorEffectListener(), this);
		getServer().getPluginManager().registerEvents(new CrystalSafetyListener(), this);
		getServer().getPluginManager().registerEvents(new CrystalEnchantListener(), this);
		getServer().getPluginManager().registerEvents(new AuthorCreditListener(), this);
		getServer().getPluginManager().registerEvents(new StarEnchantListener(), this);
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new ArmorEffectListener(), 0, 15);
		if(getServer().getPluginManager().isPluginEnabled("ProjectKorra")) projectKorra = true;
		if(getServer().getPluginManager().isPluginEnabled("WorldGuard")) worldGuard = true;
	}
	
	@Override
	public void onDisable() {
		EnchantAPI.disable(this);
		instance = null;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("addenchant")) {
			if (sender instanceof Player) {
				Player p = (Player)sender;
				
				if(p.hasPermission("BetterEnchants.command.addenchant")) {
					if(args.length >= 2) {
						String enchantName = args[0];
						Integer level;
						try{
							level = Integer.parseInt(args[1]);
						}catch(Exception e) {
							p.sendMessage(ChatColor.RED + args[1] + " is not a valid number!");
							return true;
						}
						if((p.getItemInHand() == null) || (p.getItemInHand().getType() == Material.AIR)) {
							p.sendMessage(ChatColor.RED + "You are not holding an item!");
							return true;
						}
						if(EnchantAPI.getRegisteredEnchant(enchantName) == null) {
							p.sendMessage(ChatColor.RED + "That enchantment does not exist!");
							return true;
						}
						if(level < 1) {
							p.sendMessage(ChatColor.RED + "The level cannot be below 1!");
							return true;
						}
						if(!EnchantUtil.addEnchant(p.getItemInHand(), EnchantAPI.getRegisteredEnchant(enchantName), level, p)) {
							p.sendMessage(ChatColor.RED + "Either the item you are holding is incompatible with that enchantment or an existing enchantment on the item conflicts with that enchantment!");
						}else{
							p.sendMessage(ChatColor.GREEN + "Enchantment successfully applied!");
						}
					}else{
						p.sendMessage(ChatColor.RED + "Usage: /addenchant <EnchantName> <Level>");
					}
				}else{
					p.sendMessage(ChatColor.RED + "You do not have permission to use that command!");
				}
			}else{
				sender.sendMessage(ChatColor.RED + "This command can only be used by a player!");
			}
			return true;
		}
		if (label.equalsIgnoreCase("getcrystal")) {
			if (sender instanceof Player) {
				Player p = (Player)sender;
				
				if(p.hasPermission("BetterEnchants.command.getcrystal")) {
					if(args.length >= 2) {
						String enchantName = args[0];
						Integer level;
						try{
							level = Integer.parseInt(args[1]);
						}catch(Exception e) {
							p.sendMessage(ChatColor.RED + args[1] + " is not a valid number!");
							return true;
						}
						if(EnchantAPI.getRegisteredEnchant(enchantName) == null) {
							p.sendMessage(ChatColor.RED + "That enchantment does not exist!");
							return true;
						}
						if(level < 1) {
							p.sendMessage(ChatColor.RED + "The level cannot be below 1!");
							return true;
						}
						p.sendMessage(ChatColor.GREEN + "Crystal given!");
						CrystalAPI.giveCrystal(p, EnchantAPI.getRegisteredEnchant(enchantName), level);
					}else{
						p.sendMessage(ChatColor.RED + "Usage: /getcrystal <EnchantName> <Level>");
					}
				}else{
					p.sendMessage(ChatColor.RED + "You do not have permission to use that command!");
				}
			}else{
				sender.sendMessage(ChatColor.RED + "This command can only be used by a player!");
			}
			return true;
		}
		if (label.equalsIgnoreCase("givecrystal")) {
			if(sender instanceof Player) {
				Player p = (Player)sender;
				if(!p.hasPermission("BetterEnchants.command.givecrystal")) {
					p.sendMessage(ChatColor.RED + "You do not have permission to use that command!");
					return true;
				}
			}
			if(args.length >= 3) {
				String enchantName = args[0];
				Integer level;
				try{
					level = Integer.parseInt(args[1]);
				}catch(Exception e) {
					sender.sendMessage(ChatColor.RED + args[1] + " is not a valid number!");
					return true;
				}
				if(EnchantAPI.getRegisteredEnchant(enchantName) == null) {
					sender.sendMessage(ChatColor.RED + "That enchantment does not exist!");
					return true;
				}
				if(level < 1) {
					sender.sendMessage(ChatColor.RED + "The level cannot be below 1!");
					return true;
				}
				if(Bukkit.getPlayer(args[2]) == null) {
					sender.sendMessage(ChatColor.RED + "That player is not online!");
					return true;
				}
				Player t = Bukkit.getPlayer(args[2]);
				sender.sendMessage(ChatColor.GREEN + "Crystal given to " + t.getName() + "!");
				t.sendMessage(ChatColor.GREEN + "You were given a crystal by " + sender.getName() + "!");
				CrystalAPI.giveCrystal(t, EnchantAPI.getRegisteredEnchant(enchantName), level);
			}else {
				sender.sendMessage(ChatColor.RED + "Usage: /givecrystal <EnchantName> <Level> <Name>");
			}
			return true;
		}
		
		return false; 
	}

}
