# Product Service - E-commerce

Este é o serviço responsável pelo gerenciamento de produtos do e-commerce. O projeto foi estruturado seguindo os princípios de Clean Architecture e Clean Code.

## Pré-requisitos

Antes de começar, certifique-se de ter instalado:
- Docker (20.10.x ou superior)
- Docker Compose (2.x ou superior)
- Java 21 (para desenvolvimento local)
- Maven 3.8.x (para desenvolvimento local)

## Estrutura do Projeto

O projeto está organizado nas seguintes camadas:

```
src/main/java/br/com/ecommerce/product/
├── application/
│   ├── dto/           # Data Transfer Objects
│   ├── usecases/      # Casos de uso da aplicação
│   └── ports/         # Interfaces/Contratos entre camadas
│
├── domain/
│   ├── entities/      # Entidades de domínio
│   ├── valueobjects/  # Objetos de valor imutáveis
│   └── exceptions/    # Exceções específicas do domínio
│
├── infrastructure/
│   ├── config/        # Configurações do Spring e aplicação
│   ├── persistence/   # Implementações de repositórios
│   └── messaging/     # Implementações de mensageria
│
├── presentation/
│   ├── controllers/   # Controllers da API
│   ├── responses/     # Classes de resposta da API
│   ├── requests/      # Classes de requisição da API
│   └── mappers/       # Conversores entre DTOs e objetos de domínio
│
├── interfaces/
│   ├── rest/          # Definições de endpoints e rotas
│   ├── events/        # Handlers de eventos
│   └── handlers/      # Handlers genéricos e middlewares
│
└── shared/
    ├── utils/         # Utilitários compartilhados
    └── validation/    # Validações comuns
```

### Descrição das Camadas

#### Domain (Camada de Domínio)
- Contém as regras de negócio centrais
- Independente de frameworks e bibliotecas externas
- Define as entidades principais e regras de negócio
- Não possui dependências com outras camadas

#### Application (Camada de Aplicação)
- Implementa os casos de uso do sistema
- Orquestra o fluxo de dados entre as entidades
- Define interfaces (ports) para comunicação com o mundo exterior
- Contém DTOs para transferência de dados entre camadas

#### Infrastructure (Camada de Infraestrutura)
- Implementa a persistência de dados
- Contém configurações do framework
- Implementa a comunicação com serviços externos
- Fornece implementações concretas para as interfaces definidas na camada de aplicação

#### Presentation (Camada de Apresentação)
- Responsável pela apresentação dos dados ao cliente
- Contém os controllers que recebem as requisições HTTP
- Define os formatos de request/response da API
- Realiza validações básicas de entrada
- Trata a serialização/deserialização dos dados
- Possui mappers para converter DTOs em objetos de domínio e vice-versa

#### Interfaces (Camada de Interface)
- Define os contratos da API REST
- Gerencia os eventos do sistema
- Implementa handlers de eventos
- Gerencia aspectos de segurança da API

#### Shared (Camada Compartilhada)
- Contém código utilitário usado por múltiplas camadas
- Implementa validações comuns
- Define exceções e tipos compartilhados

## Princípios Arquiteturais

Este projeto segue os princípios da Clean Architecture:
1. Independência de Frameworks
2. Testabilidade
3. Independência de UI
4. Independência de Banco de Dados
5. Independência de Agentes Externos

As dependências fluem de fora para dentro, com o domínio no centro, livre de dependências externas.

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Data JPA
- Maven

## Como Executar

### Usando Docker Compose (Recomendado)

1. **Clone o repositório**
   ```bash
   git clone <url-do-repositorio>
   cd product-service
   ```

2. **Construa e inicie os containers**
   ```bash
   # Construir as imagens
   docker-compose build

   # Iniciar os serviços em background
   docker-compose up -d
   ```

3. **Verifique o status dos serviços**
   ```bash
   docker-compose ps
   ```

4. **Acesse os serviços**
   - API: http://localhost:8080
   - MongoDB Express (Interface Admin): http://localhost:8081
     - Usuário: custormer-auth
     - Senha: MongoExpress2025!
   - MongoDB: localhost:27017
     - Usuário Root: root
     - Senha Root: MongoDB2025!

5. **Visualize os logs**
   ```bash
   # Todos os serviços
   docker-compose logs -f

   # Serviço específico
   docker-compose logs -f app
   docker-compose logs -f mongo
   docker-compose logs -f mongo-express
   ```

6. **Parar os serviços**
   ```bash
   # Parar mantendo os volumes
   docker-compose down

   # Parar e remover volumes
   docker-compose down -v
   ```

### Monitoramento e Manutenção

1. **Verificar saúde dos serviços**
   ```bash
   # Status da API
   curl http://localhost:8080/actuator/health

   # Monitorar recursos
   docker stats
   ```

