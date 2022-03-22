
# PROJETO PADRAO
Autor: André Mostaro

### INFORMAÇÕES INICIAIS:
Este projeto padrão está preparado para iniciar um projeto de backend em JAVA.

### ESTRUTURA:
- Java 11 SDK 11
- Spring Boot DevTools
- Spring Security
- Spring Data JPA
- Spring Web
- Templates Thymeleaf
- MySQL Driver
- PostgreSQL Driver
- Oracle Database Driver
- H2 Driver

### COMPILADOR:
- Gradle

### DB CONFIGURADOS:
- MySQL
- PostgreSQL

# INFORMAÇÃO IMPORTANTE!!!
### APÓS BAIXAR O PROJETO, COLOQUE-O PARA RODAR E VERÁ O SEGUINTE ERRO:
_java.sql.SQLException: Access denied for user 'root'@'localhost' (using password: YES)_

### SOLUÇÃO MYSQL:
- `CREATE DATABASE` localhost MYSQL
- atualizar os dados abaixo na `application.properties`
- spring.datasource.url = jdbc:mysql://localhost:3306/`db_criada`?useTimezone=true&serverTimezone=America/Sao_Paulo
- spring.datasource.username = `root`
- spring.datasource.password = `senha`

### SOLUÇÃO POSTGRESQL:
- abrir `application.properties` e trocar as configurações ativas do DB para PostgreSQL
- seguir a orientação na `application.properties`
- `CREATE DATABASE` localhost POSTGRESQL
- atualizar os dados abaixo na `application.properties`
- spring.datasource.url = jdbc:postgresql://localhost:5432/`db_criada`?useTimezone=true&serverTimezone=America/Sao_Paulo
- spring.datasource.username = `postgres`
- spring.datasource.password = `senha`
