package me.AlexTheCoder.BetterEnchants.API;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class EnchantActivateEvent extends Event implements Cancellable {
	
    private static final HandlerList handlers = new HandlerList();
    private LivingEntity user;
    private LivingEntity damaged;
    private boolean combat;
    private CustomEnchant enchant;
    private boolean cancelled;
 
    public EnchantActivateEvent(LivingEntity enchantUser, LivingEntity damage, boolean combatEnchant, CustomEnchant enchantUsed) {
    	user = enchantUser;
    	damaged = damage;
    	combat = combatEnchant;
    	enchant = enchantUsed;
    }
    
    public LivingEntity getUser() {
    	return user;
    }
    
    /**
     * If there is no damaged entity, this will return null
     * @return Damaged
     */
    public LivingEntity getDamaged() {
    	return damaged;
    }
    
    public boolean isCombatCaused() {
    	return combat;
    }
    
    public CustomEnchant getEnchant() {
    	return enchant;
    }
 
    public boolean isCancelled() {
        return cancelled;
    }
 
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
 
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
}