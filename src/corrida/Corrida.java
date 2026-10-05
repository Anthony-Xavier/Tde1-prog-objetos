package corrida;

import categoria.CategoriaCorrida;
import entidade.Motorista;
import entidade.Passageiro;
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
	private double valorFinal;

	public Corrida(int id, Passageiro passageiro, String origem, String destino,
			double distanciaEstimada, CategoriaCorrida categoria, FormaPagamento formaPagamento) {
		super();
		if (passageiro == null) {
			throw new IllegalArgumentException("Corrida precisa de um passageiro.");
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

	public double getValorFinal() {
		return valorFinal;
	}
	
	public void aceitar(Motorista motorista) {

		if (estado != EstadoCorrida.SOLICITADA) {
			throw new IllegalStateException("Essa corrida não está mais disponível para ser aceita.");
		}

		if (motorista == null) {
			throw new IllegalArgumentException("Nenhum motorista foi informado.");
		}

		if (!motorista.isDisponivel()) {
			throw new IllegalStateException("Esse motorista não está disponível no momento.");
		}

		this.motorista = motorista;
		this.veiculo = motorista.getVeiculo();
		this.estado = EstadoCorrida.ACEITA;

		motorista.ficarIndisponivel();
	}

	public void iniciar() {

		if (estado != EstadoCorrida.ACEITA) {
			throw new IllegalStateException("A corrida precisa ser aceita antes de ser iniciada.");
		}

		this.estado = EstadoCorrida.EM_ANDAMENTO;
	}

	public void finalizar() {

		if (estado != EstadoCorrida.EM_ANDAMENTO) {
			throw new IllegalStateException("A corrida precisa estar em andamento para ser finalizada.");
		}

		this.estado = EstadoCorrida.FINALIZADA;

		if (motorista != null) {
			motorista.ficarDisponivel();
		}
	}

	public void cancelar() {

		if (estado == EstadoCorrida.FINALIZADA) {
			throw new IllegalStateException("Essa corrida já foi finalizada e não pode ser cancelada.");
		}

		if (estado == EstadoCorrida.CANCELADA) {
			throw new IllegalStateException("Essa corrida já foi cancelada.");
		}

		this.estado = EstadoCorrida.CANCELADA;

		if (motorista != null) {
			motorista.ficarDisponivel();
		}
	}

}