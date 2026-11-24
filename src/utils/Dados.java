package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import cadastro.CadastroMotoristas;
import cadastro.CadastroPassageiros;
import cadastro.CadastroVeiculos;
import categoria.CategoriaEconomica;
import corrida.Corrida;
import corrida.GerenciadorCorridas;
import entidade.Motorista;
import entidade.Passageiro;
import pagamento.Pix;
import veiculo.Carro;
import veiculo.Van;
import veiculo.Veiculo;

public class Dados implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String ARQUIVO = "dados.dat";

	private CadastroPassageiros cadastroPassageiros = new CadastroPassageiros();
	private CadastroMotoristas cadastroMotoristas = new CadastroMotoristas();
	private CadastroVeiculos cadastroVeiculos = new CadastroVeiculos();
	private GerenciadorCorridas gerenciadorCorridas = new GerenciadorCorridas();

	public CadastroPassageiros getCadastroPassageiros() {
		return cadastroPassageiros;
	}

	public CadastroMotoristas getCadastroMotoristas() {
		return cadastroMotoristas;
	}

	public CadastroVeiculos getCadastroVeiculos() {
		return cadastroVeiculos;
	}

	public GerenciadorCorridas getGerenciadorCorridas() {
		return gerenciadorCorridas;
	}

	public static Dados ler() {
		Dados dados = new Dados();
		File f = new File(ARQUIVO);

		if (f.exists()) {
			try {
				FileInputStream fi = new FileInputStream(f);
				ObjectInputStream oi = new ObjectInputStream(fi);
				dados = (Dados) oi.readObject();
				oi.close();
				fi.close();
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("Erro lendo " + ARQUIVO + ": " + e.getMessage());
			}
		} else {
			dados.carregarDadosPadrao();
		}

		return dados;
	}

	private void carregarDadosPadrao() {
		Veiculo carro = new Carro("BBB2222", "Fiat Argo");
		cadastroVeiculos.cadastrar(carro);

		Motorista ana = new Motorista("Ana Souza", "22222222222", "54999990002", "22222", carro);
		cadastroMotoristas.cadastrar(ana);

		Veiculo van = new Van("CCC3333", "Mercedes Sprinter");
		cadastroVeiculos.cadastrar(van);

		Motorista carlos = new Motorista("Carlos Lima", "33333333333", "54999990003", "33333", van);
		cadastroMotoristas.cadastrar(carlos);

		Passageiro pedro = new Passageiro("Pedro Santos", "55555555555", "54999990005");
		cadastroPassageiros.cadastrar(pedro);

		Corrida corrida = gerenciadorCorridas.solicitarCorrida(pedro, "Rodoviária", "Aeroporto", 15,
				new CategoriaEconomica(), new Pix());
		corrida.aceitar(ana);
	}

	public void gravar() {
		try {
			FileOutputStream fo = new FileOutputStream(ARQUIVO);
			ObjectOutputStream ou = new ObjectOutputStream(fo);
			ou.writeObject(this);
			ou.close();
			fo.close();
		} catch (IOException e) {
			System.out.println("Erro gravando " + ARQUIVO + ": " + e.getMessage());
		}
	}
}
