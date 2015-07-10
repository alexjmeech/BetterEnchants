package me.AlexTheCoder.BetterEnchants.enchant;

import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.util.MiscUtil;

import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CranialStrikeHandler {
	
	public CranialStrikeHandler(Player player, LivingEntity target, int level) {
		if(MiscUtil.willOccur(2.5 * level)) {
			if (player.getGameMode().equals(GameMode.CREATIVE)) {
				return;
			}
			if ((level == -1) || (level > EnchantAPI.getRegisteredEnchant("Cranial Strike").getMaxLevel())) {
				return;
			}
			target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, (1 + level) * 20, 1));
		}
	}

}
