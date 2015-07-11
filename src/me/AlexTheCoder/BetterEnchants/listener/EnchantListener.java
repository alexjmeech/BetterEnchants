package me.AlexTheCoder.BetterEnchants.listener;

import java.util.concurrent.ConcurrentHashMap;

import me.AlexTheCoder.BetterEnchants.API.CustomEnchant;
import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.enchant.AntitoxinHandler;
import me.AlexTheCoder.BetterEnchants.enchant.BlazingTouchHandler;
import me.AlexTheCoder.BetterEnchants.enchant.CranialStrikeHandler;
import me.AlexTheCoder.BetterEnchants.enchant.DecapitationHandler;
import me.AlexTheCoder.BetterEnchants.enchant.FrostbiteHandler;
import me.AlexTheCoder.BetterEnchants.enchant.InfectedBladeHandler;
import me.AlexTheCoder.BetterEnchants.enchant.InfusionHandler;
import me.AlexTheCoder.BetterEnchants.enchant.LifestealHandler;
import me.AlexTheCoder.BetterEnchants.enchant.MultishotHandler;
import me.AlexTheCoder.BetterEnchants.enchant.ParalyzeHandler;
import me.AlexTheCoder.BetterEnchants.enchant.PoisonHandler;
import me.AlexTheCoder.BetterEnchants.enchant.ShellshockHandler;
import me.AlexTheCoder.BetterEnchants.enchant.StaggeringBlowHandler;
import me.AlexTheCoder.BetterEnchants.enchant.WitherAspectHandler;
import me.AlexTheCoder.BetterEnchants.util.EnchantUtil;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EnchantListener implements Listener{
	
	public static ConcurrentHashMap<Integer, Integer> frostbite = new ConcurrentHashMap<Integer, Integer>();
	public static ConcurrentHashMap<Integer, Integer> poison = new ConcurrentHashMap<Integer, Integer>();
	
	@EventHandler(ignoreCancelled = true)
	public void onPotionSplash(PotionSplashEvent e) {
		for(LivingEntity entity : e.getAffectedEntities()) {
			if(entity instanceof Player) {
				Player p = (Player)entity;
				boolean hasAntitoxin = false;
				for(ItemStack i : p.getInventory().getArmorContents()) {
					if(EnchantUtil.hasEnchant(i, EnchantAPI.getRegisteredEnchant("Antitoxin"))) {
						hasAntitoxin = true;
					}
				}
				if(hasAntitoxin) {
					new AntitoxinHandler(p, e, EnchantUtil.getHighestLevelofArmorEnchant(p.getInventory().getArmorContents(), EnchantAPI.getRegisteredEnchant("Antitoxin")));
				}
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onMine(BlockBreakEvent e) {
		if((e.getPlayer().getItemInHand() != null) && (e.getPlayer().getItemInHand().getType() != Material.AIR)) {
			Player p = e.getPlayer();
			ItemStack i = p.getItemInHand();
			Block b = e.getBlock();
			
			for(CustomEnchant enchant : EnchantUtil.getEnchants(i)) {
				if(enchant.getName().equalsIgnoreCase("Blazing Touch") && (EnchantUtil.getLevel(i, "Blazing Touch") > 0)) {
					if(EnchantUtil.hasEnchant(i, EnchantAPI.getRegisteredEnchant("Infusion"))) {
						new InfusionHandler(p, e, EnchantUtil.getLevel(i, "Infusion"), i, true);
					}else{
						new BlazingTouchHandler(p, b, EnchantUtil.getLevel(i, "Blazing Touch"), i);
					}
					return;
				}
				if(enchant.getName().equalsIgnoreCase("Infusion") && (EnchantUtil.getLevel(i, "Infusion") > 0)) {
					new InfusionHandler(p, e, EnchantUtil.getLevel(i, "Infusion"), i, EnchantUtil.hasEnchant(i, "Blazing Touch"));
				}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled = true)
	public void onCombat(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player p = (Player)event.getDamager();
			if(p.getItemInHand() == null) return;
			if(p.getItemInHand().getType() == Material.AIR) return;
			ItemStack i = p.getItemInHand();
			for(CustomEnchant enchant : EnchantUtil.getEnchants(i)) {
				if(enchant.getName().equalsIgnoreCase("Cranial Strike") && (EnchantUtil.getLevel(i, "Cranial Strike") > 0)) {
					new CranialStrikeHandler(p, (LivingEntity)event.getEntity(), EnchantUtil.getLevel(i, "Cranial Strike"));
				}
				if(enchant.getName().equalsIgnoreCase("Infected Blade") && (EnchantUtil.getLevel(i, "Infected Blade") > 0)) {
					new InfectedBladeHandler(p, (LivingEntity)event.getEntity(), EnchantUtil.getLevel(i, "Infected Blade"));
				}
				if(enchant.getName().equalsIgnoreCase("Lifesteal") && (EnchantUtil.getLevel(i, "Lifesteal") > 0)) {
					new LifestealHandler(p, EnchantUtil.getLevel(i, "Lifesteal"), event.getDamage());
				}
				if(enchant.getName().equalsIgnoreCase("Paralyze") && (EnchantUtil.getLevel(i, "Paralyze") > 0)) {
					new ParalyzeHandler(p, EnchantUtil.getLevel(i, "Paralyze"), (LivingEntity)event.getEntity());
				}
				if(enchant.getName().equalsIgnoreCase("Staggering Blow") && (EnchantUtil.getLevel(i, "Staggering Blow") > 0)) {
					new StaggeringBlowHandler(p, EnchantUtil.getLevel(i, "Staggering Blow"), (LivingEntity)event.getEntity());
				}
				if(enchant.getName().equalsIgnoreCase("Wither Aspect") && (EnchantUtil.getLevel(i, "Wither Aspect") > 0)) {
					new WitherAspectHandler(p, EnchantUtil.getLevel(i, "Wither Aspect"), (LivingEntity)event.getEntity());
				}
			}
		}
		if(event.getDamager() instanceof Arrow) {
			Arrow arrow = (Arrow)event.getDamager();
			if(frostbite.containsKey(arrow.getEntityId())) {
				new FrostbiteHandler((LivingEntity)event.getEntity(), frostbite.get(arrow.getEntityId()));
				frostbite.remove(arrow.getEntityId());
			}
			if(poison.containsKey(arrow.getEntityId())) {
				new PoisonHandler((LivingEntity)event.getEntity(), poison.get(arrow.getEntityId()));
				poison.remove(arrow.getEntityId());
			}
		}
		if(event.getEntity() instanceof Player) {
			Player p = (Player)event.getEntity();
			for(ItemStack armor : p.getInventory().getArmorContents()) {
				if(armor == null) continue;
				if(armor.getType() == Material.AIR) continue;
				if(EnchantUtil.hasEnchant(armor, "Shellshock")) {
					new ShellshockHandler(p, (LivingEntity)event.getDamager(), EnchantUtil.getLevel(armor, "Shellshock"));
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerDie(PlayerDeathEvent e) {
		if(e.getEntity() instanceof Player) {
			Player dead = (Player)e.getEntity();
			if(dead.getKiller() != null) {
				if(dead.getKiller() instanceof Player) {
					Player p = (Player)dead.getKiller();
					if(p.getItemInHand() == null) return;
					if(p.getItemInHand().getType() == Material.AIR) return;
					ItemStack i = p.getItemInHand();
					for(CustomEnchant enchant : EnchantUtil.getEnchants(i)) {
						if(enchant.getName().equalsIgnoreCase("Decapitation") && (EnchantUtil.getLevel(i, "Decapitation") > 0)) {
							new DecapitationHandler(p, dead, e, EnchantUtil.getLevel(i, "Decapitation"));
						}
					}
				}
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onShoot(EntityShootBowEvent event) {
		if(event.getBow() != null) {
			ItemStack i = event.getBow();
			if(!(event.getProjectile() instanceof Arrow)) return;
			for(CustomEnchant enchant : EnchantUtil.getEnchants(i)) {
				if(enchant.getName().equalsIgnoreCase("Frostbite")
						&& (EnchantUtil.getLevel(i, "Frostbite") > 0)
						&& (EnchantUtil.getLevel(i, "Frostbite") <= EnchantAPI.getRegisteredEnchant("Frostbite").getMaxLevel())) {
					frostbite.put(event.getProjectile().getEntityId(), EnchantUtil.getLevel(i, "Frostbite"));
				}
				if(enchant.getName().equalsIgnoreCase("Sharpshooter")
						&& (EnchantUtil.getLevel(i, "Sharpshooter") > 0)
						&& (EnchantUtil.getLevel(i, "Sharpshooter") <= EnchantAPI.getRegisteredEnchant("Sharpshooter").getMaxLevel())) {
					if(event.getEntity() instanceof Player) {
						Player p = (Player)event.getEntity();
						Arrow pro = (Arrow)event.getProjectile();
						pro.setVelocity(p.getEyeLocation().getDirection().normalize().multiply(event.getForce() * (1 + EnchantUtil.getLevel(i, "Sharpshooter"))));
					}
				}
				if(enchant.getName().equalsIgnoreCase("Multishot")
						&& (EnchantUtil.getLevel(i, "Multishot") > 0)
						&& (EnchantUtil.getLevel(i, "Multishot") <= EnchantAPI.getRegisteredEnchant("Multishot").getMaxLevel())) {
					if(event.getEntity() instanceof Player) {
						new MultishotHandler((Player)event.getEntity(), event, EnchantUtil.getLevel(i, "Multishot"));
					}else{
						new MultishotHandler(event, EnchantUtil.getLevel(i, "Multishot"));
					}
				}
				if(enchant.getName().equalsIgnoreCase("Poison")
						&& (EnchantUtil.getLevel(i, "Poison") > 0)
						&& (EnchantUtil.getLevel(i, "Poison") <= EnchantAPI.getRegisteredEnchant("Poison").getMaxLevel())) {
					poison.put(event.getProjectile().getEntityId(), EnchantUtil.getLevel(i, "Poison"));
				}
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onFoodChange(FoodLevelChangeEvent event) {
		if(event.getEntity() instanceof Player) {
			Player p = (Player)event.getEntity();
			boolean hasSaturation = false;
			int level = 0;
			for(ItemStack armor : p.getInventory().getArmorContents()) {
				if(armor == null) continue;
				if(armor.getType() == Material.AIR) continue;
				if(EnchantUtil.hasEnchant(armor, "Saturation")) {
					if(EnchantUtil.getLevel(armor, "Saturation") <= EnchantAPI.getRegisteredEnchant("Saturation").getMaxLevel()) {
						hasSaturation = true;
						level = EnchantUtil.getLevel(armor, "Saturation");
					}
				}
			}
			if(hasSaturation) {
				int deduct = event.getFoodLevel() / (1 + level);
				event.setFoodLevel(event.getFoodLevel() + deduct);
			}
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST, ignoreCancelled = true)
	public void onHit(ProjectileHitEvent event) {
		poison.remove(event.getEntity().getEntityId());
		frostbite.remove(event.getEntity().getEntityId());
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onTagBlockFace(PlayerInteractEvent event) {
		if(event.getPlayer() == null) return;
		if(event.getBlockFace() == null) return;
		if((event.getItem() == null) || (event.getMaterial() == Material.AIR)) return;
		if(!EnchantUtil.hasEnchant(event.getItem(), "Infusion")) return;
		InfusionHandler.updateSavedBlockFace(event.getPlayer(), event.getBlockFace());
	}
	

}
