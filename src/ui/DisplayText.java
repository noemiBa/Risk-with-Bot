package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;


public class DisplayText extends JPanel {
	private static final long serialVersionUID = 1L;
	public static int instruction = 1;
	private static final int TEXT_AREA_HEIGHT = 2;
	private static final int CHARACTER_WIDTH = 8;
	private static final int FONT_SIZE = 14;
	
	JTextArea textArea = new JTextArea(TEXT_AREA_HEIGHT, CHARACTER_WIDTH);
	JScrollPane scrollPane = new JScrollPane(textArea);
	DefaultCaret caret = (DefaultCaret)textArea.getCaret();

	public DisplayText() {	
		textArea.setEditable(false);
        textArea.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, FONT_SIZE));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBackground(Color.black);

		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		

		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		return;
	}

    public void getTextDisplay(String message)  {
    	textArea.setForeground(Color.white);
		Font  fontTextDisplay  = new Font(Font.DIALOG,  Font.BOLD, 16);
		textArea.setFont(fontTextDisplay);
    	textArea.setText(textArea.getText()+"\n\n"+message);
    }
    
    public void sendErrorMessage(String message) {
    	textArea.setForeground(new Color(122, 16, 16)); //the textArea text will turn red if there is an erroneous input
    	textArea.setText(textArea.getText() + "\n\n" + message);
    }
}
