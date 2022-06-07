package controller;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import GUI.*;
import engine.*;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.world.AntiHero;
import model.world.Champion;
import model.world.Hero;
import model.world.Villain;

public class GameController {
// String colours for console logs
public static final String ANSI_RESET = "\u001B[0m";
public static final String ANSI_BLACK = "\u001B[30m";
public static final String ANSI_RED = "\u001B[31m";
public static final String ANSI_GREEN = "\u001B[32m";
public static final String ANSI_YELLOW = "\u001B[33m";

	GameController control = this;
    StartScreen screen;
    JLayeredPane panel,panel2;
    cleanSelectChampions selectTest;
    cleanSelectChampion select;
    LeaderSelection lead;
    LeaderTestingSelection leadTest;
    BoardView board;
    BoardTest testBoard;
    
    TimerTask tt;
    Timer t;
	private GameController controller;
    private Game currentGame, tempGame;
    private Player player1, player2;
    private StartScreen startScreen;
	private ArrayList<Champion> team1, team2;
	private	JFrame frame = new JFrame();

    public GameController() throws Exception {
    	controller = this;
		//startScreen = new StartScreen(this);
		//initializeFrame();
     	player1 = new Player("Joey");
 		player2 = new Player("Fadl");
		tempGame = new Game(player1,player2);
    	
//    	player1 = new Player("Joey");
//		player2 = new Player("Fadl");
//		currentGame = new Game(player1, player2);
//		Champion champ = new Hero("Loki",88,88,88,88,88,88);
//		Champion champ2 = new AntiHero("Thor",14,14,14,14,14,14);
//		Champion champ3 = new Villain("Hela",2,2,2,2,2,2);
//		Champion champ4 = new Hero("Spiderman",88,88,88,88,88,88);
//		Champion champ5 = new AntiHero("Venom",14,14,14,14,14,14);
//		Champion champ6 = new Villain("Deadpool",2,2,2,2,2,2);
//		currentGame.getFirstPlayer().getTeam().add(champ);
//		currentGame.getFirstPlayer().getTeam().add(champ2);
//		currentGame.getFirstPlayer().getTeam().add(champ3);
//		currentGame.getSecondPlayer().getTeam().add(champ4);
//		currentGame.getSecondPlayer().getTeam().add(champ5);
//		currentGame.getSecondPlayer().getTeam().add(champ6);
		
//    	selectTest = new cleanSelectChampions(this, currentGame.getAvailableChampions(), currentGame);
//    	leadTest = new LeaderTestingSelection(this,currentGame);
//    	testBoard = new BoardTest(this);
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

    public void setGame(Game game) {
    	this.currentGame = game;
    }
    
    public static void main(String[] args) throws Exception {
        new GameController();
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
				panel = screen.getPanel();
				screen.remove(screen.getPanel());
				screen.revalidate();
				screen.repaint();
				try {
					select = new cleanSelectChampion(control);
					screen.getContentPane().add(select);
					screen.revalidate();
					screen.repaint();
				} catch (IOException | FontFormatException e1) {
				}
			}
		}
	}
	
	public class BackListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			screen.remove(select);;
			screen.revalidate();
			screen.repaint();
			screen.getContentPane().add(panel);
			screen.revalidate();
			screen.repaint();
			screen.setField(currentGame.getFirstPlayer().getName());
			screen.setField2(currentGame.getSecondPlayer().getName());
			screen.revalidate();
			screen.repaint();
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
	public class NextListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			screen.remove(select);;
			screen.revalidate();
			screen.repaint();
			try {
				lead = new LeaderSelection(control, currentGame);
			} catch (FontFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			screen.getContentPane().add(lead);
			screen.revalidate();
			screen.repaint();
		}
		
	}
	
	public class SelectListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			for(int i = 0; i < currentGame.getAvailableChampions().size(); i++){
				if (currentGame.getAvailableChampions().get(i).getName().equals(select.getCurrentName())){
					try {
						if (select.getCounter()%2==1) {currentGame.getFirstPlayer().getTeam().add(currentGame.getAvailableChampions().get(i));break;}
						else {currentGame.getSecondPlayer().getTeam().add(currentGame.getAvailableChampions().get(i));break;}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			
				}
			
			}
		
		}
	}
	public class ForwardListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//panel = screen.getPanel();
			for(int i = 0; i < currentGame.getFirstPlayer().getTeam().size(); i++){
				if (currentGame.getFirstPlayer().getTeam().get(i).getName().equals(lead.getLeader().getText())){
					currentGame.getFirstPlayer().setLeader(currentGame.getFirstPlayer().getTeam().get(i));
				}
			}
			for(int i = 0; i < currentGame.getSecondPlayer().getTeam().size(); i++){
				if (currentGame.getSecondPlayer().getTeam().get(i).getName().equals(lead.getLeader2().getText())){
					currentGame.getSecondPlayer().setLeader(currentGame.getSecondPlayer().getTeam().get(i));
				}
			}
			screen.remove(lead);
			screen.revalidate();
			screen.repaint();
			board = new BoardView(control);
			screen.getContentPane().add(board);
			screen.revalidate();
			screen.repaint();
		}
		
	}
	public class BehindListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//panel = screen.getPanel();
			screen.remove(lead);
			screen.revalidate();
			screen.repaint();
			try {
				select = new cleanSelectChampion(control);
				screen.getContentPane().add(select);
				screen.revalidate();
				screen.repaint();
			} catch (IOException | FontFormatException e1) {
			}
		}
		
		
	}
