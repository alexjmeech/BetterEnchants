package me.alexthecoder.betterenchants.enchant;

import me.alexthecoder.betterenchants.api.CustomEnchant;
import me.alexthecoder.betterenchants.api.EnchantActivateEvent;
import me.alexthecoder.betterenchants.api.EnchantmentCategory;
import me.alexthecoder.betterenchants.config.HandleActive;
import me.alexthecoder.betterenchants.util.EnchantUtil;
import me.alexthecoder.betterenchants.util.MiscUtil;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CranialStrikeEnchant extends CustomEnchant
{
	private static final Integer DURATION_MULT = HandleActive.getInstance().getInteger(StockEnchant.CRANIAL_STRIKE, "DurationMult");
	private static final Double PROBABILITY_MULT = HandleActive.getInstance().getDouble(StockEnchant.CRANIAL_STRIKE, "ProbabilityMult");
	
	public CranialStrikeEnchant()
	{
		super("Cranial Strike", EnchantmentCategory.ANCIENT, 3, "_SWORD _AXE", "", "");
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event)
	{
		if (!(event.getDamager() instanceof Player))
		{
			return;
		}
		if (!(event.getEntity() instanceof LivingEntity))
		{
			return;
		}
		
		Player player = (Player) event.getDamager();
		LivingEntity target = (LivingEntity) event.getEntity();
		
		if (player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR)
		{
			return;
		}
		ItemStack item = player.getInventory().getItemInMainHand();
		
		if (!EnchantUtil.hasEnchant(item, this))
		{
			return;
		}
		int level = EnchantUtil.getLevel(item, getName());
		
		if (!MiscUtil.willOccur(PROBABILITY_MULT * level))
		{
			return;
		}
		
		EnchantActivateEvent active = new EnchantActivateEvent(player, target, true, this);
		Bukkit.getPluginManager().callEvent(active);
		if (active.isCancelled())
		{
			return;
		}
		
		target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, DURATION_MULT * level * 20, 1));
	}
}