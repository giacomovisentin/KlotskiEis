package klotski;

import javax.swing.SwingUtilities;
import klotski.controller.InitialMenuController;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            new InitialMenuController();
        });
	}
}
