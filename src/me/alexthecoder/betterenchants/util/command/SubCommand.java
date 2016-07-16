package me.alexthecoder.betterenchants.util.command;

import org.bukkit.command.CommandSender;

public abstract class SubCommand
{
	private String _label;
	private String[] _aliases;
	private String[] _help;
	private int _requiredArgs;
	String _permission;
	private boolean _playerOnly;
	
	public SubCommand(String label, String[] aliases, String[] help, int requiredArgs, String permission, boolean playerOnly)
	{
		_label = label;
		_aliases = aliases;
		_help = help;
		_requiredArgs = requiredArgs;
		_permission = permission;
		_playerOnly = playerOnly;
	}
	
	public String getLabel()
	{
		return _label;
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
	
	public abstract void run(CommandSender sender, String[] args);
}
