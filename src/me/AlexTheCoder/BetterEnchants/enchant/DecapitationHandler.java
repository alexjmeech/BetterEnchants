package me.AlexTheCoder.BetterEnchants.enchant;

import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.config.HandleActive;
import me.AlexTheCoder.BetterEnchants.util.MiscUtil;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class DecapitationHandler {
	
	public DecapitationHandler(Player player, Player target, PlayerDeathEvent event, int level) {
		if (getProbability() == null)
			return;
		
		if(MiscUtil.willOccur(getProbability() * level)) {
			if (player.getGameMode().equals(GameMode.CREATIVE)) {
				return;
			}
			if ((level == -1) || (level > EnchantAPI.getRegisteredEnchant("Decapitation").getMaxLevel())) {
				return;
			}
			ItemStack i = new ItemStack(Material.SKULL_ITEM);
			i.setDurability((short)3);
			SkullMeta im = (SkullMeta)i.getItemMeta();
			im.setOwner(target.getName());
			im.setDisplayName(ChatColor.DARK_RED + target.getName() + "'s Decapitated Head");
			i.setItemMeta(im);
			if(event.getDrops().contains(i)) return;
			event.getDrops().add(i);
		}
	}
	
	private Double getProbability() {
		return HandleActive.getInstance().getDouble(StockEnchant.DECAPITATION, "ProbabilityMult");
	}

}
