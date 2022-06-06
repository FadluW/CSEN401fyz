package GUI;

import javax.swing.*;

import controller.GameController;
import engine.Game;
import model.world.Champion;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class cleanSelectChampion extends JLayeredPane implements ActionListener {
    JLayeredPane background = this;
    public cleanSelectChampion(GameController controller) throws IOException, FontFormatException {
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setTitle("Marvel Ultimate War");
//        setVisible(true);
//        setResizable(false);
//        setBounds(20,20,1366,768);

        Game game = controller.getCurrentGame();
        ArrayList<Champion> Champions = game.getAvailableChampions();


//        ImageIcon icon = new ImageIcon("assets/background/Game Start Small.jpg");
//        setIconImage(icon.getImage());

        InputStream is = new BufferedInputStream(new FileInputStream("assets/fonts/BlackWidowMovie-d95Rg.ttf"));
        Font blackWidowFont = Font.createFont(Font.TRUETYPE_FONT,is);
        Font selectChamp = blackWidowFont.deriveFont(45f);
        Font player = blackWidowFont.deriveFont(20f);

        background.setBounds(0,0,1366,768);
        setBackground(new Color(103,0,0));
        //add(background);
        setLayout(null);
        background.setVisible(true);


        JPanel icons = new JPanel();
        icons.setLayout(new GridLayout(3,5));
        icons.setBounds(358,10,650,390);
        icons.setVisible(true);
        background.add(icons);
        placeButtons(icons, Champions);

        JPanel player1 = new JPanel();
        player1.setBounds(0,510,390,170);
        player1.setVisible(true);
        player1.setBackground(Color.red);
        background.add(player1,Integer.valueOf(0));

        JLabel p1 = new JLabel(game.getFirstPlayer().getName());
        p1.setHorizontalAlignment(JLabel.CENTER);
        p1.setBounds(0,520,390,20);
        p1.setVisible(true);
        p1.setFont(player);
        p1.setForeground(Color.WHITE);
        background.add(p1,Integer.valueOf(1));

        ImageIcon venom = new ImageIcon("assets/ui/button_Venom.png");
        JLabel champ = new JLabel(venom);

        ImageIcon spiderman = new ImageIcon("assets/ui/button_Spiderman.png");
        JLabel champ2 = new JLabel(spiderman);

        ImageIcon captainAmerica = new ImageIcon("assets/ui/button_Captain America.png");
        JLabel champ3 = new JLabel(captainAmerica);


        JPanel champs1 = new JPanel();
        champs1.setBackground(new Color(0,56,98));
        champs1.setLayout(new GridLayout(0,3));
        champs1.add(champ);
        champs1.add(champ2);
        champs1.add(champ3);
        champs1.setBounds(0,550,390,130);
        champs1.setVisible(true);
        background.add(champs1,Integer.valueOf(2));

        JPanel player2 = new JPanel();
        player2.setBounds(976,510,390,170);
        player2.setVisible(true);
        player2.setBackground(Color.blue);
        background.add(player2,Integer.valueOf(0));

        JLabel p2 = new JLabel(game.getSecondPlayer().getName());
        p2.setBounds(970,520,390,20);
        p2.setHorizontalAlignment(JLabel.CENTER);
        p2.setVisible(true);
        p2.setFont(player);
        p2.setForeground(Color.WHITE);
        background.add(p2,Integer.valueOf(1));

        JPanel text = new JPanel();
        text.setBounds(430,430,500,75);
        text.setBackground(Color.GRAY);
        JLabel actualText = new JLabel("Select Your Champion");
        actualText.setFont(selectChamp);
        actualText.setForeground(Color.cyan);
        actualText.setVerticalAlignment((int) CENTER_ALIGNMENT);
        text.add(actualText);
        background.add(text);

        JPanel info1 = new JPanel();
        info1.setBounds(54,45,250,300);
        info1.setBackground(Color.orange);

        JLabel test = new JLabel("test test");
        test.setForeground(Color.black);
        info1.add(test);
        background.add(info1);

        JLabel test2 = new JLabel("test test");
        test2.setForeground(Color.black);

        JPanel info2 = new JPanel();
        info2.setBounds(1062,45,250,300);
        info2.setBackground(Color.orange);
        info2.add(test2);
        background.add(info2);

        JButton back = new JButton("Back");
        back.setForeground(Color.blue);
        back.setBackground(Color.WHITE);
        back.setBounds(410,635,100,30);
        back.setFocusPainted(false);
        back.addActionListener(controller.new backListener("start", background));
        background.add(back,Integer.valueOf(1));

        JButton start = new JButton("Start");
        start.setForeground(Color.blue);
        start.setBackground(Color.WHITE);
        start.setBounds(856,635,100,30);
        start.setFocusPainted(false);
        background.add(start,Integer.valueOf(1));

        JButton ok = new JButton("OK");
        ok.setForeground(Color.blue);
        ok.setBackground(Color.WHITE);
        ok.setBounds(633,530,100,100);
        ok.setFocusPainted(false);
        background.add(ok,Integer.valueOf(1));


        revalidate();
        repaint();
    }

    private void placeButtons(JPanel north, ArrayList<Champion> Champions) throws IOException {


        for(int i = 0; i < 15; i++){
            ImageIcon btn = new ImageIcon("assets/ui/button_" + Champions.get(i).getName() + ".png");
            JButton button = new JButton(btn);
            button.setBackground(new Color(103,0,0));
            //button.setBorderPainted(false);
            button.setFocusPainted(false);
            //button.setBorder(BorderFactory.createLineBorder(Color.black,1));
            //button.addActionListener(this);
            north.add(button);
        }

    }

    public static void main(String[] args) throws IOException, FontFormatException {
        // new cleanSelectChampion();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
