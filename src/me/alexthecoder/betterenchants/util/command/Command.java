package me.alexthecoder.betterenchants.util.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class Command implements CommandExecutor
{
	private String _label;
	private String[] _aliases;
	private SubCommand[] _subcommands;
	private String[] _help;
	private int _requiredArgs;
	String _permission;
	private boolean _playerOnly;
	
	public Command(String label, String[] aliases, String[] help, int requiredArgs, String permission, boolean playerOnly)
	{
		_label = label;
		_aliases = aliases;
		_subcommands = new SubCommand[] {};
		_help = help;
		_requiredArgs = requiredArgs;
		_permission = permission;
		_playerOnly = playerOnly;
	}
	
	public Command(String label, String[] aliases, SubCommand[] subcommands, String[] help, int requiredArgs, String permission, boolean playerOnly)
	{
		_label = label;
		_aliases = aliases;
		_subcommands = subcommands;
		_help = help;
		_requiredArgs = requiredArgs;
		_permission = permission;
		_playerOnly = playerOnly;
	}
	
	public String getLabel()
	{
		return _label;
	}
	
	public String[] getAliases()
	{
		return _aliases;
	}
	
	public boolean isAcceptableLabel(String label)
	{
		if (label.equalsIgnoreCase(_label))
			return true;
		
		for (String alias : _aliases)
		{
			if (label.equalsIgnoreCase(alias))
				return true;
		}
		
		return false;
	}
	
	public SubCommand[] getSubcommands()
	{
		return _subcommands;
	}
	
	public String getPermission()
	{
		return _permission;
	}
	
	public boolean isPlayerOnly()
	{
		return _playerOnly;
	}
	
	public String[] getHelpMessages()
	{
		return _help;
	}
	
	public int getRequiredArgs()
	{
		return _requiredArgs;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args)
	{
		if (isPlayerOnly() && !(sender instanceof Player))
		{
			sender.sendMessage(ChatColor.RED + "That command can only be used in-game!");
			return true;
		}
		if (!sender.hasPermission(getPermission()))
		{
			sender.sendMessage(MessageFormatter.noPermission());
			return true;
		}
		
		for (SubCommand sc : getSubcommands())
		{
			if (args.length < 1)
				break;
			if (sc.isAcceptableLabel(args[0]))
			{
				String slabel = args[0];
				String[] sArgs = new String[args.length - 1];
				
				for (int i = 1; i < args.length; i++)
				{
					sArgs[i - 1] = args[i];
				}
				
				if ((args.length - 1) < sc.getRequiredArgs())
				{
					for (String help : MessageFormatter.formatHelpMessages("/" + label + slabel, sc.getHelpMessages(), new SubCommand[] {}))
					{
						sender.sendMessage(help);
					}
					return true;
				}
				sc.run(sender, sArgs);
				return true;
			}
		}
		if (args.length < getRequiredArgs())
		{
			for (String help : MessageFormatter.formatHelpMessages(label, getHelpMessages(), getSubcommands()))
			{
				sender.sendMessage(help);
			}
			return true;
		}
		run(sender, args);
		return true;
	}
	
	public abstract void run(CommandSender sender, String[] args);
}