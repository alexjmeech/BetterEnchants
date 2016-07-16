package me.alexthecoder.betterenchants.api;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class EntityShotByEntityEvent extends Event implements Cancellable
{
	private static final HandlerList handlers = new HandlerList();
	private LivingEntity _damaged, _damager;
	private Arrow _projectile;
	private double _damage;
	private boolean _cancelled;

	public EntityShotByEntityEvent(LivingEntity shot, LivingEntity shooter, Arrow arrow, double damage)
	{
		_damaged = shot;
		_damager = shooter;
		_projectile = arrow;
		_damage = damage;
	}

	public LivingEntity getShot()
	{
		return _damaged;
	}
	
	public LivingEntity getShooter()
	{
		return _damager;
	}
	
	public Arrow getArrow()
	{
		return _projectile;
	}
	
	public double getDamage()
	{
		return _damage;
	}
	
	public void setDamage(double damage)
	{
		_damage = damage;
	}
	
	@Override
	public boolean isCancelled()
	{
		return _cancelled;
	}
	
	@Override
	public void setCancelled(boolean cancel)
	{
		_cancelled = cancel;
	}
	
	@Override
	public HandlerList getHandlers()
	{
		return handlers;
	}
}