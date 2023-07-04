package klotski.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Classe che rappresenta la Board e l'insieme dei pezzi Piece
 */
public class Board {
	/**
	 * Array dei pezzi posizionati sulla Board
	 */
	Piece[] pieces;
	
	/**
	 * Pezzo selezionato per effettuare delle azioni su esso
	 */
	Piece selected;
	
	/**
	 * Altezza della Board
	 */
	int height;
	
	/**
	 * Larghezza della Board
	 */
	int width;
	
	/**
	 * Counter delle mosse effettuate
	 */
	int moves;
	
	/**
	 * Numero della configurazione iniziale su cui si ha iniziato a giocare
	 */
	int configuration;
	
	/**
	 * Condizione di vittoria
	 */
	boolean hasWon;
	
	/**
	 * Pila contenente tutte le mosse effettuate da inizio partita
	 */
	Stack<List<String>> pila;
	
	/**
	 * Costruttore base che inizializza altezza e larghezza ai valori standard del klotski.
	 * Inizializza tutti i pezzi secondo la configurazione 1 e pone a 0 il numero delle mosse.
	 */
	public Board() {
		this.pieces = new Piece[10];
		this.configuration = 1;
		this.pila = new Stack<>();
		reset();
		this.height = 5;
		this.width = 4;
	}
	
	/**
	 * Costruttore custom che usa un array custom di pezzi
	 * @param pieces l'array di pezzi posizionati sulla Board
	 */
	public Board(Piece[] pieces) {
		this.pieces = pieces;
		this.height = 5;
		this.width = 4;
		this.moves = 0;
		this.configuration = 1;
		this.pila = new Stack<>();
		this.hasWon = false;
		this.selected = null;
	}
	
	/**
	 * Pone la configurazione al numero passato in input
	 * @param number numero con cui si vuole porre la configurazione della Board
	 */
	public void setConfig(int number) {
		this.configuration = number;
	}
	
	/**
	 * Reads in a set a lines representing a board state and sets the pieces of
	 * this board to match it
	 * @param lines a List of lines with the first being the number of moves,
	 * and the rest representing the x, y, w, and h of pieces
	 * @return true if able to successfully read in from file, false otherwise
	 */
	public boolean setPieces(List<String> lines) {
		int i;
		String[] tokens;
		if (lines.size() < 1 || lines.size() > this.width * this.height + 1) {
			throw new IllegalArgumentException("Illegal list of lines");
		}
		this.moves = Integer.parseInt(lines.get(0).trim());
		pieces = new Piece[lines.size() - 1];
		for (i = 1; i < lines.size(); ++i) {
			tokens = lines.get(i).trim().split("\\s+");
			pieces[i - 1] = new Piece(Integer.parseInt(tokens[0]),
					Integer.parseInt(tokens[1]),
					Integer.parseInt(tokens[2]),
					Integer.parseInt(tokens[3]));
		}
		return true;
	}
	
	/**
	 * hasWon getter
	 * @return true se il giocatore ha vinto, falso altrimenti
	 */
	public boolean checkWin() { return hasWon; }
	
	/**
	 * move getter
	 * @return il counter delle mosse effettuate
	 */
	public int getMoves() { return moves; }
	
	/**
	 * Pone il contatore delle mosse pari al numero passato in input
	 * @param m numero di mosse
	 */
	public void setMoves(int m){
		this.moves = m;
	}
	
	/**
	 * selectedPiece getter
	 * @return il pezzo Piece selezionato
	 */
	public Piece getSelectedPiece() { return selected; }
	
	/**
	 * width getter
	 * @return la larghezza della Board
	 */
	public int getWidth() { return width; }
	
	/**
	 * height getter
	 * @return l'altezza della Board
	 */
	public int getHeight() { return height; }
	
	/**
	 * pieces getter
	 * @return l'array dei pezzi della Board
	 */
	public Piece[] getPieces() { return pieces; }
	
	/**
	 * Seleziona il pezzo corrispondente alle x e y date
	 * @param x la coordinata x del punto che si vuole selezionare
	 * @param y la coordinata y del punto che si vuole selezionare
	 * @return true se un pezzo e' stato selezionato, false altrimenti
	 */
	public boolean selectPiece(int x, int y) {
		for (Piece p : pieces) {
			if (p.containsPoint(x, y)) {
				selected = p;
				return true;
			}
		}
		
		selected = null;
		return false;
	}
	
