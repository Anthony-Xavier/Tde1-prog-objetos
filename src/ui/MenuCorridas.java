package ui;

import java.util.ArrayList;

import categoria.CategoriaCorrida;
import domain.Corrida;
import domain.EstadoCorrida;
import domain.Motorista;
import domain.Passageiro;
import pagamento.FormaPagamento;
import sistema.Empresa;

public class MenuCorridas extends Menu {

	public MenuCorridas(Empresa empresa) {
		super(empresa);
	}

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
			solicitarCorrida();
			break;
		case 2:
			aceitarCorrida();
			break;
		case 3:
			iniciarCorrida();
			break;
		case 4:
			finalizarCorrida();
			break;
		case 5:
			cancelarCorrida();
			break;
		case 6:
			avaliarMotorista();
			break;
		default:
			System.out.println("Resposta inválida.");
		}

	}

	private void solicitarCorrida() {
		ArrayList<Passageiro> passageiros = empresa.getPassageiros();
		if (passageiros.isEmpty()) {
			System.out.println("Não há passageiros cadastrados.");
			return;
		}
		System.out.println("Passageiro:");
		for (int i = 0; i < passageiros.size(); i++) {
			System.out.println((i + 1) + " - " + passageiros.get(i).getNome());
		}
		int indicePassageiro = lerOpcao(passageiros.size());
		if (indicePassageiro < 0) {
			return;
		}

		String origem = lerTexto("Endereço de origem");
		String destino = lerTexto("Endereço de destino");
		double distancia = lerDecimal("Distância estimada (km)");

		ArrayList<CategoriaCorrida> categorias = empresa.getCategorias();
		System.out.println("Categoria:");
		for (int i = 0; i < categorias.size(); i++) {
			System.out.println((i + 1) + " - " + categorias.get(i).getNome());
		}
		int indiceCategoria = lerOpcao(categorias.size());
		if (indiceCategoria < 0) {
			return;
		}

		ArrayList<FormaPagamento> formas = empresa.getFormasPagamento();
		System.out.println("Forma de pagamento:");
		for (int i = 0; i < formas.size(); i++) {
			System.out.println((i + 1) + " - " + formas.get(i).getDescricao());
		}
		int indiceForma = lerOpcao(formas.size());
		if (indiceForma < 0) {
			return;
		}

		Corrida corrida = empresa.solicitarCorrida(passageiros.get(indicePassageiro), origem, destino,
				distancia, categorias.get(indiceCategoria), formas.get(indiceForma));
		System.out.println("Corrida nº " + corrida.getId() + " solicitada.");
	}

	private void aceitarCorrida() {
		Corrida corrida = escolherCorrida(empresa.getCorridasPorEstado(EstadoCorrida.SOLICITADA),
				"Não há corridas solicitadas.");
		if (corrida == null) {
			return;
		}
		ArrayList<Motorista> aptos = empresa.getMotoristasAptos(corrida);
		if (aptos.isEmpty()) {
			System.out.println("Não há motoristas disponíveis com veículo compatível com a categoria "
					+ corrida.getCategoria().getNome() + ".");
			return;
		}
		System.out.println("Motoristas disponíveis:");
		for (int i = 0; i < aptos.size(); i++) {
			System.out.println((i + 1) + " - " + aptos.get(i).getNome() + " - " + aptos.get(i).getVeiculo().getDescricao());
		}
		int indice = lerOpcao(aptos.size());
		if (indice < 0) {
			return;
		}
		Motorista motorista = aptos.get(indice);
		corrida.aceitar(motorista);
		System.out.println("Corrida nº " + corrida.getId() + " aceita por " + motorista.getNome() + ".");
	}

	private void iniciarCorrida() {
		Corrida corrida = escolherCorrida(empresa.getCorridasPorEstado(EstadoCorrida.ACEITA),
				"Não há corridas aceitas.");
		if (corrida == null) {
			return;
		}
		corrida.iniciar();
		System.out.println("Corrida nº " + corrida.getId() + " em andamento.");
	}

	private void finalizarCorrida() {
		Corrida corrida = escolherCorrida(empresa.getCorridasPorEstado(EstadoCorrida.EM_ANDAMENTO),
				"Não há corridas em andamento.");
		if (corrida == null) {
			return;
		}
		corrida.finalizar();
		System.out.println("Corrida nº " + corrida.getId() + " finalizada.");
		System.out.println("Valor da corrida: " + formatarValor(corrida.getValorBase()));
		System.out.println("Com categoria " + corrida.getCategoria().getNome() + ": "
				+ formatarValor(corrida.getValorComCategoria()));
		System.out.println("Valor final (" + corrida.getFormaPagamento().getDescricao() + "): "
				+ formatarValor(corrida.getValorFinal()));
	}

	private void cancelarCorrida() {
		Corrida corrida = escolherCorrida(empresa.getCorridasCancelaveis(),
				"Não há corridas que possam ser canceladas.");
		if (corrida == null) {
			return;
		}
		corrida.cancelar();
		System.out.println("Corrida nº " + corrida.getId() + " cancelada.");
	}

	private void avaliarMotorista() {
		Corrida corrida = escolherCorrida(empresa.getCorridasAvaliaveis(),
				"Não há corridas finalizadas aguardando avaliação.");
		if (corrida == null) {
			return;
		}
		Motorista motorista = corrida.getMotorista();
		int nota = lerInteiro("Nota para " + motorista.getNome() + " (1 a 5)");
		while (nota < 1 || nota > 5) {
			System.out.println("A nota deve estar entre 1 e 5.");
			nota = lerInteiro("Nota para " + motorista.getNome() + " (1 a 5)");
		}
		String comentario = lerTexto("Comentário (opcional, ENTER para pular)");
		corrida.avaliarMotorista(nota, comentario);
		System.out.println("Avaliação registrada. Média atual de " + motorista.getNome() + ": "
				+ String.format("%.2f", motorista.getMediaAvaliacoes()));
	}

}
