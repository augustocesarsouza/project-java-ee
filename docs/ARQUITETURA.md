# Arquitetura do projeto

Este projeto foi montado como uma base para sistemas web corporativos em Java usando:

- JSF
- PrimeFaces
- JPA/Hibernate
- EJB
- CDI
- WildFly

O objetivo é simples: deixar uma estrutura pronta para começar com login e cadastro e depois crescer para módulos como produtos, pedidos, clientes ou administração.

## Sobre o nome "Java EE"

Você pediu:

`Java EE (JSF, Primefaces, JPA/Hibernate, EJB, CDI) utilizando o servidor de aplicação Wildfly`

Na prática, hoje o nome oficial dessa plataforma é `Jakarta EE`.

Ou seja:

- Java EE = nome antigo
- Jakarta EE = nome atual

O projeto foi implementado com a stack atual, usando pacotes `jakarta.*`, porque isso é o caminho mais compatível com versões atuais do WildFly e com versões atuais do PrimeFaces.

## Visão geral da stack

Cada tecnologia aqui tem uma responsabilidade clara:

### JSF

O JSF é a camada web baseada em componentes do lado do servidor.

Ele cuida de:

- receber requisições das páginas `.xhtml`
- ligar os campos da tela com os beans Java
- validar dados
- navegar entre páginas
- renderizar a resposta HTML

No projeto, as telas principais são:

- [index.xhtml](/workspaces/project-java-ee/src/main/webapp/index.xhtml)
- [login.xhtml](/workspaces/project-java-ee/src/main/webapp/login.xhtml)
- [register.xhtml](/workspaces/project-java-ee/src/main/webapp/register.xhtml)
- [home.xhtml](/workspaces/project-java-ee/src/main/webapp/app/home.xhtml)

### PrimeFaces

O PrimeFaces fica em cima do JSF.

Ele fornece componentes visuais prontos, como:

- formulário
- botão
- mensagens
- painel
- campo de senha

Exemplos no projeto:

- `p:inputText`
- `p:password`
- `p:commandButton`
- `p:messages`
- `p:panel`

Isso acelera bastante a construção da interface.

### JPA / Hibernate

O JPA é a especificação de persistência.

Ele define como mapear objetos Java para tabelas do banco.

O Hibernate é o provedor JPA usado pelo WildFly para executar isso na prática.

No projeto:

- a entidade [User.java](/workspaces/project-java-ee/src/main/java/br/com/baseee/model/User.java) representa o usuário
- o arquivo [persistence.xml](/workspaces/project-java-ee/src/main/resources/META-INF/persistence.xml) define a unidade de persistência

Responsabilidade dessa camada:

- salvar usuário no banco
- consultar usuário por e-mail
- contar usuários

### EJB

O EJB foi usado na camada de serviço.

Aqui ele entra principalmente para:

- organizar regra de negócio
- integrar com transação do servidor
- manter a aplicação dentro do modelo tradicional de aplicação corporativa Java

No projeto, isso aparece em:

- [UserService.java](/workspaces/project-java-ee/src/main/java/br/com/baseee/service/UserService.java)

Esse serviço concentra regras como:

- cadastrar usuário
- validar se e-mail já existe
- autenticar login

### CDI

O CDI é a camada de injeção de dependência e gerenciamento de beans.

Ele permite:

- injetar objetos em outros objetos
- controlar escopo de vida dos beans
- separar melhor as responsabilidades

No projeto, isso aparece em beans como:

- [SessionBean.java](/workspaces/project-java-ee/src/main/java/br/com/baseee/web/SessionBean.java)
- [LoginBean.java](/workspaces/project-java-ee/src/main/java/br/com/baseee/web/LoginBean.java)
- [RegisterBean.java](/workspaces/project-java-ee/src/main/java/br/com/baseee/web/RegisterBean.java)
- [DashboardBean.java](/workspaces/project-java-ee/src/main/java/br/com/baseee/web/DashboardBean.java)

Também está habilitado por:

- [beans.xml](/workspaces/project-java-ee/src/main/webapp/WEB-INF/beans.xml)

### WildFly

O WildFly é o servidor de aplicação.

Ele executa a aplicação e entrega serviços de infraestrutura como:

- container web
- JSF
- CDI
- EJB
- JPA/Hibernate
- gerenciamento de datasource
- transações

Neste projeto, o WildFly é responsável por hospedar o WAR e fornecer a maior parte da infraestrutura da stack.

## Estrutura de pastas

A estrutura foi organizada para ficar simples e extensível.

### Código Java

`src/main/java/br/com/baseee`

Aqui está o código-fonte Java dividido em camadas.

#### `model`

Contém as entidades JPA.

Hoje:

- [User.java](/workspaces/project-java-ee/src/main/java/br/com/baseee/model/User.java)

Função:

- representar dados do domínio
- mapear tabelas do banco

#### `service`

Contém regras de negócio e serviços da aplicação.

Hoje:

- [UserService.java](/workspaces/project-java-ee/src/main/java/br/com/baseee/service/UserService.java)
- [PasswordHasher.java](/workspaces/project-java-ee/src/main/java/br/com/baseee/service/PasswordHasher.java)
- [BusinessException.java](/workspaces/project-java-ee/src/main/java/br/com/baseee/service/BusinessException.java)