	/**
	 * Controlla se un dato punto di coordinate (x,y) è occupato da un pezzo
	 * @param x la coordinata x del punto da controllare
	 * @param y la coordinata y del punto da controllare
	 * @return true se il punto e' occupato, false altrimenti
	 */
	public boolean isOccupied(int x, int y) {
		for (Piece p : pieces) {
			if (p.containsPoint(x, y)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Prova a muovere il pezzo selezionato nella direzione data
	 * @param direction 0=su, 1=destra, 2=giu', 3=sinistra
	 * @return true se il pezzo e' stato mosso con successo, false altrimenti
	 */
	public boolean movePiece(int direction) {
		int i;
		
		// if there's no selected piece we can't move, so just return false
		if (selected == null) {
			return false;
		}
		
		// check for a win
		if (selected == pieces[0] && selected.x == 1 &&
				selected.y == 3 && direction == 2) {
			hasWon = true;
			return true;
		}
		
		if (direction == 0) {
			// up
			if (selected.y == 0) return false;
			for (i = selected.x; i < selected.x + selected.w; ++i) {
				if (isOccupied(i, selected.y - 1)) {
					// there's a piece blocking this one
					return false;
				}
			}
		} else if (direction == 1) {
			// right
			if (selected.x + selected.w == width) return false;
			for (i = selected.y; i < selected.y + selected.h; ++i) {
				if (isOccupied(selected.x + selected.w, i)) {
					// there's a piece blocking this one
					return false;
				}
			}
		} else if (direction == 2) {
			// down
			if (selected.y + selected.h == height) return false;
			for (i = selected.x; i < selected.x + selected.w; ++i) {
				if (isOccupied(i, selected.y + selected.h)) {
					// there's a piece blocking this one
					return false;
				}
			}
		} else if (direction == 3) {
			// left
			if (selected.x == 0) return false;
			for (i = selected.y; i < selected.y + selected.h; ++i) {
				if (isOccupied(selected.x - 1, i)) {
					// there's a piece blocking this one
					return false;
				}
			}
		} else {
			throw new IllegalArgumentException("direction must be 0..3");
		}
		
		selected.move(direction);
		++moves;
		pushIntoStack();
		return true;
	}
	/**
	 * Prova a ripristinare la Board nello stato precedente a quello attuale.
	 * @return true se viene ripristinata l'ultima mossa con successo, false altrimenti.
	 */
	public boolean undo() {
		
		if (this.moves > 0 && pila.size() > 1) {
			--this.moves;
			pila.pop();
			setPieces(pila.peek());
			return true;
		}
		else 
			return false;
	}

	/**
	 * Ripristina i pezzi alle posizioni originali, coerentemente alla configurazione corrente.
	 */
	public void reset() {
		pila.clear();
		pieces = new Piece[10];
		if (configuration == 1) {
			pieces[0] = new Piece(1, 0, 2, 2);
			pieces[1] = new Piece(0, 0, 1, 2);
			pieces[2] = new Piece(3, 0, 1, 2);
			pieces[3] = new Piece(0, 2, 1, 2);
			pieces[4] = new Piece(1, 2, 1, 1);
			pieces[5] = new Piece(2, 2, 1, 1);
			pieces[6] = new Piece(3, 2, 1, 2);
			pieces[7] = new Piece(1, 3, 1, 1);
			pieces[8] = new Piece(2, 3, 1, 1);
			pieces[9] = new Piece(1, 4, 2, 1);
		} else if (configuration == 2) {
			pieces[0] = new Piece(1, 0, 2, 2);
			pieces[1] = new Piece(0, 0, 1, 1);
			pieces[2] = new Piece(3, 0, 1, 1);
			pieces[3] = new Piece(0, 1, 1, 2);
			pieces[4] = new Piece(3, 1, 1, 2);
			pieces[5] = new Piece(1, 2, 1, 2);
			pieces[6] = new Piece(0, 3, 1, 1);
			pieces[7] = new Piece(3, 3, 1, 1);
			pieces[8] = new Piece(0, 4, 2, 1);
			pieces[9] = new Piece(2, 4, 2, 1);
		} else if (configuration == 3) {
			pieces[0] = new Piece(2, 1, 2, 2);
			pieces[1] = new Piece(0, 0, 1, 2);
			pieces[2] = new Piece(1, 0, 1, 1);
			pieces[3] = new Piece(2, 0, 1, 1);
			pieces[4] = new Piece(3, 0, 1, 1);
			pieces[5] = new Piece(1, 1, 1, 2);
			pieces[6] = new Piece(0, 2, 1, 2);
			pieces[7] = new Piece(1, 3, 2, 1);
			pieces[8] = new Piece(3, 3, 1, 1);
			pieces[9] = new Piece(2, 4, 2, 1);
		} else if (configuration == 4) {
			pieces[0] = new Piece(1, 0, 2, 2);
			pieces[1] = new Piece(0, 0, 1, 2);
			pieces[2] = new Piece(3, 0, 1, 2);
			pieces[3] = new Piece(0, 2, 1, 2);
			pieces[4] = new Piece(1, 2, 2, 1);
			pieces[5] = new Piece(3, 2, 1, 2);
			pieces[6] = new Piece(1, 3, 1, 1);
			pieces[7] = new Piece(2, 3, 1, 1);
			pieces[8] = new Piece(0, 4, 1, 1);
			pieces[9] = new Piece(3, 4, 1, 1);
		}
		
		moves = 0;
		selected = null;
		hasWon = false;
		pushIntoStack();
	}
	
	/**
	 * Converte la Board a stringa, trascrivendo le informazioni dei pezzi
	 * @return la stringa che rappresenta la Board
	 */
	@Override
	public String toString() {
		String out = Integer.toString(moves) + "\n";
		for (Piece p : pieces) {
			out = out.concat(p.toString() + "\n");
		}
		return out;
	}
	/**
	 * Restituisce lo stato della Board e dei pezzi sotto forma di lista di stringhe
	 * @return la lista dei pezzi
	 */
	public List<String> getBoard(){
		List<String> boardList = new ArrayList<>();
		for (Piece p : pieces) {
			boardList.add(p.toString());
		}
		return boardList;
	}
	
	/**
	 * Restituisce una stringa che salva lo stato della pila, 
	 * ovvero la serie di tutte le mosse compiute e le board precedenti
	 * @return la stringa che rappresenta la pila
	 */
	public String stackToString() {
		String out = "";
		for(List<String> list : pila) {
			out = out.concat(list.get(0)+ "\n");
			for(int i=0 ; i < 10; i++) {
				out = out.concat(list.get(i+1)+"\n");		
			}			
		}
		return out;
	}
	/** 
	 * Restituisce il numero della configurazione corrente
	 * @return numero della configurazione della Board
	 */
	public int getConfig() { return this.configuration; }
	
	/**
	 * Inserisce nella pila lo stato corrente della Board come lista di stringhe,
	 * la quale contiene il contatore delle mosse e lo stato dei pezzi
	 */
	public void pushIntoStack() {
		List<String> lines = new ArrayList<>();
		lines.add(""+this.moves);
		lines.addAll(1,getBoard());
		pila.push(lines);
	}
	
	/**
	 * Inserisce nella pila lo stato della Board passato come parametro
	 * @param lines lista che rappresenta il contatore delle mosse e i pezzi 
	 */
	public void pushIntoStack(List<String> lines) {
		if (lines.size() != 11) {
			throw new IllegalArgumentException("Illegal list of lines");
		}
		pila.push(lines);
	}
	
	/** Svuota la pila delle mosse */
	public void emptyStack() { pila.clear(); }
	
	/** 
	 * Restituisce la dimensione della pila
	 * @return la dimensione della pila
	 */
	public int stackSize() { return pila.size(); }
	
	/** 
	 * Ispeziona l'elemento in cima alla pila senza toglierlo
	 * @return l'ultimo elemento aggiunto alla pila
	 */
	public List<String> stackPeek() { return pila.peek(); }

}
	