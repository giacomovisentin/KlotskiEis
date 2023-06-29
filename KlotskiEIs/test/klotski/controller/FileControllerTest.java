package klotski.controller;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import javax.swing.JOptionPane;
import java.nio.file.Path;
import klotski.view.KlotskiApp;
import klotski.model.Board;


class FileControllerTest {
    private FileController fileController;
    private KlotskiApp app;
    private Board board;
    private Path filePath;
    private InitialMenuController menu;

    @BeforeEach
    void setUp() {
        
        board = new Board();
        app = KlotskiApp.getInstance(board);
        fileController = new FileController(app, board, filePath);
    }

    

    //nei seguenti quattro test, si valuta la corretta funzione dei tasti di uscita dal gioco e uscita dalla partita
    @Test 
    void confirmQuitClickYesTest() {
        
    	JOptionPane jOptionPane = new JOptionPane();
        jOptionPane.setValue(JOptionPane.OK_OPTION);

        
        boolean result = fileController.confirmQuit(app);

        
        assertTrue(result);
    }

    @Test
    void confirmQuitClickNoTest() {
        
    	JOptionPane jOptionPane = new JOptionPane();
        jOptionPane.setValue(JOptionPane.NO_OPTION);

        
        boolean result = fileController.confirmQuit(app);

        
        assertFalse(result);
    }
    @Test
    void confirmExitClickYesTest() {
        
    	JOptionPane jOptionPane = new JOptionPane();
        jOptionPane.setValue(JOptionPane.OK_OPTION);

        
        boolean result = fileController.confirmExit(menu);

        
        assertTrue(result);
        
    }

    @Test
    void confirmExitClickNoTest() {
        
    	JOptionPane jOptionPane = new JOptionPane();
        jOptionPane.setValue(JOptionPane.NO_OPTION);

        
        boolean result = fileController.confirmExit(menu);

        
        assertFalse(result);
    }

    //si controlla se funziona il reset della configurazione.
    @Test
    void resetTest() {
        
    	fileController.setConfig(1);
    	board.selectPiece(0, 2);
    	board.movePiece(2);
    	board.selectPiece(1, 4);
    	board.movePiece(1);
        board.setMoves(10);

        
        fileController.reset();
        //si controlla che le mosse tornano a 0
        assertEquals(0, board.getMoves());
        //si controlla che la configurazione torna quella iniziale
        assertEquals(1, board.getConfig());
        
       
    }

   

   
    //si controlla il corretto selezionamento della configurazione
    @Test
    void setConfigTest() {
        fileController.setConfig(2);
        assertEquals(2,board.getConfig());
        }

    }