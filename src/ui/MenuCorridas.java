package ui;

import java.awt.BorderLayout;

import cadastro.CadastroMotoristas;
import cadastro.CadastroPassageiros;
import corrida.GerenciadorCorridas;

/** Swing entry screen for trip operations (request, acceptance, status and rating). */
public class MenuCorridas extends Menu {
    private static final long serialVersionUID = 1L;

    public MenuCorridas(GerenciadorCorridas gerenciador, CadastroPassageiros passageiros,
            CadastroMotoristas motoristas) {
        super("Corridas");
        CorridasFrame telaCorridas = new CorridasFrame(gerenciador, passageiros, motoristas);
        setLayout(new BorderLayout());
        setContentPane(telaCorridas.getContentPane());
        telaCorridas.dispose();
        setSize(1200, 700);
        setLocationRelativeTo(null);
    }
}
