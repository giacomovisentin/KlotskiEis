package klotski.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PieceTest {

	@Test
	public void testPiece() {
		try {
			new Piece(0, 0, 1, 1);
			new Piece(3, 4, 8, 5);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	//si controlla che il controllo sulla grandezza dei pezzi è corretto
	@Test
    void testPieceException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Piece(1, 1, 0, 0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Piece(-1, -5, 2, 4);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Piece(1, -2, 3, 4);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Piece(2, 2, 3, 0);
        });
    }
	
	// si controllano i movimenti corretti dei pezzi
	@Test
	public void testMove() {
		Piece testPieceA = new Piece(1, 1, 2, 2);
		Piece testPieceB = new Piece(2, 2, 1, 1);
		Piece testPieceC = new Piece(1, 1, 2, 1);
		Piece testPieceD = new Piece(1, 1, 1, 3);
		
		try {
			testPieceA.move(0); // move up
			testPieceB.move(1); // move right
			testPieceC.move(2); // move down
			testPieceD.move(3); // move left
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		int[] dimensions = testPieceA.getDims();
		assertEquals(dimensions[0], 1);
		assertEquals(dimensions[1], 0);
		assertEquals(dimensions[2], 2);
		assertEquals(dimensions[3], 2);
		
		dimensions = testPieceB.getDims();
		assertEquals(dimensions[0], 3);
		assertEquals(dimensions[1], 2);
		assertEquals(dimensions[2], 1);
		assertEquals(dimensions[3], 1);
		
		dimensions = testPieceC.getDims();
		assertEquals(dimensions[0], 1);
		assertEquals(dimensions[1], 2);
		assertEquals(dimensions[2], 2);
		assertEquals(dimensions[3], 1);
		
		dimensions = testPieceD.getDims();
		assertEquals(dimensions[0], 0);
		assertEquals(dimensions[1], 1);
		assertEquals(dimensions[2], 1);
		assertEquals(dimensions[3], 3);
	}
	
	 @Test
	  void testMoveException() {
	        Piece testPieceB = new Piece(2, 2, 1, 1);
	        assertThrows(IllegalArgumentException.class, () -> {
	            testPieceB.move(4); // questo dovrebbe non essere corretto, dato che non essite move4
	    });
	}

	 //si controlla se sono corrette le dimensioni dei pezzi per poi selezionarli
	@Test
	public void testContainsPoint() {
		Piece testPiece = new Piece(1, 1, 2, 2);
		assertTrue(testPiece.containsPoint(1, 1));
		assertTrue(testPiece.containsPoint(2, 1));
		assertTrue(testPiece.containsPoint(1, 2));
		assertTrue(testPiece.containsPoint(2, 2));
		assertFalse(testPiece.containsPoint(0, 0));
		assertFalse(testPiece.containsPoint(4, 5));
	}
	//si controllano se le dimensioni del pezzo possono essere chiamatie tramite la funzione getDims()
	@Test
	public void testGetDims() {
		Piece testPiece = new Piece(8, 5, 1, 2);
		int[] dimensions = testPiece.getDims();
		assertEquals(dimensions[0], 8);
		assertEquals(dimensions[1], 5);
		assertEquals(dimensions[2], 1);
		assertEquals(dimensions[3], 2);
	}
	//si controlla la corretta concatenazione tra pezzi
	@Test
	public void testToString() {
		Piece testPiece = new Piece(8, 5, 1, 2);
		assertEquals("8 5 1 2", testPiece.toString());
	}

}


