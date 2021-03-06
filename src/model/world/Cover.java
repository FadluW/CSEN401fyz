package model.world;

import java.awt.*;
import java.util.Random;

public class Cover implements Damageable{
    private int currentHP;
    private Point location;

    public Point getLocation() {
        return location;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
    	this.currentHP = (currentHP < 0) ? 0 : currentHP;
    }

    public Cover(int x, int y){
        Random random = new Random();
        currentHP = random.nextInt(900) + 100;
        location = new Point(x,y);
    }

}
