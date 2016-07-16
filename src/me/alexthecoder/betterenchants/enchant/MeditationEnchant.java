package me.alexthecoder.betterenchants.enchant;

import org.bukkit.potion.PotionEffectType;

import me.alexthecoder.betterenchants.api.CustomArmorBuff;
import me.alexthecoder.betterenchants.api.EnchantmentCategory;
import me.alexthecoder.betterenchants.config.HandleActive;

public class MeditationEnchant extends CustomArmorBuff
{
	private static final boolean COMBAT_DISABLE = HandleActive.getInstance().getBoolean(StockEnchant.MEDITATION, "DisableInCombat");
	
	public MeditationEnchant()
	{
		super("Meditation", EnchantmentCategory.MYTHIC, 3, "_HELMET _CHESTPLATE _LEGGINGS _BOOTS", "", "", 1, PotionEffectType.REGENERATION, COMBAT_DISABLE);
	}
}