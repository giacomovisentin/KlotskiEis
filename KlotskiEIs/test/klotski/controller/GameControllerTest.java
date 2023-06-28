package klotski.controller;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.event.MouseEvent;
import klotski.model.Board;
import klotski.view.KlotskiApp;

class GameControllerTest {
    
    private Board board;
    private GameController controller;

    @BeforeEach
    void setUp() {
       
        board = new Board();
        KlotskiApp app = KlotskiApp.getInstance(board); 
        controller = new GameController(app, board);
    }
    
    //controllo se effettivamente fa la mossa l'applicazione, e se non la fa quando
    @Test
    void moveTest() {
    	board.setConfig(1);
    	assertTrue(board.selectPiece(0, 2));
    	
    	assertTrue(board.movePiece(2));
    	

        boolean result = controller.move(0);
        int Moves = board.getMoves();
        assertTrue(result);
        assertEquals(Moves, 2);
        board.reset();
        board.setConfig(1);
        board.selectPiece(0, 2);
        assertFalse(board.movePiece(0));
        assertEquals(board.getMoves(), 0);
    }
  

	//controllo se il solver funziona, e se quando ci si trova in una configurazione non presente nel file dà un avviso di best move non trovata
	@Test
	void nextBestMoveTest() {
		
		assertFalse(controller.nextBestMove());
		board.setConfig(2);
		board.reset();
		assertTrue(controller.nextBestMove());
		board.setConfig(3);
		board.reset();
		assertFalse(controller.nextBestMove());
		board.setConfig(4);
		board.reset();
		assertTrue(controller.nextBestMove());
	}
}

