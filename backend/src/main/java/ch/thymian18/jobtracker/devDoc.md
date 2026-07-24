# Milestone 1: Data Model
The overall database structure was defined and the corresponding entities were created in `/entity`.  

For each entity, a Java interface implementing JpaRepository<Entity,Long> was added to `/repository`.  
Those automatically get `findById`, `findAll`, `save`, `delete`, etc. and custom methods without needing to write SQL. Spring Data JPA automatically generates SQL from the method names.

`StatusHistory` has `cascade = CascadeType.ALL` + `orphanRemoval = true` on the `Application` page because a history entry does not make sense without any Application.


# Milestone 2: REST API
HTTP end-points to interact with the data.

3 layers:  
Controller (Http-layer) -> Service (logic) -> repository (data access) -> database

DTOs in `/dto` contain Java Records that define what is coming in and going out through the API. Entities never go out directly, but always as DTOs. The reason for this is that we don't want the API to be coupled with the DB too much.

The business logic is contained in `/service`.

Controllers in `/controller` are thin and delegate to services. They set HTTP status codes.


# Milestone 3: Spring Security + JWT
Only logged-in users should be allowed to access the API.

JWT works as follows:
1. User registers, password is saved in hashed format (BCrypt)
2. User logs in, server checks credentials and returns JWT token
3. With every subsequent request, the client sends the token along in the header (`Authorization: Bearer <token>`)
4. The filter validates the token before the request reaches the controller