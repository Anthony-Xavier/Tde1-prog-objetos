import javax.swing.SwingUtilities;

import ui.MenuPrincipal;

public class Main {
    private Main() { }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal().mostrar());
    }
}
