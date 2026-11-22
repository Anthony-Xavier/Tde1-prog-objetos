package exceptions;

public class AvaliacaoInvalidaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AvaliacaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}