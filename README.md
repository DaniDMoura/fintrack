# fintrack

**FinTrack** é uma API REST para **gestão financeira pessoal** desenvolvida com **Java e Spring Boot**.  
A aplicação permite registrar **receitas**, **despesas** e gerar **resumos financeiros mensais e anuais** com base nos dados do usuário autenticado.

O projeto foi desenvolvido com foco em:

- arquitetura em camadas
- segurança com autenticação JWT
- consultas otimizadas no banco
- observabilidade através de logging com AOP
- integração contínua com Jenkins
- execução em container com Docker
- versionamento do banco com Flyway

---

# Tecnologias

- Java
- Spring Boot
- Spring Security
- JWT Authentication
- Spring AOP (logging)
- JPA / Hibernate
- PostgreSQL
- Flyway (migrations SQL)
- Maven
- Docker
- Jenkins (Continuous Integration)
- SQL (queries customizadas e agregações)

---

# Funcionalidades

## Autenticação

O sistema utiliza autenticação baseada em **JWT**.

Fluxo:

1. Usuário realiza registro
2. Usuário realiza login
3. A API gera um **token JWT**
4. O token deve ser enviado no header das requisições autenticadas

Header esperado:

```
Authorization: Bearer <token>
```

---

## Gestão de Usuário

Funcionalidades disponíveis para o usuário autenticado:

- registro de conta
- login
- consulta do usuário atual
- exclusão da própria conta

Usuários possuem **roles**:

- `ROLE_USER`
- `ROLE_ADMIN`

---

## Gestão de Despesas

O sistema permite registrar e gerenciar despesas do usuário.

Funcionalidades:

- criar despesa
- listar despesas
- filtrar despesas por mês
- atualizar despesa
- remover despesa

Cada despesa possui:

- valor
- categoria
- descrição
- data de criação
- relacionamento com usuário

---

## Gestão de Receitas

O sistema permite registrar receitas mensais.

Funcionalidades:

- criar receita
- atualizar receita
- atualizar apenas o valor da receita
- consultar receita atual

Cada receita contém:

- valor
- descrição
- mês de referência (`YearMonth`)
- relacionamento com usuário

---

## Resumo Financeiro Mensal

O sistema gera automaticamente um resumo financeiro mensal.

Dados calculados:

- receita total
- despesa total
- resultado líquido
- taxa de poupança

Cálculo:

```
netResult = totalIncome - totalExpenses
savingsRate = netResult / totalIncome
```

---

## Resumo Financeiro Anual

Para um determinado ano a API retorna:

- receita total anual
- despesa total anual
- resultado líquido anual
- taxa de poupança anual
- média mensal de receita
- média mensal de despesas
- maior receita mensal
- maior despesa mensal
- menor receita mensal
- menor despesa mensal

Também é retornado um **resumo financeiro de cada mês do ano**.

---

## Administração

Usuários com role **ADMIN** possuem permissões administrativas.

Funcionalidades:

- listar todos os usuários
- consultar usuário por id
- remover usuários
- promover usuário para admin
- consultar despesas de qualquer usuário
- consultar receitas de qualquer usuário
- remover despesas ou receitas

---

## Logging com AOP

O projeto utiliza **Spring AOP** para implementar logging centralizado.

Isso permite:

- registrar execução de métodos
- registrar parâmetros relevantes
- medir tempo de execução
- manter o código de negócio desacoplado da lógica de logging

---

## Queries Otimizadas

Consultas críticas utilizam **queries customizadas no repositório** para melhorar performance.

Exemplos de consultas otimizadas:

- soma de despesas por mês
- agregações financeiras
- consultas filtradas por usuário
- consultas por período (`YearMonth`)

Essas operações são executadas diretamente no banco.

---

# Configuração do Banco de Dados

O projeto utiliza **PostgreSQL**.

As migrations são gerenciadas pelo **Flyway**, permitindo versionamento do schema do banco através de **scripts SQL**.

Exemplo de configuração no `application.properties`:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/fintrack
spring.datasource.username=postgres
spring.datasource.password=password

spring.jpa.hibernate.ddl-auto=validate

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
```

---

# Continuous Integration (Jenkins)

O projeto possui **pipeline de CI utilizando Jenkins**.

A pipeline executa automaticamente:

- build da aplicação
- build da imagem Docker
- push da imagem para o registry

A pipeline também envia **email em caso de sucesso do build**.

Exemplo de `Jenkinsfile`:

```groovy
pipeline {
  agent any

  parameters {
     string(name: 'EMAIL', defaultValue: 'example@email.com')
  }

  environment {
    DOCKER_CREDS = credentials('DOCKER_SECRET')
  }

  triggers {
    pollSCM('H/5 * * * *')
  }

  stages {

    stage('Build Image') {
      steps {
        sh './jenkins/scripts/build.sh ${DOCKER_CREDS_USR} ${env.BUILD_NUMBER}'
      }
    }

    stage('Push Image') {
      steps {
        sh './jenkins/scripts/push.sh ${DOCKER_CREDS_USR} ${DOCKER_CREDS_PSW} ${env.BUILD_NUMBER}'
      }
    }

  }

  post {
    success {
      mail(
        to: params.EMAIL,
        subject: "SUCCESS - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
        body: "Build concluída com sucesso.\n${env.BUILD_URL}"
      )
    }
  }
}
```

O pipeline é acionado automaticamente via:

```
pollSCM('H/5 * * * *')
```

Ou seja, o Jenkins verifica mudanças no repositório a cada 5 minutos.

---

# Docker

A aplicação pode ser executada em container utilizando **Docker**.

Build da imagem:

```
docker build -t fintrack .
```

Executar container:

```
docker run -p 8080:8080 fintrack
```

---

# Formas de Rodar o Projeto

## Rodar Localmente

Build da aplicação:

```
./mvnw clean package
```

Executar:

```
java -jar target/fintrack.jar
```

A API iniciará em:

```
http://localhost:8080
```

---

## Rodar com Docker

Build da imagem:

```
docker build -t fintrack .
```

Executar container:

```
docker run -p 8080:8080 fintrack
```

---

# Objetivo do Projeto

Este projeto foi desenvolvido para praticar:

- arquitetura backend com Spring Boot
- autenticação segura com JWT
- modelagem de domínio financeiro
- otimização de queries SQL
- observabilidade com AOP
- integração contínua com Jenkins
- containerização com Docker
- versionamento de banco com Flyway
