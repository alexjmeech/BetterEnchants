package me.alexthecoder.betterenchants.util;

import org.bukkit.block.BlockFace;

public class YawUtil
{
	public static Float getYaw(BlockFace face)
	{
		switch (face)
		{
		case DOWN:
			return 0.0F;
		case EAST:
			return 90.0F;
		case EAST_NORTH_EAST:
			return 180.0F;
		case EAST_SOUTH_EAST:
			return 270.0F;
		case NORTH:
			return null;
		case NORTH_EAST:
			return null;
		case NORTH_NORTH_EAST:
			return 45.0F;
		case NORTH_NORTH_WEST:
			return 315.0F;
		case NORTH_WEST:
			return 135.0F;
		case SELF:
			return 225.0F;
		case SOUTH:
			return 292.5F;
		case SOUTH_EAST:
			return 337.5F;
		case SOUTH_SOUTH_EAST:
			return 22.5F;
	    case SOUTH_SOUTH_WEST:
	    	return 67.5F;
	    case SOUTH_WEST:
	    	return 112.5F;
	    case UP:
	    	return 157.5F;
	    case WEST:
	    	return 202.5F;
	    case WEST_NORTH_WEST:
	    	return 247.5F;
	    case WEST_SOUTH_WEST:
	    	return null;
	    }
		return null;
	}
	
	public static BlockFace yawToDirection(float yaw, boolean precise)
	{
		yaw -= 90.0F;
		if (yaw < 0.0F)
		{
			yaw += 360.0F;
		}
		yaw %= 360.0F;
		int i = (int)((yaw + 8.0F) / 22.5D);
		if (precise)
		{
			if (i == 15)
			{
				return BlockFace.WEST_SOUTH_WEST;
			}
			if (i == 13)
			{
				return BlockFace.SOUTH_SOUTH_WEST;
			}
			if (i == 11)
			{
				return BlockFace.SOUTH_SOUTH_EAST;
			}
			if (i == 9)
			{
				return BlockFace.EAST_SOUTH_EAST;
			}
			if (i == 7)
			{
				return BlockFace.EAST_NORTH_EAST;
			}
			if (i == 5)
			{
				return BlockFace.NORTH_NORTH_EAST;
			}
			if (i == 3)
			{
				return BlockFace.NORTH_NORTH_WEST;
			}
			if (i == 1)
			{
				return BlockFace.WEST_NORTH_WEST;
			}
		}
		if (i >= 15)
		{
			return BlockFace.WEST;
		}
		if (i >= 13)
		{
			return BlockFace.SOUTH_WEST;
		}
		if (i == 12)
		{
			return BlockFace.SOUTH;
	    }
		if (i >= 9)
		{
			return BlockFace.SOUTH_EAST;
		}
		if (i == 8)
		{
			return BlockFace.EAST;
	    }
	    if (i >= 5)
	    {
	    	return BlockFace.NORTH_EAST;
	    }
	    if (i == 4)
	    {
	    	return BlockFace.NORTH;
	    }
	    if (i >= 1)
	    {
	    	return BlockFace.NORTH_WEST;
	    }
	    return BlockFace.WEST;
	}
}