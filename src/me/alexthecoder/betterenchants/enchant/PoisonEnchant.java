package me.alexthecoder.betterenchants.enchant;

import me.alexthecoder.betterenchants.api.CustomEnchant;
import me.alexthecoder.betterenchants.api.EnchantActivateEvent;
import me.alexthecoder.betterenchants.api.EnchantShootArrowEvent;
import me.alexthecoder.betterenchants.api.EnchantToxinApplyEvent;
import me.alexthecoder.betterenchants.api.EnchantmentCategory;
import me.alexthecoder.betterenchants.api.EntityShotByEntityEvent;
import me.alexthecoder.betterenchants.config.HandleActive;
import me.alexthecoder.betterenchants.util.EnchantUtil;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PoisonEnchant extends CustomEnchant
{
	private static final Integer DURATION = HandleActive.getInstance().getInteger(StockEnchant.POISON, "BaseDuration");
	
	public PoisonEnchant()
	{
		super("Poison", EnchantmentCategory.MYTHIC, 3, "BOW", "ARROW_FIRE", "FROSTBITE");
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onShootBow(EntityShootBowEvent event)
	{
		if (event.getBow() == null)
		{
			return;
		}
		if (!(event.getEntity() instanceof Player))
		{
			return;
		}
		ItemStack bow = event.getBow();
		if (EnchantUtil.hasEnchant(bow, this))
		{
			int level = EnchantUtil.getLevel(bow, getName());
			event.getProjectile().setMetadata("POISON_ARROW", new FixedMetadataValue(Bukkit.getPluginManager().getPlugin("BetterEnchants"), level));
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onShootBow(EnchantShootArrowEvent event)
	{
		if (event.getBow() == null)
		{
			return;
		}
		ItemStack bow = event.getBow();
		if (EnchantUtil.hasEnchant(bow, this))
		{
			int level = EnchantUtil.getLevel(bow, getName());
			event.getArrow().setMetadata("POISON_ARROW", new FixedMetadataValue(Bukkit.getPluginManager().getPlugin("BetterEnchants"), level));
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onEntityShot(EntityShotByEntityEvent event)
	{
		if (event.getArrow().hasMetadata("POISON_ARROW"))
		{
			int level = event.getArrow().getMetadata("POISON_ARROW").get(0).asInt();
			EnchantActivateEvent ac = new EnchantActivateEvent(event.getShooter(), event.getShot(), true, this);
			Bukkit.getPluginManager().callEvent(ac);
			if (!ac.isCancelled())
			{
				int duration = DURATION * 20;
				int amplifier = Math.max(level - 1, 1);
				EnchantToxinApplyEvent toxin = new EnchantToxinApplyEvent(event.getShot(), event.getShooter(), this, PotionEffectType.POISON, amplifier, duration);
				Bukkit.getPluginManager().callEvent(toxin);
				if (toxin.isCancelled())
				{
					return;
				}
				event.getShot().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, toxin.getDuration(), toxin.getAmplifier()));
			}
		}
	}
}