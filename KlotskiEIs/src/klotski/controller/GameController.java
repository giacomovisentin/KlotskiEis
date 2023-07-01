package klotski.controller;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import klotski.model.Board;
import klotski.view.KlotskiApp;
/**
 * Classe GameController che si occupa di selezionare e muovere i pezzi nella board.
 */
public class GameController {
	final KlotskiApp app;
	final Board b;
	
	/**
	 * Costruttore del controller.
	 * @param app la visualizzazione dell'applicazione
	 * @param b il modello della Board
	 */
	public GameController(KlotskiApp app, Board b) {
		this.app = app;
		this.b = b;
	}
	
	/**
	 * Prova a muovere il pezzo selezionato nella direzione passata come input.
	 * Non succede nulla se nessun pezzo viene selezionato.
	 * Se la mossa avviene con successo, incrementa di 1 il contatore delle mosse.
	 * @param direction 0=su, 1=destra, 2=giu', 3=sinistra
	 * @return true se la mossa e' avvenuta con successo, false altrimenti.
	 */
	public boolean move(int direction) {
		if (b.movePiece(direction)) {
			app.getMovesCounter().setText(Integer.toString(b.getMoves()));
			app.getPuzzleView().refresh();
			// congratulate the player if he/she has won
			if (b.checkWin()) {
				showWin();
				KlotskiApp.close();
				new InitialMenuController();
				return true;
			} 
			return true;
		}
			return false;
	}
	
	/**
	 * Prova a tornare indietro di una mossa.
	 * Non succede nulla se non è' ancora stata effettuata la prima mossa.
	 * Se l'undo avviene con successo, decrementa di 1 il contatore delle mosse
	 * @return true se viene ripristinata l'ultima mossa con successo, false altrimenti.
	 */
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
	 * Estrae le coordinate x e y del punto cliccato e prova a selezionare il pezzo Piece
	 * che occupa tale punto.
	 * @param e il MouseEvent passato da PuzzleView
	 * @return true se il pezzo Piece e' stato selezionato con successo, false altrimenti
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
	
	/**
	 * Cerca la mossa migliore per provare a vincere tra le configurazioni salvate di cui si conosce la nextBestMove
	 * Se la mossa viene trovata, viene aggiornato il contatore delle mosse e viene aggioranato il diplay
	 * @return true se la mossa e' stata trovata e compiuta, false altrimenti
	 */
	public boolean nextBestMove() {
	    boolean found = false;
	    int m = b.getMoves();

	    ClassLoader classLoader = GameController.class.getClassLoader();
	    String fileName = "";
	    int conf = b.getConfig();

	    if (conf == 2) {
	        fileName = "conf2Solver.txt";
	    } else if (conf == 4) {
	        fileName = "conf4Solver.txt";
	    } else {
	        JOptionPane.showMessageDialog(app, "Unable to find the next best move", "Hint error", JOptionPane.ERROR_MESSAGE);
	        return false;
	    }

	    try (InputStream inputStream = classLoader.getResourceAsStream(fileName);
	         InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
	         BufferedReader reader = new BufferedReader(inputStreamReader)) {

	        String line;
	        List<String> lines = new ArrayList<>();
	        while (!found && (line = reader.readLine()) != null) {
	        		lines.add(line);
	            for (int j = 0; j < 10; j++) {
	            	line = reader.readLine();
	                lines.add(line);
	            }
	            lines.remove(0);
	            if (lines.equals(b.getBoard())) {
	                lines.removeAll(lines);
	                for (int r = 0; r < 11; r++) {
	                	line = reader.readLine();
	                    lines.add(line);
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
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    if (found) {
	        b.setMoves(++m);
	        b.pushIntoStack();
	        app.getMovesCounter().setText(Integer.toString(b.getMoves()));
	        app.getPuzzleView().refresh();
	        if (b.checkWin()) {
	            showWin();
	            KlotskiApp.close();
	            new InitialMenuController();
	        }
	    } else {
	        JOptionPane.showMessageDialog(app, "Unable to find the next best move", "Hint error", JOptionPane.ERROR_MESSAGE);
	    }
	    
	    return found;
	}
	
	/**
	 * Crea una  nuova finestra di dialogo con l'utente per congratularsi in caso di vittoria
	 */
	public void showWin() {
		Image resizedIcon = new ImageIcon(this.getClass().getResource("/win.png")).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);;
        ImageIcon icon = new ImageIcon(resizedIcon);
		JOptionPane.showMessageDialog(app, "<html><h1 style='font-size: 36pt;'>Congratulations! You win!", "Klotski - WIN", JOptionPane.INFORMATION_MESSAGE, icon);
	}
}
