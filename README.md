
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
- PostgreSQl

# INFORMAÇÃO IMPORTANTE!!!
### APÓS BAIXAR O PROJETO, COLOQUE-O PARA RODAR E VERÁ O SEGUINTE ERRO:
_java.sql.SQLException: Access denied for user 'root'@'localhost' (using password: YES)_

### SOLUÇÃO:
`CREATE DATABASE` localhost MYSQL e depois os dados abaixo na `application.properties`
- spring.datasource.url = jdbc:mysql://localhost:3306/`db_projeto_padrao`?useTimezone=true&serverTimezone=America/Sao_Paulo
- spring.datasource.username = `root`
- spring.datasource.password = `senha`
