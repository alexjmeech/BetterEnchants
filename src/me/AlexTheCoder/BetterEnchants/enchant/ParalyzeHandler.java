package me.AlexTheCoder.BetterEnchants.enchant;

import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.config.HandleActive;
import me.AlexTheCoder.BetterEnchants.util.MiscUtil;

import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ParalyzeHandler {
	
	public ParalyzeHandler(Player p, int level, LivingEntity target) {
		if (getProbability() == null)
			return;
		if (getBoost() == null)
			return;
		
		if(MiscUtil.willOccur(getProbability() * level)) {
			if (p.getGameMode().equals(GameMode.CREATIVE)) {
				return;
			}
			if ((level == -1) || (level > EnchantAPI.getRegisteredEnchant("Paralyze").getMaxLevel())) {
				return;
			}
			target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (getBoost() + level) * 20, 10));
			target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, (getBoost() + level) * 20, 10));
			target.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, (getBoost() + level) * 20, -10));
		}
	}
	
	private Double getProbability() {
		return HandleActive.getInstance().getDouble(StockEnchant.PARALYZE, "ProbabilityMult");
	}
	
	private Integer getBoost() {
		return HandleActive.getInstance().getInteger(StockEnchant.PARALYZE, "DurationBoost");
	}
}
