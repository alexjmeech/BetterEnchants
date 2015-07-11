package me.AlexTheCoder.BetterEnchants.API;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import me.AlexTheCoder.BetterEnchants.Main;
import me.AlexTheCoder.BetterEnchants.enchant.StockArmorBuff;
import me.AlexTheCoder.BetterEnchants.enchant.StockEnchant;

public class EnchantAPI {
	
	private static ConcurrentHashMap<CustomEnchant, EnchantType> registeredEnchants;
	private static ConcurrentHashMap<CustomArmorBuff, EnchantType> registeredArmorBuffs;
	
	public static void initialize(Main plugin) {
		registeredEnchants = new ConcurrentHashMap<CustomEnchant, EnchantType>();
		for(StockEnchant e : StockEnchant.values()) {
			registeredEnchants.put(new CustomEnchant(e.getName(), e.getMaxLevel(), e.getEnchantableItems(), e.getVanillaConflicts(), e.getBetterConflicts(), e.getBaseXpCost()), EnchantType.STOCK);
		}
		registeredArmorBuffs = new ConcurrentHashMap<CustomArmorBuff, EnchantType>();
		for(StockArmorBuff e : StockArmorBuff.values()) {
			registeredArmorBuffs.put(new CustomArmorBuff(e.getEnchant(), e.getPiecesNeeded(), e.getEffect(), e.disableInCombat()), EnchantType.STOCK);
		}
	}
	
	public static void disable(Main plugin) {
		registeredArmorBuffs.clear();
		registeredArmorBuffs = null;
		registeredEnchants.clear();
		registeredEnchants = null;
	}
	
	public static void registerCustomEnchant(CustomEnchant e) {
		registeredEnchants.put(e, EnchantType.CUSTOM);
	}
	
	public static void unregisterCustomEnchant(CustomEnchant e) {
		if(registeredEnchants.containsKey(e)) {
			if(registeredEnchants.get(e) != EnchantType.STOCK) {
				registeredEnchants.remove(e);
			}
		}
	}
	
	public static CustomEnchant getRegisteredEnchant(String name) {
		for(CustomEnchant e : registeredEnchants.keySet()) {
			if(e.getName().replaceAll(" ", "_").equalsIgnoreCase(name.replaceAll(" ", "_"))) {
				return e;
			}
		}
		return null;
	}
	
	public static Collection<CustomEnchant> getRegisteredEnchantments() {
		return registeredEnchants.keySet();
	}
	
	public static void registerCustomArmorBuff(CustomArmorBuff e) {
		registeredArmorBuffs.put(e, EnchantType.CUSTOM);
	}
	
	public static void unregisterCustomArmorBuff(CustomArmorBuff e) {
		if(registeredArmorBuffs.containsKey(e)) {
			if(registeredArmorBuffs.get(e) != EnchantType.STOCK) {
				registeredArmorBuffs.remove(e);
			}
		}
	}
	
	public static CustomArmorBuff getRegisteredArmorBuff(CustomEnchant baseEnchant) {
		for(CustomArmorBuff e : registeredArmorBuffs.keySet()) {
			if(e.getEnchant() == baseEnchant) return e;
		}
		return null;
	}
	
	public static Collection<CustomArmorBuff> getRegisteredArmorBuffs() {
		return registeredArmorBuffs.keySet();
	}
	
	private enum EnchantType {
		STOCK, CUSTOM;
	}

}
