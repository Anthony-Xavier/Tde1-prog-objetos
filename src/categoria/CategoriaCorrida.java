package categoria;

import veiculo.Veiculo;

public interface CategoriaCorrida {

	String getNome();

	double getPercentualAcrescimo();

	int getNivelMinimoExigido();

	default double aplicarAcrescimo(double valor) {
		return valor + valor * getPercentualAcrescimo();
	}

	default boolean isVeiculoCompativel(Veiculo veiculo) {
		return veiculo.getNivelConforto() >= getNivelMinimoExigido();
	}

}