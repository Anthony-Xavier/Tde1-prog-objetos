package veiculo;

public abstract class Veiculo {

	private String placa;
	private String modelo;

	protected Veiculo(String placa, String modelo) {
		super();
		this.placa = placa;
		this.modelo = modelo;
	}

	public String getPlaca() {
		return placa;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	protected abstract double getTarifaFixa();

	protected abstract double getTarifaPorKm();

	public abstract int getNivelConforto();

	public final double calcularTarifaBase(double distanciaKm) {
		return getTarifaFixa() + getTarifaPorKm() * distanciaKm;
	}
	
	@Override
	public String toString() {
		return "Placa: " + placa
		  + " | Modelo: " + modelo;
	}

}