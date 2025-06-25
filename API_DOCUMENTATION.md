# API Documentation - Sistema de Gerenciamento de Entregas

## Base URL
```
http://localhost:3000/api/v1
```

## Autenticação

### Login
**POST** `/user/login`

**Request:**
```json
{
  "email": "usuario@exemplo.com",
  "password": "senha123"
}
```

**Response (200):**
```json
{
  "success": true,
  "message": "Login realizado com sucesso",
  "token": "jwt_token_aqui",
  "refreshToken": "refresh_token_aqui",
  "user": {
    "id": 1,
    "email": "usuario@exemplo.com",
    "name": "Nome do Usuário",
    "role": "admin"
  }
}
```

**Response (401):**
```json
{
  "success": false,
  "message": "Credenciais inválidas"
}
```

### Logout
**POST** `/user/logout`

**Headers:** `Authorization: Bearer {token}`

**Response (200):**
```json
{
  "success": true,
  "message": "Logout realizado com sucesso"
}
```

### Refresh Token
**POST** `/user/refresh`

**Request:**
```json
{
  "refreshToken": "refresh_token_aqui"
}
```

**Response (200):**
```json
{
  "success": true,
  "token": "novo_jwt_token",
  "refreshToken": "novo_refresh_token"
}
```

## Usuários

### Listar Usuários
**GET** `/users`

**Headers:** `Authorization: Bearer {token}`

**Response (200):**
```json
[
  {
    "id": 1,
    "email": "admin@exemplo.com",
    "name": "Administrador",
    "role": "admin"
  },
  {
    "id": 2,
    "email": "usuario@exemplo.com",
    "name": "Usuário Normal",
    "role": "user"
  }
]
```

### Perfil do Usuário
**GET** `/users/profile`

**Headers:** `Authorization: Bearer {token}`

**Response (200):**
```json
{
  "id": 1,
  "email": "usuario@exemplo.com",
  "name": "Nome do Usuário",
  "role": "admin"
}
```

## Pontos de Entrega

### Listar Pontos de Entrega
**GET** `/delivery-points`

**Headers:** `Authorization: Bearer {token}`

**Response (200):**
```json
[
  {
    "id": 1,
    "name": "Loja Centro",
    "address": "Rua das Flores, 123 - Centro",
    "latitude": -23.5505,
    "longitude": -46.6333,
    "contactName": "João Silva",
    "contactPhone": "(11) 99999-9999",
    "notes": "Entregar no horário comercial",
    "isActive": true,
    "createdAt": "2024-01-01T10:00:00Z",
    "updatedAt": "2024-01-01T10:00:00Z"
  }
]
```

### Obter Ponto de Entrega
**GET** `/delivery-points/{id}`

**Headers:** `Authorization: Bearer {token}`

**Response (200):**
```json
{
  "id": 1,
  "name": "Loja Centro",
  "address": "Rua das Flores, 123 - Centro",
  "latitude": -23.5505,
  "longitude": -46.6333,
  "contactName": "João Silva",
  "contactPhone": "(11) 99999-9999",
  "notes": "Entregar no horário comercial",
  "isActive": true,
  "createdAt": "2024-01-01T10:00:00Z",
  "updatedAt": "2024-01-01T10:00:00Z"
}
```

### Criar Ponto de Entrega
**POST** `/delivery-points`

**Headers:** `Authorization: Bearer {token}`

**Request:**
```json
{
  "name": "Nova Loja",
  "address": "Av. Paulista, 1000 - Bela Vista",
  "latitude": -23.5631,
  "longitude": -46.6544,
  "contactName": "Maria Santos",
  "contactPhone": "(11) 88888-8888",
  "notes": "Entregar após 14h"
}
```

**Response (201):**
```json
{
  "id": 2,
  "name": "Nova Loja",
  "address": "Av. Paulista, 1000 - Bela Vista",
  "latitude": -23.5631,
  "longitude": -46.6544,
  "contactName": "Maria Santos",
  "contactPhone": "(11) 88888-8888",
  "notes": "Entregar após 14h",
  "isActive": true,
  "createdAt": "2024-01-01T11:00:00Z",
  "updatedAt": "2024-01-01T11:00:00Z"
}
```

### Atualizar Ponto de Entrega
**PUT** `/delivery-points/{id}`

**Headers:** `Authorization: Bearer {token}`

**Request:**
```json
{
  "name": "Loja Centro Atualizada",
  "address": "Rua das Flores, 123 - Centro",
  "latitude": -23.5505,
  "longitude": -46.6333,
  "contactName": "João Silva",
  "contactPhone": "(11) 99999-9999",
  "notes": "Entregar no horário comercial"
}
```

