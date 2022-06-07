package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import controller.GameController;
import engine.Game;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.world.Champion;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
//import controller.GameController.TimeListener;
//import jdk.internal.misc.FileSystemOption;

public class cleanSelectChampions extends JFrame implements ActionListener {
	GameController control;
	Move move = new Move();
	int counter = 0;
	Timer timer;
	JPanel champs1;
	JPanel champs2;
	ImageIcon img;
	JPanel icons;
	Border noBorder,labelBorder;
	Font font,font2,font3,font4,font5,font5Big,plain,font5Small;
	JLabel test,test2;
	Champion champo;
	String currentName;
	boolean mouse = false;
	
    JLayeredPane background=new JLayeredPane(); //= this;
    
    public cleanSelectChampions(GameController control) throws IOException, FontFormatException {

    	this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);	
		this.getContentPane().setBackground(Color.black);
		this.setSize(1366,768);
		this.setVisible(true);
		this.setResizable(false);
		//TimeListener time = control.new TimeListener();
		
		current = new JButton();
		Border empty = BorderFactory.createDashedBorder(new Color(0x440931), 4, 20, 5, true);
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

        icons = new JPanel();
        icons.setLayout(new GridLayout(3,5));
        icons.setBounds(358,10,650,390);
        icons.setVisible(true);
        placeButtons(icons, control.getCurrentGame().getAvailableChampions());

        JLabel p1 = new JLabel(control.getCurrentGame().getFirstPlayer().getName());
        p1.setHorizontalAlignment(JLabel.CENTER);
        p1.setBounds(1,520,390,20);
        p1.setVisible(true);
        p1.setFont(player);
        p1.setForeground(Color.WHITE);

        JLabel p2 = new JLabel(control.getCurrentGame().getSecondPlayer().getName());
        p2.setHorizontalAlignment(JLabel.CENTER);
        p2.setBounds(962,520,390,20);
        p2.setVisible(true);
        p2.setFont(player);
        p2.setForeground(Color.WHITE);

        champs1 = new JPanel();
        champs1.setBackground(new Color(0x48142a));
        champs1.setLayout(new GridLayout(0,3,2,0));
        champs1.setBounds(1,550,390,130);
        champs1.setVisible(true);
        
        champs2 = new JPanel();
        champs2.setBackground(new Color(0x0d0f26));
        champs2.setLayout(new GridLayout(0,3,2,0));;
        champs2.setBounds(962,550,390,130);
        champs2.setVisible(true);
        
        for (int ind = 0; ind < 6; ind++) {
			Color color = (ind < 3? new Color(0x0d0f26) :  new Color(0x48142a));
			JPanel one = (ind < 3? champs1/*new Color(0,56,98)*/ : champs2);
			champion = new JButton();
			if (ind==0) champion.setBorder(BorderFactory.createLineBorder(color.red, 3));
			champion.setBackground(color);
			one.add((champion));
        }
        

        JLabel actualText = new JLabel("Select Your Champion");
        actualText.setBounds(430,430,500,75);
        actualText.setFont(selectChamp);
        actualText.setBorder(empty);
        actualText.setForeground(new Color(0x1d916f));
        actualText.setHorizontalAlignment((int) CENTER_ALIGNMENT);

        testing = new JButton();
        testing.setOpaque(false);
        testing.setContentAreaFilled(false);
        testing.setFocusPainted(false);
        testing.setBorder(BorderFactory.createLineBorder((Color.green), 1));
        testing.setBounds(86, 260, 114, 20);
        testing.setVisible(false);
        
        testing2 = new JButton();
        testing2.setContentAreaFilled(false);
        testing2.setFocusPainted(false);
        testing2.setBorder(BorderFactory.createLineBorder((Color.green), 1));
        testing2.setBounds(86, 280, 114, 20);
        testing2.setVisible(false);
        
        testing3 = new JButton();
        testing3.setContentAreaFilled(false);
        testing3.setFocusPainted(false);
        testing3.setBorder(BorderFactory.createLineBorder((Color.green), 1));
        testing3.setBounds(86, 300, 114, 20);
        testing3.setVisible(false);
        
        test2 = new JLabel("");
        test2.setFont(font3);
        test2.setForeground(Color.black);
        test2.setBounds(1015,28,330,250);
        test2.setVerticalAlignment(JLabel.TOP);
        test2.setVisible(false);
        test2.setOpaque(true);
        test2.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        test2.setBackground(new Color(103,0,0));
        
