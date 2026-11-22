
package exceptions;

public class TransicaoCorridaInvalidaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TransicaoCorridaInvalidaException() {
        super("Transição de estado da corrida inválida.");
    }

    public TransicaoCorridaInvalidaException(String mensagem) {
        super(mensagem);
    }
}
