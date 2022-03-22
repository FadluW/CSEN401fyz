package engine;

import model.abilities.Ability;
import model.world.Champion;

import java.util.ArrayList;

public class Game {
    private Player firstPlayer;
    private Player secondPlayer;
    private boolean firstLeaderAbilityUsed;
    private boolean secondLeaderAbilityUsed;
    private Object[][] board;
    private static ArrayList<Champion> availableChampions;
    private static ArrayList<Ability> availableAbilities;
    private PriorityQueue turnOrder;
    private static int BOARDHEIGHT;
    private static int BOARDWIDTH;

    public static int getBOARDWIDTH() {
        return BOARDWIDTH;
    }

    public static int getBOARDHEIGHT() {
        return BOARDHEIGHT;
    }

    public PriorityQueue getTurnOrder() {
        return turnOrder;
    }

    public static ArrayList<Ability> getAvailableAbilities() {
        return availableAbilities;
    }

    public static ArrayList<Champion> getAvailableChampions() {
        return availableChampions;
    }

    public Object[][] getBoard() {
        return board;
    }

    public Game(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        board = new Object[5][5];
        BOARDHEIGHT = 1;
        BOARDWIDTH = 1;
        availableChampions = new ArrayList<>();
        availableAbilities = new ArrayList<>();
        firstLeaderAbilityUsed = false;
        secondLeaderAbilityUsed = false;
        turnOrder = new PriorityQueue(6);

    }

    public boolean isSecondLeaderAbilityUsed() {
        return secondLeaderAbilityUsed;
    }

    public boolean isFirstLeaderAbilityUsed() {
        return firstLeaderAbilityUsed;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }
}
