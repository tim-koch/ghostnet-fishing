# Ghost Net Fishing – Fallstudie IPWA02-01

Dies ist das öffentliche Repository für den Prototyp zur Fallstudie im Kurs IPWA02-01.

## Start des Projekts

Voraussetzungen:
- Java 17 oder höher
- Maven
- VS Code oder IntelliJ

Starten:

mvn spring-boot:run


Das Projekt läuft anschließend unter:
http://localhost:8080/

## Architektur

- Spring Boot 4
- Spring MVC + Thymeleaf
- JPA / Hibernate
- H2 In-Memory-Datenbank

## Funktionen

- Geisternetz melden (anonym / namentlich)
- Offene Geisternetze einsehen
- Bergung übernehmen
- Statusänderungen (Geborgen / Verschollen)
