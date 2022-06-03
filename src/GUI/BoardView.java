package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

import model.world.AntiHero;
import model.world.Champion;
import model.world.Cover;

public class BoardView extends JFrame {
	
	JLabel champion,label,label2,leader,leader2;
	JLayeredPane panel = new JLayeredPane();
	JFrame frame = this;
	Move move = new Move();
	
	
	public BoardView() {
		move.setSize(1366,768);
		leader=new JLabel("");
		leader.setText("<html> Current Champion <br> Current HP</html>");
		leader.setBounds(1100,290,225,300);
		leader.setVerticalAlignment(JLabel.TOP);
		leader.setOpaque(true);
		leader.setBackground(Color.white);
		leader2=new JLabel("");
		
		Object[][] testBoard = new Object[5][5];
		testBoard[1][3] = new AntiHero("Dr Strange", 0, 0, 0, 0, 0, 0);
		
		drawBoard(testBoard);
		
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.getContentPane().add(panel);	
		this.getContentPane().setBackground(Color.pink);
		this.setSize(1366,768);
		this.setVisible(true);
		this.setResizable(false);
	}
	
	public void drawBoard(Object[][] board) {
		JLayeredPane panel2 = new JLayeredPane();
		panel2.setLayout(new GridLayout(board.length, board[0].length, 5, 5));
		panel2.setBounds(333, 16, 700, 700);
		
		// Iterate over game board, rows and columns, generating buttons
		for (int i = 0; i < board.length; i++) {
			
			for (int j = 0; j < board[i].length; j++) {
				
				JButton button = new JButton();
				button.setOpaque(false);
				button.setContentAreaFilled(false);
				//button.setBorderPainted(false);
				
				if (board[i][j] == null) {
					panel2.add(button);
				}
				
				if (board[i][j] instanceof Cover) {
//					add cover to board
					panel2.add(button);
				}
				
				if (board[i][j] instanceof Champion) {
					// add that champion to this place
					Champion curr = (Champion) board[i][j];
					ImageIcon champIcon = new ImageIcon("assets/characters/128/" + curr.getName() + ".png", curr.getName());
					button.setIcon(champIcon);
					panel2.add(button);
				}
			}
		}
		
		// Listen for mouse over alive champ
		panel.addMouseMotionListener(
			new MouseMotionListener() {
				@Override
				public void mouseDragged(MouseEvent e) {}

				@Override
				public void mouseMoved(MouseEvent e) {
					//if (i==100) {
					leader.setText("<html> Current Champion <br> Current HP</html>");
					//}
					//else {leader2.setText("Leader "+ champion.getLocation()+"\n"+"Current HP");
					//leader2.setBounds(120,i+100,300,50);//}
					//frame.getContentPane().add(panel2);
					frame.revalidate();
					//if(leader.getText()!="" && leader2.getText()!="") forward.setEnabled(true);
				}
			}
		);
		
		final int ICON_WIDTH = 75;
		final int ROWS = 2;

		// for (int i = 0; i < ROWS; i++) {
		// 	// Fetch team "i"

		// 	for (int j = 0; /*j < team[i].size*/; j++) {
		// 		// Fetch champion j from team
		// 		// Champion curr = team.get(j);

		// 		// Fetch Icon of champion
		// 		// ImageIcon iconChamp = new ImageIcon("assets/characters/64/" + curr.getName() + ".png");
		// 	}
		// }

		int j =1100;
		for (int ind = 0; ind < 6; ind++) {
			j = (ind==3? 1100 : j);
			ImageIcon btn = new ImageIcon("assets/characters/64/Dr Strange.png");
			champion = new JLabel(btn);
			int i = (ind<3? 100 : 200);
			champion.setBounds(j,i,64,64);
			champion.addMouseMotionListener(new MouseMotionListener() {
				@Override
				public void mouseDragged(MouseEvent e) {}

				@Override
				public void mouseMoved(MouseEvent e) {
					champion = (JLabel) e.getSource();
					//if (i==100) {
					leader.setText("<html>Leader "+ champion.getLocation()+"<br> Current HP</html>");
					//}
					//else {leader2.setText("Leader "+ champion.getLocation()+"\n"+"Current HP");
					//leader2.setBounds(120,i+100,300,50);//}
					//frame.getContentPane().add(panel2);
					frame.revalidate();
					//if(leader.getText()!="" && leader2.getText()!="") forward.setEnabled(true);
				}
			});
			panel.add(champion,Integer.valueOf(0));
			j+=75;
		}
		
		label =  new JLabel("Team 1's Info:");
		label.setBounds(1100, 60, 350, 50);
		//label.setFont(plain);
		
		label2 =  new JLabel("Team 2's Info:");
		label2.setBounds(1100, 160, 350, 50);
		//label2.setFont(plain);
		
		
		panel.add(panel2,Integer.valueOf(1));
		panel.add(label,Integer.valueOf(1));
		panel.add(label2,Integer.valueOf(1));
		panel.add(leader,Integer.valueOf(1));
		panel.add(move,Integer.valueOf(0));
	}
	
	public static void main(String[] args) {
		BoardView view = new BoardView();
	}
	
	public class Move extends JPanel{
		public void paintComponent(Graphics g) {
			ImageIcon image = new ImageIcon("assets/background/Board_grass.jpg");
			Image zeina = image.getImage();
			Graphics2D g2 = (Graphics2D) g;
//			GradientPaint gradient = new GradientPaint(0,0,Color.cyan,1366,768,Color.blue);
//			g2.setPaint(gradient);
//			g2.fillRect(0, 0, 1366, 768);
			g2.drawImage(zeina, 0, 0, 	1366, 768, null);
			//frame.pack();
			//g2.setColor(Color.red);
			//g2.drawRoundRect( 465, 570, 175, 70, 10, 10);
			///g2.fillRoundRect(  465, 600, 175, 70, 30, 30);
			//g2.fillRoundRect( 726, 600, 175, 70, 30, 30);
		}
	}
}
