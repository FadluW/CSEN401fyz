package model.effects;

import model.abilities.Ability;
import model.world.Champion;

import java.util.ArrayList;
import java.util.Collections;

public class Silence extends Effect{
	static ArrayList<Ability> a;
//	a is to temporarily store abilities when Silence is applied
	public Silence (int duration) {
		super("Silence", duration, EffectType.DEBUFF);
	}

	@Override
	public void apply(Champion c) {
		c.getAppliedEffects().add(this);
		a = c.getAbilities();
		c.getAbilities().clear();
		c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn()+2);
		c.setCurrentActionPoints(c.getCurrentActionPoints()+2);
	}


	@Override
	public void remove(Champion c) {
		c.getAppliedEffects().remove(this);
		//3andy so2al henaaa!
		Collections.copy(c.getAbilities(),a);
		c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn()-2);
		c.setCurrentActionPoints(c.getCurrentActionPoints()-2);
	}
}
