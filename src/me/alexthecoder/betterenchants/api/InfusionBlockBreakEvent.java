package me.alexthecoder.betterenchants.api;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public final class InfusionBlockBreakEvent extends BlockBreakEvent
{
	private static final HandlerList handlers = new HandlerList();
	private ItemStack _pick;
	private boolean _cancelled;

	public InfusionBlockBreakEvent(Player enchantUser, Block broken, ItemStack pickaxe)
	{
		super(broken, enchantUser);
		_pick = pickaxe;
	}

	public ItemStack getPickaxe()
	{
		return _pick;
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