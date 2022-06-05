package controller;

import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import GUI.*;
import engine.*;
import model.world.AntiHero;
import model.world.Champion;
import model.world.Hero;
import model.world.Villain;

public class GameController {
    Game currentGame;
    Player player1, player2;
    StartScreen screen;
	GameController controller;
	ArrayList<Champion> team1, team2;

	public Game getCurrentGame() {
		return currentGame;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public ArrayList<Champion> getTeam1() {
		return team1;
	}

	public ArrayList<Champion> getTeam2() {
		return team2;
	}

	public GameController() throws Exception {
		controller = this;
//    	screen = new StartScreen(this);
    	player1 = new Player("Joey");
		player2 = new Player("Fadl");

		player1.getTeam().add(new Hero("Dr Strange", 1000,10,2,20,3,3));
		player1.getTeam().add(new AntiHero("Deadpool", 1000,10,2,20,3,3));
		player1.getTeam().add(new Hero("Hulk", 1000,10,2,20,3,3));
		player2.getTeam().add(new Villain("Loki", 1000,10,2,20,3,3));
		player2.getTeam().add(new Hero("Thor", 1000,10,2,20,3,3));
		player2.getTeam().add(new Hero("Iceman", 1000,10,2,20,3,3));
		currentGame = new Game(player1, player2);
		team1 = player1.getTeam();
		team2 = player2.getTeam();
//    	new cleanSelectChampions(currentGame.getAvailableChampions());
		new editingBoard(controller);
    }

    public void setGame(Game game) {
    	this.currentGame = game;
    }
    
    public static void main(String[] args) throws Exception {
        new GameController();
    }
    
	public class BeginListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(((screen.getField()).equals("") || screen.getField2().equals(""))){
				System.out.println("Button Works");
				screen.getError().setVisible(true);
				screen.repaint();
				screen.revalidate();
				new java.util.Timer().schedule(new java.util.TimerTask() {
					@Override
					public void run() {
						screen.getError().setVisible(false);
						screen.repaint();
						screen.revalidate();
					}
				}, 1500);
				//setTimeout(screen.getError().setVisible(false),3000);
					
			}
			else {
				System.out.println("Button Worked");
				player1 = new Player(screen.getField());
				player2 = new Player(screen.getField2());
				try {
					currentGame = new Game(player1, player2);
				} catch (Exception e1) {
				}
				screen.remove(screen.getPanel());
				screen.revalidate();
				screen.repaint();
				try {
					screen.getContentPane().add((new cleanSelectChampion(currentGame, currentGame.getAvailableChampions())));
					screen.revalidate();
					screen.repaint();
				} catch (IOException | FontFormatException e1) {
				}
			}
		}
	}
}
