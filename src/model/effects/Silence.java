package model.effects;

import exceptions.AbilityUseException;
import model.abilities.Ability;
import model.world.Champion;

import java.util.ArrayList;
import java.util.Collections;

public class Silence extends Effect{
	public Silence (int duration) {
		super("Silence", duration, EffectType.DEBUFF);
	}

	@Override
	public void apply(Champion c) {
		c.getAppliedEffects().add(this);
		c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn()+2);
		c.setCurrentActionPoints(c.getCurrentActionPoints()+2);
	}

	@Override
	public void remove(Champion c) {
		c.getAppliedEffects().remove(this);
		c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn()-2);
		c.setCurrentActionPoints(c.getCurrentActionPoints()-2);
	}
}
