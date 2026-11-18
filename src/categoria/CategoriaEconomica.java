package categoria;

import java.io.Serializable;

public class CategoriaEconomica implements CategoriaCorrida, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public String getNome() {
		return "Econômica";
	}

	@Override
	public double getPercentualAcrescimo() {
		return 0.0;
	}

	@Override
	public int getNivelMinimoExigido() {
		return 1;
	}

}