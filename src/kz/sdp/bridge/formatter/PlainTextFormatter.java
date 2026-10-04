package kz.sdp.bridge.formatter;

import java.util.List;

public class PlainTextFormatter implements ReportFormatter {

    private static final int COLUMN_WIDTH = 14;

    @Override
    public String beginDocument(String title) {
        String border = "=".repeat(title.length());
        return border + "\n" + title.toUpperCase() + "\n" + border + "\n";
    }

    @Override
    public String section(String heading) {
        return "\n" + heading + "\n" + "-".repeat(heading.length()) + "\n";
    }

    @Override
    public String keyValue(String key, String value) {
        return key + ": " + value + "\n";
    }

    @Override
    public String beginTable(List<String> headers) {
        return tableRow(headers) + "-".repeat(COLUMN_WIDTH * headers.size()) + "\n";
    }

    @Override
    public String tableRow(List<String> cells) {
        StringBuilder row = new StringBuilder();
        for (String cell : cells) {
            row.append(String.format("%-" + COLUMN_WIDTH + "s", cell));
        }
        return row.toString().stripTrailing() + "\n";
    }

    @Override
    public String endTable() {
        return "";
    }

    @Override
    public String endDocument() {
        return "";
    }
}
