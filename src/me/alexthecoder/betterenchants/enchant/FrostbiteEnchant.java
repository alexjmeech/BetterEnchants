package me.alexthecoder.betterenchants.enchant;

import me.alexthecoder.betterenchants.api.CustomEnchant;
import me.alexthecoder.betterenchants.api.EnchantActivateEvent;
import me.alexthecoder.betterenchants.api.EnchantShootArrowEvent;
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

public class FrostbiteEnchant extends CustomEnchant
{
	private static final Integer DURATION = HandleActive.getInstance().getInteger(StockEnchant.FROSTBITE, "Duration");
	private static final Integer LEVEL_MULT = HandleActive.getInstance().getInteger(StockEnchant.FROSTBITE, "LevelMult");
	
	public FrostbiteEnchant()
	{
		super("Frostbite", EnchantmentCategory.ANCIENT, 3, "BOW", "ARROW_FIRE", "POISON");
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
			event.getProjectile().setMetadata("FROSTBITE_ARROW", new FixedMetadataValue(Bukkit.getPluginManager().getPlugin("BetterEnchants"), level));
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
			event.getArrow().setMetadata("FROSTBITE_ARROW", new FixedMetadataValue(Bukkit.getPluginManager().getPlugin("BetterEnchants"), level));
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onEntityShot(EntityShotByEntityEvent event)
	{
		if (event.getArrow().hasMetadata("FROSTBITE_ARROW"))
		{
			int level = event.getArrow().getMetadata("FROSTBITE_ARROW").get(0).asInt();
			EnchantActivateEvent ac = new EnchantActivateEvent(event.getShooter(), event.getShot(), true, this);
			Bukkit.getPluginManager().callEvent(ac);
			if (!ac.isCancelled())
			{
				event.getShot().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, DURATION * 20, (LEVEL_MULT * level) - 1));
			}
		}
	}
}