package GUI;

import javax.swing.*;

import model.world.Champion;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class cleanSelectChampions extends JFrame implements ActionListener {
	Move move = new Move();
	JButton champion;
	int counter = 0;
	
    JLayeredPane background=new JLayeredPane(); //= this;
    public cleanSelectChampions(ArrayList<Champion> Champions) throws IOException, FontFormatException {

    	this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);	
		this.getContentPane().setBackground(Color.black);
		this.setSize(1366,768);
		this.setVisible(true);
		this.setResizable(false);
		
		
		move.setBounds(0,0,1366, 768);

        InputStream is = new BufferedInputStream(new FileInputStream("assets/fonts/BlackWidowMovie-d95Rg.ttf"));
        Font blackWidowFont = Font.createFont(Font.TRUETYPE_FONT,is);
        Font selectChamp = blackWidowFont.deriveFont(45f);
        Font player = blackWidowFont.deriveFont(20f);

        background.setBounds(0,0,1366,768);
        this.getContentPane().setBackground(new Color(103,0,0));
        add(background);
        setLayout(null);
        background.setVisible(true);


        JPanel icons = new JPanel();
        icons.setLayout(new GridLayout(3,5));
        icons.setBounds(358,10,650,390);
        icons.setVisible(true);
        background.add(icons);
        placeButtons(icons, Champions);


        background.add(move,Integer.valueOf(0));
//
        JLabel p1 = new JLabel("Player one");
        p1.setBounds(150,520,390,20);
        p1.setVisible(true);
        p1.setFont(player);
        p1.setForeground(Color.WHITE);
        background.add(p1,Integer.valueOf(1));

//        ImageIcon venom = new ImageIcon("assets/ui/button_Venom.png");
//        JLabel champ = new JLabel(venom);
//
//        ImageIcon spiderman = new ImageIcon("assets/ui/button_Spiderman.png");
//        JLabel champ2 = new JLabel(spiderman);
//
//        ImageIcon captainAmerica = new ImageIcon("assets/ui/button_Captain America.png");
//        JLabel champ3 = new JLabel(captainAmerica);

        int j = 0;
		for (int ind = 0; ind < 6; ind++) {
			j = (ind == 3? 976 : j);
			Color color = (ind < 3? Color.blue/*new Color(0,56,98)*/ : Color.red);
			champion = new JButton();
			//champion.addActionListener(new MyPlayListener());
			champion.setBackground(color);
			champion.setEnabled(false);
			//int i = (ind<3? 100 : 500);
			champion.setBounds(j,550,130,130);
//			champion.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					System.out.print("Button Works");
//					champion = (JButton) e.getSource();
//					if (i==100) {
//					leader.setText("Leader "+ champion.getLocation());
//					leader.setBounds(120,i+100,300,50);}
//					else {leader2.setText("Leader "+ champion.getLocation());
//					leader2.setBounds(120,i+100,300,50);}
//					//frame.getContentPane().add(panel2);
//					frame.revalidate();
//					if(leader.getText()!="" && leader2.getText()!="") forward.setEnabled(true);
//				}
//			});
			background.add(champion,Integer.valueOf(1));
			j+=130;
		}
        
        JLabel p2 = new JLabel("Player two");
        p2.setBounds(1120,520,390,20);
        p2.setVisible(true);
        p2.setFont(player);
        p2.setForeground(Color.WHITE);
        background.add(p2,Integer.valueOf(1));
//
//        JPanel text = new JPanel();
//        text.setBounds(430,430,500,75);
//        text.setBackground(Color.GRAY);
//        JLabel actualText = new JLabel("Select Your Champion");
//        actualText.setFont(selectChamp);
//        actualText.setForeground(Color.cyan);
//        actualText.setVerticalAlignment((int) CENTER_ALIGNMENT);
//        text.add(actualText);
//        background.add(text);
//
//        JPanel info1 = new JPanel();
//        info1.setBounds(54,45,250,300);
//        info1.setBackground(Color.orange);
//
//        JLabel test = new JLabel("test test");
//        test.setForeground(Color.black);
//        info1.add(test);
//        background.add(info1);
//
//        JLabel test2 = new JLabel("test test");
//        test2.setForeground(Color.black);
//
//        JPanel info2 = new JPanel();
//        info2.setBounds(1062,45,250,300);
//        info2.setBackground(Color.orange);
//        info2.add(test2);
//        background.add(info2);
//
//        JButton back = new JButton("Back");
//        back.setForeground(Color.blue);
//        back.setBackground(Color.WHITE);
//        back.setBounds(410,635,100,30);
//        back.setFocusPainted(false);
//        background.add(back,Integer.valueOf(1));
//
//        JButton start = new JButton("Start");
//        start.setForeground(Color.blue);
//        start.setBackground(Color.WHITE);
//        start.setBounds(856,635,100,30);
//        start.setFocusPainted(false);
//        background.add(start,Integer.valueOf(1));
//
//        JButton ok = new JButton("OK");
//        ok.setForeground(Color.blue);
//        ok.setBackground(Color.WHITE);
//        ok.setBounds(633,530,100,100);
//        ok.setFocusPainted(false);
//        background.add(ok,Integer.valueOf(1));


        revalidate();
        repaint();
    }

    private void placeButtons(JPanel north, ArrayList<Champion> Champions) throws IOException {
        // ArrayList<String> champions = new ArrayList<>();
        // champions.add("Captain America");
        // champions.add("Deadpool");
        // champions.add("Dr Strange");
        // champions.add("Electro");
        // champions.add("Iceman");
        // champions.add("Spiderman");
        // champions.add("Ghost Rider");
        // champions.add("Hulk");
        // champions.add("Ironman");
        // champions.add("Loki");
        // champions.add("Venom");
        // champions.add("Venom");
        // champions.add("Venom");
        // champions.add("Venom");
        // champions.add("Venom");

        for(int i = 0; i < 15; i++){
            ImageIcon btn = new ImageIcon("assets/ui/button_" + Champions.get(i).getName() + ".png");
            JButton button = new JButton(btn);
            button.setBackground(new Color(103,0,0));
            button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				System.out.print("Button Works");
				champion = (JButton) e.getSource();
//				if (i==100) {
//				leader.setText("Leader "+ champion.getLocation());
//				leader.setBounds(120,i+100,300,50);}
//				else {leader2.setText("Leader "+ champion.getLocation());
//				leader2.setBounds(120,i+100,300,50);}
//				//frame.getContentPane().add(panel2);
//				frame.revalidate();
//				if(leader.getText()!="" && leader2.getText()!="") forward.setEnabled(true);
			}
		});
            //button.setBorderPainted(false);
            button.setFocusPainted(false);
            //button.setBorder(BorderFactory.createLineBorder(Color.black,1));
            //button.addActionListener(this);
            north.add(button);
        }

    }

    public static void main(String[] args) throws IOException, FontFormatException {
        // new cleanSelectChampions();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

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
			g2.setColor(Color.red);
			//g2.drawRoundRect( 465, 570, 175, 70, 10, 10);
			g2.fillRect(0,510,390,170);
			g2.setColor(Color.blue);
			g2.fillRect(976,510,390,170);
			//g2.fillRoundRect( 726, 570, 175, 70, 30, 30);
		}
	}
}
