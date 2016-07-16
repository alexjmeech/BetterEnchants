package me.alexthecoder.betterenchants.command;

import me.alexthecoder.betterenchants.BEPermission;
import me.alexthecoder.betterenchants.api.CustomEnchant;
import me.alexthecoder.betterenchants.api.EnchantAPI;
import me.alexthecoder.betterenchants.util.command.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ListEnchantsCommand extends Command
{
	public ListEnchantsCommand()
	{
		super("listenchants", new String[] {}, new String[] {}, 0, BEPermission.LIST_ENCHANTS.getPermission(), false);
	}

	@Override
	public void run(CommandSender sender, String[] args)
	{
		sender.sendMessage(ChatColor.GREEN + "Loaded Enchantments:");
		for (CustomEnchant ce : EnchantAPI.getRegisteredEnchantments())
		{
			sender.sendMessage(ce.getCategory().getColor() + ce.getName());
		}
	}
}