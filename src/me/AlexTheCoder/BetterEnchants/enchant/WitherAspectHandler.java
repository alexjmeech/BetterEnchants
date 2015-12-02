package me.AlexTheCoder.BetterEnchants.enchant;

import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.config.HandleActive;
import me.AlexTheCoder.BetterEnchants.util.EnchantUtil;
import me.AlexTheCoder.BetterEnchants.util.MiscUtil;

import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WitherAspectHandler {
	
	public WitherAspectHandler(Player p, int level, LivingEntity target) {
		if (getProbability() == null)
			return;
		if ((getValue(true) == null) || (getValue(false) == null))
			return;
		if (AntitoxinHandler.getValue() == null)
			return;
		
		if(MiscUtil.willOccur(getProbability() * level)) {
			if (p.getGameMode().equals(GameMode.CREATIVE)) {
				return;
			}
			if ((level == -1) || (level > EnchantAPI.getRegisteredEnchant("Wither Aspect").getMaxLevel())) {
				return;
			}
			if(target instanceof Player) {
				Player t = (Player)target;
				int antitoxinLevel = EnchantUtil.getHighestLevelofArmorEnchant(t.getInventory().getArmorContents(), EnchantAPI.getRegisteredEnchant("Antitoxin"));
				if(antitoxinLevel > 0) {
					t.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, AntitoxinHandler.getNewDuration((getValue(true) + level) * 20, antitoxinLevel), getValue(false)));
				}else{
					t.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (getValue(true) + level) * 20, getValue(false)));
				}
			}else{
				target.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (getValue(true) + level) * 20, getValue(false)));
			}
		}
	}
	
	private Double getProbability() {
		return HandleActive.getInstance().getDouble(StockEnchant.WITHER_ASPECT, "ProbabilityMult");
	}
	
	private Integer getValue(boolean duration) {
		if (duration) {
			return HandleActive.getInstance().getInteger(StockEnchant.WITHER_ASPECT, "DurationBoost");
		}
		
		return HandleActive.getInstance().getInteger(StockEnchant.WITHER_ASPECT, "EffectLevel");
	}

}
