import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * Name: Maria Sitkovets
 * Teacher: Mr. Naccarato 
 * Course: ICS 4U
 * Date: May 1, 2018
 * Summary: The class that draws the sprites
 */
public class Draw extends JPanel implements KeyListener
{
	Random r = new Random();
	int x2 = 10;
	int y2 = 0;
	int count = 0;
	int count2 = 0;
	//instantiate all the sprites needed
	Sprite background = new Sprite("background.png", 0, 0, 0, 0, 600, 300);
	Sprite background1 = new Sprite("background.png", 400, 0, 0, 0, 600, 300);
	Sprite sonic = new Sprite("sonicsprite.png", 40, 193, 0, 245, 36, 39);
	Sprite obstacle1 = new Sprite("obstacle1.png", r.nextInt(141) + 100, 180, 0, 0, 55, 50);
	Sprite obstacle2 = new Sprite("obstacle1.png", r.nextInt(141) + 300, 180, 0, 0, 55, 50);
	public int points;
	public boolean continueGame = false;

	public Draw()
	{
		super(); //instantiates the different members of JPanel
		super.setFocusable(true); //set the JPanel to active Lets java know that this window is going to take all the keyboard and mouse clicks
		super.addKeyListener(this); //adds key listener to the class
	}
	public void paint(Graphics g)
	{
		g.clearRect(0, 0, getWidth(), getHeight());
		background.drawSprite(g);
		background1.drawSprite(g);
		sonic.drawSprite(g);
		obstacle1.drawSprite(g);
		obstacle2.drawSprite(g);
	}
	@Override
	public void keyPressed(KeyEvent arg0) 
	{
		//change the height of the sprite when they press the up arrow key
		if(arg0.getKeyCode() == KeyEvent.VK_UP && count2 < 5)
		{
			sonic.y = 133;
			count2++;
		}
		else
		{
			//bring sonic down to its original position
			sonic.y = 193;
			count2 = 0;
		}	
		repaint();			
	}

	@Override
	public void keyReleased(KeyEvent arg0) 
	{
		//return sonic to original height of ground
		if(arg0.getKeyCode() == KeyEvent.VK_UP)
			sonic.y = 193;
			count2 = 0;
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent arg0) 
	{
	}

	public void update()
	{
		background.updateBackground(x2, y2);
		background1.updateBackground(x2, y2);

		//only update the obstacles when they are off the frame
		if(count >= 16 && obstacle1.x <= -25)
		{
			obstacle1.updateBackground(r.nextInt(141) + 100, y2);
			count = 0;
		}
		else if (count >= 16 && obstacle2.x <= -58)
		{
			obstacle2.updateBackground(r.nextInt(141) + 300, y2);
			count = 0;
		}
		else
		{
			obstacle1.updateBackground(x2, y2);
			obstacle2.updateBackground(x2, y2);
			count++;
		}

		//check if the sprite collided with any obstacles
		Collisions col = new Collisions(sonic.x, sonic.y, obstacle1.x, obstacle1.y, obstacle2.x, obstacle2.y);
		col.collided = col.checkCollision();
		if(col.collided)
		{
			pauseGame();			
		}
		else
		{
			//increment points
			points += 1;
			GameManager.label.setText("Points: " + points);
		}
		sonic.update(sonic.x, sonic.y);
		repaint();

	}
	
	private void pauseGame() 
	{
		HighScore score = new HighScore();
		int returnValue = 0;
		Object[] options = {"OK", "CANCEL"};
		//display a choice for the user to play again or just view the high scores
		returnValue = JOptionPane.showOptionDialog(Main.frame, "GAME OVER \n" + "Click OK to play again", "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		
		if(returnValue == JOptionPane.OK_OPTION)
		{		
			//change the high score
			score.addScore(points);
			score.sort();
			//need to restart the game loop to reset everything
			points = 0;
			sonic.y = 193;
			obstacle1.updateBackground(r.nextInt(141) + 100, y2);
			obstacle2.updateBackground(r.nextInt(141) + 300, y2);
			repaint();
		}
		else
		{
			//need to add points to high score 
			continueGame = false;  
			//change the high score
			score.addScore(points);
			score.sort();
			score.viewScores();
			//erase frame when they exit game
			Main.frame.setVisible(false);
			JOptionPane.getRootFrame().dispose();
			GameManager.sequencer.stop();
		}
	}
}
