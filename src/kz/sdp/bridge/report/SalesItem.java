package kz.sdp.bridge.report;

public record SalesItem(String product, int quantity, double unitPrice) {

    public double total() {
        return quantity * unitPrice;
    }
}
