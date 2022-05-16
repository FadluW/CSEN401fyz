package model.world;

import java.util.ArrayList;
import java.util.Collections;

import model.effects.Effect;
import model.effects.EffectType;
import model.effects.Embrace;

public class Hero extends Champion{
    public Hero(String name, int maxHP, int mana, int maxActions, int speed, int attackRange, int attackDamage) {
        super(name, maxHP, mana, maxActions, speed, attackRange, attackDamage);
    }

	@Override
	public void useLeaderAbility(ArrayList<Champion> targets) {
		for(Champion a : targets){
			Embrace newEmbrace = new Embrace(2);

			ArrayList<Integer> toRemoveIndex = new ArrayList<Integer>();
			int x = 0;
			
			for(Effect effect : a.getAppliedEffects()){
				if (effect.getType()==EffectType.DEBUFF) toRemoveIndex.add(x);
				x++;
			}
			Collections.reverse(toRemoveIndex);
			// Remove to be removed
			for (Integer i : toRemoveIndex) {
				Effect e = a.getAppliedEffects().get(i);
				e.remove(a);
				// current.getAppliedEffects().remove(e);
			}
			a.getAppliedEffects().add(newEmbrace);
			newEmbrace.apply(a);
		}
	}
}
