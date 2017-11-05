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
		operacoes.rodarExpressao("MATCH (a) WHERE a.name = \"Emanoel\" OPTIONAL MATCH (a)-[r]->() RETURN a.nome, a.idade");
		
		operacoes.adicionarPessoa(new Pessoa("Bruno", 21));
		operacoes.rodarExpressao("MATCH (a) WHERE a.name = \"Bruno\" OPTIONAL MATCH (a)-[r]->() RETURN a.nome, a.idade");
		
		operacoes.adicionarPessoa(new Pessoa("Gabriell", 21));
		operacoes.rodarExpressao("MATCH (a) WHERE a.name = \"Gabriell\" OPTIONAL MATCH (a)-[r]->() RETURN a.nome, a.idade");
		
		System.out.println("[INFO] adicionando os nodos para os grupos");
		operacoes.adicionarGrupo(new Grupo("PUCRS"));
		
		System.out.println("[INFO] criando as relações para amigos");
		operacoes.adicionarRelacionamentoEntreAmigos("Emanoel", "Pessoa", "Bruno", "Pessoa", Relacionamento.AMIGO_DE);
		operacoes.adicionarRelacionamentoEntreAmigos("Emanoel", "Pessoa", "Gabriell", "Pessoa", Relacionamento.AMIGO_DE);
		operacoes.adicionarRelacionamentoEntreAmigos("Gabriell", "Pessoa", "Bruno", "Pessoa", Relacionamento.AMIGO_DE);
		
		System.out.println("[INFO] criando as relações para grupos");
		operacoes.adicionarRelacionamentoEntreAmigos("Emanoel", "Pessoa", "PUCRS", "Grupo", Relacionamento.PARTICIPA);
		operacoes.adicionarRelacionamentoEntreAmigos("Bruno", "Pessoa", "PUCRS", "Grupo", Relacionamento.PARTICIPA);
		operacoes.adicionarRelacionamentoEntreAmigos("Gabriell", "Pessoa", "PUCRS", "Grupo", Relacionamento.PARTICIPA);
		
		System.out.println("[INFO] base de dados criada");
	}

}
