package GUI;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import com.sun.tools.javac.Main;

import GUI.BoardTest.Move;
import GUI.buttonPresets.ArrowButtonTypes;
import GUI.buttonPresets.ArrowButtons;
//import GUI.editingBoard.ArrowPanelListener;
//import GUI.editingBoard.ArrowPanelListener;
//import GUI.editingBoard.boardUpdateListener;
//import GUI.editingBoard.AbilityPanelListener;
//import GUI.editingBoard.ArrowPanelListener;
//import GUI.editingBoard.SingleTargetPanelListener;
//import GUI.editingBoard.boardUpdateListener;
//import GUI.editingBoard.boardUpdateListener;
import controller.GameController;
import controller.GameController.BoardListener;
import controller.GameController.CastListener;
import controller.GameController.LeaderAbilityListener;
import engine.Game;
import engine.Player;
import engine.PriorityQueue;
import model.abilities.Ability;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.world.AntiHero;
import model.world.Champion;
import model.world.Cover;

public class BoardView extends JLayeredPane {
	
	JLabel champion,label,label2,leader,leader2;
	JProgressBar bar;
	boolean haslead,america=false;
	boolean silver = false;
	public boolean isSilver() {
		return silver;
	}
	public void setSilver(boolean silver) {
		this.silver = silver;
	}
	Champion first;
	//JLayeredPane panel = new JLayeredPane();
	JLayeredPane panel = this;
	//JFrame frame = this;
	Move move = new Move();
	//JLabel champion,label,label2,leader,leader2;
	//JLayeredPane panel = new JLayeredPane();
	AudioInputStream sound;
	//JLayeredPane panel = this;
	//JFrame frame = this;
	Timer timer;
	GameController control;
	JButton testing,testing2,testing3,testing4,animeMove,up,down,left,right,play,moving,attack,useLeaderAbility,castAbility,endTurn;
	JLabel test2;
	//Move move = new Move();
	Clip clip;
	Boolean testing4bool = false;
	int barIndex;
	Boolean firstBarDraw = true;
	int xPos=500;
	int finalY,xfpPos,yfPos;
	public boolean isAmerica() {
		return america;
	}
	public void setAmerica(boolean america) {
		this.america = america;
	}
	public int getXfpPos() {
		return xfpPos;
	}
	public void setXfpPos(int xfpPos) {
		this.xfpPos = xfpPos;
	}
	public int getYfPos() {
		return yfPos;
	}
	public void setYfPos(int yfPos) {
		this.yfPos = yfPos;
	}
	int yPos =600;
	int finalX;
	Image champImage;
	boolean goAhead=false;
	int transparent,transparent2=360;
	ImageIcon image = new ImageIcon("assets/characters/128/Untitled22_20220607135427.png");
	Image Joey = image.getImage();
	ImageIcon cap = new ImageIcon("assets/characters/new64/Captain America2.png");
	Image captain = cap.getImage();
	ImageIcon image2 = new ImageIcon("assets/characters/128/Untitled22_20220607135443.png");
	Image Joey2 = image2.getImage();
	ImageIcon image3 = new ImageIcon("assets/characters/128/Untitled22_20220607135453.png");
	Image Joey3 = image3.getImage();
	int count = 0;
	ArrayList<JProgressBar> coverBars;
	JLabel errorLabel;
	JLayeredPane panel2 = new JLayeredPane();
	JLayeredPane panel3 = new JLayeredPane();
	JLayeredPane panel4 = new JLayeredPane();
	JLayeredPane panel5 = new JLayeredPane();
	JLayeredPane panel6 = new JLayeredPane();
	JLayeredPane arrowPanel = new JLayeredPane();
	JLayeredPane panelWin;
	JLayeredPane errorPanel;
	ArrayList<Champion> team1;
	ArrayList<Champion> team2;
	PriorityQueue turnOrder;
	Champion next;
	PriorityQueue temp = new PriorityQueue(6);
	String sound_track;
    Music se = new Music();
	Game game;
	InputStream is;
	Font font,font2,font3,font4,font5Big,font5Small,font5,plain,blackWidowFont;
	JLabel currentBox,nextBox,currentLabel,nextLabel;
	//Graphics2D g2 = (Graphics2D) g;
	
	public BoardView(GameController control) {
		coverBars = new ArrayList();
		this.control = control;
		this.setSize(1366,768);
		this.setVisible(true);
		move.setSize(1366,768);
		game = control.getCurrentGame();
		team1= game.getFirstPlayer().getTeam();
		team2= game.getSecondPlayer().getTeam();
		turnOrder = game.getTurnOrder();
		first = (Champion) turnOrder.peekMin();
		 next=null;
		int f = 0;
		while (!turnOrder.isEmpty()) {
			if(f==1) next = (Champion) turnOrder.peekMin();
			temp.insert(turnOrder.remove());
			f++;
		}
		while (!temp.isEmpty()) {
			//if(f==1) next = (Champion) turnOrder.peekMin();
			turnOrder.insert(temp.remove());
		}
		//		leader=new JLabel("");
//		leader.setText("<html> Current Champion <br> Current HP</html>");
//		leader.setBounds(1100,290,225,300);
//		leader.setVerticalAlignment(JLabel.TOP);
//		leader.setOpaque(true);
//		leader.setBackground(Color.white);
//		leader2=new JLabel("");
//		
//		
		//testBoard[1][3] = new AntiHero("Dr Strange", 0, 0, 0, 0, 0, 0);
//		
		Object[][] board = control.getCurrentGame().getBoard();
		drawBoard(board);
//		
//		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
//		this.getContentPane().add(panel);	
//		//this.getContentPane().setBackground(Color.pink);
//		this.setSize(1366,768);
//		this.setVisible(true);
//		this.setResizable(false);

				
				
		sound_track = "assets/audio/Bring Me Thanos.mp3";
		currentBox = new JLabel();
		currentBox.setBounds(1100, 50, 15, 15);
		currentBox.setBorder(BorderFactory.createLineBorder(Color.green, 3));
		panel.add(currentBox,Integer.valueOf(1));
		nextBox = new JLabel();
		nextBox.setBounds(1200, 50, 15, 15);
		nextBox.setBorder(BorderFactory.createLineBorder(Color.yellow, 3));
		panel.add(nextBox,Integer.valueOf(1));
		currentLabel = new JLabel("Current");
		currentLabel.setForeground(new Color(0xf8df82).brighter());
		currentLabel.setBounds(1120, 50, 70, 15);
		panel.add(currentLabel,Integer.valueOf(1));
		nextLabel = new JLabel("Next");
		nextLabel.setBounds(1220, 50, 70, 15);
		nextLabel.setForeground(new Color(0xf8df82).brighter());
		panel.add(nextLabel,Integer.valueOf(1));
		
		try {
			font4 = Font.createFont(Font.TRUETYPE_FONT,new File( "assets/fonts/Super Webcomic Bros. Bold Italic.ttf"));
			plain = font4.deriveFont(Font.PLAIN,20f);
		} 
		catch (FontFormatException e1) {} 
		catch (IOException e1) {}
		try {
			font5 = Font.createFont(Font.TRUETYPE_FONT,new File( "assets/fonts/eight-bit-dragon.otf"));
			font5Big = font5.deriveFont(Font.PLAIN, 38f);
			font5Small = font5.deriveFont(Font.PLAIN,14f);
			font5 = font5.deriveFont(Font.PLAIN,28f);
			
		} 
		catch (FontFormatException e1) {} 
		catch (IOException e1) {}
		font = new Font("serif",Font.ITALIC + Font.BOLD,30);
		font2 = new Font("serif", Font.BOLD,20);
		font3 = new Font("serif", Font.BOLD,70);
		font4 = new Font("serif", Font.BOLD,35);

        
		try {
			is = new BufferedInputStream(new FileInputStream("assets/fonts/BlackWidowMovie-d95Rg.ttf"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			blackWidowFont = Font.createFont(Font.TRUETYPE_FONT,is);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Font selectChamp = blackWidowFont.deriveFont(45f);
        Font player = blackWidowFont.deriveFont(20f);
		//panel2 = new JLayeredPane();
		
		play = new JButton("Play Sound");
		play.setBounds(1200,300,100,100);
		play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				AudioClip clip2 = Applet.newAudioClip(BoardView.class.getResource("assets/audio/Bring Me Thanos.mp3"));
//				clip2.play();
				//3esssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
				//se.setFile(sound_track);
                //se.play();
			}
			
		});
		//panel.add(play,Integer.valueOf(1));
		
//		
//		up = new JButton();
//		// 
//		up.setBounds(1200, 500, 100, 100);
//		up.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				xPos = game.getCurrentChampion().getLocation().y*141+337;
//				yPos = game.getCurrentChampion().getLocation().x*141+16;
//				/*Up*/
//				finalY = yPos-141;
//				
//				champImage = new ImageIcon("assets/characters/128/" + game.getCurrentChampion().getName() + ".png", game.getCurrentChampion().getName()).getImage();
//				timer = new Timer(20,new ActionListener() {
//					
//						@Override
//						public void actionPerformed(ActionEvent e) {
//							// TODO Auto-generated method stub
//					
//						
//						
//						yPos--;
//						move.repaint();
//					//	
//						//testBoard.setCount(testBoard.getCount()+1);
//						if (yPos <= finalY) timer.stop();
//						//testBoard.getMove().repaint();
//						
//						
//						}
//				});
//					((JButton) panel2.getComponent(game.getCurrentChampion().getLocation().y+game.getCurrentChampion().getLocation().x*5)).setIcon(null);
//					timer.start();
//			}
//			
//		});
//		panel.add(up,Integer.valueOf(1));
//		
		down = new JButton();
		// 
		down.setBounds(1200, 600, 100, 100);
		down.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				xPos = game.getCurrentChampion().getLocation().y*141+337;
				yPos = (4-game.getCurrentChampion().getLocation().x)*141+17;
				/*Up*/
				finalY = yPos+141;
				
				champImage = new ImageIcon("assets/characters/128/" + game.getCurrentChampion().getName() + ".png", game.getCurrentChampion().getName()).getImage();
				timer = new Timer(20,new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
						
						
						yPos++;
						move.repaint();
						//	
						//testBoard.setCount(testBoard.getCount()+1);
						if (yPos >= finalY) timer.stop();
						//testBoard.getMove().repaint();
						
						
					}
				});
				((JButton) panel2.getComponent(game.getCurrentChampion().getLocation().y+game.getCurrentChampion().getLocation().x*5)).setIcon(null);
				timer.start();
			}
			
		});
		//panel.add(down,Integer.valueOf(8));
		left = new JButton();
		// 
		left.setBounds(1100, 550, 100, 100);
		left.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				xPos = game.getCurrentChampion().getLocation().y*141+337;
				yPos = (4-game.getCurrentChampion().getLocation().x)*141+17;
				/*Up*/
				finalX = xPos-141;
				
				champImage = new ImageIcon("assets/characters/128/" + game.getCurrentChampion().getName() + ".png", game.getCurrentChampion().getName()).getImage();
				timer = new Timer(20,new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
						
						
						xPos--;
						move.repaint();
						//	
						//testBoard.setCount(testBoard.getCount()+1);
						if (xPos <= finalX) timer.stop();
						//testBoard.getMove().repaint();
						
						
					}
				});
				((JButton) panel2.getComponent(game.getCurrentChampion().getLocation().y+game.getCurrentChampion().getLocation().x*5)).setIcon(null);
				timer.start();
			}
			
		});
		//panel.add(left,Integer.valueOf(8));
