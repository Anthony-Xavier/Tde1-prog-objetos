package cadastro;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import categoria.CategoriaCorrida;
import entidade.Motorista;
import veiculo.Veiculo;
import exceptions.CadastroDuplicadoException;

public class CadastroMotoristas implements Serializable {

	private static final long serialVersionUID = 1L;

    private List<Motorista> motoristas = new ArrayList<>();

    public Motorista cadastrar(Motorista motorista) {

        if (consultarPorCpf(motorista.getCpf()) != null) {
            throw new CadastroDuplicadoException(
                "Já existe um motorista cadastrado com o CPF: "
                + motorista.getCpf()
            );
        }

        motoristas.add(motorista);
        return motorista;
    }

    public Motorista consultarPorCpf(String cpf) {

        for (Motorista motorista : motoristas) {

            if (motorista.getCpf().equals(cpf)) {
                return motorista;
            }
        }

        return null;
    }

    public List<Motorista> listarTodos() {
        return motoristas;
    }

    public boolean veiculoJaVinculado(Veiculo veiculo) {

        for (Motorista motorista : motoristas) {

            if (motorista.getVeiculo() != null
                    && motorista.getVeiculo().getPlaca()
                            .equalsIgnoreCase(veiculo.getPlaca())) {

                return true;
            }
        }

        return false;
    }

    public List<Motorista> listarDisponiveis() {

        List<Motorista> disponiveis = new ArrayList<>();

        for (Motorista motorista : motoristas) {

            if (motorista.isDisponivel()) {
                disponiveis.add(motorista);
            }
        }

        return disponiveis;
    }

    public List<Motorista> listarDisponiveisPorCategoria(CategoriaCorrida categoria) {

        List<Motorista> compativeis = new ArrayList<>();

        for (Motorista motorista : listarDisponiveis()) {

            if (categoria.isVeiculoCompativel(motorista.getVeiculo())) {
                compativeis.add(motorista);
            }
        }

        return compativeis;
    }
}