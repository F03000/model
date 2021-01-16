import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JModel extends Frame {

    private double scale = 10;

    public JModel() {
        super("AWTPainting");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        JButton buttonPlus = new JButton("+");
        buttonPlus.addActionListener(actionEvent -> {
            scale /= 10;
            repaint();
        });
        buttonSettings(buttonPlus);
        add(buttonPlus);
        JButton buttonMinus = new JButton("-");
        buttonMinus.addActionListener(actionEvent -> {
            scale *= 10;
            repaint();
        });
        buttonSettings(buttonMinus);
        add(buttonMinus);
        setLayout(new FlowLayout());
        setSize(500, 500);
    }

    private void buttonSettings(JButton button) {
        button.setMargin(new Insets(10, 10, 10, 10));
        button.setVerticalAlignment(SwingConstants.TOP);
        button.setHorizontalAlignment(SwingConstants.RIGHT);
        button.setHorizontalTextPosition(SwingConstants.LEFT);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setPreferredSize(new Dimension(40, 40));
        button.setBackground(Color.gray);
    }

    public void paint(Graphics g) {
        new PlotPanel(scale).paintFunction(g);
    }

    public static void main(String[] args) {
        new JModel().setVisible(true);
    }
}