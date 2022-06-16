package controller;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
//import javax.swing.Timer;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.*;
import javax.swing.*;

import GUI.*;
import engine.*;
import exceptions.*;
//import jdk.internal.misc.FileSystemOption;
import model.abilities.*;
import model.world.*;

public class GameController {
	// String colours for console logs
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	
	boolean quick=false;
	String text = "";
	boolean faster=false;
    JLayeredPane panel, panel2;
    int num,count,speed;
    boolean moving=false;
    cleanSelectChampions selectTest;
    cleanSelectChampion select;
    LeaderSelection lead;
    LeaderTestingSelection leadTest;
    //editingBoard board;
    BoardTest testBoard;
    BoardView board;
    
    TimerTask tt;
    Timer t;
	private Clip clip;
	private Clip themeAudio;
  //  Timer t;
    javax.swing.Timer timer = null;
	private GameController controller;
    private Game currentGame;
    private Player player1, player2;
    private StartScreen startScreen;
	private ArrayList<Champion> team1, team2;
	private Boolean isBoardCasting = false;
	private int abilityIndex = -1;
	Object[][] boardGame;
	private	JFrame frame = new JFrame();

	public Game getCurrentGame() {
		return currentGame;
	}

	public void setCurrentGame(Game currentGame) {
		this.currentGame = currentGame;
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
		startScreen = new StartScreen(this);
		initializeFrame();
    }

    public BoardView getBoard() {
		return board;
	}

	public void setBoard(BoardView board) {
		this.board = board;
	}

	public void setGame(Game game) {
    	this.currentGame = game;
    }
    
    public static void main(String[] args) throws Exception {
        new GameController();
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
    
	// Sets up initial frame to be used and link start screen to it
	public void initializeFrame() {
		ImageIcon icon = new ImageIcon("assets/background/Game Start Small.jpg");
		frame.setIconImage(icon.getImage());
		frame.setTitle("Marvel: Ultimate War");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(startScreen);	
		frame.getContentPane().setBackground(Color.black);
		frame.setSize(1366,768);
		frame.setVisible(true);
		frame.setResizable(false);
		playAudioTheme();
	}

	private void playAudioTheme() {
		new Thread(new Runnable() {
			public void run() {
			  try {
				  AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("assets/audio/theme.wav").getAbsoluteFile());
				  themeAudio = AudioSystem.getClip();
				  themeAudio.open(audioStream);
				  themeAudio.start();
			  } catch (Exception e) {
				e.printStackTrace();
			  }
			}
		  }).start();
	}
	
	private void playAudioGame() {
		new Thread(new Runnable() {
			public void run() {
			  try {
				  AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("assets/audio/game_theme.wav").getAbsoluteFile());
				  themeAudio = AudioSystem.getClip();
				  themeAudio.open(audioStream);
				  themeAudio.loop(Clip.LOOP_CONTINUOUSLY);
				  themeAudio.start();
			  } catch (Exception e) {
				e.printStackTrace();
			  }
			}
		  }).start();
	}
	
	public void stopAudioTheme() {
		themeAudio.stop();
	}

	public class LeaderAbilityListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
			try {
				playAudio("button_click.wav");
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
            try {
                currentGame.useLeaderAbility();
                 board.getUseLeaderAbility().setEnabled(false);
                 board.getUseLeaderAbility().setBackground(new Color(0xb6b884));
                 board.getUseLeaderAbility().setForeground(Color.black);
                 
            } catch (LeaderAbilityAlreadyUsedException | LeaderNotCurrentException e1) {
                board.getErrorPanel().setVisible(true);
				try {
					playAudio("button_error.wav");
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
					e2.printStackTrace();
				}
				board.repaint();
				board.revalidate();

				if(e1 instanceof LeaderAbilityAlreadyUsedException)board.getErrorLabel().setText("Leader Ability Already Used");
				else board.getErrorLabel().setText("Current Champion is not a leader");

				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						board.getErrorPanel().setVisible(false);
						board.repaint();
						board.revalidate();
					}
				}, 1500);
                System.out.println((e1.getMessage() == null) ? e1.getClass().getName() : e1.getMessage());
            }
            checkGame();
        }

    }

	public class BeginListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(((startScreen.getField()).equals("") || startScreen.getField2().equals(""))){
				System.out.println("Button Works");
				startScreen.getError().setVisible(true);
				try {
					playAudio("button_error.wav");
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
					e2.printStackTrace();
				}
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
					currentGame = new Game(player1,player2);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				try {
					try {
						playAudio("button_click.wav");
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
						e1.printStackTrace();
					}
					select = new cleanSelectChampion(controller);
					changeScreen(startScreen, select);
				} catch (IOException | FontFormatException e1) {
					e1.printStackTrace();
				}
			}
		}
	}


	public class QuitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispatchEvent(new WindowEvent(frame,WindowEvent.WINDOW_CLOSING));
		}
	}
	
