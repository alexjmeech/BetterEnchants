package me.alexthecoder.betterenchants.api;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public final class EnchantShootArrowEvent extends Event implements Cancellable
{
	private static final HandlerList handlers = new HandlerList();
	private Player _user;
	private ItemStack _bow;
	private Arrow _projectile;
	private boolean _cancelled;

	public EnchantShootArrowEvent(Player enchantUser, ItemStack bow, Arrow arrow)
	{
		_user = enchantUser;
		_bow = bow;
		_projectile = arrow;
	}
	
	public Player getPlayer()
	{
		return _user;
	}

	public ItemStack getBow()
	{
		return _bow;
	}
	
	public Arrow getArrow()
	{
		return _projectile;
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