# 🩺 Medical Prescription System

Aplicação web desenvolvida como **teste técnico para desenvolvedor Java EE**, com o objetivo de gerenciar **pacientes, medicamentos e prescrições médicas**.  
O sistema permite **cadastrar, pesquisar e relacionar medicamentos a pacientes**, além de gerar **consultas e relatórios** com dados estatísticos detalhados.

---

## 📁 Tecnologias Utilizadas

- ☕ **Java EE 8** – APIs enterprise  
- 🐘 **PostgreSQL** – Banco de dados relacional  
- 📦 **Hibernate / JPA** – Persistência e ORM  
- 🖥️ **JavaServer Faces (JSF) + PrimeFaces 8.0** – Interface web  
- 🐺 **WildFly 26+** – Servidor de aplicações  
- 🔧 **Maven** – Gerenciamento de dependências e build  
- 🧪 **JUnit 4** – Testes unitários

---

## 📦 Estrutura do Projeto

```bash
medical-prescription-system/
├── src/
│   ├── main/
│   │   ├── java/com/company/clinic/
│   │   │   ├── model/          # Entidades JPA (Paciente, Medicamento, Receita, ReceitaItem)
│   │   │   ├── repository/     # Camada de acesso a dados (Hibernate/JPA)
│   │   │   ├── service/        # Regras de negócio
│   │   │   └── web/bean/       # ManagedBeans JSF (camada de controle)
│   │   ├── resources/
│   │   │   └── META-INF/persistence.xml
│   │   └── webapp/
│   │       ├── pages/          # Páginas JSF (.xhtml)
│   │       └── WEB-INF/
│   │           └── web.xml
└── pom.xml
⚙️ Pré-requisitos
☕ Java 11+

🐘 PostgreSQL 12+

🐺 WildFly 26+

🛠️ Maven 3.6+

🗄️ Configuração do Banco de Dados
Crie o banco de dados:

sql
Copiar código
CREATE DATABASE clinic;
Edite o arquivo persistence.xml com as credenciais do seu banco:

xml
Copiar código
<jta-data-source>java:/PostgresDS</jta-data-source>
<property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQL95Dialect"/>
Crie o datasource no WildFly:

bash
Copiar código
/subsystem=datasources/jdbc-driver=postgresql:add(driver-name=postgresql,driver-module-name=org.postgresql,driver-class-name=org.postgresql.Driver)
data-source add --name=PostgresDS --jndi-name=java:/PostgresDS --driver-name=postgresql --connection-url=jdbc:postgresql://localhost:5432/clinic --user-name=postgres --password=postgres
🚀 Como Rodar o Projeto
Compile e empacote o projeto:

bash
Copiar código
mvn clean package
Copie o .war gerado para a pasta de deploy do WildFly:

bash
Copiar código
cp target/medical-prescription-system.war $WILDFLY_HOME/standalone/deployments/
Acesse no navegador:

bash
Copiar código
http://localhost:8080/medical-prescription-system/
📋 Funcionalidades Implementadas
👤 Pacientes
CRUD completo: cadastrar, editar, excluir e listar

Pesquisa por nome e CPF

Paginação com lazy loading

💊 Medicamentos
CRUD completo: cadastrar, editar, excluir e listar

Pesquisa por nome

Paginação com lazy loading

📜 Prescrições
Associação de um ou mais medicamentos a um paciente

Exibe: idReceita, idPaciente, idMedicamentoReceitado

🔎 Consulta de Medicamentos por Paciente
Pesquisa por paciente e medicamento

Exibe o total de medicamentos por receita

Paginação com lazy loading

📊 Relatórios
Os 2 medicamentos mais prescritos

Os 2 pacientes com mais medicamentos prescritos

Lista geral de pacientes com a quantidade total de medicamentos prescritos

📁 Estrutura do Banco de Dados
paciente – armazena informações dos pacientes

medicamento – armazena os medicamentos disponíveis

receita – representa a prescrição associada ao paciente

receita_item – relaciona receita com medicamentos prescritos

🌐 URLs Principais da Aplicação
Tela	Caminho
Lista de pacientes	/pages/paciente/list.xhtml
Lista de medicamentos	/pages/medicamento/list.xhtml
Prescrições	/pages/receita/list.xhtml
Nova prescrição	/pages/receita/form.xhtml
Consulta de medicamentos por paciente	/pages/consulta/index.xhtml
Relatórios	/pages/relatorios/dashboards.xhtml

🧪 Testes Unitários
Execute os testes com:

bash
Copiar código
mvn test
Inclui testes de:

Camada de serviço (Service)

Regras de negócio

Camada de persistência (Repository)

📤 Entrega Final
Para envio do projeto:

✅ Código fonte completo

✅ Arquivo .war gerado

✅ Dump do banco PostgreSQL (dump.sql)

✅ Este arquivo README.md atualizado

📌 Observações
A aplicação está pronta para rodar no WildFly com PostgreSQL.

Todas as funcionalidades descritas no desafio técnico foram implementadas e testadas.

O sistema segue boas práticas de arquitetura em camadas e uso de tecnologias padrão do ecossistema Java EE.

📍 Desenvolvido como parte de um teste técnico para desenvolvedor Java EE – demonstrando habilidades com back-end Java, front-end JSF, persistência com Hibernate, integração com PostgreSQL e implantação em WildFly.



# Telas do teste
<img width="1505" height="519" alt="image" src="https://github.com/user-attachments/assets/7d047deb-f7d9-4ca0-8ef1-1c5a7f119bc2" />
<img width="1129" height="366" alt="image" src="https://github.com/user-attachments/assets/6a13d7d2-a5d6-4f97-9fe7-8e6795f3beca" />
<img width="1110" height="298" alt="image" src="https://github.com/user-attachments/assets/ce80add6-cf59-423e-8d41-673f37a2bf83" />
<img width="1161" height="686" alt="image" src="https://github.com/user-attachments/assets/fa241b52-2ff2-436b-9f11-86dab026c0c6" />
<img width="1092" height="366" alt="image" src="https://github.com/user-attachments/assets/c3d330a3-a90b-4da8-a0f6-7c82e43294fe" />
<img width="1243" height="684" alt="image" src="https://github.com/user-attachments/assets/a8917d76-c807-4a85-9980-65dc62010691" />
<img width="1107" height="444" alt="image" src="https://github.com/user-attachments/assets/01372cc7-b822-4614-a73f-dc0a1048cab7" />
<img width="1166" height="650" alt="image" src="https://github.com/user-attachments/assets/ed7de1b5-fe6b-464d-bbaa-3e0f78ae33d4" />
<img width="1162" height="423" alt="image" src="https://github.com/user-attachments/assets/998e4aaa-a999-4061-83ae-e1ba87a3c625" />
<img width="1076" height="664" alt="image" src="https://github.com/user-attachments/assets/5989c423-40d0-4ac2-ada6-b337ac9ba50c" />












