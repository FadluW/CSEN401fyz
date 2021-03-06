//A class representing Champions in the game.
package model.world;

import model.abilities.Ability;
import model.effects.Effect;

import java.awt.*;
import java.util.ArrayList;

public abstract class Champion implements Comparable, Damageable{
    private String name;
    private int maxHP;
    private int currentHP;
    private int mana;
    private int maxActionPointsPerTurn;
    private int currentActionPoints;
    private int attackRange;
    private int attackDamage;
    private int speed;
    private ArrayList<Ability> abilities;
    private ArrayList<Effect> appliedEffects;
    private Condition condition;
    private Point location;
    
    public Champion(String name, int maxHP, int mana, int maxActions, int speed, int attackRange, int attackDamage) {
        this.name = name;
        this.maxHP = maxHP;
        this.mana = mana;
        this.maxActionPointsPerTurn = maxActions;
        this.speed = speed;
        this.attackRange = attackRange;
        this.attackDamage = attackDamage;
        condition = Condition.ACTIVE;
        currentHP = maxHP;
        currentActionPoints = maxActions;
        abilities = new ArrayList<Ability>();
        appliedEffects = new ArrayList<Effect>();
        
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public ArrayList<Effect> getAppliedEffects() {
        return appliedEffects;
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public int getCurrentActionPoints() {
        return currentActionPoints;
    }

    public int getMaxActionPointsPerTurn() {
        return maxActionPointsPerTurn;
    }

    public void setMaxActionPointsPerTurn(int maxActionPointsPerTurn) {
        this.maxActionPointsPerTurn = maxActionPointsPerTurn;
    }
    
    public void setMana(int mana) {
        this.mana = mana;
    }
    
    public void setCurrentActionPoints(int currentActionPoints) {
    	if (currentActionPoints > this.maxActionPointsPerTurn) this.currentActionPoints=this.maxActionPointsPerTurn ;
    	else
    		this.currentActionPoints = (currentActionPoints < 0) ? 0 : currentActionPoints;
    }

    public int getMana() {
        return mana;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
    	// Ensure that new HP does not exceed initial max HP
    	if (currentHP > this.maxHP) this.currentHP=this.maxHP ;
    	else
    		this.currentHP = (currentHP < 0) ? 0 : currentHP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public String getName() {
        return name;
    }
    

    public String toString() {
 	   return (this.name);
    }

    @Override
	public int compareTo(Object o) {
		Champion champion = (Champion) o;
		if(this.speed<champion.getSpeed())
			return 1;
		if(this.speed>champion.getSpeed())
			return -1;
		else
			return((this.name).compareTo(champion.getName()));
	}
	
	public abstract void useLeaderAbility(ArrayList<Champion> targets);
	
//Testing law el turn order sa7.. 
//  public static void main(String[] args) {
//		Champion ch = new Champion("Fadlu", 50, 50, 50, 100,50 , 50);
//		Champion ch1 = new Champion("Joey", 50, 50, 50, 101,50 , 50);
//		Champion ch2 = new Champion("Zeina", 50, 50, 50, 98,50 , 50);
//		Champion ch3 = new Champion("Youssef", 50, 50, 50, 100,50 , 50);
//		PriorityQueue p =  new PriorityQueue(5);
//		p.insert(ch1);
//		p.insert(ch2);
//		p.insert(ch3);
//		p.insert(ch);
//		System.out.println(p);
//	}	
}
