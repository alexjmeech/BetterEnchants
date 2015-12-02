package me.AlexTheCoder.BetterEnchants.enchant;

import me.AlexTheCoder.BetterEnchants.API.CustomEnchant;
import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;

import org.bukkit.potion.PotionEffectType;

public enum StockArmorBuff {
	
	HIGHLANDER(EnchantAPI.getRegisteredEnchant("Highlander"), StockEnchant.HIGHLANDER, 1, PotionEffectType.SPEED),
	MEDITATION(EnchantAPI.getRegisteredEnchant("Meditation"), StockEnchant.MEDITATION, 1, PotionEffectType.REGENERATION)
	;
	
	private CustomEnchant enchant;
	private StockEnchant stock;
	private int piecesNeeded;
	private PotionEffectType effect;
	
	private StockArmorBuff(CustomEnchant enchant, StockEnchant stock, int piecesNeeded, PotionEffectType effect) {
		this.enchant = enchant;
		this.stock = stock;
		this.piecesNeeded = piecesNeeded;
		this.effect = effect;
	}
	
	public CustomEnchant getEnchant() {
		return this.enchant;
	}
	
	public StockEnchant getStock() {
		return this.stock;
	}
	
	public int getPiecesNeeded() {
		return this.piecesNeeded;
	}
	
	public PotionEffectType getEffect() {
		return this.effect;
	}

}