//		right = new JButton();
//		// 
//		right.setBounds(1300, 550, 100, 100);
//		right.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				xPos = game.getCurrentChampion().getLocation().y*141+337;
//				yPos = game.getCurrentChampion().getLocation().x*141+17;
//				/*Up*/
//				finalX = xPos+141;
//				
//				champImage = new ImageIcon("assets/characters/128/" + game.getCurrentChampion().getName() + ".png", game.getCurrentChampion().getName()).getImage();
//				timer = new Timer(20,new ActionListener() {
//					
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						// TODO Auto-generated method stub
//						
//						
//						
//						xPos++;
//						move.repaint();
//						//	
//						//testBoard.setCount(testBoard.getCount()+1);
//						if (xPos >= finalX) timer.stop();
//						//testBoard.getMove().repaint();
//						
//						
//					}
//				});
//				((JButton) panel2.getComponent(game.getCurrentChampion().getLocation().y+game.getCurrentChampion().getLocation().x*5)).setIcon(null);
//				timer.start();
//			}
//			
//		});
//		panel.add(right,Integer.valueOf(1));
		
		testing = new JButton();
        testing.setOpaque(false);
        testing.setContentAreaFilled(false);
        testing.setFocusPainted(false);
        testing.setBorder(BorderFactory.createLineBorder((Color.green), 1));
        testing.setBounds(66, 315, 114, 20);
        testing.setVisible(false);
        
        testing2 = new JButton();
        testing2.setContentAreaFilled(false);
        testing2.setFocusPainted(false);
        testing2.setBorder(BorderFactory.createLineBorder((Color.green), 1));
        testing2.setBounds(66, 335, 114, 20);
        testing2.setVisible(false);
        
        testing3 = new JButton();
        testing3.setContentAreaFilled(false);
        testing3.setFocusPainted(false);
        testing3.setBorder(BorderFactory.createLineBorder((Color.green), 1));
        testing3.setBounds(66, 355, 114, 20);
        testing3.setVisible(false);
        
        testing4 = new JButton();
        testing4.setContentAreaFilled(false);
        testing4.setFocusPainted(false);
        testing4.setBorder(BorderFactory.createLineBorder((Color.green), 1));
        testing4.setBounds(66, 375, 114, 20);
        testing4.setVisible(false);
        
        test2 = new JLabel("");
       // test2.setFont(font3);
        test2.setForeground(Color.black);
        test2.setBounds(15,480,320,230);
        test2.setVerticalAlignment(JLabel.TOP);
        test2.setVerticalAlignment(JLabel.TOP);
        test2.setVisible(false);
        test2.setForeground(new Color(0xf8df82).brighter());
        //test2.setOpaque(true);
        //test2.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        //test2.setBackground(new Color(103,0,0));
		
        panel.add(testing,Integer.valueOf(1));
        panel.add(testing2,Integer.valueOf(1));
        panel.add(testing3,Integer.valueOf(1));
        panel.add(test2,Integer.valueOf(1));
        panel.add(panel2,Integer.valueOf(1));
        
        
        
		leader = new JLabel();
		leader.setBounds(20, 50, 300, 430);
		//leader.setOpaque(true);
		leader.setVerticalAlignment(JLabel.TOP);
		leader.setHorizontalAlignment(JLabel.LEFT);
		//leader.setBackground(new Color(5,5,5,50));
		//leader.setOpaque(true);
		leader.setForeground(new Color(0xf8df82).brighter());
		leader.setText(putText(control.getCurrentGame().getCurrentChampion()));
		leader.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
				//currentGame.getAvailableChampions()timer = new Timer(2000,control.new TimeListener());
				int length=0;
				int length2=0;
				int length3=0;
				int length4 = 0;
				FontRenderContext frc = new FontRenderContext(new AffineTransform(),true,true);
				//for(int i = 0; i < control.getCurrentGame().getAvailableChampions().size(); i++){
					//if (!(control.getCurrentGame().getAvailableChampions().get(i).getName().equals(currentName))) continue;
					//if (control.getCurrentGame().getAvailableChampions().get(i).getName().equals(currentName)){
						Font fadl = leader.getFont().deriveFont(14f);
						length = (int) fadl.getStringBounds(game.getCurrentChampion().getAbilities().get(0).getName(), frc).getWidth() +14;
						length2 = (int) fadl.getStringBounds(game.getCurrentChampion().getAbilities().get(1).getName(), frc).getWidth() +14;
						length3 = (int) fadl.getStringBounds(game.getCurrentChampion().getAbilities().get(2).getName(), frc).getWidth() +14;
						panel.add(testing4,Integer.valueOf(1));
						if (game.getCurrentChampion().getAbilities().size()==4) {
							length4 = (int) fadl.getStringBounds(game.getCurrentChampion().getAbilities().get(3).getName(), frc).getWidth() +14;
						}else 
							panel.remove(testing4);
					//}
				
				if (e.getX()<=44+length && e.getX()>=44 && e.getY()>=260 && e.getY()<280){
					testing2.setVisible(false);
					testing4.setVisible(false);
					testing3.setVisible(false);
					test2.setVisible(false);
					goAhead = false;
					move.repaint();
					testing.setBounds(66, 315, length, 20);//test2.setBounds(1015,28,330,250);}

					String ability = "";
					if(game.getCurrentChampion().getAbilities().get(0) instanceof DamagingAbility)
						ability = "Damaging Amount: "+((((DamagingAbility) game.getCurrentChampion().getAbilities().get(0)).getDamageAmount())) + "";
					else if (game.getCurrentChampion().getAbilities().get(0) instanceof HealingAbility) 
						ability = "Healing Amount: "+((((HealingAbility) game.getCurrentChampion().getAbilities().get(0)).getHealAmount())) + "";
					else { if (game.getCurrentChampion().getAbilities().get(0) instanceof CrowdControlAbility) { 
						ability = "<html>Effect: "+((((CrowdControlAbility) game.getCurrentChampion().getAbilities().get(0)).getEffect().getName())) + "<br> Duration: "+
								((((CrowdControlAbility) game.getCurrentChampion().getAbilities().get(0)).getEffect().getDuration()))+"</html>";
//						if (counter%2==0) { test2.setBounds(1015,28,330,270);}
//						else {test2.setBounds(15,28,330,270);}
						}
						}
					test2.setText("<html><h1>"
							+"<div style='text-indent:15px; font-size:24; margin-top :-40;'>"
							+game.getCurrentChampion().getAbilities().get(0).getName()
							+"</div></h1><h2><div style='font-size:18;margin-left:35;margin-top :-32;'>"+"Type: "
							+game.getCurrentChampion().getAbilities().get(0).getClass().getSimpleName()+"<br>Area Of Effect: "
							+game.getCurrentChampion().getAbilities().get(0).getCastArea()+"<br>Cast Range: "
							+game.getCurrentChampion().getAbilities().get(0).getCastRange()+"<br>Mana Cost: "
							+game.getCurrentChampion().getAbilities().get(0).getManaCost()+"<br>Attack Points Required: "
							+game.getCurrentChampion().getAbilities().get(0).getRequiredActionPoints()+"<br>Cool Down: "
							+game.getCurrentChampion().getAbilities().get(0).getCurrentCooldown()+"<br>" + ability		
							+"</div></h2><html>");
					testing.setVisible(true);
					test2.setVisible(true);
					goAhead = true;
					move.repaint();
				}
				else if (e.getX()<=44+length2 && e.getX()>=44 && e.getY()>=280 && e.getY()<300){
					testing.setVisible(false);
					testing4.setVisible(false);
					testing3.setVisible(false);
					test2.setVisible(false);
					goAhead=false;
					move.repaint();
					testing2.setBounds(66, 335, length2, 20);
//					else {testing2.setBounds(1100, 284, length2, 20);test2.setBounds(15,28,330,250);}
					String ability = "";
					if(game.getCurrentChampion().getAbilities().get(1) instanceof DamagingAbility)
						ability = "Damaging Amount: "+((((DamagingAbility) game.getCurrentChampion().getAbilities().get(1)).getDamageAmount())) + "";
					else if (game.getCurrentChampion().getAbilities().get(1) instanceof HealingAbility) 
						ability = "Healing Amount: "+((((HealingAbility) game.getCurrentChampion().getAbilities().get(1)).getHealAmount())) + "";
					else { if (game.getCurrentChampion().getAbilities().get(1) instanceof CrowdControlAbility) { 
						ability = "<html>Effect: "+((((CrowdControlAbility) game.getCurrentChampion().getAbilities().get(1)).getEffect().getName())) + "<br> Duration: "+
								((((CrowdControlAbility) game.getCurrentChampion().getAbilities().get(1)).getEffect().getDuration()))+"</html>";
//						if (counter%2==0) { test2.setBounds(1015,28,330,270);}
//						else {test2.setBounds(15,28,330,270);}
						}}
					test2.setText("<html><h1>"
							+"<div style='text-indent:15px; font-size:24; margin-top :-40;'>"
							+game.getCurrentChampion().getAbilities().get(1).getName()
							+"</div></h1><h2><div style='font-size:18;margin-left:35;margin-top :-32;'>"+"Type: "
							+game.getCurrentChampion().getAbilities().get(1).getClass().getSimpleName()+"<br>Area Of Effect: "
							+game.getCurrentChampion().getAbilities().get(1).getCastArea()+"<br>Cast Range: "
							+game.getCurrentChampion().getAbilities().get(1).getCastRange()+"<br>Mana Cost: "
							+game.getCurrentChampion().getAbilities().get(1).getManaCost()+"<br>Attack Points Required: "
							+game.getCurrentChampion().getAbilities().get(1).getRequiredActionPoints()+"<br>Cool Down: "
							+game.getCurrentChampion().getAbilities().get(1).getCurrentCooldown()+"<br>" + ability		
							+"</div></h2><html>");
					testing2.setVisible(true);
					test2.setVisible(true);
					goAhead=true;
					move.repaint();
				}
				else if (e.getX()<=44+length3 && e.getX()>=44 && e.getY()>=300 && e.getY()<320){
					testing.setVisible(false);
					testing2.setVisible(false);
					testing4.setVisible(false);
					test2.setVisible(false);
					goAhead=false;
					move.repaint();
					testing3.setBounds(66, 355, length3, 20);
//					else {testing3.setBounds(1100, 304, length3, 20);test2.setBounds(15,28,330,250);}
					String ability = "";
					if(game.getCurrentChampion().getAbilities().get(2) instanceof DamagingAbility)
						ability = "Damaging Amount: "+((((DamagingAbility) game.getCurrentChampion().getAbilities().get(2)).getDamageAmount())) + "";
					else if (game.getCurrentChampion().getAbilities().get(2) instanceof HealingAbility) 
						ability = "Healing Amount: "+((((HealingAbility) game.getCurrentChampion().getAbilities().get(2)).getHealAmount())) + "";
					else { if (game.getCurrentChampion().getAbilities().get(2) instanceof CrowdControlAbility) { 
						ability = "<html>Effect: "+((((CrowdControlAbility) game.getCurrentChampion().getAbilities().get(2)).getEffect().getName())) + "<br> Duration: "+
								((((CrowdControlAbility) game.getCurrentChampion().getAbilities().get(2)).getEffect().getDuration()))+"</html>";
//						if (counter%2==0) { test2.setBounds(1015,28,330,270);}
//						else {test2.setBounds(15,28,330,270);}
						}}
					test2.setText("<html><h1>"
							+"<div style='text-indent:15px; font-size:24; margin-top :-40;'>"
							+game.getCurrentChampion().getAbilities().get(2).getName()
							+"</div></h1><h2><div style='font-size:18;margin-left:35;margin-top :-32;'>"+"Type: "
							+game.getCurrentChampion().getAbilities().get(2).getClass().getSimpleName()+"<br>Area Of Effect: "
							+game.getCurrentChampion().getAbilities().get(2).getCastArea()+"<br>Cast Range: "
							+game.getCurrentChampion().getAbilities().get(2).getCastRange()+"<br>Mana Cost: "
							+game.getCurrentChampion().getAbilities().get(2).getManaCost()+"<br>Attack Points Required: "
							+game.getCurrentChampion().getAbilities().get(2).getRequiredActionPoints()+"<br>Cool Down: "
							+game.getCurrentChampion().getAbilities().get(2).getCurrentCooldown()+"<br>" + ability		
							+"</div></h2><html>");
					testing3.setVisible(true);
					test2.setVisible(true);
					goAhead=true;
					move.repaint();
				}
				else if ((e.getX()<=44+length4 && e.getX()>=44 && e.getY()>=320 && e.getY()<340)&&game.getCurrentChampion().getAbilities().size()==4){
					testing.setVisible(false);
					testing2.setVisible(false);
					testing3.setVisible(false);
					test2.setVisible(false);
					goAhead=false;
					move.repaint();
					testing4.setBounds(66, 355, length4, 20);
//					else {testing3.setBounds(1100, 304, length3, 20);test2.setBounds(15,28,330,250);}
					String ability = "";
					if(game.getCurrentChampion().getAbilities().get(2) instanceof DamagingAbility)
						ability = "Damaging Amount: "+((((DamagingAbility) game.getCurrentChampion().getAbilities().get(3)).getDamageAmount())) + "";
					else if (game.getCurrentChampion().getAbilities().get(2) instanceof HealingAbility) 
						ability = "Healing Amount: "+((((HealingAbility) game.getCurrentChampion().getAbilities().get(3)).getHealAmount())) + "";
					else { if (game.getCurrentChampion().getAbilities().get(0) instanceof CrowdControlAbility) { 
						ability = "<html>Effect: "+((((CrowdControlAbility) game.getCurrentChampion().getAbilities().get(3)).getEffect().getName())) + "<br> Duration: "+
								((((CrowdControlAbility) game.getCurrentChampion().getAbilities().get(3)).getEffect().getDuration()))+"</html>";
//						if (counter%2==0) { test2.setBounds(1015,28,330,270);}
//						else {test2.setBounds(15,28,330,270);}
					}}
					test2.setText("<html><h1>"
							+"<div style='text-indent:15px; font-size:24; margin-top :-40;'>"
							+game.getCurrentChampion().getAbilities().get(3).getName()
							+"</div></h1><h2><div style='font-size:18;margin-left:35;margin-top :-32;'>"+"Type: "
							+game.getCurrentChampion().getAbilities().get(3).getClass().getSimpleName()+"<br>Area Of Effect: "
							+game.getCurrentChampion().getAbilities().get(3).getCastArea()+"<br>Cast Range: "
							+game.getCurrentChampion().getAbilities().get(3).getCastRange()+"<br>Mana Cost: "
							+game.getCurrentChampion().getAbilities().get(3).getManaCost()+"<br>Attack Points Required: "
							+game.getCurrentChampion().getAbilities().get(3).getRequiredActionPoints()+"<br>Cool Down: "
							+game.getCurrentChampion().getAbilities().get(3).getCurrentCooldown()+"<br>" + ability		
							+"</div></h2><html>");
					testing4.setVisible(true);
					test2.setVisible(true);
					goAhead=true;
					move.repaint();
				}
				else {
					testing.setVisible(false);
					testing2.setVisible(false);
					testing3.setVisible(false);
					testing4.setVisible(false);
					test2.setVisible(false);
					goAhead=false
							;
					move.repaint();
				}
		//}
			}
        });
        
		
		//leader.setText("");
		//leader.setBackground(Color.black);
		//Color color = new Color(5,5,5,50);
		//leader.setOpaque(true);
		//leader.setBackground(new Color(5,5,5,50));
		
		
		// Listen for mouse over alive champ
		panel.addMouseMotionListener(
			new MouseMotionListener() {
				@Override
				public void mouseDragged(MouseEvent e) {}

				@Override
				public void mouseMoved(MouseEvent e) {
					leader.setText(putText(control.getCurrentGame().getCurrentChampion()));
				}
					
			}
		);
		
		label =  new JLabel(game.getFirstPlayer().getName()+"'s Info:");
		label.setBounds(1100, 60, 350, 50);
		label.setForeground(new Color(0xf8df82).brighter());
		//label.setFont(plain);
		
		label2 =  new JLabel(game.getSecondPlayer().getName()+"'s Info:");
		label2.setBounds(1100, 160, 350, 50);
		label2.setForeground(new Color(0xf8df82).brighter());
		
		//label2.setFont(plain);
		
		drawBorder(next);
		PlaceErrorMessages();
		putButton();
		
		panel.add(move,Integer.valueOf(0));
		panel.add(label,Integer.valueOf(1));
		panel.add(label2,Integer.valueOf(1));
		panel.add(leader,Integer.valueOf(1));
		
	}
	public void putButton() {
		//panel4.removeAll();
		panel4.setBounds(0, 0, 1366, 768);
		panel.add(panel4,Integer.valueOf(3));
		
		attack = new JButton("Attack");
		attack.setBounds(1100, 350, 200, 50);
		attack.setFocusPainted(false);
		attack.setFocusPainted(false);
		attack.setOpaque(true);
        attack.setBackground(new Color(0x20390e));
        attack.setForeground(new Color(0xb6b884));
        attack.setBorder(BorderFactory.createLineBorder(Color.black,5));
        attack.addActionListener(new ArrowPanelListener());
        attack.setName("open|attack");
		//attack.setBorder(BorderFactory.createDashedBorder(new Color(0x440931), 4, 10, 5, true));
		panel4.add(attack);
		
        moving = new JButton("Move");
        moving.setBounds(1100, 410, 200, 50);
        moving.setOpaque(true);
        moving.setFocusPainted(false);
        moving.setBackground(new Color(0x20390e));
        moving.setForeground(new Color(0xb6b884));
        moving.addActionListener(new ArrowPanelListener());
        moving.setName("open|move");
        //moving.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED,Color.BLACK,Color.white));
        moving.setBorder(BorderFactory.createLineBorder(Color.black,5));
        panel4.add(moving);
        
        castAbility = new JButton("Cast Ability");
        castAbility.setBounds(1100, 470, 200, 50);
        //castAbility.setContentAreaFilled(false);
        castAbility.setOpaque(true);
        castAbility.setFocusPainted(false);
        castAbility.setBackground(new Color(0x20390e));
        castAbility.setForeground(new Color(0xb6b884));
        castAbility.setBorder(BorderFactory.createLineBorder(Color.black,5));
        castAbility.addActionListener(new AbilityPanelListener());
        castAbility.setName("open");
        //castAbility.setBorder(BorderFactory.createDashedBorder(/*new Color(0x0d0f26)*/ Color.black, 4, 10, 5, true));
        panel4.add(castAbility);
        
        useLeaderAbility = new JButton("Leader Ability");
        useLeaderAbility.setBounds(1100, 530, 200, 50);
        useLeaderAbility.setOpaque(true);
        useLeaderAbility.setFocusPainted(false);
        useLeaderAbility.setBackground(new Color(0x20390e));
        useLeaderAbility.setForeground(new Color(0xb6b884));
        useLeaderAbility.setBorder(BorderFactory.createLineBorder(Color.black,5));
        useLeaderAbility.addActionListener(control.new LeaderAbilityListener());
        //useLeaderAbility.setBorder(BorderFactory.createDashedBorder(new Color(0x440931), 4, 10, 5, true));
        if(game.getCurrentChampion() == control.getPlayer1().getLeader() || game.getCurrentChampion() == control.getPlayer2().getLeader()) {
        	panel4.add(useLeaderAbility);
        	haslead=true;
        	if(game.getCurrentChampion() == control.getPlayer1().getLeader()&& game.isFirstLeaderAbilityUsed()) {
        		useLeaderAbility.setEnabled(false);
                useLeaderAbility.setBackground(new Color(0xb6b884));
                useLeaderAbility.setForeground(Color.black);}
        	else if(game.getCurrentChampion() == control.getPlayer2().getLeader()&& game.isSecondLeaderAbilityUsed()) {
        		useLeaderAbility.setEnabled(false);
                useLeaderAbility.setBackground(new Color(0xb6b884));
                useLeaderAbility.setForeground(Color.black);}
        	else {useLeaderAbility.setEnabled(true);
        		useLeaderAbility.setBackground(new Color(0x20390e));
        		useLeaderAbility.setForeground(new Color(0xb6b884));}
        }else haslead=false;
        
        endTurn = new JButton("End Turn");
        endTurn.setBounds(1100, 590, 200, 50);
        endTurn.setOpaque(true);
        endTurn.setFocusPainted(false);
        endTurn.setBackground(new Color(0x20390e));
        endTurn.setForeground(new Color(0xb6b884));
        endTurn.setBorder(BorderFactory.createLineBorder(Color.black,5));
        endTurn.addActionListener(new EndTurnButtonListener());
        panel4.add(endTurn);
	}
	// Button listener that opens/closes the abilities panel
    private class AbilityPanelListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String buttonID = ((JButton) e.getSource()).getName().toLowerCase();

            if (buttonID == "close") {
                panel.remove(panel5);
                panel.add(panel4,Integer.valueOf(3));
                panel.repaint();
                panel.revalidate();
            } else {
            	
            	panel.remove(panel4);
                generateAbilityList();
                panel.add(panel5,Integer.valueOf(3));
                panel.revalidate();
            }
        }
    }
    public void generateAbilityList () {
    	
    	panel5.setBounds(0, 0, 1366, 768);
    	panel5.removeAll();
		//panel.add(panel4,Integer.valueOf(3));
		
        Champion c = control.getCurrentGame().getCurrentChampion();
       // JPanel buttons = new JPanel(new GridLayout(c.getAbilities().size(), 1));

        // Iterate over abilities adding buttons to panel
        int i = 0;
        int m=0;
        if (c.getAbilities().size()==4) {
        	 m = 350;}
        else if (c.getAbilities().size()==3) {
        	 m = 350;}
        for (Ability a : c.getAbilities()) {
            JButton btn = new JButton(a.getName());

            switch (a.getCastArea()) {
                // Cast ability
                case SELFTARGET:
                case SURROUND:
                case TEAMTARGET: {
                    btn.setName("cast|" + i);
                    btn.addActionListener(control.new CastListener());
                    btn.addActionListener(new boardUpdateListener());
                    break;
                }
                // Set controller casting ability flag to true
                case SINGLETARGET:{
                    btn.setName("cast|" + i);
                    btn.addActionListener(control.new CastListener());
                    btn.setActionCommand("open");
                    btn.addActionListener(new SingleTargetPanelListener());
                    break;
                }
                // Display arrows to select direction
                case DIRECTIONAL:{
                    btn.setName("open|cast|" + i);
                    btn.addActionListener(new ArrowPanelListener());
                    break;
                }
                default:
                    break;
            }
            btn.setBounds(1100, m, 200, 50);
            btn.setText(a.getName());
            btn.setFocusPainted(false);
            btn.setOpaque(true);
            btn.setBackground(new Color(0x20390e));
            btn.setForeground(new Color(0xb6b884));
            btn.setBorder(BorderFactory.createLineBorder(Color.black,5));
            panel5.add(btn);
            m+=60;
            i++;
        }

        JButton backbtn = new JButton("Back");
        backbtn.setBounds(1100, m+60, 200, 50);
        backbtn.setOpaque(true);
        backbtn.setBackground(new Color(0x20390e));
        backbtn.setForeground(new Color(0xb6b884));
        backbtn.setBorder(BorderFactory.createLineBorder(Color.black,5));
        backbtn.addActionListener(new AbilityPanelListener());
        backbtn.setName("close");
        panel5.add(backbtn);

    }
    
    public void printWinner(Player p){
		control.stopAudioTheme();
		try {
			control.playAudio("winner.wav");
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        // JLayeredPane win = new JLayeredPane();
        panel.remove(panel4);
        panel.remove(arrowPanel);
        panel.remove(panel5);

        panelWin = new JLayeredPane();
		panelWin.setOpaque(true);
		panelWin.setBounds(433, 234, 500, 300);
		panelWin.setBackground(Color.black);
		panelWin.setBorder(BorderFactory.createLineBorder(new Color(0xf30c0c),5));
		
		JLabel win = new JLabel("Winner!!!");
		win.setForeground(new Color(0xf30c0c));
		//win.setText("<html><div style=' font-size:30;'>"+"WINNER:</div></html>");
		win.setFont(font5Big);
		win.setBounds(135,40,250, 60);
		win.setHorizontalAlignment(0);
		win.setVisible(true);
		
		JLabel winner = new JLabel(p.getName()+"!");
		winner.setForeground(new Color(0x289baf));
		//winner.setText("<html><div style='font-size:20;'>Joey!</div></html>");
		winner.setBounds(125,90,250, 60);
		winner.setHorizontalAlignment(0);
		winner.setFont(font5);
		winner.setVisible(true);
		
		JButton quit = new JButton();
		quit.addActionListener(control.new QuitListener());
		quit.setFocusPainted(false);
		quit.setOpaque(true);
		quit.setForeground(new Color(/*0x8a8a8a*/0xeeff00));
		quit.setBackground(Color.black);
		quit.setText("Quit!");
		quit.setFont(font5Small);
		quit.setBounds(40,210,130,40);
		//quit.setBorder(BorderFactory.createLineBorder(new Color(/*0x8a8a8a*/0xffdc00),3));
		quit.setBorder(BorderFactory.createLineBorder(new Color(/*0x8a8a8a*/0xeeff00),3));
		
		JButton begin = new JButton();
		begin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					new GameController();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		begin.setOpaque(true);
		begin.setForeground(Color.green);
		begin.setBackground(Color.black);
		//begin.setBackground(new Color(0x48142a));
		begin.setFocusPainted(false);
		begin.setFont(font5Small);
		begin.setText("New Game!");
		begin.setBorder(BorderFactory.createLineBorder(Color.green,3));
		begin.setBounds(330,210,130,40);
		
        panelWin.add(win,Integer.valueOf(6));
        panelWin.add(winner,Integer.valueOf(6));
        panelWin.add(quit,Integer.valueOf(7));
        panelWin.add(begin,Integer.valueOf(7));
        panel.add(panelWin,Integer.valueOf(7));
        
    }
    
    private void PlaceErrorMessages(){
        errorPanel = new JLayeredPane();
        errorPanel.setBounds(333,395,700,50);
        errorPanel.setOpaque(true);
        errorPanel.setBackground(Color.black);
        errorPanel.setBorder(BorderFactory.createLineBorder(new Color(0xf30c0c),5));
        errorPanel.setVisible(false);

        errorLabel = new JLabel();
        errorLabel.setBounds(100, 0, 500, 50);
        errorLabel.setHorizontalAlignment(0);
        errorLabel.setForeground(/*new Color(0x20390e)*/new Color(0xf30c0c));

        errorPanel.add(errorLabel);
        panel.add(errorPanel, Integer.valueOf(6));
    }
    
 // Button listener that opens/closes D-Pad panel for directional actions
    /* ButtonID = (open / close)|(move/cast/attack)|(abilityIndex) */
    private class ArrowPanelListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] buttonID = ((JButton) e.getSource()).getName().split("\\|");

            
            if (buttonID[0].equals("close")) {
				try {
					control.playAudio("button_click.wav");
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
                System.out.println("Closing arrow panel");
                panel.remove(arrowPanel);
                panel.add(panel4, Integer.valueOf(3));
                panel.repaint();
                panel.revalidate();
            } else {
				try {
					control.playAudio("button_click.wav");
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
                panel.remove(panel4);
                arrowPanel.setBounds(1100,500,225,180);
                arrowPanel.removeAll();
                System.out.println("Making new arrow panel of " + buttonID[1]);

                switch (buttonID[1].toLowerCase()) {
                    case "move": {
                        ArrowButtons arrows = new ArrowButtons(control, ArrowButtonTypes.MOVE);
                        arrows.placeButtons(arrowPanel,22,0);
                        arrows.addBackListener(new ArrowPanelListener());
                        //arrows.addListener(new boardUpdateListener());
                        break;
                    }
                    case "cast": {
                    	panel.remove(panel5);
                        ArrowButtons arrows = new ArrowButtons(control, ArrowButtonTypes.CAST_ABILITY, Integer.parseInt(buttonID[2]));
                        arrows.placeButtons(arrowPanel,22,0);
                        arrows.addBackListener(new ArrowPanelListener());
                        //arrows.addListener(new boardUpdateListener());
                        break;
                    }
                    case "attack": {
                        ArrowButtons arrows = new ArrowButtons(control, ArrowButtonTypes.ATTACK);
                        arrows.placeButtons(arrowPanel,22,0);
                        arrows.addBackListener(new ArrowPanelListener());
                        //arrows.addListener(new boardUpdateListener());
                        break;
                    }
                }

                JButton back = new JButton("Back");
                back.addActionListener(new ArrowPanelListener());
                back.setName("close");

                arrowPanel.add(back);

                panel.add(arrowPanel, Integer.valueOf(4));
                panel.revalidate();
            }
        }
    }

    
    private class SingleTargetPanelListener implements ActionListener {
    	
    	
        @Override
        public void actionPerformed(ActionEvent e) {
			try {
				control.playAudio("button_click.wav");
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
        	panel6.setBounds(0, 0, 1366, 768);
        	//panel6.removeAll();
            String action = ((JButton) e.getSource()).getActionCommand();
            if (action.equals("close")) {
                panel.remove(panel6);
                panel.add(panel5, Integer.valueOf(3));
                panel.repaint();
                panel.revalidate();
                control.cancelSingleTarget();
            } else if (action.equals("open")) {
                //panel.remove(panel4);
                panel.remove(panel5);
                panel6.removeAll();

                JLabel infoText = new JLabel("Select target on the board!");
                infoText.setBounds(1100, 430, 200, 50);
                infoText.setHorizontalAlignment(0);
                infoText.setOpaque(true);
                infoText.setBackground(new Color(0x20390e));
                infoText.setForeground(new Color(0xb6b884));
                infoText.setBorder(BorderFactory.createLineBorder(Color.black,5));
                
                JButton backButton = new JButton("Back");
                backButton.addActionListener(new SingleTargetPanelListener());
                backButton.setBounds(1100, 500, 200, 50);
                backButton.setOpaque(true);
                backButton.setBackground(new Color(0x20390e));
                backButton.setForeground(new Color(0xb6b884));
                backButton.setBorder(BorderFactory.createLineBorder(Color.black,5));
                backButton.setActionCommand("close");

                panel6.add(infoText);
                panel6.add(backButton);
                panel.add(panel6, Integer.valueOf(3));
                panel.repaint();
                panel.revalidate();
            }
            
        }
        
    }
	private class EndTurnButtonListener implements ActionListener{
        
            @Override
            public void actionPerformed(ActionEvent e) {
//            	boolean stunned =  false;
//            	boolean enter = false;
//            	PriorityQueue temp2 = new PriorityQueue(6);
//                game.endTurn();
//                turnOrder = game.getTurnOrder();
//                int f = 0;
//                if (turnOrder.size()==1) {
//                	while (!turnOrder.isEmpty()) {
//                		temp.insert(game.getTurnOrder().remove());
//                	}
//                	game.prepareChampionTurns();
//                	
//                	while (!game.getTurnOrder().isEmpty()) {
//            		  if(!enter) {
//            			for(Effect effect: ((Champion) game.getTurnOrder().peekMin()).getAppliedEffects()) {
//            				if(effect.getName().equals("Stun")) {
//            					stunned = true;
//            					break;
//            				}
//            			}}
//            			if (!stunned) { next = (Champion) game.getTurnOrder().peekMin();
//            			enter=true;}
//            			temp2.insert(game.getTurnOrder().remove());
//            		}
//                	
//                	//next = first;
//                }else {
//        		while (!turnOrder.isEmpty()) {
//        			if(f==1 || enter) { 
//        				enter = true;
//        				for(Effect effect: ((Champion) turnOrder.peekMin()).getAppliedEffects()) {
//        				if(effect.getName().equals("Stun")) {
//        					stunned = true;
//        					break;
//        				}
//        			}
//        			if (!stunned) {next = (Champion) turnOrder.peekMin();
//        			enter = false;}
//        		}
//        			temp.insert(turnOrder.remove());
//        			f++;
//        		}
//        		while (!temp.isEmpty()) {
//        			//if(f==1) next = (Champion) turnOrder.peekMin();
//        			turnOrder.insert(temp.remove());
//        		}}
            	 drawBoard(control.getCurrentGame().getBoard());
                 leader.setText(putText(control.getCurrentGame().getCurrentChampion()));
                game.endTurn();
                turnOrder = game.getTurnOrder();
                int f = 0;
                if (turnOrder.size()==1) {
                	next = first;
                }else {
        		while (!turnOrder.isEmpty()) {
        			if(f==1) next = (Champion) turnOrder.peekMin();
        			temp.insert(turnOrder.remove());
        			f++;
        		}
        		while (!temp.isEmpty()) {
        			//if(f==1) next = (Champion) turnOrder.peekMin();
        			turnOrder.insert(temp.remove());
        		}}
                Champion curr = (Champion) turnOrder.peekMin();
                leader.setText(putText(control.getCurrentGame().getCurrentChampion()));
                drawBorder(next);
                //displayQueue(queue, info);
                //displayAbilitiesAndEffects(curr, abilities1Info, abilities2Info, effectsInfo);
                //abilities1Info.setText("");
                //abilities2Info.setText("");
                //effectsInfo.setText("");
                if(game.getCurrentChampion() == control.getPlayer1().getLeader() || game.getCurrentChampion() == control.getPlayer2().getLeader()) {
                	if(haslead==false) {
                	panel4.add(useLeaderAbility);
                	haslead=true;}
                	if(game.getCurrentChampion() == control.getPlayer1().getLeader()&& game.isFirstLeaderAbilityUsed()) {
                		useLeaderAbility.setEnabled(false);
                        useLeaderAbility.setBackground(new Color(0xb6b884));
                        useLeaderAbility.setForeground(Color.black);}
                	else if(game.getCurrentChampion() == control.getPlayer2().getLeader()&& game.isSecondLeaderAbilityUsed()) {
                		useLeaderAbility.setEnabled(false);
                        useLeaderAbility.setBackground(new Color(0xb6b884));
                        useLeaderAbility.setForeground(Color.black);}
                	else {useLeaderAbility.setEnabled(true);
                		useLeaderAbility.setBackground(new Color(0x20390e));
                		useLeaderAbility.setForeground(new Color(0xb6b884));}
                }
                else if (haslead==true) {panel4.remove(useLeaderAbility);
                haslead=false;}
                
            }
        
    }
	public String putText(Champion curr) {
		transparent2=360;
		String effects = "";
		for (int i = 0; i < curr.getAppliedEffects().size();i++) {
			effects += "<html>"+curr.getAppliedEffects().get(i).getName()+": "+curr.getAppliedEffects().get(i).getDuration()+"<br></html>";
			transparent2+=10;
		}
		
		String isLeader = "";
		if (curr==control.getCurrentGame().getFirstPlayer().getLeader()){
			if (game.isFirstLeaderAbilityUsed()) {
			isLeader = "Leader: Used";}
			else isLeader=  "Leader: On";
			transparent = 60;
			transparent2 +=20;
		}
		else if (curr==control.getCurrentGame().getSecondPlayer().getLeader()){
			if (game.isSecondLeaderAbilityUsed()) {
				isLeader = "Leader: Used";}
			else isLeader=  "Leader: On";
			transparent = 60;
			transparent2 +=20;
		}else {
			transparent2 = transparent2;
			transparent = 80;
		}
		move.repaint();
		String text="";
		if (curr.getAbilities().size()==4) {
		testing4bool =true;
		 text=
				"<html><h1>"
						+"<div style='text-indent:15px; font-size:26; margin-top :-40;'>"
						+isLeader+"</div><div style='text-indent:15px;margin-left:8; font-size:24; margin-top :0;'>"+curr.getName()
						+"</div></h1><h2><div style='font-size:18;margin-left:35;margin-top :-32;'>"+"Type: "
						+curr.getClass().getSimpleName()+"<br>Mana: "
						+curr.getMana()+"<br>Health Points: "
						+curr.getCurrentHP()+"/"+curr.getMaxHP()+"<br>Action Points: "
						+curr.getCurrentActionPoints()+"<br>Attack Points: "
						+curr.getAttackDamage()+"<br> Attack Range: "
						+curr.getAttackRange()+"<br>Abilities:"		
						+"</div><div style='font-size:14; margin-left:50; margin-top:8;'>"
						+curr.getAbilities().get(0).getName()+"<br>"
						+curr.getAbilities().get(1).getName()+"<br>"
						+curr.getAbilities().get(2).getName()+"<br>"
						+curr.getAbilities().get(3).getName()
						+"</div><div style='font-size:18;margin-left:35;margin-top :8;'>Applied Effects: "+curr.getAppliedEffects().size()
						+"</div><div style='font-size:14; margin-left:50; margin-top:14;'>" + effects
						+"</div></h2><html>";}
		else text=
				"<html><h1>"
						+"<div style='text-indent:15px; font-size:26; margin-top :-40;'>"
						+isLeader+"</div><div style='text-indent:15px;margin-left:8; font-size:24; margin-top :0;'>"+curr.getName()
						+"</div></h1><h2><div style='font-size:18;margin-left:35;margin-top :-32;'>"+"Type: "
						+curr.getClass().getSimpleName()+"<br>Mana: "
						+curr.getMana()+"<br>Health Points: "
						+curr.getCurrentHP()+"/"+curr.getMaxHP()+"<br>Action Points: "
						+curr.getCurrentActionPoints()+"<br>Attack Points: "
						+curr.getAttackDamage()+"<br> Attack Range: "
						+curr.getAttackRange()+"<br>Abilities:"		
						+"</div><div style='font-size:14; margin-left:50; margin-top:8;'>"
						+curr.getAbilities().get(0).getName()+"<br>"
						+curr.getAbilities().get(1).getName()+"<br>"
						+curr.getAbilities().get(2).getName()
						+"</div><div style='font-size:18;margin-left:35;margin-top :8;'>Applied Effects: "+curr.getAppliedEffects().size()
						+"</div><div style='font-size:14; margin-left:50; margin-top:14;'>" + effects
						+"</div></h2><html>";
		return text;
	}
	
	public class boardUpdateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
			try {
				control.playAudio("button_click.wav");
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				e1.printStackTrace();
			}
            new java.util.Timer().schedule(new java.util.TimerTask() {
                @Override
                public void run() {
                	System.out.println("Called..");
                    drawBoard(control.getCurrentGame().getBoard());
                    leader.setText(putText(control.getCurrentGame().getCurrentChampion()));
                    panel.revalidate();
                    panel.repaint();
                }
            }, 50);
        }
        
    }
	
	public void drawBorder(Champion next) {
		panel3.removeAll();
		panel3.setBounds(0, 0, 1366, 768);
		panel.add(panel3,Integer.valueOf(3));
		int j =1100;
        for (int ind = 0; ind < 6; ind++) {
            j = (ind==3? 1100 : j);
            Champion realChampion;
            JLabel previous = new JLabel();
            if(ind <= 2){
            	if(control.getCurrentGame().getFirstPlayer().getTeam().size()>ind) {
                ImageIcon btn = new ImageIcon("assets/characters/64/" + control.getCurrentGame().getFirstPlayer().getTeam().get(ind).getName() + ".png");
                champion = new JLabel(btn);
                champion.setName(control.getCurrentGame().getFirstPlayer().getTeam().get(ind).getName());
                if (champion.getName()== game.getCurrentChampion().getName()) champion.setBorder(BorderFactory.createLineBorder(Color.green,5));
                if (champion.getName()== next.getName()) champion.setBorder(BorderFactory.createLineBorder(Color.yellow,5));
                //realChampion = control.getCurrentGame().getFirstPlayer().getTeam().get(ind);
                panel3.add(champion,Integer.valueOf(0));
            }}
            else if(control.getCurrentGame().getSecondPlayer().getTeam().size()>ind-3){
                ImageIcon btn = new ImageIcon("assets/characters/64/" + control.getCurrentGame().getSecondPlayer().getTeam().get(ind-3).getName() + ".png");
                champion = new JLabel(btn);
                champion.setName(control.getCurrentGame().getSecondPlayer().getTeam().get(ind-3).getName());
                System.out.println(champion.getName());
                if (champion.getName()== next.getName()) champion.setBorder(BorderFactory.createLineBorder(Color.yellow,5));
                if (champion.getName().equals(game.getCurrentChampion().getName())) champion.setBorder(BorderFactory.createLineBorder(Color.green,5));
                //realChampion = control.getCurrentGame().getSecondPlayer().getTeam().get(ind-3);
                panel3.add(champion,Integer.valueOf(0));
            }


            int i = (ind<3? 100 : 200);
            champion.setBounds(j,i,64,64);
            champion.addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent e) {}

                @Override
                public void mouseMoved(MouseEvent e) {
                	for(int i = 0; i < control.getCurrentGame().getFirstPlayer().getTeam().size(); i++){
						if (control.getCurrentGame().getFirstPlayer().getTeam().get(i).getName().equals(((JLabel) (e.getSource())).getName()))
							leader.setText(putText(control.getCurrentGame().getFirstPlayer().getTeam().get(i)));
							
                	}
                	
                	for(int i = 0; i < control.getCurrentGame().getSecondPlayer().getTeam().size(); i++){
						if (control.getCurrentGame().getSecondPlayer().getTeam().get(i).getName().equals(((JLabel) (e.getSource())).getName())){
							leader.setText(putText(control.getCurrentGame().getSecondPlayer().getTeam().get(i)));
							
						}
                	}	
                }
            });
