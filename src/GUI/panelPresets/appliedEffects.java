package GUI.panelPresets;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import model.effects.*;
import model.world.Champion;

public class appliedEffects extends JPanel {
    private final int PANEL_WIDTH = 225;
    private final int PANEL_HEIGHT = 190;

    private Champion champion;
    private int currentPage;
    private JPanel panel = this;

    public appliedEffects(Champion champion) {
        renderPanel(champion, 0);
    }
    
    public appliedEffects(Champion champion, int index) {
        renderPanel(champion, index);
    }

    private void renderPanel(Champion champion, int pageIndex){
        this.champion = champion;
        this.currentPage = pageIndex;
        Effect currEffect = ((champion.getAppliedEffects().size() > 0) ?  champion.getAppliedEffects().get(pageIndex) : null );

        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.white);

        // Add effect name to the top
        panel.add(new JLabel("Applied Effects"), BorderLayout.NORTH);

        // Add effect info to the center
        panel.add(new JLabel(getInfo(currEffect)) , BorderLayout.CENTER);

        System.out.println(getInfo(currEffect));
        // Add arrows to the bottom
        panel.add(createArrows(), BorderLayout.SOUTH);
        this.setVisible(true);
    }

    // Return string info of effect
    private String getInfo(Effect e) {
        if (e == null) return "No applied effects on " + champion.getName();

        String r = "";

        r += "Name: " + e.getName() + "\n";
        r += "Duration: " + e.getDuration() + "\n";
        r += "Type: " + e.getType();

        return r;
    }

    // Create arrow buttons + page number
    private JPanel createArrows() {
        JPanel arrowPanel = new JPanel();
        arrowPanel.setLayout(new GridLayout(1, 3, 5, 0));

        JButton back = new JButton("<");
        back.setName("page|" + (currentPage - 1));
        back.addActionListener(new ButtonListen());
        if (currentPage < 0) back.setEnabled(false);

        JButton next = new JButton(">");
        next.setName("page|" + (currentPage - 1));
        next.addActionListener(new ButtonListen());
        if (currentPage >= champion.getAppliedEffects().size()) next.setEnabled(false);

        JLabel index = new JLabel((currentPage + 1) + " / " + champion.getAppliedEffects().size());
        index.setHorizontalAlignment(JLabel.CENTER);

        arrowPanel.add(back);
        arrowPanel.add(index);
        arrowPanel.add(next);
        return arrowPanel;
    }

    private class ButtonListen implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] buttonID = ((JButton) e.getSource()).getName().split("\\|");
             
            panel = new appliedEffects(champion, Integer.parseInt(buttonID[1]));
        }
        
    } 
    
    public int getHeight() {
        return PANEL_HEIGHT;
    }
    
    public int getWidth() {
        return PANEL_WIDTH;
    }
}
