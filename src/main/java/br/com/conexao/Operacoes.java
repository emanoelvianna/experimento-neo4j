package br.com.conexao;

import static org.neo4j.driver.v1.Values.parameters;

import java.util.List;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;

import br.com.modelo.Grupo;
import br.com.modelo.Pessoa;
import br.com.modelo.Relacionamento;

public class Operacoes extends Conexao {

	public List<Record> rodarExpressao(final String expression) {
		try (Session session = this.driver.session()) {
			return session.writeTransaction(new TransactionWork<List<Record>>() {
				@Override
				public List<Record> execute(Transaction transaction) {
					StatementResult result = transaction.run(expression);
					return result.list();
				}
			});
		}
	}

	public void limparDB() {
		try (Session session = this.driver.session()) {
			session.writeTransaction(new TransactionWork<Void>() {
				@Override
				public Void execute(Transaction transaction) {
					transaction.run("MATCH (a) OPTIONAL MATCH (a)-[r]->() DELETE a, r");
					return null;
				}
			});
		}
	}

	public long adicionarPessoa(Pessoa pessoa) {
		try (Session session = this.driver.session()) {
			session.writeTransaction(new TransactionWork<Void>() {
				@Override
				public Void execute(Transaction transaction) {
					return Operacoes.this.criarNodoPessoa(transaction, pessoa);
				}
			});
			return session.readTransaction(new TransactionWork<Long>() {
				@Override
				public Long execute(Transaction transaction) {
					return Operacoes.this.combinarPessoaNodo(transaction, pessoa);
				}
			});
		}
	}

	protected Void criarNodoPessoa(Transaction transaction, Pessoa pessoa) {
		transaction.run("CREATE (a:Pessoa {nome: $nome, idade: $idade})",
				parameters("nome", pessoa.getNome(), "idade", pessoa.getIdade()));
		return null;
	}

	public long combinarPessoaNodo(Transaction transaction, Pessoa pessoa) {
		StatementResult result = transaction.run("MATCH (a:Pessoa {nome: $nome}) RETURN id(a)",
				parameters("nome", pessoa.getNome()));

		return result.single().get(0).asLong();
	}

	public void adicionarRelacionamentoEntreAmigos(final String nomeA, final String definicaoA, final String nomeB,
			final String definicaoB, final Relacionamento relacionamento) {
		try (Session session = this.driver.session()) {
			session.writeTransaction(new TransactionWork<Void>() {
				@Override
				public Void execute(Transaction transaction) {

					String statement = "MATCH (a:" + definicaoA + ") , (b:" + definicaoB + ") " + "WHERE a.nome = \""
							+ nomeA + "\" AND b.nome = \"" + nomeB + "\" " + "CREATE (a)-[r1:"
							+ relacionamento.getValor() + "{nome: \"" + relacionamento.getChave() + "\"}]->(b), "
							+ "(b)-[r2:" + relacionamento.getValor() + "{nome: \"" + relacionamento.getChave()
							+ "\"}]->(a)" + "RETURN a.nome, r1.nome, b.nome, r2.nome";

					StatementResult result = transaction.run(statement);
					result.list().forEach(r -> System.out.println(r));
					return null;
				}
			});
		}
	}

	public long adicionarGrupo(Grupo grupo) {
		try (Session session = this.driver.session()) {
			session.writeTransaction(new TransactionWork<Void>() {
				@Override
				public Void execute(Transaction transaction) {
					return Operacoes.this.criarNodoGrupo(transaction, grupo);
				}
			});
			return session.readTransaction(new TransactionWork<Long>() {
				@Override
				public Long execute(Transaction transaction) {
					return Operacoes.this.combinarGrupoNodo(transaction, grupo);
				}
			});
		}
	}

	protected Void criarNodoGrupo(Transaction transaction, Grupo grupo) {
		transaction.run("CREATE (a:Grupo {nome: $nome})", parameters("nome", grupo.getNome()));
		return null;
	}

	public long combinarGrupoNodo(Transaction transaction, Grupo grupo) {
		StatementResult result = transaction.run("MATCH (a:Grupo {nome: $nome}) RETURN id(a)",
				parameters("nome", grupo.getNome()));

		return result.single().get(0).asLong();
	}

	public void adicionarRelacionamentoEntreGrupo(final String nomeA, final String definicaoA, final String nomeB,
			final String definicaoB, final Relacionamento relacionamento) {
		try (Session session = this.driver.session()) {
			session.writeTransaction(new TransactionWork<Void>() {
				@Override
				public Void execute(Transaction transaction) {
					String statement = "MATCH (a:Pessoa{" + definicaoA + ":\"" + nomeA + "\"}),(b:Grupo{" + definicaoB
							+ ":\"" + nomeB + "\"}) MERGE (a)-[r:" + relacionamento.getValor() + "]->(b)";

					StatementResult result = transaction.run(statement);
					result.list().forEach(r -> System.out.println(r));
					return null;
				}
			});
		}
	}

}
