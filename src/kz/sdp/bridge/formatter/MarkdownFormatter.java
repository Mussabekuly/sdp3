package kz.sdp.bridge.formatter;

import java.util.List;

public class MarkdownFormatter implements ReportFormatter {

    @Override
    public String beginDocument(String title) {
        return "# " + title + "\n";
    }

    @Override
    public String section(String heading) {
        return "\n## " + heading + "\n\n";
    }

    @Override
    public String keyValue(String key, String value) {
        return "- **" + key + "**: " + value + "\n";
    }

    @Override
    public String beginTable(List<String> headers) {
        List<String> separators = headers.stream().map(header -> "---").toList();
        return tableRow(headers) + tableRow(separators);
    }

    @Override
    public String tableRow(List<String> cells) {
        return "| " + String.join(" | ", cells) + " |\n";
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
