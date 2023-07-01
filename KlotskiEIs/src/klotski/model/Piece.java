package klotski.model;

/**
 * Classe Piece che rappresenta un singolo pezzo della Board
 */
public class Piece {
	int x; // coordinata x dell'angolo in alto a sinistra del pezzo
	int y; // coordinata y dell'angolo in alto a sinistra del pezzo
	int w; // larghezza orizzontale del pezzo
	int h; // altezza verticale del pezzo
	
	/**
	 * Costruttore base per inizializzare il pezzo
	 * @param x la coordinata x dell'angolo in alto a sinistra del pezzo
	 * @param y la coordinata y dell'angolo in alto a sinistra del pezzo
	 * @param w la larghezza orizzontale del pezzo
	 * @param h l'altezza verticale del pezzo
	 */
	public Piece(int x, int y, int w, int h) {
		if (x < 0 || y < 0 || w < 1 || h < 1)
			throw new IllegalArgumentException("Piece values must be positive");
		this.x = x;
		this.y = y;
		this.w = w;
		this.h= h;
	}

	/**
	 * Muove un pezzo nella Board nella direzione passata come input.
	 * @param direction la direzione definendo che 0=su, 1=destra, 2=giu', 3=sinistra
	 */
	public void move(int direction) {
		if (direction == 0) // su
			this.y--;
		else if (direction == 1) // destra
			this.x++;
		else if (direction == 2) // giu'
			this.y++;
		else if (direction == 3) // sinistra
			this.x--;
		else
			throw new IllegalArgumentException("direction must be 0..3");
	}
	
	/**
	 * Controlla se un determinato pezzo contiene il punto passato in input
	 * @param x la coordinata x del punto che sto testando
	 * @param y la coordinata y del punto che sto testando
	 * @return true se il pezzo contiene il punto selezionato, false altrimenti
	 */
	public boolean containsPoint(int x, int y) {
		return (x >= this.x && y >= this.y &&
				x < (this.x + this.w) && y < (this.y + this.h));
	}
	
	/**
	 * Trova le coordinate x e y dell'angolo alto a sinistra del punto,
	 * cosi' come la larghezza e l'altezza del pezzo.
	 * @return un array di int con i valori delle coordinate x e y del punto
	 * in alto a sinistra, della larghezza e dell'altezza.
	 */
	public int[] getDims() {
		return new int[] {this.x, this.y, this.w, this.h};
	}
	
	/**
	 * Converte le informazioni di un pezzo Piece a stringa
	 * @return la stringa che rappresenta tale pezzo
	 */
	public String toString() {
		String out = "";
		out = out.concat(Integer.toString(x) + " ")
				.concat(Integer.toString(y) + " ")
				.concat(Integer.toString(w) + " ")
				.concat(Integer.toString(h));
		return out;
	}
}
