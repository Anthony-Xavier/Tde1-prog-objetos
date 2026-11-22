package cadastro;

import java.util.ArrayList;
import java.util.List;

import entidade.Passageiro;
import exceptions.CadastroDuplicadoException;

public class CadastroPassageiros {

    private List<Passageiro> passageiros = new ArrayList<>();

    public Passageiro cadastrar(Passageiro passageiro) {

        if (consultarPorCpf(passageiro.getCpf()) != null) {
            throw new CadastroDuplicadoException(
                "Já existe um passageiro cadastrado com o CPF: "
                + passageiro.getCpf()
            );
        }

        passageiros.add(passageiro);
        return passageiro;
    }

    public Passageiro consultarPorCpf(String cpf) {

        for (Passageiro passageiro : passageiros) {

            if (passageiro.getCpf().equals(cpf)) {
                return passageiro;
            }
        }

        return null;
    }

    public List<Passageiro> listarTodos() {
        return passageiros;
    }
}