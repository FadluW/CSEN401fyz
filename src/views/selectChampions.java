package views;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.*;

public class selectChampions extends JFrame {


    public selectChampions() throws HeadlessException, IOException, FontFormatException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Marvel Ultimate War");
        setVisible(true);
        setBounds(20,20,1366,768);

        ImageIcon icon = new ImageIcon("assets/background/Game Start Small.jpg");
        setIconImage(icon.getImage());

        JPanel background = new JPanel();
        background.setBounds(0,0,1366,768);
        background.setBackground(Color.pink);
        add(background);
        setLayout(null);

        JPanel north = new JPanel();
        JPanel center = new JPanel();
        JPanel south = new JPanel();

        JLabel text = new JLabel("Select your champions");
        text.setHorizontalAlignment(JLabel.CENTER);
        text.setForeground(Color.cyan);
        text.setBackground(Color.black);
        text.setOpaque(true);

        InputStream is = new BufferedInputStream(new FileInputStream("assets/fonts/BlackWidowMovie-d95Rg.ttf"));
        Font blackWidowFont = Font.createFont(Font.TRUETYPE_FONT,is);
        Font selectChamp = blackWidowFont.deriveFont(64f);
        text.setFont(selectChamp);


        Border border = BorderFactory.createLineBorder(Color.black,1);
        text.setBorder(border);
        text.setBounds(50,50,100,100);


//        JButton ironMan = new JButton("Iron man");
//        ironMan.setHorizontalAlignment(JLabel.CENTER);
//        ironMan.setBackground(Color.red);
//        ironMan.setForeground(Color.black);
//        ironMan.setBounds(300,350,100,100);

        JTextArea p1 = new JTextArea("Player 1");
        JTextArea p2 = new JTextArea("Player 2");

        JPanel southWest = new JPanel();
        southWest.add(p1,BorderLayout.NORTH);

        JPanel champ11 = new JPanel();
        JPanel champ12 = new JPanel();
        JPanel champ13 = new JPanel();

        southWest.add(champ11,BorderLayout.WEST);
        southWest.add(champ12,BorderLayout.CENTER);
        southWest.add(champ13,BorderLayout.EAST);

        JPanel southEast = new JPanel();
        southEast.add(p2,BorderLayout.NORTH);

        JPanel champ21 = new JPanel();
        JPanel champ22 = new JPanel();
        JPanel champ23 = new JPanel();

        southWest.add(champ21,BorderLayout.WEST);
        southWest.add(champ22,BorderLayout.CENTER);
        southWest.add(champ23,BorderLayout.EAST);

        north.setLayout(new GridLayout(3,5));
        placeButtons(north);
        //north.add(ironMan);

        center.add(text,BorderLayout.CENTER);

        south.add(southEast,BorderLayout.EAST);
        south.add(southWest,BorderLayout.WEST);

        background.add(north);
        background.add(center);
        background.add(south);
        setResizable(false);
        revalidate();
        repaint();
    }

    private void placeButtons(JPanel north) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("assets/Champions - icons.csv"));
        String line;
        ImageIcon btn = new ImageIcon("assets/ui/button_Dr Strange.png");
        JButton button = new JButton(btn);
        button.setBackground(new Color(103,0,0));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        north.add(button);

//        while((line = br.readLine()) != null){
//            String[] championDetails = line.split(",");
//            ImageIcon btn = new ImageIcon("assets/ui/button_"+ championDetails[1]);
//            button = new JButton(championDetails[1]);
//            button.setBackground(Color.red);
//            button.setForeground(Color.black);
//            north.add(button);
//        }
    }

    public static void main(String[] args) {
        try {
            new selectChampions();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
    }
}
