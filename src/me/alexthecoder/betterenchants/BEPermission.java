package me.alexthecoder.betterenchants;

public enum BEPermission
{
	//Command Permissions
	MAIN_COMMAND("command", false),
	ADD_ENCHANT("addenchant", true),
	GET_CRYSTAL("getcrystal", true),
	LIST_ENCHANTS("listenchants", true),
	GIVE_ITEM("giveitem", true),
	GIVE_CRYSTAL("givecrystal", true),
	//Mechanic Permissions
	BYPASS_CONFLICTS("BypassConflicts", false),
	DEV_ALERT("alert", false)
	;
	
	private static final String PERM_START = "BetterEnchants.";
	private static final String COMMAND_INFIX = "command.";
	
	private String perm;
	private boolean _cmd;
	
	private BEPermission(String permission, boolean command)
	{
		perm = permission;
		_cmd = command;
	}
	
	public String getPermission()
	{
		String prefix = PERM_START;
		if (_cmd)
		{
			prefix = prefix + COMMAND_INFIX;
		}
		return perm;
	}
}