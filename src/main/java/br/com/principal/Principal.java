package br.com.principal;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.driver.v1.Record;

import br.com.conexao.Operacoes;
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
		
		System.out.println("[INFO] adicionando os nodos para os grupos");
		//TODO:
		
		System.out.println("[INFO] criando as relações");
		//operacoes.adicionarRelacionamento("Emanoel", "Pessoa", "Bruno", "Pessoa", Relacionamento.AMIGO_DE);
	}

}