//	public class TimeListener implements MouseMotionListener,MouseListener{
//
//		@Override
//		public void mouseDragged(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void mouseMoved(MouseEvent e) {
//			
//			t = new Timer();
//			 tt = new TimerTask() {
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					
//			int length=0;
//			int length2=0;
//			int length3=0;
//			FontRenderContext frc = new FontRenderContext(new AffineTransform(),true,true);
//			for(int i = 0; i < currentGame.getAvailableChampions().size(); i++){
//				if (!(currentGame.getAvailableChampions().get(i).getName().equals(select.getCurrentName()))) continue;
//				if (currentGame.getAvailableChampions().get(i).getName().equals(select.getCurrentName())){
//					//length = currentGame.getAvailableChampions().get(i).getAbilities().get(0).getName().length()*7+10;
//					Font fadl = select.getTest().getFont().deriveFont(14f);
//							
//					//LineMetrics metrics = fadl.getLineMetrics(currentGame.getAvailableChampions().get(i).getAbilities().get(0).getName(), frc);
//					
//					length = (int) fadl.getStringBounds(currentGame.getAvailableChampions().get(i).getAbilities().get(0).getName(), frc).getWidth() +14;
//					length2 = (int) fadl.getStringBounds(currentGame.getAvailableChampions().get(i).getAbilities().get(1).getName(), frc).getWidth() +14;
//					length3 = (int) fadl.getStringBounds(currentGame.getAvailableChampions().get(i).getAbilities().get(2).getName(), frc).getWidth() +14;
//					
//				}
//			
//			// TODO Auto-generated method stub if (e.getX()<=70 && e.getX()>=46 && e.getY()>=232 && e.getY()<=252){
//			if (e.getX()<=44+length && e.getX()>=44 && e.getY()>=232 && e.getY()<252 && select.isMouse()){
//				select.getTesting2().setVisible(false);
//				select.getTesting3().setVisible(false);
//				select.getTest2().setVisible(false);
//				if (select.getCounter()%2==0) { select.getTesting().setBounds(90, 266, length, 20);select.getTest2().setBounds(1050,28,273,280);}
//				else {select.getTesting().setBounds(1100, 266, length, 20);select.getTest2().setBounds(40,28,273,280);}
//				String ability = "";
//				if(currentGame.getAvailableChampions().get(i).getAbilities().get(0) instanceof DamagingAbility)
//					ability = "Damaging Amount: "+((((DamagingAbility) currentGame.getAvailableChampions().get(i).getAbilities().get(0)).getDamageAmount())) + "";
//				else if (currentGame.getAvailableChampions().get(i).getAbilities().get(0) instanceof HealingAbility) 
//					ability = "Healing Amount: "+((((HealingAbility) currentGame.getAvailableChampions().get(i).getAbilities().get(0)).getHealAmount())) + "";
//				else { if (currentGame.getAvailableChampions().get(i).getAbilities().get(0) instanceof CrowdControlAbility) 
//					ability = "Effect: "+((((CrowdControlAbility) currentGame.getAvailableChampions().get(i).getAbilities().get(0)).getEffect().getName())) + "";}
//				select.getTest2().setText("<html><h1>"
//						+"<div style='text-indent:15px; font-size:24; margin-top :-40;'>"
//						+currentGame.getAvailableChampions().get(i).getAbilities().get(0).getName()
//						+"</div></h1><h2><div style='font-size:18;margin-left:35;margin-top :-32;'>"+"Type: "
//						+currentGame.getAvailableChampions().get(i).getAbilities().get(0).getClass().getTypeName()+"<br>Area Of Effect: "
//						+currentGame.getAvailableChampions().get(i).getAbilities().get(0).getCastArea().getClass().getSimpleName()+"<br>Cast Range: "
//						+currentGame.getAvailableChampions().get(i).getAbilities().get(0).getCastRange()+"<br>Mana Cost: "
//						+currentGame.getAvailableChampions().get(i).getAbilities().get(0).getManaCost()+"<br>Attack Points Required: "
//						+currentGame.getAvailableChampions().get(i).getAbilities().get(0).getRequiredActionPoints()+"<br>Base Cool Down: "
//						+currentGame.getAvailableChampions().get(i).getAbilities().get(0).getBaseCooldown()+"<br>" + ability		
//						+"</div></h2><html>");
//				select.getTesting().setVisible(true);
//				select.getTest2().setVisible(true);
//				//test.setText("HE");
//			}
//			else if (e.getX()<=44+length2 && e.getX()>=44 && e.getY()>=252 && e.getY()<272 && select.isMouse()){
//				select.getTesting().setVisible(false);
//				select.getTesting3().setVisible(false);
//				select.getTest2().setVisible(false);
//				if (select.getCounter()%2==0) {select.getTesting2().setBounds(90, 284, length2, 20);select.getTest2().setBounds(1050,28,273,280);}
//				else {select.getTesting2().setBounds(1100, 284, length2, 20);select.getTest2().setBounds(40,28,273,280);}
//				String ability = "";
//				if(currentGame.getAvailableChampions().get(i).getAbilities().get(1) instanceof DamagingAbility)
//					ability = "Damaging Amount: "+((((DamagingAbility) currentGame.getAvailableChampions().get(i).getAbilities().get(1)).getDamageAmount())) + "";
//				else if (currentGame.getAvailableChampions().get(i).getAbilities().get(1) instanceof HealingAbility) 
//					ability = "Healing Amount: "+((((HealingAbility) currentGame.getAvailableChampions().get(i).getAbilities().get(1)).getHealAmount())) + "";
//				else { if (currentGame.getAvailableChampions().get(i).getAbilities().get(1) instanceof CrowdControlAbility) 
//					ability = "Effect: "+((((CrowdControlAbility) currentGame.getAvailableChampions().get(i).getAbilities().get(1)).getEffect().getName())) + "";}
//				select.getTest2().setText("<html><h1>"
//						+"<div style='text-indent:15px; font-size:24; margin-top :-40;'>"
//						+currentGame.getAvailableChampions().get(i).getAbilities().get(1).getName()
//						+"</div></h1><h2><div style='font-size:18;margin-left:35;margin-top :-32;'>"+"Type: "
//						+currentGame.getAvailableChampions().get(i).getAbilities().get(1).getClass().getSimpleName()+"<br>Area Of Effect: "
//						+currentGame.getAvailableChampions().get(i).getAbilities().get(1).getCastArea().getClass().getSimpleName()+"<br>Cast Range: "
//						+currentGame.getAvailableChampions().get(i).getAbilities().get(1).getCastRange()+"<br>Mana Cost: "
//						+currentGame.getAvailableChampions().get(i).getAbilities().get(1).getManaCost()+"<br>Attack Points Required: "
//						+currentGame.getAvailableChampions().get(i).getAbilities().get(1).getRequiredActionPoints()+"<br>Base Cool Down: "
//						+currentGame.getAvailableChampions().get(i).getAbilities().get(1).getBaseCooldown()+"<br>" + ability		
//						+"</div></h2><html>");
//				select.getTesting2().setVisible(true);
//				select.getTest2().setVisible(true);
//				//test.setText("HE");
//			}
//			else if (e.getX()<=44+length3 && e.getX()>=44 && e.getY()>=272 && e.getY()<=292 && select.isMouse()){
//				select.getTesting().setVisible(false);
//				select.getTesting2().setVisible(false);
//				select.getTest2().setVisible(false);
//				if (select.getCounter()%2==0) { select.getTesting3().setBounds(90, 304, length3, 20);select.getTest2().setBounds(1050,28,273,280);}
//				else {select.getTesting3().setBounds(1100, 304, length3, 20);select.getTest2().setBounds(40,28,273,280);}
//				String ability = "";
//				if(currentGame.getAvailableChampions().get(i).getAbilities().get(2) instanceof DamagingAbility)
//					ability = "Damaging Amount: "+((((DamagingAbility) currentGame.getAvailableChampions().get(i).getAbilities().get(2)).getDamageAmount())) + "";
//				else if (currentGame.getAvailableChampions().get(i).getAbilities().get(2) instanceof HealingAbility) 
//					ability = "Healing Amount: "+((((HealingAbility) currentGame.getAvailableChampions().get(i).getAbilities().get(2)).getHealAmount())) + "";
//				else { if (currentGame.getAvailableChampions().get(i).getAbilities().get(2) instanceof CrowdControlAbility) 
//					ability = "Effect: "+((((CrowdControlAbility) currentGame.getAvailableChampions().get(i).getAbilities().get(2)).getEffect().getName())) + "";}
//				select.getTest2().setText("<html><h1>"
//						+"<div style='text-indent:15px; font-size:24; margin-top :-40;'>"
//						+currentGame.getAvailableChampions().get(i).getAbilities().get(2).getName()
//						+"</div></h1><h2><div style='font-size:18;margin-left:35;margin-top :-32;'>"+"Type: "
//						+currentGame.getAvailableChampions().get(i).getAbilities().get(2).getClass().getSimpleName()+"<br>Area Of Effect: "
//						+currentGame.getAvailableChampions().get(i).getAbilities().get(2).getCastArea().getClass().getSimpleName()+"<br>Cast Range: "
//						+currentGame.getAvailableChampions().get(i).getAbilities().get(2).getCastRange()+"<br>Mana Cost: "
//						+currentGame.getAvailableChampions().get(i).getAbilities().get(2).getManaCost()+"<br>Attack Points Required: "
//						+currentGame.getAvailableChampions().get(i).getAbilities().get(2).getRequiredActionPoints()+"<br>Base Cool Down: "
//						+currentGame.getAvailableChampions().get(i).getAbilities().get(2).getBaseCooldown()+"<br>" + ability		
//						+"</div></h2><html>");
//				select.getTesting3().setVisible(true);
//				select.getTest2().setVisible(true);
//				//test.setText("HE");
//			}else {
//				//test.setText("");
//				
//				select.getTesting2().setVisible(false);
//				select.getTesting().setVisible(false);
//				select.getTesting3().setVisible(false);
//				select.getTest2().setVisible(false);
//			}
//		break;}
//			
//			};};
//			
//		t.schedule(tt, 1000);}
////
////	
//
//		@Override
//		public void mouseClicked(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void mousePressed(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void mouseReleased(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void mouseEntered(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void mouseExited(MouseEvent e) {
//			// TODO Auto-generated method stub
//		tt.cancel();
//		t.cancel();
//		}
//}

	public Game getCurrentGame() {
		return currentGame;
	}

	public void setCurrentGame(Game currentGame) {
		this.currentGame = currentGame;
	}
}
