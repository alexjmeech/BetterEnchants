package me.AlexTheCoder.BetterEnchants.enchant;

import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.config.HandleActive;
import me.AlexTheCoder.BetterEnchants.util.EnchantUtil;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PoisonHandler {
	
	public PoisonHandler(LivingEntity damaged, int level) {
		if (getValue() == null)
			return;
		if (AntitoxinHandler.getValue() == null)
			return;
		
		int time = getValue() * 20;
		int amplifier = level - 1;
		
		if(level > 2) {
			time = time + (level * 20);
			amplifier = 1;
		}
		
		if(damaged instanceof Player) {
			Player p = (Player)damaged;
			int antitoxinLevel = EnchantUtil.getHighestLevelofArmorEnchant(p.getInventory().getArmorContents(), EnchantAPI.getRegisteredEnchant("Antitoxin"));
			if(antitoxinLevel > 0) {
				damaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON, AntitoxinHandler.getNewDuration(time, antitoxinLevel), amplifier));
			}else{
				damaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON, time, amplifier));
			}
		}else{
			damaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON, time, amplifier));
		}
	}
	
	private Integer getValue() {
		return HandleActive.getInstance().getInteger(StockEnchant.POISON, "BaseDuration");
	}

}
