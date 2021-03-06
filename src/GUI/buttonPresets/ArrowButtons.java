package GUI.buttonPresets;

import java.beans.ConstructorProperties;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import controller.GameController;
import model.world.Direction;

public class ArrowButtons {
    private final int BUTTON_WIDTH = 60;
    private final int BUTTON_HEIGHT = 50;
    private final int OFFSET = 5;
    private final int GRID_WIDTH = 2 * BUTTON_WIDTH + BUTTON_HEIGHT + 2 * OFFSET;
    private final int GRID_HEIGHT = 2 * BUTTON_WIDTH + BUTTON_HEIGHT + 2 * OFFSET;

    private GameController controller;
    private ArrayList<JButton> buttonGrid;
    
    /**
     * Creates an arrow button grid with text.
     * Grid button order goes in same order as "Direction" enum
     * 
     * @param controller is needed to link to proper listeners.
     * @param type type of button corresponding to "arrowButtonTypes" enum.
     * @param abilityIndex is the index of the ability to be cast in the champions arrayList of abilities.
     */
    @ConstructorProperties({"text"})
    public ArrowButtons(GameController controller, ArrowButtonTypes type, int abilityIndex) {
        this.controller = controller;
        buttonGrid = initializeGrid(controller, type, null, abilityIndex);
    }
    /**
     * Creates an arrow button grid with text.
     * Grid button order goes in same order as "Direction" enum
     * 
     * @param controller is needed to link to proper listeners.
     * @param type type of button corresponding to "arrowButtonTypes" enum.
     */
    @ConstructorProperties({"text"})
    public ArrowButtons(GameController controller, ArrowButtonTypes type) {
        this.controller = controller;
        buttonGrid = initializeGrid(controller, type, null, 0);
    }
    /**
     * Creates an arrow button grid with custom icons.
     * Grid button order goes in same order as "Direction" enum
     * 
     * @param controller is needed to link to proper listeners.
     * @param type type of button corresponding to "arrowButtonTypes" enum.
     * @param icons arrayList of icons.
     * @param abilityIndex is the index of the ability to be cast in the champions arrayList of abilities.
     */
    @ConstructorProperties({"icon"})
    public ArrowButtons(GameController controller, ArrowButtonTypes type, ArrayList<Icon> icons, int abilityIndex) {
        this.controller = controller;
        buttonGrid = initializeGrid(controller, type, icons, abilityIndex);
    }
    /**
     * Creates an arrow button grid with custom icons.
     * Grid button order goes in same order as "Direction" enum
     * 
     * @param controller is needed to link to proper listeners.
     * @param type type of button corresponding to "arrowButtonTypes" enum.
     * @param icons arrayList of icons.
     */
    @ConstructorProperties({"icon"})
    public ArrowButtons(GameController controller, ArrowButtonTypes type, ArrayList<Icon> icons) {
        this.controller = controller;
        buttonGrid = initializeGrid(controller, type, icons, 0);
    }
    
    // Create arrow buttons and link to appropriate listeners in controller
    private static ArrayList<JButton> initializeGrid(GameController controller, ArrowButtonTypes type, ArrayList<Icon> icons, int abilityIndex) {
        ArrayList<JButton> grid = new ArrayList<JButton>();

        // Iterate over different directions and create a button for each
        int i = 0;
        for (Direction direction : Direction.values()) {
            JButton btn = new JButton(((icons == null) ? direction.toArrow() : null), ((icons == null) ? null : icons.get(i)));
            
            // Attach appropriate listener and name
            switch (type) {
                case ATTACK: {
                    btn.setName("attack|" + "null|" + direction.toString());
                    btn.addActionListener(controller.new AttackListener());
                    break;
                }
                case CAST_ABILITY: {
                    btn.setName("cast|" + abilityIndex + "|" + direction.toString());
                    btn.addActionListener(controller.new CastListener());
                    break;
                }
                case MOVE: {
                    btn.setName("move|" + "null|" + direction.toString());
                    btn.addActionListener(controller.new MoveListener());
                    break;
                }
                default:
                    break;
            }
            btn.setOpaque(true);
            btn.setFocusPainted(false);
            btn.setBackground(new Color(0x20390e));
            btn.setForeground(new Color(0xb6b884));
            btn.setBorder(BorderFactory.createLineBorder(Color.black,5));
            grid.add(btn);
            i++;
        }

        JButton backBtn = new JButton("B");
        //backBtn.setFont(backBtn.getFont().deriveFont(Font.BOLD, 12));
        backBtn.setName("close|null|back");
        backBtn.setOpaque(true);
        backBtn.setBackground(new Color(0x20390e));
        backBtn.setForeground(new Color(0xb6b884));
        backBtn.setBorder(BorderFactory.createLineBorder(Color.black,5));
        grid.add(backBtn);

        return grid;
    }

