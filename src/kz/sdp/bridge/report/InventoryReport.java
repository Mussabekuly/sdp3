package kz.sdp.bridge.report;

import kz.sdp.bridge.formatter.ReportFormatter;

import java.util.List;

public class InventoryReport extends Report {

    private final String warehouse;
    private final List<StockItem> stock;

    public InventoryReport(ReportFormatter formatter, String warehouse, List<StockItem> stock) {
        super(formatter);
        this.warehouse = warehouse;
        this.stock = List.copyOf(stock);
    }

    @Override
    protected String title() {
        return "Inventory Report - " + warehouse;
    }

    @Override
    protected String body() {
        StringBuilder body = new StringBuilder();
        body.append(formatter.section("Overview"));
        body.append(formatter.keyValue("Products tracked", String.valueOf(stock.size())));
        body.append(formatter.keyValue("Need restock", String.valueOf(countLowStock())));

        body.append(formatter.section("Stock levels"));
        body.append(formatter.beginTable(List.of("Product", "In stock", "Minimum", "Status")));
        for (StockItem item : stock) {
            body.append(formatter.tableRow(List.of(
                    item.product(),
                    String.valueOf(item.inStock()),
                    String.valueOf(item.minimumLevel()),
                    item.needsRestock() ? "RESTOCK" : "OK")));
        }
        body.append(formatter.endTable());
        return body.toString();
    }

    private long countLowStock() {
        return stock.stream().filter(StockItem::needsRestock).count();
    }
}
