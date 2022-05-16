package model.abilities;

import java.util.ArrayList;

import model.effects.Effect;
import model.world.Champion;
import model.world.Damageable;

public class CrowdControlAbility extends Ability{
    private Effect effect;

    public Effect getEffect() {
        return effect;
    }

    public CrowdControlAbility(String name, int cost, int baseCooldown, int castRange, AreaOfEffect area, int required, Effect effect) {
    	super(name, cost, baseCooldown, castRange, area, required);
        this.effect = effect;
    }

	@Override
	public void execute(ArrayList<Damageable> targets) throws CloneNotSupportedException {
		for(Damageable a : targets){
			if (a instanceof Champion) {
					Effect effectCopy = (Effect) effect.clone();
					((Champion) a).getAppliedEffects().add(effectCopy);
					effectCopy.apply((Champion) a);
			}
		}
	}
}
