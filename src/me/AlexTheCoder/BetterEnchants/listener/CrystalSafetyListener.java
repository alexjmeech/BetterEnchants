package me.AlexTheCoder.BetterEnchants.listener;

import java.util.Set;

import me.AlexTheCoder.BetterEnchants.API.CustomEnchant;
import me.AlexTheCoder.BetterEnchants.util.CrystalUtil;
import me.AlexTheCoder.BetterEnchants.util.EnchantUtil;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class CrystalSafetyListener implements Listener {
	
	private ItemStack blockedBeacon = null;
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onMakeBeaconWithCrystal(PrepareItemCraftEvent event) {
		if ((event.getInventory().getResult() == null) || (!event.getInventory().getResult().getType().equals(Material.BEACON))) {
			return;
		}
		if (!CrystalUtil.isCrystal(event.getInventory().getItem(5))) {
			return;
		}
		event.getInventory().setResult(this.blockedBeacon);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onCraftBeaconWithCrystal(CraftItemEvent event) {
		if ((event.getInventory().getResult() == null) || (!event.getInventory().getResult().getType().equals(Material.BEACON))) {
			return;
		}
		if (!CrystalUtil.isCrystal(event.getInventory().getItem(5))) {
			return;
		}
		event.setCancelled(true);
	}
	  
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlaceCrystalInAnvil(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) {
			return;
		}
		if (!event.getInventory().getType().equals(InventoryType.ANVIL)) {
			return;
		}
		if ((!CrystalUtil.isCrystal(event.getCursor())) && (!CrystalUtil.isCrystal(event.getCurrentItem()))) {
			return;
		}
		event.setCancelled(true);
		if ((event.getInventory().getItem(0) == null) && (event.getInventory().getItem(1) == null)) {
			event.getWhoClicked().closeInventory();
		}
		((Player)event.getWhoClicked()).sendMessage(ChatColor.RED + "Right click with the crystal in your hand to use it!");
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onEnchantItemCrystalConflict(EnchantItemEvent event) {
		boolean doesConflict = false;
		for(CustomEnchant e : EnchantUtil.getEnchants(event.getItem())) {
			for(Enchantment ench : event.getEnchantsToAdd().keySet()) {
				if(e.willConflict(ench)) { 
					doesConflict = true;
				}
			}
		}
		if(doesConflict) {
			event.setCancelled(true);
			event.getEnchanter().sendMessage(ChatColor.RED + "One or more of the enchants you were about to obtain would conflict with your crystals, so to save you wasting your experience the enchanting has been cancelled!");
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onWronglyEnchantItem(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) {
			return;
		}
		if (!event.getInventory().getType().equals(InventoryType.ANVIL)) {
			return;
		}
		if (conflictingEnchants(new ItemStack[] { event.getCursor(), event.getCurrentItem(), event.getInventory().getItem(0), event.getInventory().getItem(1) })) {
			event.setCancelled(true);
			((Player)event.getWhoClicked()).sendMessage(ChatColor.RED + "There are some conflicting enchantments on your items!");
		}
	}
	
	/*@EventHandler(ignoreCancelled = true)
	public void onUseAnvil(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) {
			return;
		}
		if (!event.getInventory().getType().equals(InventoryType.ANVIL)) {
			return;
		}
		if ((event.getInventory().getItem(0) == null) || (event.getInventory().getItem(1) == null)) {
			return;
		}
		if (event.getInventory().getItem(2) == null) {
			return;
		}
		if (event.getCurrentItem() != event.getInventory().getItem(2)) {
			return;
		}
		if (!event.getClickedInventory().getName().equals(event.getInventory().getName())) {
			return;
		}
		if (event.getSlot() == 2) {
			ItemStack i = event.getInventory().getItem(0);
			ItemStack ii = event.getInventory().getItem(1);
			ItemStack iii = event.getInventory().getItem(2);
			if (!i.hasItemMeta() && !ii.hasItemMeta()) {
				return;
			}
			List<String> l = new ArrayList<String>();
			List<String> ll = new ArrayList<String>();
			List<String> lll = new ArrayList<String>();
			if(i.hasItemMeta() && i.getItemMeta().hasLore()) {
				for(String s : i.getItemMeta().getLore()) {
					l.add(s);
				}
			}
			if(ii.hasItemMeta() && ii.getItemMeta().hasLore()) {
				for(String s : i.getItemMeta().getLore()) {
					ll.add(s);
				}
			}
			if(l.isEmpty() && ll.isEmpty()) return;
			if(!l.isEmpty())
				for(String s : l) {
					lll.add(s);
				}
			if(!ll.isEmpty())
				for(String s : ll) {
					lll.add(s);
				}
			if(!lll.isEmpty()) {
				ItemMeta im = iii.getItemMeta();
				im.setLore(lll);
				iii.setItemMeta(im);
				((Player)event.getWhoClicked()).sendMessage(ChatColor.GREEN + "Your special enchantments were preserved!");
			}
		}
	}*/
	
	private boolean conflictingEnchants(ItemStack... items) {
		boolean doesConflict = false;
		for (ItemStack itm : items) {
			if ((itm != null) && (!itm.getType().equals(Material.AIR))) {
				Set<Enchantment> enchants = null;
				if (itm.getType().equals(Material.ENCHANTED_BOOK)) {
					enchants = ((EnchantmentStorageMeta)itm.getItemMeta()).getStoredEnchants().keySet();
				} else {
					enchants = itm.getEnchantments().keySet();
				}
				for(CustomEnchant ench : EnchantUtil.getEnchants(itm)) {
					for(Enchantment e : enchants) {
						if(ench.willConflict(e)) doesConflict = true;
					}
				}
			}
		}
		return doesConflict;
	}

}
