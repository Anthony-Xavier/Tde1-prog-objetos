package domain;

public abstract class Pessoa {
	private String nome;
	private String cpf;
	private String telefone;

	protected Pessoa(String nome, String cpf, String telefone) {
		super();
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
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	private void setCpf(String cpf) {
		if (cpf == null || cpf.isBlank()) {
			throw new IllegalArgumentException("CPF não pode ser vazio.");
		}
		String cpfLimpo = cpf.replaceAll("[.\\-\\s]", "");
		if (!cpfLimpo.matches("\\d+")) {
			throw new IllegalArgumentException("CPF deve conter apenas números.");
		}
		if (cpfLimpo.length() != 11) {
			throw new IllegalArgumentException("CPF deve conter exatamente 11 dígitos.");
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
		if (!telefoneLimpo.matches("\\d+")) {
			throw new IllegalArgumentException("Telefone deve conter apenas números.");
		}
		if (telefoneLimpo.length() < 10 || telefoneLimpo.length() > 11) {
			throw new IllegalArgumentException("Telefone deve ter entre 10 e 11 dígitos.");
		}
		this.telefone = telefoneLimpo;
	}
}
