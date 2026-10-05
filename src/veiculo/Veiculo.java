package veiculo;

public abstract class Veiculo {

	private String placa;
	private String modelo;

	protected Veiculo(String placa, String modelo) {
		super();
		this.setPlaca(placa);
		this.setModelo(modelo);
	}

	public String getPlaca() {
		return placa;
	}

	private void setPlaca(String placa) {
		if (placa == null || placa.isBlank()) {
			throw new IllegalArgumentException("Placa não pode ser vazia.");
		}
		String placaLimpa = placa.replace("-", "").replace(" ", "").toUpperCase();
		if (placaLimpa.length() != 7) {
			throw new IllegalArgumentException("Placa deve ter 7 caracteres (ex: ABC1234).");
		}
		this.placa = placaLimpa;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		if (modelo == null || modelo.isBlank()) {
			throw new IllegalArgumentException("Modelo não pode ser vazio.");
		}
		this.modelo = modelo;
	}

	public abstract String getTipo();

	protected abstract double getTarifaFixa();

	protected abstract double getTarifaPorKm();

	public abstract int getNivelConforto();

	public final double calcularTarifaBase(double distanciaKm) {
		return getTarifaFixa() + getTarifaPorKm() * distanciaKm;
	}

	public String getDescricao() {
		return getTipo() + " " + modelo + " (" + placa + ")";
	}

}