**Response (200):**
```json
{
  "id": 1,
  "name": "Loja Centro Atualizada",
  "address": "Rua das Flores, 123 - Centro",
  "latitude": -23.5505,
  "longitude": -46.6333,
  "contactName": "João Silva",
  "contactPhone": "(11) 99999-9999",
  "notes": "Entregar no horário comercial",
  "isActive": true,
  "createdAt": "2024-01-01T10:00:00Z",
  "updatedAt": "2024-01-01T12:00:00Z"
}
```

### Deletar Ponto de Entrega
**DELETE** `/delivery-points/{id}`

**Headers:** `Authorization: Bearer {token}`

**Response (204):** No content

## Rotas

### Listar Rotas
**GET** `/routes`

**Headers:** `Authorization: Bearer {token}`

**Response (200):**
```json
[
  {
    "id": 1,
    "name": "Rota Centro",
    "description": "Entrega no centro da cidade",
    "points": [
      {
        "id": 1,
        "routeId": 1,
        "deliveryPointId": 1,
        "sequence": 1,
        "deliveryPoint": {
          "id": 1,
          "name": "Loja Centro",
          "address": "Rua das Flores, 123 - Centro",
          "latitude": -23.5505,
          "longitude": -46.6333,
          "contactName": "João Silva",
          "contactPhone": "(11) 99999-9999",
          "notes": "Entregar no horário comercial",
          "isActive": true,
          "createdAt": "2024-01-01T10:00:00Z",
          "updatedAt": "2024-01-01T10:00:00Z"
        }
      }
    ],
    "driverId": 1,
    "driverName": "Carlos Motorista",
    "estimatedDuration": 120,
    "totalDistance": 15.5,
    "isActive": true,
    "createdAt": "2024-01-01T10:00:00Z",
    "updatedAt": "2024-01-01T10:00:00Z"
  }
]
```

### Obter Rota
**GET** `/routes/{id}`

**Headers:** `Authorization: Bearer {token}`

**Response (200):**
```json
{
  "id": 1,
  "name": "Rota Centro",
  "description": "Entrega no centro da cidade",
  "points": [
    {
      "id": 1,
      "routeId": 1,
      "deliveryPointId": 1,
      "sequence": 1,
      "deliveryPoint": {
        "id": 1,
        "name": "Loja Centro",
        "address": "Rua das Flores, 123 - Centro",
        "latitude": -23.5505,
        "longitude": -46.6333,
        "contactName": "João Silva",
        "contactPhone": "(11) 99999-9999",
        "notes": "Entregar no horário comercial",
        "isActive": true,
        "createdAt": "2024-01-01T10:00:00Z",
        "updatedAt": "2024-01-01T10:00:00Z"
      }
    }
  ],
  "driverId": 1,
  "driverName": "Carlos Motorista",
  "estimatedDuration": 120,
  "totalDistance": 15.5,
  "isActive": true,
  "createdAt": "2024-01-01T10:00:00Z",
  "updatedAt": "2024-01-01T10:00:00Z"
}
```

### Criar Rota
**POST** `/routes`

**Headers:** `Authorization: Bearer {token}`

**Request:**
```json
{
  "name": "Nova Rota",
  "description": "Rota para zona sul",
  "pointIds": [1, 2, 3]
}
```

**Response (201):**
```json
{
  "id": 2,
  "name": "Nova Rota",
  "description": "Rota para zona sul",
  "points": [
    {
      "id": 2,
      "routeId": 2,
      "deliveryPointId": 1,
      "sequence": 1,
      "deliveryPoint": {
        "id": 1,
        "name": "Loja Centro",
        "address": "Rua das Flores, 123 - Centro",
        "latitude": -23.5505,
        "longitude": -46.6333,
        "contactName": "João Silva",
        "contactPhone": "(11) 99999-9999",
        "notes": "Entregar no horário comercial",
        "isActive": true,
        "createdAt": "2024-01-01T10:00:00Z",
        "updatedAt": "2024-01-01T10:00:00Z"
      }
    }
  ],
  "driverId": null,
  "driverName": null,
  "estimatedDuration": null,
  "totalDistance": null,
  "isActive": true,
  "createdAt": "2024-01-01T13:00:00Z",
  "updatedAt": "2024-01-01T13:00:00Z"
}
```

### Atualizar Rota
**PUT** `/routes/{id}`

**Headers:** `Authorization: Bearer {token}`

**Request:**
```json
{
  "name": "Rota Centro Atualizada",
  "description": "Entrega no centro da cidade - atualizada",
  "pointIds": [1, 3, 2]
}
```

