package me.alexthecoder.betterenchants.command;

import me.alexthecoder.betterenchants.BEPermission;
import me.alexthecoder.betterenchants.api.CrystalAPI;
import me.alexthecoder.betterenchants.api.EnchantAPI;
import me.alexthecoder.betterenchants.util.command.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetCrystalCommand extends Command
{
	public GetCrystalCommand()
	{
		super("getcrystal", new String[] {}, new String[]
			{
				"/getcrystal <Enchant> <Level> - Gives you a crystal with the specified enchantment and level"
			},
		2, BEPermission.GET_CRYSTAL.getPermission(), true);
	}

	@Override
	public void run(CommandSender sender, String[] args)
	{
		Player p = (Player) sender;
		String enchantName = args[0];
		Integer level;
		try
		{
			level = Integer.parseInt(args[1]);
		}
		catch(Exception e)
		{
			p.sendMessage(ChatColor.RED + args[1] + " is not a valid number!");
			return;
		}
		if (EnchantAPI.getRegisteredEnchant(enchantName) == null)
		{
			p.sendMessage(ChatColor.RED + "That enchantment does not exist!");
			return;
		}
		if (level < 1)
		{
			p.sendMessage(ChatColor.RED + "The level cannot be below 1!");
			return;
		}
		p.sendMessage(ChatColor.GREEN + "Crystal given!");
		CrystalAPI.giveCrystal(p, EnchantAPI.getRegisteredEnchant(enchantName), level, 100);
	}
}