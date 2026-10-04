package kz.sdp.bridge.report;

import kz.sdp.bridge.formatter.ReportFormatter;

import java.util.Objects;

/**
 * Abstraction: a business report.
 * Decides WHAT goes into the document and delegates HOW it looks to the formatter (the bridge).
 */
public abstract class Report {

    protected ReportFormatter formatter;

    protected Report(ReportFormatter formatter) {
        setFormatter(formatter);
    }

    public void setFormatter(ReportFormatter formatter) {
        this.formatter = Objects.requireNonNull(formatter, "formatter must not be null");
    }

    /** Same skeleton for every report: header, report-specific body, footer. */
    public final String generate() {
        return formatter.beginDocument(title())
                + body()
                + formatter.endDocument();
    }

    protected abstract String title();

    protected abstract String body();
}
