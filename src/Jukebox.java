
/*
 *    Copyright (c) The League of Amazing Programmers 2013-2018
 *    Level 1
 */

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javazoom.jl.player.advanced.AdvancedPlayer;

/* 1. Download the JavaZoom jar from here: http://bit.ly/javazoom
 * 2. Right click your project and add it as an External JAR (Under Java Build Path > Libraries).*/

public class Jukebox implements Runnable, MouseListener {
	JLabel labelUndyne;
	JLabel labelFrisk;
	JButton button;
	
	Song song;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Jukebox());
	}

	public void run() {

		// 3. Find an mp3 on your computer or on the Internet.
		// 4. Create a Song
		// 5. Play the Song
		/*
		 * 6. Create a user interface for your Jukebox so that the user can to choose
		 * which song to play. You can use can use a different button for each song, or
		 * a picture of the album cover. When the button or album cover is clicked, stop
		 * the currently playing song, and play the one that was selected.
		 */

		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setSize(900, 500);
		frame.setTitle("Choose a song! :3");
		JPanel panel = new JPanel();
		frame.add(panel);
		labelUndyne = loadImage("BattleAgainstATrueHero-Image.jpg");
		labelFrisk = loadImage("Frisk.jfif");
		panel.add(labelUndyne);
		panel.add(labelFrisk);
		labelUndyne.addMouseListener(this);
		labelFrisk.addMouseListener(this);
		button = new JButton();
		panel.add(button);
		button.setSize(50,20);
		button.setText("Stop Music");
		button.addMouseListener(this);
		frame.pack();

	}

	/* Use this method to add album covers to your Panel. */
	private JLabel loadImage(String fileName) {
		URL imageURL = getClass().getResource(fileName);
		Icon icon = new ImageIcon(imageURL);
		return new JLabel(icon);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (song != null) {	
			//if stop button pressed, stops song (if one's playing)
			song.stop();

		}
		if (arg0.getSource().equals(labelUndyne)) {
			song = new Song("BattleAgainstATrueHero.mp3");
			song.play();
		} else if (arg0.getSource().equals(labelFrisk)) {
			song = new Song("Undertale.mp3");
			song.play();
		}
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}

class Song {

	private int duration;
	private String songAddress;
	private AdvancedPlayer mp3Player;
	private InputStream songStream;

	/**
	 * Songs can be constructed from files on your computer or Internet addresses.
	 * 
	 * Examples: <code> 
	 * 		new Song("everywhere.mp3"); 	//from default package 
	 * 		new Song("/Users/joonspoon/music/Vampire Weekend - Modern Vampires of the City/03 Step.mp3"); 
	 * 		new	Song("http://freedownloads.last.fm/download/569264057/Get%2BGot.mp3"); 
	 * </code>
	 */
	public Song(String songAddress) {
		this.songAddress = songAddress;
	}

	public void play() {
		loadFile();
		if (songStream != null) {
			loadPlayer();
			startSong();
		} else
			System.err.println("Unable to load file: " + songAddress);
	}

	public void setDuration(int seconds) {
		this.duration = seconds * 100;
	}

	public void stop() {
		if (mp3Player != null)
			mp3Player.close();
	}

	private void startSong() {
		Thread t = new Thread() {
			public void run() {
				try {
					if (duration > 0)
						mp3Player.play(duration);
					else
						mp3Player.play();
				} catch (Exception e) {
				}
			}
		};
		t.start();
	}

	private void loadPlayer() {
		try {
			this.mp3Player = new AdvancedPlayer(songStream);
		} catch (Exception e) {
		}
	}

	private void loadFile() {
		if (songAddress.contains("http"))
			this.songStream = loadStreamFromInternet();
		else
			this.songStream = loadStreamFromComputer();
	}

	private InputStream loadStreamFromInternet() {
		try {
			return new URL(songAddress).openStream();
		} catch (Exception e) {
			return null;
		}
	}

	private InputStream loadStreamFromComputer() {
		try {
			return new FileInputStream(songAddress);
		} catch (FileNotFoundException e) {
			return this.getClass().getResourceAsStream(songAddress);
		}
	}
}
