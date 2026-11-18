package veiculo;

public class Carro extends Veiculo {

	private static final long serialVersionUID = 1L;

	public Carro(String placa, String modelo) {
		super(placa, modelo);
	}

	@Override
	protected double getTarifaFixa() {
		return 5.0;
	}

	@Override
	protected double getTarifaPorKm() {
		return 2.0;
	}

	@Override
	public int getNivelConforto() {
		return 2;
	}

	@Override
	public String getTipo() {
		return "Carro";
	}

}
