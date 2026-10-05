package veiculo;

public class Moto extends Veiculo {

	public Moto(String placa, String modelo) {
		super(placa, modelo);
	}

	@Override
	public String getTipo() {
		return "Moto";
	}

	@Override
	protected double getTarifaFixa() {
		return 3.0;
	}

	@Override
	protected double getTarifaPorKm() {
		return 1.50;
	}

	@Override
	public int getNivelConforto() {
		return 1;
	}

}