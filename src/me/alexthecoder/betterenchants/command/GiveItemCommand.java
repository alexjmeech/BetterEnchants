package me.alexthecoder.betterenchants.command;

import me.alexthecoder.betterenchants.BEPermission;
import me.alexthecoder.betterenchants.api.SpecialItemAPI;
import me.alexthecoder.betterenchants.api.SpecialItemAPI.ItemType;
import me.alexthecoder.betterenchants.util.command.Command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveItemCommand extends Command
{
	public GiveItemCommand()
	{
		super("giveitem", new String[] {}, new String[]
			{
				"/giveitem <Item> <Player> - Gives a player an item"
			},
		2, BEPermission.GIVE_ITEM.getPermission(), false);
	}

	@Override
	public void run(CommandSender sender, String[] args)
	{
		String itemName = args[0];
		ItemType type = null;
		for (ItemType test : ItemType.values())
		{
			if (test.toString().equalsIgnoreCase(itemName))
				type = test;
		}
		if (type == null)
		{
			sender.sendMessage(ChatColor.RED + "That item does not exist!");
			return;
		}
		if (Bukkit.getPlayer(args[1]) == null)
		{
			sender.sendMessage(ChatColor.RED + "That player is not online!");
			return;
		}
		Player t = Bukkit.getPlayer(args[1]);
		sender.sendMessage(ChatColor.GREEN + "Item given to " + t.getName() + "!");
		t.sendMessage(ChatColor.GREEN + "You were given an item by " + sender.getName() + "!");
		SpecialItemAPI.giveItem(t, type, 1);
	}
}