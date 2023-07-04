package klotski.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import klotski.model.Board;
import klotski.view.KlotskiApp;
/**
 * Controller FileController che si occupa di aprire una partita salvata su un file, di salvare la partita corrente,
 * di terminare la partita senza salvare e di resettare la partita alla configurazione originale.
 */
public class FileController {
	
	/** Visualizzazione dell'applicazione */
	KlotskiApp app;
	
	/** Modello della Board */
	Board b;
	
	/** Percorso del file che voglio salvare o aprire */
	final Path p;
	
	/**
	 * Costruttore base usato dal metodo open()
	 * @param app la visualizzazione dell'applicazione
	 * @param b lo stato della Board
	 * @param p il percorso del file
	 */
	public FileController(KlotskiApp app, Board b, Path p) {
		this.app = app;
		this.b = b;
		this.p = p;
	}
	
	/**
	 * Costruttore custom usato dal metodo save()
	 * @param b lo stato della Board
	 * @param p il percorso del file
	 */
	public FileController(Board b, Path p) {
		this.app = null;
		this.b = b;
		this.p = p;
	}
	
	/**
	 * Costruttore custom usato dai metodi reset() e setConfig()
	 * @param app la visualizzazione dell'applicazione
	 * @param b lo stato della Board
	 */
	public FileController(KlotskiApp app, Board b) {
		this.app = app;
		this.b = b;
		this.p = null;
	}
	
	/**
	 * Costruttore custom usato dal metodo confirmQuit()
	 * @param b modello della Board
	 */
	public FileController(Board b) {
		this.app = null;
		this.b = b;
		this.p = null;
	}
	
	/**
	 * Apre da file lo stato di una partita iniziata in precedenza, con la possibilita' di riavvolgere le ultime mmosse fatte.
	 * @return true se la partita e' stata aperta da file e ripresa con successo, false altrimenti
	 */
	public boolean open() {
		b.reset();
		b.emptyStack();
		int conf = 1;
		int num = 0;
		try {
			conf = Integer.parseInt(Files.readAllLines(p).get(0));
			num = Files.readAllLines(p).size();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		b.setConfig(conf);
		if ((num-1)%11 != 0) {
			throw new IllegalArgumentException("Illegal list of lines");
		}
		
		int nIter = (num-1)/11;
				
		try {
			for(int i = 0; i < nIter; i++) {
				List<String> lines = new ArrayList<>();
				for(int j = 0; j < 11; j++) {
		    		try {
		    			lines.add(Files.readAllLines(p).get(j+1+i*11));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}	    		
				b.pushIntoStack(lines);				
			}
		} catch (Exception e) {
			System.err.println(e);
			return false;
		}
		b.setPieces(b.stackPeek());
		app.getPuzzleView().refresh();
		app.getMovesCounter().setText(Integer.toString(b.getMoves()));
		return true;
	}
	
	/**
	 * Chiede al giocatore di confermare di voler uscire dall'applicazione
	 * @param app la visualizzazione dell'applicazione
	 * @return true se il giocatore ha premuto su 'yes'
	 */
	public boolean confirmQuit(KlotskiApp app) {
		return JOptionPane.showOptionDialog (app, 
				"Are you sure you want to quit?", "Klotski - Confirm Quit",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
				null, null) == JOptionPane.OK_OPTION;	
	}
	
	/**
	 * Chiede al giocatore di confermare di voler uscire dall'applicazione
	 * @param menu il menu 
	 * @return true se il giocatore ha premuto su 'yes'
	 */
	public boolean confirmExit(InitialMenuController menu) {
		return JOptionPane.showOptionDialog (menu, 
				"Are you sure you want to quit?", "Klotski - Confirm Quit",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
				null, null) == JOptionPane.OK_OPTION;	
	}
	
	/**
	 * Ripristina tutti i pezzi alla loro posizione originale,
	 * fissa il counter delle mosse a zero e aggiorna il display
	 */
	public void reset() {
		b.reset();
		app.getMovesCounter().setText(Integer.toString(b.getMoves()));
		app.getPuzzleView().refresh();
	}
	
	/**
	 * Salva lo stato della Board su file di testo ad uno specifico percoso,
	 * tenendo memoria delle mosse effettuate in precedenza dall'inizio della partita
	 * @return true se salvagto con successo, false altrimenti
	 */
	public boolean save() {
		String s = b.getConfig() + "\n" + b.stackToString();
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
	
	/**
	 * Pone il valore di input come numero della configurazione,
	 * aggiornando di conseguenza il display con la Board e la disposizione dei pezzi
	 * @param number il numero della configurazione
	 */
	public void setConfig(int number) {
		b.setConfig(number);
		b.reset();
		app.getPuzzleView().refresh();
		app.getMovesCounter().setText(Integer.toString(b.getMoves()));
	}
}
