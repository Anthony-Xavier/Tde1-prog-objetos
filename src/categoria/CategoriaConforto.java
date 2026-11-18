package categoria;

import java.io.Serializable;

public class CategoriaConforto implements CategoriaCorrida, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public String getNome() {
		return "Conforto";
	}

	@Override
	public double getPercentualAcrescimo() {
		return 0.20;
	}

	@Override
	public int getNivelMinimoExigido() {
		return 2;
	}

}