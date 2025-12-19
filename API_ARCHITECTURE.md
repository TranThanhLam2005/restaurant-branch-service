# Branch Service API Architecture

## Overview

This service follows a clean architecture pattern with separation between external (REST API) and internal (inter-service) interfaces.

## Architecture Layers

### 1. DTOs (Data Transfer Objects)

#### External DTOs (`dto/external/`)

Used for REST API communication with frontend/external clients:

- `BranchResponseDTO` - Response for GET requests
- `BranchCreateRequestDTO` - Request for POST/PUT operations
- `BranchTableResponseDTO` - Table information for external clients

#### Internal DTOs (`dto/internal/`)

Used for inter-service communication (Order, Reservation, Menu services):

- `BranchDTO` - Branch information without nested collections
- `BranchTableDTO` - Table information with branchId reference

### 2. Services

#### External Service Interface (`service/api/BranchServiceApi`)

Contract exposed to other microservices:

- `getBranchById()` - Get branch details
- `getAllBranches()` - List all branches
- `branchExists()` - Validate branch existence
- `getAvailableTablesByBranchId()` - Get available tables for reservations
- `getTableById()` - Get table details
- `updateTableAvailability()` - Update table status

#### Implementation (`service/impl/BranchServiceApiImpl`)

Implements the external service API contract

#### Internal Services

- `BranchService` - Handles REST API requests (CRUD operations)
- `BranchTableService` - Handles table-related REST API requests

### 3. Controllers

#### Public REST API (`controller/BranchController`)

Endpoints for frontend/external clients:

- `GET /api/branches` - List all branches
- `GET /api/branches/{id}` - Get branch details
- `GET /api/branches/{id}/tables` - Get tables for a branch
- `POST /api/branches` - Create branch
- `PUT /api/branches/{id}` - Update branch
- `DELETE /api/branches/{id}` - Delete branch

#### Internal API (`controller/api/BranchInternalApiController`)

Endpoints for inter-service communication:

- `GET /internal/api/branches/{id}` - Get branch (for Order/Menu services)
- `GET /internal/api/branches` - List branches (for Admin service)
- `GET /internal/api/branches/{id}/exists` - Verify branch exists
- `GET /internal/api/branches/{branchId}/tables/available` - Get available tables (for Reservation)
- `GET /internal/api/branches/tables/{tableId}` - Get table details
- `PATCH /internal/api/branches/tables/{tableId}/availability` - Update table status

## Usage Examples

### External API (Frontend)

```javascript
// Get all branches
GET http://localhost:8080/api/branches

// Create a branch
POST http://localhost:8080/api/branches
{
  "name": "Downtown Branch",
  "address": "123 Main St",
  "phoneNumber": "+1-555-0101",
  "openingTime": "08:00:00",
  "closingTime": "22:00:00"
}
```

### Internal API (Other Microservices)

```java
// From Reservation Service - Check branch exists
GET http://branch-service:8080/internal/api/branches/1/exists

// From Reservation Service - Get available tables
GET http://branch-service:8080/internal/api/branches/1/tables/available

// From Reservation Service - Reserve a table
PATCH http://branch-service:8080/internal/api/branches/tables/5/availability?isAvailable=false
```

## Benefits

1. **Separation of Concerns**: External and internal APIs have different contracts
2. **Flexibility**: Can evolve external API independently from internal services
3. **Security**: Internal endpoints can be protected differently (API gateway, service mesh)
4. **Performance**: Internal DTOs are optimized for service-to-service communication
5. **Maintainability**: Clear boundaries between public and internal interfaces

## Future Integration

### Feign Client (for other services)

```java
@FeignClient(name = "branch-service", url = "http://branch-service:8080")
public interface BranchServiceClient {

    @GetMapping("/internal/api/branches/{id}")
    BranchDTO getBranchById(@PathVariable Long id);

    @GetMapping("/internal/api/branches/{branchId}/tables/available")
    List<BranchTableDTO> getAvailableTablesByBranchId(@PathVariable Long branchId);

    @PatchMapping("/internal/api/branches/tables/{tableId}/availability")
    void updateTableAvailability(@PathVariable Long tableId, @RequestParam Boolean isAvailable);
}
```

### Message Queue (Event-Driven)

The `BranchServiceApi` interface can also be implemented to publish/consume Kafka events:

- Branch created/updated events → Menu Service, Order Service
- Table availability changed → Reservation Service
