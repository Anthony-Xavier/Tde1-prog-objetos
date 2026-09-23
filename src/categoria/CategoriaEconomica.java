package categoria;

public class CategoriaEconomica implements CategoriaCorrida {

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