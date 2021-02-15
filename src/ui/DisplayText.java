package ui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.botharmon.Game;
import gamecomponents.Country;

public class DisplayText extends JPanel {
	private static final long serialVersionUID = 1L;
	public static int instruction = 1;
	private static JLabel labelDisplayText;
	
	public DisplayText()
	{
		setBorder(BorderFactory.createTitledBorder("Display Text"));
        setBackground(Color.white);
	}
	
	

    public void getTextDisplay(String message)
    {
        removeAll();
        labelDisplayText = new JLabel(message);
        add(labelDisplayText);

        updateUI();
        System.out.println("Text Display Updated");
    }
}
