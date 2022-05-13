package model.effects;

import model.abilities.AreaOfEffect;
import model.abilities.DamagingAbility;
import model.world.Champion;

public class Disarm extends Effect {
    static DamagingAbility p;
//  damage variable is to store the original damaging amount
    public Disarm(int duration) {
    	super("Disarm", duration, EffectType.DEBUFF);
    }
    public void apply(Champion c){
        c.getAppliedEffects().add(this);
        DamagingAbility Punch = new DamagingAbility("Punch",0,1,1, AreaOfEffect.SINGLETARGET,1,50);
        p = Punch;
        c.getAbilities().add(Punch);
    }

    @Override
    public void remove(Champion c) {
        c.getAppliedEffects().remove(this);
        c.getAbilities().remove(p);
        // (TODO) Swap p for a loop checking all abilities for punch
    }
}
