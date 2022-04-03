package model.abilities;

public class Ability {
    private String name;
    private int manaCost;
    private int baseCooldown;
    private int currentCooldown;
    private int castRange;
    private int requiredActionPoints;
    private AreaOfEffect castArea;

    public void setCurrentCooldown(int currentCooldown) {
<<<<<<< HEAD
    	if (currentCooldown > this.baseCooldown) this.currentCooldown=this.baseCooldown ;
    	else
    		this.currentCooldown = (currentCooldown < 0) ? 0 : currentCooldown;
=======
        this.currentCooldown = (currentCooldown < 0) ? 0 : currentCooldown;
>>>>>>> abc2ce15223f6720d51253ba1d7245116a8609ca
    }

    public AreaOfEffect getCastArea() {
        return castArea;
    }

    public int getRequiredActionPoints() {
        return requiredActionPoints;
    }

    public int getCastRange() {
        return castRange;
    }

    public int getCurrentCooldown() {
        return currentCooldown;
    }

    public int getBaseCooldown() {
        return baseCooldown;
    }

    public int getManaCost() {
        return manaCost;
    }

    public String getName() {
        return name;
    }

    public Ability(String name, int cost, int baseCooldown, int castRange, AreaOfEffect area, int required) {
    	this.name = name;
        this.manaCost = cost;
        this.baseCooldown = baseCooldown;
        this.castRange = castRange;
        this.castArea = area;
        this.requiredActionPoints = required;
    }
}
