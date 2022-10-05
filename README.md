# SGP - Sistema de Gerenciamento de Pedidos

### Membros e papéis:

- Fernando Eduardo Pinto Moreira - desenvolvedor front-end;
- Gilliard Gabriel Rodrigues - desenvolvedor back-end;
- Maíla Ferreira Silva - desenvolvedora front-end;
- Samuel William Almeida Santos - desenvolvedor back-end.

### Funcional (objetivo do sistema, principais features, etc)
**Objetivo:** 
Trata-se de um sistema que permite o gerenciamento de pedidos para o DACompSI, sejam eles de camisas, canecas e/ou tirantes. Podendo futuramente ser expandido com a chegada de novos produtos.


**Principais features:**

Os administradores têm uma área privada que exige autenticação via login e senha, onde conseguem:

- Gerenciar os pedidos, filtrando por tamanho, cor, curso, data, status de pagamento e situação de entrega;

- Abrir e fechar a temporada de pedidos com itens específicos;

- Criar um inventário com itens à pronta-entrega e cadastrar fornecedores.

Os clientes conseguem:

- Fazer pedidos, consultar status de pedidos através de seus códigos e também ver os itens disponíveis à pronta-entrega.



### Tecnologias 
- Front-end
	- HTML
	- CSS
	- Javascript
	- Bootstrap
- Back-end
	- Java
	- Spring
	- Hibernate
- Banco de dados
	- PostgreSQL
- Deploy
  - AWS ou Heroku

### Product Backlog

- Como administrador, eu gostaria de poder iniciar temporadas de recebimento de pedidos, informando os itens que estarão à venda.

- Como administrador, eu gostaria de dar baixa de pagamento parcial ou total de pedidos

- Como administrador, eu gostaria de poder filtrar os itens dos pedidos por suas características, como tamanho, cor, curso, data, status de pagamento e situação de entrega, para fins de conferência.

- Como administrador, eu gostaria de cadastrar fornecedores com observações específicas para cada um.

- Como administrador, eu gostaria de cadastrar produtos do inventário.

- Como usuário, eu gostaria de poder consultar o inventário de produtos disponíveis à pronta-entrega.

- Como usuário, eu gostaria de acompanhar a situação do meu pedido, tendo transparência sobre a situação de pagamento e previsão de retirada.

- Como administrador, eu gostaria de poder fechar temporadas de recebimento de pedidos, gerando aviso aos clientes de que o pedido já foi repassado para a produção.

- Como administrador, eu gostaria de gerar relatórios de forma agrupada para os fornecedores a respeito dos pedidos após fechar uma temporada.

- Como administrador, eu gostaria de poder notificar os clientes a respeito do tempo de entrega previsto de cada produto de acordo com o tempo do fornecedor e também quando algum produto chegasse.

### Sprint Backlog

**História:** Como administrador, eu gostaria de poder iniciar temporadas de recebimento de pedidos, informando os itens que estarão à venda.

- [x] Projetar tela de gerenciamento de temporada no Figma e validar design. [Maíla]

- [ ] Implementar interface web. [Fernando]

- [ ] Criar e implementar rota para pesquisar temporadas existentes. [Gilliard]

- [ ] Criar e implementar rota para abrir uma nova temporada de pedidos. [Gilliard]

- [ ] Criar e implementar rota para editar informações da temporada. [Gilliard]

- [ ] Criar e implementar rota para excluir uma temporada. [Gilliard]

- [ ] Implementar regras de negócio de encerramento de temporada e contabilização de pedidos. [Gilliard]


**História:** Como administrador, eu gostaria de dar baixa de pagamento parcial ou total de pedidos

- [x] Projetar tela de gerenciamento de pedidos no Figma e validar design. [Fernando]

- [ ] Implementar interface web. [Maíla]

- [ ] Criar e implementar rota para pesquisar pedidos existentes. [Gilliard]

- [ ] Criar e implementar rota para editar pedidos. [Gilliard]

- [ ] Implementar regras de negócio relativas às edições de pedidos e seus reflexos. [Gilliard]

