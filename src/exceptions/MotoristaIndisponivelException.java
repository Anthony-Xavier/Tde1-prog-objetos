package exceptions;

public class MotoristaIndisponivelException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MotoristaIndisponivelException() {
        super("O motorista está indisponível.");
    }

    public MotoristaIndisponivelException(String mensagem) {
        super(mensagem);
    }
}