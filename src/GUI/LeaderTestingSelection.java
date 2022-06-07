package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

import GUI.StartScreen.Move;
import controller.GameController;
import engine.Game;
import model.world.Champion;

public class LeaderTestingSelection extends JFrame{
	JLayeredPane panel;
	JButton champion,forward;
	Move move;
	Font font,font2,font3,font4,plain,font5,font5Small,font5Big;
	JTextField field,field2;
	JLabel label,label2,title,background1,leader,leader2,test;
	Image image;
	//JLayeredPane panel = this;
	GameController control;
	ImageIcon btn;
	Border labelBorder;
	ArrayList<Champion> team;
	//GameController control;
	
	public LeaderTestingSelection(GameController control, Game game ) throws FontFormatException, IOException {
		this.control = control;
		panel = new JLayeredPane();
		panel.setBackground(new Color(122,66,148));
		panel.setBounds(0,0,1366,768);
		//this.setBackground(new Color(103,0,0));
		//add(background);
		panel.setLayout(null);
		panel.setVisible(true);
		panel.setOpaque(true);
		leader=new JLabel("");
		leader2=new JLabel("");
		move = new Move();
		move.setBounds(0,0,1366, 768);
		this.control = control;
		
		try {
			font4 = Font.createFont(Font.TRUETYPE_FONT,new File( "assets/fonts/Super Webcomic Bros. Bold Italic.ttf"));
			plain = font4.deriveFont(Font.PLAIN,20f);
		} 
		catch (FontFormatException e1) {} 
		catch (IOException e1) {}
		try {
			font5 = Font.createFont(Font.TRUETYPE_FONT,new File( "assets/fonts/eight-bit-dragon.otf"));
			font5Big = font5.deriveFont(Font.PLAIN, 32f);
			font5Small = font5.deriveFont(Font.PLAIN,14f);
			font5 = font5.deriveFont(Font.PLAIN,20f);
			
		} 
		catch (FontFormatException e1) {} 
		catch (IOException e1) {}
		font = new Font("serif",Font.ITALIC + Font.BOLD,30);
		font2 = new Font("serif", Font.BOLD,20);
		font3 = new Font("serif", Font.BOLD,70);

        InputStream is = new BufferedInputStream(new FileInputStream("assets/fonts/BlackWidowMovie-d95Rg.ttf"));
        Font blackWidowFont = Font.createFont(Font.TRUETYPE_FONT,is);
        Font selectChamp = blackWidowFont.deriveFont(45f);
        Font player = blackWidowFont.deriveFont(20f);
		
		forward = new JButton();
		forward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.print("Button Worked");
				JLayeredPane panel2 = new JLayeredPane();
				panel2.setBackground(new Color(231,154,32));
//				frame.getContentPane().remove(panel);
//				frame.getContentPane().add(panel2);
//				frame.revalidate();
				//label.setText("HI");
			}
		});
		//forward.addActionListener(new MyButtonListener());
		forward.setOpaque(false);
		forward.setContentAreaFilled(false);
		
		forward.setBackground(new Color(0x0d0f26));
		//forward.setBorderPainted(false);
		forward.setBorder(BorderFactory.createLineBorder(new Color(0xf8df82), 4, true));
		forward.setFocusPainted(false);
		forward.setFont(font);
		forward.setBounds(1000,570,175,70);
		forward.setText("Continue..");
		//forword.setforeground()
		//forward.disable();
		forward.setEnabled(false);
		
		test = new JLabel("");
        test.setFont(font3);
        test.setForeground(Color.white);
        test.setBackground(new Color(0x0d0f26)/*new Color(103,0,0)*/);
        labelBorder = test.getBorder();
        test.setBounds(1050,28,250,320);
        test.setVerticalAlignment(JLabel.TOP);
        
		int j = 438;
		for (int ind = 0; ind < 6; ind++) {
			
			if (ind <3 && game.getFirstPlayer().getTeam().size()>0) 
             btn = new ImageIcon("assets/ui/button_" + game.getFirstPlayer().getTeam().get(ind).getName() + ".png");
			else if(game.getSecondPlayer().getTeam().size()>0)
				btn = new ImageIcon("assets/ui/button_" + game.getSecondPlayer().getTeam().get(ind-3).getName() + ".png");
			j = (ind == 3? 438 : j);
			champion = new JButton(btn);
			if (ind<3)champion.setName(game.getFirstPlayer().getTeam().get(ind).getName());
			else champion.setName(game.getSecondPlayer().getTeam().get(ind-3).getName());
			
			//champion.addActionListener(new MyPlayListener());
			champion.setBackground(new Color(0x0d0f26)/*new Color(49,145,237)*/);
			champion.addMouseListener(new MouseListener() {

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
//					if ((!mouse || (e.getSource()==current)) && ((JButton)(e.getSource())).isEnabled() == true && counter != 6) {
//						System.out.print("Enter");
						
					test.setOpaque(true);
					test.setBorder(BorderFactory.createLineBorder(Color.black, 5));
					
//					if (counter%2==0) test.setBounds(40,28,250,320);
//					else test.setBounds(1050,28,250,320);
					//test.setText(getName());
					if (((JButton) e.getSource()).getY()<500) {
						team = game.getFirstPlayer().getTeam();
					}else team= game.getSecondPlayer().getTeam();
					for(int i = 0; i < team.size(); i++){
						if (team.get(i).getName().equals(((JButton) (e.getSource())).getName())){
							test.setText("<html><h1>"
							+"<div style='text-indent:15px; font-size:24; margin-top :-40;'>"
							+team.get(i).getName()
							+"</div></h1><h2><div style='font-size:18;margin-left:35;margin-top :-32;'>"+"Type: "
							+team.get(i).getClass().getSimpleName()+"<br>Mana: "
							+team.get(i).getCurrentHP()+"<br>Health Points: "
							+team.get(i).getMana()+"<br>Action Points: "
							+team.get(i).getCurrentActionPoints()+"<br>Attack Points: "
							+team.get(i).getAttackDamage()+"<br> Attack Range: "
							+team.get(i).getAttackRange()+"<br>Abilities:"		
							+"</div><div style='font-size:14; margin-left:50; margin-top:8;'>"
							+team.get(i).getAbilities().get(0).getName()+"<br>"
							+team.get(i).getAbilities().get(1).getName()+"<br>"
							+team.get(i).getAbilities().get(2).getName()+"</div>"
							+"</div></h2><html>");
            			}
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
					test.setText(""); test.setOpaque(false);
					test.setBorder(labelBorder);
					// TODO Auto-generated method stub
					
				}
            	
            });
			int i = (ind<3? 100 : 500);
			champion.setBounds(j,i,150,150);
			champion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.print("Button Works");
					champion = (JButton) e.getSource();
					if (i==100) {
					leader.setText("Leader "+ champion.getLocation());
					leader.setBounds(120,i+100,300,50);}
					else {leader2.setText("Leader "+ champion.getLocation());
					leader2.setBounds(120,i+100,300,50);}
					//frame.getContentPane().add(panel2);
					//frame.revalidate();
					if(leader.getText()!="" && leader2.getText()!="") forward.setEnabled(true);
				}
			});
			panel.add(champion,Integer.valueOf(1));
			j+=170;
		}
		
		title = new JLabel("Select Your Leader");
		title.setBorder(BorderFactory.createDashedBorder(new Color(0x1d916f), 6, 10, 4, true));
		title.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		title.setBounds(435, 320, 500, 100);
		title.setFont(selectChamp);
		title.setForeground(new Color(0xf8df82));
		
		label =  new JLabel("First Player's Leader");
		label.setBounds(100, 150, 300, 50);
		label.setForeground(/*new Color(103,0,0)*/new Color(0x1d916f));
		//label.setOpaque(true);
		//Stroke stroke = new Stroke();
		
		//.setBorder(BorderFactory.createLineBorder(new Color(0x050505), 5, true));
		//label.setBackground(new Color(0x050505));
		label.setFont(font5);
		
		label2 =  new JLabel("Second Player's Leader");
		label2.setBounds(100, 550, 350, 50);
		label2.setFont(plain);
		
		panel.add(move,Integer.valueOf(0));
		panel.add(title,Integer.valueOf(1));
		panel.add(forward,Integer.valueOf(1));
		panel.add(label,Integer.valueOf(1));
		panel.add(label2,Integer.valueOf(1));
		panel.add(leader,Integer.valueOf(1));
		panel.add(leader2,Integer.valueOf(1));
		panel.add(test,Integer.valueOf(1));
		
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.getContentPane().add(panel);	
		this.setSize(1366,768);
		this.setVisible(true);
		this.setResizable(false);
	}
