package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class StartScreen extends JFrame{
	JLayeredPane panel;
	JButton begin, quit;
	Move move;
	Font font,font2,font3,font4,plain,font5;
	JTextField field,field2;
	JLabel label,label2,title,background1;
	Image image;
	JFrame frame = this;
	
	public StartScreen(){//JLayeredPane panel, JButton begin, JButton quit) {
		
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
		
		field = new JTextField();
		field.setBounds(300, 460, 100, 50);
		field.setBackground(new Color(0x9DBFD1));
		field.setBorder(null);
		field.setForeground(Color.red);
		field.requestFocus();
		
		field2 = new JTextField();
		field2.setBounds(950, 460, 100, 50);
		field2.setBackground(new Color(0x9DBFD1));
		field2.setBorder(null);
		field2.setForeground(Color.red);
		field2.requestFocus();
		
		label =  new JLabel("First Player's name:");
		label.setBounds(300, 400, 190, 50);
		label.setFont(plain);
		//label.setForeground(new Color(0x9DBFD1));
		
		label2 =  new JLabel("Second Player's name:");
		label2.setBounds(950, 400, 210, 50);
		label2.setFont(plain);
		
		background1 = new JLabel();
		//background1.setFont(font3);
		background1.setIcon(image);
		background1.setBackground(Color.white);
		background1.setBounds(0, 0, 1366, 768);
		
		begin = new JButton();
		begin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.print("Button Works");
				JLayeredPane panel2 = new JLayeredPane();
				panel2.setBackground(Color.yellow);
				frame.getContentPane().remove(panel);
				frame.getContentPane().add(panel2);
				frame.revalidate();
				//label.setText("HI");
			}
		});
		//begin.addActionListener(new MyButtonListener());
		begin.setOpaque(false);
		begin.setContentAreaFilled(false);
		begin.setBorderPainted(false);
		begin.setFocusPainted(false);
		begin.setFont(font5);
		begin.setText("Start");
		begin.setBounds(465,570,175,70);
		
		quit = new JButton();
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.print("Button Works");
				JLayeredPane panel2 = new JLayeredPane();
				panel2.setBackground(Color.yellow);
				frame.getContentPane().remove(panel);
				frame.getContentPane().add(panel2);
				frame.revalidate();
				//label.setText("HI");
			}
		});
		//quit.addActionListener(new MyPlayListener());
		//quit.setBackground(Color.white);
		quit.setOpaque(false);
		quit.setContentAreaFilled(false);
		quit.setBorderPainted(false);
		quit.setFocusPainted(false);
		quit.setFont(font5);
		quit.setText("Quit");
		quit.setBounds(726,570,175,70);
		
		//panel.add(background1,Integer.valueOf(0));
		panel.add(move,Integer.valueOf(0));
		//panel.add(title,Integer.valueOf(2));
		panel.add(label,Integer.valueOf(1));
		panel.add(label2,Integer.valueOf(1));
		panel.add(field,Integer.valueOf(1));
		panel.add(field2,Integer.valueOf(1));
		panel.add(begin,Integer.valueOf(1));
		panel.add(quit,Integer.valueOf(1));
		
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.getContentPane().add(panel);	
		this.getContentPane().setBackground(Color.black);
		this.setSize(1366,768);
		this.setVisible(true);
		this.setResizable(false);
		
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
			g2.fillRoundRect(  465, 570, 175, 70, 30, 30);
			g2.fillRoundRect( 726, 570, 175, 70, 30, 30);
		}
	}
	
//	public class MyButtonListener implements ActionListener{
//	public void actionPerformed(ActionEvent a) {
//		panel.setBackground(Color.white);
//		
//	}
//}
	public static void main(String[] args) {
		StartScreen start = new StartScreen();
	}
}

