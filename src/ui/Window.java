package ui;

import com.botharmon.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.swing.border.TitledBorder;


/*
 * @Author: BotHarmon (Jess Dempsey, Rebeca Buarque, Noemi Banal)
 */
public class Window
{
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;

    private static DisplayText displayText;
    private static CommandPanel commandPanel;

    public Window(Game risk)
    {
        displayText = new DisplayText();
        commandPanel = new CommandPanel(risk.getActivePlayers());
        createAndShowGUI(risk);
    }

    // used to print the current message for each player's turn
    public void getTextDisplay(String message)
    {
        displayText.getTextDisplay(message);
    }

    // takes the current instruction for each player
    public String getCommand()
    {
        return commandPanel.getCommand();
    }

    public static void createAndShowGUI(Game risk)
    {
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

    public static void addComponentsToPane(Game risk, Container pane)
    {
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

        // change to image icon
        JButton button = new JButton(new ImageIcon(card));
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.gridx = 2;
        c.gridy = 0;
        pane.add(button, c);

        TitledBorder titledBorderMap = BorderFactory.createTitledBorder("MAP");
        risk.getMap().setBorder(titledBorderMap);
        titledBorderMap.setTitleColor(Color.WHITE);
        risk.getMap().setBackground(Color.black);
        c.fill = GridBagConstraints.BOTH;
        c.ipady = 70;      //make this component tall
        c.ipadx = 200;
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;

        c.ipady = 0;
        c.gridy = 1;
        
        pane.add(risk.getMap(), c);
        pane.validate();
        pane.repaint();
    }
}