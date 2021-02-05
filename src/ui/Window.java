package ui;

import gamecomponents.Card;
import gamecomponents.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import player.Player;

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

    public Window() {
        instruction = 1;
        createAndShowGUI();
    }

    public static void addComponentsToPane(Player[] players, Container pane) {
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }

        JButton button;
        JLabel label;

        pane.setSize(900, 700);
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
        nextTextDisplay(displayText, "<html>WELCOME TO RISK WITH BOT HARMON!<br>Enter First Player Name </html>" );
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
            nextInstruction(players, input);
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

        //MAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAP
        JPanel mapPanel = new JPanel(new GridBagLayout());
        label = new JLabel(new ImageIcon(Window.class.getResource("/images/map.png")));
        mapPanel.setBorder(BorderFactory.createTitledBorder("MAP"));
        c.fill = GridBagConstraints.BOTH;
        c.ipady = 70;      //make this component tall
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);

        mapPanel.add(label, c);
        //calling Map class to create the map
        Map map = new Map();
        map.initialiseCountries();
        mapPanel.add(map, c);

        c.ipady = 0;
        c.gridy = 1;
        pane.add(mapPanel, c);
        pane.validate();
        pane.repaint();

    }

    //Method that will handle the next instruction, we maybe need to make a class for this
    public static void nextInstruction(Player[] players, String input)
    {
        switch(instruction)
        {
            case 1:
                players[0].setName(input);
                nextTextDisplay(displayText, "<html>Thanks for that! <br>Now Enter Second Player Name </html>");
                break;
            case 2:
                players[1].setName(input);
                nextTextDisplay(displayText, "<html>We are now ready to start the game!<br> See you on next Assignment <3 </html>");
                break;
            default:
                // player turn implemented here
                break;
        }
//        for(Player p: players)
//        {
//            System.out.print(p.getName() + " has countries ");
//            for(Card c: p.getCards())
//            {
//                System.out.print(c.getCountryName() + " ");
//            }
//            System.out.println();
//        }
        instruction++;
    }


    private static void nextTextDisplay(JPanel jp, String message) {
        jp.removeAll();
        labelDisplayText = new JLabel(message);
        jp.add(labelDisplayText);
        System.out.println("button working");
    }



    public static void createAndShowGUI() {
//Create and set up the window.
        JFrame frame = new JFrame("GridBagLayoutDemo");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//Set up the content pane.
        addComponentsToPane(Game.getPlayers(), frame.getContentPane());

//Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


}