Função:

- centralizar regras do sistema
- evitar lógica de negócio espalhada nas telas

#### `web`

Contém os beans usados pelas páginas JSF.

Hoje:

- [LoginBean.java](/workspaces/project-java-ee/src/main/java/br/com/baseee/web/LoginBean.java)
- [RegisterBean.java](/workspaces/project-java-ee/src/main/java/br/com/baseee/web/RegisterBean.java)
- [SessionBean.java](/workspaces/project-java-ee/src/main/java/br/com/baseee/web/SessionBean.java)
- [DashboardBean.java](/workspaces/project-java-ee/src/main/java/br/com/baseee/web/DashboardBean.java)

Função:

- receber dados da tela
- chamar serviços
- controlar navegação
- expor dados para exibição

### Configuração

#### `src/main/resources/META-INF`

Arquivos de configuração da persistência.

Hoje:

- [persistence.xml](/workspaces/project-java-ee/src/main/resources/META-INF/persistence.xml)

Esse arquivo define:

- nome da unidade de persistência
- datasource do WildFly
- propriedades do Hibernate

#### `src/main/webapp/WEB-INF`

Arquivos internos da aplicação web.

Hoje:

- [web.xml](/workspaces/project-java-ee/src/main/webapp/WEB-INF/web.xml)
- [beans.xml](/workspaces/project-java-ee/src/main/webapp/WEB-INF/beans.xml)
- [default.xhtml](/workspaces/project-java-ee/src/main/webapp/WEB-INF/templates/default.xhtml)

Função:

- registrar servlet JSF
- configurar CDI
- manter template base das páginas

### Telas

#### `src/main/webapp`

Contém as páginas públicas e recursos web.

Páginas públicas:

- [index.xhtml](/workspaces/project-java-ee/src/main/webapp/index.xhtml)
- [login.xhtml](/workspaces/project-java-ee/src/main/webapp/login.xhtml)
- [register.xhtml](/workspaces/project-java-ee/src/main/webapp/register.xhtml)

#### `src/main/webapp/app`

Contém páginas internas/protegidas.

Hoje:

- [home.xhtml](/workspaces/project-java-ee/src/main/webapp/app/home.xhtml)

Essa separação ajuda porque deixa claro o que é:

- público
- autenticado

#### `src/main/webapp/resources`

Recursos estáticos como CSS, imagens e scripts.

Hoje:

- [app.css](/workspaces/project-java-ee/src/main/webapp/resources/css/app.css)

## Fluxo da aplicação

O fluxo atual é este:

1. usuário acessa `index.xhtml`
2. o sistema decide se redireciona para login ou para área interna
3. se ele não tiver conta, entra em `register.xhtml`
4. o `RegisterBean` recebe os dados da tela
5. o `UserService` valida e grava o usuário
6. a sessão é aberta no `SessionBean`
7. o usuário entra na área interna

No login:

1. usuário envia e-mail e senha
2. `LoginBean` chama `UserService`
3. `UserService` procura o usuário por e-mail
4. a senha informada é comparada com o hash salvo
5. se estiver correta, o `SessionBean` guarda os dados da sessão

## Responsabilidade de cada bean atual

### `LoginBean`

Responsável por:

- capturar dados da tela de login
- chamar autenticação
- enviar mensagem de erro ou sucesso
- redirecionar para a área interna

### `RegisterBean`

Responsável por:

- capturar dados da tela de cadastro
- validar confirmação de senha
- chamar o serviço de cadastro
- abrir sessão após o cadastro

### `SessionBean`

Responsável por:

- guardar dados do usuário logado na sessão
- controlar logout
- proteger páginas
- redirecionar usuário conforme o estado da sessão

### `DashboardBean`

Responsável por:

- expor dados simples da área interna

Hoje ele mostra:

- total de usuários cadastrados

### `UserService`

Responsável por:

- regra de cadastro
- regra de autenticação
- busca por e-mail
- contagem de usuários

## Banco de dados

Para facilitar o início, o projeto usa:

- datasource `java:jboss/datasources/ExampleDS`

Isso é útil para colocar a aplicação no ar rápido no WildFly.

Depois você pode trocar para:

- PostgreSQL
- MySQL
- MariaDB
- outro banco corporativo

Sem mudar a estrutura principal da aplicação.

## Como crescer depois

A ideia é manter o mesmo padrão.

Se você quiser criar `Produto`, por exemplo, o caminho natural é:

1. criar entidade `Produto` em `model`
2. criar `ProdutoService` em `service`
3. criar bean web em `web`
4. criar páginas em `webapp/app`

Exemplo de estrutura futura:

- `model/Product.java`
- `service/ProductService.java`
- `web/ProductBean.java`
- `webapp/app/products.xhtml`

## Resumo prático

Em termos simples:

- JSF/PrimeFaces = tela
- CDI = injeção e escopo dos beans
- EJB = serviço e regra de negócio com suporte do servidor
- JPA/Hibernate = acesso ao banco
- WildFly = servidor que integra tudo

Essa base já está pronta para continuar crescendo sem precisar reorganizar o projeto depois.
