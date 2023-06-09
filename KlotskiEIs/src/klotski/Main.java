package klotski;

import javax.swing.SwingUtilities;
import klotski.view.InitialMenu;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            InitialMenu menu = new InitialMenu();
        });
	}
}
