package klotski.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import klotski.model.Board;
import klotski.view.InitialMenu;
import klotski.view.KlotskiApp;

public class FileController {
	KlotskiApp app;
	Board b;
	final Path p;
	
	/**
	 * Basic constructor
	 * @param app the view application
	 * @param b the model board
	 * @param p the path of the file 
	 */
	public FileController(KlotskiApp app, Board b, Path p) {
		this.app = app;
		this.b = b;
		this.p = p;
	}
	
	/**
	 * Reads in a saved game state text file and replaces the current board 
	 * with it
	 * @return true if successful, false otherwise
	 */
	public boolean open() {
		try {
			List<String> lines = new ArrayList<>();
			for(int j = 1; j < 12; j++) {
	    		try {
					lines.add(Files.readAllLines(p).get(j));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			b.setPieces(lines);
		} catch (Exception e) {
			System.err.println(e);
			return false;
		}
		app.getPuzzleView().refresh();
		app.getMovesCounter().setText(Integer.toString(b.getMoves()));
		int conf = 1;
		try {
			conf = Integer.parseInt(Files.readAllLines(p).get(0));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		b.setConfig(conf);
		return true;
	}
	
	/**
	 * Asks the player to confirm quitting the app
	 * @param app the top level application view
	 * @return true if the player clicks yes
	 */
	public boolean confirmQuit(KlotskiApp app) {
		return JOptionPane.showOptionDialog (app, 
				"Are you sure you want to quit?", "Klotski - Confirm Quit",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
				null, null) == JOptionPane.OK_OPTION;	
	}
	
	public boolean confirmExit(InitialMenu menu) {
		return JOptionPane.showOptionDialog (menu, 
				"Are you sure you want to quit?", "Klotski - Confirm Quit",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
				null, null) == JOptionPane.OK_OPTION;	
	}
	
	/**
	 * Resets all pieces to their original position, sets moves to 0, and
	 * refreshes display
	 */
	public void reset() {
		b.reset();
		app.getMovesCounter().setText(Integer.toString(b.getMoves()));
		app.getPuzzleView().refresh();
	}
	
	/**
	 * Saves the state of the board to a text file at the given path
	 * @return true if successful save, false otherwise
	 */
	public boolean save() {
		// Convert the string to a byte array.
	    String s = b.getConfig() + "\n" + b.toString();
	    byte data[] = s.getBytes();

	    try (OutputStream out = new BufferedOutputStream(
	    		Files.newOutputStream(p))) {
	      out.write(data, 0, data.length);
	    } catch (IOException e) {
	      System.err.println(e);
	      return false;
	    }
	    return true;
	}
	
	public void setConfig(int number) {
		b.setConfig(number);
		b.reset();
		app.getPuzzleView().refresh();
		app.getMovesCounter().setText(Integer.toString(b.getMoves()));
	}
}
