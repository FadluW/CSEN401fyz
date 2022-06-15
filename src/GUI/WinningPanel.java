package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import controller.GameController.BeginListener;
import controller.GameController.QuitListener;

public class WinningPanel extends JFrame {
	JFrame frame = this;
	JLayeredPane panelWin;
	JLayeredPane panel = new JLayeredPane();
	InputStream is;
	Font font,font2,font3,font4,font5Big,font5Small,font5,plain,blackWidowFont;
	public WinningPanel() {
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
		
		JLabel winner = new JLabel("Joey!");
		winner.setForeground(new Color(0x289baf));
		//winner.setText("<html><div style='font-size:20;'>Joey!</div></html>");
		winner.setBounds(175,90,150, 60);
		winner.setHorizontalAlignment(0);
		winner.setFont(font5);
		winner.setVisible(true);
		
		JButton quit = new JButton();
		//quit.addActionListener(control.new QuitListener());
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
		//begin.addActionListener(control.new BeginListener());
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
		panel.setBounds(0,0,1366,768);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);	
		frame.getContentPane().setBackground(Color.black);
		frame.setSize(1366,768);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
//	public static void main(String[] args) {
//		new WinningPanel();
//	}
}
