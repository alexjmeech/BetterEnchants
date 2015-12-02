package me.AlexTheCoder.BetterEnchants.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import me.AlexTheCoder.BetterEnchants.Main;
import me.AlexTheCoder.BetterEnchants.enchant.StockEnchant;

import org.bukkit.configuration.file.FileConfiguration;

public class HandleActive {
	
	private static HandleActive instance;
	private static String path = "Stock.<MOVE>.<OPTION>";
	private static String globalPath = "Global.<OPTION>";
	private List<StockEnchant> active;
	private FileConfiguration config;
	
	private HandleActive() {}
	
	private String formatPath(StockEnchant enchant, String option) {
		return path.replace("<MOVE>", enchant.toString()).replace("<OPTION>", option);
	}
	
	private String formatGlobal(String option) {
		return globalPath.replace("<OPTION>", option);
	}
	
	private void handleDefaults() {
		Main.instance.getLogger().info("==== Handling Defaults ====");
		
		if (!config.contains(formatGlobal("CrystalXpLevelCost")))
			config.set(formatGlobal("CrystalXpLevelCost"), 20);
		Main.instance.getLogger().info("Handled Global Defaults");
		
		for (StockEnchant se : StockEnchant.values()) {
			if (!config.contains(formatPath(se, "Enabled"))) {
				config.set(formatPath(se, "Enabled"), true);
				Main.instance.saveConfig();
			}
			
			ConcurrentHashMap<String, Object> optionMap = StockEnchant.getDefaultMapping(se);
			
			for (String option : optionMap.keySet()) {
				if (!config.contains(formatPath(se, option))) {
					config.set(formatPath(se, option), optionMap.get(option));
				}
			}
			Main.instance.getLogger().info("Handled Defaults for " + se.getName());
		}
		
		Main.instance.saveConfig();
		Main.instance.getLogger().info("==== Finished Handling Defaults ====");
	}
	
	public static HandleActive getInstance() {
		if (instance == null)
			instance = new HandleActive();
		
		return instance;
	}
	
	public void onPluginEnable(FileConfiguration config) {
		active = new ArrayList<StockEnchant>();
		this.config = config;
		
		handleDefaults();
		
		for (StockEnchant se : StockEnchant.values()) {
			if (getBoolean(se, "Enabled") == null)
				continue;
			
			if (getBoolean(se, "Enabled")) {
				active.add(se);
			}
		}
	}
	
	public boolean isEnabled(StockEnchant enchant) {
		return active.contains(enchant);
	}
	
	public Integer getInteger(StockEnchant enchant, String option) {
		try {
			return config.getInt(formatPath(enchant, option));
		} catch (Exception e) {
			Main.instance.getLogger().severe("Configuration value at path " + formatPath(enchant, option) + " is missing or not an Integer!");
			return null;
		}
	}
	
	public Boolean getBoolean(StockEnchant enchant, String option) {
		try {
			return config.getBoolean(formatPath(enchant, option));
		} catch (Exception e) {
			Main.instance.getLogger().severe("Configuration value at path " + formatPath(enchant, option) + " is missing or not a Boolean!");
			return null;
		}
	}
	
	public Double getDouble(StockEnchant enchant, String option) {
		try {
			return config.getDouble(formatPath(enchant, option));
		} catch (Exception e) {
			Main.instance.getLogger().severe("Configuration value at path " + formatPath(enchant, option) + " is missing or not a number!");
			return null;
		}
	}
	
	public Integer getCrystalXpLevelCost() {
		return config.getInt(formatGlobal("CrystalXpLevelCost"));
	}
}
