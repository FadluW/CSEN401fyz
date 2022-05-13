package model.world;

import java.util.ArrayList;

import exceptions.LeaderAbilityAlreadyUsedException;
import model.effects.Effect;
import model.effects.EffectType;
import model.effects.Embrace;

public class Hero extends Champion{
    public Hero(String name, int maxHP, int mana, int maxActions, int speed, int attackRange, int attackDamage) {
        super(name, maxHP, mana, maxActions, speed, attackRange, attackDamage);
    }

    //Ezay ne3raf law el target dool mafihomsh opponents?
	@Override
	public void useLeaderAbility(ArrayList<Champion> targets) throws LeaderAbilityAlreadyUsedException {
		Embrace newEmbrace = new Embrace(2);
		for(Champion a : targets){
			for(Effect effect : a.getAppliedEffects()){
				if (effect.getType()==EffectType.DEBUFF) 
					a.getAppliedEffects().remove(effect);
			}
			a.getAppliedEffects().add(newEmbrace);
		}
	}
}
