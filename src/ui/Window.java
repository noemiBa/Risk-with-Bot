package ui;

import com.botharmon.Game;
import gamecomponents.Turns;
import player.ActivePlayer;
import player.PassivePlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.border.TitledBorder;
import javax.swing.AbstractAction;


/*
 * @Author: BotHarmon (Jess Dempsey, Rebeca Buarque, Noemi Banal)
 */
public class Window {
    final static boolean shouldWeightX = true;
    final static boolean shouldWeightY = true;
    final static boolean RIGHT_TO_LEFT = false;

    private DisplayText displayText;
    private CommandPanel commandPanel;
    private PlayerInfo playerInfo;
    private Map map;

    public Window(Game risk)
    {
        displayText = new DisplayText();
        commandPanel = new CommandPanel(risk.getActivePlayers());
        playerInfo = new PlayerInfo();
        map = risk.getMap();
        createAndShowGUI(displayText, commandPanel, playerInfo, map);
    }

    public void updatePlayerInfo(Turns.stage gameStage, ActivePlayer[] activePlayers, PassivePlayer[] passivePlayers)
    {
        playerInfo.updateUI(gameStage, activePlayers, passivePlayers);
        map.updateUI(gameStage);
    }

    public void updateMap(Turns.stage gameStage)
    {
        map.updateUI(gameStage);
    }

    public void updateMap()
    {
        map.updateUI();
    }

    // used to print the current message for each player's turn
    public void getTextDisplay(String message) {
        displayText.getTextDisplay(message);
    }

    public void clearText(){displayText.clearTextDisplay();}

    // used to print an error message in response to the players' erroneous input
    public void sendErrorMessage(String message) {
        displayText.sendErrorMessage(message);
    }

    // takes the current instruction for each player
    public String getCommand() {
        return commandPanel.getCommand();
    }

    public static void createAndShowGUI(DisplayText displayText, CommandPanel commandPanel, PlayerInfo playerInfo, Map map) {
        //Create and set up the window.
        JFrame frame = new JFrame("Risk");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Window.class.getResource("/images/icon.png")));

        //Set up the content pane.
        addComponentsToPane(displayText, commandPanel, playerInfo, map, frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void addComponentsToPane(DisplayText displayText, CommandPanel commandPanel, PlayerInfo playerInfo, Map map, Container pane) {
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }

        pane.setLayout(new GridBagLayout());
        GridBagConstraints cDisplay = new GridBagConstraints();
        GridBagConstraints cMap = new GridBagConstraints();
        GridBagConstraints cPlayerInfo = new GridBagConstraints();
        GridBagConstraints cCommand = new GridBagConstraints();

        //Setting the display constraints
        if (shouldWeightX) {
            cMap.weightx = 1;
            cCommand.weightx = 1;
            cPlayerInfo.weightx = 1;
            cDisplay.weightx = 1;
        }

        if (shouldWeightY) {
            cDisplay.weighty = 1;
            cCommand.weighty = 1;
            cPlayerInfo.weighty = 1;
            cMap.weighty = 10;
        }

        cDisplay.fill = GridBagConstraints.BOTH;
        cDisplay.gridx = 0;
        cDisplay.gridy = 0;
        cDisplay.gridheight = 7;
        cDisplay.ipadx = 30;
        cDisplay.weighty = 7;

        JLabel icon = new JLabel(new ImageIcon(Window.class.getResource("/images/displayText.png")));
        JPanel iconPanel = new JPanel();
        iconPanel.setBackground(Color.black);
        iconPanel.add(icon, BorderLayout.CENTER);

        displayText.add(iconPanel, BorderLayout.NORTH);

        pane.add(displayText, cDisplay);

        cCommand.fill = GridBagConstraints.BOTH;
        cCommand.gridx = 1;
        cCommand.gridy = 0;
        cCommand.ipady = 5;
        cCommand.ipadx = 400;
        cCommand.weighty = 1;

        pane.add(commandPanel, cCommand);

        //DECK BUTTON
        Image card = null;
        try {
            card = ImageIO.read(Window.class.getResource("/images/card.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // change to image icon
        playerInfo.setPreferredSize(new Dimension(200, 100));
        cPlayerInfo.fill = GridBagConstraints.BOTH;
        cPlayerInfo.gridx = 2;
        cPlayerInfo.gridy = 0;
        cPlayerInfo.ipadx = 5;
        cPlayerInfo.ipady = 5;

        pane.add(playerInfo, cPlayerInfo);

        TitledBorder titledBorderMap = BorderFactory.createTitledBorder("MAP");
        map.setBorder(titledBorderMap);
        titledBorderMap.setTitleColor(Color.WHITE);
        map.setBackground(Color.black);

        cMap.fill = GridBagConstraints.BOTH;
        cMap.gridx = 1;
        cMap.gridy = 1;
        cMap.ipady = 150;      //make this component tall
        cMap.ipadx = 230;
        cMap.gridwidth = 2;
        cMap.gridheight = 2;

        pane.add(map, cMap);

        pane.validate();
        pane.repaint();
    }
}