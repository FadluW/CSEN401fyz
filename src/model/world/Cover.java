package model.world;

import java.awt.*;
import java.util.Random;

public class Cover {
    private int currentHP;
    private Point location;

    public Point getLocation() {
        return location;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public Cover(int x, int y){
        Random random = new Random();
        currentHP = random.nextInt(1000-100) + 100;
        location = new Point(x,y);
    }

}
