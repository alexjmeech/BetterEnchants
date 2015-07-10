package me.AlexTheCoder.BetterEnchants.enchant;

import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.util.EnchantUtil;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FrostbiteHandler {
	
	public FrostbiteHandler(LivingEntity damaged, int level) {
		if(damaged instanceof Player) {
			Player p = (Player)damaged;
			int antitoxinLevel = EnchantUtil.getHighestLevelofArmorEnchant(p.getInventory().getArmorContents(), EnchantAPI.getRegisteredEnchant("Antitoxin"));
			if(antitoxinLevel > 0) {
				damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, AntitoxinHandler.getNewDuration(5 * 20, antitoxinLevel), level - 1));
			}else{
				damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5 * 20, level - 1));
			}
		}else{
			damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5 * 20, level - 1));
		}
	}

}
