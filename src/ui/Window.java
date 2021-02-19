package ui;

import com.botharmon.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.border.TitledBorder;
import javax.swing.AbstractAction;


/*
 * @Author: BotHarmon (Jess Dempsey, Rebeca Buarque, Noemi Banal)
 */
public class Window {
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;

    private static DisplayText displayText;
    private static CommandPanel commandPanel;

    public Window(Game risk) {
        displayText = new DisplayText();
        commandPanel = new CommandPanel(risk.getActivePlayers());
        createAndShowGUI(risk);
    }

    // used to print the current message for each player's turn
    public void getTextDisplay(String message) {
        displayText.getTextDisplay(message);
    }

    // used to print an error message in response to the players' erroneous input
    public void sendErrorMessage(String message) {
        displayText.sendErrorMessage(message);
    }

    // takes the current instruction for each player
    public String getCommand() {
        return commandPanel.getCommand();
    }

    public static void createAndShowGUI(Game risk) {
        //Create and set up the window.
        JFrame frame = new JFrame("Risk");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(risk, frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void addComponentsToPane(Game risk, Container pane) {
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }

        pane.setLayout(new GridBagLayout());
        GridBagConstraints cDisplay = new GridBagConstraints();
        GridBagConstraints cMap = new GridBagConstraints();
        GridBagConstraints cButton = new GridBagConstraints();
        GridBagConstraints cCommand = new GridBagConstraints();

        //Setting the display constraints
        if (shouldWeightX) {
            cDisplay.weightx = 1;
            cMap.weightx = 1;
            cCommand.weightx = 1;
            cButton.weightx = 1;
        }

        cDisplay.fill = GridBagConstraints.BOTH;
        cDisplay.gridx = 0;
        cDisplay.gridy = 0;
        cDisplay.gridheight = 7;
        cDisplay.ipadx = 100;


        pane.add(displayText, cDisplay);

        cCommand.fill = GridBagConstraints.BOTH;
        cCommand.gridx = 1;
        cCommand.gridy = 0;
        cCommand.ipadx = 590;

        pane.add(commandPanel, cCommand);

        //DECK BUTTON
        Image card = null;
        try {
            card = ImageIO.read(Window.class.getResource("/images/card.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // change to image icon
        JButton button = new JButton(new ImageIcon(card));
        cButton.fill = GridBagConstraints.BOTH;
        cButton.gridx = 2;
        cButton.gridy = 0;
        cButton.ipadx = 15;

        pane.add(button, cButton);

        TitledBorder titledBorderMap = BorderFactory.createTitledBorder("MAP");
        risk.getMap().setBorder(titledBorderMap);
        titledBorderMap.setTitleColor(Color.WHITE);
        risk.getMap().setBackground(Color.black);

        cMap.fill = GridBagConstraints.BOTH;
        cMap.gridx = 1;
        cMap.gridy = 1;
        cMap.ipady = 150;      //make this component tall
        cMap.ipadx = 220;
        cMap.weightx = 0.0;
        cMap.gridwidth = 3;

        pane.add(risk.getMap(), cMap);
        pane.validate();
        pane.repaint();
    }
}