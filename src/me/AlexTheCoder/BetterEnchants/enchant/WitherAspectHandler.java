package me.AlexTheCoder.BetterEnchants.enchant;

import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.util.EnchantUtil;
import me.AlexTheCoder.BetterEnchants.util.MiscUtil;

import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WitherAspectHandler {
	
	public WitherAspectHandler(Player p, int level, LivingEntity target) {
		if(MiscUtil.willOccur(2.5 * level)) {
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
					t.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, AntitoxinHandler.getNewDuration((1 + level) * 20, antitoxinLevel), 1));
				}else{
					t.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (1 + level) * 20, 1));
				}
			}else{
				target.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (1 + level) * 20, 1));
			}
			target.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (1 + level) * 20, 1));
		}
	}

}
