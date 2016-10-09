/* Mishael Zerrudo
   Draws the gallows for Hangman game*/
   
import java.awt.*;
import javax.swing.*;
public class DrawGallows extends JPanel
{
	private int tries;
	
	public DrawGallows()
	{
		super();
		tries = 0;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.drawLine(50, 20, 50, 190);
		g.drawLine(50, 20, 130, 20);
		g.drawLine(130, 20, 130, 50);
		if (tries >= 1)
			g.drawOval(115, 50, 30, 30);			//draws the head
		if (tries >= 2)
			g.drawLine(130, 80, 130, 130);			//draws the body
		if (tries >= 3)
			g.drawLine(130, 90, 115, 120);				//draws left arm
		if (tries >= 4)
			g.drawLine(130, 90, 145, 120);				//draws right arm
		if (tries >= 5)
			g.drawLine(130, 130, 115, 160);				//draws left leg
		if (tries == 6)
			g.drawLine(130, 130, 145, 160);				//draws right leg
	}
	
	public void resetTries()
	{
		tries = 0;
	}
	
	public void incrementTries()
	{
		tries++;
	}
}