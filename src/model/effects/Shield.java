package model.effects;

import model.world.Champion;

public class Shield extends Effect{
	public Shield(int duration) {
		super("Shield", duration, EffectType.BUFF);
	}

	@Override
	public void apply(Champion c) {
		//c.getAppliedEffects().add(this);
		c.setSpeed((int) Math.round (c.getSpeed()*1.02));
	}

	@Override
	public void remove(Champion c) {
		c.getAppliedEffects().remove(this);
		c.setSpeed((int)Math.round  (c.getSpeed()/1.02));
	}
}
