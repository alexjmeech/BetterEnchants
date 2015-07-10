package me.AlexTheCoder.BetterEnchants.enchant;

import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.util.MiscUtil;

import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ParalyzeHandler {
	
	public ParalyzeHandler(Player p, int level, LivingEntity target) {
		if(MiscUtil.willOccur(2.5 * level)) {
			if (p.getGameMode().equals(GameMode.CREATIVE)) {
				return;
			}
			if ((level == -1) || (level > EnchantAPI.getRegisteredEnchant("Paralyze").getMaxLevel())) {
				return;
			}
			target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (1 + level) * 20, 10));
			target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, (1 + level) * 20, 10));
			target.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, (1 + level) * 20, -10));
		}
	}

}