2. **Reiniciar serviços**
   ```bash
   # Reiniciar um serviço específico
   docker-compose restart app
   
   # Reiniciar todos os serviços
   docker-compose restart
   ```

3. **Limpar ambiente**
   ```bash
   # Remover todos os containers e redes
   docker-compose down

   # Remover tudo, incluindo volumes
   docker-compose down -v --rmi all
   ```

### Desenvolvimento Local

Para desenvolvimento local sem Docker:

1. **Instale o MongoDB localmente ou use um serviço remoto**

2. **Configure o application.yml**
   ```yaml
   spring:
     data:
       mongodb:
         uri: mongodb://localhost:27017/product-service
   ```

3. **Execute a aplicação**
   ```bash
   ./mvnw spring-boot:run
   ```

## Endpoints da API

### Criar Cliente
```http
POST /api/products
Content-Type: application/json

{
  "name": "João Silva",
  "email": "joao@email.com",
  "phone": "11999999999",
  "addresses": [
    {
      "street": "Rua Exemplo",
      "number": "123",
      "complement": "Apto 45",
      "neighborhood": "Centro",
      "city": "São Paulo",
      "state": "SP",
      "zipCode": "01234-567"
    }
  ]
}
```

## Troubleshooting

1. **Erro de conexão com MongoDB**
   - Verifique se o container do MongoDB está rodando: `docker-compose ps`
   - Verifique os logs: `docker-compose logs mongo`
   - Confirme as credenciais no application.yml

2. **Aplicação não inicia**
   - Verifique os logs: `docker-compose logs app`
   - Confirme se o MongoDB está acessível
   - Verifique as configurações de memória no docker-compose.yml

3. **Mongo Express não acessível**
   - Confirme se o container está rodando: `docker-compose ps`
   - Verifique os logs: `docker-compose logs mongo-express`
   - Tente reiniciar o serviço: `docker-compose restart mongo-express`

## Como Contribuir

[Instruções de contribuição serão adicionadas]

### Mappers

O projeto utiliza mappers para realizar a conversão entre DTOs (Data Transfer Objects) e objetos de domínio. Esta abordagem segue os princípios do Clean Architecture, mantendo a separação de responsabilidades e facilitando a manutenção do código.

#### Estrutura dos Mappers
```java
// productMapper
public class productMapper {
    public static product toDomain(CreateproductRequest request) {
        // Converte CreateproductRequest para product
    }
}

// AddressMapper
public class AddressMapper {
    public static Address toDomain(AddressRequest request) {
        // Converte AddressRequest para Address
    }
}
```

#### Benefícios dos Mappers
- Separação clara entre objetos de transporte de dados (DTOs) e objetos de domínio
- Centralização da lógica de conversão
- Facilidade de manutenção e teste
- Redução de acoplamento entre camadas
- Melhor organização do código

## Deploy no Amazon EKS

### Pré-requisitos para Deploy

1. **Ferramentas Necessárias**:
   - AWS CLI configurado
   - kubectl instalado
   - helm (opcional, para instalação de componentes adicionais)

2. **Configurações na AWS**:
   - Cluster EKS criado
   - ECR Repository criado
   - IAM Role com permissões adequadas
   - VPC e Subnets configuradas

3. **Secrets do GitHub necessários**:
   ```yaml
   AWS_REGION: "sua-região"
   AWS_ROLE_ARN: "arn:aws:iam::123456789012:role/seu-role"
   ECR_REPOSITORY: "nome-do-seu-repositorio"
   EKS_CLUSTER_NAME: "nome-do-seu-cluster"
   KUBE_CONFIG_DATA: "config-base64-encoded"
   SLACK_WEBHOOK_URL: "url-do-webhook-slack"
   ```

### Estrutura Kubernetes

O projeto utiliza uma estrutura Kubernetes organizada para gerenciar diferentes ambientes:

```
K8S/
├── namespace.yaml          # Definição dos namespaces
├── configmap.yaml         # Configurações da aplicação
├── secret.yaml           # Secrets da aplicação
├── deployment.yaml       # Deployment da aplicação
├── service.yaml         # Serviço para exposição
├── ingress.yaml        # Ingress para roteamento
├── DB/
│   └── mongodb.yaml   # StatefulSet do MongoDB
└── Metrics/
    ├── prometheus.yaml        # Configuração do Prometheus
    └── grafana-dashboard.yaml # Dashboard Grafana
```

### Ambientes

O projeto suporta dois ambientes:

1. **Development**:
   - 2 réplicas da aplicação
   - MongoDB single node
   - Recursos:
     - Memory: 512Mi-1Gi
     - CPU: 250m-500m
   - URL: api.product.dev.seudominio.com

