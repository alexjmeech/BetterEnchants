package me.AlexTheCoder.BetterEnchants.util;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import me.AlexTheCoder.BetterEnchants.listener.ArmorEffectListener;

import org.bukkit.entity.Player;

public class CombatTagUtil {
	
	private static ConcurrentHashMap<UUID, Long> lastCombatTime = new ConcurrentHashMap<UUID, Long>();
	
	public static void updateCombatTime(Player p) {
		lastCombatTime.put(p.getUniqueId(), System.currentTimeMillis());
		ArmorEffectListener.updateBuffs(p);
	}
	
	public static void removePlayer(Player p) {
		lastCombatTime.remove(p.getUniqueId());
	}
	
	public static boolean hasBeenInCombat(Player p, Double duration) {
		if(!lastCombatTime.containsKey(p.getUniqueId())) return false;
		Long difference = System.currentTimeMillis() - lastCombatTime.get(p.getUniqueId());
		if(difference <= (duration * 1000)) return true;
		System.out.println(difference <= (duration * 1000));
		return false;
	}

}
