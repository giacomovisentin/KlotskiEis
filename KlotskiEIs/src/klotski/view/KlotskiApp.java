package klotski.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Paths;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

import klotski.model.Board;
import klotski.controller.FileController;
import klotski.controller.GameController;
import klotski.controller.InitialMenuController;

/**
 * Classe KlotskiApp che si occupa della rappresentazione dell'interfaccia grafica 
 * e della visualizzazione della Board
 */
public class KlotskiApp extends JFrame {
	
	/** Modello della Board */
	Board board;
	
	/** Interfaccia grafica */
	PuzzleView puzzleView;
	
	/** Contatore delle mosse */
	JLabel movesCounter;
	
	/** Bottone per il reset */
	JButton btnReset;
	
	/** Bottone per l'aiuto */
	JButton btnHint;
	
	/** Bottone per l'undo */
	JButton btnUndo;
	
	/** Bottone per salvare */
	JButton btnSave;
	
	/** Bottone per uscire */
	JButton btnQuit;
	
	/** Punto salvato per il calcolo dello spostamento del cursore */
	Point storedPoint;
	
private static KlotskiApp instance;

	/**
	 * Restituisce l'istanza di KlotskiApp.
	 * Se e' nulla, la inizializza secondo la Board b passata come input, altrimenti restituisce quella esistente.
	 * @param b Board
	 * @return istanza di KlotskiApp
	 */
	public static KlotskiApp getInstance(Board b) {
		if (instance == null) {
			instance = new KlotskiApp(b);
		}
		return instance;
	}
	
	/**
	 * Chiude l'istanza di KlotskyApp, resettandola a null.
	 */	
	public static void close() {
		if(instance != null) {
			instance.dispose();
			instance = null;
		}
	}
	
	private static final long serialVersionUID = 5052390254637954176L;
	
	/** Pannello che crea l'interfaccia grafica con i vari bottoni */
	private JPanel contentPane;

	/** 
	 * Restituisce l'etichetta che mostra il contatore delle mosse
	 * @return il display del contatore delle mosse 
	 */
	public JLabel getMovesCounter() { return movesCounter; }
	
	/** 
	 * Restituisce la view del puzzle
	 * @return il disegno dei pezzi sulla board visualizzati sull'interfaccia 
	 */
	public PuzzleView getPuzzleView() { return puzzleView; }
	
	/** 
	 * Bottone per il reset
	 * @return bottone interattivo per la funzione 'Reset' 
	 */
	public JButton getResetButton() { return btnReset; }
	
	/** 
	 * Bottone per l'aiuto
	 * @return bottone interattivo per la funzione 'Hint' 
	 */
	public JButton getHintButton() { return btnHint; }
	
	/**
	 * Bottone per la funzione undo
	 *  @return bottone interattivo per la funzione 'Undo' 
	 */
	public JButton getUndoButton() { return btnUndo; }
	
	/**
	 * Bottone per il salvataggio su file
	 *  @return bottone interattivo per la funzione 'Save as...' 
	 */
	public JButton getSaveButton() { return btnSave; }
	
	/**
	 * Bottone per uscire dal gioco senza salvare
	 *  @return bottone interattivo per la funzione 'Quit' 
	 */
	public JButton getQuitButton() { return btnQuit; }
	


