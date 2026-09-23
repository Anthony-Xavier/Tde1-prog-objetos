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

}