package GUI;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import model.world.AntiHero;
import model.world.Champion;
import model.world.Cover;

public class BoardView extends JFrame {
	
	
	
	public BoardView() {
		
		
		Object[][] testBoard = new Object[5][5];
		testBoard[1][3] = new AntiHero("Venom", 0, 0, 0, 0, 0, 0);
		
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.getContentPane().add(drawBoard(testBoard));	
		this.getContentPane().setBackground(Color.black);
		this.setSize(1366,768);
		this.setVisible(true);
		this.setResizable(false);
	}
	
	public JLayeredPane drawBoard(Object[][] board) {
		
		JLayeredPane panel = new JLayeredPane();
		JLayeredPane panel2 = new JLayeredPane();
		panel2.setLayout(new GridLayout(board.length, board[0].length, 5, 5));
		panel2.setBounds(358, 16, 700, 700);
		
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
		panel.add(panel2,Integer.valueOf(0));
		return panel;
	}
	
	public static void main(String[] args) {
		BoardView view = new BoardView();
	}
}
