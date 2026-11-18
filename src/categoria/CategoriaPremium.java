package categoria;

import java.io.Serializable;

public class CategoriaPremium implements CategoriaCorrida, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public String getNome() {
		return "Premium";
	}

	@Override
	public double getPercentualAcrescimo() {
		return 0.50;
	}

	@Override
	public int getNivelMinimoExigido() {
		return 3;
	}

}	