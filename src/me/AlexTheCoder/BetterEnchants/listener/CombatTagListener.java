package me.AlexTheCoder.BetterEnchants.listener;

import java.util.Set;

import me.AlexTheCoder.BetterEnchants.util.CombatTagUtil;

import org.bukkit.GameMode;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

import com.google.common.collect.ImmutableSet;

public class CombatTagListener implements Listener {
	private static Set<PotionEffectType> harmfulEffects = ImmutableSet.of(PotionEffectType.BLINDNESS, PotionEffectType.CONFUSION, PotionEffectType.HARM, PotionEffectType.HUNGER, PotionEffectType.POISON, PotionEffectType.SLOW, new PotionEffectType[] { PotionEffectType.SLOW_DIGGING, PotionEffectType.WEAKNESS, PotionEffectType.WITHER });
	
	@EventHandler(ignoreCancelled=true)
	public void tagPlayer(EntityDamageByEntityEvent event) {
		Entity victimEntity = event.getEntity();
		Entity attackerEntity = event.getDamager();
		Player victim;
		if ((victimEntity instanceof Tameable)) {
			AnimalTamer owner = ((Tameable)victimEntity).getOwner();
			if (!(owner instanceof Player)) {
				return;
			}
			victim = (Player)owner;
		} else {
			if ((victimEntity instanceof Player)) {
				victim = (Player)victimEntity;
			} else {
				return;
			}
		}
		if ((victim.getGameMode() == GameMode.CREATIVE)) {
			victim = null;
		}
		Player attacker;
		if (((attackerEntity instanceof LivingEntity))) {
			attacker = null;
		} else {
			if ((attackerEntity instanceof Projectile)) {
				Projectile p = (Projectile)attackerEntity;
				ProjectileSource source = p.getShooter();
				if (!(source instanceof Player)) {
					return;
				}
				if ((p.getType() == EntityType.ENDER_PEARL) && (victim == source)) {
					return;
				}
				attacker = (Player)source;
			} else {
				if ((attackerEntity instanceof Tameable)) {
					AnimalTamer owner = ((Tameable)attackerEntity).getOwner();
					if (!(owner instanceof Player)) {
						return;
					}
					attacker = (Player)owner;
				} else if ((attackerEntity instanceof Player)) {
					attacker = (Player)attackerEntity;
					if ((attacker.getGameMode() == GameMode.CREATIVE)) {
						attacker = null;
					}
				} else {
					return;
				}
			}
		}
		if (victim == attacker) {
			return;
		}
		CombatTagUtil.updateCombatTime(attacker);
		CombatTagUtil.updateCombatTime(victim);
	}
	
	@EventHandler(ignoreCancelled=true)
	public void tagPlayer(PotionSplashEvent event) {
		ProjectileSource source = event.getEntity().getShooter();
		if (!(source instanceof Player)) {
			return;
	    }
		Player attacker = (Player)source;
		boolean isHarmful = false;
		for (PotionEffect effect : event.getPotion().getEffects()) {
			if (harmfulEffects.contains(effect.getType())) {
				isHarmful = true;
				break;
			}
		}
		if (!isHarmful) {
			return;
		}
		for (LivingEntity entity : event.getAffectedEntities()) {
			if ((entity instanceof Player)) {
				Player victim = (Player)entity;
				if (victim != attacker) {
					CombatTagUtil.updateCombatTime(victim);
					CombatTagUtil.updateCombatTime(attacker);
				}
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		CombatTagUtil.removePlayer(event.getPlayer());
	}

}
