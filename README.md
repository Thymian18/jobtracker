# Job Application Tracker

Ein persönliches Tool zum Verwalten von Bewerbungen, Firmen und Kontaktpersonen während der Jobsuche, mit automatischem Status-Tracking und (geplant) LLM-gestütztem Matching zwischen Bewerber-Profil und Stellenausschreibung.

## Motivation

Entstanden während meiner eigenen Jobsuche nach Abschluss meines Informatik Masters an der EPFL. Statt Excel-Listen oder Notion Boards zu nutzen, baue ich das Tool, das ich selbst während der Bewerbungsphase brauche. Gleichzeitig nutze ich es, um mein Java/Spring-Wissen nach einer mehrjährigen Pause wieder aufzufrischen und auf den aktuellen Stand zu bringen.

## Tech-Stack

**Backend (aktuell umgesetzt):**
- Java 21
- Spring Boot 3 (Spring Web, Spring Data JPA, Validation)
- Hibernate / JPA
- PostgreSQL (lokal via Docker)
- Maven
- Lombok

**Geplant:**
- Spring Security + JWT (Authentifizierung)
- LLM-Integration (Anthropic API) für automatisches Matching von Job-Postings gegen das eigene Profil
- Angular-Frontend
- Testing: JUnit 5, Mockito, Testcontainers
- Docker, CI/CD via GitHub Actions
- Deployment auf Azure (App Service + Azure Database for PostgreSQL)

## Architektur

Klassische Schichtenarchitektur:

```
Controller  →  Service  →  Repository  →  Datenbank
 (HTTP)       (Business-    (Datenzugriff)
               Logik)
```

DTOs (als Java Records) entkoppeln die API-Schicht bewusst von den JPA-Entities — nach aussen werden nie Entities direkt exponiert.

**Datenmodell:**
- `Company` — Firma, zu der man sich bewirbt
- `Contact` — Ansprechperson bei einer Firma
- `Application` — eine konkrete Bewerbung, verknüpft mit einer `Company`
- `StatusHistory` — automatisch geführter Verlauf aller Statusänderungen einer `Application`

## Features

- Vollständige CRUD-Verwaltung für Companies, Contacts und Applications
- Automatisches Status-Tracking: jede Statusänderung einer Bewerbung wird in `StatusHistory` protokolliert
- Bewusste Trennung zwischen allgemeinem Update (`PUT /applications/{id}`) und gezielter Statusänderung (`PATCH /applications/{id}/status`) — nur Statusänderungen erzeugen einen neuen History-Eintrag
- Globales Exception-Handling mit sauberen, strukturierten Fehler-Responses (z.B. `404` statt internem Stacktrace)

## API-Endpoints (Auszug)

| Methode | Pfad | Beschreibung |
|---|---|---|
| GET | `/companies` | Alle Firmen abrufen |
| GET | `/companies/{id}` | Einzelne Firma abrufen |
| POST | `/companies` | Firma erstellen |
| PUT | `/companies/{id}` | Firma aktualisieren |
| DELETE | `/companies/{id}` | Firma löschen |
| GET | `/contacts` | Alle Kontakte abrufen |
| POST | `/contacts` | Kontakt erstellen (mit `companyId`) |
| GET | `/applications` | Alle Bewerbungen abrufen (inkl. Status-Verlauf) |
| POST | `/applications` | Bewerbung erstellen (Status automatisch `APPLIED`) |
| PUT | `/applications/{id}` | Bewerbung aktualisieren (ohne Statusänderung) |
| PATCH | `/applications/{id}/status` | Status gezielt ändern (erzeugt History-Eintrag) |
| DELETE | `/applications/{id}` | Bewerbung löschen |

Analoge Endpoints existieren für `Contact` und folgen demselben Muster.

## Setup

### Voraussetzungen
- Java 21
- Maven
- Docker Desktop

### Lokale Datenbank starten

```bash
docker compose up -d
```

Startet einen PostgreSQL-Container (siehe `docker-compose.yml`).

### Anwendung starten

Über die IDE (z.B. IntelliJ) die Main-Klasse starten, oder via Maven:

```bash
./mvnw spring-boot:run
```

Die App läuft danach auf `http://localhost:8080`.

### Konfiguration

Die Datenbankverbindung wird in `src/main/resources/application.yml` konfiguriert. Für die lokale Entwicklung ist `spring.jpa.hibernate.ddl-auto: update` gesetzt — Hibernate legt Tabellen automatisch anhand der Entities an. Für eine echte Produktivumgebung würde stattdessen ein Migrationstool (z.B. Flyway) zum Einsatz kommen.

## Design-Entscheidungen (Auswahl)

- **Records statt Klassen für DTOs**: unveränderliche Datenhalter ohne Hibernate-Constraints, im Gegensatz zu Entities, die veränderlich sein müssen.
- **`EnumType.STRING` statt `ORDINAL`** für den `ApplicationStatus`-Enum: verhindert, dass sich gespeicherte Werte verschieben, falls die Enum-Reihenfolge später geändert wird.
- **Fremdschlüssel als ID statt verschachtelter Entity im DTO**: vermeidet zirkuläre Serialisierung und entkoppelt API-Vertrag von der internen Datenstruktur.
- **`StatusHistory` ohne eigene Endpoints**: kein eigenständiges Konzept, sondern ein automatisch geführtes Protokoll, das nur über Statusänderungen an `Application` entsteht.

## Roadmap

- [x] Datenmodell & Repositories
- [x] REST-API mit DTOs, Services, globalem Exception-Handling
- [ ] Spring Security + JWT-Authentifizierung
- [ ] LLM-Integration für Job-Posting-Analyse
- [ ] Angular-Frontend
- [ ] Tests (Unit & Integration mit Testcontainers)
- [ ] Docker & CI/CD
- [ ] Deployment auf Azure