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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import com.sun.tools.javac.Main;

import GUI.BoardTest.Move;
import controller.GameController;
import engine.Game;
import engine.PriorityQueue;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.world.AntiHero;
import model.world.Champion;
import model.world.Cover;

public class BoardView extends JLayeredPane {
	
	JLabel champion,label,label2,leader,leader2;
	JProgressBar bar;
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
	JButton testing,testing2,testing3,animeMove,up,down,left,right,play;
	JLabel test2;
	//Move move = new Move();
	Clip clip;
	int xPos=500;
	int finalY;
	int yPos =600;
	int finalX;
	Image champImage;
	boolean goAhead=false;
	int transparent,transparent2=360;
	ImageIcon image = new ImageIcon("assets/characters/128/Untitled22_20220607135427.png");
	Image Joey = image.getImage();
	ImageIcon image2 = new ImageIcon("assets/characters/128/Untitled22_20220607135443.png");
	Image Joey2 = image2.getImage();
	ImageIcon image3 = new ImageIcon("assets/characters/128/Untitled22_20220607135453.png");
	Image Joey3 = image3.getImage();
	int count = 0;
	ArrayList<JProgressBar> coverBars;
	JLayeredPane panel2;
	ArrayList<Champion> team1;
	ArrayList<Champion> team2;
	PriorityQueue turnOrder;
	PriorityQueue temp = new PriorityQueue(6);
	String sound_track;
    Music se = new Music();
	Game game;
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
		Champion next=null;;
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
		
		
		up = new JButton();
		// 
		up.setBounds(1200, 500, 100, 100);
		up.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				xPos = game.getCurrentChampion().getLocation().y*141+337;
				yPos = game.getCurrentChampion().getLocation().x*141+16;
				/*Up*/
				finalY = yPos-141;
				
