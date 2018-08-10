/*
 * Name: Maria Sitkovets
 * Teacher: Mr. Naccarato 
 * Course: ICS 4U
 * Date: May 15, 2018
 * Summary: The class that checks if the sonic sprite collides with any of the obstacles
 */
public class Collisions 
{
	protected int sonicx, sonicy, obstaclex1, obstacley1, obstaclex2, obstacley2;
	protected boolean collided = false;
	
	public Collisions(int sonicx, int sonicy, int obstaclex1, int obstacley1, int obstaclex2, int obstacley2) 
	{
		this.sonicx = sonicx;
		this.sonicy = sonicy;
		this.obstaclex1 = obstaclex1;
		this.obstacley1 = obstacley1;
		this.obstaclex2 = obstaclex2;
		this.obstacley2 = obstacley2;	
	}
	
	public boolean checkCollision()
	{
		//check if the sprite hits into any obstacle
		if(sonicx >= (obstaclex1 + 12) && sonicx <= (obstaclex1 + 55) && sonicy >= obstacley1 ||sonicx >= (obstaclex2 + 12) && sonicx <= (obstaclex2 + 55) && sonicy >= obstacley2)
		{
			//returns true to draw if sonic hit into any obstacle
			return true;
		}
		else
		{
			return false;
		}
	}
}
