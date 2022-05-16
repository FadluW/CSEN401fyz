package model.effects;

import model.world.Champion;
import model.world.Condition;

public class Root extends Effect{
    public Root(int duration) {
        super("Root", duration, EffectType.DEBUFF);
    }

    @Override
    public void apply(Champion c) {
        //c.getAppliedEffects().add(this);
        if(!(c.getCondition()==Condition.INACTIVE))
        	c.setCondition(Condition.ROOTED);
    }

    @Override
    public void remove(Champion c) {
        c.getAppliedEffects().remove(this);

        // TODO: Check for other root
        Boolean otherRoot = false;
        for (Effect a : c.getAppliedEffects()) {
            if (a.getName().equals("Root")) {
                otherRoot = true;
                break;
            }
        }

        if (c.getCondition() != Condition.INACTIVE) {
            if (otherRoot) c.setCondition(Condition.ROOTED);
        	else c.setCondition(Condition.ACTIVE);
        } else if (otherRoot) {
            c.setCondition(Condition.ROOTED);
        }
    }
}
