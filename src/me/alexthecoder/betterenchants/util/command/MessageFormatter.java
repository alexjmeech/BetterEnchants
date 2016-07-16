package me.alexthecoder.betterenchants.util.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class MessageFormatter
{
	public static String getSquare()
	{
		return "\u2589";
	}
	
	public static String noPermission()
	{
		return ChatColor.RED + "You do not have permission for that!";
	}
	
	public static List<String> formatHelpMessages(String label, String[] help, SubCommand[] subs)
	{
		List<String> messages = new ArrayList<String>();
		
		messages.add(ChatColor.GREEN + "Help for " + label + ":");
		for (String s : help)
		{
			messages.add(ChatColor.YELLOW + s);
		}
		for (SubCommand sc : subs)
		{
			for (String s : sc.getHelpMessages())
			{
				messages.add(ChatColor.YELLOW + s);
			}
		}
		
		return messages;
	}
	
	public static String getItemName(ItemStack i)
	{
		if (i == null || i.getType() == Material.AIR)
		{
			return "Air";
		}
		if (i.getItemMeta().hasDisplayName())
		{
			return i.getItemMeta().getDisplayName();
		}
		
		return CraftItemStack.asNMSCopy(i).getName();
	}
}