# Portal Base EE

Base pronta para evoluir com:

- login
- cadastro de usuários
- JSF + PrimeFaces
- JPA/Hibernate
- EJB
- CDI
- deploy em WildFly

## Explicação da arquitetura

Se você quiser uma explicação mais completa da stack e da estrutura do projeto, leia:

- [docs/ARQUITETURA.md](/workspaces/project-java-ee/docs/ARQUITETURA.md)

## Stack adotada

- Jakarta EE 10 API
- PrimeFaces 15
- WildFly
- JPA com provedor Hibernate do próprio servidor
- Datasource padrão `java:jboss/datasources/ExampleDS`

## Como subir

1. Gere o WAR:

   `mvn clean package`

2. Copie o arquivo `target/portal-base-ee.war` para:

   `WILDFLY_HOME/standalone/deployments/`

3. Suba o WildFly e acesse:

   `http://localhost:8080/portal-base-ee`

## O que já está pronto

- cadastro com validação de e-mail único
- login com senha armazenada em hash PBKDF2
- sessão autenticada
- área interna protegida
- estrutura separada em camadas para crescer depois

## Como crescer depois

Você pode adicionar novos módulos mantendo este padrão:

- `model` para entidades JPA
- `service` para regras de negócio com EJB
- `web` para beans JSF/CDI
- páginas em `src/main/webapp/app`

Exemplos de próximos módulos:

- produtos
- categorias
- pedidos
- clientes
- painel administrativo

## Observação

O projeto usa o datasource `ExampleDS` que normalmente já vem no WildFly para facilitar o primeiro deploy. Se você quiser, depois eu posso adaptar para PostgreSQL ou MySQL com datasource próprio.
