package me.alexthecoder.betterenchants.enchant;

import me.alexthecoder.betterenchants.api.CustomEnchant;
import me.alexthecoder.betterenchants.api.EnchantActivateEvent;
import me.alexthecoder.betterenchants.api.EnchantmentCategory;
import me.alexthecoder.betterenchants.config.HandleActive;
import me.alexthecoder.betterenchants.util.EnchantUtil;
import me.alexthecoder.betterenchants.util.MiscUtil;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ShellshockEnchant extends CustomEnchant
{
	private static double PROBABILITY_MULT = HandleActive.getInstance().getDouble(StockEnchant.SHELLSHOCK, "ProbabilityMult");
	private static double DAMAGE_MULT = HandleActive.getInstance().getDouble(StockEnchant.SHELLSHOCK, "DamageMult");
	
	public ShellshockEnchant()
	{
		super("Shellshock", EnchantmentCategory.MYTHIC, 3, "_HELMET _CHESTPLATE _LEGGINGS _BOOTS", "THORNS", "");
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onHit(EntityDamageByEntityEvent event)
	{
		if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof LivingEntity))
		{
			return;
		}
		Player player = (Player) event.getEntity();
		LivingEntity target = (LivingEntity) event.getDamager();
		if (player.getGameMode() == GameMode.CREATIVE)
		{
			return;
		}
		
		for (ItemStack armor : player.getInventory().getArmorContents())
		{
			if (armor == null || armor.getType() == Material.AIR)
			{
				continue;
			}
			if (EnchantUtil.hasEnchant(armor, "Shellshock"))
			{
				int level = EnchantUtil.getLevel(armor, getName());
				if (!MiscUtil.willOccur(PROBABILITY_MULT * level))
				{
					return;
				}
				EnchantActivateEvent ac = new EnchantActivateEvent(player, target, true, this);
				Bukkit.getPluginManager().callEvent(ac);
				if (!ac.isCancelled())
				{
					target.damage((level * DAMAGE_MULT), player);
				}
			}
		}
	}
}