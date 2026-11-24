
package exceptions;

public class VeiculoIndisponivelException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public VeiculoIndisponivelException() {
        super("O veículo não está disponível.");
    }

    public VeiculoIndisponivelException(String mensagem) {
        super(mensagem);
    }
}