				champImage = new ImageIcon("assets/characters/128/" + game.getCurrentChampion().getName() + ".png", game.getCurrentChampion().getName()).getImage();
				timer = new Timer(20,new ActionListener() {
					
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
					
						
						
						yPos--;
						move.repaint();
					//	
						//testBoard.setCount(testBoard.getCount()+1);
						if (yPos <= finalY) timer.stop();
						//testBoard.getMove().repaint();
						
						
						}
				});
					((JButton) panel2.getComponent(game.getCurrentChampion().getLocation().y+game.getCurrentChampion().getLocation().x*5)).setIcon(null);
					timer.start();
			}
			
		});
		panel.add(up,Integer.valueOf(1));
		
		down = new JButton();
		// 
		down.setBounds(1200, 600, 100, 100);
		down.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				xPos = game.getCurrentChampion().getLocation().y*141+337;
				yPos = game.getCurrentChampion().getLocation().x*141+16;
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
		panel.add(down,Integer.valueOf(1));
		left = new JButton();
		// 
		left.setBounds(1100, 550, 100, 100);
		left.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				xPos = game.getCurrentChampion().getLocation().y*141+337;
				yPos = game.getCurrentChampion().getLocation().x*141+17;
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
		panel.add(left,Integer.valueOf(1));
		right = new JButton();
		// 
		right.setBounds(1300, 550, 100, 100);
		right.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				xPos = game.getCurrentChampion().getLocation().y*141+337;
				yPos = game.getCurrentChampion().getLocation().x*141+17;
				/*Up*/
				finalX = xPos+141;
				
				champImage = new ImageIcon("assets/characters/128/" + game.getCurrentChampion().getName() + ".png", game.getCurrentChampion().getName()).getImage();
				timer = new Timer(20,new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
						
						
						xPos++;
						move.repaint();
						//	
						//testBoard.setCount(testBoard.getCount()+1);
						if (xPos >= finalX) timer.stop();
						//testBoard.getMove().repaint();
						
						
					}
				});
				((JButton) panel2.getComponent(game.getCurrentChampion().getLocation().y+game.getCurrentChampion().getLocation().x*5)).setIcon(null);
				timer.start();
			}
			
		});
		panel.add(right,Integer.valueOf(1));
		
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
        
        test2 = new JLabel("");
       // test2.setFont(font3);
        test2.setForeground(Color.black);
        test2.setBounds(20,470,300,250);
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
        
        
        
		leader = new JLabel();
		leader.setBounds(20, 50, 300, 400);
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
				FontRenderContext frc = new FontRenderContext(new AffineTransform(),true,true);
				//for(int i = 0; i < control.getCurrentGame().getAvailableChampions().size(); i++){
					//if (!(control.getCurrentGame().getAvailableChampions().get(i).getName().equals(currentName))) continue;
					//if (control.getCurrentGame().getAvailableChampions().get(i).getName().equals(currentName)){
						Font fadl = leader.getFont().deriveFont(14f);
						length = (int) fadl.getStringBounds(game.getCurrentChampion().getAbilities().get(0).getName(), frc).getWidth() +14;
						length2 = (int) fadl.getStringBounds(game.getCurrentChampion().getAbilities().get(1).getName(), frc).getWidth() +14;
						length3 = (int) fadl.getStringBounds(game.getCurrentChampion().getAbilities().get(2).getName(), frc).getWidth() +14;	
					//}
				
				if (e.getX()<=44+length && e.getX()>=44 && e.getY()>=260 && e.getY()<280){
					testing2.setVisible(false);
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
							+game.getCurrentChampion().getAbilities().get(0).getRequiredActionPoints()+"<br>Base Cool Down: "
							+game.getCurrentChampion().getAbilities().get(0).getBaseCooldown()+"<br>" + ability		
							+"</div></h2><html>");
					testing.setVisible(true);
					test2.setVisible(true);
					goAhead = true;
					move.repaint();
				}
				else if (e.getX()<=44+length2 && e.getX()>=44 && e.getY()>=280 && e.getY()<300){
					testing.setVisible(false);
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
					else { if (game.getCurrentChampion().getAbilities().get(0) instanceof CrowdControlAbility) { 
						ability = "<html>Effect: "+((((CrowdControlAbility) game.getCurrentChampion().getAbilities().get(0)).getEffect().getName())) + "<br> Duration: "+
								((((CrowdControlAbility) game.getCurrentChampion().getAbilities().get(0)).getEffect().getDuration()))+"</html>";
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
							+game.getCurrentChampion().getAbilities().get(1).getRequiredActionPoints()+"<br>Base Cool Down: "
							+game.getCurrentChampion().getAbilities().get(1).getBaseCooldown()+"<br>" + ability		
							+"</div></h2><html>");
					testing2.setVisible(true);
					test2.setVisible(true);
					goAhead=true;
					move.repaint();
				}
				else if (e.getX()<=44+length3 && e.getX()>=44 && e.getY()>=300 && e.getY()<=320){
					testing.setVisible(false);
					testing2.setVisible(false);
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
					else { if (game.getCurrentChampion().getAbilities().get(0) instanceof CrowdControlAbility) { 
						ability = "<html>Effect: "+((((CrowdControlAbility) game.getCurrentChampion().getAbilities().get(0)).getEffect().getName())) + "<br> Duration: "+
								((((CrowdControlAbility) game.getCurrentChampion().getAbilities().get(0)).getEffect().getDuration()))+"</html>";
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
							+game.getCurrentChampion().getAbilities().get(2).getRequiredActionPoints()+"<br>Base Cool Down: "
							+game.getCurrentChampion().getAbilities().get(2).getBaseCooldown()+"<br>" + ability		
							+"</div></h2><html>");
					testing3.setVisible(true);
					test2.setVisible(true);
					goAhead=true;
					move.repaint();
				}else {
					testing.setVisible(false);
					testing2.setVisible(false);
					testing3.setVisible(false);
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
		int j =1100;
        for (int ind = 0; ind < 6; ind++) {
            j = (ind==3? 1100 : j);
            Champion realChampion;

            if(ind <= 2){
                ImageIcon btn = new ImageIcon("assets/characters/64/" + control.getCurrentGame().getFirstPlayer().getTeam().get(ind).getName() + ".png");
                champion = new JLabel(btn);
                champion.setName(control.getCurrentGame().getFirstPlayer().getTeam().get(ind).getName());
                if (champion.getName()== game.getCurrentChampion().getName()) champion.setBorder(BorderFactory.createLineBorder(Color.green,5));
                if (champion.getName()== next.getName()) champion.setBorder(BorderFactory.createLineBorder(Color.yellow,5));
                //realChampion = control.getCurrentGame().getFirstPlayer().getTeam().get(ind);
            }
            else{
                ImageIcon btn = new ImageIcon("assets/characters/64/" + control.getCurrentGame().getSecondPlayer().getTeam().get(ind-3).getName() + ".png");
                champion = new JLabel(btn);
                champion.setName(control.getCurrentGame().getSecondPlayer().getTeam().get(ind-3).getName());
                System.out.println(champion.getName());
                if (champion.getName()== next.getName()) champion.setBorder(BorderFactory.createLineBorder(Color.yellow,5));
                if (champion.getName().equals(game.getCurrentChampion().getName())) champion.setBorder(BorderFactory.createLineBorder(Color.green,5));
                //realChampion = control.getCurrentGame().getSecondPlayer().getTeam().get(ind-3);
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
            panel.add(champion,Integer.valueOf(0));
            j+=75;
        }
		
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
		
		
		panel.add(move,Integer.valueOf(0));
		panel.add(label,Integer.valueOf(1));
		panel.add(label2,Integer.valueOf(1));
		panel.add(leader,Integer.valueOf(1));
		
	}
	public String putText(Champion curr) {
		String effects = "";
		for (int i = 0; i < curr.getAppliedEffects().size();i++) {
			effects += "<html>"+curr.getAppliedEffects().get(i).getName()+"<br></html>";
		}
		String isLeader = "";
		if (curr==control.getCurrentGame().getFirstPlayer().getLeader()){
			if (game.isFirstLeaderAbilityUsed()) {
			isLeader = "Leader: Used";}
			else isLeader=  "Leader: On";
			transparent = 60;
			transparent2 = 380;
			move.repaint();
		}
		else if (curr==control.getCurrentGame().getSecondPlayer().getLeader()){
			if (game.isSecondLeaderAbilityUsed()) {
				isLeader = "Leader: Used";}
			else isLeader=  "Leader: On";
			transparent = 60;
			transparent2 = 380;
			move.repaint();
		}else {
			transparent2 = 360;
			transparent = 80;
			move.repaint();
		}
		String text="";
		if (curr.getAbilities().size()==4) {
		 text=
				"<html><h1>"
						+"<div style='text-indent:15px; font-size:26; margin-top :-40;'>"
						+isLeader+"</div><div style='text-indent:15px;margin-left:8; font-size:24; margin-top :0;'>"+curr.getName()
						+"</div></h1><h2><div style='font-size:18;margin-left:35;margin-top :-32;'>"+"Type: "
						+curr.getClass().getSimpleName()+"<br>Mana: "
						+curr.getCurrentHP()+"<br>Health Points: "
						+curr.getMana()+"<br>Action Points: "
						+curr.getCurrentActionPoints()+"<br>Attack Points: "
						+curr.getAttackDamage()+"<br> Attack Range: "
						+curr.getAttackRange()+"<br>Abilities:"		
						+"</div><div style='font-size:14; margin-left:50; margin-top:8;'>"
						+curr.getAbilities().get(0).getName()+"<br>"
						+curr.getAbilities().get(1).getName()+"<br>"
						+curr.getAbilities().get(2).getName()+"<br>"
						+curr.getAbilities().get(3).getName()
						+"</div><div style='font-size:18;margin-left:35;margin-top :8;'>Applied Effects: "+curr.getAppliedEffects().size()
						+"</div><div style='font-size:14; margin-left:50; margin-top:18;'>" + effects
						+"</div></h2><html>";}
		else text=
				"<html><h1>"
						+"<div style='text-indent:15px; font-size:26; margin-top :-40;'>"
						+isLeader+"</div><div style='text-indent:15px;margin-left:8; font-size:24; margin-top :0;'>"+curr.getName()
						+"</div></h1><h2><div style='font-size:18;margin-left:35;margin-top :-32;'>"+"Type: "
						+curr.getClass().getSimpleName()+"<br>Mana: "
						+curr.getCurrentHP()+"<br>Health Points: "
						+curr.getMana()+"<br>Action Points: "
						+curr.getCurrentActionPoints()+"<br>Attack Points: "
						+curr.getAttackDamage()+"<br> Attack Range: "
						+curr.getAttackRange()+"<br>Abilities:"		
						+"</div><div style='font-size:14; margin-left:50; margin-top:8;'>"
						+curr.getAbilities().get(0).getName()+"<br>"
						+curr.getAbilities().get(1).getName()+"<br>"
						+curr.getAbilities().get(2).getName()
						+"</div><div style='font-size:18;margin-left:35;margin-top :8;'>Applied Effects: "+curr.getAppliedEffects().size()
						+"</div><div style='font-size:14; margin-left:50; margin-top:18;'>" + effects
						+"</div></h2><html>";
		return text;
	}

	public void drawBoard(Object[][] board) {
		 panel2 = new JLayeredPane();
		panel2.setLayout(new GridLayout(board.length, board[0].length, 5, 5));
		panel2.setBounds(333, 16, 700, 700);
		panel.add(panel2,Integer.valueOf(1));
//		
//		
//	
//		// Iterate over game board, rows and columns, generating buttons
		for (int i = 0; i < board.length; i++) {
//			
			for (int j = 0; j < board[i].length; j++) {
//				
				JButton button = new JButton();
				button.setOpaque(false);
				button.setContentAreaFilled(false);
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
//					add cover to board
					bar = new JProgressBar(-((Cover) board[i][j]).getCurrentHP(),0);
					bar.setString(((Cover) (board[i][j])).getCurrentHP()+"");
					bar.setValue(-((Cover) (board[i][j])).getCurrentHP());
					bar.setBackground(Color.green);
					//bar.setOpaque(true);
					bar.setStringPainted(true);
					
					bar.setVisible(false);
					bar.setForeground(Color.red);
					bar.setBorder(BorderFactory.createLineBorder(Color.black, 3));
					//bar.setBorderPainted(false);
					bar.setBounds(j*141+333,i*141+122 , 136,30 );
					coverBars.add(bar);
					button.setIcon(cover);
					button.setName(""+(coverBars.size()-1));
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
							(coverBars.get(Integer.parseInt(((JButton) e.getSource()).getName()))).setVisible(true);
							//bar.setVisible(true);
						}

						@Override
						public void mouseExited(MouseEvent e) {
							// TODO Auto-generated method stub
							(coverBars.get(Integer.parseInt(((JButton) e.getSource()).getName()))).setVisible(false);
						}
						
					});
					panel.add(bar,Integer.valueOf(2));
					panel2.add(button);
					
				}
				
				else if (board[i][j] instanceof Champion) {
					// add that champion to this place
					
					Champion curr = (Champion) board[i][j];
					ImageIcon champIcon = new ImageIcon("assets/characters/128/" + curr.getName() + ".png", curr.getName());
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
			//frame.pack();
			g2.setColor(Color.black);
			//g2.drawRoundRect( 465, 570, 175, 70, 10, 10);
			AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 0.5f);
			g2.setComposite(alpha);
			//g2.fillRoundRect(  xPos, yPos, 175, 70, 30, 30);
			g2.fillRect( 30, transparent, 230, transparent2);
			g2.fillRect( 1100, 45, 215, 220);
			if (goAhead) {
			g2.fillRect( 20, 470, 300, 255);}
			
			//g2.fillRoundRect( 726, 600, 175, 70, 30, 30);
		}
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