package model.world;

import java.util.ArrayList;

import exceptions.LeaderAbilityAlreadyUsedException;
import model.effects.Stun;

public class AntiHero extends Champion{
    public AntiHero(String name, int maxHP, int mana, int maxActions, int speed, int attackRange, int attackDamage) {
        super(name, maxHP, mana, maxActions, speed, attackRange, attackDamage);
    }

	@Override
	public void useLeaderAbility(ArrayList<Champion> targets) throws LeaderAbilityAlreadyUsedException{
		Stun newStun = new Stun(2);
		for(Champion a : targets){
			a.getAppliedEffects().add(newStun);
		}
		
	}
}
