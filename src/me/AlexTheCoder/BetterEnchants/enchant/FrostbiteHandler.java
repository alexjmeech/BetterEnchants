package me.AlexTheCoder.BetterEnchants.enchant;

import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.config.HandleActive;
import me.AlexTheCoder.BetterEnchants.util.EnchantUtil;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FrostbiteHandler {
	
	public FrostbiteHandler(LivingEntity damaged, int level) {
		if ((getValue(true) == null) || (getValue(false) == null))
			return;
		if (AntitoxinHandler.getValue() == null)
			return;
		
		if(damaged instanceof Player) {
			Player p = (Player)damaged;
			int antitoxinLevel = EnchantUtil.getHighestLevelofArmorEnchant(p.getInventory().getArmorContents(), EnchantAPI.getRegisteredEnchant("Antitoxin"));
			if(antitoxinLevel > 0) {
				damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, AntitoxinHandler.getNewDuration(getValue(true) * 20, antitoxinLevel), (level * getValue(false)) - 1));
			}else{
				damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, getValue(true) * 20, (level * getValue(false)) - 1));
			}
		}else{
			damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, getValue(true) * 20, (level * getValue(false)) - 1));
		}
	}
	
	private Integer getValue(boolean duration) {
		if (duration) {
			return HandleActive.getInstance().getInteger(StockEnchant.FROSTBITE, "Duration");
		}
		
		return HandleActive.getInstance().getInteger(StockEnchant.FROSTBITE, "LevelMult");
	}

}
