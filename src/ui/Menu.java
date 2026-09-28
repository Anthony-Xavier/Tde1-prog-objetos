package ui;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Menu {
	protected static final Scanner teclado = new Scanner(System.in);

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
			
			int resposta;
			try {
				resposta = teclado.nextInt();
			} catch(InputMismatchException e) {
				teclado.nextLine();
				System.out.println("Resposta inválida.");
				continue;
			}
			
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
