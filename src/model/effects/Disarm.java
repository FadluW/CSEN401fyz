package model.effects;

import model.abilities.*;
import model.world.Champion;

public class Disarm extends Effect {
    public Disarm(int duration) {
    	super("Disarm", duration, EffectType.DEBUFF);
    }
    public void apply(Champion c){
 
        //c.getAppliedEffects().add(this);
        DamagingAbility Punch = new DamagingAbility("Punch",0,1,1, AreaOfEffect.SINGLETARGET,1,50);
        c.getAbilities().add(Punch);
    }

    @Override
    public void remove(Champion c) {
        c.getAppliedEffects().remove(this);

        for (Ability a : c.getAbilities()) {
            if (!(a instanceof DamagingAbility)) continue;

            if (a.getName().equals("Punch")) {
                c.getAbilities().remove((DamagingAbility) a);
                break;
            }
        }
        // (TODO) Swap p for a loop checking all abilities for punch
    }
}
