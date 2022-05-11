package model.effects;

import model.world.Champion;
import model.world.Condition;

public class Stun extends Effect{
	static Condition condition;
	public Stun(int duration) {
		super("Stun", duration, EffectType.DEBUFF);
	}

	@Override
	public void apply(Champion c) {
		c.getAppliedEffects().add(this);
		condition = c.getCondition();
		c.setCondition(Condition.INACTIVE);
	}

	@Override
	public void remove(Champion c) {
		c.getAppliedEffects().remove(this);
		c.setCondition(condition);
		// not sure of this line

	}
}
