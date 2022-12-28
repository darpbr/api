# Sistema para cadastro de médicos e pacientes

Projeto criado a partir do curso de Spring Boot da Alura.
Sistema que faz o cadastro, consulta, atualização de dados e
deleção lógica de um banco de dados MySQL utilizando
Spring Boot 3.

# Médicos

## Dados cadastrados:

* Nome;
* e-mail;
* telefone;
* CRM;
* especialidade;
* endereço.

### Dados exibidos na consulta:

* id;
* nome;
* e-mail;
* CRM;
* especialidade.

### Dados possíveis de ser atualizado:

* nome;
* telefone;
* endereço.

# Pacientes

## Dados cadastrados:

* Nome;
* ultimo_nome;
* cpf;
* data_nascimento;
* e-mail;
* telefone;
* endereço.

### Dados exibidos na consulta:

* id;
* nome;
* ultimo_nome;
* cpf;
* telefone.

### Dados possíveis de ser atualizado:

* nome;
* ultimo_nome;
* telefone;
* endereço.

## Bando de dados:

Utilizado o banco de dados MySQL com as tabelas:
**medicos** e **pacientes**. As querys para criação/alteração
de tabelas encontram-se na pasta resources.db.migration.

## Exclusão lógica:

Como requisito foi solicitado que fosse criado um
método de exclusão lógica de um Médico/Paciente.
Para isto adicionamos nas duas entidades um atributo
do tipo boolean chamado ativo. Se ativo=true o Médico/paciente
será apresentado na consulta. Para efetuar a exclusão
de um componente do banco de dados faremos o atributo
ativo assumir valor false, assim o componente não fará
mais parte dos dados retornados na consulta. No banco
de dados este atributo assumirá o valor tinyint.
