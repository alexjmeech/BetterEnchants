package me.AlexTheCoder.BetterEnchants.enchant;

import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.util.EnchantUtil;
import me.AlexTheCoder.BetterEnchants.util.MiscUtil;

import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class InfectedBladeHandler {
	
	public InfectedBladeHandler(Player p, LivingEntity target, int level) {
		if(MiscUtil.willOccur(2.5 * level)) {
			int time = 5 * 20;
			int amplifier = level - 1;
			
			if(level > 2) {
				time = time + (level * 20);
				amplifier = 1;
			}
			
			if (p.getGameMode().equals(GameMode.CREATIVE)) {
				return;
			}
			if ((level == -1) || (level > EnchantAPI.getRegisteredEnchant("Infected Blade").getMaxLevel())) {
				return;
			}
			if(target instanceof Player) {
				Player t = (Player)target;
				int antitoxinLevel = EnchantUtil.getHighestLevelofArmorEnchant(t.getInventory().getArmorContents(), EnchantAPI.getRegisteredEnchant("Antitoxin"));
				if(antitoxinLevel > 0) {
					t.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, AntitoxinHandler.getNewDuration(time, antitoxinLevel), amplifier));
				}else{
					t.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, time, amplifier));
				}
			}else{
				target.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, time, amplifier));
			}
		}
	}

}
