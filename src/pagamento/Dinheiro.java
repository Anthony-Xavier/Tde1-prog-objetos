package pagamento;

import java.io.Serializable;

public class Dinheiro implements FormaPagamento, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDescricao() {
		return "Dinheiro";
	}

	@Override
	public double aplicarValor(double valor) {
		return valor;
	}

}
