package GUI;

import javax.swing.*;
import javax.swing.border.Border;

import model.world.Champion;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class cleanSelectChampions extends JFrame implements ActionListener {
	Move move = new Move();
	JButton champion,current,ok;
	int counter = 0;
	JPanel champs1;
	JPanel champs2;
	ImageIcon img;
	Font font,font2,font3,font4,font5,font5Big,plain,font5Small;
	
    JLayeredPane background=new JLayeredPane(); //= this;
    public cleanSelectChampions(ArrayList<Champion> Champions) throws IOException, FontFormatException {

    	this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);	
		this.getContentPane().setBackground(Color.black);
		this.setSize(1366,768);
		this.setVisible(true);
		this.setResizable(false);
		
		current = new JButton();
		move.setBounds(0,0,1366, 768);
		
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
//        JLabel champ3 = new JLabel(captainAmerica);JPanel champs1 = new JPanel();
        champs1 = new JPanel();
        champs1.setBackground(new Color(0,56,98));
        champs1.setLayout(new GridLayout(0,3));
		
//			//champion.addActionListener(new MyPlayListener());
//			champion.setBackground(color);
//			champion.setEnabled(false
        champs1.setBounds(1,550,390,130);
        champs1.setVisible(true);
        background.add(champs1,Integer.valueOf(2));
        
        champs2 = new JPanel();
        champs2.setBackground(new Color(103,0,0));
        champs2.setLayout(new GridLayout(0,3));;
        champs2.setBounds(962,550,390,130);
        champs2.setVisible(true);
        background.add(champs2,Integer.valueOf(2));
        
        for (int ind = 0; ind < 6; ind++) {
			Color color = (ind < 3? new Color(0x0d0f26) :  new Color(0x48142a));
			JPanel one = (ind < 3? champs1/*new Color(0,56,98)*/ : champs2);
			champion = new JButton();
			//champion.addActionListener(new MyPlayListener());
			champion.setBackground(color);
			//champion.setEnabled(false);
			one.add((champion));
        
        }

//        int j = 0;
//		for (int ind = 0; ind < 6; ind++) {
//			j = (ind == 3? 976 : j);
//			Color color = (ind < 3? Color.blue/*new Color(0,56,98)*/ : Color.red);
//			champion = new JButton();
//			//champion.addActionListener(new MyPlayListener());
//			champion.setBackground(color);
//			champion.setEnabled(false);
//			champion.setName(""+ind);
//			//int i = (ind<3? 100 : 500);
//			champion.setBounds(j,550,130,130);
////			champion.addActionListener(new ActionListener() {
////				public void actionPerformed(ActionEvent e) {
////					System.out.print("Button Works");
////					champion = (JButton) e.getSource();
////					if (i==100) {
////					leader.setText("Leader "+ champion.getLocation());
////					leader.setBounds(120,i+100,300,50);}
////					else {leader2.setText("Leader "+ champion.getLocation());
////					leader2.setBounds(120,i+100,300,50);}
////					//frame.getContentPane().add(panel2);
////					frame.revalidate();
////					if(leader.getText()!="" && leader2.getText()!="") forward.setEnabled(true);
////				}
////			});
//			background.add(champion,Integer.valueOf(1));
//			j+=130;
//		}
        
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
        JLabel actualText = new JLabel("Select Your Champion");
        actualText.setBounds(430,430,500,75);
        actualText.setFont(selectChamp);
        Border empty = BorderFactory.createDashedBorder(new Color(0x440931), 4, 20, 5, true);
        //Border empty = BorderFactory.
        actualText.setBorder(empty);
        actualText.setForeground(new Color(0x1d916f));
        actualText.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        background.add(actualText,Integer.valueOf(1));
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
        JButton back = new JButton("Back");
        back.setForeground(Color.red/*new Color(0xeeff00)*/);
        back.setBackground(new Color(0x440931));
        back.setBounds(412,635,100,30);
        back.setFocusPainted(false);
        back.setBorder(BorderFactory.createLineBorder(new Color(/*0x8a8a8a*/0xeeff00),3));
        back.setFont(font5Small);
        background.add(back,Integer.valueOf(1));
//
        JButton start = new JButton("Next");
        start.setForeground(/*new Color(0xeeff00)*/Color.green);
        start.setBackground(new Color(0x440931));
        start.setBounds(843,635,100,30);
        start.setFocusPainted(false);
        start.setEnabled(false);
        start.setFont(font5Small);
        //start.setEnabled(true);
		start.setBorder(BorderFactory.createLineBorder(new Color(0x8a8a8a),3));
        background.add(start,Integer.valueOf(1));
//
        ok = new JButton("OK");
        ok.setForeground(new Color(0x4cbdf0));
        ok.setOpaque(true);
        ok.setBackground(new Color(0x11173c)/*new Color(0,56,98)*/);
        ok.setBounds(635,530,100,100);
        ok.setFocusPainted(false);
        ok.setEnabled(false);
        ok.setFont(font5Big);
        ok.setBorder(BorderFactory.createLineBorder(new Color(0x8a8a8a),3));
        Border border = ok.getBorder();
        ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				counter+=1;
				if (counter ==6) {
					start.setEnabled(true);
					start.setBorder(BorderFactory.createLineBorder(new Color(/*0x8a8a8a*/0xeeff00),3));
				}
				else	
					current.setEnabled(false);
				ok.setEnabled(false);
				ok.setBorder(border);
				current.setForeground(Color.green);
//				current.setDisabledIcon(img);
//				current.setForeground(Color.red);
				//ok.ena
			}
        	
        });
        background.add(ok,Integer.valueOf(1));


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
				img = (ImageIcon) ((JButton) (e.getSource())).getIcon();
				switch (counter) {
					case 0:
						((JButton)(champs1.getComponent(0))).setIcon(img);
						break;
					case 1:
						((JButton)(champs2.getComponent(0))).setIcon(img);
						break;
					case 2:
						((JButton)(champs1.getComponent(1))).setIcon(img);
						break;
					case 3:
						((JButton)(champs2.getComponent(1))).setIcon(img);
						break;
					case 4:
						((JButton)(champs1.getComponent(2))).setIcon(img);
						break;
					case 5:
						((JButton)(champs2.getComponent(2))).setIcon(img);
						break;
					default :
						return;
				}
					current = ((JButton) (e.getSource()));
					ok.setEnabled(true);
					ok.setBorder(BorderFactory.createLineBorder(new Color(0x4cbdf0),5));
					//ok.setb
						
				}
				//background.get
				//counter+=1;
