package pagamento;

public class Cartao implements FormaPagamento{

	@Override
	public String getDescricao() {
		return "Cartão";
	}

	@Override
	public double aplicarValor(double valor) {
		return valor + valor * 0.03;
	}

}
