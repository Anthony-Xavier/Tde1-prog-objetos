package corrida;

import java.time.LocalDateTime;

public class Avaliacao {

	private int nota;
	private String comentario;
	private LocalDateTime data;

	public Avaliacao(int nota, String comentario) {
		if (nota < 1 || nota > 5) {
			throw new IllegalArgumentException("Nota deve estar entre 1 e 5.");
		}
		this.nota = nota;
		this.comentario = comentario;
		this.data = LocalDateTime.now();
	}

	public int getNota() {
		return nota;
	}

	public String getComentario() {
		return comentario;
	}

	public LocalDateTime getData() {
		return data;
	}

}