package GUI.buttonPresets;

import java.awt.Point;
import javax.swing.JButton;
import model.world.Damageable;

public class BoardObject extends JButton {
    private Point point;

    /**
     * Creates a board cell button containing a damagable.
     * 
     * @param object Cover / Champion that is contained in that cell
     */
    public BoardObject(Damageable object) {
        
    }
    /**
     * Creates a board cell button.
     * 
     */
    public BoardObject() {
        
    }
}
