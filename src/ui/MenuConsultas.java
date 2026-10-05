package ui;

import java.util.ArrayList;

import domain.Avaliacao;
import domain.Corrida;
import domain.Motorista;
import domain.Passageiro;
import sistema.Empresa;

public class MenuConsultas extends Menu {

	public MenuConsultas(Empresa empresa) {
		super(empresa);
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

	protected void receberResposta(int resposta) {
		switch(resposta) {
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
		ArrayList<Corrida> corridas = empresa.getCorridas();
		if (corridas.isEmpty()) {
			System.out.println("Nenhuma corrida registrada.");
			return;
		}
		for (Corrida corrida : corridas) {
			System.out.println("Corrida nº " + corrida.getId());
			System.out.println("  Passageiro: " + corrida.getPassageiro().getNome());
			if (corrida.getMotorista() == null) {
				System.out.println("  Motorista: -");
				System.out.println("  Veículo: -");
			} else {
				System.out.println("  Motorista: " + corrida.getMotorista().getNome());
				System.out.println("  Veículo: " + corrida.getVeiculo().getDescricao());
			}
			System.out.println("  Origem: " + corrida.getOrigem());
			System.out.println("  Destino: " + corrida.getDestino());
			System.out.println("  Distância: " + corrida.getDistanciaEstimada() + " km");
			System.out.println("  Categoria: " + corrida.getCategoria().getNome());
			if (corrida.getMotorista() == null) {
				System.out.println("  Valor: a definir (sem motorista)");
			} else {
				System.out.println("  Valor: " + formatarValor(corrida.getValorBase())
						+ " (final: " + formatarValor(corrida.getValorFinal()) + ")");
			}
			System.out.println("  Forma de pagamento: " + corrida.getFormaPagamento().getDescricao());
			System.out.println("  Situação: " + corrida.getEstado());
			System.out.println();
		}
	}

	private void consultarPassageiro() {
		Passageiro passageiro = empresa.buscarPassageiro(lerTexto("CPF do passageiro"));
		if (passageiro == null) {
			System.out.println("Passageiro não encontrado.");
			return;
		}
		System.out.println("Nome: " + passageiro.getNome());
		System.out.println("CPF: " + passageiro.getCpf());
		System.out.println("Telefone: " + passageiro.getTelefone());
		ArrayList<Corrida> historico = passageiro.getHistorico();
		if (historico.isEmpty()) {
			System.out.println("Nenhuma corrida solicitada.");
			return;
		}
		System.out.println("Corridas:");
		for (Corrida corrida : historico) {
			System.out.println("  " + resumoCorrida(corrida));
		}
	}

	private void consultarMotorista() {
		Motorista motorista = empresa.buscarMotorista(lerTexto("CPF do motorista"));
		if (motorista == null) {
			System.out.println("Motorista não encontrado.");
			return;
		}
		System.out.println("Nome: " + motorista.getNome());
		System.out.println("CPF: " + motorista.getCpf());
		System.out.println("Telefone: " + motorista.getTelefone());
		System.out.println("CNH: " + motorista.getCnh());
		System.out.println("Veículo: " + motorista.getVeiculo().getDescricao());
		if (motorista.isDisponivel()) {
			System.out.println("Situação: Disponível");
		} else {
			System.out.println("Situação: Indisponível");
		}

		ArrayList<Avaliacao> avaliacoes = motorista.getAvaliacoes();
		if (avaliacoes.isEmpty()) {
			System.out.println("Média de avaliações: sem avaliações");
			return;
		}
		System.out.println("Média de avaliações: " + String.format("%.2f", motorista.getMediaAvaliacoes())
				+ " (total: " + avaliacoes.size() + ")");
		for (Avaliacao avaliacao : avaliacoes) {
			if (avaliacao.getComentario() == null) {
				System.out.println("  Nota " + avaliacao.getNota());
			} else {
				System.out.println("  Nota " + avaliacao.getNota() + " - " + avaliacao.getComentario());
			}
		}
	}

}
