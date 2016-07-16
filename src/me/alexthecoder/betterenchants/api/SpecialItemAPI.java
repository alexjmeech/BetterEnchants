package me.alexthecoder.betterenchants.api;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpecialItemAPI
{
	public static final String DustName = ChatColor.DARK_AQUA + "" + ChatColor.ITALIC + "Enchanted Dust";
	public static final Material DustItem = Material.GLOWSTONE_DUST;
	public static final List<String> DustLore = Arrays.asList(new String[] {ChatColor.GRAY + "Apply to a Crystal to get a 100% success rate!"});
	public static final String PowderName = ChatColor.GREEN + "" + ChatColor.ITALIC + "Upgrade Powder";
	public static final Material PowderItem = Material.SULPHUR;
	public static final List<String> PowderLore = Arrays.asList(new String[] {ChatColor.GRAY + "Apply to a Crystal to upgrade its Enchantment levels!!"});

	public static void giveItem(Player player, ItemType type, int amount)
	{
		player.getInventory().addItem(getItem(type, amount));
	}

	public static boolean isItem(ItemType type, ItemStack test)
	{
		if (test == null)
		{
			return false;
		}
		if (test.getType() != type.getMaterial())
		{
			return false;
		}
		if (!test.hasItemMeta())
		{
			return false;
		}
		if (!test.getItemMeta().hasDisplayName())
		{
			return false;
		}
		if (!test.getItemMeta().getDisplayName().equals(type.getName()))
		{
			return false;
		}
		return true;
	}

	public static ItemStack getItem(ItemType type, int amount)
	{
		ItemStack item = new ItemStack(type.getMaterial(), amount);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(type.getName());
		im.setLore(type.getLore());
		item.setItemMeta(im);

		return item;
	}

	public static enum ItemType
	{
		POWDER(PowderItem, PowderName, PowderLore),
		DUST(DustItem, DustName, DustLore)
		;
		private Material _mat;
		private String _name;
		private List<String> _lore;

		private ItemType(Material base, String name, List<String> lore)
		{
			_mat = base;
			_name = name;
			_lore = lore;
		}

		public String getName()
		{
			return _name;
		}

		public Material getMaterial()
		{
			return _mat;
		}

		public List<String> getLore()
		{
			return _lore;
		}
	}
}