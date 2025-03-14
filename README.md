<h1 align="center" style="font-weight: bold;">Desafio Back-End do Itaú Unibanco 🧩</h1>

<p align="center">
  <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" alt="Spring">
  <img src="https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white" alt="Postgres">
  <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white" alt="Docker">
  <img src="https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white" alt="Swagger">
</p>

<p align="center">
 <a href="#estrutura">Estrutura do projeto</a> • 
 <a href="#inicio">Primeiros Passos</a> • 
 <a href="#rotas">Endpoints da API</a> •
 <a href="#extras">Extras</a> •
 <a href="#contribuir">Contribuir</a>
</p>

<p align="center">
  <b>Esta é a minha resolução para o <a href=https://github.com/feltex/desafio-itau-backend>Desafio Back-End</a> do Itaú Unibanco. A API foi desenvolvida seguindo os princípios da <a href=https://medium.com/@marcio.kgr/arquitetura-hexagonal-8958fb3e5507>Arquitetura Hexagonal</a> e utilizando um banco de dados Postgres.</b>
</p>

<h2 id="estrutura">📂 Estrutura do projeto</h2>

```yaml
src/main/java/br/com/gabrieudev/itau/
├── application # Camada de aplicação
│   ├── exception # Exceções
│   ├── ports # Portas
│   │   ├── input # Portas de entrada
│   │   └── output # Portas de saída
│   └── service # Serviços
├── domain # Camada de domínio
├── adapters # Camada de adaptadores
│   ├── input # Adaptadores de entrada
│   │   └── rest # Interação web
│   │       ├── controllers # Controladores
│   │       └── dtos # DTOs
│   └── output # Adaptadores de saída
│       └── persistence # Banco de dados
│           ├── entities # Entidades JPA
│           └── repositories # Repositórios (JPA e adaptadores)
├── config # Configurações do Spring
└── ItauApplication.java # Inicializador da aplicação
```

<h2 id="inicio">🚀 Primeiros Passos</h2>

<h3>Pré-requisitos</h3>

- [Docker](https://www.docker.com/get-started/)
- [Git](https://git-scm.com/downloads)

<h3>Clonando</h3>

```bash
git clone https://github.com/gabrieudev/itau.git
```

<h3>Inicializando</h3>

Execute os seguintes comandos:

```bash
cd itau
docker compose up -d --build
```

<h2 id="rotas">📍 Endpoints da API</h2>

Agora você poderá testar a aplicação através da interface Swagger em [http://localhost:8080/api/v1/swagger-ui/index.html](http://localhost:8080/api/v1/swagger-ui/index.html).

<h2 id="extras">➕ Extras</h2>

O projeto cumpriu os seguintes requisitos extras:

1. **Testes automatizados:** Foram realizados testes unitários na camada de serviço com 100% de cobertura de código.
2. **Containerização:** A aplicação está totalmente containerizada, incluindo Dockerfile e docker compose com todos os serviços necessários.
3. **Logs:** Logs de informação e erro foram adicionados nas camadas de controladores (requisições HTTP) e serviços (lógica de negócio).
4. **Observabilidade:** É possível obter o status da aplicação através da URL [http://localhost:8080/api/v1/actuator/health](http://localhost:8080/api/v1/actuator/health).
5. **Performance:** A aplicação gasta, em média, 16ms para o cálculo das estatísticas.
6. **Tratamento de Erros:** As exceções são tratadas através de um Exception Handler global, que os converte para uma classe de resposta genérica contendo informações como status, mensagem e timestamp.
7. **Documentação da API:** A API utiliza o Swagger UI como interface para documentação.
8. **Documentação do Sistema:** Além de criar uma imagem, com o Dockerfile também é possível interpretar um passo a passo de como executar a aplicação sem utilizar Docker, incluindo suas dependências e comandos para execução.
9. **Configurações:** É possível definir a quantidade de segundos para o cálculo das estatísticas através do parâmetro `segundos` no endpoint `/estatistica`

<h2 id="contribuir">📫 Contribuir</h2>

Contribuições são muito bem vindas! Caso queira contribuir, faça um fork do repositório e crie um pull request.

1. `git clone https://github.com/gabrieudev/itau.git`
2. `git checkout -b feature/NOME`
3. Siga os padrões de commits.
4. Abra um Pull Request explicando o problema resolvido ou a funcionalidade desenvolvida. Se houver, anexe screenshots das modificações visuais e aguarde a revisão!
