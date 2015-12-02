package me.AlexTheCoder.BetterEnchants.enchant;

import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.config.HandleActive;
import me.AlexTheCoder.BetterEnchants.util.MiscUtil;

import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class ShellshockHandler {
	
	public ShellshockHandler(Player p, LivingEntity attacker, int level) {
		if ((getValue(true) == null) || (getValue(false) == null))
			return;
		
		if(MiscUtil.willOccur(getValue(true) * level)) {
			if (p.getGameMode().equals(GameMode.CREATIVE)) {
				return;
			}
			if ((level == -1) || (level > EnchantAPI.getRegisteredEnchant("Shellshock").getMaxLevel())) {
				return;
			}
			attacker.damage((level * getValue(false)), p);
		}
	}
	
	private Double getValue(boolean chance) {
		if (chance) {
			return HandleActive.getInstance().getDouble(StockEnchant.SHELLSHOCK, "ProbabilityMult");
		}
		
		return HandleActive.getInstance().getDouble(StockEnchant.SHELLSHOCK, "DamageMult");
	}

}
