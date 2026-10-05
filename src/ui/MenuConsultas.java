package ui;

import java.util.List;

import cadastro.CadastroMotoristas;
import cadastro.CadastroPassageiros;
import corrida.Corrida;
import corrida.GerenciadorCorridas;
import entidade.Motorista;
import entidade.Passageiro;
import utils.leitorDados;

public class MenuConsultas extends Menu {

	private CadastroPassageiros cadastroPassageiros;
	private CadastroMotoristas cadastroMotoristas;
	private GerenciadorCorridas gerenciadorCorridas;

	public MenuConsultas(
			CadastroPassageiros cadastroPassageiros,
			CadastroMotoristas cadastroMotoristas,
			GerenciadorCorridas gerenciadorCorridas) {

		this.cadastroPassageiros = cadastroPassageiros;
		this.cadastroMotoristas = cadastroMotoristas;
		this.gerenciadorCorridas = gerenciadorCorridas;
	}

	@Override
	protected void mostrarTitulo() {
		System.out.println(" - Consultas");
	}

	@Override
	protected void mostrarOpcoes() {
		System.out.println("1 - Listar corridas");
		System.out.println("2 - Consultar passageiro");
		System.out.println("3 - Consultar motorista");
	}

	@Override
	protected void receberResposta(int resposta) {

		switch (resposta) {

		case 1:
			listarCorridas();
			break;

		case 2:
			consultarPassageiro();
			break;

		case 3:
			consultarMotorista();
			break;

		default:
			System.out.println("Resposta inválida.");
		}
	}

	private void listarCorridas() {

		List<Corrida> corridas = gerenciadorCorridas.listarTodas();

		if (corridas.isEmpty()) {
			System.out.println("Nenhuma corrida cadastrada.");
			return;
		}

		System.out.println();
		System.out.println("Corridas cadastradas:");

		for (Corrida corrida : corridas) {

			System.out.println("-----------------------------------------");
			System.out.println("ID: " + corrida.getId());
			System.out.println("Passageiro: " + corrida.getPassageiro().getNome());
			System.out.println("Origem: " + corrida.getOrigem());
			System.out.println("Destino: " + corrida.getDestino());
			System.out.println("Distância: " + corrida.getDistanciaEstimada() + " km");
			System.out.println("Categoria: " + corrida.getCategoria().getNome());
			System.out.println("Estado: " + corrida.getEstado());

			if (corrida.getMotorista() != null) {
				System.out.println("Motorista: " + corrida.getMotorista().getNome());
			}
			else {
				System.out.println("Motorista: Não definido");
			}
		}

		System.out.println("-----------------------------------------");
	}

	private void consultarPassageiro() {

		String cpf = leitorDados.lerCpf("CPF do passageiro: ");

		Passageiro passageiro =
				cadastroPassageiros.consultarPorCpf(cpf);

		if (passageiro != null) {
			System.out.println();
			System.out.println(passageiro);

			for (Corrida corrida : passageiro.getHistorico()) {
				System.out.println(
						"  Corrida " + corrida.getId()
						+ " | " + corrida.getOrigem() + " -> " + corrida.getDestino()
						+ " | " + corrida.getEstado()
				);
			}
		}
		else {
			System.out.println("Passageiro não encontrado.");
		}
	}

	private void consultarMotorista() {

		String cpf = leitorDados.lerCpf("CPF do motorista: ");

		Motorista motorista =
				cadastroMotoristas.consultarPorCpf(cpf);

		if (motorista != null) {
			System.out.println();
			System.out.println(motorista);
		}
		else {
			System.out.println("Motorista não encontrado.");
		}
	}
}