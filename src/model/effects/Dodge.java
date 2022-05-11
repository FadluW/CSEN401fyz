package model.effects;

import model.world.Champion;

public class Dodge extends Effect{
	public Dodge(int duration) {
		super("Dodge", duration, EffectType.BUFF);
	}
	public void apply(Champion c){
		c.getAppliedEffects().add(this);
//      the commented lines are for having 50% chance dodging attacks bas mesh 3arfa a3melha ezay
//      Random rand = new Random();
//      int chance = rand.nextInt(100);
//      if(chance < 50) c.setCurrentHP(c.getCurrentHP() - );
		c.setSpeed((int) (c.getSpeed()*1.05));
	}

	@Override
	public void remove(Champion c) {
		c.getAppliedEffects().remove(this);
		c.setSpeed((int) (c.getSpeed()*0.95));
//		should remove the 50% chance to dodge
	}
}