2. **Production**:
   - 3 réplicas da aplicação
   - MongoDB cluster com 3 réplicas
   - Recursos:
     - Memory: 1Gi-2Gi
     - CPU: 500m-1000m
   - URL: api.product.prod.seudominio.com
   - SSL/TLS habilitado

### Pipeline de Deploy (GitHub Actions)

O processo de deploy é automatizado através do GitHub Actions e inclui:

1. **Testes Unitários**:
   ```yaml
   - Execução de testes
   - Upload de resultados
   ```

2. **Análise de Segurança**:
   ```yaml
   - OWASP Dependency Check
   - Upload de relatório
   ```

3. **Build e Push**:
   ```yaml
   - Build do projeto
   - Build da imagem Docker
   - Push para Amazon ECR
   ```

4. **Deploy**:
   - Development (branch develop)
   - Production (branch main)

### Como Realizar o Deploy

1. **Configuração Inicial**:
   ```bash
   # Configurar AWS CLI
   aws configure

   # Configurar kubectl
   aws eks update-kubeconfig --name seu-cluster-name --region sua-regiao
   ```

2. **Preparar Ambiente**:
   ```bash
   # Criar namespaces
   kubectl apply -f K8S/namespace.yaml

   # Criar secrets necessários
   kubectl create secret generic mongodb-secret \
     --from-literal=root-username=root \
     --from-literal=root-password=sua-senha \
     -n development

   kubectl create secret generic mongodb-secret \
     --from-literal=root-username=root \
     --from-literal=root-password=sua-senha \
     -n production
   ```

3. **Deploy da Infraestrutura**:
   ```bash
   # Deploy do MongoDB
   kubectl apply -f K8S/DB/

   # Verificar status
   kubectl get pods -n development
   kubectl get pods -n production
   ```

4. **Deploy da Aplicação**:
   ```bash
   # Aplicar configurações
   kubectl apply -f K8S/configmap.yaml
   kubectl apply -f K8S/secret.yaml

   # Deploy da aplicação
   kubectl apply -f K8S/deployment.yaml
   kubectl apply -f K8S/service.yaml
   kubectl apply -f K8S/ingress.yaml
   ```

5. **Configurar Monitoramento**:
   ```bash
   # Deploy do Prometheus ServiceMonitor
   kubectl apply -f K8S/Metrics/prometheus.yaml

   # Deploy do Dashboard Grafana
   kubectl apply -f K8S/Metrics/grafana-dashboard.yaml
   ```

### Monitoramento

1. **Métricas Disponíveis**:
   - JVM metrics
   - HTTP requests
   - MongoDB metrics
   - Custom business metrics

2. **Dashboards**:
   - Request Rate
   - Latência
   - Erros
   - Recursos do Sistema

3. **Alertas**:
   - High CPU/Memory usage
   - High error rate
   - Slow response time
   - MongoDB issues

### Troubleshooting

1. **Problemas Comuns**:
   ```bash
   # Verificar logs da aplicação
   kubectl logs -f deployment/product-service -n development
   
   # Verificar status dos pods
   kubectl get pods -n development
   
   # Verificar eventos do cluster
   kubectl get events -n development
   ```

2. **Problemas de Conexão**:
   ```bash
   # Testar conectividade com MongoDB
   kubectl exec -it product-service-pod -n development -- nc -zv mongodb 27017
   
   # Verificar logs do MongoDB
   kubectl logs -f statefulset/mongodb -n development
   ```

3. **Problemas de Recursos**:
   ```bash
   # Verificar uso de recursos
   kubectl top pods -n development
   kubectl describe pod product-service-pod -n development
   ```

### Rollback

Em caso de problemas após deploy:

```bash
# Verificar histórico de rollout
kubectl rollout history deployment/product-service -n production

# Voltar para versão anterior
kubectl rollout undo deployment/product-service -n production

# Voltar para versão específica
kubectl rollout undo deployment/product-service -n production --to-revision=2
```

### Segurança

1. **Network Policies**:
   - Acesso restrito entre namespaces
   - Comunicação MongoDB limitada
   - Ingress controlado

2. **Secrets Management**:
   - Valores sensíveis em Secrets
   - Rotação automática de credenciais
   - Encryption at rest

3. **Compliance**:
   - Logging habilitado
   - Audit trail
   - Resource quotas

## Tratamento de Erros

O projeto implementa um sistema robusto de tratamento de erros seguindo os princípios do Clean Architecture e Clean Code.

### Estrutura de Exceções

