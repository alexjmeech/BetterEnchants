package me.alexthecoder.betterenchants.command;

import me.alexthecoder.betterenchants.BEPermission;
import me.alexthecoder.betterenchants.util.command.Command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BetterEnchantsCommand extends Command
{
	public BetterEnchantsCommand()
	{
		super("betterenchants", new String[] {"be"}, new String[] {}, 0, BEPermission.MAIN_COMMAND.getPermission(), false);
	}

	@Override
	public void run(CommandSender sender, String[] args)
	{
		sender.sendMessage(ChatColor.GREEN + "[BetterEnchants version " + Bukkit.getPluginManager().getPlugin("BetterEnchants").getDescription().getVersion() + " by AlexTheCoder]");
		if (sender instanceof Player)
		{
			Player p = (Player)sender;
			if (p.hasPermission(BEPermission.ADD_ENCHANT.getPermission()))
				p.sendMessage(ChatColor.GOLD + "/addenchant <EnchantName> <Level> - Adds a custom enchantment to the item you are holding!");
			if (p.hasPermission(BEPermission.GET_CRYSTAL.getPermission()))
				p.sendMessage(ChatColor.GOLD + "/getcrystal <EnchantName> <Level> - Gives you an enchanted crystal!");
		}
		if (sender.hasPermission(BEPermission.LIST_ENCHANTS.getPermission()))
			sender.sendMessage(ChatColor.GOLD + "/listenchants - Lists all loaded custom enchantments!");
		if (sender.hasPermission(BEPermission.GIVE_ITEM.getPermission()))
			sender.sendMessage(ChatColor.GOLD + "/giveitem <ItemName> <Name> - Gives a player an item!");
		if (sender.hasPermission(BEPermission.GIVE_CRYSTAL.getPermission()))
			sender.sendMessage(ChatColor.GOLD + "/givecrystal <EnchantName> <Level> <Name> - Gives a player an enchanted crystal!");
	}
}