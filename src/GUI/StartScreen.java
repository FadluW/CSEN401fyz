package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import controller.GameController;
import controller.GameController.BeginListener;

public class StartScreen extends JFrame{
	JLayeredPane panel;
	JButton begin, quit;
	Move move;
	Font font,font2,font3,font4,plain,font5, font5Big;
	JTextField field,field2;
	JLabel label,label2,title,background1,error;
	Image image;
	JFrame frame = this;
	GameController control;
	
	public StartScreen(GameController control){//JLayeredPane panel, JButton begin, JButton quit) {
		
		
		ImageIcon icon = new ImageIcon("assets/background/Game Start Small.jpg");
        setIconImage(icon.getImage());
		this.control = control;
		//JFrame frame = this;
		panel = new JLayeredPane();
		try {
			font4 = Font.createFont(Font.TRUETYPE_FONT,new File( "assets/fonts/Super Webcomic Bros. Bold Italic.ttf"));
			plain = font4.deriveFont(Font.PLAIN,20f);
		} 
		catch (FontFormatException e1) {} 
		catch (IOException e1) {}
		try {
			font5 = Font.createFont(Font.TRUETYPE_FONT,new File( "assets/fonts/eight-bit-dragon.otf"));
			font5Big = font5.deriveFont(Font.PLAIN, 32f);
			font5 = font5.deriveFont(Font.PLAIN,20f);
		} 
		catch (FontFormatException e1) {} 
		catch (IOException e1) {}
		
		font = new Font("serif",Font.ITALIC + Font.BOLD,30);
		font2 = new Font("serif", Font.BOLD,20);
		font3 = new Font("serif", Font.BOLD,70);
		ImageIcon image = new ImageIcon("assets/background/Game Start Small.jpg");
		
		move = new Move();
		move.setBounds(0,0,1366, 768);

		title = new JLabel("Logo");
		title.setBounds(600, 100, 200, 200);
		title.setFont(font3);
		title.setForeground(Color.green);
		
		field = new JTextField("Player 1");
		field.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (field.getText().length()>=11)
				field.setText(field.getText().substring(0, 10));
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		field.setBounds(250, 530,200, 50);
		field.setBackground(Color.yellow);
		field.setOpaque(true);
		field.setForeground(Color.red);
		field.setFont(font5);
		field.setHorizontalAlignment(JLabel.CENTER);
		field.requestFocus();
		
		field2 = new JTextField("Player 2");
		field2.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (field2.getText().length()>=11)
				field2.setText(field2.getText().substring(0, 10));
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		field2.setBounds(916, 530, 200, 50);
		field2.setOpaque(true);
		field2.setBackground(Color.yellow);
		field2.setFont(font5);
		field2.setHorizontalAlignment(JLabel.CENTER);
		field2.setForeground(Color.red);
		field2.requestFocus();
		
		error = new JLabel("Please enter both players' names first!");
		error.setOpaque(true);
		error.setBackground(Color.black);
		error.setBounds((380),350, 580, 100);
		error.setForeground(Color.red);
		error.setFont(plain);
		error.setHorizontalAlignment(0);
		error.setVisible(false);
		
		label =  new JLabel("First Player's name:");
		label.setOpaque(true);
		label.setBackground(Color.red);
		label.setBounds(200, 450, 300, 50);
		label.setFont(plain);
		label.setHorizontalAlignment(0);
		//label.setForeground(new Color(0x9DBFD1));
		
		label2 =  new JLabel("Second Player's name:");
		label2.setOpaque(true);
		label2.setBackground(Color.red);
		label2.setBounds(866, 450, 300, 50);
		label2.setFont(plain);
		label2.setHorizontalAlignment(0);
		
		background1 = new JLabel();
		//background1.setFont(font3);
		background1.setIcon(image);
		background1.setBackground(Color.white);
		background1.setBounds(0, 0, 1366, 768);
		
		quit = new JButton();
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispatchEvent(new WindowEvent(frame,WindowEvent.WINDOW_CLOSING));
			}
		});
		
		//quit.addActionListener(new MyButtonListener());
		quit.setOpaque(false);
		quit.setContentAreaFilled(false);
		quit.setBorderPainted(false);
		quit.setFocusPainted(false);
		quit.setFont(font5);
		quit.setText("Quit");
		quit.setBounds(465,600,175,70);
		
		begin = new JButton();
		begin.addActionListener(control.new BeginListener());
		//begin.addActionListener(new MyPlayListener());
		//begin.setBackground(Color.white);
		begin.setOpaque(false);
		begin.setContentAreaFilled(false);
		begin.setBorderPainted(false);
		begin.setFocusPainted(false);
		begin.setFont(font5);
		begin.setText("Start");
		begin.setBounds(726,600,175,70);
		
		//panel.add(background1,Integer.valueOf(0));
		panel.add(move,Integer.valueOf(0));
		//panel.add(title,Integer.valueOf(2));
		panel.add(label,Integer.valueOf(1));
		panel.add(label2,Integer.valueOf(1));
		panel.add(field,Integer.valueOf(1));
		panel.add(field2,Integer.valueOf(1));
		panel.add(begin,Integer.valueOf(1));
		panel.add(quit,Integer.valueOf(1));
		panel.add(error,Integer.valueOf(2));
		
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.getContentPane().add(panel);	
		this.getContentPane().setBackground(Color.black);
		this.setSize(1366,768);
		this.setVisible(true);
		this.setResizable(false);
		
	}
	public JLayeredPane getPanel() {
		return panel;
	}
	public JLabel getError() {
		return error;
	}
	public String getField() {
		return field.getText();
	}

	public String getField2() {
		return field2.getText();
	}

	public class Move extends JPanel{
		public void paintComponent(Graphics g) {
			ImageIcon image = new ImageIcon("assets/background/Game Start Small.jpg");
			Image zeina = image.getImage();
			Graphics2D g2 = (Graphics2D) g;
//			GradientPaint gradient = new GradientPaint(0,0,Color.cyan,1366,768,Color.blue);
//			g2.setPaint(gradient);
//			g2.fillRect(0, 0, 1366, 768);
			g2.drawImage(zeina, 0, 0, 	1366, 768, null);
			//frame.pack();
			g2.setColor(Color.red);
			//g2.drawRoundRect( 465, 570, 175, 70, 10, 10);
			g2.fillRoundRect(  465, 600, 175, 70, 30, 30);
			g2.fillRoundRect( 726, 600, 175, 70, 30, 30);
		}
	}
	
//	public class MyButtonListener implements ActionListener{
//	public void actionPerformed(ActionEvent a) {
//		panel.setBackground(Color.white);
//		
//	}
//}
////	public static void main(String[] args) {
////		StartScreen start = new StartScreen(null);
////	}
}

