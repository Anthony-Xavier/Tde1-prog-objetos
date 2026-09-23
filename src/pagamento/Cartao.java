package pagamento;

public class Cartao implements FormaPagamento{

	@Override
	public String getDescricao() {
		// TODO Auto-generated method stub
		return "Cartão";
	}

	@Override
	public double aplicarValor(double valor) {
		// TODO Auto-generated method stub
		return valor + valor * 0.03;
	}

}
