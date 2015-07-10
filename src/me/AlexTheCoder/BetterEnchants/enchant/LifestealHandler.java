package me.AlexTheCoder.BetterEnchants.enchant;

import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.util.MiscUtil;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class LifestealHandler {
	
	public LifestealHandler(Player p, int level, double damage) {
		if(MiscUtil.willOccur(2.5 * level)) {
			if (p.getGameMode().equals(GameMode.CREATIVE)) {
				return;
			}
			if ((level == -1) || (level > EnchantAPI.getRegisteredEnchant("Lifesteal").getMaxLevel())) {
				return;
			}
			double percentage = .10 * level;
			double healthboost = percentage * damage;
			if(p.getHealth() + healthboost > p.getMaxHealth()) {
				p.setHealth(p.getMaxHealth());
			}else{
				p.setHealth(p.getHealth() + healthboost);
			}
			p.sendMessage(ChatColor.RED + "You siphoned some life away from your opponent!");
		}
	}

}
