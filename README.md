# Assignment #3 — Bridge Pattern (Report Generator)

**Course:** Software Design Patterns
**Topic:** Business reports × output formats

## Idea

A company produces different **reports** (sales, inventory) and needs them in different **formats**
(plain text for the console, Markdown for GitHub/wiki, HTML for the browser).

Without Bridge this becomes a class explosion: `SalesTextReport`, `SalesHtmlReport`, `InventoryMarkdownReport`, …
— `M reports × N formats` classes. With Bridge we get `M + N` classes, and both hierarchies grow independently.

## Pattern roles

| Bridge role            | Class                                                    | Responsibility                                  |
|------------------------|----------------------------------------------------------|-------------------------------------------------|
| Abstraction            | `report.Report`                                          | Holds a `ReportFormatter` reference; defines `generate()` |
| Refined Abstraction    | `report.SalesReport`, `report.InventoryReport`           | Decide **what** data goes into the report       |
| Implementor            | `formatter.ReportFormatter` (interface)                  | Low-level primitives: title, section, key-value, table |
| Concrete Implementor   | `PlainTextFormatter`, `MarkdownFormatter`, `HtmlFormatter` | Decide **how** each primitive is rendered      |
| Client                 | `Main`                                                   | Combines reports and formatters at runtime, switches formatter via `setFormatter()` |

## UML

```
          Abstraction side                                Implementation side
 ┌──────────────────────────────┐   formatter    ┌──────────────────────────────────┐
 │ <<abstract>> Report          │◇──────────────▶│ <<interface>> ReportFormatter    │
 │ # formatter: ReportFormatter │                │ + beginDocument(title)           │
 │ + setFormatter(f)            │                │ + section(heading)               │
 │ + generate(): String {final} │                │ + keyValue(key, value)           │
 │ # title(): String            │                │ + beginTable(headers)            │
 │ # body(): String             │                │ + tableRow(cells)                │
 └──────────────▲───────────────┘                │ + endTable()                     │
        ┌───────┴────────┐                       │ + endDocument()                  │
 ┌──────┴──────┐ ┌───────┴────────┐              └───────────────▲──────────────────┘
 │ SalesReport │ │ InventoryReport│                ┌─────────────┼──────────────┐
 └─────────────┘ └────────────────┘     PlainTextFormatter MarkdownFormatter HtmlFormatter
```

## How to run

Requires JDK 17+.

```bash
javac -d out $(find src -name "*.java")
java -cp out kz.sdp.bridge.Main
```

Or open the folder in IntelliJ IDEA, mark `src` as Sources Root and run `Main`.

The demo prints each of the 2 reports in each of the 3 formats. The **same report object** is reused —
only `setFormatter(...)` is called, which demonstrates switching the implementation at runtime
without changing the abstraction.

## Clean Code principles

1. **Separation of abstraction-side vs. implementation-side responsibilities.**
   Reports never produce markup (`<td>`, `|`, `##`) — they only call `formatter.section(...)`, `formatter.tableRow(...)`.
   Formatters never know about revenue, stock or restock rules. Packages `report` and `formatter` mirror the two sides.
   The client never calls formatter methods directly — it only calls `report.generate()`.

2. **Meaningful, role-revealing names.**
   `Report` / `SalesReport` / `InventoryReport` — abstraction side (business meaning).
   `ReportFormatter` / `HtmlFormatter` / `MarkdownFormatter` — implementor side (presentation).
   Methods say what they do: `needsRestock()`, `countLowStock()`, `totalRevenue()`, `beginTable()`.

3. **Small, focused classes (Single Responsibility).**
   Every class is under ~60 lines and has one reason to change: a formatter changes only when its output format changes,
   a report changes only when its business content changes. Data is kept in tiny immutable records `SalesItem`, `StockItem`.

4. **No duplicated logic (DRY).**
   The document skeleton (header → body → footer) is written once in `Report.generate()` (declared `final`).
   Inside `HtmlFormatter` header and data rows share one private `row(cells, tag)` method;
   `MarkdownFormatter.beginTable` reuses `tableRow`. Business calculations live in the records, not in formatters.

5. **Open/Closed & backward compatibility.**
   Adding a new format (e.g. `CsvFormatter`) = one new class implementing `ReportFormatter`, **zero** changes in `Report`
   or its subclasses. Adding a new report (e.g. `PayrollReport`) = one new subclass, works with all existing formatters.

6. **Program to an interface + Dependency Injection.**
   `Report` depends on the `ReportFormatter` interface, not on concrete classes; the formatter is injected via the constructor
   or `setFormatter()`. `Objects.requireNonNull` fails fast on invalid input.

7. **Immutability & encapsulation.**
   Records are immutable, report data is defensively copied with `List.copyOf`, fields are `private final`,
   magic numbers are named constants (`COLUMN_WIDTH`).
