package ui;

public class MenuConsultas extends Menu {

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

	protected void receberResposta(int resposta) {
		switch(resposta) {
		case 1:
			
		default:
			System.out.println("Resposta inválida.");
		}

	}

}
