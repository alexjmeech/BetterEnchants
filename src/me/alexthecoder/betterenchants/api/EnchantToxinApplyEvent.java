package me.alexthecoder.betterenchants.api;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.potion.PotionEffectType;

public final class EnchantToxinApplyEvent extends Event implements Cancellable
{
	private static final HandlerList handlers = new HandlerList();
	private LivingEntity _damaged, _damager;
	private CustomEnchant _enchant;
	private PotionEffectType _type;
	private int _amplifier, _duration;
	private boolean _cancelled;

	public EnchantToxinApplyEvent(LivingEntity receiving, LivingEntity cause, CustomEnchant enchant, PotionEffectType potionType, int amplifier, int duration)
	{
		_damaged = receiving;
		_damager = cause;
		_enchant = enchant;
		_type = potionType;
		_amplifier = amplifier;
		_duration = duration;
	}

	public LivingEntity getReceiving()
	{
		return _damaged;
	}
	
	public LivingEntity getCause()
	{
		return _damager;
	}
	
	public CustomEnchant getEnchant()
	{
		return _enchant;
	}
	
	public PotionEffectType getPotionType()
	{
		return _type;
	}
	
	public int getAmplifier()
	{
		return _amplifier;
	}
	
	public int getDuration()
	{
		return _duration;
	}
	
	public void setAmplifier(int amplifier)
	{
		_amplifier = amplifier;
	}
	
	public void setDuration(int duration)
	{
		_duration = duration;
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