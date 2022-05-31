package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import GUI.StartScreen.Move;

public class LeaderSelection extends JFrame{
	JLayeredPane panel;
	JButton champion,forward;
	Move move;
	Font font,font2,font3,font4,plain;
	JTextField field,field2;
	JLabel label,label2,title,background1,leader,leader2;
	Image image;
	JFrame frame = this;
	
	public LeaderSelection() {
		panel = new JLayeredPane();
		leader=new JLabel("");
		leader2=new JLabel("");
		move = new Move();
		move.setBounds(0,0,1366, 768);
		
		try {
			font4 = Font.createFont(Font.TRUETYPE_FONT,new File( "assets/fonts/Super Webcomic Bros. Bold Italic.ttf"));
			plain = font4.deriveFont(Font.PLAIN,20f);
		} catch (FontFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		font = new Font("serif",Font.ITALIC + Font.BOLD,30);
		font2 = new Font("serif", Font.BOLD,20);
		font3 = new Font("serif", Font.BOLD,60);
		
		forward = new JButton();
		forward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.print("Button Worked");
				JLayeredPane panel2 = new JLayeredPane();
				panel2.setBackground(Color.yellow);
				frame.getContentPane().remove(panel);
				frame.getContentPane().add(panel2);
				frame.revalidate();
				//label.setText("HI");
			}
		});
		//forward.addActionListener(new MyButtonListener());
		forward.setOpaque(false);
		forward.setContentAreaFilled(false);
		forward.setBorderPainted(false);
		forward.setFocusPainted(false);
		forward.setFont(font);
		forward.setBounds(1000,570,175,70);
		forward.setText("Continue..");
		//forward.disable();
		forward.setEnabled(false);
		
		
		int j =473;
		for (int ind = 0; ind < 6; ind++) {
			j = (ind==3? 473 : j);
			champion = new JButton();
			//champion.addActionListener(new MyPlayListener());
			champion.setBackground(Color.black);
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
					frame.revalidate();
					if(leader.getText()!="" && leader2.getText()!="") forward.setEnabled(true);
				}

				private void setText(String string) {
					// TODO Auto-generated method stub
					
				}
			});
			panel.add(champion,Integer.valueOf(1));
			j+=170;
		}
		
		title = new JLabel("Select your leader!!!");
		title.setBounds(460, 260, 600, 200);
		title.setFont(font3);
		title.setForeground(Color.green);
		
		label =  new JLabel("First Player's Leader");
		label.setBounds(100, 150, 350, 50);
		label.setFont(plain);
		
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
		
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.getContentPane().add(panel);	
		this.getContentPane().setBackground(Color.black);
		this.setSize(1366,768);
		this.setVisible(true);
		this.setResizable(false);
	}
	public static void main(String[] args) {
		LeaderSelection leader = new LeaderSelection();
	}
	
	public class Move extends JPanel{
		public void paintComponent(Graphics g) {
//			ImageIcon image = new ImageIcon("assets/background/Game Start Small.jpg");
//			Image zeina = image.getImage();
			Graphics2D g2 = (Graphics2D) g;
//			GradientPaint gradient = new GradientPaint(0,0,Color.cyan,1366,768,Color.blue);
//			g2.setPaint(gradient);
//			g2.fillRect(0, 0, 1366, 768);
			//g2.drawImage(zeina, 0, 0, 	1366, 768, null);
			//frame.pack();
			g2.setColor(Color.yellow);
			//g2.drawRoundRect( 465, 570, 175, 70, 10, 10);
			g2.fillRoundRect(  1000,570, 175, 70, 30, 30);
			//g2.fillRoundRect( 726, 570, 175, 70, 30, 30);
		}
	}
}

