import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        int width = 800;
        int height = 600;
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        JFrame frame = new JFrame("Happy Bird Simulation");
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);

        gamePanel p = new gamePanel();
        frame.add(p);
        frame.pack();
        p.requestFocus();
        frame.setVisible(true);


    }
}