package klotski.controller;

import java.awt.event.MouseEvent;
import java.util.*;

import javax.swing.JOptionPane;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import klotski.model.Board;
import klotski.view.KlotskiApp;

public class GameController {
	final KlotskiApp app;
	final Board b;
	
	/**
	 * Basic constructor
	 * @param app the view application
	 * @param b the model board
	 */
	public GameController(KlotskiApp app, Board b) {
		this.app = app;
		this.b = b;
	}
	
	/**
	 * Attempts to move the selected piece in the input direction. Does nothing
	 * if no piece is selected. If move is successful, updates moves counter
	 * @param direction 0=up, 1=right, 2=down, 3=right
	 * @return true if move was successful, false otherwise
	 */
	public boolean move(int direction) {
		if (b.movePiece(direction)) {
			app.getMovesCounter().setText(Integer.toString(b.getMoves()));
			app.getPuzzleView().refresh();
			return true;
		}
			return false;
	}
	
	public boolean undo() {
		if (b.undo()) {
			app.getMovesCounter().setText(Integer.toString(b.getMoves()));
			app.getPuzzleView().refresh();
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Extracts the x and y coordinates of the point clicked and attempts to
	 * select the Piece occupying that point
	 * @param e the MouseEvent passed from the view
	 * @return true if a Piece was successfully selected, false otherwise
	 */
	public boolean select(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			int xCoord = e.getX() / app.getPuzzleView().getSquareSize();
			int yCoord = e.getY() / app.getPuzzleView().getSquareSize();
						
			// tell the model to select the piece
			b.selectPiece(xCoord, yCoord);
			
			// tell the view to update
			app.getPuzzleView().refresh();
			
			return true;
		}
		
		return false;
	}
	
	public boolean nextBestMove() {
		boolean found = false;
		int end = 12;
		int m = b.getMoves();
		
		//Funzionerà in un file .jar?????? mi sa di no. Occhio anche a initialMenu.java...
        ClassLoader classLoader = GameController.class.getClassLoader();
        String fileName = "";
        Path p = null;
        int conf = b.getConfig();

        if(conf == 2) {
        	fileName = "conf2Solver.txt";
        } else if(conf == 4) {
        	fileName = "conf4Solver.txt";
        } else {
        	JOptionPane.showMessageDialog(app, "Unable to find the next best move", "Hint error", JOptionPane.ERROR_MESSAGE);
        	return false;
        }
        
		try {
			p = Paths.get(classLoader.getResource(fileName).toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
        
	    List<String> lines = new ArrayList<>();
    	
	    try {
			end = Files.readAllLines(p).size();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    for(int i = 0; !found && i < end;) {
	    	for(int j = 0; j < 11; i++) {
	    		try {
					lines.add(Files.readAllLines(p).get(i));
				} catch (IOException e) {
					e.printStackTrace();
				}
	    		j++;
	    	}

	    	lines.remove(0);
	    	
	    	if(lines.equals(b.getBoard())) {
	    		lines.removeAll(lines);
	    		for(int r = i; r < i+11; r++) {
	    			try {
						lines.add(Files.readAllLines(p).get(r));
					} catch (IOException e) {
						e.printStackTrace();
					}
	    		}
	    		try {
					b.setPieces(lines);
				} catch (Exception e) {
					System.err.println(e);
				}
	    		
	    		found = true;
	    	} else {
	    		lines.removeAll(lines);
	    	}
	    }
	    
	    if(found) {
	    	b.setMoves(++m);
	    	b.pushIntoStack();
			app.getPuzzleView().refresh();
			app.getMovesCounter().setText(Integer.toString(b.getMoves()));
	    } else {
	    	JOptionPane.showMessageDialog(app, "Unable to find the next best move", "Hint error", JOptionPane.ERROR_MESSAGE);
	    }
		return found;
	}
}
