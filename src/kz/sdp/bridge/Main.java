package kz.sdp.bridge;

import kz.sdp.bridge.formatter.HtmlFormatter;
import kz.sdp.bridge.formatter.MarkdownFormatter;
import kz.sdp.bridge.formatter.PlainTextFormatter;
import kz.sdp.bridge.formatter.ReportFormatter;
import kz.sdp.bridge.report.InventoryReport;
import kz.sdp.bridge.report.Report;
import kz.sdp.bridge.report.SalesItem;
import kz.sdp.bridge.report.SalesReport;
import kz.sdp.bridge.report.StockItem;

import java.util.List;

/** Client: combines any Report with any ReportFormatter at runtime. */
public class Main {

    public static void main(String[] args) {
        Report sales = new SalesReport(new PlainTextFormatter(), "September 2026", List.of(
                new SalesItem("Laptop", 3, 450_000),
                new SalesItem("Mouse", 12, 7_500),
                new SalesItem("Monitor", 4, 120_000)));

        Report inventory = new InventoryReport(new PlainTextFormatter(), "Astana-1", List.of(
                new StockItem("Laptop", 5, 3),
                new StockItem("Mouse", 2, 10),
                new StockItem("Keyboard", 0, 5)));

        List<ReportFormatter> formatters = List.of(
                new PlainTextFormatter(), new MarkdownFormatter(), new HtmlFormatter());

        for (Report report : List.of(sales, inventory)) {
            for (ReportFormatter formatter : formatters) {
                report.setFormatter(formatter); // same report object, different implementation
                printHeader(report, formatter);
                System.out.println(report.generate());
            }
        }
    }

    private static void printHeader(Report report, ReportFormatter formatter) {
        System.out.printf("%n>>> %s rendered by %s%n%n",
                report.getClass().getSimpleName(), formatter.getClass().getSimpleName());
    }
}
