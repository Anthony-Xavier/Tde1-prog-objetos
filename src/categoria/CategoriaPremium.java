package categoria;

public class CategoriaPremium implements CategoriaCorrida {

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