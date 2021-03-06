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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StaggeringBlowEnchant extends CustomEnchant
{
	private static final double PROBABILITY_MULT = HandleActive.getInstance().getDouble(StockEnchant.STAGGERING_BLOW, "ProbabilityMult");
	private static final int DURATION = HandleActive.getInstance().getInteger(StockEnchant.STAGGERING_BLOW, "Duration");

	public StaggeringBlowEnchant()
	{
		super("Staggering Blow", EnchantmentCategory.ANCIENT, 3, "_SWORD _AXE", "", "");
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
				target.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, DURATION * 20, level - 1));
			}
		}
	}
}