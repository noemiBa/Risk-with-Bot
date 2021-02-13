package ui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gamecomponents.Game;
import player.Player;

public class DisplayText extends JPanel{
	private static final long serialVersionUID = 1L;
	public static int instruction = 1;
	private static JLabel labelDisplayText;
	
	public DisplayText()
	{
		setBorder(BorderFactory.createTitledBorder("Display Text"));
        setBackground(Color.white);
        nextTextDisplay(this, "<html>WELCOME TO RISK WITH BOT HARMON!<br>Enter First Player Name </html>");
	}
	
	//Method that will handle the next instruction, we maybe need to make a class for this
    public void nextInstruction(Game game, String input)
    {
        switch(instruction) {
            case 1:
                game.getPlayers()[0].setName(input);
                nextTextDisplay(this, "<html>Thanks for that! <br>Now Enter Second Player Name </html>");
                break;
            case 2:
                game.getPlayers()[1].setName(input);
                Player.initialiseUnits(game.getPlayers());
                game.getMap().addPlayers(game.getPlayers());
                game.getDeck().shuffle();
                nextTextDisplay(this, "<html>We are now ready to start the game:<br>" + game.getPlayers()[0].getName() + " your color is Cyan!<br>" + game.getPlayers()[1].getName() + " your color is Pink! <br> See you in two weeks! xoxo </html>");
                game.getMap().updateUI();
                break;
            default:
                break;
        }
        instruction++;
    }

    void nextTextDisplay(JPanel jp, String message)
    {
        jp.removeAll();
        labelDisplayText = new JLabel(message);
        jp.add(labelDisplayText);

        jp.updateUI();
        System.out.println("Text Display Updated");
    }
    
    public static int getInstruction()
    {
        return instruction;
    }
}
