package Test;

import javax.swing.*;

public class TestMapSwingPanel {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("JXMapViewer Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            // Add your MapSwingPanel instance
            frame.add(new Swing.MapGetCoordinates());

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
