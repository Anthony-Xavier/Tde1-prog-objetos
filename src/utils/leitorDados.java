package utils;
 
import java.util.Scanner;

public class leitorDados {

	private static final Scanner teclado = new Scanner(System.in);

	public static String lerNome(String mensagem) {
		while (true) {
			System.out.print(mensagem);
			String nome = teclado.nextLine().trim();

			if (nome.matches("[A-Za-zÀ-ÿ ]+")) {
				return nome;
			}

			System.out.println("Nome inválido. Digite apenas letras.");
		}
	}

	public static String lerCpf(String mensagem) {
		while (true) {
			System.out.print(mensagem);
			String cpf = teclado.nextLine().trim();

			String cpfLimpo = cpf.replaceAll("[.\\-\\s]", "");

			if (cpfLimpo.matches("\\d{11}")) {
				return cpfLimpo;
			}

			System.out.println("CPF inválido. Digite exatamente 11 números.");
		}
	}

	public static String lerTelefone(String mensagem) {
		while (true) {
			System.out.print(mensagem);
			String telefone = teclado.nextLine().trim();

			String telefoneLimpo = telefone.replaceAll("[()\\-\\s]", "");

			if (telefoneLimpo.matches("\\d{10,11}")) {
				return telefoneLimpo;
			}

			System.out.println("Telefone inválido. Digite 10 ou 11 números.");
		}
	}
	
	public static String lerCnh(String mensagem) {
		while (true) {
			System.out.print(mensagem);
			String cnh = teclado.nextLine().trim();

			if (cnh.matches("\\d{11}")) {
				return cnh;
			}

			System.out.println("CNH inválida. Digite exatamente 11 números.");
		}
	}
	
	public static String lerTexto(String mensagem) {

		while (true) {
			System.out.print(mensagem);
			String texto = teclado.nextLine().trim();

			if (!texto.isEmpty()) {
				return texto;
			}

			System.out.println("O campo não pode ficar vazio.");
		}
	}

	public static String lerTextoOpcional(String mensagem) {
		System.out.print(mensagem);
		return teclado.nextLine().trim();
	}

	public static int lerInt(String mensagem) {

		while (true) {
			System.out.print(mensagem);
			String valor = teclado.nextLine().trim();

			try {
				return Integer.parseInt(valor);
			}
			catch (NumberFormatException e) {
				System.out.println("Digite um número inteiro válido.");
			}
		}
	}

	public static double lerDouble(String mensagem) {
	
		while (true) {
			System.out.print(mensagem);
			String valor = teclado.nextLine().trim().replace(",", ".");

			try {
				return Double.parseDouble(valor);
			}
			catch (NumberFormatException e) {
				System.out.println("Digite um número válido.");
			}
		}
	}
	
	public static String lerPlaca(String mensagem) {

		while (true) {
			System.out.print(mensagem);

			String placa = teclado.nextLine().trim().toUpperCase().replace("-", "");

			if (placa.matches("[A-Z]{3}[0-9][A-Z0-9][0-9]{2}")) {
				return placa;
			}

			System.out.println("Placa inválida. Exemplo: ABC1234 ou ABC1D23.");
		}
	}
	
	public static boolean lerSimNao(String mensagem) {

		while (true) {

			System.out.print(mensagem);
			String resposta = teclado.nextLine().trim();

			if (resposta.equalsIgnoreCase("S")) {
				return true;
			}

			if (resposta.equalsIgnoreCase("N")) {
				return false;
			}

			System.out.println("Digite S ou N.");
		}
	}
}