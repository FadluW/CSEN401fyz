package model.effects;

import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.world.Champion;

import java.util.ArrayList;
import java.util.Random;

// Class representing the effects in the game
public class Effect implements Cloneable{
    private String name;
    private int duration;
    private EffectType type;

    public Effect(String name, int duration, EffectType type) {
        this.name = name;
        this.duration = duration;
        this.type = type;
    }

    public EffectType getType() {
        return type;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    public void apply(Champion c){
        ArrayList<Effect> appliedEffects = c.getAppliedEffects();
        if(appliedEffects.size() != 0){
            Effect effect = appliedEffects.get(appliedEffects.size()-1);
            switch (effect.getName()){
                case "Disarm":
                    c.setAttackDamage(0);
                    DamagingAbility Punch = new DamagingAbility("Punch",0,1,1, AreaOfEffect.SINGLETARGET,1,50);
                    break;
                case "Dodge":
//                  the commented lines are for having 50% chance dodging attackes bas mesh 3arfa a3melha ezay
//                  Random rand = new Random();
//                  int chance = rand.nextInt(100);
//                  if(chance < 50) c.setCurrentHP(c.getCurrentHP() - );
                    c.setSpeed((int) (c.getSpeed()*1.05));
                    break;
                case "Embrace":
                    c.setCurrentHP(c.getCurrentHP() + (int) (c.getMaxHP()*0.2));
                    c.setMana((int) (c.getMana()*1.2));
                    c.setSpeed((int) (c.getSpeed()*1.2));
                    c.setAttackDamage((int) (c.getAttackDamage()*1.2));
                    break;
                case "PowerUp":
                    ArrayList<Ability> abilities = c.getAbilities();
                    for(Ability a : abilities){
                        String abilityType = a.getName();
                        switch (abilityType){
                            case "HealingAbility":
                                HealingAbility ha = (HealingAbility) a;
                                ha.setHealAmount((int) (ha.getHealAmount()*1.2));
                                break;
                            case "DamagingAbility":
                                DamagingAbility da = (DamagingAbility) a;
                                da.setDamageAmount((int) (da.getDamageAmount()*1.2));
                                break;
                        }
                    }
                    break;
                case "Root":

            }
        }
    }

}
