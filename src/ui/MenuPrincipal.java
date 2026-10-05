package ui;

import sistema.Empresa;

public class MenuPrincipal extends Menu {

	private final MenuCadastros cadastros;
	private final MenuCorridas corridas;
	private final MenuConsultas consultas;

	public MenuPrincipal(Empresa empresa) {
		super(empresa);
		this.cadastros = new MenuCadastros(empresa);
		this.corridas = new MenuCorridas(empresa);
		this.consultas = new MenuConsultas(empresa);
	}

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
			cadastros.mostrar();
			break;
		case 2:
			corridas.mostrar();
			break;
		case 3:
			consultas.mostrar();
			break;
		default:
			System.out.println("Resposta inválida.");
		}

	}

}
