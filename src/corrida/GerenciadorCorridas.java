package corrida;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import categoria.CategoriaCorrida;
import entidade.Motorista;
import entidade.Passageiro;
import pagamento.FormaPagamento;
import exceptions.CorridaNaoEncontradaException;

public class GerenciadorCorridas implements Serializable {

	private static final long serialVersionUID = 1L;

    private List<Corrida> corridas = new ArrayList<>();
    private int proximoId = 1;

    public Corrida solicitarCorrida(
            Passageiro passageiro,
            String origem,
            String destino,
            double distanciaEstimada,
            CategoriaCorrida categoria,
            FormaPagamento formaPagamento) {

        Corrida corrida = new Corrida(
                proximoId,
                passageiro,
                origem,
                destino,
                distanciaEstimada,
                categoria,
                formaPagamento
        );

        corridas.add(corrida);
        passageiro.adicionarCorrida(corrida);
        proximoId++;

        return corrida;
    }

    public Corrida buscarPorId(int id) {

        for (Corrida corrida : corridas) {

            if (corrida.getId() == id) {
                return corrida;
            }
        }

        return null;
    }

    public void aceitarCorrida(int id, Motorista motorista) {

        Corrida corrida = buscarPorId(id);

        if (corrida == null) {
            throw new CorridaNaoEncontradaException(
                "Corrida não encontrada. ID: " + id
            );
        }

        corrida.aceitar(motorista);
    }

    public void iniciarCorrida(int id) {

        Corrida corrida = buscarPorId(id);

        if (corrida == null) {
            throw new CorridaNaoEncontradaException(
                "Corrida não encontrada. ID: " + id
            );
        }

        corrida.iniciar();
    }

    public void finalizarCorrida(int id) {

        Corrida corrida = buscarPorId(id);

        if (corrida == null) {
            throw new CorridaNaoEncontradaException(
                "Corrida não encontrada. ID: " + id
            );
        }

        corrida.finalizar();
    }

    public void cancelarCorrida(int id) {

        Corrida corrida = buscarPorId(id);

        if (corrida == null) {
            throw new CorridaNaoEncontradaException(
                "Corrida não encontrada. ID: " + id
            );
        }

        corrida.cancelar();
    }

    public void avaliarMotorista(int id, Avaliacao avaliacao) {

        Corrida corrida = buscarPorId(id);

        if (corrida == null) {
            throw new CorridaNaoEncontradaException(
                "Corrida não encontrada. ID: " + id
            );
        }

        corrida.avaliar(avaliacao);
    }

    public List<Corrida> listarPorEstado(EstadoCorrida estado) {

        List<Corrida> resultado = new ArrayList<>();

        for (Corrida corrida : corridas) {

            if (corrida.getEstado() == estado) {
                resultado.add(corrida);
            }
        }

        return resultado;
    }

    public List<Corrida> listarTodas() {
        return corridas;
    }
}