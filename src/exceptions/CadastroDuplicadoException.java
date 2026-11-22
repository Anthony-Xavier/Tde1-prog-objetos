package exceptions;

public class CadastroDuplicadoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CadastroDuplicadoException() {
        super("O cadastro já existe.");
    }

    public CadastroDuplicadoException(String mensagem) {
        super(mensagem);
    }
}