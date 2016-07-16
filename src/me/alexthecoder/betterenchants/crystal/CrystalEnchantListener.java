package me.alexthecoder.betterenchants.crystal;

import java.util.Map;
import java.util.Random;

import me.alexthecoder.betterenchants.api.CustomEnchant;
import me.alexthecoder.betterenchants.util.CrystalUtil;
import me.alexthecoder.betterenchants.util.EnchantUtil;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class CrystalEnchantListener implements Listener
{
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPickItem(InventoryClickEvent event)
	{
		if(!(event.getWhoClicked() instanceof Player))
			return;
		
		Player p = (Player)event.getWhoClicked();
		
		if (!CrystalUtil.isCrystal(event.getCursor()))
		{
			return;
		}
		if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR)
		{
			return;
		}
		
		Map<CustomEnchant, Integer> cEnchantments = CrystalUtil.getEnchantsFromCrystal(event.getCursor());
		Double chance = CrystalUtil.getSuccessPercentage(event.getCursor());
		for (CustomEnchant ce : cEnchantments.keySet())
		{
			if (!ce.isEnchantable(event.getCurrentItem().getType()))
			{
				p.sendMessage(ChatColor.RED + "That Crystal cannot be applied to that type of item!");
				return;
			}
			if (EnchantUtil.getEnchants(event.getCurrentItem()).contains(ce))
			{
				p.sendMessage(ChatColor.RED + "That item already has that Enchantment!");
				return;
			}
			for (CustomEnchant test : EnchantUtil.getEnchants(event.getCurrentItem()))
			{
				if (ce.willConflict(test))
				{
					p.sendMessage(ChatColor.RED + "That Crystal will conflict with that item's existing Enchantments!!");
					return;
				}
			}
			for (Enchantment test : event.getCurrentItem().getEnchantments().keySet())
			{
				if (ce.willConflict(test))
				{
					p.sendMessage(ChatColor.RED + "That Crystal will conflict with that item's existing Enchantments!!");
					return;
				}
			}
		}
		event.setCancelled(true);
		event.setCursor(new ItemStack(Material.AIR));
		if (new Random().nextDouble() <= chance)
		{
			for (CustomEnchant ce : cEnchantments.keySet())
			{
				EnchantUtil.addEnchant(event.getCurrentItem(), ce, cEnchantments.get(ce), p);
			}
		}
		p.updateInventory();
	}
}