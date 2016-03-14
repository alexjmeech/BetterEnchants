package me.AlexTheCoder.BetterEnchants.crystal;

import java.util.Map;

import me.AlexTheCoder.BetterEnchants.API.CustomEnchant;
import me.AlexTheCoder.BetterEnchants.util.CrystalUtil;
import me.AlexTheCoder.BetterEnchants.util.EnchantUtil;
import me.AlexTheCoder.BetterEnchants.util.FormatUtil;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CrystalEnchantMenu {
	
	public static String INVENTORY_NAME = FormatUtil.format("&r&6&oEnchant an item!", new Object[0]);
	
	public Inventory inv;
	private Map<CustomEnchant, Integer> enchants;
	private ItemStack crystal;
	public int total;
	
	public CrystalEnchantMenu(Map<CustomEnchant, Integer>enchants, ItemStack crystal) {
		inv = Bukkit.getServer().createInventory(null, 9 * 4, INVENTORY_NAME);
		
		this.enchants = enchants;
		this.crystal = crystal;
		total = 0;
	}
	
	public void applyEnchants(Player player, int slot) {
		if (this.crystal.getAmount() > 1) {
			for (ItemStack itm : player.getInventory().getContents()) {
				if (CrystalUtil.isCrystal(itm)) {
					boolean same = true;
					for (Map.Entry<CustomEnchant, Integer> entry : CrystalUtil.getEnchantsFromCrystal(itm).entrySet()) {
						if ((!this.enchants.containsKey(entry.getKey())) || (this.enchants.get(entry.getKey()) != entry.getValue())) {
							same = false;
							break;
						}
					}
					if (same) {
						itm.setAmount(itm.getAmount() - 1);
						break;
					}
				}
			}
		} else {
			player.getInventory().removeItem(new ItemStack[] { this.crystal });
		}
		player.setLevel(player.getLevel() - 20);
		player.closeInventory();
		player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
		for (Map.Entry<CustomEnchant, Integer> entry : this.enchants.entrySet()) {
			EnchantUtil.addEnchant(player.getInventory().getItem(slot), (CustomEnchant)entry.getKey(), ((Integer)entry.getValue()).intValue(), player);
		}
		player.updateInventory();
	}
	
	public void addItem() {
		total = total + 1;
	}
	
	public void show(Player p) {
		p.openInventory(inv);
	}

}
