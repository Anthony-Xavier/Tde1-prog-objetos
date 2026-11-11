package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import cadastro.CadastroMotoristas;
import cadastro.CadastroPassageiros;
import categoria.CategoriaConforto;
import categoria.CategoriaCorrida;
import categoria.CategoriaEconomica;
import categoria.CategoriaPremium;
import corrida.Avaliacao;
import corrida.Corrida;
import corrida.EstadoCorrida;
import corrida.GerenciadorCorridas;
import entidade.Motorista;
import entidade.Passageiro;
import pagamento.Cartao;
import pagamento.Dinheiro;
import pagamento.FormaPagamento;
import pagamento.Pix;

/** Tela Swing para solicitação e gerenciamento de corridas. */
public class CorridasFrame extends JFrame {
    private final GerenciadorCorridas gerenciador;
    private final CadastroPassageiros cadastroPassageiros;
    private final CadastroMotoristas cadastroMotoristas;

    private final JTextField pesquisaPassageiro = new JTextField(22);
    private final JComboBox<Passageiro> passageirosCombo = new JComboBox<>();
    private final JLabel cpfPassageiro = new JLabel("CPF: —");
    private final JTextField pesquisaMotorista = new JTextField(22);
    private final JComboBox<Motorista> motoristasCombo = new JComboBox<>();
    private final JLabel cpfMotorista = new JLabel("CPF: —");
    private final JTextField origem = new JTextField(22);
    private final JTextField destino = new JTextField(22);
    private final JTextField distancia = new JTextField(10);
    private final JComboBox<CategoriaCorrida> categoriaCombo = new JComboBox<>(new CategoriaCorrida[] {
            new CategoriaEconomica(), new CategoriaConforto(), new CategoriaPremium()
    });
    private final JComboBox<FormaPagamento> pagamentoCombo = new JComboBox<>(new FormaPagamento[] {
            new Pix(), new Cartao(), new Dinheiro()
    });

