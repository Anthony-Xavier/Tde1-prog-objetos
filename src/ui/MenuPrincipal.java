package ui;

import cadastro.CadastroMotoristas;
import cadastro.CadastroPassageiros;
import cadastro.CadastroVeiculos;
import corrida.GerenciadorCorridas;

public class MenuPrincipal extends Menu {

	private CadastroPassageiros cadastroPassageiros = new CadastroPassageiros();
	private CadastroMotoristas cadastroMotoristas = new CadastroMotoristas();
	private CadastroVeiculos cadastroVeiculos = new CadastroVeiculos();

	private GerenciadorCorridas gerenciadorCorridas = new GerenciadorCorridas();

	@Override
	protected void mostrarOpcoes() {
		System.out.println("1 - CADASTROS");
		System.out.println("2 - CORRIDAS");
		System.out.println("3 - CONSULTAS");
	}

	@Override
	protected void mostrarVoltar() {
		System.out.println("0 - Sair");
	}

	@Override
	protected void receberResposta(int resposta) {

		switch(resposta) {

		case 1:
			MenuCadastros cadastros = new MenuCadastros(
					cadastroPassageiros,
					cadastroMotoristas,
					cadastroVeiculos
			);

			cadastros.mostrar();
			break;

		case 2:
			MenuCorridas corridas = new MenuCorridas(
					gerenciadorCorridas,
					cadastroPassageiros,
					cadastroMotoristas
			);

			corridas.mostrar();
			break;

		case 3:
			MenuConsultas consultas = new MenuConsultas(
					cadastroPassageiros,
					cadastroMotoristas,
					gerenciadorCorridas
			);

			consultas.mostrar();
			break;

		default:
			System.out.println("Resposta inválida.");
		}
	}
}