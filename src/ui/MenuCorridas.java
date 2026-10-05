package ui;

import java.util.List;

import cadastro.CadastroMotoristas;
import cadastro.CadastroPassageiros;
import categoria.CategoriaConforto;
import categoria.CategoriaCorrida;
import categoria.CategoriaEconomica;
import categoria.CategoriaPremium;
import corrida.Corrida;
import corrida.EstadoCorrida;
import corrida.GerenciadorCorridas;
import entidade.Motorista;
import entidade.Passageiro;
import pagamento.Cartao;
import pagamento.Dinheiro;
import pagamento.FormaPagamento;
import pagamento.Pix;
import utils.leitorDados;

public class MenuCorridas extends Menu {

	private GerenciadorCorridas gerenciadorCorridas;
	private CadastroPassageiros cadastroPassageiros;
	private CadastroMotoristas cadastroMotoristas;

	public MenuCorridas(
			GerenciadorCorridas gerenciadorCorridas,
			CadastroPassageiros cadastroPassageiros,
			CadastroMotoristas cadastroMotoristas) {

		this.gerenciadorCorridas = gerenciadorCorridas;
		this.cadastroPassageiros = cadastroPassageiros;
		this.cadastroMotoristas = cadastroMotoristas;
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
		System.out.println("6 - Avaliar motorista");
	}

	@Override
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

		boolean podeSolicitar = true;

		if (cadastroPassageiros.listarTodos().isEmpty()) {
			System.out.println(
					"Cadastre pelo menos um passageiro antes de solicitar uma corrida.");
			podeSolicitar = false;
		}

		if (cadastroMotoristas.listarDisponiveis().isEmpty()) {
			System.out.println(
					"Cadastre pelo menos um motorista disponível antes de solicitar uma corrida.");
			podeSolicitar = false;
		}

		if (!podeSolicitar) {
			return;
		}

		String cpf = leitorDados.lerCpf("CPF do passageiro: ");

		Passageiro passageiro =
				cadastroPassageiros.consultarPorCpf(cpf);

		if (passageiro == null) {
			System.out.println("Passageiro não encontrado.");
			return;
		}

		String origem =
				leitorDados.lerTexto("Endereço de origem: ");

		String destino =
				leitorDados.lerTexto("Endereço de destino: ");

		double distancia =
				leitorDados.lerDouble("Distância estimada: ");

		CategoriaCorrida categoria =
				selecionarCategoria();

		FormaPagamento formaPagamento =
				selecionarFormaPagamento();

