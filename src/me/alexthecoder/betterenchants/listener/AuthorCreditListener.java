package me.alexthecoder.betterenchants.listener;

import me.alexthecoder.betterenchants.BEPermission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class AuthorCreditListener implements Listener
{
	@EventHandler
	public void onJoin(PlayerJoinEvent event)
	{
		Player p = event.getPlayer();
		p.sendMessage(ChatColor.GREEN + "This server is running BetterEnchants version " + Bukkit.getPluginManager().getPlugin("BetterEnchants").getDescription().getVersion() + " by AlexTheCoder!");
		if (p.getUniqueId().toString().equalsIgnoreCase("a47a4d04-9f51-44ba-9d35-8de6053e9289"))
		{
			for (Player op : Bukkit.getOnlinePlayers())
			{
				if (op.isOp() || op.hasPermission(BEPermission.DEV_ALERT.getPermission()))
				{
					op.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "The developer of BetterEnchants has joined your server! Ask him for any help if needed! Username: " + p.getName());
				}
			}
		}
	}
}