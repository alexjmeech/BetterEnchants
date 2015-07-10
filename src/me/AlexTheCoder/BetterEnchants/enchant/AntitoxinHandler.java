package me.AlexTheCoder.BetterEnchants.enchant;

import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.PotionSplashEvent;

public class AntitoxinHandler{

	public AntitoxinHandler(LivingEntity entity, Event event, int level) {
		if(entity instanceof Player) {
			if (((Player)entity).getGameMode().equals(GameMode.CREATIVE)) {
				return;
			}
		}
		if(event instanceof PotionSplashEvent) {
			PotionSplashEvent e = (PotionSplashEvent)event;
			double i = e.getIntensity(entity);
			double newIntensity = i - (i * (.10 * level));
			e.setIntensity(entity, newIntensity);
		}
	}
	
	public static int getNewDuration(int old, int level) {
		Double newDuration = old - (old * (.10 * level));
		return Math.round(newDuration.floatValue());
	}

}
