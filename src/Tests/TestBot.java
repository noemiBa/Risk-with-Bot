package Tests;

import ui.Window;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class TestBot extends Robot
{

    public TestBot() throws AWTException {}

    public void click(int x, int y)
    {
        mouseMove(x, y);
        mousePress(InputEvent.BUTTON1_DOWN_MASK);
        mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public void typeKey(int keyEvent)
    {
        keyPress(keyEvent);
        keyRelease(keyEvent);
    }

    public void enterText(String command)
    {

        click(880, 211);
        StringSelection stringSelection = new StringSelection(command);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);

        keyPress(KeyEvent.VK_CONTROL);
        typeKey(KeyEvent.VK_V);
        keyRelease(KeyEvent.VK_CONTROL);
        pressEnter();
    }

    public void pressEnter()
    {
        keyPress(KeyEvent.VK_ENTER);
        keyRelease(KeyEvent.VK_ENTER);
    }
}
