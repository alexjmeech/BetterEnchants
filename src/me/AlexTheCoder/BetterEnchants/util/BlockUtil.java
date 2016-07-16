package me.alexthecoder.betterenchants.util;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.google.common.collect.Lists;

public class BlockUtil
{
	public static List<Block> getCube(Location loc, Integer radius)
	{
		List<Block> blocks = Lists.newArrayList();
		
	    // Loop through all blocks within the radius (cube, not sphere)
	    for (int x = (radius * -1) - 1; x <= (radius + 1); x++)
	    {
	        for (int y = (radius * -1); y <= radius; y++)
	        {
	            for (int z = (radius * -1) - 1; z <= (radius + 1); z++)
	            {
	                // Grab the current block
	                Block b = loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
	                
	                if (!b.getType().equals(Material.AIR) && (b != null))
	                {
	                	blocks.add(b);
	                }
	            }
	        }
	    }
		
		return blocks;
	}
	
	private static List<Block> getBlocks(Location base, int changeX, int changeY, int changeZ)
	{
		List<Block> blocks = Lists.newArrayList();
	    for (int x = (base.getBlockX() - changeX); x <= (base.getBlockX() + changeX); x++)
	    {
	        for (int y = (base.getBlockY() - changeY); y <= (base.getBlockY() + changeY); y++)
	        {
	            for (int z = (base.getBlockZ() - changeZ); z <= (base.getBlockZ() + changeZ); z++)
	            {
	            	Location loc = new Location(base.getWorld(), x, y, z);
	                Block b = loc.getBlock();
	                
	                if (!b.getType().equals(Material.AIR) && (b != null))
	                {
	                	blocks.add(b);
	                }
	            }
	        }
	    }
	    
	    return blocks;
	}
	
	public static List<Block> getSquare(Block b, BlockFace face)
	{
		List<Block> blocks = Lists.newArrayList();
		switch(face)
		{
		case DOWN:
			blocks.addAll(getBlocks(b.getLocation(), 1, 0, 1));
			break;
		case EAST:
			blocks.addAll(getBlocks(b.getLocation(), 0, 1, 1));
			break;
		case NORTH:
			blocks.addAll(getBlocks(b.getLocation(), 1, 1, 0));
			break;
		case SOUTH:
			blocks.addAll(getBlocks(b.getLocation(), 1, 1, 0));
			break;
		case UP:
			blocks.addAll(getBlocks(b.getLocation(), 1, 0, 1));
			break;
		case WEST:
			blocks.addAll(getBlocks(b.getLocation(), 0, 1, 1));
			break;
		default:
			break;
		}
		
		return blocks;
	}
	
	public static List<Block> getSquare(Block b, BlockFace face, int infusionLevel)
	{
		List<Block> blocks = Lists.newArrayList();
		switch(face)
		{
		case DOWN:
			blocks.addAll(getBlocks(b.getLocation(), infusionLevel, 0, infusionLevel));//1
			break;
		case EAST:
			blocks.addAll(getBlocks(b.getLocation(), 0, infusionLevel, infusionLevel));
			break;
		case NORTH:
			blocks.addAll(getBlocks(b.getLocation(), infusionLevel, infusionLevel, 0));
			break;
		case SOUTH:
			blocks.addAll(getBlocks(b.getLocation(), infusionLevel, infusionLevel, 0));
			break;
		case UP:
			blocks.addAll(getBlocks(b.getLocation(), infusionLevel, 0, infusionLevel));
			break;
		case WEST:
			blocks.addAll(getBlocks(b.getLocation(), 0, infusionLevel, infusionLevel));
			break;
		default:
			break;
		}
		
		return blocks;
	}
}