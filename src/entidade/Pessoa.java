package entidade;

public abstract class Pessoa {

	private String nome;
	private String cpf;
	private String telefone;

	protected Pessoa(String nome, String cpf, String telefone) {
		this.setNome(nome);
		this.setCpf(cpf);
		this.setTelefone(telefone);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if (nome == null || nome.isBlank()) {
			throw new IllegalArgumentException("Nome não pode ser vazio.");
		}

		String nomeLimpo = nome.trim();

		if (!nomeLimpo.matches("[A-Za-zÀ-ÿ ]+")) {
			throw new IllegalArgumentException("Nome deve conter apenas letras.");
		}

		this.nome = nomeLimpo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		if (cpf == null || cpf.isBlank()) {
			throw new IllegalArgumentException("CPF não pode ser vazio.");
		}

		String cpfLimpo = cpf.replaceAll("[.\\-\\s]", "");

		if (!cpfLimpo.matches("\\d{11}")) {
			throw new IllegalArgumentException(
					"CPF deve conter exatamente 11 números."
			);
		}

		this.cpf = cpfLimpo;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		if (telefone == null || telefone.isBlank()) {
			throw new IllegalArgumentException("Telefone não pode ser vazio.");
		}

		String telefoneLimpo = telefone.replaceAll("[()\\-\\s]", "");

		if (!telefoneLimpo.matches("\\d{10,11}")) {
			throw new IllegalArgumentException(
					"Telefone deve conter apenas números e ter 10 ou 11 dígitos."
			);
		}

		this.telefone = telefoneLimpo;
	}

	@Override
	public String toString() {
		return "Nome: " + nome
				+ "\nCPF: " + cpf
				+ "\nTelefone: " + telefone;
	}
}