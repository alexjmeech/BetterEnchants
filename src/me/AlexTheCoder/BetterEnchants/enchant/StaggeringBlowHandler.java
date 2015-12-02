package me.AlexTheCoder.BetterEnchants.enchant;

import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.config.HandleActive;
import me.AlexTheCoder.BetterEnchants.util.MiscUtil;

import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StaggeringBlowHandler {
	
	public StaggeringBlowHandler(Player p, int level, LivingEntity target) {
		if (getProbability() == null)
			return;
		if (getDuration() == null)
			return;
		
		if(MiscUtil.willOccur(getProbability() * level)) {
			if (p.getGameMode().equals(GameMode.CREATIVE)) {
				return;
			}
			if ((level == -1) || (level > EnchantAPI.getRegisteredEnchant("Staggering Blow").getMaxLevel())) {
				return;
			}
			target.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, getDuration() * 20, level - 1));
		}
	}
	
	private Double getProbability() {
		return HandleActive.getInstance().getDouble(StockEnchant.STAGGERING_BLOW, "ProbabilityMult");
	}
	
	private Integer getDuration() {
		return HandleActive.getInstance().getInteger(StockEnchant.STAGGERING_BLOW, "Duration");
	}

}
