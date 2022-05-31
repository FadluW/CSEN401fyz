package GUI;

import java.awt.*;
import java.awt.GridLayout;
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
	
	
	public BoardView() {
		leader=new JLabel("");
		leader.setText("<html> Current Champion <br> Current HP</html>");
		leader.setBounds(1100,150,250,300);
		leader.setBackground(Color.white);
		leader2=new JLabel("");
		
		Object[][] testBoard = new Object[5][5];
		testBoard[1][3] = new AntiHero("Venom", 0, 0, 0, 0, 0, 0);
		
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
//					add that champion to this place
					
					panel2.add(button);
				}
			}
		}
		
		panel.addMouseMotionListener(new MouseMotionListener() {
			

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			System.out.print("Button Works");
			//if (i==100) {
			leader.setText("<html> Current Champion <br> Current HP</html>");
			//}
			//else {leader2.setText("Leader "+ champion.getLocation()+"\n"+"Current HP");
			//leader2.setBounds(120,i+100,300,50);//}
			//frame.getContentPane().add(panel2);
			frame.revalidate();
			//if(leader.getText()!="" && leader2.getText()!="") forward.setEnabled(true);
		}
	});
		
		int j =1100;
		for (int ind = 0; ind < 6; ind++) {
			j = (ind==3? 1100 : j);
			ImageIcon btn = new ImageIcon("assets/ui/button_Dr Strange.png");
			champion = new JLabel(btn);
			int i = (ind<3? 60 : 160);
			champion.setBounds(j,i,64,64);
			champion.addMouseMotionListener(new MouseMotionListener() {
					

				@Override
				public void mouseDragged(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseMoved(MouseEvent e) {
					System.out.print("Button Works");
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
		label.setBounds(1100, 20, 350, 50);
		//label.setFont(plain);
		
		label2 =  new JLabel("Team 2's Info:");
		label2.setBounds(1100, 120, 350, 50);
		//label2.setFont(plain);
		
		
		panel.add(panel2,Integer.valueOf(0));
		panel.add(label,Integer.valueOf(0));
		panel.add(label2,Integer.valueOf(0));
		panel.add(leader,Integer.valueOf(0));
	}
	
	public static void main(String[] args) {
		BoardView view = new BoardView();
	}
}
