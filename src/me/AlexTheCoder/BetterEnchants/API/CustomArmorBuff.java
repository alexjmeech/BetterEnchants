package me.alexthecoder.betterenchants.api;

import org.bukkit.potion.PotionEffectType;

public abstract class CustomArmorBuff extends CustomEnchant
{
	private int _piecesNeeded;
	private PotionEffectType _effect;
	private boolean _disable;
	
	public CustomArmorBuff(String name, EnchantmentCategory category, int maxLevel, String enchantableItems, String vanillaConflicts, String betterConflicts, int piecesNeeded, PotionEffectType effect, boolean disableInCombat)
	{
		super(name, category, maxLevel, enchantableItems, vanillaConflicts, betterConflicts);
		_piecesNeeded = piecesNeeded;
		_effect = effect;
		_disable = disableInCombat;
	}
	
	public int getPiecesNeeded()
	{
		return _piecesNeeded;
	}
	
	public PotionEffectType getEffect()
	{
		return _effect;
	}
	
	public boolean getDisableInCombat()
	{
		return _disable;
	}
}