        test = new JLabel("");
        test.setFont(font3);
        test.setForeground(Color.black);
        test.setBackground(new Color(103,0,0));
        labelBorder = test.getBorder();
        test.setBounds(40,28,250,320);
        test.setVerticalAlignment(JLabel.TOP);
        test.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
				//currentGame.getAvailableChampions()timer = new Timer(2000,control.new TimeListener());
				int length=0;
				int length2=0;
				int length3=0;
				FontRenderContext frc = new FontRenderContext(new AffineTransform(),true,true);
				for(int i = 0; i < control.getCurrentGame().getAvailableChampions().size(); i++){
					if (!(control.getCurrentGame().getAvailableChampions().get(i).getName().equals(currentName))) continue;
					if (control.getCurrentGame().getAvailableChampions().get(i).getName().equals(currentName)){
						Font fadl = test.getFont().deriveFont(14f);
						length = (int) fadl.getStringBounds(control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(0).getName(), frc).getWidth() +14;
						length2 = (int) fadl.getStringBounds(control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(1).getName(), frc).getWidth() +14;
						length3 = (int) fadl.getStringBounds(control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(2).getName(), frc).getWidth() +14;	
					}
				
				if (e.getX()<=44+length && e.getX()>=44 && e.getY()>=232 && e.getY()<252 && mouse){
					testing2.setVisible(false);
					testing3.setVisible(false);
					test2.setVisible(false);
					if (counter%2==0) { testing.setBounds(90, 266, length, 20);test2.setBounds(1015,28,330,250);}
					else {testing.setBounds(1100, 266, length, 20);test2.setBounds(15,28,330,250);}
					String ability = "";
					if(control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(0) instanceof DamagingAbility)
						ability = "Damaging Amount: "+((((DamagingAbility) control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(0)).getDamageAmount())) + "";
					else if (control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(0) instanceof HealingAbility) 
						ability = "Healing Amount: "+((((HealingAbility) control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(0)).getHealAmount())) + "";
					else { if (control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(0) instanceof CrowdControlAbility) 
						ability = "Effect: "+((((CrowdControlAbility) control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(0)).getEffect().getName())) + "";}
					test2.setText("<html><h1>"
							+"<div style='text-indent:15px; font-size:24; margin-top :-40;'>"
							+control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(0).getName()
							+"</div></h1><h2><div style='font-size:18;margin-left:35;margin-top :-32;'>"+"Type: "
							+control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(0).getClass().getSimpleName()+"<br>Area Of Effect: "
							+control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(0).getCastArea()+"<br>Cast Range: "
							+control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(0).getCastRange()+"<br>Mana Cost: "
							+control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(0).getManaCost()+"<br>Attack Points Required: "
							+control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(0).getRequiredActionPoints()+"<br>Base Cool Down: "
							+control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(0).getBaseCooldown()+"<br>" + ability		
							+"</div></h2><html>");
					testing.setVisible(true);
					test2.setVisible(true);
				}
				else if (e.getX()<=44+length2 && e.getX()>=44 && e.getY()>=252 && e.getY()<272 && mouse){
					testing.setVisible(false);
					testing3.setVisible(false);
					test2.setVisible(false);
					if (counter%2==0) {testing2.setBounds(90, 284, length2, 20);test2.setBounds(1015,28,330,250);}
					else {testing2.setBounds(1100, 284, length2, 20);test2.setBounds(15,28,330,250);}
					String ability = "";
					if(control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(1) instanceof DamagingAbility)
						ability = "Damaging Amount: "+((((DamagingAbility) control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(1)).getDamageAmount())) + "";
					else if (control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(1) instanceof HealingAbility) 
						ability = "Healing Amount: "+((((HealingAbility) control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(1)).getHealAmount())) + "";
					else { if (control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(1) instanceof CrowdControlAbility) 
						ability = "Effect: "+((((CrowdControlAbility) control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(1)).getEffect().getName())) + "";}
					test2.setText("<html><h1>"
							+"<div style='text-indent:15px; font-size:24; margin-top :-40;'>"
							+control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(1).getName()
							+"</div></h1><h2><div style='font-size:18;margin-left:35;margin-top :-32;'>"+"Type: "
							+control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(1).getClass().getSimpleName()+"<br>Area Of Effect: "
							+control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(1).getCastArea()+"<br>Cast Range: "
							+control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(1).getCastRange()+"<br>Mana Cost: "
							+control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(1).getManaCost()+"<br>Attack Points Required: "
							+control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(1).getRequiredActionPoints()+"<br>Base Cool Down: "
							+control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(1).getBaseCooldown()+"<br>" + ability		
							+"</div></h2><html>");
					testing2.setVisible(true);
					test2.setVisible(true);
				}
				else if (e.getX()<=44+length3 && e.getX()>=44 && e.getY()>=272 && e.getY()<=292 && mouse){
					testing.setVisible(false);
					testing2.setVisible(false);
					test2.setVisible(false);
					if (counter%2==0) { testing3.setBounds(90, 304, length3, 20);test2.setBounds(1015,28,330,250);}
					else {testing3.setBounds(1100, 304, length3, 20);test2.setBounds(15,28,330,250);}
					String ability = "";
					if(control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(2) instanceof DamagingAbility)
						ability = "Damaging Amount: "+((((DamagingAbility) control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(2)).getDamageAmount())) + "";
					else if (control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(2) instanceof HealingAbility) 
						ability = "Healing Amount: "+((((HealingAbility) control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(2)).getHealAmount())) + "";
					else { if (control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(2) instanceof CrowdControlAbility) 
						ability = "Effect: "+((((CrowdControlAbility) control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(2)).getEffect().getName())) + "";}
					test2.setText("<html><h1>"
							+"<div style='text-indent:15px; font-size:24; margin-top :-40;'>"
							+control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(2).getName()
							+"</div></h1><h2><div style='font-size:18;margin-left:35;margin-top :-32;'>"+"Type: "
							+control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(2).getClass().getSimpleName()+"<br>Area Of Effect: "
							+control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(2).getCastArea()+"<br>Cast Range: "
							+control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(2).getCastRange()+"<br>Mana Cost: "
							+control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(2).getManaCost()+"<br>Attack Points Required: "
							+control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(2).getRequiredActionPoints()+"<br>Base Cool Down: "
							+control.getCurrentGame().getAvailableChampions().get(i).getAbilities().get(2).getBaseCooldown()+"<br>" + ability		
							+"</div></h2><html>");
					testing3.setVisible(true);
					test2.setVisible(true);
				}else {
					testing.setVisible(false);
					testing2.setVisible(false);
					testing3.setVisible(false);
					test2.setVisible(false);
				}
			break;}
			}
        });
        
        JButton back = new JButton("Back");
        back.setForeground(Color.red/*new Color(0xeeff00)*/);
        back.setBackground(new Color(0x440931));
        back.setBounds(412,635,100,30);
        back.setFocusPainted(false);
        back.setBorder(BorderFactory.createLineBorder(new Color(/*0x8a8a8a*/0xeeff00),3));
        back.setFont(font5Small);

        JButton start = new JButton("Next");
        start.setForeground(/*new Color(0xeeff00)*/Color.green);
        start.setBackground(new Color(0x440931));
        start.setBounds(843,635,100,30);
        start.setFocusPainted(false);
        start.setEnabled(false);
        start.setFont(font5Small);
		start.setBorder(BorderFactory.createLineBorder(new Color(0x8a8a8a),3));
		
        ok = new JButton("OK");
        ok.setForeground(new Color(0x4cbdf0));
        ok.setOpaque(true);
        ok.setBackground(new Color(0x11173c)/*new Color(0,56,98)*/);
        ok.setBounds(635,530,100,100);
        ok.setFocusPainted(false);
        ok.setEnabled(false);
        ok.setFont(font5Big);
        Border border1 = ok.getBorder();
        ok.setBorder(BorderFactory.createLineBorder(new Color(0x8a8a8a),3));
        Border border = ok.getBorder();
        ok.addActionListener(control.new SelectListener());
        ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				test.setOpaque(false);
				test.setBorder(labelBorder);
				test.setName(current.getName());
				switch (counter) {
				case 0:
					((JButton)(champs1.getComponent(0))).setBorder(border1);
					((JButton)(champs2.getComponent(0))).setBorder(BorderFactory.createLineBorder(Color.red, 3));
					break;
				case 1:
					((JButton)(champs2.getComponent(0))).setBorder(border1);
					((JButton)(champs1.getComponent(1))).setBorder(BorderFactory.createLineBorder(Color.red, 3));
					break;
				case 2:
					((JButton)(champs1.getComponent(1))).setBorder(border1);
					((JButton)(champs2.getComponent(1))).setBorder(BorderFactory.createLineBorder(Color.red, 3));					
					break;
				case 3:
					((JButton)(champs2.getComponent(1))).setBorder(border1);
					((JButton)(champs1.getComponent(2))).setBorder(BorderFactory.createLineBorder(Color.red, 3));
					break;
				case 4:
					((JButton)(champs1.getComponent(2))).setBorder(border1);
					((JButton)(champs2.getComponent(2))).setBorder(BorderFactory.createLineBorder(Color.red, 3));
					break;
				case 5:
					((JButton)(champs2.getComponent(2))).setBorder(border1);
					break;
				default :
					return;
			}	
				if (counter%2==1) test.setBounds(40,28,250,320);
				else test.setBounds(1050,28,250,320);
				
				mouse = false;
				current.setBorder(noBorder);
				current.setName("Disabled");
				counter+=1;
				
				if (counter ==6) {
					current.setEnabled(false);
					test.setText("");
					start.setEnabled(true);
					start.setBorder(BorderFactory.createLineBorder(new Color(/*0x8a8a8a*/0xeeff00),3));
				}
				else
					current.setEnabled(false);
				test.setText("");
				ok.setEnabled(false);
				ok.setBorder(border);
				current.setForeground(Color.green);
			}
        	
        });
        
        background.add(icons);
        background.add(move,Integer.valueOf(0));
        background.add(ok,Integer.valueOf(1));
        background.add(back,Integer.valueOf(1));
        background.add(start,Integer.valueOf(1));
        background.add(test,Integer.valueOf(1));
        background.add(test2,Integer.valueOf(1));
        background.add(actualText,Integer.valueOf(1));
        background.add(p1,Integer.valueOf(1));
        background.add(p2,Integer.valueOf(1));
        background.add(testing,Integer.valueOf(2));
        background.add(testing2,Integer.valueOf(2));
        background.add(testing3,Integer.valueOf(2));
        background.add(champs1,Integer.valueOf(2));
        background.add(champs2,Integer.valueOf(2));
        
        revalidate();
        repaint();
        
    }

    private void placeButtons(JPanel north, ArrayList<Champion> Champions) throws IOException {
    	
        for(int i = 0; i < 15; i++){
            ImageIcon btn = new ImageIcon("assets/ui/button_" + Champions.get(i).getName() + ".png");
            JButton button = new JButton(btn);
            button.setName((Champions.get(i).getName()));
            noBorder = button.getBorder();
            button.setBackground(new Color(103,0,0));
            button.addMouseListener(new MouseListener() {

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
					if ((!mouse || (e.getSource()==current)) && ((JButton)(e.getSource())).isEnabled() == true && counter != 6) {
						System.out.print("Enter");
						
					test.setOpaque(true);
					test.setBorder(BorderFactory.createLineBorder(Color.black, 5));
					
					if (counter%2==0) test.setBounds(40,28,250,320);
					else test.setBounds(1050,28,250,320);
					//test.setText(getName());
					
					for(int i = 0; i < Champions.size(); i++){
						if (Champions.get(i).getName().equals(((JButton) (e.getSource())).getName())){
							test.setText("<html><h1>"
							+"<div style='text-indent:15px; font-size:24; margin-top :-40;'>"
							+Champions.get(i).getName()
							+"</div></h1><h2><div style='font-size:18;margin-left:35;margin-top :-32;'>"+"Type: "
							+Champions.get(i).getClass().getSimpleName()+"<br>Mana: "
							+Champions.get(i).getCurrentHP()+"<br>Health Points: "
							+Champions.get(i).getMana()+"<br>Action Points: "
							+Champions.get(i).getCurrentActionPoints()+"<br>Attack Points: "
							+Champions.get(i).getAttackDamage()+"<br> Attack Range: "
							+Champions.get(i).getAttackRange()+"<br>Abilities:"		
							+"</div><div style='font-size:14; margin-left:50; margin-top:8;'>"
							+Champions.get(i).getAbilities().get(0).getName()+"<br>"
							+Champions.get(i).getAbilities().get(1).getName()+"<br>"
							+Champions.get(i).getAbilities().get(2).getName()+"</div>"
							+"</div></h2><html>");
            			}
					}
				}
			}
				@Override
				public void mouseExited(MouseEvent e) {
					if(e.getSource()!=current &&  !mouse) {
					test.setText(""); test.setOpaque(false);
					test.setBorder(labelBorder);}
					// TODO Auto-generated method stub
					
				}
            	
            });
            button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				  if (counter<6) {
					current.setBorder(noBorder);
					((JButton) (e.getSource())).setBorder(BorderFactory.createLineBorder(Color.green, 3));
					mouse = true;
					if (counter%2==0) test.setBounds(40,28,250,320);
					else test.setBounds(1050,28,250,320);
					
					for(int i = 0; i < Champions.size(); i++){
						if (Champions.get(i).getName().equals(((JButton) (e.getSource())).getName())){
							currentName = (Champions.get(i).getName());
							test.setText("<html><h1>"
							+"<div style='text-indent:15px; font-size:24; margin-top :-40;'>"
							+Champions.get(i).getName()
							+"</div></h1><h2><div style='font-size:18;margin-left:35;margin-top :-32;'>"+"Type: "
							+Champions.get(i).getClass().getSimpleName()+"<br>Mana: "
							+Champions.get(i).getCurrentHP()+"<br>Health Points: "
							+Champions.get(i).getMana()+"<br>Action Points: "
							+Champions.get(i).getCurrentActionPoints()+"<br>Attack Points: "
							+Champions.get(i).getAttackDamage()+"<br> Attack Range: "
							+Champions.get(i).getAttackRange()+"<br>Abilities:"		
							+"</div><div style='font-size:14; margin-left:50; margin-top:8;'>"
							+Champions.get(i).getAbilities().get(0).getName()+"<br>"
							+Champions.get(i).getAbilities().get(1).getName()+"<br>"
							+Champions.get(i).getAbilities().get(2).getName()+"</div>"
							+"</div></h2><html>");
							break;
            			}
            				;
					}
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
				}}
				
		});
            button.setFocusPainted(false);
            north.add(button);
        }

    }

    public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}

	public JButton getChampion() {
		return champion;
	}

	public void setChampion(JButton champion) {
		this.champion = champion;
	}

	public JButton getCurrent() {
		return current;
	}

	public void setCurrent(JButton current) {
		this.current = current;
	}

	public JButton getOk() {
		return ok;
	}

	public void setOk(JButton ok) {
		this.ok = ok;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public JPanel getChamps1() {
		return champs1;
	}

	public void setChamps1(JPanel champs1) {
		this.champs1 = champs1;
	}

	public JPanel getChamps2() {
		return champs2;
	}

	public void setChamps2(JPanel champs2) {
		this.champs2 = champs2;
	}

	public ImageIcon getImg() {
		return img;
	}

	public void setImg(ImageIcon img) {
		this.img = img;
	}

	public JPanel getIcons() {
		return icons;
	}

	public void setIcons(JPanel icons) {
		this.icons = icons;
	}

	public Border getNoBorder() {
		return noBorder;
	}

	public void setNoBorder(Border noBorder) {
		this.noBorder = noBorder;
	}

	public Border getLabelBorder() {
		return labelBorder;
	}

	public void setLabelBorder(Border labelBorder) {
		this.labelBorder = labelBorder;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Font getFont2() {
		return font2;
	}

	public void setFont2(Font font2) {
		this.font2 = font2;
	}

	public Font getFont3() {
		return font3;
	}

	public void setFont3(Font font3) {
		this.font3 = font3;
	}

	public Font getFont4() {
		return font4;
	}

	public void setFont4(Font font4) {
		this.font4 = font4;
	}

	public Font getFont5() {
		return font5;
	}

	public void setFont5(Font font5) {
		this.font5 = font5;
	}

	public Font getFont5Big() {
		return font5Big;
	}

	public void setFont5Big(Font font5Big) {
		this.font5Big = font5Big;
	}

	public Font getPlain() {
		return plain;
	}

	public void setPlain(Font plain) {
		this.plain = plain;
	}

	public Font getFont5Small() {
		return font5Small;
	}

	public void setFont5Small(Font font5Small) {
		this.font5Small = font5Small;
	}

	JButton champion,current,ok,testing,testing2,testing3;
	public JButton getTesting() {
		return testing;
	}
	
	public void setTesting(JButton testing) {
		this.testing = testing;
	}
	
	public JButton getTesting2() {
		return testing2;
	}
	
	public void setTesting2(JButton testing2) {
		this.testing2 = testing2;
	}
	
	public JButton getTesting3() {
		return testing3;
	}
	
	public void setTesting3(JButton testing3) {
		this.testing3 = testing3;
	}
	public JLabel getTest() {
		return test;
	}

	public void setTest(JLabel test) {
		this.test = test;
	}

	public JLabel getTest2() {
		return test2;
	}

	public void setTest2(JLabel test2) {
		this.test2 = test2;
	}

	public Champion getChampo() {
		return champo;
	}

	public void setChampo(Champion champo) {
		this.champo = champo;
	}

	public boolean isMouse() {
		return mouse;
	}

	public void setMouse(boolean mouse) {
		this.mouse = mouse;
	}

//	public JLayeredPane getBackground() {
//		return background;
//	}

	public String getCurrentName() {
		return currentName;
	}

	public void setCurrentName(String currentName) {
		this.currentName = currentName;
	}

	public void setBackground(JLayeredPane background) {
		this.background = background;
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
