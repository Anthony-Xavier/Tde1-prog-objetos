package categoria;

public class CategoriaConforto implements CategoriaCorrida {

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