package me.alexthecoder.betterenchants.enchant;

import me.alexthecoder.betterenchants.api.CustomEnchant;
import me.alexthecoder.betterenchants.api.EnchantActivateEvent;
import me.alexthecoder.betterenchants.api.EnchantmentCategory;
import me.alexthecoder.betterenchants.util.EnchantUtil;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class SaturationEnchant extends CustomEnchant
{
	public SaturationEnchant()
	{
		super("Saturation", EnchantmentCategory.ANCIENT, 3, "_HELMET _CHESTPLATE _LEGGINGS _BOOTS", "", "");
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onFoodChange(FoodLevelChangeEvent event)
	{
		if (event.getEntity() instanceof Player)
		{
			Player p = (Player)event.getEntity();
			if (EnchantUtil.hasArmorEnchant(p.getInventory().getArmorContents(), this, 1))
			{
				EnchantActivateEvent ac = new EnchantActivateEvent(event.getEntity(), null, false, this);
				Bukkit.getPluginManager().callEvent(ac);
				if (!ac.isCancelled())
				{
					event.setFoodLevel(event.getFoodLevel() + (event.getFoodLevel() / (1 + EnchantUtil.getHighestLevelofArmorEnchant(p.getInventory().getArmorContents(), this))));
				}
			}
		}
	}
}