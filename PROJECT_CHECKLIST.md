# Logistics Network Project Checklist

## Overall Status

- Application code: Complete
- Submission evidence: Incomplete
- Security status: Action required

## Ground Rules

- [x] Shared BaseClass with id, isActive, createdDate, and updatedDate
- [x] All 16 entities extend BaseClass
- [x] Read operations use active-only repository queries
- [x] Delete operations use soft delete
- [x] Create and update controllers use @RequestBody with @Valid
- [x] Controllers return DTOs instead of raw entities
- [x] DTO validation sizes match entity column sizes
- [x] Debugging log is included
- [x] Cyclic JSON relationships are protected
- [x] Work is separated into frequent Git commits
- [ ] Remove the database password from application.properties
- [ ] Rotate the exposed MySQL password
- [ ] Consider removing the credential from Git history before final submission

## Enum Type Safety

- [x] ShipmentStatus
- [x] VehicleStatus
- [x] DriverStatus
- [x] RouteStatus
- [x] DeliveryStopStatus
- [x] TrackingStatus
- [x] InvoiceStatus
- [x] CustomerType
- [x] VehicleType
- [x] StaffRole
- [x] Entity enums use EnumType.STRING
- [x] DTOs use enum types
- [x] Business logic compares enum constants
- [x] Invalid enum JSON is handled as 400 Bad Request
- [x] Product category remains a flexible String

## Phase 1 - Entities and Relationships

- [x] 16 required entities created
- [x] BaseClass created
- [x] Warehouse relationships mapped
- [x] Product relationships mapped
- [x] Shipment to ShipmentItem to Product chain mapped
- [x] Route to DeliveryStop to Shipment chain mapped
- [x] Foreign-key relationships implemented
- [x] Cyclic JSON protection added
- [x] Hibernate schema generated locally
- [x] Add a screenshot of the generated schema to the repository

## Phase 2 - Repositories, Services, and CRUD

- [x] 16 JpaRepository interfaces
- [x] Active-only get-all query in every repository
- [x] Active-only get-by-id query in every repository
- [x] 16 entity services
- [x] Create operation for every entity
- [x] Get-all operation for every entity
- [x] Get-by-id operation for every entity
- [x] Update operation for every entity
- [x] Soft-delete operation for every entity
- [x] 16 CRUD controllers
- [x] Five CRUD mappings in every entity controller
- [x] Phase 2 Postman collection with 80 requests
- [ ] Run and save successful results for the complete Phase 2 collection

## Phase 3 - DTOs and Conversion

- [x] One main DTO for every entity
- [x] Lombok Builder pattern
- [x] Lombok Data
- [x] Lombok NoArgsConstructor
- [x] Entity-to-DTO conversion for every entity
- [x] List-to-DTO conversion for every entity
- [x] DTO-to-entity conversion for request handling
- [x] Relationship IDs replace nested entity responses
- [x] Controllers do not import or return entity classes
- [x] Phase 3 DTO Postman collection with 80 requests
- [ ] Run and save successful results for the complete Phase 3 collection

## Phase 4 - Business Operations

- [x] Create shipment with items
- [x] Decrement warehouse inventory
- [x] Reject insufficient inventory
- [x] Assign shipment to carrier
- [x] Build route with vehicle and driver
- [x] Reject unavailable vehicle or driver
- [x] Reject vehicle over-capacity
- [x] Add delivery stops in sequence
- [x] Reject duplicate stop sequence
- [x] Append tracking events
- [x] Update shipment status from tracking events
- [x] Complete delivery stops
- [x] Complete route automatically when all stops are complete
- [x] Generate invoice only for delivered shipment
- [x] List unpaid invoices for a customer
- [x] Shipments-by-status query
- [x] Low-stock inventory query
- [x] Driver routes by date query
- [x] Available vehicles query
- [x] Customer shipment-history query
- [x] Warehouse statistics
- [x] Carrier statistics
- [x] Customer statistics
- [x] 16 custom repository queries
- [x] 16 business-operation endpoints
- [x] Phase 4 Postman collection with 22 requests
- [ ] Run successful and rejection cases and save the results

## Phase 5 - Validation and Exceptions

- [x] Spring Boot validation dependency
- [x] Validation constraints on CRUD request DTOs
- [x] Validation constraints on operation request DTOs
- [x] ResourceNotFoundException
- [x] Record-based ErrorResponse
- [x] Global RestControllerAdvice
- [x] Validation error handler
- [x] Resource-not-found handler
- [x] Illegal-business-rule handler
- [x] Malformed JSON handler
- [x] Request-parameter handler
- [x] Database-constraint handler
- [x] Generic fallback handler
- [x] Not-found service cases return 404
- [x] Invalid request and business-rule cases return 400
- [x] Unexpected failures return 500
- [x] Phase 5 Postman collection with 12 automated error checks
- [ ] Run all Phase 5 error requests and save their results

## Deliverables

- [x] Source code on GitHub
- [x] Entities package
- [x] DTO package
- [x] Repositories package
- [x] Services package
- [x] Controllers package
- [x] Exceptions package
- [x] Four Postman collections
- [x] Debugging log
- [x] Database schema screenshot: [docs/database-schema.png](docs/database-schema.png)
- [ ] Evidence that all Postman collections pass
- [ ] Clean with a clean Maven build in a network-enabled environment
- [ ] Credential-security actions completed

## Postman Request Counts

| Collection | Requests |
|---|---:|
| Phase 2 CRUD | 80 |
| Phase 3 DTO CRUD | 80 |
| Phase 4 Operations | 22 |
| Phase 5 Error Cases | 12 |
| Total | 194 |

## Final Submission Blockers

1. Rotate the MySQL password because it is present in a public Git repository.
2. Replace the committed password with an environment variable.
3. Run all Postman collections and save evidence of the results.
4. Run a clean Maven build locally.
