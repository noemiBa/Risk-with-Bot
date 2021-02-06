package ui;

import gamecomponents.Game;
import player.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/*
 * @Author: BotHarmon (Jess Dempsey, Rebeca Buarque, Noemi Banal)
 */
public class Window {

    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
    private static JPanel displayText;
    private static JPanel readText;
    private static JLabel labelDisplayText;
    private static int instruction;

    public Window(Game game) {
        instruction = 1;
        createAndShowGUI(game);
    }

    public static void addComponentsToPane(Game game, Container pane) {
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }

        JButton button;

        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        if (shouldFill) {
            //natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
        }

        //DISPLAY TEXT PANEL
        displayText = new JPanel();
        displayText.setBorder(BorderFactory.createTitledBorder("Display Text"));
        displayText.setBackground(Color.yellow);
        nextTextDisplay(displayText, "<html>WELCOME TO RISK WITH BOT HARMON!<br>Enter First Player Name </html>");
        //Setting the display constraints
        if (shouldWeightX) {
            c.weightx = 0.6;
        }
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(displayText, c);


        //READ TEXT PANEL
        readText = new JPanel();
        button = new JButton("Enter");
        readText.setBorder(BorderFactory.createTitledBorder("Read Text Panel 2"));
        readText.setBackground(Color.orange);
        JTextField textArea = new JTextField(25);
        textArea.setBorder(BorderFactory.createEtchedBorder());

        //Adding button listener
        button.addActionListener(e -> {
            String input = textArea.getText();
            textArea.setText("");
            if (instruction <= 2) {
                nextInstructionBeforeStartGame(game.getPlayers(), input);
            } else if (instruction > 2){
                nextInstruction(input);
            }
            System.out.println("Button working, current instruction :" + instruction);
        });

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.5;
        c.gridx = 1; //column
        c.gridy = 0;
        readText.add(textArea);

        readText.add(button, BorderLayout.PAGE_END);
        pane.add(readText, c);

        //DECK BUTTON
        Image card = null;
        try {
            card = ImageIO.read(Window.class.getResource("/images/card.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        button = new JButton(new ImageIcon(card));
        //Add functionality later
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.gridx = 2;
        c.gridy = 0;
        pane.add(button, c);

        //MAP PANEL
        JPanel mapPanel = new JPanel(new GridBagLayout());
        mapPanel.setBorder(BorderFactory.createTitledBorder("MAP"));
        c.fill = GridBagConstraints.BOTH;
        c.ipady = 70;      //make this component tall
        c.ipadx = 200;
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;

        //mapPanel.add(label, c);
        mapPanel.add(game.getMap(), c);

        c.ipady = 0;
        c.gridy = 1;
        pane.add(mapPanel, c);
        pane.validate();
        pane.repaint();
    }

    //Method that will handle the next instruction, we maybe need to make a class for this
    public static void nextInstructionBeforeStartGame(Player[] players, String input) {
        switch (instruction) {
            case 1:
                players[0].setName(input);
                nextTextDisplay(displayText, "<html>Thanks for that! <br>Now Enter Second Player Name </html>");
                break;
            case 2:
                players[1].setName(input);
                nextTextDisplay(displayText, "<html>We are now ready to start the game!<br> See you on next Assignment <3 </html>");
                System.out.println(players);
                break;
            default:
                break;
        }
        instruction++;
    }

    //Method that will handle the next instruction, we maybe need to make a class for this
    public static void nextInstruction(String input) {
        switch (instruction) {
            case 3:
                //next assignment
                break;
            case 4:
                //next assignment
                break;
            default:
                break;
        }
        instruction++;
    }

    private static void nextTextDisplay(JPanel jp, String message) {
        jp.removeAll();
        labelDisplayText = new JLabel(message);
        jp.add(labelDisplayText);

        jp.updateUI();
        System.out.println("Text Display Updated");
    }


    public static void createAndShowGUI(Game game) {
        //Create and set up the window.
        JFrame frame = new JFrame("GridBagLayoutDemo");
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
