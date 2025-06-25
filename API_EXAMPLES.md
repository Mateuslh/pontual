# Exemplos Práticos - Sistema de Gerenciamento de Entregas API

## 🚀 Início Rápido

### 1. Iniciar a API
```bash
docker compose up --build
```

### 2. Criar usuários de exemplo
```bash
curl -X POST http://localhost:3000/api/v1/users/seed
```

## 🔐 Autenticação

### Login como Admin
```bash
curl -X POST http://localhost:3000/api/v1/user/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@exemplo.com",
    "password": "admin123"
  }'
```

**Resposta:**
```json
{
  "success": true,
  "message": "Login realizado com sucesso",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "email": "admin@exemplo.com",
    "name": "Administrador",
    "role": "admin"
  }
}
```

### Login como Usuário Comum
```bash
curl -X POST http://localhost:3000/api/v1/user/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "usuario@exemplo.com",
    "password": "user123"
  }'
```

## 👥 Gerenciamento de Motoristas (Apenas Admin)

### 1. Criar Motorista
```bash
curl -X POST http://localhost:3000/api/v1/drivers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_ADMIN_TOKEN" \
  -d '{
    "name": "Carlos Silva",
    "email": "carlos.silva@empresa.com",
    "phone": "(11) 99999-8888",
    "license_number": "12345678901",
    "vehicle_plate": "ABC-1234",
    "vehicle_model": "Fiat Strada"
  }'
```

**Resposta:**
```json
{
  "name": "Carlos Silva",
  "email": "carlos.silva@empresa.com",
  "phone": "(11) 99999-8888",
  "license_number": "12345678901",
  "vehicle_plate": "ABC-1234",
  "vehicle_model": "Fiat Strada",
  "id": 1,
  "is_active": true,
  "current_route_id": null,
  "current_route_name": null,
  "created_at": "2024-01-15T10:30:00Z",
  "updated_at": null
}
```

### 2. Listar Motoristas
```bash
curl -X GET http://localhost:3000/api/v1/drivers \
  -H "Authorization: Bearer YOUR_ADMIN_TOKEN"
```

### 3. Atualizar Motorista
```bash
curl -X PUT http://localhost:3000/api/v1/drivers/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_ADMIN_TOKEN" \
  -d '{
    "name": "Carlos Silva Santos",
    "email": "carlos.silva@empresa.com",
    "phone": "(11) 99999-8888",
    "license_number": "12345678901",
    "vehicle_plate": "ABC-1234",
    "vehicle_model": "Fiat Strada Adventure"
  }'
```

## 📍 Gerenciamento de Pontos de Entrega

### 1. Criar Ponto de Entrega
```bash
curl -X POST http://localhost:3000/api/v1/delivery-points \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "name": "Loja Shopping Center",
    "address": "Av. Paulista, 1000 - Bela Vista, São Paulo - SP",
    "contact_name": "Maria Santos",
    "contact_phone": "(11) 88888-7777",
    "notes": "Entregar no horário comercial, portaria principal"
  }'
```

**Resposta:**
```json
{
  "name": "Loja Shopping Center",
  "address": "Av. Paulista, 1000 - Bela Vista, São Paulo - SP",
  "contact_name": "Maria Santos",
  "contact_phone": "(11) 88888-7777",
  "notes": "Entregar no horário comercial, portaria principal",
  "id": 1,
  "is_active": true,
  "created_at": "2024-01-15T10:30:00Z",
  "updated_at": null
}
```

### 2. Listar Pontos de Entrega
```bash
curl -X GET http://localhost:3000/api/v1/delivery-points \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### 3. Criar Múltiplos Pontos de Entrega
```bash
# Ponto 1
curl -X POST http://localhost:3000/api/v1/delivery-points \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "name": "Loja Centro",
    "address": "Rua das Flores, 123 - Centro, São Paulo - SP",
    "contact_name": "João Silva",
    "contact_phone": "(11) 77777-6666",
    "notes": "Entregar no horário comercial"
  }'

# Ponto 2
curl -X POST http://localhost:3000/api/v1/delivery-points \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "name": "Loja Zona Sul",
    "address": "Rua Vergueiro, 500 - Liberdade, São Paulo - SP",
    "contact_name": "Ana Costa",
    "contact_phone": "(11) 66666-5555",
    "notes": "Entregar após 14h"
  }'

