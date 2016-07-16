package me.alexthecoder.betterenchants.listener;

import me.alexthecoder.betterenchants.api.EntityShotByEntityEvent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class EntityShotListener implements Listener
{
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onShot(EntityDamageByEntityEvent event)
	{
		if (!(event.getEntity() instanceof LivingEntity))
		{
			return;
		}
		if (event.getCause() != DamageCause.PROJECTILE)
		{
			return;
		}
		if (!(event.getDamager() instanceof Arrow))
		{
			return;
		}
		LivingEntity shot = (LivingEntity) event.getEntity();
		Arrow projectile = (Arrow) event.getDamager();
		
		if (!(projectile.getShooter() instanceof LivingEntity))
		{
			return;
		}
		LivingEntity shooter = (LivingEntity) projectile.getShooter();
		
		EntityShotByEntityEvent sEvent = new EntityShotByEntityEvent(shot, shooter, projectile, event.getDamage());
		Bukkit.getPluginManager().callEvent(sEvent);
		if (sEvent.isCancelled())
		{
			event.setCancelled(true);
		}
		event.setDamage(sEvent.getDamage());
	}
}