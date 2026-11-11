package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

/** Base Swing window for application screens. */
public abstract class Menu extends JFrame {
    private static final long serialVersionUID = 1L;

    protected Menu(String titulo) {
        super(titulo);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(760, 500));
        setLocationRelativeTo(null);
    }

    protected JPanel criarPainelPrincipal() {
        JPanel painel = new JPanel(new BorderLayout(12, 12));
        painel.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 16, 16, 16));
        return painel;
    }

    protected JPanel criarRodape() {
        return new JPanel(new FlowLayout(FlowLayout.RIGHT));
    }

    /** Shows this screen. Kept for compatibility with Main.java. */
    public void mostrar() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