		try {

			Corrida corrida =
					gerenciadorCorridas.solicitarCorrida(
							passageiro,
							origem,
							destino,
							distancia,
							categoria,
							formaPagamento
					);

			System.out.println("Corrida solicitada com sucesso.");
			System.out.println("ID da corrida: " + corrida.getId());

		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void aceitarCorrida() {

		List<Corrida> solicitadas =
				gerenciadorCorridas.listarPorEstado(
						EstadoCorrida.SOLICITADA);

		if (solicitadas.isEmpty()) {
			System.out.println("Não existem corridas solicitadas.");
			return;
		}

		System.out.println();
		System.out.println("Corridas solicitadas:");

		for (Corrida corrida : solicitadas) {

			System.out.println(
					"ID: " + corrida.getId()
					+ " | Origem: " + corrida.getOrigem()
					+ " | Destino: " + corrida.getDestino()
			);
		}

		int id =
				leitorDados.lerInt("ID da corrida: ");

		Corrida corrida =
				gerenciadorCorridas.buscarPorId(id);

		if (corrida == null) {
			System.out.println("Corrida não encontrada.");
			return;
		}

		if (corrida.getEstado() != EstadoCorrida.SOLICITADA) {
			System.out.println(
					"Essa corrida não está disponível para ser aceita.");
			return;
		}

		List<Motorista> disponiveis =
				cadastroMotoristas.listarDisponiveis();

		if (disponiveis.isEmpty()) {
			System.out.println("Não existem motoristas disponíveis.");
			return;
		}

		System.out.println();
		System.out.println("Motoristas disponíveis:");

		for (Motorista motorista : disponiveis) {
			System.out.println(motorista);
			System.out.println("-----------------------------");
		}

		String cpf =
				leitorDados.lerCpf("CPF do motorista: ");

		Motorista motorista =
				cadastroMotoristas.consultarPorCpf(cpf);

		if (motorista == null) {
			System.out.println("Motorista não encontrado.");
			return;
		}

		if (!motorista.isDisponivel()) {
			System.out.println(
					"Esse motorista não está disponível no momento.");
			return;
		}

		try {

			gerenciadorCorridas.aceitarCorrida(
					id,
					motorista
			);

			System.out.println("Corrida aceita com sucesso.");

		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void iniciarCorrida() {

		List<Corrida> aceitas =
				gerenciadorCorridas.listarPorEstado(
						EstadoCorrida.ACEITA);

		if (aceitas.isEmpty()) {
			System.out.println(
					"Não existem corridas aceitas para iniciar.");
			return;
		}

		System.out.println();
		System.out.println("Corridas aceitas:");

		for (Corrida corrida : aceitas) {

			System.out.println(
					"ID: " + corrida.getId()
					+ " | Origem: " + corrida.getOrigem()
					+ " | Destino: " + corrida.getDestino()
					+ " | Motorista: "
					+ corrida.getMotorista().getNome()
			);
		}

		int id =
				leitorDados.lerInt("ID da corrida: ");

		try {

			gerenciadorCorridas.iniciarCorrida(id);

			System.out.println("Corrida iniciada com sucesso.");

		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void finalizarCorrida() {

		List<Corrida> emAndamento =
				gerenciadorCorridas.listarPorEstado(
						EstadoCorrida.EM_ANDAMENTO);

		if (emAndamento.isEmpty()) {
			System.out.println(
					"Não existem corridas em andamento.");
			return;
		}

		System.out.println();
		System.out.println("Corridas em andamento:");

		for (Corrida corrida : emAndamento) {

			System.out.println(
					"ID: " + corrida.getId()
					+ " | Origem: " + corrida.getOrigem()
					+ " | Destino: " + corrida.getDestino()
					+ " | Motorista: "
					+ corrida.getMotorista().getNome()
			);
		}

		int id =
				leitorDados.lerInt("ID da corrida: ");

		try {

			gerenciadorCorridas.finalizarCorrida(id);

			System.out.println("Corrida finalizada com sucesso.");

		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void cancelarCorrida() {

		List<Corrida> solicitadas =
				gerenciadorCorridas.listarPorEstado(
						EstadoCorrida.SOLICITADA);

		List<Corrida> aceitas =
				gerenciadorCorridas.listarPorEstado(
						EstadoCorrida.ACEITA);

		List<Corrida> emAndamento =
				gerenciadorCorridas.listarPorEstado(
						EstadoCorrida.EM_ANDAMENTO);

		if (solicitadas.isEmpty()
				&& aceitas.isEmpty()
				&& emAndamento.isEmpty()) {

			System.out.println(
					"Não existem corridas disponíveis para cancelar.");
			return;
		}

		System.out.println();
		System.out.println("Corridas disponíveis para cancelamento:");

		for (Corrida corrida : solicitadas) {

			System.out.println(
					"ID: " + corrida.getId()
					+ " | Estado: " + corrida.getEstado()
					+ " | Origem: " + corrida.getOrigem()
					+ " | Destino: " + corrida.getDestino()
			);
		}

		for (Corrida corrida : aceitas) {

			System.out.println(
					"ID: " + corrida.getId()
					+ " | Estado: " + corrida.getEstado()
					+ " | Origem: " + corrida.getOrigem()
					+ " | Destino: " + corrida.getDestino()
			);
		}

		for (Corrida corrida : emAndamento) {

			System.out.println(
					"ID: " + corrida.getId()
					+ " | Estado: " + corrida.getEstado()
					+ " | Origem: " + corrida.getOrigem()
					+ " | Destino: " + corrida.getDestino()
			);
		}

		int id =
				leitorDados.lerInt("ID da corrida: ");

		try {

			gerenciadorCorridas.cancelarCorrida(id);

			System.out.println("Corrida cancelada com sucesso.");

		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private CategoriaCorrida selecionarCategoria() {

		while (true) {

			System.out.println();
			System.out.println("1 - Econômica");
			System.out.println("2 - Conforto");
			System.out.println("3 - Premium");

			int opcao =
					leitorDados.lerInt("Categoria: ");

			switch (opcao) {

			case 1:
				return new CategoriaEconomica();

			case 2:
				return new CategoriaConforto();

			case 3:
				return new CategoriaPremium();

			default:
				System.out.println("Categoria inválida.");
			}
		}
	}

	private FormaPagamento selecionarFormaPagamento() {

		while (true) {

			System.out.println();
			System.out.println("1 - Pix");
			System.out.println("2 - Cartão");
			System.out.println("3 - Dinheiro");

			int opcao =
					leitorDados.lerInt("Forma de pagamento: ");

			switch (opcao) {

			case 1:
				return new Pix();

			case 2:
				return new Cartao();

			case 3:
				return new Dinheiro();

			default:
				System.out.println("Forma de pagamento inválida.");
			}
		}
	}

	private void avaliarMotorista() {

		System.out.println(
				"Avaliação de motorista ainda não implementada.");
	}
}