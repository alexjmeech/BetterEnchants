package me.alexthecoder.betterenchants.enchant;

import me.alexthecoder.betterenchants.api.CustomEnchant;
import me.alexthecoder.betterenchants.api.EnchantActivateEvent;
import me.alexthecoder.betterenchants.api.EnchantmentCategory;
import me.alexthecoder.betterenchants.config.HandleActive;
import me.alexthecoder.betterenchants.util.EnchantUtil;
import me.alexthecoder.betterenchants.util.MiscUtil;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class DecapitationEnchant extends CustomEnchant
{
	private static final Double PROBABILITY_MULT = HandleActive.getInstance().getDouble(StockEnchant.DECAPITATION, "ProbabilityMult");
	
	public DecapitationEnchant()
	{
		super("Decapitation", EnchantmentCategory.ANCIENT, 3, "_SWORD _AXE", "LOOT_BONUS_MOBS", "");
	}
	
	@EventHandler
	public void onPlayerDie(PlayerDeathEvent e)
	{
		Player dead = e.getEntity();
		if (dead.getKiller() != null)
		{
			if (dead.getKiller() instanceof Player)
			{
				Player p = (Player)dead.getKiller();
				if (p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType() == Material.AIR)
				{
					return;
				}
				if (p.getGameMode() == GameMode.CREATIVE)
				{
					return;
				}
				ItemStack i = p.getInventory().getItemInMainHand();
				if (EnchantUtil.hasEnchant(i, this))
				{
					int level = EnchantUtil.getLevel(i, getName());
					if (!MiscUtil.willOccur(PROBABILITY_MULT * level))
					{
						return;
					}
					EnchantActivateEvent ac = new EnchantActivateEvent(p, null, false, this);
					Bukkit.getPluginManager().callEvent(ac);
					if (!ac.isCancelled())
					{
						ItemStack drop = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
						SkullMeta im = (SkullMeta)drop.getItemMeta();
						im.setOwner(dead.getName());
						im.setDisplayName(ChatColor.DARK_RED + dead.getName() + "'s Decapitated Head");
						i.setItemMeta(im);
						if (e.getDrops().contains(i))
						{
							return;
						}
						e.getDrops().add(i);
					}
				}
			}
		}
	}
}