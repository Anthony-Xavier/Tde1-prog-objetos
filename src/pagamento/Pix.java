package pagamento;

public class Pix implements FormaPagamento {

	@Override
	public String getDescricao() {
		return "Pix";
	}

	@Override
	public double aplicarValor(double valor) {
	
		return valor - valor * 0.05;
	}

}
