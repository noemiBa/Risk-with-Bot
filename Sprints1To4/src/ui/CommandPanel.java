package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import player.ActivePlayer;

public class CommandPanel extends JPanel
{

	private static final long serialVersionUID = 1L;
	private static final int FONT_SIZE = 28;

	private JTextField commandField = new JTextField();
	private LinkedList<String> commandBuffer = new LinkedList<String>();
	private TitledBorder titledBorderText = BorderFactory.createTitledBorder("Enter commands");

	public CommandPanel (ActivePlayer[] activePlayer)
	{
		class AddActionListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				synchronized (commandBuffer)
				{
					commandBuffer.add(commandField.getText());
					commandField.setText("");
					commandBuffer.notify();
				}
			}
		}
		ActionListener listener = new AddActionListener();
		commandField.addActionListener(listener);

		//Aesthetically related declarations
		commandField.setFont(new Font(Font.DIALOG, Font.BOLD, FONT_SIZE));
		setBorder(titledBorderText);
		setBackground(Color.black);
		titledBorderText.setTitleColor(Color.WHITE);

		setLayout(new BorderLayout());
		add(commandField, BorderLayout.CENTER);
	}

	public String getCommand()
	{
		String command;
		synchronized (commandBuffer)
		{
			while(commandBuffer.isEmpty())
			{
				try
				{
					commandBuffer.wait();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			command = commandBuffer.pop();
		}
		return command;
	}
}
