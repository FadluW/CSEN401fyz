package model.abilities;

import java.util.ArrayList;

import model.world.Damageable;

public class HealingAbility extends Ability{
    private int healAmount;

    public HealingAbility(String name, int cost, int baseCooldown, int castRange, AreaOfEffect area, int required, int healAmount) {
        super(name, cost, baseCooldown, castRange, area, required);
        this.healAmount = healAmount;
    }

    public int getHealAmount() {
        return healAmount;
    }

    public void setHealAmount(int healAmount) {
        this.healAmount = healAmount;
    }
//Does the targets list include opponents
	@Override
	public void execute(ArrayList<Damageable> targets) {
		for(Damageable a : targets){
			a.setCurrentHP((a.getCurrentHP()+healAmount));
		}
	}
}
