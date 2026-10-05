package ui;

import java.util.ArrayList;

import domain.Motorista;
import domain.Passageiro;
import sistema.Empresa;
import veiculo.Carro;
import veiculo.Moto;
import veiculo.Van;
import veiculo.Veiculo;

public class MenuCadastros extends Menu {

	public MenuCadastros(Empresa empresa) {
		super(empresa);
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

	protected void receberResposta(int resposta) {
		switch(resposta) {
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
		String nome = lerTexto("Nome");
		String cpf = lerTexto("CPF");
		String telefone = lerTexto("Telefone");
		Passageiro passageiro = new Passageiro(nome, cpf, telefone);
		empresa.cadastrarPassageiro(passageiro);
		System.out.println("Passageiro cadastrado: " + passageiro.getNome());
	}

	private void cadastrarMotorista() {
		ArrayList<Veiculo> veiculosLivres = empresa.getVeiculosSemMotorista();
		if (veiculosLivres.isEmpty()) {
			System.out.println("Não há veículos livres. Cadastre um veículo antes do motorista.");
			return;
		}
		String nome = lerTexto("Nome");
		String cpf = lerTexto("CPF");
		String telefone = lerTexto("Telefone");
		String cnh = lerTexto("Número da CNH");

		System.out.println("Veículo associado:");
		for (int i = 0; i < veiculosLivres.size(); i++) {
			System.out.println((i + 1) + " - " + veiculosLivres.get(i).getDescricao());
		}
		int indice = lerOpcao(veiculosLivres.size());
		if (indice < 0) {
			System.out.println("Cadastro cancelado.");
			return;
		}

		Motorista motorista = new Motorista(nome, cpf, telefone, cnh, veiculosLivres.get(indice));
		empresa.cadastrarMotorista(motorista);
		System.out.println("Motorista cadastrado: " + motorista.getNome() + " (disponível)");
	}

	private void cadastrarVeiculo() {
		System.out.println("Tipo de veículo:");
		System.out.println("1 - Carro");
		System.out.println("2 - Moto");
		System.out.println("3 - Van");
		int tipo = lerOpcao(3) + 1;
		if (tipo == 0) {
			System.out.println("Cadastro cancelado.");
			return;
		}
		String placa = lerTexto("Placa");
		String modelo = lerTexto("Modelo");

		Veiculo veiculo;
		switch (tipo) {
		case 1:
			veiculo = new Carro(placa, modelo);
			break;
		case 2:
			veiculo = new Moto(placa, modelo);
			break;
		default:
			veiculo = new Van(placa, modelo);
		}
		empresa.cadastrarVeiculo(veiculo);
		System.out.println("Veículo cadastrado: " + veiculo.getDescricao());
	}

}
