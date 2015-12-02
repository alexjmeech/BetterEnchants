package me.AlexTheCoder.BetterEnchants.enchant;

import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.config.HandleActive;
import me.AlexTheCoder.BetterEnchants.util.MiscUtil;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class LifestealHandler {
	
	public LifestealHandler(Player p, int level, double damage) {
		if ((getValue(true) == null) || (getValue(false) == null))
			return;
		
		if(MiscUtil.willOccur(getValue(true) * level)) {
			if (p.getGameMode().equals(GameMode.CREATIVE)) {
				return;
			}
			if ((level == -1) || (level > EnchantAPI.getRegisteredEnchant("Lifesteal").getMaxLevel())) {
				return;
			}
			double percentage = getValue(false) * level;
			double healthBoost = percentage * damage;
			p.setHealth(Math.min(p.getMaxHealth(), p.getHealth() + healthBoost));
			p.sendMessage(ChatColor.RED + "You siphoned some life away from your opponent!");
		}
	}
	
	private Double getValue(boolean chance) {
		if (chance) {
			return HandleActive.getInstance().getDouble(StockEnchant.LIFESTEAL, "ProbabilityMult");
		}
		
		return HandleActive.getInstance().getDouble(StockEnchant.LIFESTEAL, "DamageMult");
	}

}
