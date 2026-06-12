# TP4 — Sistema de Análisis de Transacciones Bancarias

Trabajo Práctico N°4 — Programación III  
Universidad Nacional de La Rioja (UNLaR)

---

## Descripción

Aplicación de consola desarrollada con Spring Boot 3.5.3 y Java 21 que implementa:
- Algoritmos de búsqueda lineal O(n) y binaria O(log n) con medición empírica de performance.
- Comparación de algoritmos de ordenamiento: Bubble Sort O(n²) vs TimSort O(n log n).
- Refactorización de código legacy aplicando principios SOLID (SRP, OCP, DIP).
- Inyección de dependencias por constructor con Spring Boot.
- Patrón Strategy en la capa de filtrado.
- Records de Java 21 como DTOs inmutables.

---

## Estructura del proyecto

```
src/main/java/unlar/edu/ar/Tp4_Sbancario/
├── inicial/                        # Código legacy con violaciones SOLID
│   ├── Transaction.java            # Modelo con campos públicos (sin encapsulamiento)
│   ├── TransactionProcessor.java   # God Object: búsqueda, ordenamiento, filtrado e I/O
│   └── Main.java                   # Runner del benchmark legacy
├── model/                          # Entidades refactorizadas
│   ├── TransactionType.java        # Enum con type-safety en compilación
│   ├── Transaction.java            # Clase inmutable con Lombok
│   └── SearchResult.java           # Record Java 21
├── service/                        # Capa de servicios (interfaces + implementaciones)
│   ├── SearchService.java          # Contrato de búsqueda
│   ├── SearchServiceImpl.java      # Búsqueda lineal y binaria con métricas
│   ├── SortService.java            # Contrato de ordenamiento + SortResult record
│   ├── SortServiceImpl.java        # Bubble Sort y TimSort con métricas
│   ├── FilterService.java          # Contrato de filtrado + FiltroPredicate
│   └── FilterServiceImpl.java      # Filtros encadenados con Stream API
├── report/
│   └── PerformanceReport.java      # Generador del reporte comparativo
├── config/
│   └── AppConfig.java              # Configuración Spring Boot
├── MainRefactorizado.java          # CommandLineRunner — punto de entrada
└── Tp4SbancarioApplication.java    # Clase principal Spring Boot
```

---

## Tecnologías

- Java 21
- Spring Boot 3.5.3
- Lombok
- Maven 3.9.16
- JUnit 5

---

## Ejecución

```bash
# Compilar
mvn clean compile

# Ejecutar reporte de performance
mvn spring-boot:run
```

---

## Principios SOLID aplicados

**SRP:** cada servicio tiene una única responsabilidad: `SearchService` busca, `SortService` ordena, `FilterService` filtra, `PerformanceReport` genera el reporte.

**OCP:** `FilterService` aplica el patrón Strategy mediante la interfaz funcional `FiltroPredicate`. Agregar un nuevo criterio de filtrado no requiere modificar código existente.

**DIP:** `MainRefactorizado` y `PerformanceReport` dependen de interfaces (`SearchService`, `SortService`, `FilterService`), nunca de implementaciones concretas. Spring inyecta las implementaciones por constructor.

