package ui;

import gamecomponents.Turns;
import player.ActivePlayer;
import player.PassivePlayer;

import javax.swing.*;
import java.awt.*;

public class PlayerInfo extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static final int FONT_SIZE = 14;
    private final int boxWidth = 20;
    private final int boxHeight = 20;
    private Turns.stage gameStage;
    private ActivePlayer[] activePlayers;
    private PassivePlayer[] passivePlayers;

    public PlayerInfo()
    {
        gameStage = Turns.stage.ENTER_NAMES;
    }

    public void updateUI(Turns.stage gameStage, ActivePlayer[] activePlayers, PassivePlayer[] passivePlayers)
    {
        this.activePlayers = activePlayers;
        this.passivePlayers = passivePlayers;
        this.gameStage = gameStage;
        super.updateUI();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        setBackground(Color.black);
        if(gameStage != Turns.stage.ENTER_NAMES)
        {
            drawBox(g, 10, 10, new Color(47, 206, 237));
            g.drawString(activePlayers[0].getName(), 35, 25);
            drawBox(g, 10, 40, new Color(199, 60, 194));
            g.drawString(activePlayers[1].getName(), 35, 55);
            drawBox(g, 10, 70, new Color(177, 212, 174));
            g.drawString(passivePlayers[0].getName(), 35, 85);
            drawBox(g, getWidth() / 2, 10, new Color(235, 232, 234));
            g.drawString(passivePlayers[1].getName(), (getWidth() / 2) + 25, 25);
            drawBox(g, getWidth() / 2, 40, new Color(248, 250, 162));
            g.drawString(passivePlayers[2].getName(), (getWidth() / 2) + 25, 55);
            drawBox(g, getWidth() / 2, 70, new Color(237, 181, 126));
            g.drawString(passivePlayers[3].getName(), (getWidth() / 2) + 25, 85);
        }
    }

    public void drawBox(Graphics g, int x, int y, Color color)
    {
        g.drawRect(x,y,boxWidth,boxHeight);
        g.setColor(color);
        g.fillRect(x,y,boxWidth, boxHeight);
        g.setColor(Color.white);
    }
}
