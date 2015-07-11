package me.AlexTheCoder.BetterEnchants;

import me.AlexTheCoder.BetterEnchants.API.EnchantAPI;
import me.AlexTheCoder.BetterEnchants.crystal.CrystalEnchantListener;
import me.AlexTheCoder.BetterEnchants.listener.ArmorEffectListener;
import me.AlexTheCoder.BetterEnchants.listener.AuthorCreditListener;
import me.AlexTheCoder.BetterEnchants.listener.CombatTagListener;
import me.AlexTheCoder.BetterEnchants.listener.CommandListener;
import me.AlexTheCoder.BetterEnchants.listener.CrystalSafetyListener;
import me.AlexTheCoder.BetterEnchants.listener.EnchantListener;
import me.AlexTheCoder.BetterEnchants.listener.StarEnchantListener;
import me.AlexTheCoder.BetterEnchants.util.EnchantGlow;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static Main instance;
	
	@Override
	public void onEnable() {
		instance = this;
		EnchantAPI.initialize(this);
		EnchantGlow.init();
		getServer().getPluginManager().registerEvents(new CombatTagListener(), this);
		getServer().getPluginManager().registerEvents(new CommandListener(), this);
		getServer().getPluginManager().registerEvents(new EnchantListener(), this);
		getServer().getPluginManager().registerEvents(new ArmorEffectListener(), this);
		getServer().getPluginManager().registerEvents(new CrystalSafetyListener(), this);
		getServer().getPluginManager().registerEvents(new CrystalEnchantListener(), this);
		getServer().getPluginManager().registerEvents(new AuthorCreditListener(), this);
		getServer().getPluginManager().registerEvents(new StarEnchantListener(), this);
	}
	
	@Override
	public void onDisable() {
		EnchantAPI.disable(this);
		instance = null;
	}

}
