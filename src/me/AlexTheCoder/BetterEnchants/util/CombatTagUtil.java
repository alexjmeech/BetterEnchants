package me.alexthecoder.betterenchants.util;

import java.util.HashMap;
import java.util.UUID;

import me.alexthecoder.betterenchants.listener.ArmorEffectListener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CombatTagUtil
{
	private static final HashMap<UUID, Long> LastCombatTimes = new HashMap<>();
	public static final long ARMOR_EFFECT_REQUIRED_PEACETIME = 15000;
	
	public static void updateCombatTime(final Player p)
	{
		LastCombatTimes.put(p.getUniqueId(), System.currentTimeMillis());
		ArmorEffectListener.updateBuffs(p);
		
		Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("BetterEnchants"), new Runnable()
		{
			public void run()
			{
				ArmorEffectListener.updateBuffs(p);
			}
		}, ((ARMOR_EFFECT_REQUIRED_PEACETIME/1000) * 20) + 1);
	}
	
	public static void removePlayer(Player p)
	{
		LastCombatTimes.remove(p.getUniqueId());
	}
	
	public static boolean hasBeenInCombat(Player p, long duration)
	{
		if (!LastCombatTimes.containsKey(p.getUniqueId()))
		{
			return false;
		}
		Long difference = System.currentTimeMillis() - LastCombatTimes.get(p.getUniqueId());
		
		return difference <= duration;
	}
}