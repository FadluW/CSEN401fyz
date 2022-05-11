package model.effects;

import model.abilities.Ability;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.world.Champion;

import java.util.ArrayList;

public class PowerUp extends Effect{
	public PowerUp(int duration) {
		super("PowerUp", duration, EffectType.BUFF);
	}
	public void apply(Champion c){
		c.getAppliedEffects().add(this);
		ArrayList<Ability> abilities = c.getAbilities();
		for(Ability a : abilities){
			String abilityType = a.getName();
			switch (abilityType){
				case "HealingAbility":
					HealingAbility ha = (HealingAbility) a;
					ha.setHealAmount((int) (ha.getHealAmount()*1.2));
					break;
				case "DamagingAbility":
					DamagingAbility da = (DamagingAbility) a;
					da.setDamageAmount((int) (da.getDamageAmount()*1.2));
					break;
			}
		}
	}

	@Override
	public void remove(Champion c) {
		c.getAppliedEffects().remove(this);
		ArrayList<Ability> abilities = c.getAbilities();
		for(Ability a : abilities){
			String abilityType = a.getName();
			switch (abilityType){
				case "HealingAbility":
					HealingAbility ha = (HealingAbility) a;
					ha.setHealAmount((int) (ha.getHealAmount()*0.8));
					break;
				case "DamagingAbility":
					DamagingAbility da = (DamagingAbility) a;
					da.setDamageAmount((int) (da.getDamageAmount()*0.8));
					break;
			}
		}
	}
}