# Ponto 3
curl -X POST http://localhost:3000/api/v1/delivery-points \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "name": "Loja Zona Norte",
    "address": "Av. Santana, 200 - Santana, São Paulo - SP",
    "contact_name": "Pedro Oliveira",
    "contact_phone": "(11) 55555-4444",
    "notes": "Entregar no horário comercial, estacionamento disponível"
  }'
```

## 🛣️ Gerenciamento de Rotas

### 1. Criar Rota (Usuário Comum)
```bash
curl -X POST http://localhost:3000/api/v1/routes \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_USER_TOKEN" \
  -d '{
    "name": "Rota Centro - Manhã",
    "description": "Entrega no centro da cidade no período da manhã",
    "point_ids": [1, 2, 3]
  }'
```

**Resposta:**
```json
{
  "name": "Rota Centro - Manhã",
  "description": "Entrega no centro da cidade no período da manhã",
  "id": 1,
  "points": [
    {
      "id": 1,
      "route_id": 1,
      "delivery_point_id": 1,
      "sequence": 1,
      "delivery_point": {
        "name": "Loja Centro",
        "address": "Rua das Flores, 123 - Centro, São Paulo - SP",
        "contact_name": "João Silva",
        "contact_phone": "(11) 77777-6666",
        "notes": "Entregar no horário comercial",
        "id": 1,
        "is_active": true,
        "created_at": "2024-01-15T10:30:00Z",
        "updated_at": null
      }
    }
  ],
  "user_id": 2,
  "user_name": "Usuário Normal",
  "driver_id": null,
  "driver_name": null,
  "estimated_duration": null,
  "total_distance": null,
  "is_active": true,
  "created_at": "2024-01-15T10:30:00Z",
  "updated_at": null
}
```

### 2. Listar Rotas (Usuário vê apenas suas)
```bash
curl -X GET http://localhost:3000/api/v1/routes \
  -H "Authorization: Bearer YOUR_USER_TOKEN"
```

### 3. Listar Rotas (Admin vê todas)
```bash
curl -X GET http://localhost:3000/api/v1/routes \
  -H "Authorization: Bearer YOUR_ADMIN_TOKEN"
```

### 4. Atribuir Motorista à Rota (Apenas Admin)
```bash
curl -X POST http://localhost:3000/api/v1/routes/assign \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_ADMIN_TOKEN" \
  -d '{
    "route_id": 1,
    "driver_id": 1
  }'
```

**Resposta:**
```json
{
  "name": "Rota Centro - Manhã",
  "description": "Entrega no centro da cidade no período da manhã",
  "id": 1,
  "points": [...],
  "user_id": 2,
  "user_name": "Usuário Normal",
  "driver_id": 1,
  "driver_name": "Carlos Silva Santos",
  "estimated_duration": null,
  "total_distance": null,
  "is_active": true,
  "created_at": "2024-01-15T10:30:00Z",
  "updated_at": "2024-01-15T11:00:00Z"
}
```

## 📦 Gerenciamento de Entregas

### 1. Listar Entregas
```bash
# Todas as entregas
curl -X GET http://localhost:3000/api/v1/deliveries \
  -H "Authorization: Bearer YOUR_TOKEN"

# Filtrar por status
curl -X GET "http://localhost:3000/api/v1/deliveries?status=pending" \
  -H "Authorization: Bearer YOUR_TOKEN"

# Filtrar por motorista
curl -X GET "http://localhost:3000/api/v1/deliveries?driver_id=1" \
  -H "Authorization: Bearer YOUR_TOKEN"

# Filtrar por rota
curl -X GET "http://localhost:3000/api/v1/deliveries?route_id=1" \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### 2. Atualizar Status da Entrega
```bash
curl -X PUT http://localhost:3000/api/v1/deliveries/1/status \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "status": "completed",
    "notes": "Entregue com sucesso. Cliente assinou o recibo.",
    "signature": "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQ..."
  }'
```

