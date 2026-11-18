package pagamento;

import java.io.Serializable;

public class Cartao implements FormaPagamento, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDescricao() {
		return "Cartão";
	}

	@Override
	public double aplicarValor(double valor) {
		return valor + valor * 0.03;
	}

}