    public void placeButtons(JLayeredPane screen, int dx, int dy) {
        // Ensure buttons are in area
        if (screen.getHeight() - dy < GRID_HEIGHT || screen.getWidth() - dx < GRID_WIDTH) {
            System.out.println("[WARN] arrowButtons - placing buttons out of screen area");
        }

        for (JButton btn : buttonGrid) {
            int xPos, yPos, width, height;
            switch (btn.getName().split("\\|")[2].toLowerCase()) {
                case "up": {
                    xPos = dx + BUTTON_WIDTH + OFFSET;
                    yPos = dy;
                    width = BUTTON_HEIGHT;
                    height = BUTTON_WIDTH;
                    break;
                }
				case "down": {
                    xPos = dx + BUTTON_WIDTH + OFFSET;
                    yPos = dy + BUTTON_WIDTH + BUTTON_HEIGHT + 2 * OFFSET;
                    width = BUTTON_HEIGHT;
                    height = BUTTON_WIDTH;
                    break;
                }
				case "left": {
                    xPos = dx;
                    yPos = dy + BUTTON_WIDTH + OFFSET;
                    width = BUTTON_WIDTH;
                    height = BUTTON_HEIGHT;
                    break;
                }
				case "right": {
                    xPos = dx + BUTTON_WIDTH + BUTTON_HEIGHT + 2 * OFFSET;
                    yPos = dy + BUTTON_WIDTH + OFFSET;
                    width = BUTTON_WIDTH;
                    height = BUTTON_HEIGHT;
                    break;
                }
                case "back": {
                    xPos = dx + BUTTON_WIDTH + OFFSET;
                    yPos = dy + BUTTON_WIDTH + OFFSET;
                    width = BUTTON_HEIGHT;
                    height = BUTTON_HEIGHT;
                    break;
                }
                default: {
                    xPos = 0;
                    yPos = 0;
                    width = BUTTON_WIDTH;
                    height = BUTTON_HEIGHT;
                }
            }

            btn.setBounds(xPos, yPos, width, height);
            btn.setVisible(true);
            screen.add(btn, Integer.valueOf(6));
        }
    }

    public void placeButtons(JPanel panel) {
        // Ensure buttons are in area
        if (panel.getHeight() < GRID_WIDTH || panel.getWidth() < GRID_WIDTH) {
            System.out.println("[WARN] arrowButtons - placing buttons out of panel area");
        }

        for (JButton btn : buttonGrid) {
            int xPos, yPos, width, height;
            switch (btn.getName().split("\\|")[1].toLowerCase()) {
                case "up": {
                    xPos = panel.getWidth() + BUTTON_WIDTH + OFFSET;
                    yPos = panel.getHeight();
                    width = BUTTON_HEIGHT;
                    height = BUTTON_WIDTH;
                    break;
                }
				case "down": {
                    xPos = panel.getWidth() + BUTTON_WIDTH + OFFSET;
                    yPos = panel.getHeight() + BUTTON_WIDTH + BUTTON_HEIGHT + 2 * OFFSET;
                    width = BUTTON_HEIGHT;
                    height = BUTTON_WIDTH;
                    break;
                }
				case "left": {
                    xPos = panel.getWidth();
                    yPos = panel.getHeight() + BUTTON_WIDTH + OFFSET;
                    width = BUTTON_WIDTH;
                    height = BUTTON_HEIGHT;
                    break;
                }
				case "right": {
                    xPos = panel.getWidth() + BUTTON_WIDTH + BUTTON_HEIGHT + 2 * OFFSET;
                    yPos = panel.getHeight() + BUTTON_WIDTH + OFFSET;
                    width = BUTTON_WIDTH;
                    height = BUTTON_HEIGHT;
                    break;
                }
                default: {
                    xPos = 0;
                    yPos = 0;
                    width = BUTTON_WIDTH;
                    height = BUTTON_HEIGHT;
                }
            }

            btn.setBounds(xPos, yPos, width, height);
            btn.setVisible(true);
            panel.add(btn);
        }
    }

    public void addListener(ActionListener lsn) {
        for (JButton btn : buttonGrid) {
            if (btn.getText().equals("B")) continue;
            btn.addActionListener(lsn);
        }
    }

    public void addBackListener(ActionListener lsn) {
        buttonGrid.get(buttonGrid.size() - 1).addActionListener(lsn);
    }

    public ArrayList<JButton> getGrid() {
        return buttonGrid;
    }
}
