package kz.sdp.bridge.formatter;

import java.util.List;

public class HtmlFormatter implements ReportFormatter {

    @Override
    public String beginDocument(String title) {
        return "<html><head><title>" + escape(title) + "</title></head><body>\n"
                + "<h1>" + escape(title) + "</h1>\n";
    }

    @Override
    public String section(String heading) {
        return "<h2>" + escape(heading) + "</h2>\n";
    }

    @Override
    public String keyValue(String key, String value) {
        return "<p><b>" + escape(key) + ":</b> " + escape(value) + "</p>\n";
    }

    @Override
    public String beginTable(List<String> headers) {
        return "<table border=\"1\">\n" + row(headers, "th");
    }

    @Override
    public String tableRow(List<String> cells) {
        return row(cells, "td");
    }

    @Override
    public String endTable() {
        return "</table>\n";
    }

    private String row(List<String> cells, String cellTag) {
        StringBuilder row = new StringBuilder("<tr>");
        for (String cell : cells) {
            row.append("<").append(cellTag).append(">")
                    .append(escape(cell))
                    .append("</").append(cellTag).append(">");
        }
        return row.append("</tr>\n").toString();
    }

    @Override
    public String endDocument() {
        return "</body></html>\n";
    }

    private String escape(String text) {
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
