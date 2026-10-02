package ui;

import cadastro.CadastroMotoristas;
import cadastro.CadastroPassageiros;
import entidade.Motorista;
import entidade.Passageiro;
import utils.leitorDados;

public class MenuConsultas extends Menu {

	private CadastroPassageiros cadastroPassageiros;
	private CadastroMotoristas cadastroMotoristas;

	public MenuConsultas(
			CadastroPassageiros cadastroPassageiros,
			CadastroMotoristas cadastroMotoristas) {

		this.cadastroPassageiros = cadastroPassageiros;
		this.cadastroMotoristas = cadastroMotoristas;
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
		System.out.println("Listagem de corridas ainda não implementada.");
	}

	private void consultarPassageiro() {

		String cpf = leitorDados.lerCpf("CPF do passageiro: ");

		Passageiro passageiro =
				cadastroPassageiros.consultarPorCpf(cpf);

		if (passageiro != null) {
			System.out.println();
			System.out.println(passageiro);
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