**Resposta:**
```json
{
  "id": 1,
  "route_id": 1,
  "route_name": "Rota Centro - Manhã",
  "driver_id": 1,
  "driver_name": "Carlos Silva Santos",
  "delivery_point_id": 1,
  "delivery_point_name": "Loja Centro",
  "delivery_point_address": "Rua das Flores, 123 - Centro, São Paulo - SP",
  "status": "completed",
  "scheduled_date": "2024-01-15T08:00:00Z",
  "completed_date": "2024-01-15T09:30:00Z",
  "notes": "Entregue com sucesso. Cliente assinou o recibo.",
  "signature": "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQ...",
  "created_at": "2024-01-15T08:00:00Z",
  "updated_at": "2024-01-15T09:30:00Z"
}
```

## 🔄 Fluxo Completo de Exemplo

### Cenário: Entrega de Produtos em Lojas

#### 1. Admin faz login
```bash
curl -X POST http://localhost:3000/api/v1/user/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@exemplo.com",
    "password": "admin123"
  }'
```

#### 2. Admin cadastra motorista
```bash
curl -X POST http://localhost:3000/api/v1/drivers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer ADMIN_TOKEN" \
  -d '{
    "name": "Roberto Alves",
    "email": "roberto.alves@empresa.com",
    "phone": "(11) 99999-1111",
    "license_number": "98765432100",
    "vehicle_plate": "XYZ-5678",
    "vehicle_model": "Chevrolet Montana"
  }'
```

#### 3. Usuário comum faz login
```bash
curl -X POST http://localhost:3000/api/v1/user/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "usuario@exemplo.com",
    "password": "user123"
  }'
```

#### 4. Usuário cria pontos de entrega
```bash
# Loja 1
curl -X POST http://localhost:3000/api/v1/delivery-points \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer USER_TOKEN" \
  -d '{
    "name": "Loja Shopping Ibirapuera",
    "address": "Av. Ibirapuera, 3103 - Moema, São Paulo - SP",
    "contact_name": "Fernanda Lima",
    "contact_phone": "(11) 88888-9999",
    "notes": "Entregar no horário comercial, portaria 3"
  }'

# Loja 2
curl -X POST http://localhost:3000/api/v1/delivery-points \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer USER_TOKEN" \
  -d '{
    "name": "Loja Shopping Morumbi",
    "address": "Av. Roque Petroni Júnior, 1089 - Morumbi, São Paulo - SP",
    "contact_name": "Ricardo Santos",
    "contact_phone": "(11) 77777-8888",
    "notes": "Entregar após 10h, estacionamento disponível"
  }'
```

#### 5. Usuário cria rota
```bash
curl -X POST http://localhost:3000/api/v1/routes \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer USER_TOKEN" \
  -d '{
    "name": "Rota Shopping - Tarde",
    "description": "Entrega nos shoppings no período da tarde",
    "point_ids": [1, 2]
  }'
```

#### 6. Admin atribui motorista à rota
```bash
curl -X POST http://localhost:3000/api/v1/routes/assign \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer ADMIN_TOKEN" \
  -d '{
    "route_id": 1,
    "driver_id": 1
  }'
```

#### 7. Motorista atualiza status das entregas
```bash
# Iniciar entrega
curl -X PUT http://localhost:3000/api/v1/deliveries/1/status \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer DRIVER_TOKEN" \
  -d '{
    "status": "in_progress",
    "notes": "Saindo para entrega"
  }'

# Completar entrega
curl -X PUT http://localhost:3000/api/v1/deliveries/1/status \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer DRIVER_TOKEN" \
  -d '{
    "status": "completed",
    "notes": "Entregue com sucesso",
    "signature": "data:image/jpeg;base64,..."
  }'
```

## 📱 Exemplos para Postman/Insomnia

### Collection JSON
```json
{
  "info": {
    "name": "Sistema de Entregas API",
    "description": "API para gerenciamento de entregas"
  },
  "variable": [
    {
      "key": "base_url",
      "value": "http://localhost:3000/api/v1"
    },
    {
      "key": "admin_token",
      "value": ""
    },
    {
      "key": "user_token",
      "value": ""
    }
  ],
  "item": [
    {
      "name": "Auth",
      "item": [
        {
          "name": "Login Admin",
          "request": {
            "method": "POST",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"email\": \"admin@exemplo.com\",\n  \"password\": \"admin123\"\n}"
            },
            "url": "{{base_url}}/user/login"
          }
        },
        {
          "name": "Login User",
          "request": {
            "method": "POST",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"email\": \"usuario@exemplo.com\",\n  \"password\": \"user123\"\n}"
            },
            "url": "{{base_url}}/user/login"
          }
        }
      ]
    },
    {
      "name": "Drivers",
      "item": [
        {
          "name": "Create Driver",
          "request": {
            "method": "POST",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              },
              {
                "key": "Authorization",
                "value": "Bearer {{admin_token}}"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"name\": \"João Motorista\",\n  \"email\": \"joao@exemplo.com\",\n  \"phone\": \"(11) 99999-9999\",\n  \"license_number\": \"123456789\",\n  \"vehicle_plate\": \"ABC-1234\",\n  \"vehicle_model\": \"Fiat Strada\"\n}"
            },
            "url": "{{base_url}}/drivers"
          }
        }
      ]
    }
  ]
}
```

