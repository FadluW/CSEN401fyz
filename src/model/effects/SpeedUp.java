package model.effects;

import model.world.Champion;

public class SpeedUp extends Effect{
	public SpeedUp (int duration) {
		super("SpeedUp", duration, EffectType.BUFF);
	}

	@Override
	public void apply(Champion c) {
		//c.getAppliedEffects().add(this);
		c.setSpeed((int)Math.round  (c.getSpeed()*1.15));
		c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn()+1);
		c.setCurrentActionPoints(c.getCurrentActionPoints()+1);
	}

	@Override
	public void remove(Champion c) {
		c.getAppliedEffects().remove(this);
		c.setSpeed((int) Math.round (c.getSpeed()/1.15));
		c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn()-1);
		c.setCurrentActionPoints(c.getCurrentActionPoints()-1);
	}
}
