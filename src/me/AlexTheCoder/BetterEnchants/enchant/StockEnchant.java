package me.AlexTheCoder.BetterEnchants.enchant;

public enum StockEnchant {
	
	ANTITOXIN("Antitoxin", "_HELMET _CHESTPLATE _LEGGINGS _BOOTS", "", "", 3, 10),
	BLAZING_TOUCH("Blazing Touch", "_PICKAXE", "SILK_TOUCH", "", 3, 10),
	CRANIAL_STRIKE("Cranial Strike", "_SWORD _AXE", "", "", 3, 10),
	DECAPITATION("Decapitation", "_SWORD _AXE", "LOOT_BONUS_MOBS", "", 3, 10),
	FROSTBITE("Frostbite", "BOW", "ARROW_FIRE", "POISON", 3, 10),
	HIGHLANDER("Highlander", "_HELMET _CHESTPLATE _LEGGINGS _BOOTS", "", "", 3, 10),
	INFECTED_BLADE("Infected Blade", "_SWORD _AXE", "", "", 3, 10),
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
	
	public int getBaseXpCost() {
		return this.baseCost;
	}

}
