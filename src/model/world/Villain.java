package model.world;

import java.util.ArrayList;

public class Villain extends Champion{
    public Villain(String name, int maxHP, int mana, int maxActions, int speed, int attackRange, int attackDamage) {
        super(name, maxHP, mana, maxActions, speed, attackRange, attackDamage);
    }

	@Override
	public void useLeaderAbility(ArrayList<Champion> targets) {
		for(Champion a : targets){
			if (a.getCurrentHP()/(a.getMaxHP())<0.3) 
				a.setCondition(Condition.KNOCKEDOUT);
		}
	}     
}
