package view.tm;

public class DailySalesTM {
    private String productID;
    private String name;
    private String category;
    private double unitPrice;
    private int qty;
    private double discount;
    private double totPrice;

    public DailySalesTM() {
    }

    public DailySalesTM(String productID, String name, String category, double unitPrice, int qty, double discount, double totPrice) {
        this.productID = productID;
        this.name = name;
        this.category = category;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.discount = discount;
        this.totPrice = totPrice;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotPrice() {
        return totPrice;
    }

    public void setTotPrice(double totPrice) {
        this.totPrice = totPrice;
    }
}
