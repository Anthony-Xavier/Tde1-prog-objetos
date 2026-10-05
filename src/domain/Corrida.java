package domain;

import categoria.CategoriaCorrida;
import pagamento.FormaPagamento;
import veiculo.Veiculo;

public class Corrida {

	private int id;
	private Passageiro passageiro;
	private Motorista motorista;
	private Veiculo veiculo;
	private String origem;
	private String destino;
	private double distanciaEstimada;
	private CategoriaCorrida categoria;
	private FormaPagamento formaPagamento;
	private EstadoCorrida estado;
	private double valorBase;
	private double valorComCategoria;
	private double valorFinal;
	private Avaliacao avaliacao;

	Corrida(int id, Passageiro passageiro, String origem, String destino,
			double distanciaEstimada, CategoriaCorrida categoria, FormaPagamento formaPagamento) {
		super();
		if (passageiro == null) {
			throw new IllegalArgumentException("Corrida precisa de um passageiro.");
		}
		if (origem == null || origem.isBlank()) {
			throw new IllegalArgumentException("Endereço de origem não pode ser vazio.");
		}
		if (destino == null || destino.isBlank()) {
			throw new IllegalArgumentException("Endereço de destino não pode ser vazio.");
		}
		if (distanciaEstimada <= 0) {
			throw new IllegalArgumentException("Distância estimada deve ser maior que zero.");
		}
		if (categoria == null) {
			throw new IllegalArgumentException("Corrida precisa de uma categoria.");
		}
		if (formaPagamento == null) {
			throw new IllegalArgumentException("Corrida precisa de uma forma de pagamento.");
		}
		this.id = id;
		this.passageiro = passageiro;
		this.origem = origem;
		this.destino = destino;
		this.distanciaEstimada = distanciaEstimada;
		this.categoria = categoria;
		this.formaPagamento = formaPagamento;
		this.estado = EstadoCorrida.SOLICITADA;
	}

	public void aceitar(Motorista motorista) {
		verificarEstado(EstadoCorrida.SOLICITADA, "aceita");
		if (!podeSerRealizadaPor(motorista)) {
			throw new IllegalStateException("Motorista indisponível ou com veículo incompatível com a categoria "
					+ categoria.getNome() + ".");
		}
		this.motorista = motorista;
		this.veiculo = motorista.getVeiculo();
		motorista.ficarIndisponivel();
		calcularValores();
		this.estado = EstadoCorrida.ACEITA;
	}

	public void iniciar() {
		verificarEstado(EstadoCorrida.ACEITA, "iniciada");
		this.estado = EstadoCorrida.EM_ANDAMENTO;
	}

	public void finalizar() {
		verificarEstado(EstadoCorrida.EM_ANDAMENTO, "finalizada");
		motorista.ficarDisponivel();
		this.estado = EstadoCorrida.FINALIZADA;
	}

	public void cancelar() {
		if (!podeSerCancelada()) {
			throw new IllegalStateException("Corrida " + estado + " não pode ser cancelada.");
		}
		if (motorista != null) {
			motorista.ficarDisponivel();
		}
		this.estado = EstadoCorrida.CANCELADA;
	}

	public void avaliarMotorista(int nota, String comentario) {
		if (!podeSerAvaliada()) {
			throw new IllegalStateException("Somente corridas finalizadas e ainda não avaliadas podem ser avaliadas.");
		}
		Avaliacao novaAvaliacao = new Avaliacao(nota, comentario);
		this.avaliacao = novaAvaliacao;
		motorista.adicionarAvaliacao(novaAvaliacao);
	}

	public boolean podeSerRealizadaPor(Motorista motorista) {
		return motorista != null && motorista.isDisponivel()
				&& categoria.isVeiculoCompativel(motorista.getVeiculo());
	}

	public boolean podeSerCancelada() {
		return estado == EstadoCorrida.SOLICITADA || estado == EstadoCorrida.ACEITA;
	}

	public boolean podeSerAvaliada() {
		return estado == EstadoCorrida.FINALIZADA && avaliacao == null;
	}

	private void calcularValores() {
		this.valorBase = veiculo.calcularTarifaBase(distanciaEstimada);
		this.valorComCategoria = categoria.aplicarAcrescimo(valorBase);
		this.valorFinal = formaPagamento.aplicarValor(valorComCategoria);
	}

	private void verificarEstado(EstadoCorrida esperado, String acao) {
		if (estado != esperado) {
			throw new IllegalStateException("Corrida " + estado + " não pode ser " + acao
					+ ". É necessário estar " + esperado + ".");
		}
	}

	public int getId() {
		return id;
	}

	public Passageiro getPassageiro() {
		return passageiro;
	}

	public Motorista getMotorista() {
		return motorista;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public String getOrigem() {
		return origem;
	}

	public String getDestino() {
		return destino;
	}

	public double getDistanciaEstimada() {
		return distanciaEstimada;
	}

	public CategoriaCorrida getCategoria() {
		return categoria;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public EstadoCorrida getEstado() {
		return estado;
	}

	public double getValorBase() {
		return valorBase;
	}

	public double getValorComCategoria() {
		return valorComCategoria;
	}

	public double getValorFinal() {
		return valorFinal;
	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

}
