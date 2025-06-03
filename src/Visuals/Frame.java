package Visuals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Frame extends JFrame {
    public ZBuffer panel;
    public Frame() {
        super("Four Dimentions");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        panel = new ZBuffer();
        add(panel);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                Keys.keyPressed(e);
            }
            public void keyReleased(KeyEvent e) {
                Keys.keyReleased(e);
            }
        });
        Timer timer = new Timer(1, e -> {
            panel.tick();
        });
        timer.start();
        setUndecorated(true);
        GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .setFullScreenWindow(this);
        setVisible(true);
    }
}
