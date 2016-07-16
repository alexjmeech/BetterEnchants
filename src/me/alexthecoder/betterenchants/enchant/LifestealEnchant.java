package me.alexthecoder.betterenchants.enchant;

import me.alexthecoder.betterenchants.api.CustomEnchant;
import me.alexthecoder.betterenchants.api.EnchantActivateEvent;
import me.alexthecoder.betterenchants.api.EnchantmentCategory;
import me.alexthecoder.betterenchants.config.HandleActive;
import me.alexthecoder.betterenchants.util.EnchantUtil;
import me.alexthecoder.betterenchants.util.MiscUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class LifestealEnchant extends CustomEnchant
{
	private static final double PROBABILITY_MULT = HandleActive.getInstance().getDouble(StockEnchant.LIFESTEAL, "ProbabilityMult");
	private static final double DAMAGE_MULT = HandleActive.getInstance().getDouble(StockEnchant.LIFESTEAL, "DamageMult");

	public LifestealEnchant()
	{
		super("Lifesteal", EnchantmentCategory.MYTHIC, 3, "_SWORD _AXE", "", "");
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onHit(EntityDamageByEntityEvent event)
	{
		if (!(event.getEntity() instanceof LivingEntity) || !(event.getDamager() instanceof Player))
		{
			return;
		}
		Player player = (Player) event.getDamager();
		LivingEntity target = (LivingEntity) event.getEntity();
		if (player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR)
		{
			return;
		}
		if (player.getGameMode() == GameMode.CREATIVE)
		{
			return;
		}

		ItemStack i = player.getInventory().getItemInMainHand();
		if (EnchantUtil.hasEnchant(i, this))
		{
			int level = EnchantUtil.getLevel(i, getName());
			if (!MiscUtil.willOccur(PROBABILITY_MULT * level))
			{
				return;
			}
			EnchantActivateEvent ac = new EnchantActivateEvent(player, target, true, this);
			Bukkit.getPluginManager().callEvent(ac);
			if (!ac.isCancelled())
			{
				double healthBoost = (DAMAGE_MULT * level) * event.getDamage();
				player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + healthBoost));
				player.sendMessage(ChatColor.RED + "You siphoned some life away from your opponent!");
			}
		}
	}
}