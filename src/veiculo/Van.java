package veiculo;

public class Van extends Veiculo {

	public Van(String placa, String modelo) {
		super(placa, modelo);
	}

	@Override
	public String getTipo() {
		return "Van";
	}

	@Override
	protected double getTarifaFixa() {
		return 8;
	}

	@Override
	protected double getTarifaPorKm() {
		return 3;
	}

	@Override
	public int getNivelConforto() {

		return 3;
	}

}
