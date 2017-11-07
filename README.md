# experimento-neo4j
Experimento realizado sobre o banco de dados Neo4j

### Linguagem utilizada no Neo4j

- Criando definição de Pessoa:
```
CREATE (n:Pessoa{nome:"Emanoel", idade:23}) RETURN n
CREATE (n:Pessoa{nome:"Bruno", idade:20}) RETURN n
CREATE (n:Pessoa{nome:"Gabriell", idade:25}) RETURN n
```
- Criando definição de Grupo:
```
CREATE (n:Grupo{nome:"PUCRS"}) RETURN n
```
- Criando um relacionamento entre pessoas:
```
MATCH (a:Pessoa{nome:"Emanoel"}),(b:Pessoa{nome:"Gabriell"}) MERGE (a)-[r:AMIGO_DE]->(b)
```

- Criando um relacionamento entre grupo:
```
MATCH (a:Pessoa{nome:"Emanoel"}),(b:Grupo{nome:"PUCRS"}) MERGE (a)-[r:PARTICIPA]->(b)
```
- Deletar informações:
```
MATCH(n) OPTIONAL MATCH (n)-[r]-() DELETE n,r
```

