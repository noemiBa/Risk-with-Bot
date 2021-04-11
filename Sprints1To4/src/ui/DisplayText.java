package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;


public class DisplayText extends JPanel
{
    private static final long serialVersionUID = 1L;
    public static int instruction = 1;
    private static final int TEXT_AREA_HEIGHT = 2;
    private static final int CHARACTER_WIDTH = 8;
    private static final int FONT_SIZE = 14;

    JTextArea textArea = new JTextArea(TEXT_AREA_HEIGHT, CHARACTER_WIDTH);
    JScrollPane scrollPane = new JScrollPane(textArea);
    DefaultCaret caret = (DefaultCaret) textArea.getCaret();

    public DisplayText()
    {
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.DIALOG, Font.BOLD, FONT_SIZE));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(Color.black);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);


        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }

    public void getTextDisplay(String message)
    {
        textArea.setForeground(Color.white);
        textArea.setText(textArea.getText() + "\n\n" + message);
    }

    public void clearTextDisplay()
    {
    	textArea.setText("");
	}

    public void sendErrorMessage(String message)
    {
        clearTextDisplay();
        textArea.setForeground(new Color(173, 109, 109)); //the textArea text will turn red if there is an erroneous input
        textArea.setText(textArea.getText() + "\n\n" + message);
    }
}
