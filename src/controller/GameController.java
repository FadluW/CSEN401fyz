package controller;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import GUI.*;
import GUI.buttonPresets.ArrowButtons;
import engine.*;
import exceptions.*;
import model.abilities.Ability;
import model.world.*;

public class GameController {
	// String colours for console logs
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";

	private GameController controller;
    private Game currentGame, tempGame;
    private Player player1, player2;
    private StartScreen startScreen;
	private ArrayList<Champion> team1, team2;
	private	JFrame frame = new JFrame();

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
		//startScreen = new StartScreen(this);
		//initializeFrame();
     	player1 = new Player("Joey");
 		player2 = new Player("Fadl");
		 tempGame = new Game(player1,player2);


 		player1.getTeam().add(tempGame.getAvailableChampions().get(2));
 		player1.getTeam().add(tempGame.getAvailableChampions().get(1));
 		player1.getTeam().add(tempGame.getAvailableChampions().get(6));
 		player2.getTeam().add(tempGame.getAvailableChampions().get(9));
 		player2.getTeam().add(tempGame.getAvailableChampions().get(12));
 		player2.getTeam().add(tempGame.getAvailableChampions().get(7));
		currentGame = new Game(player1, player2);

 		team1 = player1.getTeam();
 		team2 = player2.getTeam();
 //    	new cleanSelectChampions(currentGame.getAvailableChampions());
 		new editingBoard(controller);
	}
	
	public static void main(String[] args) throws Exception {
		new GameController();
	}

    public void setGame(Game game) {
    	this.currentGame = game;
    }
    

	// Sets up initial frame to be used and link start screen to it
	public void initializeFrame() {
		ImageIcon icon = new ImageIcon("assets/background/Game Start Small.jpg");
		frame.setIconImage(icon.getImage());
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.getContentPane().add(startScreen);	
		frame.getContentPane().setBackground(Color.black);
		frame.setSize(1366,768);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	// Removes old screen and adds new one
	public void changeScreen(JLayeredPane oldScreen, JLayeredPane newScreen) {
		frame.remove(oldScreen);
		frame.revalidate();
		frame.repaint();

		frame.getContentPane().add((newScreen));
		frame.revalidate();
		frame.repaint();
	}

	public class QuitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispatchEvent(new WindowEvent(frame,WindowEvent.WINDOW_CLOSING));
		}
	}

	public class BeginListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(((startScreen.getField()).equals("") || startScreen.getField2().equals(""))){
				System.out.println("Button Works");
				startScreen.getError().setVisible(true);
				startScreen.repaint();
				startScreen.revalidate();
				new java.util.Timer().schedule(new java.util.TimerTask() {
					@Override
					public void run() {
						startScreen.getError().setVisible(false);
						startScreen.repaint();
						startScreen.revalidate();
					}
				}, 1500);
			}
			else {
				System.out.println(ANSI_GREEN + "Names selected: " + ANSI_RESET + startScreen.getField() + ", " + startScreen.getField2());
				player1 = new Player(startScreen.getField());
				player2 = new Player(startScreen.getField2());
				try {
					currentGame = new Game(player1, player2);
				} catch (Exception e1) {}

				try {
					changeScreen(startScreen, new cleanSelectChampion(controller));
				} catch (IOException | FontFormatException e1) {}
			}
		}
	}

	// Listener to go back to a previous screen
	public class BackListener implements ActionListener {
		private JLayeredPane previousScreen;
		private JLayeredPane currentScreen;

		// Constructor for new panel
		public BackListener(String screenName, JLayeredPane currentScreen) {
			this.currentScreen = currentScreen;
			switch (screenName.toLowerCase()) {
				case "start": previousScreen = startScreen;
			}
		}

		// Constructor for exact panel
		public BackListener (JLayeredPane screenPane, JLayeredPane currentScreen) {
			previousScreen = screenPane;
			this.currentScreen = currentScreen;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			changeScreen(this.currentScreen, this.previousScreen);
		}
	}

	// Listener to handle movement
	/* ButtonID = move|direction */
	public class MoveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String[] buttonID = ((JButton) e.getSource()).getName().split("\\|");
			// Ensure it was a move button
			if (buttonID[0] != "move") {
				System.out.println(ANSI_RED + "[ERROR] MoveListener - given incorrect button of type " + buttonID[0] + ANSI_RESET);
				return;
			}

			Direction direction = null;
			switch (buttonID[1].toLowerCase()) {
				case "up": direction = Direction.UP; break;
				case "down": direction = Direction.DOWN; break;
				case "left": direction = Direction.LEFT; break;
				case "right": direction = Direction.RIGHT; break;
			}

			try {
				currentGame.move(direction);
			} catch (UnallowedMovementException | NotEnoughResourcesException e1) {
				e1.printStackTrace();
			}
		}
	}

	// Listener to handle casting abilities
	/* ButtonID = cast| (abilityIndex inside of champion ability arrayList) | (direction / x,y)*/
	public class CastListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] buttonID = ((JButton) e.getSource()).getName().split("\\|");
			// Ensure it was a move button
			if (buttonID[0] != "cast") {
				System.out.println(ANSI_RED + "[ERROR] CastListener - given incorrect button of type " + buttonID[0] + ANSI_RESET);
				return;
			}

			Champion currentChamp = currentGame.getCurrentChampion();
			Ability chosenAbility = currentChamp.getAbilities().get(Integer.parseInt(buttonID[1]));
			
			switch (chosenAbility.getCastArea()) {
				case DIRECTIONAL:
					try {
						currentGame.castAbility(chosenAbility, Direction.directionOf(buttonID[2]));
					} catch (AbilityUseException | NotEnoughResourcesException | CloneNotSupportedException e1) {
						e1.printStackTrace();
					}
					break;
				case SELFTARGET:
				case SURROUND:
				case TEAMTARGET:

					try {
						currentGame.castAbility(chosenAbility);
					} catch (AbilityUseException | NotEnoughResourcesException | CloneNotSupportedException e1) {
						e1.printStackTrace();
					}

					break;
				case SINGLETARGET:
					int x = Integer.parseInt(buttonID[1].split(",")[0]);
					int y = Integer.parseInt(buttonID[1].split(",")[1]);

					try {
						currentGame.castAbility(chosenAbility, x, y);
					} catch (AbilityUseException | InvalidTargetException | NotEnoughResourcesException
							| CloneNotSupportedException e1) {
						e1.printStackTrace();
					}

					break;
				default:
					// Throw error
					break;

			}
		}
	}
}
