package ui;

import cadastro.CadastroMotoristas;
import cadastro.CadastroPassageiros;
import cadastro.CadastroVeiculos;
import entidade.Motorista;
import entidade.Passageiro;
import utils.leitorDados;
import veiculo.Veiculo;
import veiculo.Carro;
import veiculo.Moto;
import veiculo.Van;


public class MenuCadastros extends Menu {

	private CadastroPassageiros cadastroPassageiros;
	private CadastroMotoristas cadastroMotoristas;
	private CadastroVeiculos cadastroVeiculos;

	public MenuCadastros(
			CadastroPassageiros cadastroPassageiros,
			CadastroMotoristas cadastroMotoristas,
			CadastroVeiculos cadastroVeiculos) {

		this.cadastroPassageiros = cadastroPassageiros;
		this.cadastroMotoristas = cadastroMotoristas;
		this.cadastroVeiculos = cadastroVeiculos;
	}
	
	@Override
	protected void mostrarTitulo() {
		System.out.println(" - Cadastros");
	}

	@Override
	protected void mostrarOpcoes() {
		System.out.println("1 - Cadastrar passageiro");
		System.out.println("2 - Cadastrar motorista");
		System.out.println("3 - Cadastrar veículo");
	}

	@Override
	protected void receberResposta(int resposta) {

		switch (resposta) {

		case 1:
			cadastrarPassageiro();
			break;

		case 2:
			cadastrarMotorista();
			break;

		case 3:
			cadastrarVeiculo();
			break;

		default:
			System.out.println("Resposta inválida.");
		}
	}

	private void cadastrarPassageiro() {

		while (true) {

			String nome = leitorDados.lerNome("Nome: ");
			String cpf = leitorDados.lerCpf("CPF: ");
			String telefone = leitorDados.lerTelefone("Telefone: ");

			Passageiro passageiro = new Passageiro(
					nome,
					cpf,
					telefone
			);

			cadastroPassageiros.cadastrar(passageiro);

			System.out.println("Passageiro cadastrado com sucesso.");

			if (!leitorDados.lerSimNao(
					"Deseja cadastrar outro passageiro? (S/N): ")) {
				break;
			}
		}
	}
	
	private void cadastrarMotorista() {

		while (true) {

			String nome = leitorDados.lerNome("Nome: ");
			String cpf = leitorDados.lerCpf("CPF: ");
			String telefone = leitorDados.lerTelefone("Telefone: ");
			String cnh = leitorDados.lerCnh("CNH: ");

			String placa = leitorDados.lerPlaca("Placa do veículo: ");

			Veiculo veiculo = cadastroVeiculos.consultarPorPlaca(placa);

			while (veiculo == null) {

				System.out.println("Veículo não encontrado.");

				if (leitorDados.lerSimNao(
						"Deseja cadastrar um veículo agora? (S/N): ")) {

					cadastrarVeiculo();

					placa = leitorDados.lerPlaca(
							"Informe a placa do veículo cadastrado: ");
				}
				else {

					placa = leitorDados.lerPlaca(
							"Informe outra placa: ");
				}

				veiculo = cadastroVeiculos.consultarPorPlaca(placa);
			}

			while (cadastroMotoristas.veiculoJaVinculado(veiculo)) {

				System.out.println("Esse veículo já está associado a outro motorista.");

				if (leitorDados.lerSimNao(
						"Deseja cadastrar outro veículo agora? (S/N): ")) {

					cadastrarVeiculo();

					placa = leitorDados.lerPlaca(
							"Informe a placa do novo veículo: ");
				}
				else {

					placa = leitorDados.lerPlaca(
							"Informe outra placa de veículo: ");
				}

				veiculo = cadastroVeiculos.consultarPorPlaca(placa);

				while (veiculo == null) {

					System.out.println("Veículo não encontrado.");

					if (leitorDados.lerSimNao(
							"Deseja cadastrar esse veículo agora? (S/N): ")) {

						cadastrarVeiculo();

						placa = leitorDados.lerPlaca(
								"Informe a placa do veículo cadastrado: ");
					}
					else {

						placa = leitorDados.lerPlaca(
								"Informe outra placa: ");
					}

					veiculo = cadastroVeiculos.consultarPorPlaca(placa);
				}
			}

			Motorista motorista = new Motorista(
					nome,
					cpf,
					telefone,
					cnh,
					veiculo
			);

			cadastroMotoristas.cadastrar(motorista);

			System.out.println("Motorista cadastrado com sucesso.");

			if (!leitorDados.lerSimNao(
					"Deseja cadastrar outro motorista? (S/N): ")) {
				break;
			}
		}
	}
	
	
	private void cadastrarVeiculo() {

		while (true) {

			System.out.println("1 - Carro");
			System.out.println("2 - Moto");
			System.out.println("3 - Van");

			int tipo = leitorDados.lerInt("Tipo de veículo: ");

			String placa = leitorDados.lerPlaca("Placa: ");
			String modelo = leitorDados.lerTexto("Modelo: ");

			Veiculo veiculo;

			switch (tipo) {

			case 1:
				veiculo = new Carro(placa, modelo);
				break;

			case 2:
				veiculo = new Moto(placa, modelo);
				break;

			case 3:
				veiculo = new Van(placa, modelo);
				break;

			default:
				System.out.println("Tipo de veículo inválido.");
				continue;
			}

			cadastroVeiculos.cadastrar(veiculo);

			System.out.println("Veículo cadastrado com sucesso.");

			if (!leitorDados.lerSimNao(
					"Deseja cadastrar outro veículo? (S/N): ")) {
				break;
			}
		}
	}
}