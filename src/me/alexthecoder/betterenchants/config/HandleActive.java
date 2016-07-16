package me.alexthecoder.betterenchants.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.alexthecoder.betterenchants.enchant.StockEnchant;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class HandleActive
{
	private static HandleActive instance;
	private static String path = "Stock.<ENCHANT>.<OPTION>";
	private List<StockEnchant> active;
	private FileConfiguration config;
	
	private HandleActive() {}
	
	private String formatPath(StockEnchant enchant, String option)
	{
		return path.replace("<ENCHANT>", enchant.toString()).replace("<OPTION>", option);
	}
	
	private void handleDefaults()
	{
		Bukkit.getPluginManager().getPlugin("BetterEnchants").getLogger().info("==== Handling Defaults ====");
		
		Bukkit.getPluginManager().getPlugin("BetterEnchants").getLogger().info("Handled Global Defaults");
		
		for (StockEnchant se : StockEnchant.values())
		{
			if (!config.contains(formatPath(se, "Enabled")))
			{
				config.set(formatPath(se, "Enabled"), true);
				Bukkit.getPluginManager().getPlugin("BetterEnchants").saveConfig();
			}
			
			HashMap<String, Object> optionMap = se.getDefaultMapping();
			
			for (String option : optionMap.keySet())
			{
				if (!config.contains(formatPath(se, option)))
				{
					config.set(formatPath(se, option), optionMap.get(option));
				}
			}
			Bukkit.getPluginManager().getPlugin("BetterEnchants").getLogger().info("Handled Defaults for " + se.toString());
		}
		
		Bukkit.getPluginManager().getPlugin("BetterEnchants").saveConfig();
		Bukkit.getPluginManager().getPlugin("BetterEnchants").getLogger().info("==== Finished Handling Defaults ====");
	}
	
	public static HandleActive getInstance()
	{
		if (instance == null)
			instance = new HandleActive();
		
		return instance;
	}
	
	public void onPluginEnable(FileConfiguration config)
	{
		active = new ArrayList<StockEnchant>();
		this.config = config;
		
		handleDefaults();
		
		for (StockEnchant se : StockEnchant.values())
		{
			if (getBoolean(se, "Enabled") == null)
				continue;
			
			if (getBoolean(se, "Enabled"))
			{
				active.add(se);
			}
		}
	}
	
	public boolean isEnabled(StockEnchant enchant)
	{
		return active.contains(enchant);
	}
	
	public Integer getInteger(StockEnchant enchant, String option)
	{
		try
		{
			return config.getInt(formatPath(enchant, option));
		}
		catch (Exception e)
		{
			Bukkit.getPluginManager().getPlugin("BetterEnchants").getLogger().severe("Configuration value at path " + formatPath(enchant, option) + " is missing or not an Integer!");
			return null;
		}
	}
	
	public Boolean getBoolean(StockEnchant enchant, String option)
	{
		try
		{
			return config.getBoolean(formatPath(enchant, option));
		}
		catch (Exception e)
		{
			Bukkit.getPluginManager().getPlugin("BetterEnchants").getLogger().severe("Configuration value at path " + formatPath(enchant, option) + " is missing or not a Boolean!");
			return null;
		}
	}
	
	public Double getDouble(StockEnchant enchant, String option)
	{
		try
		{
			return config.getDouble(formatPath(enchant, option));
		}
		catch (Exception e)
		{
			Bukkit.getPluginManager().getPlugin("BetterEnchants").getLogger().severe("Configuration value at path " + formatPath(enchant, option) + " is missing or not a number!");
			return null;
		}
	}
}