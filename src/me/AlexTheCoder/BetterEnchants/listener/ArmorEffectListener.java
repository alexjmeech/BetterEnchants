package me.AlexTheCoder.BetterEnchants.listener;

import me.AlexTheCoder.BetterEnchants.Main;
import me.AlexTheCoder.BetterEnchants.API.CustomArmorBuff;
import me.AlexTheCoder.BetterEnchants.API.CustomEnchant;
import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.util.CombatTagUtil;
import me.AlexTheCoder.BetterEnchants.util.EnchantUtil;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.projectkorra.ProjectKorra.earthbending.EarthArmor;
import com.projectkorra.ProjectKorra.waterbending.PlantArmor;

public class ArmorEffectListener implements Listener, Runnable {
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onLoadArmorBuffs(PlayerJoinEvent event) {
		updateBuffs(event.getPlayer());
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onUnloadArmorBuffs(PlayerQuitEvent event) {
		updateBuffs(event.getPlayer());
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true)
	public void onAdjustArmorBuffs(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) {
			return;
		}
		Player player = (Player)event.getWhoClicked();
		if (player.getGameMode().equals(GameMode.CREATIVE)) {
			if (event.getInventory().getType().equals(InventoryType.PLAYER)) {}
		} else if (!event.getInventory().getType().equals(InventoryType.CRAFTING)) {
			return;
		}
		updateBuffs(player);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true)
	public void onAdjustArmorBuffs(PlayerItemConsumeEvent event) {
		if (!event.getItem().getType().equals(Material.MILK_BUCKET)) {
			return;
		}
		updateBuffs(event.getPlayer());
	}
	

	@Override
	public void run() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			updateBuffs(p);
		}
	}
	
	public static void updateBuffs(Player player) {
		final Player p = player;
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
			public void run() {
				for (CustomArmorBuff buff : EnchantAPI.getRegisteredArmorBuffs()) {
					if(buff.getDisableInCombat()) {
						if(!CombatTagUtil.hasBeenInCombat(p, 15.0)) {
							if (hasArmorEnchant(p.getInventory().getArmorContents(), buff.getEnchant(), buff.getPiecesNeeded())) {
								p.addPotionEffect(new PotionEffect(buff.getEffect(), 2147483647, EnchantUtil.getHighestLevelofArmorEnchant(p.getInventory().getArmorContents(), buff.getEnchant()) - 1), true);
							} else if (p.hasPotionEffect(buff.getEffect())) {
								for (PotionEffect effect : p.getActivePotionEffects()) {
									if ((effect.getType().equals(buff.getEffect())) && (effect.getDuration() > 9600)) {
										if((effect.getType() == PotionEffectType.DAMAGE_RESISTANCE) && Main.projectKorra) {
											if(!PlantArmor.instances.containsKey(p) && !EarthArmor.instances.containsKey(p))
												p.removePotionEffect(buff.getEffect());
										}else
											p.removePotionEffect(buff.getEffect());
										break;
									}
								}
							}
						}else{
							if (p.hasPotionEffect(buff.getEffect())) {
								for (PotionEffect effect : p.getActivePotionEffects()) {
									if ((effect.getType().equals(buff.getEffect())) && (effect.getDuration() > 9600)) {
										p.removePotionEffect(buff.getEffect());
										break;
									}
								}
							}
						}
					}else{
						if (hasArmorEnchant(p.getInventory().getArmorContents(), buff.getEnchant(), buff.getPiecesNeeded())) {
							p.addPotionEffect(new PotionEffect(buff.getEffect(), 2147483647, EnchantUtil.getHighestLevelofArmorEnchant(p.getInventory().getArmorContents(), buff.getEnchant()) - 1), true);
						} else if (p.hasPotionEffect(buff.getEffect())) {
							for (PotionEffect effect : p.getActivePotionEffects()) {
								if ((effect.getType().equals(buff.getEffect())) && (effect.getDuration() > 9600)) {
									p.removePotionEffect(buff.getEffect());
									break;
								}
							}
						}
					}
				}
			}
		});
	}
	
	private static boolean hasArmorEnchant(ItemStack[] armor, CustomEnchant enchant, int piecesNeeded) {
		int pieces = 0;
		ItemStack[] armors = armor;int amount = armor.length;
		for (int i = 0; i < amount; i++) {
			ItemStack itm = armors[i];
			
			int level = EnchantUtil.getLevel(itm, enchant.getName());
			if ((level != -1) && (level <= enchant.getMaxLevel())) {
				pieces++;
				if (pieces >= piecesNeeded) {
					return true;
				}
			}
		}
		return false;
	}

}
