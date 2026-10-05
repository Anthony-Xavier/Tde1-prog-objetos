package ui;

import java.util.ArrayList;
import java.util.Scanner;

import domain.Corrida;
import sistema.Empresa;

public abstract class Menu {
	protected static final Scanner teclado = new Scanner(System.in);

	protected Empresa empresa;

	protected Menu(Empresa empresa) {
		this.empresa = empresa;
	}

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
				resposta = Integer.parseInt(teclado.nextLine().trim());
			} catch(NumberFormatException e) {
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
				try {
					this.receberResposta(resposta);
				} catch (IllegalArgumentException | IllegalStateException e) {
					System.out.println("Erro: " + e.getMessage());
				}
			}
		}
	}

	protected String lerTexto(String rotulo) {
		System.out.print(rotulo + ": ");
		return teclado.nextLine().trim();
	}

	protected int lerInteiro(String rotulo) {
		while (true) {
			try {
				return Integer.parseInt(lerTexto(rotulo));
			} catch (NumberFormatException e) {
				System.out.println("Digite um número inteiro.");
			}
		}
	}

	protected double lerDecimal(String rotulo) {
		while (true) {
			try {
				return Double.parseDouble(lerTexto(rotulo).replace(',', '.'));
			} catch (NumberFormatException e) {
				System.out.println("Digite um número válido.");
			}
		}
	}

	protected int lerOpcao(int quantidade) {
		while (true) {
			int escolha = lerInteiro("Escolha (0 para cancelar)");
			if (escolha >= 0 && escolha <= quantidade) {
				return escolha - 1;
			}
			System.out.println("Opção inválida.");
		}
	}

	protected String formatarValor(double valor) {
		return String.format("R$ %.2f", valor);
	}

	protected String resumoCorrida(Corrida corrida) {
		return "#" + corrida.getId() + " - " + corrida.getPassageiro().getNome() + " | "
				+ corrida.getOrigem() + " -> " + corrida.getDestino() + " | "
				+ corrida.getDistanciaEstimada() + " km | " + corrida.getCategoria().getNome()
				+ " | " + corrida.getEstado();
	}

	protected Corrida escolherCorrida(ArrayList<Corrida> lista, String mensagemVazia) {
		if (lista.isEmpty()) {
			System.out.println(mensagemVazia);
			return null;
		}
		for (int i = 0; i < lista.size(); i++) {
			System.out.println((i + 1) + " - " + resumoCorrida(lista.get(i)));
		}
		int indice = lerOpcao(lista.size());
		if (indice < 0) {
			return null;
		}
		return lista.get(indice);
	}
}