- [ ] Criar e implementar rota para remover pedidos. [Gilliard]


**História:** Como administrador, eu gostaria de poder filtrar os itens dos pedidos por suas características, como tamanho, cor, curso, data, status de pagamento e situação de entrega, para fins de conferência.

- [ ] Implementar filtros de busca na tabela de pedidos no back-end. [Gilliard]

- [ ] Implementar esses filtros no front-end. [Fernando]

**História:** Como administrador, eu gostaria de cadastrar fornecedores com observações específicas para cada um.

- [x]  Projetar tela de cadastro e edição de fornecedores no Figma e validar design. [Maíla, Fernando]

- [ ] Implementar interface web. [Fernando]

- [x] Implementar CRUD de fornecedores. [Gilliard]

- [x] Implementar lógica das observações e histórico com fornecedores. [Gilliard]


**História:** Como administrador, eu gostaria de cadastrar produtos do inventário.

- [x] Projetar tela com visão administrativa de produtos no Figma e validar design. [Fernando]

- [ ] Implementar interface web. [Maíla]

- [x] Criar e implementar rota para popular o inventário. [Samuel]

- [ ] Criar e implementar rota para editar produtos. [Samuel]

- [ ] Criar e implementar rota para remover produtos. [Samuel]


**História:** Como usuário, eu gostaria de poder consultar o inventário de produtos disponíveis à pronta-entrega.

- [x] Projetar tela de consulta do usuário no Figma e validar design. [Maíla, Fernando]

- [ ] Implementar interface web. [Fernando]

- [ ] Criar e implementar rota para mostrar apenas produtos disponíveis à pronta-entrega. [Samuel]

**História:** Como usuário, eu gostaria de poder realizar um pedido

- [x] Projetar telas relativas ao processo de abertura de pedido no Figma e validar design. [Maíla, Fernando]

- [ ] Implementar interface web. [Fernando]

- [ ] Criar e implementar rota para criar pedido [Samuel]


**História:** Como usuário, eu gostaria de acompanhar a situação do meu pedido, tendo transparência sobre a situação de pagamento e previsão de retirada.

- [x] Projetar tela de acompanhamento de pedidos e validar design. [Maíla]

- [ ] Implementar interface web. [Fernando]

- [ ] Criar e implementar rota para buscar pedido [Samuel]

- [ ] Implementar lógica de autenticação do código do pedido. [Samuel]


**História:** Como administrador, eu gostaria de poder fechar temporadas de recebimento de pedidos, gerando aviso aos clientes de que o pedido já foi repassado para a produção.

- [x] Projetar tela de acompanhamento de pedidos da temporada. [Maíla]

- [ ] Implementar interface web. [Fernando]

- [ ] Criar e testar rota para atualizar todos os pedidos pagos da temporada para serem considerados encomendados e impedir novos pedidos na temporada [Samuel]


**História:** Como administrador, eu gostaria de notificar os clientes a respeito do tempo de entrega específico de cada tipo de produto, de acordo com o tempo do fornecedor.

- [ ] Projetar tela de acompanhamento de pedidos da temporada. [Fernando]

- [ ] Implementar interface web. [Maíla]

- [ ] Criar e testar rota para definir o prazo de entrega dos pedidos de uma temporada. [Samuel]


**Tarefas técnicas**

- [x] Preparar estrutura de arquivos para arquitetura hexagonal. [Gilliard]

- [x] Discutir e definir diagrama das classes de domínio. [Gilliard]

- [x] Criar banco de dados no PostgreSQL. [Gilliard]

- [ ] Implementar camada de persistência.  [Gilliard]

- [x] Implementar esqueleto da arquitetura hexagonal. [Gilliard]

- [ ] Implementar autenticação e autorização de usuários. [Gilliard]

- [x] Projetar telas de login e tela principal do administrador no Figma e validar design. [Fernando]

- [ ] Implementar telas de login, cadastro e tela principal do administrador. [Maíla]


### Diagrama de Classes
![Diagrama de Classes](diagrama_de_classes.png)
