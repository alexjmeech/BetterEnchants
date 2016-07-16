package me.alexthecoder.betterenchants.enchant;

import java.util.HashMap;

import me.alexthecoder.betterenchants.api.CustomEnchant;

public enum StockEnchant
{
	ANTITOXIN(AntitoxinEnchant.class, false),
	BLAZING_TOUCH(BlazingTouchEnchant.class, false),
	CRANIAL_STRIKE(CranialStrikeEnchant.class, false),
	DECAPITATION(DecapitationEnchant.class, false),
	FROSTBITE(FrostbiteEnchant.class, false),
	HIGHLANDER(HighlanderEnchant.class, true),
	INFUSION(InfusionEnchant.class, false),
	LIFESTEAL(LifestealEnchant.class, false),
	MEDITATION(MeditationEnchant.class, true),
	MULTISHOT(MultishotEnchant.class, false),
	PARALYZE(ParalyzeEnchant.class, false),
	POISON(PoisonEnchant.class, false),
	SATURATION(SaturationEnchant.class, false),
	SHELLSHOCK(ShellshockEnchant.class, false),
	STAGGERING_BLOW(StaggeringBlowEnchant.class, false),
	WITHER_ASPECT(WitherAspectEnchant.class, false)
	;
	
	private CustomEnchant _base;
	private boolean _armorBuff;

	private StockEnchant(Class<? extends CustomEnchant> base, boolean armorBuff)
	{
		_armorBuff = armorBuff;
		try
		{
			_base = base.newInstance();
		}
		catch (InstantiationException ex)
		{
			System.err.println("Is the constructor for " + toString() + " using no argument?");
			ex.printStackTrace();
			return;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return;
		}
	}
	
	public boolean isArmorBuff()
	{
		return _armorBuff;
	}

	public CustomEnchant getNew()
	{
		return _base;
	}
	
	public HashMap<String, Object> getDefaultMapping()
	{
		HashMap<String, Object> map = new HashMap<>();
		
		switch (this)
		{
		case ANTITOXIN:
			map.put("ReductionMult", .10);
			break;
		case CRANIAL_STRIKE:
			map.put("DurationMult", 1);
			map.put("ProbabilityMult", 2.5);
			break;
		case DECAPITATION:
			map.put("ProbabilityMult", 25.0);
			break;
		case FROSTBITE:
			map.put("LevelMult", 1);
			map.put("Duration", 5);
			break;
		case HIGHLANDER:
			map.put("DisableInCombat", false);
			break;
		case INFUSION:
			map.put("LevelMult", 1);
			break;
		case LIFESTEAL:
			map.put("ProbabilityMult", 2.5);
			map.put("DamageMult", .10);
			break;
		case MEDITATION:
			map.put("DisableInCombat", true);
			break;
		case PARALYZE:
			map.put("ProbabilityMult", 2.5);
			map.put("DurationBoost", 1);
			break;
		case POISON:
			map.put("BaseDuration", 5);
			break;
		case SHELLSHOCK:
			map.put("ProbabilityMult", 2.5);
			map.put("DamageMult", 2.0);
			break;
		case STAGGERING_BLOW:
			map.put("ProbabilityMult", 2.5);
			map.put("Duration", 5);
			break;
		case WITHER_ASPECT:
			map.put("ProbabilityMult", 2.5);
			map.put("EffectLevel", 1);
			map.put("DurationBoost", 1);
			break;
		default:
			break;
		}
		
		return map;
	}
}