//	public class BackingListener implements ActionListener{
//
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			// TODO Auto-generated method stub
//			screen.remove(select);;
//			screen.revalidate();
//			screen.repaint();
//			screen.getContentPane().add(panel);
//			screen.revalidate();
//			screen.repaint();
//			screen.setField(currentGame.getFirstPlayer().getName());
//			screen.setField2(currentGame.getSecondPlayer().getName());
//			screen.revalidate();
//			screen.repaint();
//		}
//
//	}

	// Listener to go back to a previous screen
	public class BackListener implements ActionListener {
		private JLayeredPane previousScreen;
		private JLayeredPane currentScreen;

		// Constructor for new panel
		public BackListener(String screenName, JLayeredPane currentScreen) {
			this.currentScreen = currentScreen;
			switch (screenName.toLowerCase()) {
				case "start": previousScreen = startScreen; break;
				case "champselect": previousScreen = select; break;
			}
			// System.out.println("initialised previous with " + previousScreen);
		}

		// Constructor for exact panel
		public BackListener (JLayeredPane screenPane, JLayeredPane currentScreen) {
			previousScreen = screenPane;
			this.currentScreen = currentScreen;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				playAudio("button_click.wav");
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			System.out.println("Back button pressed going to " + previousScreen.getClass().getSimpleName());
			if (previousScreen instanceof StartScreen) {
				resetTeams();
			} else if (previousScreen instanceof cleanSelectChampion) {
				resetTeams();
				try {
					select = new cleanSelectChampion(controller);
					// System.out.println("Created new champselect");
				} catch (IOException | FontFormatException e1) {
					e1.printStackTrace();
				}
				this.previousScreen = select;
			}
			changeScreen(this.currentScreen, this.previousScreen);
		}
	}

	// Listener to handle movement
	/* ButtonID = move|null|direction */
	public class MoveListener implements ActionListener {
		

		@Override
		public void actionPerformed(ActionEvent e) {
			if(moving)return;
			try {
				playAudio("button_click.wav");
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			String[] buttonID = ((JButton) e.getSource()).getName().split("\\|");
			// Ensure it was a move button
			if (!buttonID[0].equals("move")) {
				System.out.println(ANSI_RED + "[ERROR] MoveListener - given incorrect button of type " + buttonID[0] + ANSI_RESET);
				return;
			}
			
			Direction direction = Direction.directionOf(buttonID[2].toLowerCase());
			
//			controller.getBoard().setxPos(controller.getCurrentGame().getCurrentChampion().getLocation().y*141+337);
//			controller.getBoard().setyPos(controller.getCurrentGame().getCurrentChampion().getLocation().x*141+16);
//			controller.getBoard().setChampImage(new ImageIcon("assets/characters/128/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
			switch(direction.toString()) {
			case "Up":
				controller.getBoard().setxPos(controller.getCurrentGame().getCurrentChampion().getLocation().y*141+337);
				controller.getBoard().setyPos((4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*141+17);
				//controller.getBoard().setChampImage(new ImageIcon("assets/characters/128/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
				controller.getBoard().setChampImage(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
				controller.getBoard().setFinalY(controller.getBoard().getyPos()-141);
				try {
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
					
					currentGame.move(direction);
				moving=true;
				controller.getBoard().getLeader().setText(controller.getBoard().putText(controller.getCurrentGame().getCurrentChampion()));
				 timer = new javax.swing.Timer(2,new ActionListener() {
				
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
					
						
						controller.getBoard().setyPos(controller.getBoard().getyPos()-1);
						controller.getBoard().getMove().repaint();
						
						//testBoard.setCount(testBoard.getCount()+1);
						if (controller.getBoard().getyPos() <= controller.getBoard().getFinalY()) { timer.stop(); moving=false;}
						//testBoard.getMove().repaint();
						
						
						}
				});
				 ((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x+1)*5)).setIcon(null);
				((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
					//((JButton) panel2.getComponent(game.getCurrentChampion().getLocation().y+game.getCurrentChampion().getLocation().x*5)).setIcon(null);
					timer.start();
			} catch (UnallowedMovementException | NotEnoughResourcesException e1) {
				board.getErrorPanel().setVisible(true);
				try {
					playAudio("button_error.wav");
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
					e2.printStackTrace();
				}
				board.repaint();
				board.revalidate();

				if(e1 instanceof UnallowedMovementException)board.getErrorLabel().setText("You Can't Move Here");
				else board.getErrorLabel().setText("Your Champion Doesn't Have the Enough Resources");

				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						board.getErrorPanel().setVisible(false);
						board.repaint();
						board.revalidate();
					}
				}, 1500);
				System.out.println((e1.getMessage() == null) ? e1.getClass().getName() : e1.getMessage());
			}
					break;
			
				case "Down":
					controller.getBoard().setxPos(controller.getCurrentGame().getCurrentChampion().getLocation().y*141+337);
					controller.getBoard().setyPos((4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*141+17);
					controller.getBoard().setChampImage(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
					controller.getBoard().setFinalY(controller.getBoard().getyPos()+141);
					try {
//						try {
//							Thread.sleep(100);
//						} catch (InterruptedException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
						currentGame.move(direction);
					moving=true;
					controller.getBoard().getLeader().setText(controller.getBoard().putText(controller.getCurrentGame().getCurrentChampion()));
					timer = new javax.swing.Timer(2,new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							
							
							controller.getBoard().setyPos(controller.getBoard().getyPos()+1);
							controller.getBoard().getMove().repaint();
							
							//testBoard.setCount(testBoard.getCount()+1);
							if (controller.getBoard().getyPos() >= controller.getBoard().getFinalY()) {timer.stop();moving=false;}
							//testBoard.getMove().repaint();
							
							
						}
					});
					((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x-1)*5)).setIcon(null);
					((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
					//((JButton) panel2.getComponent(game.getCurrentChampion().getLocation().y+game.getCurrentChampion().getLocation().x*5)).setIcon(null);
					timer.start();
			} catch (UnallowedMovementException | NotEnoughResourcesException e1) {
				board.getErrorPanel().setVisible(true);
				try {
					playAudio("button_error.wav");
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
					e2.printStackTrace();
				}
				board.repaint();
				board.revalidate();

				if(e1 instanceof UnallowedMovementException)board.getErrorLabel().setText("You Can't Move Here");
				else board.getErrorLabel().setText("Your Champion Doesn't Have the Enough Resources");

				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						board.getErrorPanel().setVisible(false);
						board.repaint();
						board.revalidate();
					}
				}, 1500);
				System.out.println((e1.getMessage() == null) ? e1.getClass().getName() : e1.getMessage());
			}
					break;
				
			case "Left":
				controller.getBoard().setxPos(controller.getCurrentGame().getCurrentChampion().getLocation().y*141+337);
				controller.getBoard().setyPos((4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*141+18);
				controller.getBoard().setChampImage(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
				controller.getBoard().setFinalX(controller.getBoard().getxPos()-141);
				try {
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			currentGame.move(direction);
				moving=true;
				controller.getBoard().getLeader().setText(controller.getBoard().putText(controller.getCurrentGame().getCurrentChampion()));
				timer = new javax.swing.Timer(2,new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
						
						controller.getBoard().setxPos(controller.getBoard().getxPos()-1);
						controller.getBoard().getMove().repaint();
						
						//testBoard.setCount(testBoard.getCount()+1);
						if (controller.getBoard().getxPos() <= controller.getBoard().getFinalX()) {timer.stop();moving=false;}
						//testBoard.getMove().repaint();
						
						
					}
				});
				((JButton) controller.getBoard().getPanel2().getComponent((controller.getCurrentGame().getCurrentChampion().getLocation().y+1)+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
				((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
				//((JButton) panel2.getComponent(game.getCurrentChampion().getLocation().y+game.getCurrentChampion().getLocation().x*5)).setIcon(null);
				timer.start();
				} catch (UnallowedMovementException | NotEnoughResourcesException e1) {
					board.getErrorPanel().setVisible(true);
				try {
					playAudio("button_error.wav");
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
					e2.printStackTrace();
				}
					board.repaint();
					board.revalidate();

					if(e1 instanceof UnallowedMovementException)board.getErrorLabel().setText("You Can't Move Here");
					else board.getErrorLabel().setText("Your Champion Doesn't Have the Enough Resources");

					new Timer().schedule(new TimerTask() {
						@Override
						public void run() {
							board.getErrorPanel().setVisible(false);
							board.repaint();
							board.revalidate();
						}
					}, 1500);
					System.out.println((e1.getMessage() == null) ? e1.getClass().getName() : e1.getMessage());
				}
				break;
			
			case "Right":
				controller.getBoard().setxPos(controller.getCurrentGame().getCurrentChampion().getLocation().y*141+337);
				controller.getBoard().setyPos((4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*141+17);
				controller.getBoard().setChampImage(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
				controller.getBoard().setFinalX(controller.getBoard().getxPos()+141);
				try {
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
					currentGame.move(direction);
				moving=true;
				controller.getBoard().getLeader().setText(controller.getBoard().putText(controller.getCurrentGame().getCurrentChampion()));
				timer = new javax.swing.Timer(2,new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
						
						controller.getBoard().setxPos(controller.getBoard().getxPos()+1);
						controller.getBoard().getMove().repaint();
						
						//testBoard.setCount(testBoard.getCount()+1);
						if (controller.getBoard().getxPos() >= controller.getBoard().getFinalX()) {timer.stop();moving=false;}
						//testBoard.getMove().repaint();
						
						
					}
				});
				((JButton) controller.getBoard().getPanel2().getComponent((controller.getCurrentGame().getCurrentChampion().getLocation().y-1)+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
				((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
				//((JButton) panel2.getComponent(game.getCurrentChampion().getLocation().y+game.getCurrentChampion().getLocation().x*5)).setIcon(null);
				timer.start();
				} catch (UnallowedMovementException | NotEnoughResourcesException e1) {
					board.getErrorPanel().setVisible(true);
				try {
					playAudio("button_error.wav");
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
					e2.printStackTrace();
				}
					board.repaint();
					board.revalidate();

					if(e1 instanceof UnallowedMovementException)board.getErrorLabel().setText("You Can't Move Here");
					else board.getErrorLabel().setText("Your Champion Doesn't Have the Enough Resources");

					new Timer().schedule(new TimerTask() {
						@Override
						public void run() {
							board.getErrorPanel().setVisible(false);
							board.repaint();
							board.revalidate();
						}
					}, 1500);
					System.out.println((e1.getMessage() == null) ? e1.getClass().getName() : e1.getMessage());
				}
				break;
			}
			
//			try {
////				try {
////					Thread.sleep(100);
////				} catch (InterruptedException e1) {
////					// TODO Auto-generated catch block
////					e1.printStackTrace();
////				}
//				currentGame.move(direction);
//			} catch (UnallowedMovementException | NotEnoughResourcesException e1) {
//				board.getErrorPanel().setVisible(true);
				// try {
				// 	playAudio("button_error.wav");
				// } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
				// 	e2.printStackTrace();
				// }
//				board.repaint();
//				board.revalidate();
//
//				if(e1 instanceof UnallowedMovementException)board.getErrorLabel().setText("You Can't Move Here");
//				else board.getErrorLabel().setText("Your Champion Doesn't Have the Enough Resources");
//
//				new Timer().schedule(new TimerTask() {
//					@Override
//					public void run() {
//						board.getErrorPanel().setVisible(false);
//						board.repaint();
//						board.revalidate();
//					}
//				}, 1500);
//				System.out.println((e1.getMessage() == null) ? e1.getClass().getName() : e1.getMessage());
//			}
		}
	}
	
	// Listener to handle movement
	/* ButtonID = attack|null|direction */
	public class AttackListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(moving)return;
			try {
				playAudio("button_click.wav");
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			String[] buttonID = ((JButton) e.getSource()).getName().split("\\|");
			// Ensure it was a move button
			if (!buttonID[0].equals("attack")) {
				System.out.println(ANSI_RED + "[ERROR] AttackListener - given incorrect button of type " + buttonID[0] + ANSI_RESET);
				return;
			}

			Direction direction = Direction.directionOf(buttonID[2].toLowerCase());

			try {
				 controller.getBoard().drawBoard(controller.getCurrentGame().getBoard());
				 controller.getBoard().getLeader().setText(controller.getBoard().putText(controller.getCurrentGame().getCurrentChampion()));
				
				//controller.getBoard().new boardUpdateListener();
				currentGame.attack(direction);
				boardGame = controller.getCurrentGame().getBoard();
				for (int i = 1; i <= controller.getCurrentGame().getCurrentChampion().getAttackRange(); i++) {
					Point p = controller.getCurrentGame().calcDirection(controller.getCurrentGame().getCurrentChampion().getLocation(), direction, i);
					
					int x = (int) p.getLocation().getX();
					int y = (int) p.getLocation().getY();
					
					if (x < 0 || x >= 5) throw new InvalidTargetException("Target out of board bounds");
					if (y < 0 || y >= 5) throw new InvalidTargetException("Target out of board bounds");
					
					if (boardGame[x][y]!=null) {
						controller.getBoard().setxPos(controller.getCurrentGame().getCurrentChampion().getLocation().y*141+337+50);
						controller.getBoard().setyPos((4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*141+17+50);
						
						controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
						//controller.getBoard().setChampImage(new ImageIcon("assets/characters/128/Untitled22_20220607135427.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
						//Rotate rotate = new Rotate(90);
						//controller.getBoard().getChampImage().add(new Rotate(90));
						controller.getBoard().setFinalX(y*141+337+50);
						controller.getBoard().setFinalY((4-x)*141+17+50);
						System.out.println("X Pos: "+controller.getBoard().getxPos());
						System.out.println("Final x: "+controller.getBoard().getFinalX());
						System.out.println("Y Pos: "+controller.getBoard().getyPos());
						System.out.println("Final y: "+controller.getBoard().getFinalY());
						
						num = 0;
						count = 1;
						speed=2;
						
						if(controller.getBoard().getFinalX()<controller.getBoard().getxPos()) {
							num=1;
						}else
							if(controller.getBoard().getFinalX()>controller.getBoard().getxPos()) {
								num=2;
						}else
							if(controller.getBoard().getFinalY()<controller.getBoard().getyPos()) {
								num=3;
						}else
							if(controller.getBoard().getFinalY()>controller.getBoard().getyPos()) {
								num=4;
						}
						controller.getBoard().setXfpPos(controller.getCurrentGame().getCurrentChampion().getLocation().y*141+337);
						controller.getBoard().setYfPos((4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*141+17);
						timer = new javax.swing.Timer(2,new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								moving = true;
								switch(controller.getCurrentGame().getCurrentChampion().getName()){
								case "Deadpool":
									speed=1;
									switch(count) {
									case 1:
									case 2 :
									case 3 :
										controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
										count++;
										break;
									case 4:
									case 5:
									case 6:
										controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "2.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
										count++;
										break;
									case 7:
									case 8:
									case 9:
										controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "3.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
										count++;
										break;
									case 10:
									case 11:
										controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "4.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
										count++;
										break;
									case 12:
										controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "4.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
										count=1;
										break;
									}
								break;
								case "Ghost Rider":
									speed=1;
									System.out.println("Enter..");
									switch(count) {
										case 1:
											text="assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() ;
											count++;
											break;
										case 2:
											text="assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() +count;
											count++;
											break;
										case 3:
											text="assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() +count;
											count=1;
											break;
										}
									break;
								case "Dr Strange":
								case "Venom":
								case "Ironman":
								
									speed=1;
									System.out.println("Enter..");
									switch(count) {
										case 1:
											controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
											count++;
											break;
										case 2:
											controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() +count+ ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
											count++;
											break;
										case 3:
											controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() +count+ ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
											count=1;
											break;
										}
									break;
								case "Captain America":
									speed=1;
									controller.getBoard().setAmerica(true);
									//controller.getBoard().setXfpPos(controller.getCurrentGame().getCurrentChampion().getLocation().y);
									//controller.getBoard().setYfPos((4-controller.getCurrentGame().getCurrentChampion().getLocation().x));
									((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
									((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + "2.png"));
									//((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon();
									break;
								case "Quicksilver":
									controller.getBoard().setSilver(true);
									speed=3;
									controller.getBoard().setChampImage(null);
									((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
									controller.getBoard().setCaptain((new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png")).getImage());
									
									break;
								}
								if(num==1) {
									if(controller.getBoard().isSilver()) {
										
												
												System.out.println("Quick");
												if(!faster) {
												controller.getBoard().setXfpPos(controller.getBoard().getXfpPos()-12);
												controller.getBoard().getMove().repaint();
												if (controller.getBoard().getXfpPos() <= 333 && !quick) {
													quick = true;
													controller.getBoard().setXfpPos(893);
													System.out.println("Stop");
													
												}
												if (controller.getBoard().getXfpPos() <= controller.getCurrentGame().getCurrentChampion().getLocation().y*141+337 && quick) {
													
													//controller.getBoard().setChampImage(null); 
													faster = true;
													controller.getBoard().setSilver(false);
													((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
												}}else {
													controller.getBoard().setxPos(controller.getBoard().getxPos()-speed);
													controller.getBoard().getMove().repaint();
													controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "_left.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
													if (controller.getBoard().getxPos() <= controller.getBoard().getFinalX()) {
														timer.stop();
														controller.getBoard().setChampImage(null); 
														moving = false;
														faster=false;
														quick=false;
														((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
														controller.getBoard().setCaptain(null);
														controller.getBoard().setChampImage(null);
														if(controller.getBoard().isAmerica()) {
															((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
														((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
														controller.getBoard().setAmerica(false);
														}
														
														controller.getBoard().getMove().repaint();
														}
												}
												
									}else {
										switch (controller.getCurrentGame().getCurrentChampion().getName()) {
											case "Hela" :
											case "Loki" :
											case "Spiderman" :
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "_Left.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
												break;
											case "Iceman" :
											case "Yellow Jacket" :
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "2.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
												break;
										}
										if (controller.getCurrentGame().getCurrentChampion().getName().equals("Ghost Rider"))
											controller.getBoard().setChampImage(new ImageIcon(text+"_Left.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
									if (controller.getCurrentGame().getCurrentChampion().getName().equals("Thor"))
										controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "4.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
									if (controller.getCurrentGame().getCurrentChampion().getName().equals("Electro"))
										controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "2.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
									controller.getBoard().setxPos(controller.getBoard().getxPos()-speed);
									controller.getBoard().getMove().repaint();
									if (controller.getBoard().getxPos() <= controller.getBoard().getFinalX()) {
										timer.stop();
										controller.getBoard().setChampImage(null); 
										moving = false;
										if(controller.getBoard().isAmerica()) {
											((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
										((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
										controller.getBoard().setAmerica(false);
										}
										
										controller.getBoard().getMove().repaint();
										}
									}
								}else
									if(num==2) {
										if(controller.getBoard().isSilver()) {
											
											
											System.out.println("Quick");
											if(!faster) {
											controller.getBoard().setXfpPos(controller.getBoard().getXfpPos()+12);
											controller.getBoard().getMove().repaint();
											if (controller.getBoard().getXfpPos() >= 893 && !quick) {
												quick = true;
												controller.getBoard().setXfpPos(333);
												System.out.println("Stop");
												
											}
											if (controller.getBoard().getXfpPos() >= controller.getCurrentGame().getCurrentChampion().getLocation().y*141+337 && quick) {
												
												//controller.getBoard().setChampImage(null); 
												faster = true;
												controller.getBoard().setSilver(false);
												((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
											}}else {
												controller.getBoard().setxPos(controller.getBoard().getxPos()+speed);
												controller.getBoard().getMove().repaint();
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "_right.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
												if (controller.getBoard().getxPos() >= controller.getBoard().getFinalX()) {
													timer.stop();
													controller.getBoard().setChampImage(null); 
													moving = false;
													faster=false;
													quick=false;
													((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
													controller.getBoard().setCaptain(null);
													controller.getBoard().setChampImage(null);
													if(controller.getBoard().isAmerica()) {
														((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
													((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
													controller.getBoard().setAmerica(false);
													}
													
													controller.getBoard().getMove().repaint();
													}
											}
											
								}else {
//										if(controller.getBoard().isSilver()) {
//											controller.getBoard().setXfpPos(controller.getCurrentGame().getCurrentChampion().getLocation().y*141+337);
//											controller.getBoard().setYfPos((4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*141+17);
//											timer = new javax.swing.Timer(2,new ActionListener() {
//													
//												@Override
//												public void actionPerformed(ActionEvent e) {
//													// TODO Auto-generated method stub
//													controller.getBoard().setXfpPos(controller.getBoard().getXfpPos()+4);
//													controller.getBoard().getMove().repaint();
//													if (controller.getBoard().getXfpPos() >= 893) {
//														timer.stop();
//														controller.getBoard().setSilver(false);
//														((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
//													}
//												}
//												
//											});
//											timer.start();
//										}
									switch (controller.getCurrentGame().getCurrentChampion().getName()) {
									case "Hela" :
									case "Loki" :
									case "Spiderman" :
										controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "_Right.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
										break;
									case "Iceman" :
									case "Yellow Jacket" :
										controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "2.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
										break;
									}
									if (controller.getCurrentGame().getCurrentChampion().getName().equals("Ghost Rider"))
										controller.getBoard().setChampImage(new ImageIcon(text+"_Right.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
									if (controller.getCurrentGame().getCurrentChampion().getName().equals("Thor"))
										controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "2.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
									if (controller.getCurrentGame().getCurrentChampion().getName().equals("Electro"))
										controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "2.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
									controller.getBoard().setxPos(controller.getBoard().getxPos()+speed);
									controller.getBoard().getMove().repaint();
										if (controller.getBoard().getxPos() >= controller.getBoard().getFinalX()) {
											timer.stop();
											controller.getBoard().setChampImage(null); 
											moving = false;
											if(controller.getBoard().isAmerica()) {
												((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
											((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
											controller.getBoard().setAmerica(false);
											}
											controller.getBoard().getMove().repaint();
											}
								}
								}else
									if(num==3) {
										if(controller.getBoard().isSilver()) {
											
											
											System.out.println("Quick");
											if(!faster) {
											controller.getBoard().setYfPos(controller.getBoard().getYfPos()-12);
											controller.getBoard().getMove().repaint();
											if (controller.getBoard().getYfPos() <= 16 && !quick) {
												quick = true;
												controller.getBoard().setYfPos(576);
												System.out.println("Stop");
												
											}
											if (controller.getBoard().getYfPos() <= (4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*141+17 && quick) {
												
												//controller.getBoard().setChampImage(null); 
												faster = true;
												controller.getBoard().setSilver(false);
												((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
											}}else {
												controller.getBoard().setyPos(controller.getBoard().getyPos()-speed);
												controller.getBoard().getMove().repaint();
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
												if (controller.getBoard().getyPos() <= controller.getBoard().getFinalY()) {
													timer.stop();
													controller.getBoard().setChampImage(null); 
													moving = false;
													faster=false;
													quick=false;
													((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
													controller.getBoard().setCaptain(null);
													controller.getBoard().setChampImage(null);
													if(controller.getBoard().isAmerica()) {
														((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
													((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
													controller.getBoard().setAmerica(false);
													}
													
													controller.getBoard().getMove().repaint();
													}
											}
											
								}else {
//										if(controller.getBoard().isSilver()) {
//											controller.getBoard().setXfpPos(controller.getCurrentGame().getCurrentChampion().getLocation().y*141+337);
//											controller.getBoard().setYfPos((4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*141+17);
//											timer = new javax.swing.Timer(2,new ActionListener() {
//													
//												@Override
//												public void actionPerformed(ActionEvent e) {
//													// TODO Auto-generated method stub
//													controller.getBoard().setYfPos(controller.getBoard().getYfPos()-4);
//													controller.getBoard().getMove().repaint();
//													if (controller.getBoard().getYfPos() <= 576) {
//														timer.stop();
//														controller.getBoard().setSilver(false);
//														((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
//													}
//												}
//												
//											});
//											timer.start();
//										}
									switch (controller.getCurrentGame().getCurrentChampion().getName()) {
									case "Hela" :
									case "Loki" :
									case "Spiderman" :
										controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "_Up.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
										break;
									case "Iceman" :
									case "Yellow Jacket" :
										controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
										break;
									}
									if (controller.getCurrentGame().getCurrentChampion().getName().equals("Ghost Rider"))
										controller.getBoard().setChampImage(new ImageIcon(text+"_Up.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
									if (controller.getCurrentGame().getCurrentChampion().getName().equals("Thor"))
										controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
									if (controller.getCurrentGame().getCurrentChampion().getName().equals("Electro"))
										controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
									controller.getBoard().setyPos(controller.getBoard().getyPos()-speed);
									controller.getBoard().getMove().repaint();
									if (controller.getBoard().getyPos() <= controller.getBoard().getFinalY()) {
										timer.stop();
										controller.getBoard().setChampImage(null); 
										moving = false;
										if(controller.getBoard().isAmerica()) {
											((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
										((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
										controller.getBoard().setAmerica(false);
										}
										controller.getBoard().getMove().repaint();
										}
								}
								}else
									if(num==4) {
										if(controller.getBoard().isSilver()) {
											
											
											System.out.println("Quick");
											if(!faster) {
											controller.getBoard().setYfPos(controller.getBoard().getYfPos()+12);
											controller.getBoard().getMove().repaint();
											if (controller.getBoard().getYfPos() >= 576 && !quick) {
												quick = true;
												controller.getBoard().setYfPos(16);
												System.out.println("Stop");
												
											}
											if (controller.getBoard().getYfPos() >= (4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*141+17 && quick) {
												
												//controller.getBoard().setChampImage(null); 
												faster = true;
												controller.getBoard().setSilver(false);
												((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
											}}else {
												controller.getBoard().setyPos(controller.getBoard().getyPos()+speed);
												controller.getBoard().getMove().repaint();
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "_down.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
												if (controller.getBoard().getyPos() >= controller.getBoard().getFinalY()) {
													timer.stop();
													controller.getBoard().setChampImage(null); 
													moving = false;
													faster=false;
													quick=false;
													((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
													controller.getBoard().setCaptain(null);
													controller.getBoard().setChampImage(null);
													if(controller.getBoard().isAmerica()) {
														((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
													((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
													controller.getBoard().setAmerica(false);
													}
													
													controller.getBoard().getMove().repaint();
													}
											}
											
								}else {
//										if(controller.getBoard().isSilver()) {
//											controller.getBoard().setXfpPos(controller.getCurrentGame().getCurrentChampion().getLocation().y*141+337);
//											controller.getBoard().setYfPos((4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*141+17);
//											timer = new javax.swing.Timer(2,new ActionListener() {
//													
//												@Override
//												public void actionPerformed(ActionEvent e) {
//													// TODO Auto-generated method stub
//													controller.getBoard().setYfPos(controller.getBoard().getYfPos()+4);
//													controller.getBoard().getMove().repaint();
//													if (controller.getBoard().getYfPos() >= 576) {
//														timer.stop();
//														controller.getBoard().setSilver(false);
//														((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
//													}
//												}
//												
//											});
//											timer.start();
//										}
									switch (controller.getCurrentGame().getCurrentChampion().getName()) {
									case "Hela" :
									case "Loki" :
									case "Spiderman" :
										controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "_Down.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
										break;
									case "Iceman" :
									case "Yellow Jacket" :
										controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
										break;
									}
									if (controller.getCurrentGame().getCurrentChampion().getName().equals("Ghost Rider"))
										controller.getBoard().setChampImage(new ImageIcon(text+"_Down.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
									if (controller.getCurrentGame().getCurrentChampion().getName().equals("Thor"))
										controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "3.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
									if (controller.getCurrentGame().getCurrentChampion().getName().equals("Electro"))
										controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
									controller.getBoard().setyPos(controller.getBoard().getyPos()+speed);
									controller.getBoard().getMove().repaint();
									if (controller.getBoard().getyPos() >= controller.getBoard().getFinalY()) {
										timer.stop();
										controller.getBoard().setChampImage(null); 
										moving = false;
										if(controller.getBoard().isAmerica()) {
										((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
										((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
										controller.getBoard().setAmerica(false);
										}
										controller.getBoard().getMove().repaint();
										}
								}
								
								controller.getBoard().getMove().repaint();
								
								//testBoard.setCount(testBoard.getCount()+1);
								
//								if (controller.getBoard().getxPos() >= controller.getBoard().getFinalX()) {timer.stop();
//								controller.getBoard().setChampImage(null); controller.getBoard().getMove().repaint();}
								//testBoard.getMove().repaint();
								
								
							}
							}
						});timer.start(); 
						
						break;}
				} 
				 controller.getBoard().drawBoard(controller.getCurrentGame().getBoard());
				 controller.getBoard().getLeader().setText(controller.getBoard().putText(controller.getCurrentGame().getCurrentChampion()));
				} catch (NotEnoughResourcesException | InvalidTargetException | ChampionDisarmedException e1) {
				board.getErrorPanel().setVisible(true);
				try {
					playAudio("button_error.wav");
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
					e2.printStackTrace();
				}
						board.repaint();
						board.revalidate();

						String msg = (e1.getMessage() != null) ? e1.getMessage() : "";
						if(e1 instanceof InvalidTargetException)board.getErrorLabel().setText("You Can't Attack There\n" + msg);
						else if(e1 instanceof NotEnoughResourcesException) board.getErrorLabel().setText("Your Champion Doesn't Have the Enough Resources\n" + msg);
						else if (e1 instanceof ChampionDisarmedException) board.getErrorLabel().setText("Your Champion is Disarmed");

						new Timer().schedule(new TimerTask() {
							@Override
							public void run() {
								board.getErrorPanel().setVisible(false);
								board.repaint();
								board.revalidate();
							}
						}, 1500);
						System.out.println((e1.getMessage() == null) ? e1.getClass().getName() : e1.getMessage());
				System.out.println((e1.getMessage() == null) ? e1.getClass().getName() : e1.getMessage());
			}
			checkGame();
		}
	}
	
	// Listener to handle casting abilities
	/* ButtonID = cast| (abilityIndex inside of champion ability arrayList) |direction*/
	public class CastListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				playAudio("button_click.wav");
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			String[] buttonID = ((JButton) e.getSource()).getName().split("\\|");
			// Ensure it was a move button
			if (!buttonID[0].equals("cast")) {
				System.out.println(ANSI_RED + "[ERROR] CastListener - given incorrect button of type " + buttonID[0] + ANSI_RESET);
				return;
			}


			Champion currentChamp = currentGame.getCurrentChampion();
			Ability chosenAbility = currentChamp.getAbilities().get(Integer.parseInt(buttonID[1]));
			
			switch (chosenAbility.getCastArea()) {
				case DIRECTIONAL:
					//if(moving)return;
					Direction direction = Direction.directionOf(buttonID[2].toLowerCase());
					try {
						controller.getBoard().drawBoard(controller.getCurrentGame().getBoard());
						 controller.getBoard().getLeader().setText(controller.getBoard().putText(controller.getCurrentGame().getCurrentChampion()));
						
						currentGame.castAbility(chosenAbility, Direction.directionOf(buttonID[2]));
						if (chosenAbility instanceof DamagingAbility) {
						boardGame = controller.getCurrentGame().getBoard();
						for (int i = 1; i <= chosenAbility.getCastRange(); i++) {
							Point p = controller.getCurrentGame().calcDirection(controller.getCurrentGame().getCurrentChampion().getLocation(), direction, i);
							
							int x = (int) p.getLocation().getX();
							int y = (int) p.getLocation().getY();
							
							//if (x < 0 || x >= 5) throw new InvalidTargetException("Target out of board bounds");
							//if (y < 0 || y >= 5) throw new InvalidTargetException("Target out of board bounds");
							
							if (boardGame[x][y]!=null) {
								System.out.println("Directional");
								controller.getBoard().setxPos(controller.getCurrentGame().getCurrentChampion().getLocation().y*141+337+50);
								controller.getBoard().setyPos((4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*141+17+50);
								
								controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
								//controller.getBoard().setChampImage(new ImageIcon("assets/characters/128/Untitled22_20220607135427.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
								//Rotate rotate = new Rotate(90);
								//controller.getBoard().getChampImage().add(new Rotate(90));
								controller.getBoard().setFinalX(y*141+337+50);
								controller.getBoard().setFinalY((4-x)*141+17+50);
								System.out.println("X Pos: "+controller.getBoard().getxPos());
								System.out.println("Final x: "+controller.getBoard().getFinalX());
								System.out.println("Y Pos: "+controller.getBoard().getyPos());
								System.out.println("Final y: "+controller.getBoard().getFinalY());
								
								num = 0;
								count = 1;
								speed=2;
								
								if(controller.getBoard().getFinalX()<controller.getBoard().getxPos()) {
									num=1;
								}else
									if(controller.getBoard().getFinalX()>controller.getBoard().getxPos()) {
										num=2;
								}else
									if(controller.getBoard().getFinalY()<controller.getBoard().getyPos()) {
										num=3;
								}else
									if(controller.getBoard().getFinalY()>controller.getBoard().getyPos()) {
										num=4;
								}
								controller.getBoard().setXfpPos(controller.getCurrentGame().getCurrentChampion().getLocation().y*141+337);
								controller.getBoard().setYfPos((4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*141+17);
								timer = new javax.swing.Timer(2,new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										// TODO Auto-generated method stub
										moving = true;
										switch(controller.getCurrentGame().getCurrentChampion().getName()){
										case "Deadpool":
											speed=1;
											switch(count) {
											case 1:
											case 2 :
											case 3 :
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
												count++;
												break;
											case 4:
											case 5:
											case 6:
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "2.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
												count++;
												break;
											case 7:
											case 8:
											case 9:
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "3.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
												count++;
												break;
											case 10:
											case 11:
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "4.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
												count++;
												break;
											case 12:
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "4.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
												count=1;
												break;
											}
										break;
										
										case "Ghost Rider":
											speed=1;
											System.out.println("Enter..");
											switch(count) {
												case 1:
													text="assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() ;
													count++;
													break;
												case 2:
													text="assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() +count;
													count++;
													break;
												case 3:
													text="assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() +count;
													count=1;
													break;
												}
											break;
										case "Dr Strange":
										case "Ironman":
										
											speed=1;
											System.out.println("Enter..");
											switch(count) {
												case 1:
													controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
													count++;
													break;
												case 2:
													controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() +count+ ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
													count++;
													break;
												case 3:
													controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() +count+ ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
													count=1;
													break;
												}
											break;
										case "Captain America":
											speed=1;
											controller.getBoard().setAmerica(true);
											//controller.getBoard().setXfpPos(controller.getCurrentGame().getCurrentChampion().getLocation().y);
											//controller.getBoard().setYfPos((4-controller.getCurrentGame().getCurrentChampion().getLocation().x));
											((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
											((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + "2.png"));
											//((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon();
											break;
										case "Quicksilver":
											controller.getBoard().setSilver(true);
											speed=3;
											controller.getBoard().setChampImage(null);
											((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
											controller.getBoard().setCaptain((new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png")).getImage());
											
											break;
										}
										if(num==1) {
											if(controller.getBoard().isSilver()) {
												
														
														System.out.println("Quick");
														if(!faster) {
														controller.getBoard().setXfpPos(controller.getBoard().getXfpPos()-12);
														controller.getBoard().getMove().repaint();
														if (controller.getBoard().getXfpPos() <= 333 && !quick) {
															quick = true;
															controller.getBoard().setXfpPos(893);
															System.out.println("Stop");
															
														}
														if (controller.getBoard().getXfpPos() <= controller.getCurrentGame().getCurrentChampion().getLocation().y*141+337 && quick) {
															
															//controller.getBoard().setChampImage(null); 
															faster = true;
															controller.getBoard().setSilver(false);
															((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
														}}else {
															controller.getBoard().setxPos(controller.getBoard().getxPos()-speed);
															controller.getBoard().getMove().repaint();
															controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "_left.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
															if (controller.getBoard().getxPos() <= controller.getBoard().getFinalX()) {
																timer.stop();
																controller.getBoard().setChampImage(null); 
																moving = false;
																faster=false;
																quick=false;
																((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
																controller.getBoard().setCaptain(null);
																controller.getBoard().setChampImage(null);
																if(controller.getBoard().isAmerica()) {
																	((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
																((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
																controller.getBoard().setAmerica(false);
																}
																
																controller.getBoard().getMove().repaint();
																}
														}
														
											}else {
												
												switch (controller.getCurrentGame().getCurrentChampion().getName()) {
												case "Hela" :
												case "Loki" :
												case "Venom" :
												case "Spiderman" :
													controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "_Left.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
													break;
												case "Iceman" :
												case "Yellow Jacket" :
													controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "2.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
													break;
												}
											if (controller.getCurrentGame().getCurrentChampion().getName().equals("Thor"))
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "6.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
											if (controller.getCurrentGame().getCurrentChampion().getName().equals("Ghost Rider"))
												controller.getBoard().setChampImage(new ImageIcon(text+"_left.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
											if (controller.getCurrentGame().getCurrentChampion().getName().equals("Ironman"))
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "5.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
											if (controller.getCurrentGame().getCurrentChampion().getName().equals("Electro"))
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "2.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
											controller.getBoard().setxPos(controller.getBoard().getxPos()-speed);
											controller.getBoard().getMove().repaint();
											if (controller.getBoard().getxPos() <= controller.getBoard().getFinalX()) {
												timer.stop();
												controller.getBoard().setChampImage(null); 
												moving = false;
												if(controller.getBoard().isAmerica()) {
													((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
												((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
												controller.getBoard().setAmerica(false);
												}
												
												controller.getBoard().getMove().repaint();
												}
											}
										}else
											if(num==2) {
												if(controller.getBoard().isSilver()) {
													
													
													System.out.println("Quick");
													if(!faster) {
													controller.getBoard().setXfpPos(controller.getBoard().getXfpPos()+12);
													controller.getBoard().getMove().repaint();
													if (controller.getBoard().getXfpPos() >= 893 && !quick) {
														quick = true;
														controller.getBoard().setXfpPos(333);
														System.out.println("Stop");
														
													}
													if (controller.getBoard().getXfpPos() >= controller.getCurrentGame().getCurrentChampion().getLocation().y*141+337 && quick) {
														
														//controller.getBoard().setChampImage(null); 
														faster = true;
														controller.getBoard().setSilver(false);
														((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
													}}else {
														controller.getBoard().setxPos(controller.getBoard().getxPos()+speed);
														controller.getBoard().getMove().repaint();
														controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "_right.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
														if (controller.getBoard().getxPos() >= controller.getBoard().getFinalX()) {
															timer.stop();
															controller.getBoard().setChampImage(null); 
															moving = false;
															faster=false;
															quick=false;
															((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
															controller.getBoard().setCaptain(null);
															controller.getBoard().setChampImage(null);
															if(controller.getBoard().isAmerica()) {
																((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
															((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
															controller.getBoard().setAmerica(false);
															}
															
															controller.getBoard().getMove().repaint();
															}
													}
													
										}else {
//												if(controller.getBoard().isSilver()) {
//													controller.getBoard().setXfpPos(controller.getCurrentGame().getCurrentChampion().getLocation().y*141+337);
//													controller.getBoard().setYfPos((4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*141+17);
//													timer = new javax.swing.Timer(2,new ActionListener() {
//															
//														@Override
//														public void actionPerformed(ActionEvent e) {
//															// TODO Auto-generated method stub
//															controller.getBoard().setXfpPos(controller.getBoard().getXfpPos()+4);
//															controller.getBoard().getMove().repaint();
//															if (controller.getBoard().getXfpPos() >= 893) {
//																timer.stop();
//																controller.getBoard().setSilver(false);
//																((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
//															}
//														}
//														
//													});
//													timer.start();
//												}
											switch (controller.getCurrentGame().getCurrentChampion().getName()) {
											case "Hela" :
											case "Loki" :
											case "Venom" :
											case "Spiderman" :
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "_Right.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
												break;
											case "Iceman" :
											case "Yellow Jacket" :
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "2.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
												break;
											}
											if (controller.getCurrentGame().getCurrentChampion().getName().equals("Thor"))
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "6.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
											if (controller.getCurrentGame().getCurrentChampion().getName().equals("Ironman"))
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "5.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
											if (controller.getCurrentGame().getCurrentChampion().getName().equals("Electro"))
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "2.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
											if (controller.getCurrentGame().getCurrentChampion().getName().equals("Ghost Rider"))
												controller.getBoard().setChampImage(new ImageIcon(text+"_right.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
											controller.getBoard().setxPos(controller.getBoard().getxPos()+speed);
											controller.getBoard().getMove().repaint();
												if (controller.getBoard().getxPos() >= controller.getBoard().getFinalX()) {
													timer.stop();
													controller.getBoard().setChampImage(null); 
													moving = false;
													if(controller.getBoard().isAmerica()) {
														((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
													((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
													controller.getBoard().setAmerica(false);
													}
													controller.getBoard().getMove().repaint();
													}
										}
										}else
											if(num==3) {
												if(controller.getBoard().isSilver()) {
													
													
													System.out.println("Quick");
													if(!faster) {
													controller.getBoard().setYfPos(controller.getBoard().getYfPos()-12);
													controller.getBoard().getMove().repaint();
													if (controller.getBoard().getYfPos() <= 16 && !quick) {
														quick = true;
														controller.getBoard().setYfPos(576);
														System.out.println("Stop");
														
													}
													if (controller.getBoard().getYfPos() <= (4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*141+17 && quick) {
														
														//controller.getBoard().setChampImage(null); 
														faster = true;
														controller.getBoard().setSilver(false);
														((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
													}}else {
														controller.getBoard().setyPos(controller.getBoard().getyPos()-speed);
														controller.getBoard().getMove().repaint();
														controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
														if (controller.getBoard().getyPos() <= controller.getBoard().getFinalY()) {
															timer.stop();
															controller.getBoard().setChampImage(null); 
															moving = false;
															faster=false;
															quick=false;
															((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
															controller.getBoard().setCaptain(null);
															controller.getBoard().setChampImage(null);
															if(controller.getBoard().isAmerica()) {
																((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
															((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
															controller.getBoard().setAmerica(false);
															}
															
															controller.getBoard().getMove().repaint();
															}
													}
													
										}else {
//												if(controller.getBoard().isSilver()) {
//													controller.getBoard().setXfpPos(controller.getCurrentGame().getCurrentChampion().getLocation().y*141+337);
//													controller.getBoard().setYfPos((4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*141+17);
//													timer = new javax.swing.Timer(2,new ActionListener() {
//															
//														@Override
//														public void actionPerformed(ActionEvent e) {
//															// TODO Auto-generated method stub
//															controller.getBoard().setYfPos(controller.getBoard().getYfPos()-4);
//															controller.getBoard().getMove().repaint();
//															if (controller.getBoard().getYfPos() <= 576) {
//																timer.stop();
//																controller.getBoard().setSilver(false);
//																((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
//															}
//														}
//														
//													});
//													timer.start();
//												}
											switch (controller.getCurrentGame().getCurrentChampion().getName()) {
											case "Hela" :
											case "Loki" :
											case "Venom" :
											case "Spiderman" :
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "_Up.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
												break;
											case "Iceman" :
											case "Yellow Jacket" :
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
												break;
											}
											if (controller.getCurrentGame().getCurrentChampion().getName().equals("Thor"))
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "5.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
											if (controller.getCurrentGame().getCurrentChampion().getName().equals("Ironman"))
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "4.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
											if (controller.getCurrentGame().getCurrentChampion().getName().equals("Electro"))
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
											if (controller.getCurrentGame().getCurrentChampion().getName().equals("Ghost Rider"))
												controller.getBoard().setChampImage(new ImageIcon(text+"_Up.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
											controller.getBoard().setyPos(controller.getBoard().getyPos()-speed);
											controller.getBoard().getMove().repaint();
											if (controller.getBoard().getyPos() <= controller.getBoard().getFinalY()) {
												timer.stop();
												controller.getBoard().setChampImage(null); 
												moving = false;
												if(controller.getBoard().isAmerica()) {
													((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
												((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
												controller.getBoard().setAmerica(false);
												}
												controller.getBoard().getMove().repaint();
												}
										}
										}else
											if(num==4) {
												if(controller.getBoard().isSilver()) {
													
													
													System.out.println("Quick");
													if(!faster) {
													controller.getBoard().setYfPos(controller.getBoard().getYfPos()+12);
													controller.getBoard().getMove().repaint();
													if (controller.getBoard().getYfPos() >= 576 && !quick) {
														quick = true;
														controller.getBoard().setYfPos(16);
														System.out.println("Stop");
														
													}
													if (controller.getBoard().getYfPos() >= (4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*141+17 && quick) {
														
														//controller.getBoard().setChampImage(null); 
														faster = true;
														controller.getBoard().setSilver(false);
														((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
													}}else {
														controller.getBoard().setyPos(controller.getBoard().getyPos()+speed);
														controller.getBoard().getMove().repaint();
														controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "_down.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
														if (controller.getBoard().getyPos() >= controller.getBoard().getFinalY()) {
															timer.stop();
															controller.getBoard().setChampImage(null); 
															moving = false;
															faster=false;
															quick=false;
															((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
															controller.getBoard().setCaptain(null);
															controller.getBoard().setChampImage(null);
															if(controller.getBoard().isAmerica()) {
																((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
															((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
															controller.getBoard().setAmerica(false);
															}
															
															controller.getBoard().getMove().repaint();
															}
													}
													
										}else {
//												if(controller.getBoard().isSilver()) {
//													controller.getBoard().setXfpPos(controller.getCurrentGame().getCurrentChampion().getLocation().y*141+337);
//													controller.getBoard().setYfPos((4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*141+17);
//													timer = new javax.swing.Timer(2,new ActionListener() {
//															
//														@Override
//														public void actionPerformed(ActionEvent e) {
//															// TODO Auto-generated method stub
//															controller.getBoard().setYfPos(controller.getBoard().getYfPos()+4);
//															controller.getBoard().getMove().repaint();
//															if (controller.getBoard().getYfPos() >= 576) {
//																timer.stop();
//																controller.getBoard().setSilver(false);
//																((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
//															}
//														}
//														
//													});
//													timer.start();
//												}
											switch (controller.getCurrentGame().getCurrentChampion().getName()) {
											case "Hela" :
											case "Loki" :
											case "Venom" :
											case "Spiderman" :
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "_Down.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
												break;
											case "Iceman" :
											case "Yellow Jacket" :
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
												break;
											}
											if (controller.getCurrentGame().getCurrentChampion().getName().equals("Thor"))
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "5.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
											if (controller.getCurrentGame().getCurrentChampion().getName().equals("Electro"))
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
											if (controller.getCurrentGame().getCurrentChampion().getName().equals("Ironman"))
												controller.getBoard().setChampImage(new ImageIcon("assets/characters/32/" + controller.getCurrentGame().getCurrentChampion().getName() + "4.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
											if (controller.getCurrentGame().getCurrentChampion().getName().equals("Ghost Rider"))
												controller.getBoard().setChampImage(new ImageIcon(text+"_Down.png", controller.getCurrentGame().getCurrentChampion().getName()).getImage());
											controller.getBoard().setyPos(controller.getBoard().getyPos()+speed);
											controller.getBoard().getMove().repaint();
											if (controller.getBoard().getyPos() >= controller.getBoard().getFinalY()) {
												timer.stop();
												controller.getBoard().setChampImage(null); 
												moving = false;
												if(controller.getBoard().isAmerica()) {
												((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(null);
												((JButton) controller.getBoard().getPanel2().getComponent(controller.getCurrentGame().getCurrentChampion().getLocation().y+(4-controller.getCurrentGame().getCurrentChampion().getLocation().x)*5)).setIcon(new ImageIcon("assets/characters/new64/" + controller.getCurrentGame().getCurrentChampion().getName() + ".png"));
												controller.getBoard().setAmerica(false);
												}
												controller.getBoard().getMove().repaint();
												}
										}
										
										controller.getBoard().getMove().repaint();
										
										//testBoard.setCount(testBoard.getCount()+1);
										
//										if (controller.getBoard().getxPos() >= controller.getBoard().getFinalX()) {timer.stop();
//										controller.getBoard().setChampImage(null); controller.getBoard().getMove().repaint();}
										//testBoard.getMove().repaint();
										
										
									}
									}
								});timer.start(); 
								
								break;}
						} 
						 controller.getBoard().drawBoard(controller.getCurrentGame().getBoard());
						 controller.getBoard().getLeader().setText(controller.getBoard().putText(controller.getCurrentGame().getCurrentChampion()));
						}} catch (AbilityUseException | NotEnoughResourcesException | CloneNotSupportedException e1) {
						board.getErrorPanel().setVisible(true);
						board.repaint();
						board.revalidate();

						String msg = (e1.getMessage() != null) ? e1.getMessage() : "";
						if(e1 instanceof AbilityUseException)board.getErrorLabel().setText("You Can't Use this Ability\n" + msg);
						else if(e1 instanceof NotEnoughResourcesException) board.getErrorLabel().setText("Your Champion Doesn't Have the Enough Resources\n" + msg);

						new Timer().schedule(new TimerTask() {
							@Override
							public void run() {
								board.getErrorPanel().setVisible(false);
								board.repaint();
								board.revalidate();
							}
						}, 1500);
						System.out.println((e1.getMessage() == null) ? e1.getClass().getName() : e1.getMessage());
					}
					break;
				case SELFTARGET:
				case SURROUND:
				case TEAMTARGET:

					try {
						currentGame.castAbility(chosenAbility);
					} catch (AbilityUseException | NotEnoughResourcesException | CloneNotSupportedException e1) {
						board.getErrorPanel().setVisible(true);
				try {
					playAudio("button_error.wav");
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
					e2.printStackTrace();
				}
						board.repaint();
						board.revalidate();

						String msg = (e1.getMessage() != null) ? e1.getMessage() : "";
						if(e1 instanceof AbilityUseException)board.getErrorLabel().setText("You Can't Use this Ability\n" + msg);
						else if(e1 instanceof NotEnoughResourcesException) board.getErrorLabel().setText("Your Champion Doesn't Have the Enough Resources\n" + msg);

						new Timer().schedule(new TimerTask() {
							@Override
							public void run() {
								board.getErrorPanel().setVisible(false);
								board.repaint();
								board.revalidate();
							}
						}, 1500);
						System.out.println((e1.getMessage() == null) ? e1.getClass().getName() : e1.getMessage());
					}

					break;
				case SINGLETARGET: {
					System.out.println("Board Casting - SINGLETARGET");
					// Set casting true
					isBoardCasting = true;

					// Set ability index
					abilityIndex = Integer.parseInt(buttonID[1]);
					break;
				}
				default:
					// Throw error
					break;
			}
			checkGame();
		}
	}

	public class NextListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				try {
					playAudio("button_click.wav");
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					e1.printStackTrace();
				}
				lead = new LeaderSelection(controller, currentGame);
				changeScreen(select, lead);
			} catch (FontFormatException | IOException e1) {
				System.out.println((e1.getMessage() == null) ? e1.getClass().getName() : e1.getMessage());
			}
		}	
	}
	
	/* ButtonID = board|x|y */
	public class BoardListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String[] buttonID = ((JButton) e.getSource()).getName().split("\\|");

			if (!buttonID[0].equals("board")) {
				System.out.println("[ERROR] BoardListener - Invalid ID of " + buttonID[0]);
				return;
			}

			if (isBoardCasting) {
				if (abilityIndex < 0 || abilityIndex > currentGame.getCurrentChampion().getAbilities().size() - 1) {
					System.out.println("[ERROR] BoardListener - Invalid ability index of " + abilityIndex);
					return;
				}

				try {
					currentGame.castAbility(currentGame.getCurrentChampion().getAbilities().get(abilityIndex), Integer.parseInt(buttonID[1]), Integer.parseInt(buttonID[2]));
				} catch (NumberFormatException | AbilityUseException | InvalidTargetException
						| NotEnoughResourcesException | CloneNotSupportedException e1) {
							board.getErrorPanel().setVisible(true);
				try {
					playAudio("button_error.wav");
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
					e2.printStackTrace();
				}
							board.repaint();
							board.revalidate();
							
							String msg = (e1.getMessage() != null) ? e1.getMessage() : "";
							if(e1 instanceof AbilityUseException)board.getErrorLabel().setText("You Can't Use this Ability\n" + msg);
							else if(e1 instanceof NotEnoughResourcesException) board.getErrorLabel().setText("Your Champion Doesn't Have the Enough Resources\n" + msg);
							else if (e1 instanceof InvalidTargetException) board.getErrorLabel().setText("Invalid Target");
	
							new Timer().schedule(new TimerTask() {
								@Override
								public void run() {
									board.getErrorPanel().setVisible(false);
									board.repaint();
									board.revalidate();
								}
							}, 1500);
							System.out.println((e1.getMessage() == null) ? e1.getClass().getName() : e1.getMessage());
					System.out.println((e1.getMessage() == null) ? e1.getClass().getName() : e1.getMessage());
				}
				checkGame();
			}
		}	
	}
	public class SelectListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			for(int i = 0; i < Game.getAvailableChampions().size(); i++){
				if (Game.getAvailableChampions().get(i).getName().equals(select.getCurrentName())){
					try {
						if (select.getCounter()%2==1) {currentGame.getFirstPlayer().getTeam().add(Game.getAvailableChampions().get(i));break;}
						else {currentGame.getSecondPlayer().getTeam().add(Game.getAvailableChampions().get(i));break;}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						System.out.println((e1.getMessage() == null) ? e1.getClass().getName() : e1.getMessage());
					}
			
				}
			
			}
		
		}
	}

	public class ForwardListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				playAudio("button_click.wav");
				stopAudioTheme();
				playAudioGame();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				e1.printStackTrace();
			}
			//panel = screen.getPanel();
//			player1 = currentGame.getFirstPlayer();
//			player2 = currentGame.getSecondPlayer();
			team1 = player1.getTeam();
			team2 = player2.getTeam();
			for(int i = 0; i < team1.size(); i++){
				if (team1.get(i).getName().equals(lead.getLeader().getText())){
					player1.setLeader(team1.get(i));
				}
			}
			for(int i = 0; i < team2.size(); i++){
				if (team2.get(i).getName().equals(lead.getLeader2().getText())){
					player2.setLeader(team2.get(i));
				}
			}
			try {
				currentGame = new Game(player1,player2);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
//			board = new editingBoard(controller);
//			changeScreen(lead, board);
			 board = new BoardView(controller);
			changeScreen(lead, board);
		}
		
	}

	public void resetTeams() {
		System.out.println("Resetting Teams...");
		for(int i = currentGame.getFirstPlayer().getTeam().size()-1; i >= 0; i--){
			currentGame.getFirstPlayer().getTeam().remove(i);
		}
		for(int i = currentGame.getSecondPlayer().getTeam().size()-1; i >= 0; i--){
			currentGame.getSecondPlayer().getTeam().remove(i);
		}
	}

	public void playAudio(String filename) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		new Thread(new Runnable() {
			  public void run() {
				try {
					AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("assets/audio/" + filename).getAbsoluteFile());
					clip = AudioSystem.getClip();
					clip.open(audioStream);
					clip.start();
				} catch (Exception e) {
				  e.printStackTrace();
				}
			  }
			}).start();
	}

	public void stopAudio() {
		clip.stop();
	}

	public void cancelSingleTarget() {
		isBoardCasting = false;
		abilityIndex = -1;
	}

	public void checkGame() {
		Player winner = currentGame.checkGameOver();

		if (winner != null) {
			board.printWinner(winner);
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
}
