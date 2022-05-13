package model.abilities;

import java.util.ArrayList;

import model.world.Champion;
import model.world.Damageable;

public class DamagingAbility extends Ability{
    private int damageAmount;

    public DamagingAbility(String name, int cost, int baseCooldown, int castRange, AreaOfEffect area, int required, int damageAmount) {
        super(name, cost, baseCooldown, castRange, area, required);
        this.damageAmount = damageAmount;
    }

    public int getDamageAmount() {
        return damageAmount;
    }

    public void setDamageAmount(int damageAmount) {
        this.damageAmount = damageAmount;
    }

	@Override
	public void execute(ArrayList<Damageable> targets) {
		for(Damageable a : targets){
			a.setCurrentHP((a.getCurrentHP()-damageAmount));
		}
	}
}