//	public static void main(String[] args) {
//		LeaderSelection leader = new LeaderSelection();
//	}
	
	public class Move extends JPanel{
		public void paintComponent(Graphics g) {
//			ImageIcon image = new ImageIcon("assets/background/Game Start Small.jpg");
//			Image zeina = image.getImage();
			Graphics2D g2 = (Graphics2D) g;
			GradientPaint gradient = new GradientPaint(1366/2,768/2+100,new Color(0x050505),1366/2,0,new Color(0x48142a)/*new Color(0x9e9e9e)*/);
//			GradientPaint gradient = new GradientPaint(0,0,Color.cyan,1366,768,Color.blue);
			g2.setPaint(gradient);
			g2.fillRect(0, 0, 1366, 768);
			//new Color(0x48142a);
			//g2.drawImage(zeina, 0, 0, 	1366, 768, null);
			//frame.pack();
			g2.setColor(new Color(0xf8df82));
//			g2.drawRoundRect(1000, 570, 175, 70, 30, 30);
//			g2.drawRoundRect(1001, 571, 174, 69, 30, 30);
//			g2.drawRoundRect(1002, 572, 173, 68, 30, 30);
//			g2.drawRoundRect(1003, 573, 172, 67, 30, 30);
//			g2.drawRoundRect(1004, 574, 171, 66, 30, 30);
			
			g2.setColor(new Color(0x0d0f26));
			g2.fillRoundRect(  1000,570, 175, 70, 30, 30);
			//g2.fillRoundRect( 726, 570, 175, 70, 30, 30);
		}
	}
}

