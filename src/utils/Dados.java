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
import corrida.GerenciadorCorridas;

// Guarda todos os dados do sistema em um único objeto,
// para que tudo seja gravado e lido de um único arquivo.
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

	// Lê os dados do arquivo. Se o arquivo não existir, devolve dados vazios.
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
		}

		return dados;
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
