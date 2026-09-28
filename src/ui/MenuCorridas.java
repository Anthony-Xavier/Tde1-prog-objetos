package ui;

public class MenuCorridas extends Menu {

	@Override
	protected void mostrarTitulo() {
		System.out.println(" - Corridas");
	}

	@Override
	protected void mostrarOpcoes() {
		System.out.println("1 - Solicitar corrida");
		System.out.println("2 - Aceitar corrida");
		System.out.println("3 - Iniciar corrida");
		System.out.println("4 - Finalizar corrida");
		System.out.println("5 - Cancelar corrida");
		System.out.println("6 – Avaliar motorista");
	}

	protected void receberResposta(int resposta) {
		switch (resposta) {
		case 1:

		default:
			System.out.println("Resposta inválida.");
		}

	}

}
