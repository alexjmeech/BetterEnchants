package me.AlexTheCoder.BetterEnchants.enchant;

import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.config.HandleActive;
import me.AlexTheCoder.BetterEnchants.util.MiscUtil;

import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CranialStrikeHandler {
	
	public CranialStrikeHandler(Player player, LivingEntity target, int level) {
		if (getProbability() == null)
			return;
		if (getBoost() == null)
			return;
		
		if(MiscUtil.willOccur(getProbability() * level)) {
			if (player.getGameMode().equals(GameMode.CREATIVE)) {
				return;
			}
			if ((level == -1) || (level > EnchantAPI.getRegisteredEnchant("Cranial Strike").getMaxLevel())) {
				return;
			}
			target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, (getBoost() + level) * 20, 1));
		}
	}
	
	private Double getProbability() {
		return HandleActive.getInstance().getDouble(StockEnchant.CRANIAL_STRIKE, "ProbabilityMult");
	}
	
	private Integer getBoost() {
		return HandleActive.getInstance().getInteger(StockEnchant.CRANIAL_STRIKE, "DurationMult");
	}
}
