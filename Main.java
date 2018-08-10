import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * Name: Maria Sitkovets
 * Teacher: Mr. Naccarato 
 * Course: ICS 4U
 * Date: May 1, 2018
 * Summary: The class that creates the window for the game and starts running the game
 */
public class Main 
{
	public static JFrame frame; 
	public static String name;
	public static Draw draw;
	public static GameManager manager;
	public static void main(String[] args) 
	{
		//instantiate the draw class
		draw = new Draw();
		
		//use the JFrame library to create a window in java with this title
		frame = new JFrame("Sonic Race");
		frame.setSize(400, 330);
		//call the GameManager class to set up the menu bar and music
		manager = new GameManager();
		frame.add(draw);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//display dialogs for user input
		JOptionPane.showMessageDialog(frame,"Welcome to Sonic Race! \nUse the up arrow key to jump"); 
		name = (String)JOptionPane.showInputDialog(frame,"Enter your name: \n","Player Name", JOptionPane.PLAIN_MESSAGE, null, null, null); 
		//give player 3 seconds to turn off music if they wish to do so
		try
		{
			Thread.sleep(3000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		draw.continueGame = true;
		//game loop
		while(draw.continueGame)
		{
			draw.update();
			try
			{
				Thread.sleep(30);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

}
