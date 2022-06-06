package GUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import engine.PriorityQueue;

import javax.swing.*;

import controller.GameController;
import model.abilities.Ability;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.effects.Effect;
import model.world.Champion;
import model.world.Cover;

public class editingBoard extends JFrame {

    JLabel champion, player1, player2, info;
    JTextArea queue, abilitiesInfo, effectsInfo;
    JPanel abilitiesPanel, effectsPanel, queuePanel;
    JLayeredPane panel = new JLayeredPane();
    JFrame frame = this;
    baseBackground grass = new baseBackground();
    ArrayList<Champion> team1,team2;
    ArrayList<Ability> abilities;
    ArrayList<Effect> appliedEffects;
    GameController controller;
    Object[][] board;
    PriorityQueue turnOrder;


    public editingBoard(GameController controller) {
        this.controller = controller;
        turnOrder = this.controller.getCurrentGame().getTurnOrder();
        team1 = controller.getTeam1();
        team2 = controller.getTeam2();
        grass.setSize(1366,768);
        info =new JLabel("");
        //info.setText("<html> Current Champion: <br>" +  + "Current HP</html>");
        info.setBounds(5,20,170,130);
        info.setVerticalAlignment(JLabel.TOP);
        info.setOpaque(true);
        info.setBackground(Color.white);

        abilitiesPanel = new JPanel();
        abilitiesPanel.setBounds(30, 180,225,500);
        abilitiesPanel.setBackground(Color.white);
        abilitiesPanel.setOpaque(true);

        effectsPanel = new JPanel();
        effectsPanel.setBounds(1100,284,225,220);
        effectsPanel.setBackground(Color.WHITE);
        effectsPanel.setOpaque(true);

        JLabel abilitiesLabel = new JLabel("<html><p style=\"font-size:14pt\">Abilities: </p></html>");
        JLabel effectsLabel = new JLabel("<html><p style=\"font-size:14pt\">Applied Effects: </p></html>");

        abilitiesInfo = new JTextArea();
        abilitiesInfo.setEditable(false);

        effectsInfo = new JTextArea();
        effectsInfo.setEditable(false);

        abilitiesPanel.add(abilitiesLabel);
        abilitiesPanel.add(abilitiesInfo);

        effectsPanel.add(effectsLabel);
        effectsPanel.add(effectsInfo);

        //abilitiesAndEffects.add(effectsInfo);

        queuePanel = new JPanel(new GridLayout(1,2));
        queuePanel.setBounds(185,20,145,130);
        queuePanel.setBackground(Color.WHITE);
        queuePanel.setOpaque(true);

        JLabel queueLabel = new JLabel("Turn Order:");
        queueLabel.setVerticalAlignment(JLabel.TOP);

        queue = new JTextArea();
        queue.setEditable(false);
        queue.setBackground(Color.WHITE);
        queue.setOpaque(true);

        queuePanel.add(queueLabel);
        queuePanel.add(queue);

        board = controller.getCurrentGame().getBoard();

        drawBoard(board);

        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.getContentPane().add(panel);
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
                ImageIcon cover = new ImageIcon("assets/characters/128/Cover_grass.png");
                //button.setBorderPainted(false);

                if (board[i][j] == null) {
                    panel2.add(button);
                }

                else if (board[i][j] instanceof Cover) {
//					add cover to board
                    button.setIcon(cover);
                    panel2.add(button);
                    button.addMouseMotionListener(new MouseMotionListener() {
                        @Override
                        public void mouseDragged(MouseEvent e) {

                        }

                        @Override
                        public void mouseMoved(MouseEvent e) {

                        }
                    });
                }

                else if (board[i][j] instanceof Champion) {
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
                        //info.setText("<html> Current Champion <br> Current HP</html>");
                        //}
                        //else {queue.setText("Leader "+ champion.getLocation()+"\n"+"Current HP");
                        //queue.setBounds(120,i+100,300,50);//}
                        //frame.getContentPane().add(panel2);
                        displayQueue(queue,info);
                        frame.revalidate();
                        //if(leader.getText()!="" && queue.getText()!="") forward.setEnabled(true);
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


        // placing each player's champions for display
        int j =1100;
        for (int ind = 0; ind < 6; ind++) {
            j = (ind==3? 1100 : j);
            Champion realChampion;

            if(ind <= 2){
                ImageIcon btn = new ImageIcon("assets/characters/64/" + team1.get(ind).getName() + ".png");
                champion = new JLabel(btn);
                realChampion = team1.get(ind);
            }
            else{
                ImageIcon btn = new ImageIcon("assets/characters/64/" + team2.get(ind-3).getName() + ".png");
                champion = new JLabel(btn);
                realChampion = team2.get(ind-3);
            }

            abilities = realChampion.getAbilities();
            appliedEffects = realChampion.getAppliedEffects();

            int i = (ind<3? 100 : 200);
            champion.setBounds(j,i,64,64);
            champion.addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent e) {}

                @Override
                public void mouseMoved(MouseEvent e) {
                    //champion = (JLabel) e.getSource();
                    //if (i==100) {
                    String type = getType(realChampion.getClass().getName());
                    info.setText("<html>" + realChampion.getName() + "<br>Type: " + type + "<br>Current HP: "
                            + realChampion.getCurrentHP() + "<br>Mana: " + realChampion.getMana() + "<br>Action Points: " +
                            realChampion.getCurrentActionPoints() + "<br>Attack Damage: " + realChampion.getAttackDamage() +
                            "<br>Attack Range: " + realChampion.getAttackRange() + "<br>Leader? " + isLeader(realChampion) + "</html>");
                    abilitiesInfo.setText("");
                    effectsInfo.setText("");
                    displayAbilitiesAndEffects(realChampion,abilitiesInfo,effectsInfo);


                    //}
                    //else {queue.setText("Leader "+ champion.getLocation()+"\n"+"Current HP");
                    //queue.setBounds(120,i+100,300,50);//}
                    //frame.getContentPane().add(panel2);
                    frame.revalidate();
                    frame.repaint();
                    //if(leader.getText()!="" && queue.getText()!="") forward.setEnabled(true);
                }
            });
            panel.add(champion,Integer.valueOf(0));
            j+=75;
        }

        JPanel p1 = new JPanel();
        p1.setBounds(1080, 60, 90, 35);
        p1.setBackground(Color.white);

        JPanel leader1Ability = new JPanel();
        leader1Ability.setBounds(1180,60,150,35);
        leader1Ability.setBackground(Color.WHITE);

        JLabel ability1Used = new JLabel();
        if(controller.getCurrentGame().isFirstLeaderAbilityUsed()) ability1Used.setText("Leader's ability used");
        else ability1Used.setText("Leader's ability not used");
        ability1Used.setBounds(1180,60,150,35);
        ability1Used.setHorizontalAlignment(JLabel.CENTER);

        player1 =  new JLabel(controller.getPlayer1().getName() + "'s team");
        player1.setBounds(1080, 60, 100, 30);
        player1.setHorizontalAlignment(JLabel.LEFT);

        JPanel p2 = new JPanel();
        p2.setBounds(1080, 160, 90, 35);
        p2.setBackground(Color.white);

        JPanel leader2Ability = new JPanel();
        leader2Ability.setBounds(1180,160,150,35);
        leader2Ability.setBackground(Color.WHITE);

        JLabel ability2Used = new JLabel();
        if(controller.getCurrentGame().isSecondLeaderAbilityUsed()) ability2Used.setText("Leader's ability used");
        else ability2Used.setText("Leader's ability not used");
        ability2Used.setBounds(1180,160,150,35);
        ability2Used.setHorizontalAlignment(JLabel.CENTER);

        player2 =  new JLabel(controller.getPlayer2().getName() + "'s team");
        player2.setBounds(1080, 160, 100, 30);
        player2.setForeground(Color.black);
        player2.setHorizontalAlignment(JLabel.LEFT);

        displayQueue(queue, info);
        panel.add(panel2,Integer.valueOf(1));
        panel.add(player1,Integer.valueOf(2));
        panel.add(player2,Integer.valueOf(2));
        panel.add(p1,Integer.valueOf(1));
        panel.add(p2,Integer.valueOf(1));
        panel.add(leader1Ability,Integer.valueOf(1));
        panel.add(ability1Used,Integer.valueOf(2));
        panel.add(leader2Ability,Integer.valueOf(1));
        panel.add(ability2Used,Integer.valueOf(2));
        panel.add(info,Integer.valueOf(1));
        panel.add(abilitiesPanel, Integer.valueOf(1));
        panel.add(effectsPanel,Integer.valueOf(1));
        panel.add(queuePanel, Integer.valueOf(1));
        panel.add(grass,Integer.valueOf(0));

    }

    public static void main(String[] args) {
        BoardView view = new BoardView();
    }

    public class baseBackground extends JPanel{
        public void paintComponent(Graphics g) {
            ImageIcon image = new ImageIcon("assets/background/Board_grass.jpg");
            Image background = image.getImage();
            Graphics2D g2 = (Graphics2D) g;
//			GradientPaint gradient = new GradientPaint(0,0,Color.cyan,1366,768,Color.blue);
//			g2.setPaint(gradient);
//			g2.fillRect(0, 0, 1366, 768);
            g2.drawImage(background, 0, 0, 	1366, 768, null);
            //frame.pack();
            //g2.setColor(Color.red);
            //g2.drawRoundRect( 465, 570, 175, 70, 10, 10);
            ///g2.fillRoundRect(  465, 600, 175, 70, 30, 30);
            //g2.fillRoundRect( 726, 600, 175, 70, 30, 30);
        }
    }

    public void displayQueue(JTextArea queue, JLabel info){
        Champion c = (Champion) turnOrder.peekMin();
        abilities = c.getAbilities();
        appliedEffects = c.getAppliedEffects();
        String type = getType(c.getClass().getName());

        info.setText("<html> Current Champion: " + c.getName() + "<br>Type: " + type + "<br>Current HP: " +
                c.getCurrentHP() + "<br>Mana: " + c.getMana() + "<br>Action Points: " + c.getCurrentActionPoints() +
                "<br>Attack Damage: " + c.getAttackDamage() + "<br>Attack Range: " + c.getAttackRange() + "<br>Leader? " +
                isLeader(c) + "</html>");
        abilitiesInfo.setText("");
        effectsInfo.setText("");
        displayAbilitiesAndEffects(c,abilitiesInfo,effectsInfo);

        PriorityQueue temp = new PriorityQueue(6);
        queue.setText("");
        while(!turnOrder.isEmpty()){
            c = (Champion) turnOrder.peekMin();
            queue.append(c.getName() + "\n");
            temp.insert(turnOrder.remove());
        }
        while(!temp.isEmpty()) turnOrder.insert(temp.remove());
    }

    private String getType(String type){
        switch(type){
            case "model.world.Hero": type = "Hero"; break;
            case "model.world.AntiHero": type = "Anti Hero"; break;
            case "model.world.Villain": type = "Villain"; break;
            case "model.abilities.CrowdControlAbility" : type = "Crowd Control"; break;
            case "model.abilities.DamagingAbility" : type = "Damaging"; break;
            case "model.abilities.HealingAbility" : type = "Healing"; break;
            default: break;
        }
        return type;
    }

    public void displayAbilitiesAndEffects(Champion c, JTextArea aInfo, JTextArea eInfo){
        abilities = c.getAbilities();
        appliedEffects = c.getAppliedEffects();
        String type;

        for(Ability a : abilities){
            type = a.getClass().getName();
            //aInfo.setText("");
            aInfo.append("Name: " + a.getName() + "\nType: " + getType(type) + "\nArea of Effect: " + a.getCastArea() +
                    "\nCast Range: " + a.getCastRange() + "\nMana: " + a.getManaCost() + "\nAction cost: " + a.getRequiredActionPoints() +
                    "\nBase Cooldown: " + a.getBaseCooldown() + "\nCurrent Cooldown: " + a.getCurrentCooldown());
            switch (getType(type)){
                case "Healing":
                    HealingAbility healingAbility = (HealingAbility) a;
                    aInfo.append("\nHealing Amount: " + healingAbility.getHealAmount() + "\n\n");
                    break;
                case "Damaging":
                    DamagingAbility damagingAbility = (DamagingAbility) a;
                    aInfo.append("\nDamaging Amount: " + damagingAbility.getDamageAmount() + "\n\n");
                    break;
                case "Crowd Control":
                    CrowdControlAbility crowdControlAbility = (CrowdControlAbility) a;
                    aInfo.append("\nEffect: " + crowdControlAbility.getEffect().getName() + "\n\n");
                    break;
                default: break;
            }
            frame.revalidate();
        }
        if(appliedEffects.size() == 0) eInfo.append("There is no applied effects yet");
        else{
            for(Effect e: appliedEffects){

            }
        }

    }

    private String isLeader(Champion c){
        if(c == controller.getPlayer1().getLeader() || c == controller.getPlayer2().getLeader()) return "Yes";
        else return "No";
    }
}

