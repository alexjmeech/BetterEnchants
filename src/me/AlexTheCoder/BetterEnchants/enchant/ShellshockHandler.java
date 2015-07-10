package me.AlexTheCoder.BetterEnchants.enchant;

import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.util.MiscUtil;

import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class ShellshockHandler {
	
	public ShellshockHandler(Player p, LivingEntity attacker, int level) {
		if(MiscUtil.willOccur(2.5 * level)) {
			if (p.getGameMode().equals(GameMode.CREATIVE)) {
				return;
			}
			if ((level == -1) || (level > EnchantAPI.getRegisteredEnchant("Shellshock").getMaxLevel())) {
				return;
			}
			attacker.damage((level * 2), p);
		}
	}

}
