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
	
	public DisplayText() {
		//instruction = 1;
		setBorder(BorderFactory.createTitledBorder("Display Text"));
        setBackground(Color.white);
        nextTextDisplay(this, "<html>WELCOME TO RISK WITH BOT HARMON!<br>Enter First Player Name </html>");
	}
	
	//Method that will handle the next instruction, we maybe need to make a class for this
    public void nextInstruction(Player[] players, String input) {
        switch (instruction) {
            case 1:
                players[0].setName(input);
                nextTextDisplay(this, "<html>Thanks for that! <br>Now Enter Second Player Name </html>");
                break;
            case 2:
                players[1].setName(input);
                nextTextDisplay(this, "<html>We are now ready to start the game:<br>" + Game.getPlayers()[0].getName() + " your color is Cyan!<br>" + Game.getPlayers()[1].getName() + " your color is Pink! <br> See you in two weeks! xoxo </html>");
                Window.mapPanel.updateUI();
                break;
            default:
                break;
        }
        instruction++;
    }

     void nextTextDisplay(JPanel jp, String message) {
        jp.removeAll();
        labelDisplayText = new JLabel(message);
        jp.add(labelDisplayText);

        jp.updateUI();
        System.out.println("Text Display Updated");
    }
    
    public static int getInstruction() {
        return instruction;
    }
}
