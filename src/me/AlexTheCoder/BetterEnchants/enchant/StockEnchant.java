package me.AlexTheCoder.BetterEnchants.enchant;

import java.util.concurrent.ConcurrentHashMap;

public enum StockEnchant {
	
	ANTITOXIN("Antitoxin", "_HELMET _CHESTPLATE _LEGGINGS _BOOTS", "", "", 3, 10),
	BLAZING_TOUCH("Blazing Touch", "_PICKAXE", "SILK_TOUCH", "", 3, 10),
	CRANIAL_STRIKE("Cranial Strike", "_SWORD _AXE", "", "", 3, 10),
	DECAPITATION("Decapitation", "_SWORD _AXE", "LOOT_BONUS_MOBS", "", 3, 10),
	FROSTBITE("Frostbite", "BOW", "ARROW_FIRE", "POISON", 3, 10),
	HIGHLANDER("Highlander", "_HELMET _CHESTPLATE _LEGGINGS _BOOTS", "", "", 3, 10),
	//INFECTED_BLADE("Infected Blade", "_SWORD _AXE", "", "", 3, 10),
	INFUSION("Infusion", "_PICKAXE _SPADE", "SILK_TOUCH", "", 3, 10),
	LIFESTEAL("Lifesteal", "_SWORD _AXE", "", "", 3, 10),
	MEDITATION("Meditation", "_HELMET _CHESTPLATE _LEGGINGS _BOOTS", "", "", 3, 10),
	MULTISHOT("Multishot", "BOW", "", "", 2, 10),
	PARALYZE("Paralyze", "_SWORD _AXE", "", "", 3, 10),
	//PERSEVERANCE("Perseverance", "_HELMET _CHESTPLATE _LEGGINGS _BOOTS", "", "", 3, 10),
	POISON("Poison", "BOW", "ARROW_FIRE", "FROSTBITE", 3, 10),
	SATURATION("Saturation", "_HELMET _CHESTPLATE _LEGGINGS _BOOTS", "", "", 3, 10),
	//SHARPSHOOTER("Sharpshooter", "BOW", "ARROW_DAMAGE", "", 3, 10),
	SHELLSHOCK("Shellshock", "_HELMET _CHESTPLATE _LEGGINGS _BOOTS", "THORNS", "", 3, 10),
	STAGGERING_BLOW("Staggering Blow", "_SWORD _AXE", "", "", 3, 10),
	WITHER_ASPECT("Wither Aspect", "_SWORD _AXE", "FIRE_ASPECT", "", 3, 10)
	;
	
	private String name, enchantables, conflicts, vanillaconflicts;
	private int maxLevel, baseCost;
	
	private StockEnchant(String name, String enchantableItems, String vanillaConflicts, String betterConflicts, int maxLevel, int baseXpCost) {
		this.name = name;
		this.enchantables = enchantableItems;
		this.conflicts = betterConflicts;
		this.vanillaconflicts = vanillaConflicts;
		this.maxLevel = maxLevel;
		this.baseCost = baseXpCost;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getEnchantableItems() {
		return enchantables;
	}
	
	public String getVanillaConflicts() {
		return vanillaconflicts;
	}
	
	public String getBetterConflicts() {
		return conflicts;
	}
	
	public int getMaxLevel() {
		return this.maxLevel;
	}
	
	public int getDefaultBaseXpCost() {
		return this.baseCost;
	}
	
	public static ConcurrentHashMap<String, Object> getDefaultMapping(StockEnchant se) {
		ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();
		
		map.put("BaseXpLevelCost", se.getDefaultBaseXpCost());
		
		switch (se) {
		case ANTITOXIN:
			map.put("ReductionMult", ((Double).10));
			break;
		case BLAZING_TOUCH:
			break;
		case CRANIAL_STRIKE:
			map.put("DurationMult", ((Integer)1));
			map.put("ProbabilityMult", ((Double)2.5));
			break;
		case DECAPITATION:
			map.put("ProbabilityMult", ((Double)25.0));
			break;
		case FROSTBITE:
			map.put("LevelMult", ((Integer)1));
			map.put("Duration", ((Integer)5));
			break;
		case HIGHLANDER:
			map.put("DisableInCombat", ((Boolean)true));
			break;
		case INFUSION:
			map.put("LevelMult", ((Integer)1));
			break;
		case LIFESTEAL:
			map.put("ProbabilityMult", ((Double)2.5));
			map.put("DamageMult", ((Double).10));
			break;
		case MEDITATION:
			map.put("DisableInCombat", ((Boolean)true));
			break;
		case MULTISHOT:
			break;
		case PARALYZE:
			map.put("ProbabilityMult", ((Double)2.5));
			map.put("DurationBoost", ((Integer)1));
			break;
		case POISON:
			map.put("BaseDuration", ((Integer)5));
			break;
		case SATURATION:
			break;
		case SHELLSHOCK:
			map.put("ProbabilityMult", ((Double)2.5));
			map.put("DamageMult", ((Double)2.0));
			break;
		case STAGGERING_BLOW:
			map.put("ProbabilityMult", ((Double)2.5));
			map.put("Duration", ((Integer)5));
			break;
		case WITHER_ASPECT:
			map.put("ProbabilityMult", ((Double)2.5));
			map.put("EffectLevel", ((Integer)1));
			map.put("DurationBoost", ((Integer)1));
			break;
		default:
			break;
		}
		
		return map;
	}

}
