import java.awt.Graphics;

import javax.swing.ImageIcon;

/*
 * Name: Maria Sitkovets
 * Teacher: Mr. Naccarato 
 * Course: ICS 4U
 * Date: May 1, 2018
 * Summary: The class that initializes the sprites
 */

public class Sprite 
{
	//create an image icon to store the image of the sprite
	protected ImageIcon image;
	//declare the ints that will keep track of the sprite's position on the window and on the spread sheet
	protected int x, y, sprtX, sprtY, width, height;

	public Sprite(String file, int x, int y, int sprtX, int sprtY,int width, int height) 
	{
		image = new ImageIcon(file);
		this.x = x;
		this.y = y;
		this.sprtX = sprtX;
		this.sprtY = sprtY;
		this.width = width;
		this.height = height;
	}

	//method that draws the sonic sprite
	public void drawSprite(Graphics g)
	{
		g.drawImage(image.getImage(), x, y, x+width, y+height, sprtX, sprtY, sprtX+width, sprtY+height, null);

	}
	//to animate the image you have to move the viewing window of your image
	public void update(int x, int y)
	{
		//if the sprite ends at a certain x position then reset the sprtX to the beginning
		if(sprtX+width>=265)
		{
			sprtX = 0;
		}
		//otherwise just increment our sprtX to the next animation location
		else
		{
			sprtX += width;
		}
		//then we change our x,y with our parameter
		this.x = x;
		this.y = y;
	}

	//scrolls the background
	public void updateBackground(int x2, int y2)
	{
		if(this.x <= -400)
		{
			this.x = 390;
		}
		else
		{
			this.x -= x2;
			x2 += 10;
		}

		sprtY = y2;
	}
}
