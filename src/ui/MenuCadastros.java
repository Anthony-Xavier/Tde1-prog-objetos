package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import cadastro.CadastroMotoristas;
import cadastro.CadastroPassageiros;
import cadastro.CadastroVeiculos;
import entidade.Motorista;
import entidade.Passageiro;
import veiculo.Carro;
import veiculo.Moto;
import veiculo.Van;
import veiculo.Veiculo;

/** Swing forms and lists for passengers, drivers, and vehicles. */
public class MenuCadastros extends Menu {
    private static final long serialVersionUID = 1L;

    private final CadastroPassageiros cadastroPassageiros;
    private final CadastroMotoristas cadastroMotoristas;
    private final CadastroVeiculos cadastroVeiculos;

    private final DefaultTableModel passageirosModel = modelo("Nome", "CPF", "Telefone");
    private final DefaultTableModel motoristasModel = modelo("Nome", "CPF", "Telefone", "CNH", "Veículo");
    private final DefaultTableModel veiculosModel = modelo("Tipo", "Placa", "Modelo");

    public MenuCadastros(CadastroPassageiros passageiros, CadastroMotoristas motoristas,
            CadastroVeiculos veiculos) {
        super("Cadastros");
        this.cadastroPassageiros = passageiros;
        this.cadastroMotoristas = motoristas;
        this.cadastroVeiculos = veiculos;

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Passageiros", criarAbaPassageiros());
        abas.addTab("Motoristas", criarAbaMotoristas());
        abas.addTab("Veículos", criarAbaVeiculos());
        setContentPane(abas);
        atualizarTabelas();
        setSize(850, 600);
    }

    private JPanel criarAbaPassageiros() {
        JTextField nome = new JTextField(22);
        JTextField cpf = new JTextField(22);
        JTextField telefone = new JTextField(22);
        JPanel form = formulario(new String[] {"Nome:", "CPF:", "Telefone:"}, nome, cpf, telefone);
        JButton salvar = new JButton("Cadastrar passageiro");
        salvar.addActionListener(event -> executar(() -> {
            exigirTexto(nome.getText(), "Nome");
            String cpfLimpo = normalizarCpf(cpf.getText());
            exigirTexto(telefone.getText(), "Telefone");
            if (cadastroPassageiros.consultarPorCpf(cpfLimpo) != null) throw new IllegalArgumentException("Já existe passageiro com esse CPF.");
            cadastroPassageiros.cadastrar(new Passageiro(nome.getText().trim(), cpfLimpo, telefone.getText().trim()));
            limpar(nome, cpf, telefone);
            atualizarTabelas();
            mensagem("Passageiro cadastrado.");
        }));
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.add(form, BorderLayout.NORTH);
        painel.add(new JScrollPane(new JTable(passageirosModel)), BorderLayout.CENTER);
        painel.add(rodape(salvar), BorderLayout.SOUTH);
        return painel;
    }

    private JPanel criarAbaMotoristas() {
        JTextField nome = new JTextField(22);
        JTextField cpf = new JTextField(22);
        JTextField telefone = new JTextField(22);
        JTextField cnh = new JTextField(22);
        JComboBox<Veiculo> veiculo = new JComboBox<>();
        atualizarComboVeiculos(veiculo);
        veiculo.setRenderer((list, value, index, selected, focus) -> new JLabel(value == null ? "Cadastre um veículo" : value.toString()));
        JPanel form = formulario(new String[] {"Nome:", "CPF:", "Telefone:", "CNH:", "Veículo:"}, nome, cpf, telefone, cnh, veiculo);
        JButton salvar = new JButton("Cadastrar motorista");
        salvar.addActionListener(event -> executar(() -> {
            exigirTexto(nome.getText(), "Nome");
            String cpfLimpo = normalizarCpf(cpf.getText());
            exigirTexto(telefone.getText(), "Telefone");
            exigirTexto(cnh.getText(), "CNH");
            Veiculo selecionado = (Veiculo) veiculo.getSelectedItem();
            if (selecionado == null) throw new IllegalArgumentException("Cadastre um veículo antes do motorista.");
            if (cadastroMotoristas.consultarPorCpf(cpfLimpo) != null) throw new IllegalArgumentException("Já existe motorista com esse CPF.");
            if (cadastroMotoristas.veiculoJaVinculado(selecionado)) throw new IllegalArgumentException("Esse veículo já está associado a outro motorista.");
            cadastroMotoristas.cadastrar(new Motorista(nome.getText().trim(), cpfLimpo,
                    telefone.getText().trim(), cnh.getText().trim(), selecionado));
            limpar(nome, cpf, telefone, cnh);
            atualizarTabelas();
            mensagem("Motorista cadastrado.");
        }));
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.add(form, BorderLayout.NORTH);
        painel.add(new JScrollPane(new JTable(motoristasModel)), BorderLayout.CENTER);
        painel.add(rodape(salvar), BorderLayout.SOUTH);
        return painel;
    }