//            if(!previous.equals(champion))
//           
//            previous = champion;
            j+=75;
        }
	}
	public void drawBoard(Object[][] board) {
		barIndex = 0;
		panel2.removeAll();
		panel2.setLayout(new GridLayout(board.length, board[0].length, 5, 5));
		panel2.setBounds(333, 16, 700, 700);
//		
//		
//	
//		// Iterate over game board, rows and columns, generating buttons
		for (int i = board.length - 1; i >= 0; i--) {
//			
			for (int j = 0; j < board[i].length; j++) {
//				
				JButton button = new JButton();
				button.setOpaque(false);
				button.setContentAreaFilled(false);
				button.setName("board|" + i + "|" + j);
				button.addActionListener(control.new BoardListener());
				button.addActionListener(new boardUpdateListener());
				ImageIcon cover = new ImageIcon("assets/characters/128/Cover_grass.png");
//				//button.setBorderPainted(false);
//				if (i==0 && j ==0) {
//					JButton button1 = new JButton();
//					button1.setOpaque(false);
//					button1.setContentAreaFilled(false);
//					ImageIcon rider = new ImageIcon("assets/characters/128/Untitled18_20220607134512.png");
//					button1.setIcon(rider);
//					panel2.add(button1);
//				}
				 if (board[i][j] == null) {
					panel2.add(button);
				}
				
				else if (board[i][j] instanceof Cover) {
					button.setText("Cover");
					// add cover to board
                    if (firstBarDraw) {
                        bar = new JProgressBar(-((Cover) board[i][j]).getCurrentHP(),0);
                        coverBars.add(bar);
                    } 
                    coverBars.get(barIndex).setString(((Cover) (board[i][j])).getCurrentHP()+"");
                    coverBars.get(barIndex).setValue(-((Cover) (board[i][j])).getCurrentHP());
                    coverBars.get(barIndex).setBackground(Color.green);
                    //bar.setOpaque(true);
                    coverBars.get(barIndex).setStringPainted(true);
                    coverBars.get(barIndex).setVisible(false);
                    coverBars.get(barIndex).setForeground(Color.red);
                    coverBars.get(barIndex).setBorder(BorderFactory.createLineBorder(Color.black, 3));
                    //bar.setBorderPainted(false);
                    coverBars.get(barIndex).setBounds(j*141+333,Math.abs(i - 4)*141+122 , 136,30 );
                    button.setIcon(cover);
                    button.setActionCommand("" + barIndex);
                    button.addMouseListener(new MouseListener() {

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            // TODO Auto-generated method stub
                            
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {
                            // TODO Auto-generated method stub
                            
                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {
                            // TODO Auto-generated method stub
                            
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            // TODO Auto-generated method stub
                            (coverBars.get(Integer.parseInt(((JButton) e.getSource()).getActionCommand()))).setVisible(true);
                            //bar.setVisible(true);
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            // TODO Auto-generated method stub
                            (coverBars.get(Integer.parseInt(((JButton) e.getSource()).getActionCommand()))).setVisible(false);
                        }
                        
                    });
                    panel.add(coverBars.get(barIndex),Integer.valueOf(2));
                    panel2.add(button);   
                    barIndex++;
				}
				else if (board[i][j] instanceof Champion) {
					// add that champion to this place
					
					Champion curr = (Champion) board[i][j];
					ImageIcon champIcon = new ImageIcon("assets/characters/new64/" + curr.getName() + ".png", curr.getName());
					button.setIcon(champIcon);
					button.addMouseListener(new MouseListener() {

						@Override
						public void mouseClicked(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mousePressed(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mouseReleased(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mouseEntered(MouseEvent e) {
							// TODO Auto-generated method stub
							leader.setText(putText(curr));
						}

						@Override
						public void mouseExited(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}
						
					});
					
					panel2.add(button);
				}
			}
		}
		firstBarDraw = false;
//		
		
		
//		
//		final int ICON_WIDTH = 75;
//		final int ROWS = 2;
//
//		// for (int i = 0; i < ROWS; i++) {
//		// 	// Fetch team "i"
//
//		// 	for (int j = 0; /*j < team[i].size*/; j++) {
//		// 		// Fetch champion j from team
//		// 		// Champion curr = team.get(j);
//
//		// 		// Fetch Icon of champion
//		// 		// ImageIcon iconChamp = new ImageIcon("assets/characters/64/" + curr.getName() + ".png");
//		// 	}
//		// }
//

//		

		
		
	}
	
//	public static void main(String[] args) {
//		BoardView view = new BoardView();
//	}
	
	public class Move extends JPanel{
		public void paintComponent(Graphics g) {
			
			
			ImageIcon image = new ImageIcon("assets/background/Board_grass.jpg");
			Image fadl = image.getImage();
			Graphics2D g2 = (Graphics2D) g;
//			GradientPaint gradient = new GradientPaint(0,0,Color.cyan,1366,768,Color.blue);
//			g2.setPaint(gradient);
//			g2.fillRect(0, 0, 1366, 768);
			g2.drawImage(fadl, 0, 0, 	1366, 768, null);
//			if (count == 0) {System.out.println("Changed");
//			g.drawImage(Joey, xPos, yPos, null);}
			g2.drawImage(champImage, xPos, yPos, null);
//			else if (count == 1) {
//				System.out.println("Changed2");
//				g.drawImage(Joey2, xPos, yPos, null);}
//			else {System.out.println("Changed3");g.drawImage(Joey3, xPos, yPos, null);
//			count = -1;}
//			GradientPaint gradient = new GradientPaint(0,0,Color.cyan,1366,768,Color.blue);
//			g2.setPaint(gradient);
//			g2.fillRect(0, 0, 1366, 768);
			
			if(silver) g2.drawImage(captain, xfpPos, yfPos, null);
			//frame.pack();
			g2.setColor(Color.black);
			//g2.drawRoundRect( 465, 570, 175, 70, 10, 10);
			AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 0.5f);
			g2.setComposite(alpha);
			//g2.fillRoundRect(  xPos, yPos, 175, 70, 30, 30);
			g2.fillRect( 30, transparent, 270, transparent2);
			g2.fillRect( 1100, 45, 215, 220);
			if (goAhead) {
			g2.fillRect( 15, 480, 320, 235);}
			
			//g2.fillRoundRect( 726, 600, 175, 70, 30, 30);
		}
	}

	public Image getCaptain() {
		return captain;
	}
	public void setCaptain(Image captain) {
		this.captain = captain;
	}
	public JLabel getChampion() {
		return champion;
	}

	public void setChampion(JLabel champion) {
		this.champion = champion;
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	public JLabel getLabel2() {
		return label2;
	}

	public void setLabel2(JLabel label2) {
		this.label2 = label2;
	}

	public JLabel getLeader() {
		return leader;
	}

	public void setLeader(JLabel leader) {
		this.leader = leader;
	}

	public JLabel getLeader2() {
		return leader2;
	}

	public void setLeader2(JLabel leader2) {
		this.leader2 = leader2;
	}

	public JLayeredPane getPanel() {
		return panel;
	}

	public void setPanel(JLayeredPane panel) {
		this.panel = panel;
	}

//	public JFrame getFrame() {
//		return frame;
//	}
//
//	public void setFrame(JFrame frame) {
//		this.frame = frame;
//	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public ImageIcon getImage() {
		return image;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}

	public Image getJoey() {
		return Joey;
	}

	public void setJoey(Image joey) {
		Joey = joey;
	}

	public ImageIcon getImage2() {
		return image2;
	}

	public void setImage2(ImageIcon image2) {
		this.image2 = image2;
	}

	public Image getJoey2() {
		return Joey2;
	}

	public void setJoey2(Image joey2) {
		Joey2 = joey2;
	}

	public ImageIcon getImage3() {
		return image3;
	}

	public void setImage3(ImageIcon image3) {
		this.image3 = image3;
	}

	public Image getJoey3() {
		return Joey3;
	}

	public void setJoey3(Image joey3) {
		Joey3 = joey3;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	public class Music {
	    Clip clip;
	    AudioInputStream sound;
	    public void setFile(String soundFileName) {
	        try {
	            File file = new File(soundFileName);
	            sound = AudioSystem.getAudioInputStream(file);
	            AudioFormat format = sound.getFormat();
	            DataLine.Info info = new DataLine.Info(Clip.class, format);
	            
	            this.clip = (Clip) AudioSystem.getLine(info);
	            clip.open(sound);
	        } catch (Exception e) {
	        }
	    }
	    public void play() {
	        clip.start();
	    }
	    public void stop() throws IOException {
	        sound.close();
	        clip.close();
	        clip.stop();
	    }
	}
	public JProgressBar getBar() {
		return bar;
	}
	public void setBar(JProgressBar bar) {
		this.bar = bar;
	}
	public AudioInputStream getSound() {
		return sound;
	}
	public void setSound(AudioInputStream sound) {
		this.sound = sound;
	}
	public GameController getControl() {
		return control;
	}
	public void setControl(GameController control) {
		this.control = control;
	}
	public JButton getTesting() {
		return testing;
	}
	public void setTesting(JButton testing) {
		this.testing = testing;
	}
	public JButton getTesting2() {
		return testing2;
	}
	public void setTesting2(JButton testing2) {
		this.testing2 = testing2;
	}
	public JButton getTesting3() {
		return testing3;
	}
	public void setTesting3(JButton testing3) {
		this.testing3 = testing3;
	}
	public JButton getTesting4() {
		return testing4;
	}
	public void setTesting4(JButton testing4) {
		this.testing4 = testing4;
	}
	public JButton getAnimeMove() {
		return animeMove;
	}
	public void setAnimeMove(JButton animeMove) {
		this.animeMove = animeMove;
	}
	public JButton getUp() {
		return up;
	}
	public void setUp(JButton up) {
		this.up = up;
	}
	public JButton getDown() {
		return down;
	}
	public void setDown(JButton down) {
		this.down = down;
	}
	public JButton getLeft() {
		return left;
	}
	public void setLeft(JButton left) {
		this.left = left;
	}
	public JButton getRight() {
		return right;
	}
	public void setRight(JButton right) {
		this.right = right;
	}
	public JButton getPlay() {
		return play;
	}
	public void setPlay(JButton play) {
		this.play = play;
	}
	public JButton getMoving() {
		return moving;
	}
	public void setMoving(JButton moving) {
		this.moving = moving;
	}
	public JButton getAttack() {
		return attack;
	}
	public void setAttack(JButton attack) {
		this.attack = attack;
	}
	public JButton getUseLeaderAbility() {
		return useLeaderAbility;
	}
	public void setUseLeaderAbility(JButton useLeaderAbility) {
		this.useLeaderAbility = useLeaderAbility;
	}
	public JButton getCastAbility() {
		return castAbility;
	}
	public void setCastAbility(JButton castAbility) {
		this.castAbility = castAbility;
	}
	public JButton getEndTurn() {
		return endTurn;
	}
	public void setEndTurn(JButton endTurn) {
		this.endTurn = endTurn;
	}
	public JLabel getTest2() {
		return test2;
	}
	public void setTest2(JLabel test2) {
		this.test2 = test2;
	}
	public Clip getClip() {
		return clip;
	}
	public void setClip(Clip clip) {
		this.clip = clip;
	}
	public Boolean getTesting4bool() {
		return testing4bool;
	}
	public void setTesting4bool(Boolean testing4bool) {
		this.testing4bool = testing4bool;
	}
	public int getBarIndex() {
		return barIndex;
	}
	public void setBarIndex(int barIndex) {
		this.barIndex = barIndex;
	}
	public Boolean getFirstBarDraw() {
		return firstBarDraw;
	}
	public void setFirstBarDraw(Boolean firstBarDraw) {
		this.firstBarDraw = firstBarDraw;
	}
	public int getFinalY() {
		return finalY;
	}
	public void setFinalY(int finalY) {
		this.finalY = finalY;
	}
	public int getFinalX() {
		return finalX;
	}
	public void setFinalX(int finalX) {
		this.finalX = finalX;
	}
	public Image getChampImage() {
		return champImage;
	}
	public void setChampImage(Image champImage) {
		this.champImage = champImage;
	}
	public boolean isGoAhead() {
		return goAhead;
	}
	public void setGoAhead(boolean goAhead) {
		this.goAhead = goAhead;
	}
	public int getTransparent() {
		return transparent;
	}
	public void setTransparent(int transparent) {
		this.transparent = transparent;
	}
	public int getTransparent2() {
		return transparent2;
	}
	public void setTransparent2(int transparent2) {
		this.transparent2 = transparent2;
	}
	public ArrayList<JProgressBar> getCoverBars() {
		return coverBars;
	}
	public void setCoverBars(ArrayList<JProgressBar> coverBars) {
		this.coverBars = coverBars;
	}
	public JLabel getErrorLabel() {
		return errorLabel;
	}
	public void setErrorLabel(JLabel errorLabel) {
		this.errorLabel = errorLabel;
	}
	public JLayeredPane getPanel2() {
		return panel2;
	}
	public void setPanel2(JLayeredPane panel2) {
		this.panel2 = panel2;
	}
	public JLayeredPane getPanel3() {
		return panel3;
	}
	public void setPanel3(JLayeredPane panel3) {
		this.panel3 = panel3;
	}
	public JLayeredPane getPanel4() {
		return panel4;
	}
	public void setPanel4(JLayeredPane panel4) {
		this.panel4 = panel4;
	}
	public JLayeredPane getPanel5() {
		return panel5;
	}
	public void setPanel5(JLayeredPane panel5) {
		this.panel5 = panel5;
	}
	public JLayeredPane getPanel6() {
		return panel6;
	}
	public void setPanel6(JLayeredPane panel6) {
		this.panel6 = panel6;
	}
	public JLayeredPane getArrowPanel() {
		return arrowPanel;
	}
	public void setArrowPanel(JLayeredPane arrowPanel) {
		this.arrowPanel = arrowPanel;
	}
	public JLayeredPane getErrorPanel() {
		return errorPanel;
	}
	public void setErrorPanel(JLayeredPane errorPanel) {
		this.errorPanel = errorPanel;
	}
	public ArrayList<Champion> getTeam1() {
		return team1;
	}
	public void setTeam1(ArrayList<Champion> team1) {
		this.team1 = team1;
	}
	public ArrayList<Champion> getTeam2() {
		return team2;
	}
	public void setTeam2(ArrayList<Champion> team2) {
		this.team2 = team2;
	}
	public PriorityQueue getTurnOrder() {
		return turnOrder;
	}
	public void setTurnOrder(PriorityQueue turnOrder) {
		this.turnOrder = turnOrder;
	}
	public Champion getNext() {
		return next;
	}
	public void setNext(Champion next) {
		this.next = next;
	}
	public PriorityQueue getTemp() {
		return temp;
	}
	public void setTemp(PriorityQueue temp) {
		this.temp = temp;
	}
	public String getSound_track() {
		return sound_track;
	}
	public void setSound_track(String sound_track) {
		this.sound_track = sound_track;
	}
	public Music getSe() {
		return se;
	}
	public void setSe(Music se) {
		this.se = se;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public JLabel getCurrentBox() {
		return currentBox;
	}
	public void setCurrentBox(JLabel currentBox) {
		this.currentBox = currentBox;
	}
	public JLabel getNextBox() {
		return nextBox;
	}
	public void setNextBox(JLabel nextBox) {
		this.nextBox = nextBox;
	}
	public JLabel getCurrentLabel() {
		return currentLabel;
	}
	public void setCurrentLabel(JLabel currentLabel) {
		this.currentLabel = currentLabel;
	}
	public JLabel getNextLabel() {
		return nextLabel;
	}
	public void setNextLabel(JLabel nextLabel) {
		this.nextLabel = nextLabel;
	}
	public boolean isHaslead() {
		return haslead;
	}
	public void setHaslead(boolean haslead) {
		this.haslead = haslead;
	}
}
//public Point getLocationOnBoard(int i, int j) {
//	xPos = getLocationOnBoard(currentGame.getCurrentChampion().getLocation().y,currentGame.getCurrentChampion().getLocation().x).x;
//	yPos = getLocationOnBoard(currentGame.getCurrentChampion().getLocation().y,currentGame.getCurrentChampion().getLocation().x).y;
//	switch(direction) {
//		case DOWN :  =  339; break;
//		case UP : i = 484; break;
//		case LEFT : i = 629; break;
//		case RIGHT : i = 774; break;
//		default : break;
//	}
//	timer = new Timer(30,new ActionListener() {
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		// TODO Auto-generated method stub
//		
//	
//	xPos++;
//	yPos++;
//	
//	testBoard.setCount(testBoard.getCount()+1);
//	if (xPos > 400 || yPos >400) timer.stop();
//	testBoard.getMove().repaint();
//	
//	
//	}
//});
//		
//		switch(i) {
//			case 0 : i = 339; break;
//			case 1 : i = 484; break;
//			case 2 : i = 629; break;
//			case 3 : i = 774; break;
//			case 4 : i = 919; break;
//			default : break;
//		}
//		
//		switch(j) {
//			case 0 : j = 22; break;
//			case 1 : j = 167; break;
//			case 2 : j = 312; break;
//			case 3 : j = 457; break;
//			case 4 : j = 602; break;
//			default : break;
//		}
//		Point position = new Point(i,j);
//		return position;
//	}
//		JButton buttonAnime = new JButton();
//		buttonAnime.setBounds(500, 500, 30, 30);
//		buttonAnime.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				timer = new Timer(20,new ActionListener() {
//
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						// TODO Auto-generated method stub
//						
//					
//					xPos++;
//					yPos++;
//					count++;
//					if (xPos > 400 || yPos >400) timer.stop();
//					move.repaint();
//					
//					
//					}
//				});
//				timer.start();
//				// TODO Auto-generated method stub
//				//for(int i = 0; i<130; i++) {
////					xPos+=30;
////					yPos+=30;
////					move.repaint();
////					//move.repaint(100);
////					frame.repaint();
////					frame.revalidate();
////					try {
////						Thread.sleep(500);
////					}catch(Exception ex) {}
////					xPos+=30;
////					yPos+=30;
////					move.repaint();
////					//move.repaint(100);
////					frame.repaint();
////					frame.revalidate();
////					try {
////						Thread.sleep(500);
////					}catch(Exception ex) {}
////					frame.repaint();
////					frame.revalidate();
//				//}
//				
//			}
//			
//		});
//		panel.add(buttonAnime, Integer.valueOf(1));
////					add cover to board
//					bar = new JProgressBar(-((Cover) board[i][j]).getCurrentHP(),0);
//					bar.setString(((Cover) (board[i][j])).getCurrentHP()+"");
//					bar.setValue(-((Cover) (board[i][j])).getCurrentHP());
//					bar.setBackground(Color.green);
//					//bar.setOpaque(true);
//					bar.setStringPainted(true);
//					
//					bar.setVisible(false);
//					bar.setForeground(Color.red);
//					bar.setBorder(BorderFactory.createLineBorder(Color.black, 3));
//					//bar.setBorderPainted(false);
//					bar.setBounds(j*141+333,i*141+122 , 136,30 );
//					coverBars.add(bar);
//					button.setIcon(cover);
//					button.setName(""+(coverBars.size()-1));
//					button.addMouseListener(new MouseListener() {
//
//						@Override
//						public void mouseClicked(MouseEvent e) {
//							// TODO Auto-generated method stub
//							
//						}
//
//						@Override
//						public void mousePressed(MouseEvent e) {
//							// TODO Auto-generated method stub
//							
//						}
//
//						@Override
//						public void mouseReleased(MouseEvent e) {
//							// TODO Auto-generated method stub
//							
//						}
//
//						@Override
//						public void mouseEntered(MouseEvent e) {
//							// TODO Auto-generated method stub
//							(coverBars.get(Integer.parseInt(((JButton) e.getSource()).getName()))).setVisible(true);
//							//bar.setVisible(true);
//						}
//
//						@Override
//						public void mouseExited(MouseEvent e) {
//							// TODO Auto-generated method stub
//							(coverBars.get(Integer.parseInt(((JButton) e.getSource()).getName()))).setVisible(false);
//						}
//						
//					});
//					panel.add(bar,Integer.valueOf(2));
//					panel2.add(button);
//					
//				}
//				