## 🚨 Tratamento de Erros

### Erro de Autenticação (401)
```json
{
  "detail": "Token inválido"
}
```

### Erro de Permissão (403)
```json
{
  "detail": "Acesso negado. Apenas administradores podem realizar esta ação."
}
```

### Erro de Recurso não encontrado (404)
```json
{
  "detail": "Rota não encontrada"
}
```

### Erro de Validação (422)
```json
{
  "detail": [
    {
      "loc": ["body", "email"],
      "msg": "field required",
      "type": "value_error.missing"
    }
  ]
}
```

## 🔧 Scripts Úteis

### Script para Testar API Completa
```bash
#!/bin/bash

echo "🚀 Iniciando testes da API..."

# 1. Criar usuários
echo "📝 Criando usuários..."
curl -X POST http://localhost:3000/api/v1/users/seed

# 2. Login admin
echo "🔐 Login admin..."
ADMIN_RESPONSE=$(curl -s -X POST http://localhost:3000/api/v1/user/login \
  -H "Content-Type: application/json" \
  -d '{"email": "admin@exemplo.com", "password": "admin123"}')

ADMIN_TOKEN=$(echo $ADMIN_RESPONSE | jq -r '.token')
echo "Admin token: $ADMIN_TOKEN"

# 3. Login user
echo "🔐 Login user..."
USER_RESPONSE=$(curl -s -X POST http://localhost:3000/api/v1/user/login \
  -H "Content-Type: application/json" \
  -d '{"email": "usuario@exemplo.com", "password": "user123"}')

USER_TOKEN=$(echo $USER_RESPONSE | jq -r '.token')
echo "User token: $USER_TOKEN"

# 4. Criar motorista (admin)
echo "👨‍💼 Criando motorista..."
curl -X POST http://localhost:3000/api/v1/drivers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -d '{
    "name": "Carlos Motorista",
    "email": "carlos@exemplo.com",
    "phone": "(11) 99999-8888",
    "license_number": "123456789",
    "vehicle_plate": "ABC-1234",
    "vehicle_model": "Fiat Strada"
  }'

# 5. Criar pontos de entrega (user)
echo "📍 Criando pontos de entrega..."
curl -X POST http://localhost:3000/api/v1/delivery-points \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $USER_TOKEN" \
  -d '{
    "name": "Loja Centro",
    "address": "Rua das Flores, 123 - Centro",
    "contact_name": "João Silva",
    "contact_phone": "(11) 77777-6666",
    "notes": "Entregar no horário comercial"
  }'

# 6. Criar rota (user)
echo "🛣️ Criando rota..."
curl -X POST http://localhost:3000/api/v1/routes \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $USER_TOKEN" \
  -d '{
    "name": "Rota Centro",
    "description": "Entrega no centro da cidade",
    "point_ids": [1]
  }'

# 7. Atribuir motorista à rota (admin)
echo "🔗 Atribuindo motorista à rota..."
curl -X POST http://localhost:3000/api/v1/routes/assign \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -d '{
    "route_id": 1,
    "driver_id": 1
  }'

echo "✅ Testes concluídos!"
```

Salve como `test_api.sh` e execute:
```bash
chmod +x test_api.sh
./test_api.sh
```

## 📊 Status de Entrega

- `pending`: Aguardando início
- `in_progress`: Em andamento
- `completed`: Concluída
- `failed`: Falhou
- `cancelled`: Cancelada

## 🔐 Segurança

- Todos os endpoints (exceto login) exigem autenticação JWT
- Tokens expiram em 30 minutos
- Refresh tokens expiram em 7 dias
- Usuários só podem acessar seus próprios recursos
- Admins têm acesso total ao sistema 