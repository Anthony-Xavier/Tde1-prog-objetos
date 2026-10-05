package ui;

import utils.leitorDados;

public abstract class Menu {

	protected abstract void mostrarOpcoes();
	protected abstract void receberResposta(int resposta);
	
	private void mostrarDivisor() {
		System.out.println("=========================================");
	}
	
	protected void mostrarTitulo() {
		System.out.println();
	};
	
	protected void mostrarVoltar() {
		System.out.println("0 - Voltar");
	}
	
	public void mostrar() {
		
		while (true) {
			this.mostrarDivisor();
			System.out.print("SISTEMA DE TRANSPORTE");
			this.mostrarTitulo();
			this.mostrarDivisor();
			this.mostrarOpcoes();
			this.mostrarVoltar();
			this.mostrarDivisor();
			
			int resposta = leitorDados.lerInt("Opção: ");
			
			if (resposta == 0) {
				break;
			}
			else if (resposta < 0) {
				System.out.println("Resposta inválida.");
			}
			else {
				this.receberResposta(resposta);
			}
		}
	}
}