	/**
	 * Costruttore che crea il frame del gioco composto da interfaccia e bottoni 
	 * @param b lo stato della Board
	 */
	private KlotskiApp(Board b) {
		this.board = b;
		setTitle("Klotski");
		setFocusable(true);
		requestFocus();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 650, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JFileChooser fc = new JFileChooser();
		
		/********************\
		 *   Close Window   *
		\********************/
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				if (new FileController(b).confirmQuit(KlotskiApp.this)) {
					KlotskiApp.close();
				}
			}
		});
		
		
		/*******************\
		 *   Puzzle View   *
		\*******************/
		
		puzzleView = new PuzzleView(instance, board);
		puzzleView.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				KlotskiApp.this.requestFocus();
				storedPoint = e.getPoint();
				new GameController(KlotskiApp.this, board).select(e);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				KlotskiApp.this.requestFocus();
				Point newPoint = e.getPoint();
				int dx = newPoint.x - storedPoint.x;
				int dy = newPoint.y - storedPoint.y;
				if (Math.abs(dx) > 10 || Math.abs(dy) > 10) {
					// mouse dragged
					if (Math.abs(dx) > Math.abs(dy)) {
						// horizontal drag
						if (dx > 0) {
							new GameController(KlotskiApp.this, board)
							.move(1);
						} else {
							new GameController(KlotskiApp.this, board)
							.move(3);
						}
					} else {
						// vertical drag
						if (dy > 0) {
							new GameController(KlotskiApp.this, board)
							.move(2);
						} else {
							new GameController(KlotskiApp.this, board)
							.move(0);
						}
					}
				}
			}
		});
		puzzleView.setBounds(12, 12, puzzleView.getPreferredSize().width,
				puzzleView.getPreferredSize().height);
		contentPane.add(puzzleView);
		
		
		/*************************\
		 *   Keyboard Listener   *
		\*************************/
		
		this.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				int kc = e.getKeyCode();
				if (kc == KeyEvent.VK_UP || kc == KeyEvent.VK_W || 
						kc == KeyEvent.VK_K) {
					// up
					new GameController(KlotskiApp.this, board).move(0);
				} else if (kc == KeyEvent.VK_RIGHT || kc == KeyEvent.VK_D ||
						kc == KeyEvent.VK_L) {
					// right
					new GameController(KlotskiApp.this, board).move(1);
				} else if (kc == KeyEvent.VK_DOWN || kc == KeyEvent.VK_S ||
						kc == KeyEvent.VK_J) {
					// down
					new GameController(KlotskiApp.this, board).move(2);
				} else if (kc == KeyEvent.VK_LEFT || kc == KeyEvent.VK_A ||
						kc == KeyEvent.VK_H) {
					// left
					new GameController(KlotskiApp.this, board).move(3);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyTyped(KeyEvent e) {}
			
		});
		
		
		/*******************\
		 *   GUI BUttons   *
		\*******************/
		
		btnHint = new JButton("Hint");
		btnHint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new GameController(KlotskiApp.this, board).nextBestMove();
			}
		});
		btnHint.setFocusable(false);
		btnHint.setBounds(475, 25, 100, 25);
		contentPane.add(btnHint);
		
		btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new FileController(KlotskiApp.this, board).reset();
			}
		});
		btnReset.setFocusable(false);
		btnReset.setBounds(475, 50, 100, 25);
		contentPane.add(btnReset);
		
		btnUndo = new JButton("Undo");
		btnUndo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new GameController(KlotskiApp.this, board).undo();
			}
		});
		btnUndo.setFocusable(false);
		btnUndo.setBounds(475, 75, 100, 25);
		contentPane.add(btnUndo);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (fc.showSaveDialog(KlotskiApp.this) == 
						JFileChooser.APPROVE_OPTION) {
					String path = fc.getSelectedFile().getAbsolutePath();
					new FileController(board, Paths.get(path)).save();
					KlotskiApp.close();
					new InitialMenuController();
				}
			}
		});
		btnSave.setFocusable(false);
		btnSave.setBounds(475, 475, 100, 25);
		contentPane.add(btnSave);
		
		btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (new FileController(b).confirmQuit(KlotskiApp.this)) {
					KlotskiApp.close();
					new InitialMenuController();
				}
			}
		});
		btnQuit.setFocusable(false);
		btnQuit.setBounds(475, 500, 100, 25);
		contentPane.add(btnQuit);
		
		JButton btnUp = new JButton("↑");
		btnUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new GameController(KlotskiApp.this, board).move(0);
			}
		});
		btnUp.setFocusable(false);
		btnUp.setBounds(500, 200, 50, 25);
		contentPane.add(btnUp);
		
		JButton btnRight = new JButton("→");
		btnRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new GameController(KlotskiApp.this, board).move(1);
			}
		});
		btnRight.setFocusable(false);
		btnRight.setBounds(550, 250, 50, 25);
		contentPane.add(btnRight);
		
		JButton btnLeft = new JButton("←");
		btnLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new GameController(KlotskiApp.this, board).move(3);
			}
		});
		btnLeft.setFocusable(false);
		btnLeft.setBounds(450, 250, 50, 25);
		contentPane.add(btnLeft);
		
		JButton btnDown = new JButton("↓");
		btnDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new GameController(KlotskiApp.this, board).move(2);
			}
		});
		btnDown.setFocusable(false);
		btnDown.setBounds(500, 300, 50, 25);
		contentPane.add(btnDown);
		
		
		
		/******************\
		 *   GUI Labels   *
		\******************/

		JLabel lblMoves = new JLabel("Moves:");
		lblMoves.setBounds(475, 150, 66, 15);
		contentPane.add(lblMoves);
		
		movesCounter = new JLabel("0");
		movesCounter.setBounds(550, 150, 66, 15);
		contentPane.add(movesCounter);
	}
}
