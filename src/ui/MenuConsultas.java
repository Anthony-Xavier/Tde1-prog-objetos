package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.Locale;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import cadastro.CadastroMotoristas;
import cadastro.CadastroPassageiros;
import corrida.Corrida;
import corrida.GerenciadorCorridas;
import entidade.Motorista;
import entidade.Passageiro;

/** Swing search and details screens for passengers, drivers, and trips. */
public class MenuConsultas extends Menu {
    private static final long serialVersionUID = 1L;
    private final CadastroPassageiros cadastroPassageiros;
    private final CadastroMotoristas cadastroMotoristas;
    private final GerenciadorCorridas gerenciadorCorridas;

    public MenuConsultas(CadastroPassageiros passageiros, CadastroMotoristas motoristas,
            GerenciadorCorridas corridas) {
        super("Consultas");
        this.cadastroPassageiros = passageiros;
        this.cadastroMotoristas = motoristas;
        this.gerenciadorCorridas = corridas;
        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Corridas", criarTabelaCorridas());
        abas.addTab("Passageiros", criarPesquisaPassageiros());
        abas.addTab("Motoristas", criarPesquisaMotoristas());
        setContentPane(abas);
        setSize(1100, 620);
    }

    private JPanel criarTabelaCorridas() {
        String[] colunas = {"ID", "Passageiro", "CPF passageiro", "Motorista", "CPF motorista", "Origem", "Destino", "Distância", "Categoria", "Pagamento", "Estado", "Valor base", "Valor final", "Avaliação"};
        DefaultTableModel model = modelo(colunas);
        for (Corrida c : gerenciadorCorridas.listarTodas()) {
            Motorista m = c.getMotorista();
            model.addRow(new Object[] {c.getId(), c.getPassageiro().getNome(), c.getPassageiro().getCpf(),
                    m == null ? "—" : m.getNome(), m == null ? "—" : m.getCpf(), c.getOrigem(), c.getDestino(),
                    c.getDistanciaEstimada(), c.getCategoria().getNome(), c.getFormaPagamento().getDescricao(),
                    c.getEstado(), m == null ? "A definir" : String.format(Locale.forLanguageTag("pt-BR"), "R$ %.2f", c.getValorBase()),
                    m == null ? "A definir" : String.format(Locale.forLanguageTag("pt-BR"), "R$ %.2f", c.getValorFinal()),
                    c.getAvaliacao() == null ? "Sem avaliação" : c.getAvaliacao().getNota() + "/5 - " + c.getAvaliacao().getComentario()});
        }
        JPanel painel = new JPanel(new BorderLayout());
        painel.add(new JScrollPane(new JTable(model)), BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarPesquisaPassageiros() {
        JTextField pesquisa = new JTextField(22);
        JComboBox<Passageiro> resultados = new JComboBox<>();
        resultados.setRenderer((list, value, index, selected, focus) -> new JLabel(value == null ? "" : value.getNome() + " | CPF: " + value.getCpf()));
        JTextArea detalhes = areaDetalhes();
        filtrar(pesquisa, resultados, cadastroPassageiros.listarTodos(), Passageiro::getNome);
        resultados.addActionListener(event -> {
            Passageiro p = (Passageiro) resultados.getSelectedItem();
            if (p == null) { detalhes.setText(""); return; }
            StringBuilder texto = new StringBuilder(p.toString()).append("\n\nHistórico de corridas:\n");
            for (Corrida c : p.getHistorico()) texto.append("Corrida #").append(c.getId()).append(" | ")
                    .append(c.getOrigem()).append(" → ").append(c.getDestino()).append(" | ").append(c.getEstado()).append('\n');
            detalhes.setText(texto.toString());
        });
        return painelBusca(pesquisa, resultados, detalhes, "Pesquisar passageiro por nome:");
    }

    private JPanel criarPesquisaMotoristas() {
        JTextField pesquisa = new JTextField(22);
        JComboBox<Motorista> resultados = new JComboBox<>();
        resultados.setRenderer((list, value, index, selected, focus) -> new JLabel(value == null ? "" : value.getNome() + " | CPF: " + value.getCpf()));
        JTextArea detalhes = areaDetalhes();
        filtrar(pesquisa, resultados, cadastroMotoristas.listarTodos(), Motorista::getNome);
        resultados.addActionListener(event -> {
            Motorista m = (Motorista) resultados.getSelectedItem();
            detalhes.setText(m == null ? "" : m.toString());
        });
        return painelBusca(pesquisa, resultados, detalhes, "Pesquisar motorista por nome:");
    }

    private JPanel painelBusca(JTextField pesquisa, JComboBox<?> resultados, JTextArea detalhes, String rotulo) {
        JPanel topo = new JPanel(new GridLayout(2, 2, 8, 8));
        topo.add(new JLabel(rotulo)); topo.add(pesquisa);
        topo.add(new JLabel("Selecione um resultado:")); topo.add(resultados);
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.add(topo, BorderLayout.NORTH);
        painel.add(new JScrollPane(detalhes), BorderLayout.CENTER);
        return painel;
    }

    private <T> void filtrar(JTextField campo, JComboBox<T> combo, List<T> registros,
            java.util.function.Function<T, String> nome) {
        Runnable atualizar = () -> {
            String termo = campo.getText().trim().toLowerCase(Locale.ROOT);
            DefaultComboBoxModel<T> model = new DefaultComboBoxModel<>();
            for (T registro : registros) if (nome.apply(registro).toLowerCase(Locale.ROOT).contains(termo)) model.addElement(registro);
            combo.setModel(model);
        };
        campo.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { atualizar.run(); }
            @Override public void removeUpdate(DocumentEvent e) { atualizar.run(); }
            @Override public void changedUpdate(DocumentEvent e) { atualizar.run(); }
        });
        atualizar.run();
    }

    private JTextArea areaDetalhes() {
        JTextArea area = new JTextArea(12, 50);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    private DefaultTableModel modelo(String... colunas) {
        return new DefaultTableModel(colunas, 0) {
            private static final long serialVersionUID = 1L;
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
    }
}
