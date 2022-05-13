package model.effects;

import exceptions.AbilityUseException;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.world.Champion;

import java.util.ArrayList;
import java.util.Random;

// Class representing the effects in the game
public abstract class Effect implements Cloneable{
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

    public abstract void apply(Champion c) throws AbilityUseException;
    public abstract void remove(Champion c);

}
