package ui;

import gamecomponents.Game;
import player.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.swing.JColorChooser;
import javax.swing.border.TitledBorder;


/*
 * @Author: BotHarmon (Jess Dempsey, Rebeca Buarque, Noemi Banal)
 */
public class Window {

    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
   
    private static DisplayText displayText = new DisplayText();
    static Map mapPanel = new Map();
    private static CommandPanel commandPanel = new CommandPanel(displayText); 

    public Window(Game game) {
        createAndShowGUI(game);
    }

    public static void addComponentsToPane(Game game, Container pane) {
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }
        
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        if (shouldFill) {
            //natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
        }

        //Setting the display constraints
        if (shouldWeightX) {
            c.weightx = 0.6;
        }
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(displayText, c);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.5;
        c.gridx = 1; //column
        c.gridy = 0;

        pane.add(commandPanel, c);

        //DECK BUTTON
        Image card = null;
        try {
            card = ImageIO.read(Window.class.getResource("/images/card.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JButton button = new JButton(new ImageIcon(card));
        //Add functionality later
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.gridx = 2;
        c.gridy = 0;
        pane.add(button, c);

        TitledBorder titledBorderMap = BorderFactory.createTitledBorder("MAP");
        mapPanel.setBorder(titledBorderMap);
        titledBorderMap.setTitleColor(Color.WHITE);
        mapPanel.setBackground(Color.black);
        c.fill = GridBagConstraints.BOTH;
        c.ipady = 70;      //make this component tall
        c.ipadx = 200;
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;

        mapPanel.initialiseCountries(); 
        c.ipady = 0;
        c.gridy = 1;
        
        pane.add(mapPanel, c);
        pane.validate();
        pane.repaint();

    }


    public static void createAndShowGUI(Game game) {
        //Create and set up the window.
        JFrame frame = new JFrame("Risk");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(game, frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}