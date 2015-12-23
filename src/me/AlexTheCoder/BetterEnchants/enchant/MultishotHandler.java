package me.AlexTheCoder.BetterEnchants.enchant;

import me.AlexTheCoder.BetterEnchants.Main;
import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.listener.EnchantListener;
import me.AlexTheCoder.BetterEnchants.util.EnchantUtil;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;

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
	    			MultishotHandler.this.spawnArrow(loc, shooter, velocity, 0.3f, bow, e.getProjectile().getFireTicks());
	    		}else if (level == 2) {
	    			MultishotHandler.this.spawnArrow(loc, shooter, velocity, 0.3f, bow, e.getProjectile().getFireTicks());
	    			MultishotHandler.this.spawnArrow(loc, shooter, velocity, 0.3f, bow, e.getProjectile().getFireTicks());
	    		}
	    	}
	    });
	}
	
	private void spawnArrow(Location loc, LivingEntity shooter, Vector velocity, float offset, ItemStack bow, int fireTicks) {
		loc = modifyLocation(loc, offset);
	    Arrow arrow = (Arrow)loc.getWorld().spawnEntity(loc, EntityType.ARROW);
	    arrow.setVelocity(velocity);
	    arrow.setShooter(shooter);
	    arrow.setFireTicks(fireTicks);
	    if(EnchantUtil.hasEnchant(bow, EnchantAPI.getRegisteredEnchant("Frostbite"))) EnchantListener.frostbite.put(arrow.getEntityId(), EnchantUtil.getLevel(bow, "Frostbite"));
	    if(EnchantUtil.hasEnchant(bow, EnchantAPI.getRegisteredEnchant("Poison"))) EnchantListener.poison.put(arrow.getEntityId(), EnchantUtil.getLevel(bow, "Poison"));
	}

	private Location modifyLocation(Location loc, float offset) {
		Location clone = loc.clone();
		Random rand = new Random();
		if (rand.nextInt(3) == 0) {
			clone.setX(loc.getX() + offset);
		} else {
			clone.setX(loc.getX() - offset);
		}
		if (rand.nextInt(3) == 1) {
			clone.setY(loc.getY() + offset);
		} else {
			clone.setY(loc.getY() - offset);
		}
		if (rand.nextInt(3) == 2) {
			clone.setZ(loc.getZ() + offset);
		} else {
			clone.setZ(loc.getZ() - offset);
		}
		return clone;
	}
}
