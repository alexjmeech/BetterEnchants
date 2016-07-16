package me.alexthecoder.betterenchants.api;

import java.util.Collection;
import java.util.HashMap;

import me.alexthecoder.betterenchants.BetterEnchants;
import me.alexthecoder.betterenchants.enchant.StockEnchant;

import org.bukkit.event.HandlerList;

public class EnchantAPI
{
	private static HashMap<CustomEnchant, EnchantType> RegisteredEnchants;
	private static HashMap<CustomArmorBuff, EnchantType> RegisteredArmorBuffs;
	
	public static void initialize(BetterEnchants plugin)
	{
		RegisteredEnchants = new HashMap<>();
		RegisteredArmorBuffs = new HashMap<>();
		for (StockEnchant e : StockEnchant.values())
		{
			CustomEnchant ce = e.getNew();
			RegisteredEnchants.put(e.getNew(), EnchantType.STOCK);
			if (e.isArmorBuff())
			{
				RegisteredArmorBuffs.put((CustomArmorBuff)ce, EnchantType.STOCK);
			}
		}
	}
	
	public static void disable(BetterEnchants plugin)
	{
		for (CustomEnchant e : RegisteredEnchants.keySet())
		{
			HandlerList.unregisterAll(e);
		}
		RegisteredArmorBuffs.clear();
		RegisteredArmorBuffs = null;
		RegisteredEnchants.clear();
		RegisteredEnchants = null;
	}
	
	public static void registerCustomEnchant(CustomEnchant e)
	{
		RegisteredEnchants.put(e, EnchantType.CUSTOM);
		if (e instanceof CustomArmorBuff)
		{
			RegisteredArmorBuffs.put((CustomArmorBuff)e, EnchantType.CUSTOM);
		}
	}
	
	public static void unregisterCustomEnchant(CustomEnchant e)
	{
		if(RegisteredEnchants.containsKey(e))
		{
			if(RegisteredEnchants.get(e) != EnchantType.STOCK)
			{
				RegisteredEnchants.remove(e);
				if (e instanceof CustomArmorBuff)
				{
					RegisteredArmorBuffs.remove((CustomArmorBuff)e);
				}
			}
		}
		HandlerList.unregisterAll(e);
	}
	
	public static CustomEnchant getRegisteredEnchant(String name)
	{
		for (CustomEnchant e : RegisteredEnchants.keySet())
		{
			if (e.getName().replaceAll(" ", "_").equalsIgnoreCase(name.replaceAll(" ", "_")))
			{
				return e;
			}
		}
		return null;
	}
	
	public static Collection<CustomEnchant> getRegisteredEnchantments()
	{
		return RegisteredEnchants.keySet();
	}
	
	public static Collection<CustomArmorBuff> getRegisteredArmorBuffs()
	{
		return RegisteredArmorBuffs.keySet();
	}
	
	private enum EnchantType
	{
		STOCK, CUSTOM;
	}
}