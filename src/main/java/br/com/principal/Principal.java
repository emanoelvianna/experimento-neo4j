package br.com.principal;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.driver.v1.Record;

import br.com.conexao.Operacoes;
import br.com.modelo.Grupo;
import br.com.modelo.Pessoa;
import br.com.modelo.Relacionamento;

public class Principal {

	public static void main(String[] args) {
		Operacoes operacoes = new Operacoes();
		List<Record> resultados = new ArrayList<Record>();

		System.out.println("[INFO] iniciando conexao");
		System.out.println("[INFO] iniciando a base de dados");
		operacoes.limparDB();

		System.out.println("[INFO] adicionando os nodos para pessoas");
		operacoes.adicionarPessoa(new Pessoa("Emanoel", 23));

		operacoes.adicionarPessoa(new Pessoa("Bruno", 21));

		operacoes.adicionarPessoa(new Pessoa("Gabriell", 21));

		System.out.println("[INFO] adicionando os nodos para os grupos");
		operacoes.adicionarGrupo(new Grupo("PUCRS"));

		System.out.println("[INFO] criando as relações para amigos");
		operacoes.adicionarRelacionamentoEntreAmigos("Emanoel", "Bruno", Relacionamento.AMIGO_DE);
		operacoes.adicionarRelacionamentoEntreAmigos("Emanoel", "Gabriell", Relacionamento.AMIGO_DE);
		operacoes.adicionarRelacionamentoEntreAmigos("Gabriell", "Bruno", Relacionamento.AMIGO_DE);

		System.out.println("[INFO] criando as relações para grupos");
		operacoes.adicionarRelacionamentoEntreGrupo("Emanoel", "PUCRS", Relacionamento.PARTICIPA);
		operacoes.adicionarRelacionamentoEntreGrupo("Bruno", "PUCRS", Relacionamento.PARTICIPA);
		operacoes.adicionarRelacionamentoEntreGrupo("Gabriell", "PUCRS", Relacionamento.PARTICIPA);

		System.out.println("[INFO] base de dados criada");
	}

}
