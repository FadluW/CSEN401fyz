package model.abilities;

import java.util.ArrayList;
import java.util.Random;
import model.world.Champion;
import model.world.Damageable;
import model.effects.*;;

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
		for(Damageable a : targets) {

            Boolean deflected = false;
            if (a instanceof Champion) {
                Random rand = new Random();
                // Iterate over target's current effects
                ArrayList<Effect> targetEffects = ((Champion) a).getAppliedEffects();
                for (Effect e : targetEffects) {
                    if (e instanceof Shield) {
                        // Remove shield from target
                        e.remove(((Champion) a));
                        deflected = true;
                        break;
                    }
                }
                if (!deflected) {
                    for (Effect e : targetEffects) {
                        if (e instanceof Dodge){
                            int chance = rand.nextInt(100);
                            // If dodged
                            if (chance < 50) deflected = true;
                            break;
                        }
                    }
                }
            }
            
			a.setCurrentHP((a.getCurrentHP() - ((deflected) ? 0 : damageAmount)));
		}
	}
}