//				if (i==100) {
//				leader.setText("Leader "+ champion.getLocation());
//				leader.setBounds(120,i+100,300,50);}
//				else {leader2.setText("Leader "+ champion.getLocation());
//				leader2.setBounds(120,i+100,300,50);}
//				//frame.getContentPane().add(panel2);
//				frame.revalidate();
//				if(leader.getText()!="" && leader2.getText()!="") forward.setEnabled(true);
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
			//GradientPaint gradient = new GradientPaint(1366/2,768,new Color(0x590303),1366/2,0,new Color(0xf80707));
			GradientPaint gradient = new GradientPaint(1366/2,768/2+100,new Color(0x050505),1366/2,0,new Color(0x9e9e9e));
			//GradientPaint gradient = new GradientPaint(0,0,new Color(0x44000B),1366,768,new Color(0xE0455F));
			g2.setPaint(gradient);
			g2.fillRect(0, 0, 1366, 768);
			//g2.drawImage(zeina, 0, 0, 	1366, 768, null);
			//frame.pack();
			g2.setColor(new Color(0x48142a));
			//g2.drawRoundRect( 465, 570, 175, 70, 10, 10);
			g2.fillRect(1,510,390,170);
			g2.setColor(new Color(0x0d0f26));
			g2.fillRect(962,510,390,170);
			//g2.fillRoundRect( 726, 570, 175, 70, 30, 30);
		}
	}
}
