package controller;

import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import GUI.*;
import engine.*;

public class GameController {
    Game currentGame;
    Player player1, player2;
    StartScreen screen;

    public GameController() throws Exception {
    	screen = new StartScreen(this);
//    	player1 = new Player("Joey");
//		player2 = new Player("Fadl");
//		
//			currentGame = new Game(player1, player2);
//		
//    	new cleanSelectChampions(currentGame.getAvailableChampions());
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
