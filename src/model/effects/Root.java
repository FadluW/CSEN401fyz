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
        if(!(c.getCondition().equals(Condition.INACTIVE)))
        	c.setCondition(Condition.ROOTED);
    }

    @Override
    public void remove(Champion c) {
        // Ensure it actually ran out
        if (this.getDuration() < 1) {
            c.getAppliedEffects().remove(this);
            Boolean otherRoot = false;
            for (Effect a : c.getAppliedEffects()) {
                if (a instanceof Root) {
                    otherRoot = true;
                }
            }

            if (c.getCondition() != Condition.INACTIVE) {
                if (!otherRoot){
                    c.setCondition(Condition.ACTIVE);
                }
                else c.setCondition(Condition.ROOTED);
            }
        }
        //else return;

        // TODO: Check for other root

//        else if (otherRoot) {
//            c.setCondition(Condition.ROOTED);
//        }
    }
}
