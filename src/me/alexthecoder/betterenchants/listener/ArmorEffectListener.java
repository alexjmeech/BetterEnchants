package me.alexthecoder.betterenchants.listener;

import java.util.concurrent.ConcurrentHashMap;

import me.alexthecoder.betterenchants.api.CustomArmorBuff;
import me.alexthecoder.betterenchants.api.EnchantAPI;
import me.alexthecoder.betterenchants.util.CombatTagUtil;
import me.alexthecoder.betterenchants.util.EnchantUtil;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.projectkorra.projectkorra.earthbending.EarthArmor;
import com.projectkorra.projectkorra.waterbending.PlantArmor;

public class ArmorEffectListener implements Listener
{
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onLoadArmorBuffs(PlayerJoinEvent event)
	{
		updateBuffs(event.getPlayer());
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onUnloadArmorBuffs(PlayerQuitEvent event)
	{
		updateBuffs(event.getPlayer());
	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true)
	public void onAdjustArmorBuffs(InventoryClickEvent event)
	{
		if (!(event.getWhoClicked() instanceof Player))
		{
			return;
		}
		Player player = (Player)event.getWhoClicked();
		if (player.getGameMode().equals(GameMode.CREATIVE))
		{
			if (event.getInventory().getType().equals(InventoryType.CREATIVE))
			{
				return;
			}
		}
		else if (!event.getInventory().getType().equals(InventoryType.CRAFTING))
		{
			return;
		}
		updateBuffs(player);
	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true)
	public void onAdjustArmorBuffs(PlayerItemConsumeEvent event)
	{
		if (event.getItem().getType() == Material.MILK_BUCKET || event.getItem().getType() == Material.POTION)
		{
			updateBuffs(event.getPlayer());
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPotionSplash(PotionSplashEvent event)
	{
		for (LivingEntity le : event.getAffectedEntities())
		{
			if (le instanceof Player)
			{
				updateBuffs((Player)le);
			}
		}
	}

	public static void updateBuffs(final Player player)
	{
		Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("BetterEnchants"), new Runnable()
		{
			public void run()
			{
				ConcurrentHashMap<PotionEffectType, PotionEffect> effects = new ConcurrentHashMap<>();
				ConcurrentHashMap<PotionEffectType, Integer> allowed = new ConcurrentHashMap<>();
				for (CustomArmorBuff buff : EnchantAPI.getRegisteredArmorBuffs())
				{
					if (EnchantUtil.hasArmorEnchant(player.getInventory().getArmorContents(), buff, buff.getPiecesNeeded()))
					{
						if (!buff.getDisableInCombat() || !CombatTagUtil.hasBeenInCombat(player, CombatTagUtil.ARMOR_EFFECT_REQUIRED_PEACETIME))
						{
							int amplifier = EnchantUtil.getHighestLevelofArmorEnchant(player.getInventory().getArmorContents(), buff) - 1;
							effects.put(buff.getEffect(), new PotionEffect(buff.getEffect(), 2147483647, amplifier));
							allowed.put(buff.getEffect(), amplifier);
						}
					}
				}
				for (PotionEffect effect : player.getActivePotionEffects())
				{
					if (effect.getDuration() > 9600)
					{
						if (effect.getType() == PotionEffectType.DAMAGE_RESISTANCE && Bukkit.getPluginManager().isPluginEnabled("ProjectKorra"))
						{
							if (EarthArmor.instances.containsKey(player) || PlantArmor.instances.containsKey(player))
							{
								effects.remove(effect.getType());
								continue;
							}
						}
						if (!allowed.containsKey(effect.getType()) || allowed.get(effect.getType()) != effect.getAmplifier())
						{
							player.removePotionEffect(effect.getType());
						}
						else
						{
							effects.remove(effect.getType());
						}
					}
				}
				for (PotionEffect pe : effects.values())
				{
					if (player.hasPotionEffect(pe.getType()))
					{
						player.removePotionEffect(pe.getType());
					}
					
					player.addPotionEffect(pe);
				}
			}
		});
	}
}