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
		//c.getAppliedEffects().add(this);
		// condition = c.getCondition();
		c.setCondition(Condition.INACTIVE);
	}

	@Override
	public void remove(Champion c) {
		c.getAppliedEffects().remove(this);

		// TODO: Check for other root
        Boolean otherRoot = false;
        for (Effect a : c.getAppliedEffects()) {
            if (a instanceof Root) {
                otherRoot = true;
                break;
            }
        }
		c.setCondition((otherRoot) ? Condition.ROOTED : Condition.ACTIVE);
	}
}
