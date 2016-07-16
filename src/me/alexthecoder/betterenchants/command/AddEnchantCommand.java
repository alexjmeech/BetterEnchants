package me.alexthecoder.betterenchants.command;

import me.alexthecoder.betterenchants.BEPermission;
import me.alexthecoder.betterenchants.api.EnchantAPI;
import me.alexthecoder.betterenchants.util.EnchantUtil;
import me.alexthecoder.betterenchants.util.command.Command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddEnchantCommand extends Command
{
	public AddEnchantCommand()
	{
		super("addenchant", new String[] {}, new String[]
			{
				"/addenchant <Enchant> <Level> - Adds an enchantment to the item you are holding"
			},
		2, BEPermission.ADD_ENCHANT.getPermission(), true);
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
		if (p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType() == Material.AIR)
		{
			p.sendMessage(ChatColor.RED + "You are not holding an item!");
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
		if (!EnchantUtil.addEnchant(p.getInventory().getItemInMainHand(), EnchantAPI.getRegisteredEnchant(enchantName), level, p))
		{
			p.sendMessage(ChatColor.RED + "Either the item you are holding is incompatible with that enchantment or an existing enchantment on the item conflicts with that enchantment!");
		}
		else
		{
			p.sendMessage(ChatColor.GREEN + "Enchantment successfully applied!");
		}
	}
}