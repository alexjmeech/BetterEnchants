package me.alexthecoder.betterenchants.command;

import me.alexthecoder.betterenchants.BEPermission;
import me.alexthecoder.betterenchants.api.CrystalAPI;
import me.alexthecoder.betterenchants.api.EnchantAPI;
import me.alexthecoder.betterenchants.util.command.Command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCrystalCommand extends Command
{
	public GiveCrystalCommand()
	{
		super("givecrystal", new String[] {}, new String[]
			{
				"/givecrystal <Enchant> <Level> <Player> - Gives a player a crystal with the specified enchantment and level"
			},
		3, BEPermission.GIVE_CRYSTAL.getPermission(), false);
	}

	@Override
	public void run(CommandSender sender, String[] args)
	{
		String enchantName = args[0];
		Integer level;
		try
		{
			level = Integer.parseInt(args[1]);
		}
		catch(Exception e)
		{
			sender.sendMessage(ChatColor.RED + args[1] + " is not a valid number!");
			return;
		}
		if (EnchantAPI.getRegisteredEnchant(enchantName) == null)
		{
			sender.sendMessage(ChatColor.RED + "That enchantment does not exist!");
			return;
		}
		if (level < 1)
		{
			sender.sendMessage(ChatColor.RED + "The level cannot be below 1!");
			return;
		}
		if (Bukkit.getPlayer(args[2]) == null)
		{
			sender.sendMessage(ChatColor.RED + "That player is not online!");
			return;
		}
		Player t = Bukkit.getPlayer(args[2]);
		sender.sendMessage(ChatColor.GREEN + "Crystal given to " + t.getName() + "!");
		t.sendMessage(ChatColor.GREEN + "You were given a crystal by " + sender.getName() + "!");
		CrystalAPI.giveCrystal(t, EnchantAPI.getRegisteredEnchant(enchantName), level, 100);
	}
}