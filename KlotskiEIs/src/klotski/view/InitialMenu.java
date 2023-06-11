package klotski.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import klotski.controller.FileController;
import klotski.model.Board;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

public class InitialMenu extends JFrame {
    private static final long serialVersionUID = 1L;

	public InitialMenu() {
        setTitle("Menu Iniziale");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 200));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setLayout(new GridLayout(3, 1));
        setBounds(400, 200, 400, 600);

        JButton nuovaPartitaButton = new JButton("Nuova Partita");
        nuovaPartitaButton.setPreferredSize(new Dimension(200, 40));
        JButton caricaPartitaButton = new JButton("Carica Partita");
        JButton esciButton = new JButton("Esci");

        nuovaPartitaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Board b = new Board();
        		KlotskiApp app = new KlotskiApp(b);
        		
        		ConfigurationMenu conf = new ConfigurationMenu(app, b, InitialMenu.this);
        		conf.setVisible(true);
            }
        });

        caricaPartitaButton.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		JFileChooser fc = new JFileChooser();
        		
        		Board b = new Board();
        		KlotskiApp app = new KlotskiApp(b);
 
				if (fc.showOpenDialog(app) == JFileChooser.APPROVE_OPTION) {
					
					String path = fc.getSelectedFile().getAbsolutePath();
					new FileController(app, b, Paths.get(path)).open();
					app.setVisible(true);
					setVisible(false);
				}
			}
        });

        esciButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
				if (new FileController(null, null, null).confirmExit(InitialMenu.this)) {
					InitialMenu.this.dispose();
				}
			}
        });

        panel.add(nuovaPartitaButton);
        panel.add(caricaPartitaButton);
        panel.add(esciButton);

        getContentPane().add(panel);
        pack();
        setVisible(true);
    }
}
