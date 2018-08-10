import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

/*
 * Name: Maria Sitkovets
 * Teacher: Mr. Naccarato 
 * Course: ICS 4U
 * Date: June 12, 2018
 * Summary: The class that sets up the menu bar and the music
 */
public class GameManager 
{
	static Sequencer sequencer; //midi Sequencer that plays music
	static HighScore score = new HighScore();
	static JLabel label = new JLabel("", SwingConstants.CENTER);

	GameManager()
	{
		//set up the menu bar in the frame
		Main.frame.setJMenuBar(createMenuBar());
		//layout the frame
		BorderLayout layout = new BorderLayout();
		Main.frame.setLayout(layout);
		Main.frame.setResizable(false);
		//create a new label to keep track of points
		label.setSize(new Dimension(100, 30));
		Main.frame.add(label, BorderLayout.SOUTH);
		
		//error testing for music
		Sequence sequence = null;
		try
		{
			//set the sequence to the Sonic midi
			sequence = MidiSystem.getSequence(Main.class.getResource("Sonic.mid"));
		} 
		catch (InvalidMidiDataException | IOException e)
		{
			e.printStackTrace();
		}
		sequencer = null;
		try
		{
			sequencer = MidiSystem.getSequencer();
		}
		catch (MidiUnavailableException e)
		{
			e.printStackTrace();
		}
		try
		{
			sequencer.open();
		}
		catch (MidiUnavailableException e)
		{
			e.printStackTrace();
		}
		try
		{
			sequencer.setSequence(sequence);
			sequencer.start();
		} 
		catch (InvalidMidiDataException e)
		{
			e.printStackTrace();
		}
		
	}

	private static JMenuBar createMenuBar() 
	{
		//create a menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);

		//add the option to toggle the music to the menu bar
		JMenuItem menuItem = new JMenuItem("Toggle Music");
		menuItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				//turn on/off the music
				if (sequencer.isRunning())
				{
					sequencer.stop();
				}
				else
				{
					sequencer.start();
				}

			}

		});
		menu.add(menuItem);

		//add the option to check the top 3 high scores to the menu bar
		menuItem = new JMenuItem("High Scores");
		menuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//call the high score class to display the table of top 3 scores
				score.addScore(Main.draw.points);
				score.sort();
				score.viewScores();
			}
		});
		menu.add(menuItem);
		
		//add the option to exit to the menu bar
		menuItem = new JMenuItem("Exit");
		menuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//get the JMenuItem associated with this ActionListener
				JMenuItem source = (JMenuItem) e.getSource();
				//Get the parent of this JMenuItem, a JPopupMenu
				Container parent = source.getParent();
				if (parent != null)
				{
					//Get the JComponent that activated the JPopupMenu
					JComponent invoker = (JComponent)((JPopupMenu) parent).getInvoker();
					//Get the Main class, the root of all the components
					Container top = invoker.getTopLevelAncestor();
					//Make it invisible and close the program
					top.setVisible(false);
					((Window) top).dispose();
					System.exit(0);
				}
			}	
		});
		menu.add(menuItem);
		
		return menuBar;
	}

}
