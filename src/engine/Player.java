package engine;

import model.world.Champion;

import java.util.ArrayList;

public class Player {
    private String name;
    private Champion leader;
    private ArrayList<Champion> team;

    public Player(String name) {
        this.name = name;
        team = new ArrayList<Champion>();
    }

    public ArrayList<Champion> getTeam() {
        return team;
    }

    public String getName() {
        return name;
    }

    public Champion getLeader() {
        return leader;
    }

    public void setLeader(Champion leader) {
        this.leader = leader;
    }
}
