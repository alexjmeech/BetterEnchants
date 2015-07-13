package me.AlexTheCoder.BetterEnchants;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

public class BetterEnchantsCommand {
	
	private static boolean initialized = false;
	
	public static void init(Main plugin) {
		if(!initialized) {
			PluginCommand betterEnchants = plugin.getCommand("betterenchants");
			CommandExecutor exe;
			
			exe = new CommandExecutor() {
				public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
					sender.sendMessage(ChatColor.GREEN + "[BetterEnchants version " + Main.instance.getDescription().getVersion() + " by AlexTheCoder]");
					if(sender instanceof Player) {
						Player p = (Player)sender;
						if(p.hasPermission("BetterEnchants.command.addenchant")) p.sendMessage(ChatColor.GOLD + "/addenchant <EnchantName> <Level> - Adds a custom enchantment to the item you are holding!");
						if(p.hasPermission("BetterEnchants.command.getcrystal")) p.sendMessage(ChatColor.GOLD + "/getcrystal <EnchantName> <Level> - Gives you an enchanted crystal!");
					}
					if(sender.hasPermission("BetterEnchants.command.givecrystal")) sender.sendMessage(ChatColor.GOLD + "/givecrystal <EnchantName> <Level> <Name> - Gives a player an enchanted crystal!");
					return true;
				}
			};
			betterEnchants.setExecutor(exe);
			initialized = true;
		}
	}

}
