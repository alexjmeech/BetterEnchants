package me.alexthecoder.betterenchants.util.command;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Lists;

public class CommandManager implements Listener
{
	private JavaPlugin _plugin;
	private SimpleCommandMap _commandMap;
	private List<String> _registered = Lists.newArrayList();

	public CommandManager()
	{
		try
		{
			_commandMap = (SimpleCommandMap) Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap", (Class<?>[]) new Class[0]).invoke(Bukkit.getServer());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void disable()
	{
		List<String> registered = Lists.newArrayList();
		registered.addAll(_registered);
		for (String label : registered)
		{
			removeCommand(label);
		}
	}

	public void registerCommand(Command command) throws Exception
	{
		PluginCommand cmd = Bukkit.getServer().getPluginCommand(command.getLabel().toLowerCase());
		if (cmd == null)
		{
			final Constructor<?> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
			constructor.setAccessible(true);
			cmd = (PluginCommand) constructor.newInstance(command.getLabel().toLowerCase(), _plugin);
		}
		cmd.setExecutor(command);
		final List<String> list = Arrays.asList(command.getAliases());
		cmd.setAliases(list);
		if (cmd.getAliases() != null)
		{
			for (final String alias : cmd.getAliases())
			{
				removeCommand(alias);
			}
		}

		_commandMap.register(command.getLabel().toLowerCase(), cmd);
		_registered.add(command.getLabel().toLowerCase());
	}

	@SuppressWarnings("unchecked")
	public void removeCommand(String label)
	{
		try
		{
			final Field known = SimpleCommandMap.class.getDeclaredField("knownCommands");
			final Field alias = SimpleCommandMap.class.getDeclaredField("aliases");
			known.setAccessible(true);
			alias.setAccessible(true);
			final Map<String, Command> knownCommands = (Map<String, Command>) known.get(_commandMap);
			final Set<String> aliases = (Set<String>) alias.get(_commandMap);
			knownCommands.remove(label.toLowerCase());
			aliases.remove(label.toLowerCase());
			_registered.remove(label.toLowerCase());
		}
		catch (Exception ignored) {}
	}
}