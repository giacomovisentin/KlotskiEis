package klotski.controller;

import java.awt.event.MouseEvent;
import java.util.*;
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
		System.out.println("-------- Start SOLVE ---------");
		//Charset charset = Charset.forName("UTF-8");
		boolean found = false;
		int end = 12;
		int m = b.getMoves();
		
		//Funzionerà in un file .jar?????? mi sa di no. Occhio anche a initialMenu.java...
		 // Ottenere un riferimento al ClassLoader corrente
        ClassLoader classLoader = GameController.class.getClassLoader();
        // Ottenere il percorso assoluto del file dalla cartella di risorse
        String fileName = "conf2Solver.txt";
        Path p = null;
        
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
	    System.out.println("end: " + end);
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
		    System.out.println("CURRENT BOARD");
		    System.out.println(b.getBoard().toString());
	    	System.out.println(lines.toString());
	    	System.out.println("Match?");
	    	
	    	if(lines.equals(b.getBoard())) {
	    		lines.removeAll(lines);
	    		System.out.println("********* MATCH! *********");
	    		for(int r = i; r < i+11; r++) {
	    			try {
						lines.add(Files.readAllLines(p).get(r));
					} catch (IOException e) {
						e.printStackTrace();
					}
	    		}
	    		System.out.println(lines.toString());
	    		try {
					b.setPieces(lines);
				} catch (Exception e) {
					System.err.println(e);
				}
	    		
	    		found = true;
	    	} else {
	    		lines.removeAll(lines);
	    		System.out.println("Not yet found");
	    	}
	    	//System.out.println(lines.toString());
	    }
	    
	    //System.out.println(b.getMoves());
	    if(found) {
	    	b.setMoves(++m);
			app.getPuzzleView().refresh();
			app.getMovesCounter().setText(Integer.toString(b.getMoves()));
	    } else {
	    	System.out.println("***** NULLA DA FARE*****");
	    }
		
		System.out.println("-------- End SOLVE ---------");
		return true;
	}
}