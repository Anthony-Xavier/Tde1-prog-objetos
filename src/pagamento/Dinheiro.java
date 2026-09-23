package pagamento;

public class Dinheiro implements FormaPagamento{

	@Override
	public String getDescricao() {
		return "Dinheiro";
	}

	@Override
	public double aplicarValor(double valor) {
		return valor;
	}

}
