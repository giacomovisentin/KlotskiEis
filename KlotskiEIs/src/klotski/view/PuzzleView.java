package klotski.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import klotski.model.Board;
import klotski.model.Piece;
/**
 * Classe PuzzleView che si occupa di disegnare graficamente lo sfondo e i pezzi sopra di esso
 */

public class PuzzleView extends JPanel {
	private static final long serialVersionUID = 3251334679791843551L;
	
	/** Visualizzazione dell'applicazione */
	KlotskiApp app;
	
	/** Modello della Board */
	Board board;
	
	
	/** Lo spazio dedicato alla grafica */
	Image offScreenImage = null;
	
	/** La grafica che effettivamente colora e disegna i pezzi */
	Graphics offScreenGraphics = null;
	
	/** Spazio tra i quadrati disegnati */
	final int spacing = 5;
	
	/** Dimensione del singolo quadrato */
	final int squareSize = 100;
	
	/**
	 * Restituisce la dimensione di un singolo quadrato della Board
	 * @return la dimensione di un quadrato nel disegno della Board
	 */
	public int getSquareSize() { return squareSize; }
	
	/**
	 * Costruttore base
	 * @param p la visualizzazione dell'applicazione
	 * @param b il modello della Board
	 */
	public PuzzleView(KlotskiApp p, Board b) {
		this.app = p;
		this.board = b;
	}
	
	/**
	 * Fissa le dimensioni dipendentemente  da altezza e larghezza del puzzle
	 */
	@Override
	public Dimension getPreferredSize() {
		int width = squareSize * board.getWidth();
		int height = squareSize * board.getHeight() + 6;
		
		return new Dimension(width, height);
	}
	
	/**
	 * Disegna lo sfondo e i pezzi
	 * @param g componenti grafiche da disegnare
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
				
		if (offScreenImage == null) {
			Dimension s = getPreferredSize();
			offScreenImage = this.createImage(s.width, s.height);
			if (offScreenImage == null) { return; }
			
			offScreenGraphics = offScreenImage.getGraphics();
			redraw();
		}
		
		// copy image into place
		g.drawImage(offScreenImage, 0, 0, this);
		
	}
	
	/**
	 * Disegna lo sfondo e successivamente i pezzi sopra di esso
	 */
	public void redraw() {
		if (offScreenImage == null) { return; }
		
		Dimension s = getPreferredSize();
		offScreenGraphics.clearRect(0, 0, s.width, s.height);
		
		// draw background
		Dimension s1 = getPreferredSize();
		offScreenGraphics.setColor(Color.decode("#ffffff")); 
		offScreenGraphics.fillRect(0, 0, s1.width, s1.height);
		
		// draw all pieces
		Piece[] p = board.getPieces();
		int[] currentDims;
		for (int i = 0; i < p.length; ++i) {
			currentDims = p[i].getDims();
			if (p[i] == board.getSelectedPiece())
				offScreenGraphics.setColor(Color.decode("#90b1c2")); // blue scuro
			else if (i == 0)
				offScreenGraphics.setColor(Color.decode("#003366")); 
			else
				offScreenGraphics.setColor(Color.decode("#E8EFF2"));
			offScreenGraphics.fillRect(currentDims[0] * squareSize + spacing,
					currentDims[1] * squareSize + spacing,
					currentDims[2] * squareSize - spacing * 2,
					currentDims[3] * squareSize - spacing * 2);
			
			// black outline
			offScreenGraphics.setColor(Color.decode("#222222"));
			offScreenGraphics.drawRect(currentDims[0] * squareSize + spacing,
					currentDims[1] * squareSize + spacing,
					currentDims[2] * squareSize - spacing * 2,
					currentDims[3] * squareSize - spacing * 2);
		}
		
		offScreenGraphics.setColor(Color.decode("#003366")); 
		offScreenGraphics.fillRect(squareSize, squareSize * board.getHeight(),
				squareSize * 2, 6);
	}
	
	/**
	 * Ridisegna l'intero PuzzleView quando i pezzi vengono mossi
	 */
	public void refresh() {
		if (offScreenImage == null) { return; }
		offScreenGraphics.clearRect(0, 0, offScreenImage.getWidth(this),
				offScreenImage.getHeight(this));
		redraw();
		repaint();
	}
}
