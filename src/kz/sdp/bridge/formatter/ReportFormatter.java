package kz.sdp.bridge.formatter;

import java.util.List;

/**
 * Implementor: low-level rendering primitives.
 * Knows HOW to draw a document, but nothing about WHAT a report contains.
 */
public interface ReportFormatter {

    String beginDocument(String title);

    String section(String heading);

    String keyValue(String key, String value);

    String beginTable(List<String> headers);

    String tableRow(List<String> cells);

    String endTable();

    String endDocument();
}