```
src/main/java/br/matosit/product_service/
├── domain/
│   └── exceptions/
│       ├── DomainException.java             # Exceção base do domínio
│       └── productAlreadyExistsException.java  # Exceção específica
├── presentation/
│   ├── handlers/
│   │   └── GlobalExceptionHandler.java      # Handler global de exceções
│   └── responses/
│       └── ErrorResponse.java               # DTO de resposta de erro
```

### Tipos de Exceções

1. **Exceções de Domínio**
   - Base: `DomainException`
   - Específicas: `productAlreadyExistsException`
   - Código de erro único por tipo
   - Mensagens personalizadas

2. **Exceções de Validação**
   - Tratamento de `MethodArgumentNotValidException`
   - Validação de campos obrigatórios
   - Lista detalhada de erros por campo

3. **Exceções Genéricas**
   - Tratamento de erros inesperados
   - Mensagens amigáveis ao usuário
   - Logging interno para debugging

### Formato das Respostas

1. **Erro de Domínio** (HTTP 400/409):
```json
{
    "timestamp": "2024-03-08T22:30:00",
    "code": "product-001",
    "message": "Cliente já existe com o email: joao@email.com",
    "errors": null
}
```

2. **Erro de Validação** (HTTP 400):
```json
{
    "timestamp": "2024-03-08T22:30:00",
    "code": "VALIDATION-001",
    "message": "Erro de validação nos campos",
    "errors": [
        {
            "field": "email",
            "message": "Email é obrigatório"
        },
        {
            "field": "name",
            "message": "Nome é obrigatório"
        }
    ]
}
```

3. **Erro Interno** (HTTP 500):
```json
{
    "timestamp": "2024-03-08T22:30:00",
    "code": "INTERNAL-001",
    "message": "Ocorreu um erro interno no servidor",
    "errors": null
}
```

### Códigos de Erro

| Código | Descrição | HTTP Status |
|--------|-----------|-------------|
| product-001 | Cliente já existe | 409 Conflict |
| VALIDATION-001 | Erro de validação | 400 Bad Request |
| INTERNAL-001 | Erro interno | 500 Internal Server Error |

### Benefícios

1. **Para o Usuário**
   - Mensagens claras e amigáveis
   - Respostas consistentes
   - Identificação precisa do problema
   - Feedback imediato de validações

2. **Para o Desenvolvimento**
   - Centralização do tratamento de erros
   - Padronização das respostas
   - Facilidade de manutenção
   - Melhor rastreabilidade

3. **Para o Negócio**
   - Melhor experiência do usuário
   - Redução de suporte técnico
   - Logs estruturados
   - Monitoramento eficiente

### Uso no Código

1. **Lançando Exceções**:
```java
if (existingproduct != null) {
    throw new productAlreadyExistsException(product.getEmail());
}
```

2. **Tratando Exceções**:
```java
@ExceptionHandler(productAlreadyExistsException.class)
public ResponseEntity<ErrorResponse> handleproductAlreadyExists(productAlreadyExistsException ex) {
    ErrorResponse response = ErrorResponse.of(ex.getCode(), ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
}
```

3. **Validações**:
```java
public record CreateproductRequest(
    @NotBlank(message = "Nome é obrigatório")
    String name,

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    String email
) {}
```

### Boas Práticas

1. **Exceções de Domínio**
   - Criar exceções específicas para regras de negócio
   - Usar códigos únicos por exceção
   - Mensagens claras e contextualizadas

2. **Validações**
   - Validar dados de entrada no controller
   - Usar anotações de validação
   - Mensagens em português e contextualizadas

3. **Logs**
   - Registrar erros inesperados
   - Incluir informações de contexto
   - Não logar dados sensíveis

4. **Segurança**
   - Não expor detalhes técnicos
   - Sanitizar mensagens de erro
   - Tratar timeouts e erros de conexão

# Workflow de Desenvolvimento

## Fluxo de Trabalho (GitFlow)

Este projeto segue o modelo GitFlow para gerenciamento de branches. Aqui está o fluxo de trabalho padrão:

### Branches Principais
- `main`: Código em produção
- `develop`: Branch de desenvolvimento, onde as features são integradas

### Branches de Funcionalidades
- Para iniciar uma nova funcionalidade:
  ```bash
  git checkout develop
  git checkout -b feature/nome-da-funcionalidade
  ```

### Processo de Desenvolvimento
1. Crie uma nova branch de feature a partir da develop
2. Desenvolva sua funcionalidade
3. Faça commit das alterações
4. Push para o repositório remoto
5. O GitHub Actions irá automaticamente:
   - Executar os testes unitários
   - Realizar análise de segurança
   - Criar um Pull Request para a branch develop

### Regras de Proteção
- Todas as features devem passar pelos testes automatizados
- A análise de segurança deve ser aprovada
- O código deve ser revisado por pelo menos um desenvolvedor