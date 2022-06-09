package GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import controller.GameController;
import engine.Game;
import model.world.Champion;

public class LeaderSelection extends JLayeredPane{
	JLayeredPane panel=this;
	JButton champion,forward,behind;
	Move move;
	Font font,font2,font3,font4,plain,font5,font5Big,font5Small;
	JTextField field,field2;
	JLabel label,label2,title,background1,leader,leader2,test;
	Image image;
	GameController control;
	ImageIcon btn;
	Border labelBorder;
	ArrayList<Champion> team;
	//JLayeredPane panel = this;
	//GameController control;
	
	public LeaderSelection(GameController control, Game game ) throws FontFormatException, IOException {
		//panel = new JLayeredPane();
		//add(background);
		//this.setBackground(new Color(103,0,0));
		this.control = control;
		panel.setBounds(0,0,1366,768);
		panel.setBackground(new Color(122,66,148));
		panel.setLayout(null);
		panel.setVisible(true);
		panel.setOpaque(true);
		leader=new JLabel("");
		move = new Move();
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
		font4 = new Font("serif", Font.BOLD,35);

        InputStream is = new BufferedInputStream(new FileInputStream("assets/fonts/BlackWidowMovie-d95Rg.ttf"));
        Font blackWidowFont = Font.createFont(Font.TRUETYPE_FONT,is);
        Font selectChamp = blackWidowFont.deriveFont(45f);
        Font player = blackWidowFont.deriveFont(20f);

        leader2=new JLabel("");
        leader.setFont(font4);
        leader.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        leader.setForeground(new Color(0xf8df82));
        leader2.setFont(font4);
        leader2.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        leader2.setForeground(new Color(0xf8df82));
        
		forward = new JButton();
		forward.addActionListener(control.new ForwardListener());
		forward.setOpaque(false);
		forward.setFocusPainted(false);
		forward.setContentAreaFilled(false);
		forward.setBackground(new Color(0x0d0f26));
		forward.setBorder(BorderFactory.createLineBorder(new Color(0xf8df82), 4, true));
		forward.setFont(font);
		forward.setBounds(1000,570,175,70);
		forward.setText("Continue..");
		forward.setForeground(new Color(0x1d916f));
		forward.setEnabled(false);
				
				
		behind = new JButton();
		behind.addActionListener(control.new BehindListener());
		behind.setOpaque(true);
		behind.setBackground(new Color(0x0d0f26));
		behind.setBorder(BorderFactory.createLineBorder(new Color(0xf8df82), 4, true));
		behind.setFocusPainted(false);
		behind.setFont(font);
		behind.setBounds(30,20,135,50);
		behind.setText("Back");
		behind.setForeground(new Color(0x1d916f));
		
		test = new JLabel("");
        test.setFont(font3);
        test.setForeground(/*Color.white*/new Color(0xf8df82));
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
					test.setOpaque(true);
					test.setBorder(BorderFactory.createLineBorder(Color.black, 5));
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
							+team.get(i).getMana()+"<br>Health Points: "
							+team.get(i).getCurrentHP()+"<br>Action Points: "
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
					System.out.println("Leader selected");
					champion = (JButton) e.getSource();
					if (i==100) {
					leader.setText(champion.getName());
					leader.setBounds(95,i+80,300,50);}
					else {leader2.setText(champion.getName() );
					leader2.setBounds(95,i+80,300,50);}
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
		
		label =  new JLabel(control.getCurrentGame().getFirstPlayer().getName()+"'s Leader:");
		label.setBounds(100, 140, 300, 50);
		label.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		label.setForeground(/*new Color(103,0,0)*/new Color(0x1d916f));
		label.setFont(font5);
		
		label2 =  new JLabel(control.getCurrentGame().getSecondPlayer().getName()+"'s Leader:");
		label2.setForeground(/*new Color(103,0,0)*/new Color(0x1d916f));
		label2.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		label2.setBounds(100, 540, 300, 50);
		label2.setFont(font5);
		
		panel.add(move,Integer.valueOf(0));
		panel.add(title,Integer.valueOf(1));
		panel.add(forward,Integer.valueOf(1));
		panel.add(label,Integer.valueOf(1));
		panel.add(label2,Integer.valueOf(1));
		panel.add(leader,Integer.valueOf(1));
		panel.add(leader2,Integer.valueOf(1));
		panel.add(test,Integer.valueOf(1));
		// panel.add(behind,Integer.valueOf(1));
			
		this.setSize(1366,768);
		this.setVisible(true);
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

	public JLabel getLeader() {
		return leader;
	}

	public void setLeader(JLabel leader) {
		this.leader = leader;
	}

	public JLabel getLeader2() {
		return leader2;
	}

	public void setLeader2(JLabel leader2) {
		this.leader2 = leader2;
	}
}