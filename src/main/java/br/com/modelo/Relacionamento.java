package br.com.modelo;

public enum Relacionamento {

	PARTICIPA("participa", "PARTICIPA"), AMIGO_DE("amigo_de", "AMIGO DE");

	private final String chave;
	private final String valor;

	Relacionamento(String chave, String valor) {
		this.chave = chave;
		this.valor = valor;
	}

	public String getChave() {
		return chave;
	}

	public String getValor() {
		return valor;
	}
}
