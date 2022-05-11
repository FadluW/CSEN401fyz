package model.effects;

import model.abilities.AreaOfEffect;
import model.abilities.DamagingAbility;
import model.world.Champion;

public class Disarm extends Effect {
    static int damage;
//  damage variable is to store the original damaging amount
    public Disarm(int duration) {
    	super("Disarm", duration, EffectType.DEBUFF);
    }
    public void apply(Champion c){
        c.getAppliedEffects().add(this);
        damage = c.getAttackDamage();
        c.setAttackDamage(0);
        DamagingAbility Punch = new DamagingAbility("Punch",0,1,1, AreaOfEffect.SINGLETARGET,1,50);
    }

    @Override
    public void remove(Champion c) {
        c.getAppliedEffects().remove(this);
        c.setAttackDamage(damage);
    }
}