    private final DefaultTableModel solicitadasModel = new DefaultTableModel(new Object[] {
            "ID", "Passageiro", "CPF passageiro", "Origem", "Destino", "Categoria", "Estado"
    }, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable solicitadasTable = new JTable(solicitadasModel);

    private final DefaultTableModel corridasModel = new DefaultTableModel(new Object[] {
            "ID", "Passageiro", "CPF passageiro", "Motorista", "CPF motorista", "Veículo",
            "Origem", "Destino", "Distância (km)", "Categoria", "Pagamento", "Estado",
            "Valor base", "Custo final", "Avaliação"
    }, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable corridasTable = new JTable(corridasModel);
    private final JLabel detalhes = new JLabel("Selecione uma corrida para consultar custo e avaliação.");

    public CorridasFrame(GerenciadorCorridas gerenciador,
            CadastroPassageiros cadastroPassageiros, CadastroMotoristas cadastroMotoristas) {
        super("Gerenciamento de corridas");
        this.gerenciador = gerenciador;
        this.cadastroPassageiros = cadastroPassageiros;
        this.cadastroMotoristas = cadastroMotoristas;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        configurarSeletores();

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Solicitar corrida", criarAbaSolicitacao());
        abas.addTab("Aceitar corrida", criarAbaAceitacao());
        abas.addTab("Corridas / status / avaliação", criarAbaGerenciamento());
        setContentPane(abas);
        atualizarTabelas();
    }

    private void configurarSeletores() {
        passageirosCombo.setRenderer((list, value, index, selected, focus) ->
                new JLabel(value == null ? "Selecione um passageiro" : value.getNome() + " | CPF: " + value.getCpf()));
        motoristasCombo.setRenderer((list, value, index, selected, focus) ->
                new JLabel(value == null ? "Selecione um motorista" : value.getNome() + " | CPF: " + value.getCpf()));
        categoriaCombo.setRenderer((list, value, index, selected, focus) -> new JLabel(value == null ? "" : value.getNome()));
        pagamentoCombo.setRenderer((list, value, index, selected, focus) -> new JLabel(value == null ? "" : value.getDescricao()));

        adicionarFiltro(pesquisaPassageiro, passageirosCombo, cadastroPassageiros::listarTodos, Passageiro::getNome);
        adicionarFiltro(pesquisaMotorista, motoristasCombo, cadastroMotoristas::listarDisponiveis, Motorista::getNome);
        passageirosCombo.addActionListener(event -> {
            Passageiro passageiro = (Passageiro) passageirosCombo.getSelectedItem();
            cpfPassageiro.setText("CPF: " + (passageiro == null ? "—" : passageiro.getCpf()));
        });
        motoristasCombo.addActionListener(event -> {
            Motorista motorista = (Motorista) motoristasCombo.getSelectedItem();
            cpfMotorista.setText("CPF: " + (motorista == null ? "—" : motorista.getCpf()));
        });
    }

    private <T> void adicionarFiltro(JTextField campo, JComboBox<T> combo,
            Supplier<List<T>> fonte, Function<T, String> nome) {
        Runnable filtrar = () -> {
            String termo = campo.getText().trim().toLowerCase(Locale.ROOT);
            T anterior = (T) combo.getSelectedItem();
            DefaultComboBoxModel<T> model = new DefaultComboBoxModel<>();
            for (T item : fonte.get()) {
                String texto = nome.apply(item);
                if (texto != null && texto.toLowerCase(Locale.ROOT).contains(termo)) model.addElement(item);
            }
            combo.setModel(model);
            if (anterior != null) {
                for (int i = 0; i < model.getSize(); i++) {
                    if (model.getElementAt(i) == anterior) {
                        combo.setSelectedIndex(i);
                        return;
                    }
                }
            }
            if (model.getSize() > 0) combo.setSelectedIndex(0);
        };
        campo.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent event) { filtrar.run(); }
            @Override public void removeUpdate(DocumentEvent event) { filtrar.run(); }
            @Override public void changedUpdate(DocumentEvent event) { filtrar.run(); }
        });
        filtrar.run();
    }

    private JPanel criarAbaSolicitacao() {
        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        form.add(new JLabel("Pesquisar passageiro pelo nome (trecho do nome):"));
        form.add(pesquisaPassageiro);
        form.add(new JLabel("Selecionar passageiro; CPF cadastrado será associado à corrida:"));
        form.add(passageirosCombo);
        form.add(new JLabel("CPF do passageiro selecionado:"));
        form.add(cpfPassageiro);
        form.add(new JLabel("Origem:")); form.add(origem);
        form.add(new JLabel("Destino:")); form.add(destino);
        form.add(new JLabel("Distância estimada (km):")); form.add(distancia);
        form.add(new JLabel("Categoria:")); form.add(categoriaCombo);
        form.add(new JLabel("Forma de pagamento:")); form.add(pagamentoCombo);

        JButton solicitar = new JButton("Solicitar corrida");
        solicitar.addActionListener(event -> solicitarCorrida());
        JPanel painel = new JPanel(new BorderLayout());
        painel.add(form, BorderLayout.NORTH);
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rodape.add(solicitar);
        painel.add(rodape, BorderLayout.SOUTH);
        return painel;
    }

    private JPanel criarAbaAceitacao() {
        solicitadasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        solicitadasTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) atualizarMotoristasElegiveis();
        });
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painel.add(new JScrollPane(solicitadasTable), BorderLayout.CENTER);

        JPanel selecao = new JPanel(new GridLayout(0, 2, 8, 8));
        selecao.add(new JLabel("Pesquisar motorista pelo nome (trecho do nome):"));
        selecao.add(pesquisaMotorista);
        selecao.add(new JLabel("Selecionar motorista compatível; CPF cadastrado:"));
        selecao.add(motoristasCombo);
        selecao.add(new JLabel("CPF do motorista selecionado:"));
        selecao.add(cpfMotorista);
        JButton aceitar = new JButton("Aceitar corrida selecionada");
        aceitar.addActionListener(event -> aceitarCorrida());
        selecao.add(new JLabel()); selecao.add(aceitar);
        painel.add(selecao, BorderLayout.SOUTH);
        return painel;
    }

    private JPanel criarAbaGerenciamento() {
        corridasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        corridasTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) mostrarDetalhes();
        });
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painel.add(detalhes, BorderLayout.NORTH);
        painel.add(new JScrollPane(corridasTable), BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        adicionarBotao(botoes, "Iniciar", "iniciar");
        adicionarBotao(botoes, "Finalizar", "finalizar");
        adicionarBotao(botoes, "Cancelar", "cancelar");
        JButton avaliar = new JButton("Avaliar");
        avaliar.addActionListener(event -> avaliarSelecionada());
        botoes.add(avaliar);
        JButton atualizar = new JButton("Atualizar");
        atualizar.addActionListener(event -> atualizarTabelas());
        botoes.add(atualizar);
        painel.add(botoes, BorderLayout.SOUTH);
        return painel;
    }

    private void adicionarBotao(JPanel painel, String texto, String acao) {
        JButton botao = new JButton(texto);
        botao.addActionListener(event -> alterarEstado(acao));
        painel.add(botao);
    }

    private void solicitarCorrida() {
        Passageiro passageiro = (Passageiro) passageirosCombo.getSelectedItem();
        if (passageiro == null) { informar("Pesquise e selecione um passageiro cadastrado."); return; }
        String localOrigem = origem.getText().trim();
        String localDestino = destino.getText().trim();
        if (localOrigem.isEmpty() || localDestino.isEmpty()) { informar("Informe origem e destino."); return; }
        final double km;
        try {
            km = Double.parseDouble(distancia.getText().trim().replace(',', '.'));
            if (!Double.isFinite(km) || km <= 0) throw new NumberFormatException();
        } catch (NumberFormatException exception) { informar("Informe uma distância válida maior que zero."); return; }
        try {
            Corrida corrida = gerenciador.solicitarCorrida(passageiro, localOrigem, localDestino, km,
                    (CategoriaCorrida) categoriaCombo.getSelectedItem(), (FormaPagamento) pagamentoCombo.getSelectedItem());
            informar("Corrida #" + corrida.getId() + " solicitada para " + passageiro.getNome()
                    + " (CPF " + passageiro.getCpf() + ").");
            origem.setText(""); destino.setText(""); distancia.setText("");
            atualizarTabelas();
        } catch (RuntimeException exception) { informar(exception.getMessage()); }
    }

    private void atualizarMotoristasElegiveis() {
        Corrida corrida = selecionada(solicitadasTable);
        String termo = pesquisaMotorista.getText().trim().toLowerCase(Locale.ROOT);
        DefaultComboBoxModel<Motorista> model = new DefaultComboBoxModel<>();
        if (corrida != null) {
            for (Motorista motorista : cadastroMotoristas.listarDisponiveisPorCategoria(corrida.getCategoria())) {
                if (motorista.getNome().toLowerCase(Locale.ROOT).contains(termo)) model.addElement(motorista);
            }
        }
        motoristasCombo.setModel(model);
        if (model.getSize() > 0) motoristasCombo.setSelectedIndex(0);
        Motorista motorista = (Motorista) motoristasCombo.getSelectedItem();
        cpfMotorista.setText("CPF: " + (motorista == null ? "—" : motorista.getCpf()));
    }

    private void aceitarCorrida() {
        Corrida corrida = selecionada(solicitadasTable);
        Motorista motorista = (Motorista) motoristasCombo.getSelectedItem();
        if (corrida == null) { informar("Selecione uma corrida solicitada na tabela."); return; }
        if (motorista == null) { informar("Pesquise e selecione um motorista disponível e compatível."); return; }
        try {
            gerenciador.aceitarCorrida(corrida.getId(), motorista);
            informar("Corrida aceita. Motorista: " + motorista.getNome() + " (CPF " + motorista.getCpf()
                    + "). Custo estimado: " + moeda(corrida.getValorFinal()));
            atualizarTabelas();
        } catch (RuntimeException exception) { informar(exception.getMessage()); }
    }

    private void atualizarTabelas() {
        int idSelecionado = idSelecionado(corridasTable);
        solicitadasModel.setRowCount(0);
        corridasModel.setRowCount(0);
        for (Corrida corrida : gerenciador.listarTodas()) {
            Passageiro passageiro = corrida.getPassageiro();
            Motorista motorista = corrida.getMotorista();
            if (corrida.getEstado() == EstadoCorrida.SOLICITADA) {
                solicitadasModel.addRow(new Object[] {corrida.getId(), passageiro.getNome(), passageiro.getCpf(),
                        corrida.getOrigem(), corrida.getDestino(), corrida.getCategoria().getNome(), corrida.getEstado()});
            }
            Avaliacao avaliacao = corrida.getAvaliacao();
            String textoAvaliacao = avaliacao == null ? "Sem avaliação"
                    : avaliacao.getNota() + "/5" + (avaliacao.getComentario() == null || avaliacao.getComentario().isBlank()
                            ? "" : " - " + avaliacao.getComentario());
            corridasModel.addRow(new Object[] {corrida.getId(), passageiro.getNome(), passageiro.getCpf(),
                    motorista == null ? "Não atribuído" : motorista.getNome(),
                    motorista == null ? "—" : motorista.getCpf(),
                    corrida.getVeiculo() == null ? "Não atribuído" : corrida.getVeiculo().toString(),
                    corrida.getOrigem(), corrida.getDestino(), corrida.getDistanciaEstimada(),
                    corrida.getCategoria().getNome(), corrida.getFormaPagamento().getDescricao(), corrida.getEstado(),
                    motorista == null ? "A definir" : moeda(corrida.getValorBase()),
                    motorista == null ? "A definir" : moeda(corrida.getValorFinal()), textoAvaliacao});
        }
        if (idSelecionado > 0) selecionarPorId(idSelecionado);
        atualizarMotoristasElegiveis();
        mostrarDetalhes();
    }

    private void mostrarDetalhes() {
        Corrida corrida = selecionada(corridasTable);
        if (corrida == null) { detalhes.setText("Selecione uma corrida para consultar custo e avaliação."); return; }
        String custo = corrida.getMotorista() == null ? "A definir" : moeda(corrida.getValorFinal());
        Avaliacao avaliacao = corrida.getAvaliacao();
        String avaliacaoTexto = avaliacao == null ? "Ainda não avaliada"
                : avaliacao.getNota() + "/5 — " + (avaliacao.getComentario() == null ? "" : avaliacao.getComentario());
        detalhes.setText("Corrida #" + corrida.getId() + " | Estado: " + corrida.getEstado()
                + " | Custo final: " + custo + " | Avaliação: " + avaliacaoTexto);
    }

    private void alterarEstado(String acao) {
        Corrida corrida = selecionada(corridasTable);
        if (corrida == null) { informar("Selecione uma corrida na tabela."); return; }
        try {
            switch (acao) {
                case "iniciar": gerenciador.iniciarCorrida(corrida.getId()); break;
                case "finalizar": gerenciador.finalizarCorrida(corrida.getId()); break;
                case "cancelar": gerenciador.cancelarCorrida(corrida.getId()); break;
                default: return;
            }
            atualizarTabelas();
            selecionarPorId(corrida.getId());
        } catch (RuntimeException exception) { informar(exception.getMessage()); }
    }

    private void avaliarSelecionada() {
        Corrida corrida = selecionada(corridasTable);
        if (corrida == null) { informar("Selecione uma corrida finalizada."); return; }
        if (corrida.getEstado() != EstadoCorrida.FINALIZADA) { informar("Só é possível avaliar uma corrida finalizada."); return; }
        if (corrida.getAvaliacao() != null) { informar("Essa corrida já foi avaliada."); return; }
        JTextField nota = new JTextField("5", 4);
        JTextArea comentario = new JTextArea(4, 24);
        JPanel form = new JPanel(new BorderLayout(5, 5));
        JPanel linha = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linha.add(new JLabel("Nota (1 a 5):")); linha.add(nota);
        form.add(linha, BorderLayout.NORTH);
        form.add(new JScrollPane(comentario), BorderLayout.CENTER);
        form.setBorder(BorderFactory.createTitledBorder("Comentário (opcional)"));
        if (JOptionPane.showConfirmDialog(this, form, "Avaliar corrida #" + corrida.getId(),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) != JOptionPane.OK_OPTION) return;
        try {
            gerenciador.avaliarMotorista(corrida.getId(),
                    new Avaliacao(Integer.parseInt(nota.getText().trim()), comentario.getText().trim()));
            atualizarTabelas(); selecionarPorId(corrida.getId());
        } catch (RuntimeException exception) {
            informar(exception instanceof NumberFormatException ? "A nota deve ser um inteiro entre 1 e 5." : exception.getMessage());
        }
    }

    private Corrida selecionada(JTable tabela) {
        int view = tabela.getSelectedRow();
        if (view < 0) return null;
        int model = tabela.convertRowIndexToModel(view);
        int id = ((Number) tabela.getModel().getValueAt(model, 0)).intValue();
        return gerenciador.buscarPorId(id);
    }

    private int idSelecionado(JTable tabela) {
        Corrida corrida = selecionada(tabela);
        return corrida == null ? -1 : corrida.getId();
    }

    private void selecionarPorId(int id) {
        for (int row = 0; row < corridasModel.getRowCount(); row++) {
            if (((Number) corridasModel.getValueAt(row, 0)).intValue() == id) {
                int view = corridasTable.convertRowIndexToView(row);
                if (view >= 0) corridasTable.setRowSelectionInterval(view, view);
                return;
            }
        }
    }

    private String moeda(double valor) {
        return String.format(Locale.forLanguageTag("pt-BR"), "R$ %.2f", valor);
    }

    private void informar(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Corridas", JOptionPane.INFORMATION_MESSAGE);
    }
}
