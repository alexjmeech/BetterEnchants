package me.AlexTheCoder.BetterEnchants.enchant;

import me.AlexTheCoder.BetterEnchants.Main;
import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.listener.EnchantListener;
import me.AlexTheCoder.BetterEnchants.util.EnchantUtil;
import me.AlexTheCoder.BetterEnchants.util.YawUtil;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class MultishotHandler {
	
	public MultishotHandler(Player player, EntityShootBowEvent e, int level) {
		if (player.getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}
		new MultishotHandler(e, level);
	}
	
	public MultishotHandler(final EntityShootBowEvent e, final int level) {
		if ((level == -1) || (level > EnchantAPI.getRegisteredEnchant("Multishot").getMaxLevel())) {
			return;
		}
	    final Location loc = e.getProjectile().getLocation().clone();
	    final LivingEntity shooter = e.getEntity();
	    final Vector velocity = e.getProjectile().getVelocity().clone();
	    final ItemStack bow = e.getBow();
	    
	    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
	    	public void run() {
	    		if (level == 1) {
	    			MultishotHandler.this.spawnArrow(loc, shooter, velocity, -0.5D, 0.6D, bow, e.getProjectile().getFireTicks());
	    		}else if (level == 2) {
	    			MultishotHandler.this.spawnArrow(loc, shooter, velocity, 0.5D, 0.6D, bow, e.getProjectile().getFireTicks());
	    			MultishotHandler.this.spawnArrow(loc, shooter, velocity, -0.5D, 0.6D, bow, e.getProjectile().getFireTicks());
	    		}
	    	}
	    });
	}
	
	private void spawnArrow(Location loc, LivingEntity shooter, Vector velocity, double amount, double forward, ItemStack bow, int fireTicks) {
		BlockFace direction = YawUtil.yawToDirection(shooter.getLocation().getYaw(), false);
		switch (direction){
		case EAST_NORTH_EAST: 
			loc.add(amount, 0.0D, 0.0D);
			break;
		case EAST_SOUTH_EAST: 
			loc.add(0.0D, 0.0D, amount);
			break;
		default: 
	    	loc.add(amount, 0.0D, amount);
		}
		switch (direction){
		case DOWN: 
			loc.add(0.0D, 0.0D, -forward);
			break;
		case EAST_NORTH_EAST: 
			loc.add(0.0D, 0.0D, forward);
			break;
		case EAST: 
			loc.add(forward, 0.0D, 0.0D);
			break;
		case EAST_SOUTH_EAST: 
			loc.add(-forward, 0.0D, 0.0D);
			break;
		case NORTH_NORTH_EAST: 
			loc.add(forward, 0.0D, -forward);
		case SELF: 
			loc.add(-forward, 0.0D, forward);
		case NORTH_NORTH_WEST: 
			loc.add(-forward, 0.0D, -forward);
	    default: 
	    	loc.add(forward, 0.0D, forward);
	    }
	    Arrow arrow = (Arrow)loc.getWorld().spawnEntity(loc, EntityType.ARROW);
	    arrow.setVelocity(velocity);
	    arrow.setShooter(shooter);
	    arrow.setFireTicks(fireTicks);
	    if(EnchantUtil.hasEnchant(bow, EnchantAPI.getRegisteredEnchant("Frostbite"))) EnchantListener.frostbite.put(arrow.getEntityId(), EnchantUtil.getLevel(bow, "Frostbite"));
	    if(EnchantUtil.hasEnchant(bow, EnchantAPI.getRegisteredEnchant("Poison"))) EnchantListener.poison.put(arrow.getEntityId(), EnchantUtil.getLevel(bow, "Poison"));
	}

}
