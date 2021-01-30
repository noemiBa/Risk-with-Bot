package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;

/*
 * @Author: BotHarmon (Jess Dempsey, Rebeca Buarque, Noemi Banal)
 */
public class Window extends JFrame
{
    /**
     *
     */
    private static final long serialVersionUID = 2L;
    /**
     * Frame constants
     */
    private static final int FRAME_WIDTH = 1100;
    private static final int FRAME_HEIGHT = 700;

    /**
     * Constructor
     */
    public Window() {}

    /**
     * GUI components
     */
    private JPanel mapPanel;
    private JPanel displayText;
    private JPanel readText;

    /**
     * Helper method to build the GUI
     */
    private void createGUI()
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        Window.centerFrame(frame);
        frame.setTitle("Risk");
        frame.setResizable(false);
        frame.setLayout(new FlowLayout());
        frame.setBackground(Color.lightGray);

        //adding the mapPanel to the main window.
        mapPanel = new JPanel();
        mapPanel.setBackground(Color.BLACK);
        mapPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Risk Board"));
        mapPanel.setLayout(new BorderLayout(0,0));
        Map map = new Map();
        map.initialiseCountries();
        mapPanel.add(map, BorderLayout.CENTER);
        frame.add(mapPanel);
        frame.setVisible(true);
    }

    /*
     * Helper method to centre the frame regardless of the screen dimensions.
     * @param: frame, the frame created in the main method.
     */
    public static void centerFrame(JFrame frame)
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        final int X = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        final int Y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(X, Y);
    }

    /*
     * The main launcher method
     * @param args Unused
     */
    public static void main()
    {
        Window window = new Window();
        window.createGUI();
    }

}
