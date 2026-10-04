package kz.sdp.bridge.report;

import kz.sdp.bridge.formatter.ReportFormatter;

import java.util.List;

public class SalesReport extends Report {

    private final String period;
    private final List<SalesItem> items;

    public SalesReport(ReportFormatter formatter, String period, List<SalesItem> items) {
        super(formatter);
        this.period = period;
        this.items = List.copyOf(items);
    }

    @Override
    protected String title() {
        return "Sales Report - " + period;
    }

    @Override
    protected String body() {
        StringBuilder body = new StringBuilder();
        body.append(formatter.section("Summary"));
        body.append(formatter.keyValue("Positions sold", String.valueOf(items.size())));
        body.append(formatter.keyValue("Revenue", money(totalRevenue())));

        body.append(formatter.section("Details"));
        body.append(formatter.beginTable(List.of("Product", "Qty", "Unit price", "Total")));
        for (SalesItem item : items) {
            body.append(formatter.tableRow(List.of(
                    item.product(),
                    String.valueOf(item.quantity()),
                    money(item.unitPrice()),
                    money(item.total()))));
        }
        body.append(formatter.endTable());
        return body.toString();
    }

    private double totalRevenue() {
        return items.stream().mapToDouble(SalesItem::total).sum();
    }

    private String money(double amount) {
        return String.format("%.0f KZT", amount);
    }
}