**Response (200):**
```json
{
  "id": 1,
  "name": "Rota Centro Atualizada",
  "description": "Entrega no centro da cidade - atualizada",
  "points": [
    {
      "id": 1,
      "routeId": 1,
      "deliveryPointId": 1,
      "sequence": 1,
      "deliveryPoint": {
        "id": 1,
        "name": "Loja Centro",
        "address": "Rua das Flores, 123 - Centro",
        "latitude": -23.5505,
        "longitude": -46.6333,
        "contactName": "João Silva",
        "contactPhone": "(11) 99999-9999",
        "notes": "Entregar no horário comercial",
        "isActive": true,
        "createdAt": "2024-01-01T10:00:00Z",
        "updatedAt": "2024-01-01T10:00:00Z"
      }
    }
  ],
  "driverId": 1,
  "driverName": "Carlos Motorista",
  "estimatedDuration": 120,
  "totalDistance": 15.5,
  "isActive": true,
  "createdAt": "2024-01-01T10:00:00Z",
  "updatedAt": "2024-01-01T14:00:00Z"
}
```

### Deletar Rota
**DELETE** `/routes/{id}`

**Headers:** `Authorization: Bearer {token}`

**Response (204):** No content

### Atribuir Rota a Motorista
**POST** `/routes/assign`

**Headers:** `Authorization: Bearer {token}`

**Request:**
```json
{
  "routeId": 1,
  "driverId": 1
}
```

**Response (200):**
```json
{
  "id": 1,
  "name": "Rota Centro",
  "description": "Entrega no centro da cidade",
  "points": [...],
  "driverId": 1,
  "driverName": "Carlos Motorista",
  "estimatedDuration": 120,
  "totalDistance": 15.5,
  "isActive": true,
  "createdAt": "2024-01-01T10:00:00Z",
  "updatedAt": "2024-01-01T15:00:00Z"
}
```

## Motoristas

### Listar Motoristas
**GET** `/drivers`

**Headers:** `Authorization: Bearer {token}`

**Response (200):**
```json
[
  {
    "id": 1,
    "name": "Carlos Motorista",
    "email": "carlos@exemplo.com",
    "phone": "(11) 77777-7777",
    "licenseNumber": "123456789",
    "vehiclePlate": "ABC-1234",
    "vehicleModel": "Fiat Strada",
    "isActive": true,
    "currentRouteId": 1,
    "currentRouteName": "Rota Centro",
    "createdAt": "2024-01-01T10:00:00Z",
    "updatedAt": "2024-01-01T10:00:00Z"
  }
]
```

### Obter Motorista
**GET** `/drivers/{id}`

**Headers:** `Authorization: Bearer {token}`

**Response (200):**
```json
{
  "id": 1,
  "name": "Carlos Motorista",
  "email": "carlos@exemplo.com",
  "phone": "(11) 77777-7777",
  "licenseNumber": "123456789",
  "vehiclePlate": "ABC-1234",
  "vehicleModel": "Fiat Strada",
  "isActive": true,
  "currentRouteId": 1,
  "currentRouteName": "Rota Centro",
  "createdAt": "2024-01-01T10:00:00Z",
  "updatedAt": "2024-01-01T10:00:00Z"
}
```

### Criar Motorista
**POST** `/drivers`

**Headers:** `Authorization: Bearer {token}`

**Request:**
```json
{
  "name": "João Motorista",
  "email": "joao@exemplo.com",
  "phone": "(11) 66666-6666",
  "licenseNumber": "987654321",
  "vehiclePlate": "XYZ-5678",
  "vehicleModel": "Chevrolet Montana"
}
```

**Response (201):**
```json
{
  "id": 2,
  "name": "João Motorista",
  "email": "joao@exemplo.com",
  "phone": "(11) 66666-6666",
  "licenseNumber": "987654321",
  "vehiclePlate": "XYZ-5678",
  "vehicleModel": "Chevrolet Montana",
  "isActive": true,
  "currentRouteId": null,
  "currentRouteName": null,
  "createdAt": "2024-01-01T16:00:00Z",
  "updatedAt": "2024-01-01T16:00:00Z"
}
```

### Atualizar Motorista
**PUT** `/drivers/{id}`

**Headers:** `Authorization: Bearer {token}`

**Request:**
```json
{
  "name": "Carlos Motorista Atualizado",
  "email": "carlos@exemplo.com",
  "phone": "(11) 77777-7777",
  "licenseNumber": "123456789",
  "vehiclePlate": "ABC-1234",
  "vehicleModel": "Fiat Strada"
}
```

**Response (200):**
```json
{
  "id": 1,
  "name": "Carlos Motorista Atualizado",
  "email": "carlos@exemplo.com",
  "phone": "(11) 77777-7777",
  "licenseNumber": "123456789",
  "vehiclePlate": "ABC-1234",
  "vehicleModel": "Fiat Strada",
  "isActive": true,
  "currentRouteId": 1,
  "currentRouteName": "Rota Centro",
  "createdAt": "2024-01-01T10:00:00Z",
  "updatedAt": "2024-01-01T17:00:00Z"
}
```

### Deletar Motorista
**DELETE** `/drivers/{id}`

**Headers:** `Authorization: Bearer {token}`

