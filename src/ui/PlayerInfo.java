package ui;

import gamecomponents.Turns;

import javax.swing.*;
import java.awt.*;

public class PlayerInfo extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static final int FONT_SIZE = 14;
    private final int boxWidth = 20;
    private final int boxHeight = 20;
    private Turns.stage gameStage;
    private String player1Name;
    private String player2Name;

    public PlayerInfo()
    {
        gameStage = Turns.stage.ENTER_NAMES;
    }

    public void updateUI(Turns.stage gameStage, String player1Name, String player2Name)
    {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.gameStage = gameStage;
        super.updateUI();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        setOpaque(true);
        setBackground(Color.black);
        if(gameStage != Turns.stage.ENTER_NAMES)
        {
            g.drawRect(10,10,boxWidth,boxHeight);
            g.setColor(new Color(47, 206, 237));
            g.fillRect(10,10,boxWidth,boxHeight);
            g.setColor(Color.white);
            g.drawString(player1Name, 35, 25);

            g.drawRect(10,40,boxWidth,boxHeight);
            g.setColor(new Color(199, 60, 194));
            g.fillRect(10,40,boxWidth,boxHeight);
            g.setColor(Color.white);
            g.drawString(player2Name, 35, 55);
        }
    }
}
