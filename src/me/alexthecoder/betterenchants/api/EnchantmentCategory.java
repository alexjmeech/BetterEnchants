package me.alexthecoder.betterenchants.api;

import org.bukkit.ChatColor;

public enum EnchantmentCategory
{
	ANCIENT("Ancient", ChatColor.YELLOW, 30),
	MYTHIC("Mythic", ChatColor.DARK_PURPLE, 45)
	;
	
	private String _display;
	private ChatColor _color;
	private int _xp;
	
	private EnchantmentCategory(String display, ChatColor color, int xp)
	{
		_display = display;
		_color = color;
		_xp = xp;
	}
	
	public String getDisplay()
	{
		return _color + _display;
	}
	
	public ChatColor getColor()
	{
		return _color;
	}
	
	public int getXPCost()
	{
		return _xp;
	}
}