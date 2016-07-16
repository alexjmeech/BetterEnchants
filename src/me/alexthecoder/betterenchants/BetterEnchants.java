package me.alexthecoder.betterenchants;

import me.alexthecoder.betterenchants.api.EnchantAPI;
import me.alexthecoder.betterenchants.command.AddEnchantCommand;
import me.alexthecoder.betterenchants.command.BetterEnchantsCommand;
import me.alexthecoder.betterenchants.command.GetCrystalCommand;
import me.alexthecoder.betterenchants.command.GiveCrystalCommand;
import me.alexthecoder.betterenchants.command.GiveItemCommand;
import me.alexthecoder.betterenchants.command.ListEnchantsCommand;
import me.alexthecoder.betterenchants.config.HandleActive;
import me.alexthecoder.betterenchants.crystal.CrystalEnchantListener;
import me.alexthecoder.betterenchants.listener.ArmorEffectListener;
import me.alexthecoder.betterenchants.listener.AuthorCreditListener;
import me.alexthecoder.betterenchants.listener.CombatTagListener;
import me.alexthecoder.betterenchants.listener.CrystalSafetyListener;
import me.alexthecoder.betterenchants.listener.EntityShotListener;
import me.alexthecoder.betterenchants.listener.StarEnchantListener;
import me.alexthecoder.betterenchants.util.command.CommandManager;

import org.bukkit.plugin.java.JavaPlugin;

public class BetterEnchants extends JavaPlugin
{
	public CommandManager CommandManager;
	
	@Override
	public void onEnable()
	{
		HandleActive.getInstance().onPluginEnable(getConfig());
		EnchantAPI.initialize(this);
		registerCommands();
		getServer().getPluginManager().registerEvents(new CombatTagListener(), this);
		getServer().getPluginManager().registerEvents(new ArmorEffectListener(), this);
		getServer().getPluginManager().registerEvents(new CrystalSafetyListener(), this);
		getServer().getPluginManager().registerEvents(new CrystalEnchantListener(), this);
		getServer().getPluginManager().registerEvents(new AuthorCreditListener(), this);
		getServer().getPluginManager().registerEvents(new StarEnchantListener(), this);
		getServer().getPluginManager().registerEvents(new EntityShotListener(), this);
	}
	
	@Override
	public void onDisable()
	{
		disableCommands();
		EnchantAPI.disable(this);
	}
	
	private void registerCommands()
	{
		CommandManager = new CommandManager();
		
		try
		{
			CommandManager.registerCommand(new AddEnchantCommand());
			CommandManager.registerCommand(new BetterEnchantsCommand());
			CommandManager.registerCommand(new GetCrystalCommand());
			CommandManager.registerCommand(new GiveCrystalCommand());
			CommandManager.registerCommand(new GiveItemCommand());
			CommandManager.registerCommand(new ListEnchantsCommand());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void disableCommands()
	{
		CommandManager.disable();
		CommandManager = null;
	}
}