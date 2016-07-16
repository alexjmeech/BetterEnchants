package me.alexthecoder.betterenchants.enchant;

import me.alexthecoder.betterenchants.api.CustomEnchant;
import me.alexthecoder.betterenchants.api.EnchantActivateEvent;
import me.alexthecoder.betterenchants.api.EnchantToxinApplyEvent;
import me.alexthecoder.betterenchants.api.EnchantmentCategory;
import me.alexthecoder.betterenchants.config.HandleActive;
import me.alexthecoder.betterenchants.util.EnchantUtil;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PotionSplashEvent;

public class AntitoxinEnchant extends CustomEnchant
{
	private static final Double REDUCTION_MULTIPLIER = HandleActive.getInstance().getDouble(StockEnchant.ANTITOXIN, "ReductionMult");
	
	public AntitoxinEnchant()
	{
		super("Antitoxin", EnchantmentCategory.MYTHIC, 3, "_HELMET _CHESTPLATE _LEGGINGS _BOOTS", "", "");
	}
	
	private int getNewDuration(int old, int level)
	{
		Double newDuration = old - (old * (REDUCTION_MULTIPLIER * level));
		return Math.round(newDuration.floatValue());
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onPotionSplash(PotionSplashEvent e)
	{
		for (LivingEntity entity : e.getAffectedEntities())
		{			
			if (entity instanceof Player)
			{
				Player p = (Player)entity;
				if (EnchantUtil.getHighestLevelofArmorEnchant(p.getInventory().getArmorContents(), this) > 0)
				{
					EnchantActivateEvent ac = new EnchantActivateEvent(entity, null, true, this);
					Bukkit.getPluginManager().callEvent(ac);
					if (!ac.isCancelled())
					{
						if (p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE)
						{
							double i = e.getIntensity(entity);
							double newIntensity = i - (i * (REDUCTION_MULTIPLIER * EnchantUtil.getHighestLevelofArmorEnchant(p.getInventory().getArmorContents(), this)));
							e.setIntensity(entity, newIntensity);
						}
					}
				}
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPoisonedByEnchant(EnchantToxinApplyEvent event)
	{
		if (event.getReceiving() instanceof Player)
		{
			Player p = (Player) event.getReceiving();
			if (EnchantUtil.getHighestLevelofArmorEnchant(p.getInventory().getArmorContents(), this) > 0)
			{
				EnchantActivateEvent ac = new EnchantActivateEvent(p, null, true, this);
				Bukkit.getPluginManager().callEvent(ac);
				if (!ac.isCancelled())
				{
					if (p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE)
					{
						event.setDuration(getNewDuration(event.getDuration(), EnchantUtil.getHighestLevelofArmorEnchant(p.getInventory().getArmorContents(), this)));
					}
				}
			}
		}
	}
}