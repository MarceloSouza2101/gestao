### Aplicação de Gestão de Produtos

Este repositório contém uma aplicação de gestão de produtos desenvolvida em Java com Spring Boot e SQL Server.

#### Preparação de ambiente
Segue o passo a passo necessário para preparação do ambiente de desenvolvimento, abordando/referenciando os procedimentos necessários para instalação e configuração das ferramentas adotadas pelo time técnico.

**Docker**

Para configurar o Docker em seu ambiente, siga as instruções abaixo:

1. **Instalação**
   - Instale o Docker e o Docker Compose correspondente à sua distribuição, seguindo as instruções descritas no site oficial do [Docker](https://www.docker.com/).

2. **Execução como Usuário Não Privilegiado**
   - Assegure-se de que o Docker seja executado com usuário não privilegiado, conforme explicado [aqui](https://docs.docker.com/engine/install/linux-postinstall/).

3. **Verificação de Versões**
   - Confira as versões do Docker e do Docker Compose executando o seguinte comando no terminal:
     ```bash
     docker version && docker-compose version
     ```

4. **Teste da Instalação**
   - Inicie um contêiner sem privilégios de root para verificar se a instalação está correta.

5. **Imagem Banco**
   - Execute o seguinte comando para baixar a imagem oficial do SQL Server da Microsoft:
     ```bash
     docker pull mcr.microsoft.com/mssql/server:latest
     docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=<admin123>" -p 1433:1433 --name meu-sqlserver -d mcr.microsoft.com/mssql/server:latest
     ```

**Java**

Para configurar o Java em seu ambiente, siga as instruções abaixo:

- **Ambiente Linux**
  - **JDK 17**
    1. Caso não exista, crie a pasta `/opt/dev/java`.
    2. Faça o download do JDK 17 utilizado nos projetos clicando [aqui](https://drive.google.com/drive/u/1/folders/1J1tn8zp2_pz2GJeDvkThR9_8e3DaOXNJ).
    3. Descompacte o arquivo baixado na pasta `/opt/dev/java`, resultando em `/opt/dev/java/jdk-17.0.2`.
    4. Confirme a versão executando o comando:
       ```bash
       /opt/dev/java/jdk-17.0.2/bin/java --version
       ```

**Maven**

Para configurar o Maven em seu ambiente, siga as instruções abaixo:

- **Ambiente Linux**
  1. Caso não exista, crie o diretório `/opt/dev/maven`.
  2. Baixe o Maven customizado clicando [aqui](https://drive.google.com/drive/u/1/folders/1J1tn8zp2_pz2GJeDvkThR9_8e3DaOXNJ).
  3. Descompacte o arquivo baixado no diretório `/opt/dev/maven`, resultando em: `/opt/dev/maven/apache-maven-3.8.4`.
  4. Confirme a versão executando o comando:
     ```bash
     /opt/dev/maven/apache-maven-3.8.4/bin/mvn --version
     ```

**Spring Tool Suite (STS)**

Para baixar a IDE Spring Tool Suite customizada em seu ambiente de desenvolvimento, siga as instruções abaixo:

- **Ambiente Linux**
  1. Crie o diretório `/opt/dev/tools` se ainda não existir.
  2. Baixe a IDE customizada clicando [aqui](https://drive.google.com/drive/u/1/folders/1J1tn8zp2_pz2GJeDvkThR9_8e3DaOXNJ).
  3. Descompacte o arquivo baixado no diretório `/opt/dev/tools`, resultando em: `/opt/dev/tools/sts`.
  4. Em seguida, baixe o Workspace configurado para uso em conjunto com a IDE:
     - Caso não exista, crie o diretório `/opt/dev/workspaces`.
     - Faça o download do Workspace customizado clicando [aqui](https://drive.google.com/drive/u/1/folders/1J1tn8zp2_pz2GJeDvkThR9_8e3DaOXNJ).
     - Extraia o conteúdo do arquivo baixado em `/opt/dev/workspaces`, ficando assim: `/opt/dev/workspaces/gestao`.

**DBeaver**

O DBeaver é uma ferramenta usada para executar instruções SQL nos bancos de dados. Para utilizá-lo em seu ambiente, siga as etapas abaixo:

- **Ambiente Linux**
  1. Crie o diretório `/opt/dev/tools`, se ainda não existir.
  2. Baixe a versão customizada do DBeaver clicando [aqui](https://drive.google.com/drive/u/1/folders/1J1tn8zp2_pz2GJeDvkThR9_8e3DaOXNJ).
  3. Descompacte o arquivo baixado no diretório `/opt/dev/tools`, resultando em: `/opt/dev/tools/dbeaver`.
  4. Em seguida, faça o download do Workspace configurado com as conexões usadas nas aplicações em desenvolvimento:
     - Caso o diretório `/opt/dev/workspaces` não exista, crie-o.
     - Baixe o Workspace customizado clicando [aqui](https://drive.google.com/drive/u/1/folders/1J1tn8zp2_pz2GJeDvkThR9_8e3DaOXNJ).
     - Extraia o conteúdo do arquivo baixado em `/opt/dev/workspaces`, resultando em: `/opt/dev/workspaces/dbeaver-gestao`.

Com essas configurações, seu ambiente estará pronto para executar a aplicação de gestão de produtos.