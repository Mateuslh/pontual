# Sistema de Gerenciamento de Entregas API

API completa para gerenciamento de entregas implementada com FastAPI e PostgreSQL, incluindo autenticação, motoristas, pontos de entrega, rotas e entregas.

## Estrutura do Projeto

```
api-mock/
├── app/
│   ├── __init__.py
│   ├── config.py
│   ├── database.py
│   ├── models.py
│   ├── schemas.py
│   ├── auth.py
│   ├── repository.py
│   ├── seed_data.py
│   ├── auth_middleware.py
│   └── routers/
│       ├── __init__.py
│       ├── auth.py
│       ├── users.py
│       ├── drivers.py
│       ├── delivery_points.py
│       ├── routes.py
│       └── deliveries.py
├── main.py
├── requirements.txt
├── Dockerfile
├── docker-compose.yml
└── README.md
```

## Como Executar

### Com Docker Compose (Recomendado)

```bash
docker compose up --build
```

### Localmente

1. Instale as dependências:
```bash
pip install -r requirements.txt
```

2. Configure o PostgreSQL e as variáveis de ambiente

3. Execute a aplicação:
```bash
uvicorn main:app --host 0.0.0.0 --port 3000 --reload
```

## Autenticação

- Todos os endpoints (exceto login) exigem autenticação via JWT.
- O token deve ser enviado no header:
  ```
  Authorization: Bearer {token}
  ```
- O login retorna `token` e `refreshToken`.

## Perfis de Usuário
- **admin**: pode criar, editar, listar e deletar motoristas, ver e editar todas as rotas, atribuir motoristas a rotas.
- **user**: pode criar, editar, listar e deletar apenas suas próprias rotas.

## Endpoints da API

### Base URL
```
http://localhost:3000/api/v1
```

### Autenticação

#### POST /user/login
Login de usuário
```json
{
  "email": "admin@exemplo.com",
  "password": "admin123"
}
```
**Response:**
```json
{
  "success": true,
  "message": "Login realizado com sucesso",
  "token": "...",
  "refreshToken": "...",
  "user": {
    "id": 1,
    "email": "admin@exemplo.com",
    "name": "Administrador",
    "role": "admin"
  }
}
```

### Usuários

#### GET /users
Listar todos os usuários

#### GET /users/profile
Obter perfil do usuário atual

#### POST /users/seed
Cria usuários de exemplo (admin e user)

### Motoristas (apenas admin)

#### GET /drivers
Lista todos os motoristas

#### GET /drivers/{driver_id}
Obter motorista específico

#### POST /drivers
Cria um novo motorista
```json
{
  "name": "João Motorista",
  "email": "joao@exemplo.com",
  "phone": "(11) 66666-6666",
  "license_number": "987654321",
  "vehicle_plate": "XYZ-5678",
  "vehicle_model": "Chevrolet Montana"
}
```

#### PUT /drivers/{driver_id}
Atualiza motorista

#### DELETE /drivers/{driver_id}
Remove motorista

#### GET /drivers/{driver_id}/routes
Lista rotas atribuídas ao motorista

### Pontos de Entrega

#### GET /delivery-points
Lista todos os pontos de entrega

#### GET /delivery-points/{point_id}
Obter ponto de entrega específico

#### POST /delivery-points
Cria novo ponto de entrega
```json
{
  "name": "Loja Centro",
  "address": "Rua das Flores, 123 - Centro",
  "contact_name": "João Silva",
  "contact_phone": "(11) 99999-9999",
  "notes": "Entregar no horário comercial"
}
```

#### PUT /delivery-points/{point_id}
Atualiza ponto de entrega

#### DELETE /delivery-points/{point_id}
Remove ponto de entrega

### Rotas

#### GET /routes
- **user**: lista apenas suas rotas
- **admin**: lista todas as rotas

#### GET /routes/{route_id}
Obter rota específica

#### POST /routes
Cria uma nova rota vinculada ao usuário autenticado
```json
{
  "name": "Rota Centro",
  "description": "Entrega no centro da cidade",
  "point_ids": [1, 2, 3]
}
```

#### PUT /routes/{route_id}
Edita rota (usuário só pode editar suas próprias)

#### DELETE /routes/{route_id}
Remove rota (usuário só pode deletar suas próprias)

#### POST /routes/assign
Atribui motorista a uma rota (apenas admin)
```json
{
  "route_id": 1,
  "driver_id": 1
}
```

### Entregas

#### GET /deliveries
Lista todas as entregas (filtros opcionais: status, driver_id, route_id)

#### GET /deliveries/{delivery_id}
Obter entrega específica

#### PUT /deliveries/{delivery_id}/status
Atualiza status da entrega
```json
{
  "status": "completed",
  "notes": "Entregue com sucesso",
  "signature": "data:image/jpeg;base64,..."
}
```

## Exemplo de Fluxo

1. **Usuário comum** faz login, cria e gerencia suas rotas.
2. **Admin** faz login, cadastra motoristas e pode atribuí-los a qualquer rota.
3. Todos os endpoints protegidos exigem o header `Authorization: Bearer {token}`.

## Usuários de Teste

- **Admin:** admin@exemplo.com / admin123
- **User:** usuario@exemplo.com / user123

## Dados de Exemplo

A API inclui dados de exemplo para:
- 2 usuários (admin e usuário normal)
- 2 motoristas (Carlos e João)
- 2 pontos de entrega (Loja Centro e Loja Zona Sul)
- 1 rota (Rota Centro)

## Configuração

A API está configurada para rodar na porta 3000, compatível com as configurações do cliente Kotlin:

- BASE_URL_EMULATOR: http://10.0.2.2:3000/
- BASE_URL_DEVICE: http://192.168.1.100:3000/

## Documentação Interativa
- Swagger UI: http://localhost:3000/docs
- ReDoc: http://localhost:3000/redoc

## Status HTTP

- **200**: Sucesso
- **201**: Criado com sucesso
- **204**: Sucesso sem conteúdo
- **400**: Requisição inválida
- **401**: Não autorizado
- **403**: Proibido
- **404**: Não encontrado
- **500**: Erro interno do servidor 