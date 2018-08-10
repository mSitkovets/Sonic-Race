import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * Name: Maria Sitkovets
 * Teacher: Mr. Naccarato 
 * Course: ICS 4U
 * Date: May 18, 2018
 * Summary: The class that updates the high scores
 */
public class HighScore 
{
	protected int points;
	Scanner fileInput = null; //takes in a file
	String[] arrayNames = new String [3]; //holds the names of the top 3 high scorers
	int[] arrayScores = new int [3]; // hold the scores of the top 3 high scorers
	PrintWriter pw; //writes to the file

	public HighScore()
	{
		try
		{
			//set the file input
			fileInput = new Scanner(new File("HighScores.txt"));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Unable to open file");
		}
		catch(IOException ex)
		{
			System.out.println("Unable to open file");
		}
		catch(NullPointerException exp)
		{
		}
	}

	public void addScore(int points)
	{
		this.points = points;

		//loop through the file as long as it has a new line
		while(fileInput.hasNextLine())
		{
			for(int i = 0; i < arrayNames.length; i++)
			{
				//add the first three elements of the file to the names array
				arrayNames[i] = fileInput.nextLine();
			}
			for(int i = 0; i < arrayScores.length; i++)
			{
				//add the last three elements of the file to the scores array and parse them to ints
				arrayScores[i] = Integer.parseInt(fileInput.nextLine());
			}

			//check if the new score beats any previously set high score
			for(int i = 0; i < arrayScores.length; i++)
			{
				if(arrayScores[i] < points)
				{
					//swap elements
					arrayScores[i] = points;
					arrayNames[i] = Main.name;
				}
			}
		}
	}

	public void sort()
	{
		//create a print writer to change the info in the file
		try 
		{
			pw = new PrintWriter("HighScores.txt");		
		} 
		catch (IOException e) 
		{
		}

		//bubble sort the names and scores
		int temp = 0;
		String tempString = "";
		for(int i = 0; i < arrayScores.length; i++)
		{
			for (int j = 1; j < arrayScores.length; j++)
			{
				if(arrayScores[j-1] > arrayScores[j])
				{
					//swap elements
					temp = arrayScores[j-1];
					tempString = arrayNames[j-1];
					arrayScores[j-1] = arrayScores[j];
					arrayNames[j-1] = arrayNames[j];
					arrayScores[j] = temp;
					arrayNames[j] = tempString;
				}
			}
		}

		//print the high scores in order from highest to lowest in the file using print writer
		for(int i = arrayNames.length - 1; i >= 0; i--)
		{
			pw.println(arrayNames[i]);
		}
		for(int i = arrayScores.length - 1; i >= 0; i--)
		{
			pw.println(arrayScores[i]);
		}
		pw.close();
	}

	public void viewScores()
	{
		//create a table model that will hold the columns for the table
		DefaultTableModel model = new DefaultTableModel(new Object[]{"Name","Score"}, 0);
		//loop through all the high scores and print them in separate rows
		for (int x = arrayNames.length-1;x >= 0; x--)
		{
			Object[] rows = {arrayNames[x],arrayScores[x]};
			model.addRow(rows);
		}

		//set the size of the table 
		JTable table = new JTable(model);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		table.setFillsViewportHeight(true);
		//add the table to a message dialog for the player to view
		JOptionPane.showMessageDialog(Main.frame, new JScrollPane(table),"High Scores", JOptionPane.PLAIN_MESSAGE);

	}
}