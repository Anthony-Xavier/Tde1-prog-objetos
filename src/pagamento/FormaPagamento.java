package pagamento;

public interface FormaPagamento {

	String getDescricao();

	double aplicarValor(double valor);

}