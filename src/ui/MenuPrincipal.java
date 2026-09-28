package ui;

public class MenuPrincipal extends Menu {

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

	protected void receberResposta(int resposta) {
		switch(resposta) {
		case 1:
			MenuCadastros cadastros = new MenuCadastros();
			cadastros.mostrar();
			break;
		case 2:
			MenuCorridas corridas = new MenuCorridas();
			corridas.mostrar();
			break;
		case 3:
			MenuConsultas consultas = new MenuConsultas();
			consultas.mostrar();
			break;
		default:
			System.out.println("Resposta inválida.");
		}

	}

}
