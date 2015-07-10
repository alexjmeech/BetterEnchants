package me.AlexTheCoder.BetterEnchants.API;

import org.bukkit.potion.PotionEffectType;

public class CustomArmorBuff {
	
	private CustomEnchant enchant;
	private int piecesNeeded;
	private PotionEffectType effect;
	private boolean disable;
	
	public CustomArmorBuff(CustomEnchant enchant, int piecesNeeded, PotionEffectType effect, boolean disableInCombat) {
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
	
	public boolean getDisableInCombat() {
		return this.disable;
	}
}
