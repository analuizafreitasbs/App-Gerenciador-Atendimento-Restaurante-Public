# 🍽️ App Gerenciador de Atendimento de Restaurante

Bem-vindo ao nosso sistema inteligente de **gerenciamento de atendimentos em restaurantes**, criado como projeto final para a disciplina de **Estrutura de Dados** no IFBA – Campus Vitória da Conquista. Este sistema busca organizar o fluxo de trabalho dos garçons e elevar a experiência do cliente a um novo nível de eficiência!

---

## 🎯 Objetivo

Simplificar o atendimento em restaurantes por meio de um sistema que:
- 📋 Gerencia garçons, pedidos e clientes (individuais ou em grupo)
- ⏳ Controla tempo de espera e status do atendimento
- ⚙️ Utiliza **estruturas de dados clássicas** como **Filas** e **Listas**
- ⭐ Dá prioridade a **clientes VIPs ou prioritários**
- 💾 Persiste dados usando **JSON + Gson**

---

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **JavaFX** + FXML para interface gráfica
- **Gson** para persistência
- **Maven** para gerenciamento do projeto
- **JUnit 5** para testes unitários

---

## 🧠 Estruturas de Dados Implementadas

| Estrutura         | Uso no Sistema |
|-------------------|----------------|
| **Lista**         | Armazenamento de pedidos, itens e preferências |
| **Fila (FIFO)**   | Gerenciamento da ordem de atendimento |
| **Fila com Prioridade** | Reorganização automática para clientes VIP/Prioritários |
| **Generics**      | Fila de atendimento segura e reutilizável |
| **Interface `Atendivel`** | Tratamento polimórfico de clientes e grupos |

---

## 📸 Interface do Sistema

> Interface amigável para garçons! Algumas telas:

- Tela de **Login do Garçom**
- Menu principal com gerenciamento de pedidos
- Visualização de fila geral e **fila própria do garçom**
- Cadastro de **clientes e grupos**
- Adição de pedidos com observações como “sem sal” 🍕❌🧂

---

## 🔁 Fluxo de Atendimento

1. Cliente entra na fila geral
2. Garçom o transfere para sua fila pessoal (individual ou grupo)
3. Registra-se o pedido e gerencia-se o atendimento por **status**
4. Após o pagamento, o atendimento é **finalizado** e historizado

---

## 🚀 Como Executar (é neceessário ter o JDK 21+ e Maven instalados) 

```bash
# Clone o projeto
git clone https://github.com/Bruno08004/App-Gerenciador-Atendimento-Restaurante.git

# Acesse a pasta
cd App-Gerenciador-Atendimento-Restaurante

# Compile o projeto (via Maven)
mvn clean install

# Execute o sistema
mvn javafx:run
```

- Pré-requisitos: JDK 21+, Maven e JavaFX instalados/configurados.

### 🧪 Testes
- Rodamos testes unitários com JUnit 5, garantindo robustez em:

- Validação de login

- Cálculo de total do pedido

- Organização da fila e finalização do atendimento

--- 

### 📚 Créditos
Trabalho desenvolvido por:

Ana Luiza Freitas B. Siqueira

Bruno Campos Penha

Grazielly de Sousa Barros

João Gabriel O. Magalhães

João Vitor M. Lemos

Robert Alves Guimarães

Vinicius D. Oliveira Rocha

---
### Professor orientador: Cláudio Rodolfo S. de Oliveira
---
### 🧠 Aprendizados
Este projeto nos permitiu aplicar conceitos fundamentais de Estruturas de Dados em um contexto prático e real, utilizando boas práticas de desenvolvimento com foco em modularidade, reuso e clareza de código. Além disso, aprendemos muito sobre design de sistemas com interface gráfica e persistência de dados!


