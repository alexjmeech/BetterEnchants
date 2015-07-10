package me.AlexTheCoder.BetterEnchants.enchant;

public enum StockEnchant {
	
	ANTITOXIN("Antitoxin", "_HELMET _CHESTPLATE _LEGGINGS _BOOTS", "", "", 3),
	BLAZING_TOUCH("Blazing Touch", "_PICKAXE", "SILK_TOUCH", "", 3),
	CRANIAL_STRIKE("Cranial Strike", "_SWORD _AXE", "", "", 3),
	DECAPITATION("Decapitation", "_SWORD _AXE", "LOOT_BONUS_MOBS", "", 3),
	FROSTBITE("Frostbite", "BOW", "ARROW_FIRE", "POISON", 3),
	HIGHLANDER("Highlander", "_HELMET _CHESTPLATE _LEGGINGS _BOOTS", "", "", 3),
	INFECTED_BLADE("Infected Blade", "_SWORD _AXE", "", "", 3),
	INFUSION("Infusion", "_PICKAXE _SPADE", "SILK_TOUCH", "", 1),
	LIFESTEAL("Lifesteal", "_SWORD _AXE", "", "", 3),
	MEDITATION("Meditation", "_HELMET _CHESTPLATE _LEGGINGS _BOOTS", "", "", 3),
	MULTISHOT("Multishot", "BOW", "", "", 2),
	PARALYZE("Paralyze", "_SWORD _AXE", "", "", 3),
	//PERSEVERANCE("Perseverance", "_HELMET _CHESTPLATE _LEGGINGS _BOOTS", "", "", 3),
	POISON("Poison", "BOW", "ARROW_FIRE", "FROSTBITE", 3),
	SATURATION("Saturation", "_HELMET _CHESTPLATE _LEGGINGS _BOOTS", "", "", 3),
	//SHARPSHOOTER("Sharpshooter", "BOW", "ARROW_DAMAGE", "", 3),
	SHELLSHOCK("Shellshock", "_HELMET _CHESTPLATE _LEGGINGS _BOOTS", "THORNS", "", 3),
	STAGGERING_BLOW("Staggering Blow", "_SWORD _AXE", "", "", 3),
	WITHER_ASPECT("Wither Aspect", "_SWORD _AXE", "FIRE_ASPECT", "", 3)
	;
	
	private String name, enchantables, conflicts, vanillaconflicts;
	private int maxLevel;
	
	private StockEnchant(String name, String enchantableItems, String vanillaConflicts, String betterConflicts, int maxLevel) {
		this.name = name;
		this.enchantables = enchantableItems;
		this.conflicts = betterConflicts;
		this.vanillaconflicts = vanillaConflicts;
		this.maxLevel = maxLevel;
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

}