    private JPanel criarAbaVeiculos() {
        JComboBox<String> tipo = new JComboBox<>(new String[] {"Carro", "Moto", "Van"});
        JTextField placa = new JTextField(22);
        JTextField modelo = new JTextField(22);
        JPanel form = formulario(new String[] {"Tipo:", "Placa:", "Modelo:"}, tipo, placa, modelo);
        JButton salvar = new JButton("Cadastrar veículo");
        salvar.addActionListener(event -> executar(() -> {
            String placaLimpa = placa.getText().trim().toUpperCase().replace("-", "");
            if (!placaLimpa.matches("[A-Z]{3}[0-9][A-Z0-9][0-9]{2}")) throw new IllegalArgumentException("Placa inválida. Exemplos: ABC1234 ou ABC1D23.");
            exigirTexto(modelo.getText(), "Modelo");
            if (cadastroVeiculos.consultarPorPlaca(placaLimpa) != null) throw new IllegalArgumentException("Já existe veículo com essa placa.");
            Veiculo novo;
            switch ((String) tipo.getSelectedItem()) {
                case "Moto": novo = new Moto(placaLimpa, modelo.getText().trim()); break;
                case "Van": novo = new Van(placaLimpa, modelo.getText().trim()); break;
                default: novo = new Carro(placaLimpa, modelo.getText().trim());
            }
            cadastroVeiculos.cadastrar(novo);
            placa.setText(""); modelo.setText("");
            atualizarTabelas();
            mensagem("Veículo cadastrado.");
        }));
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.add(form, BorderLayout.NORTH);
        painel.add(new JScrollPane(new JTable(veiculosModel)), BorderLayout.CENTER);
        painel.add(rodape(salvar), BorderLayout.SOUTH);
        return painel;
    }

    private JPanel formulario(String[] rotulos, java.awt.Component... campos) {
        JPanel form = new JPanel(new GridLayout(rotulos.length, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        for (int i = 0; i < rotulos.length; i++) {
            form.add(new JLabel(rotulos[i]));
            form.add(campos[i]);
        }
        return form;
    }

    private JPanel rodape(JButton botao) {
        JPanel painel = criarRodape();
        painel.add(botao);
        return painel;
    }

    private void atualizarTabelas() {
        passageirosModel.setRowCount(0);
        for (Passageiro p : cadastroPassageiros.listarTodos()) passageirosModel.addRow(new Object[] {p.getNome(), p.getCpf(), p.getTelefone()});
        motoristasModel.setRowCount(0);
        for (Motorista m : cadastroMotoristas.listarTodos()) motoristasModel.addRow(new Object[] {m.getNome(), m.getCpf(), m.getTelefone(), m.getCnh(), m.getVeiculo()});
        veiculosModel.setRowCount(0);
        for (Veiculo v : cadastroVeiculos.listarTodos()) veiculosModel.addRow(new Object[] {v.getTipo(), v.getPlaca(), v.getModelo()});
    }

    private void atualizarComboVeiculos(JComboBox<Veiculo> combo) {
        combo.removeAllItems();
        for (Veiculo veiculo : cadastroVeiculos.listarTodos()) combo.addItem(veiculo);
    }

    private static DefaultTableModel modelo(String... colunas) {
        return new DefaultTableModel(colunas, 0) {
            private static final long serialVersionUID = 1L;
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
    }

    private static String normalizarCpf(String cpf) {
        String limpo = cpf == null ? "" : cpf.replaceAll("[.\\-\\s]", "");
        if (!limpo.matches("\\d{11}")) throw new IllegalArgumentException("CPF deve conter 11 números.");
        return limpo;
    }

    private static void exigirTexto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) throw new IllegalArgumentException(campo + " é obrigatório.");
    }

    private void executar(Runnable acao) {
        try { acao.run(); } catch (RuntimeException exception) { mensagem(exception.getMessage()); }
    }

    private void mensagem(String texto) {
        JOptionPane.showMessageDialog(this, texto, "Cadastros", JOptionPane.INFORMATION_MESSAGE);
    }

    private void limpar(JTextField... campos) { for (JTextField campo : campos) campo.setText(""); }
}
