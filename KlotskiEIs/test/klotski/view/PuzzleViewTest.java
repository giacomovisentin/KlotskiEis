package klotski.view;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.awt.Dimension;



import klotski.model.Board;

public class PuzzleViewTest {
	//si controllano le dimensioni dell'app, se corrispondono a quanto richiesto
	@Test
	public void testGetSquareSize() {
		Board b = new Board();
		PuzzleView pv = new PuzzleView(null, b);
		assertEquals(100, pv.getSquareSize());
	}
	@Test
	public void testGetPreferredSize() {
		Board b = new Board();
		PuzzleView pv = new PuzzleView(null, b);
		Dimension dim = pv.getPreferredSize();
		assertEquals(400, dim.width);
		assertEquals(506, dim.height);
	}
}