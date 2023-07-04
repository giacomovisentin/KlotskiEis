package klotski;

import javax.swing.SwingUtilities;
import klotski.controller.InitialMenuController;
/**
 * Classe Main che viene eseguita e crea il primo menu per interfacciarsi
 * con l'utente.
 */
public class Main {
	/**
	 * Metodo main che crea la prima interfaccia grafica InitialMenu 
	 * all'avvio dell'applicazione.
	 * @param args array degli argomenti da linea di comando
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            new InitialMenuController();
        });
	}
}
