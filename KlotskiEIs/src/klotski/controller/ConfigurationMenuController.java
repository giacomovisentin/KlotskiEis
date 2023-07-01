package klotski.controller;

import java.awt.Dimension;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import klotski.model.Board;
import klotski.view.KlotskiApp;

/**
 * Controller ConfigurationMenuController crea il menu iniziale quando si clicca su 'Nuova partita' di InitialMenuController.
 * Una volta aperto il menu e' possibile scegliere tra 4 configurazioni per iniziare a giocare.
 */
public class ConfigurationMenuController extends JFrame {
	
	private static final long serialVersionUID = 1L;
	KlotskiApp app;
	Board b;
	
	/**
	 * Costruttore della classe che crea la finestra di dialogo con le 4 configurazioni tra cui scegliere
	 * @param app la visualizzazione dell'applicazione
	 * @param b lo stato della Board
	 * @param im menu interattivo
	 */
	public ConfigurationMenuController(KlotskiApp app, Board b, InitialMenuController im) {
		this.app = app;
		this.b = b;
		
		setTitle("Scelta Configurazione Iniziale");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 100, 400, 600);
		
		int width = 200; 
		JPanel namePanel = new JPanel();
        namePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        namePanel.setLayout(new GridLayout(1, 4));
           
        JButton conf1 = new JButton("Configurazione 1");
        conf1.setMaximumSize(new Dimension(width, 40));
        JButton conf2 = new JButton("Configurazione 2");
        JButton conf3 = new JButton("Configurazione 3");
        JButton conf4 = new JButton("Configurazione 4");

        namePanel.add(conf1);
        namePanel.add(conf2);
        namePanel.add(conf3);
        namePanel.add(conf4);
        
        JPanel imagePanel = new JPanel();
        //panel.setPreferredSize(new Dimension(500, 200));
        imagePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        imagePanel.setLayout(new GridLayout(1, 4));
        
        Image resizedImage1 = new ImageIcon(this.getClass().getResource("/conf1.png")).getImage()
        		.getScaledInstance(width, width * 5/4, Image.SCALE_SMOOTH);
        ImageIcon imageIcon1 = new ImageIcon(resizedImage1);
        JLabel img1 = new JLabel(imageIcon1);
        //img1.setPreferredSize(new Dimension(80, 100));
        Image resizedImage2 = new ImageIcon(this.getClass().getResource("/conf2.png")).getImage()
        		.getScaledInstance(width, width * 5/4, Image.SCALE_SMOOTH);
        ImageIcon imageIcon2 = new ImageIcon(resizedImage2);
        JLabel img2 = new JLabel(imageIcon2);
        
        Image resizedImage3 = new ImageIcon(this.getClass().getResource("/conf3.png")).getImage()
        		.getScaledInstance(width, width * 5/4, Image.SCALE_SMOOTH);
        ImageIcon imageIcon3 = new ImageIcon(resizedImage3);
        JLabel img3 = new JLabel(imageIcon3);
        
        Image resizedImage4 = new ImageIcon(this.getClass().getResource("/conf4.png")).getImage()
        		.getScaledInstance(width, width * 5/4, Image.SCALE_SMOOTH);
        ImageIcon imageIcon4 = new ImageIcon(resizedImage4);
        JLabel img4 = new JLabel(imageIcon4);
        
        
        imagePanel.add(img1);
        imagePanel.add(img2);
        imagePanel.add(img3);
        imagePanel.add(img4);
        
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(namePanel);
        getContentPane().add(imagePanel);
        pack();
        setVisible(true);
        
        conf1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new FileController(app, b, null).setConfig(1);
            	im.setVisible(false); 
            	setVisible(false);
            	app.setVisible(true);
            }
        });
        
        conf2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new FileController(app, b, null).setConfig(2);
            	im.setVisible(false);
            	setVisible(false);
            	app.setVisible(true);
            }
        });
        
        conf3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new FileController(app, b, null).setConfig(3);
            	im.setVisible(false);
            	setVisible(false);
            	app.setVisible(true);
            }
        });
        
        conf4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new FileController(app, b, null).setConfig(4);
            	im.setVisible(false);
            	setVisible(false);
            	app.setVisible(true);
            }
        });     
	}
}

