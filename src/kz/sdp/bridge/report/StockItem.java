package kz.sdp.bridge.report;

public record StockItem(String product, int inStock, int minimumLevel) {

    public boolean needsRestock() {
        return inStock < minimumLevel;
    }
}
