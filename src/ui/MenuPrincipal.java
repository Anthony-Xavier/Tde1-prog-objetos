package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import cadastro.CadastroMotoristas;
import cadastro.CadastroPassageiros;
import cadastro.CadastroVeiculos;
import corrida.GerenciadorCorridas;
import utils.Dados;

/** Main application window. */
public class MenuPrincipal extends Menu {
    private static final long serialVersionUID = 1L;

    private final Dados dados = Dados.ler();
    private final CadastroPassageiros cadastroPassageiros = dados.getCadastroPassageiros();
    private final CadastroMotoristas cadastroMotoristas = dados.getCadastroMotoristas();
    private final CadastroVeiculos cadastroVeiculos = dados.getCadastroVeiculos();
    private final GerenciadorCorridas gerenciadorCorridas = dados.getGerenciadorCorridas();

    public MenuPrincipal() {
        super("Sistema de transporte");
        JPanel painel = criarPainelPrincipal();
        JLabel titulo = new JLabel("Sistema de transporte", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 24f));
        painel.add(titulo, BorderLayout.NORTH);

        JPanel botoes = new JPanel(new GridLayout(3, 1, 10, 10));
        botoes.setPreferredSize(new Dimension(360, 190));
        JButton cadastros = new JButton("Cadastros");
        cadastros.addActionListener(event -> new MenuCadastros(
                cadastroPassageiros, cadastroMotoristas, cadastroVeiculos).mostrar());
        JButton corridas = new JButton("Solicitar e gerenciar corridas");
        corridas.addActionListener(event -> new MenuCorridas(
                gerenciadorCorridas, cadastroPassageiros, cadastroMotoristas).mostrar());
        JButton consultas = new JButton("Consultas");
        consultas.addActionListener(event -> new MenuConsultas(
                cadastroPassageiros, cadastroMotoristas, gerenciadorCorridas).mostrar());
        botoes.add(cadastros);
        botoes.add(corridas);
        botoes.add(consultas);
        JPanel centro = new JPanel(new GridLayout(1, 1));
        centro.add(botoes);
        painel.add(centro, BorderLayout.CENTER);
        setContentPane(painel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dados.gravar();
            }
        });
        setSize(560, 390);
        setLocationRelativeTo(null);
    }
}
