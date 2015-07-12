package me.AlexTheCoder.BetterEnchants.enchant;

import me.AlexTheCoder.BetterEnchants.API.CustomEnchant;
import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;

import org.bukkit.potion.PotionEffectType;

public enum StockArmorBuff {
	
	HIGHLANDER(EnchantAPI.getRegisteredEnchant("Highlander"), 1, PotionEffectType.SPEED, true),
	MEDITATION(EnchantAPI.getRegisteredEnchant("Meditation"), 1, PotionEffectType.REGENERATION, true)
	;
	
	private CustomEnchant enchant;
	private int piecesNeeded;
	private PotionEffectType effect;
	private boolean disable;
	
	private StockArmorBuff(CustomEnchant enchant, int piecesNeeded, PotionEffectType effect, boolean disableInCombat) {
		this.enchant = enchant;
		this.piecesNeeded = piecesNeeded;
		this.effect = effect;
		this.disable = disableInCombat;
	}
	
	public CustomEnchant getEnchant() {
		return this.enchant;
	}
	
	public int getPiecesNeeded() {
		return this.piecesNeeded;
	}
	
	public PotionEffectType getEffect() {
		return this.effect;
	}
	
	public boolean disableInCombat() {
		return this.disable;
	}

}
