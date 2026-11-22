package exceptions;

public class CorridaNaoEncontradaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CorridaNaoEncontradaException() {
        super("Corrida não encontrada.");
    }

    public CorridaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}