**Response (204):** No content

### Rotas do Motorista
**GET** `/drivers/{driverId}/routes`

**Headers:** `Authorization: Bearer {token}`

**Response (200):**
```json
[
  {
    "id": 1,
    "name": "Rota Centro",
    "description": "Entrega no centro da cidade",
    "points": [...],
    "driverId": 1,
    "driverName": "Carlos Motorista",
    "estimatedDuration": 120,
    "totalDistance": 15.5,
    "isActive": true,
    "createdAt": "2024-01-01T10:00:00Z",
    "updatedAt": "2024-01-01T10:00:00Z"
  }
]
```

## Entregas

### Listar Entregas
**GET** `/deliveries`

**Headers:** `Authorization: Bearer {token}`

**Query Parameters:**
- `status` (opcional): Filtro por status (pending, in_progress, completed, failed, cancelled)
- `driverId` (opcional): Filtro por motorista
- `routeId` (opcional): Filtro por rota

**Response (200):**
```json
[
  {
    "id": 1,
    "routeId": 1,
    "routeName": "Rota Centro",
    "driverId": 1,
    "driverName": "Carlos Motorista",
    "deliveryPointId": 1,
    "deliveryPointName": "Loja Centro",
    "deliveryPointAddress": "Rua das Flores, 123 - Centro",
    "status": "completed",
    "scheduledDate": "2024-01-01T10:00:00Z",
    "completedDate": "2024-01-01T11:30:00Z",
    "notes": "Entregue com sucesso",
    "signature": "https://exemplo.com/signatures/1.jpg",
    "createdAt": "2024-01-01T10:00:00Z",
    "updatedAt": "2024-01-01T11:30:00Z"
  }
]
```

### Obter Entrega
**GET** `/deliveries/{id}`

**Headers:** `Authorization: Bearer {token}`

**Response (200):**
```json
{
  "id": 1,
  "routeId": 1,
  "routeName": "Rota Centro",
  "driverId": 1,
  "driverName": "Carlos Motorista",
  "deliveryPointId": 1,
  "deliveryPointName": "Loja Centro",
  "deliveryPointAddress": "Rua das Flores, 123 - Centro",
  "status": "completed",
  "scheduledDate": "2024-01-01T10:00:00Z",
  "completedDate": "2024-01-01T11:30:00Z",
  "notes": "Entregue com sucesso",
  "signature": "https://exemplo.com/signatures/1.jpg",
  "createdAt": "2024-01-01T10:00:00Z",
  "updatedAt": "2024-01-01T11:30:00Z"
}
```

### Atualizar Status da Entrega
**PUT** `/deliveries/{id}/status`

**Headers:** `Authorization: Bearer {token}`

**Request:**
```json
{
  "status": "completed",
  "notes": "Entregue com sucesso",
  "signature": "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQ..."
}
```

**Response (200):**
```json
{
  "id": 1,
  "routeId": 1,
  "routeName": "Rota Centro",
  "driverId": 1,
  "driverName": "Carlos Motorista",
  "deliveryPointId": 1,
  "deliveryPointName": "Loja Centro",
  "deliveryPointAddress": "Rua das Flores, 123 - Centro",
  "status": "completed",
  "scheduledDate": "2024-01-01T10:00:00Z",
  "completedDate": "2024-01-01T12:00:00Z",
  "notes": "Entregue com sucesso",
  "signature": "https://exemplo.com/signatures/1.jpg",
  "createdAt": "2024-01-01T10:00:00Z",
  "updatedAt": "2024-01-01T12:00:00Z"
}
```

## Códigos de Status HTTP

- **200**: Sucesso
- **201**: Criado com sucesso
- **204**: Sucesso sem conteúdo
- **400**: Requisição inválida
- **401**: Não autorizado
- **403**: Proibido
- **404**: Não encontrado
- **500**: Erro interno do servidor

## Autenticação

Todas as requisições (exceto login) devem incluir o header:
```
Authorization: Bearer {jwt_token}
```

## Paginação (Opcional)

Para endpoints que retornam listas, você pode implementar paginação:

**GET** `/delivery-points?page=1&limit=10`

**Response:**
```json
{
  "data": [...],
  "pagination": {
    "page": 1,
    "limit": 10,
    "total": 50,
    "totalPages": 5
  }
}
```

## Upload de Arquivos

Para upload de assinaturas, use multipart/form-data:

**POST** `/deliveries/{id}/signature`

**Headers:** `Authorization: Bearer {token}`

**Body:** `multipart/form-data`
- `signature`: arquivo de imagem

## Webhooks (Opcional)

Para notificações em tempo real, implemente webhooks:

**POST** `/webhooks/delivery-status`

**Request:**
```json
{
  "deliveryId": 1,
  "status": "completed",
  "timestamp": "2024-01-01T12:00:00Z"
}
``` 