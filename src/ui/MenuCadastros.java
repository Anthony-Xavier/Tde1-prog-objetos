package ui;

public class MenuCadastros extends Menu {

	@Override
	protected void mostrarTitulo() {
		System.out.println(" - Cadastros");
	}
	
	@Override
	protected void mostrarOpcoes() {
		System.out.println("1 - Cadastrar passageiro");
		System.out.println("2 - Cadastrar motorista");
		System.out.println("3 - Cadastrar veículo");
	}

	protected void receberResposta(int resposta) {
		switch(resposta) {
		case 1:
			
		default:
			System.out.println("Resposta inválida.");
		}

	}

}
