package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import gamecomponents.Game;

public class CommandPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private LinkedList<String> commandBuffer = new LinkedList<String>();

	JButton button = new JButton("Enter");
	TitledBorder titledBorderText = BorderFactory.createTitledBorder("Read Text");

	CommandPanel(Game game, DisplayText dt) {
		JTextField textArea = new JTextField(25);
		
		//Adding button listener
        button.addActionListener(e -> {
            String input = textArea.getText();
            textArea.setText("");
            dt.nextInstruction(game, input);
            System.out.println("Button working, current instruction :" + DisplayText.getInstruction());
        });
		
		setBorder(titledBorderText);
		titledBorderText.setTitleColor(Color.WHITE);
		setBackground(Color.black);
		
		textArea.setBorder(BorderFactory.createEtchedBorder());

        add(button, BorderLayout.PAGE_END);
        add(textArea);
	}
	
	public String getCommand() {
		String command;
		synchronized (commandBuffer) {
			while (commandBuffer.isEmpty()) {
				try {
					commandBuffer.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			command = commandBuffer.pop();
		}
		return command;
	}
}