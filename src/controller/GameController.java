package controller;

import GUI.*;
import engine.*;

public class GameController {
    Game currentGame;
    Player player1, player2;

    public GameController() throws Exception {
        player1 = new Player("Yes");
        player2 = new Player("No");
        currentGame = new Game(player1, player2);
        new cleanSelectChampion(currentGame.getAvailableChampions());
        
    }

    public static void main(String[] args) throws Exception {
        new GameController();
    }
}
