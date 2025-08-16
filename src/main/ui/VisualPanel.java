package ui;

import javax.swing.*;
import java.awt.*;

/**
 * Panel that displays an image
 */

public class VisualPanel extends JPanel {

    // EFFECTS: Displays an image
    public VisualPanel() {
        setLayout(new BorderLayout());

        ImageIcon image = new ImageIcon("./data/language.gif");
        JLabel imageLabel = new JLabel(image);
        imageLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);

        add(imageLabel, BorderLayout.CENTER);
    }
}
