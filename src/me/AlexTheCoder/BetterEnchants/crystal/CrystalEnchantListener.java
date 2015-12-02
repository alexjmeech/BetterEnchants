package me.AlexTheCoder.BetterEnchants.crystal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.AlexTheCoder.BetterEnchants.API.CustomEnchant;
import me.AlexTheCoder.BetterEnchants.config.HandleActive;
import me.AlexTheCoder.BetterEnchants.util.CrystalUtil;
import me.AlexTheCoder.BetterEnchants.util.EnchantUtil;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CrystalEnchantListener implements Listener {
	
	private static ConcurrentHashMap<Player, CrystalEnchantMenu> instances = new ConcurrentHashMap<Player, CrystalEnchantMenu>();
	
	@EventHandler(ignoreCancelled = true)
	public void onOpenCrystalGUI(PlayerInteractEvent event) {
		if ((!event.getAction().equals(Action.RIGHT_CLICK_AIR)) && (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			return;
		}
		Map<CustomEnchant, Integer> enchants = CrystalUtil.getEnchantsFromCrystal(event.getItem());
		if (enchants == null) {
			return;
		}
		CrystalEnchantMenu menu = new CrystalEnchantMenu(enchants, event.getItem());
		for (int i = 0; i < event.getPlayer().getInventory().getContents().length; i++) {
			ItemStack itm = event.getPlayer().getInventory().getContents()[i];
			if ((itm != null) && (!itm.getType().equals(Material.AIR)) && (!CrystalUtil.isCrystal(itm))) {
				boolean skip = false;
				for (CustomEnchant enchant : enchants.keySet()) {
					if (!enchant.isEnchantable(itm.getType())) {
						skip = true;
						break;
					}
				}
				if (!skip) {
					for (CustomEnchant e : EnchantUtil.getEnchants(itm)) {
						if (enchants.containsKey(e)) {
							skip = true;
							break;
						}
					}
					if (!skip) {
						for (Enchantment enchant : itm.getEnchantments().keySet()) {
							for(CustomEnchant e : enchants.keySet()) {
								if(e.willConflict(enchant)) skip = true;
							}
						}
						
						if(!skip) {
							menu.inv.setItem(i, itm);
							menu.addItem();
						}
					}
				}
			}
		}
		if (menu.total == 0) {
			event.getPlayer().sendMessage(ChatColor.RED + "You have no items in your inventory that can be enchanted with this crystal!");
		} else if (event.getPlayer().getLevel() < HandleActive.getInstance().getCrystalXpLevelCost()) {
			if (HandleActive.getInstance().getCrystalXpLevelCost() != 1)
				event.getPlayer().sendMessage(ChatColor.RED + "You require " + HandleActive.getInstance().getCrystalXpLevelCost() + " levels to enchant an item with a crystal!");
			else
				event.getPlayer().sendMessage(ChatColor.RED + "You require " + HandleActive.getInstance().getCrystalXpLevelCost() + " level to enchant an item with a crystal!");
		} else {
			menu.show(event.getPlayer());
			instances.put(event.getPlayer(), menu);
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onPickItem(InventoryClickEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			Player p = (Player)event.getWhoClicked();
			if(!instances.containsKey(p)) return;
			if(event.getInventory().getName().equals(instances.get(p).inv.getName())) {
				if(!event.getClickedInventory().getName().equals(instances.get(p).inv.getName())) {
					event.setCancelled(true);
					return;
				}
				instances.get(p).applyEnchants(p, event.getSlot());
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onClose(InventoryCloseEvent event) {
		if(event.getPlayer() instanceof Player) {
			Player p = (Player)event.getPlayer();
			instances.remove(p);
		}
	}

}
