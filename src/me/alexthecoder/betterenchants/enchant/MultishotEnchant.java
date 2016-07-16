package me.alexthecoder.betterenchants.enchant;

import java.util.Random;

import me.alexthecoder.betterenchants.api.CustomEnchant;
import me.alexthecoder.betterenchants.api.EnchantActivateEvent;
import me.alexthecoder.betterenchants.api.EnchantShootArrowEvent;
import me.alexthecoder.betterenchants.api.EnchantmentCategory;
import me.alexthecoder.betterenchants.util.EnchantUtil;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class MultishotEnchant extends CustomEnchant
{
	public MultishotEnchant()
	{
		super("Multishot", EnchantmentCategory.MYTHIC, 2, "BOW", "", "");
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onShootBow(EntityShootBowEvent event)
	{
		if (event.getBow() == null)
		{
			return;
		}
		if (!(event.getEntity() instanceof Player))
		{
			return;
		}
		final ItemStack bow = event.getBow();
		final Player shooter = (Player) event.getEntity();
		final Vector velocity = event.getProjectile().getVelocity().clone();
		final Location loc = event.getProjectile().getLocation().clone();
		final int fireTicks = event.getProjectile().getFireTicks();
		if (EnchantUtil.hasEnchant(bow, this))
		{
			final int level = EnchantUtil.getLevel(bow, getName());
			
			EnchantActivateEvent ac = new EnchantActivateEvent(event.getEntity(), null, false, this);
			Bukkit.getPluginManager().callEvent(ac);
			if (!ac.isCancelled())
			{
				Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("BetterEnchants"), new Runnable()
				{
					public void run()
					{
						for (int i = 0; i < level; i++)
						{
							spawnArrow(loc, shooter, velocity, 0.3f, bow, fireTicks);
						}
					}
				});
			}
		}
	}
	
	private void spawnArrow(Location loc, Player shooter, Vector velocity, float offset, ItemStack bow, int fireTicks)
	{
		loc = modifyLocation(loc, offset);
	    Arrow arrow = (Arrow)loc.getWorld().spawnEntity(loc, EntityType.ARROW);
	    arrow.setVelocity(velocity);
	    arrow.setShooter(shooter);
	    arrow.setFireTicks(fireTicks);
	    EnchantShootArrowEvent event = new EnchantShootArrowEvent(shooter, bow, arrow);
	    Bukkit.getPluginManager().callEvent(event);
	    if (event.isCancelled())
	    {
	    	arrow.remove();
	    }
	}

	private Location modifyLocation(Location loc, float offset)
	{
		Location clone = loc.clone();
		Random rand = new Random();
		if (rand.nextInt(3) == 0)
		{
			clone.setX(loc.getX() + offset);
		}
		else
		{
			clone.setX(loc.getX() - offset);
		}
		if (rand.nextInt(3) == 1)
		{
			clone.setY(loc.getY() + offset);
		}
		else
		{
			clone.setY(loc.getY() - offset);
		}
		if (rand.nextInt(3) == 2)
		{
			clone.setZ(loc.getZ() + offset);
		}
		else
		{
			clone.setZ(loc.getZ() - offset);
		}
		return clone;
	}
}