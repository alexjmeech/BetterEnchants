package me.alexthecoder.betterenchants.enchant;

import org.bukkit.potion.PotionEffectType;

import me.alexthecoder.betterenchants.api.CustomArmorBuff;
import me.alexthecoder.betterenchants.api.EnchantmentCategory;
import me.alexthecoder.betterenchants.config.HandleActive;

public class HighlanderEnchant extends CustomArmorBuff
{
	private static final boolean COMBAT_DISABLE = HandleActive.getInstance().getBoolean(StockEnchant.HIGHLANDER, "DisableInCombat");
	
	public HighlanderEnchant()
	{
		super("Highlander", EnchantmentCategory.MYTHIC, 3, "_HELMET _CHESTPLATE _LEGGINGS _BOOTS", "", "", 1, PotionEffectType.SPEED, COMBAT_DISABLE);
	}
}