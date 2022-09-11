package model;

public class ReturnDetail {
    private String receiptNo;
    private String productNo;
    private int qty;
    private String description;

    public ReturnDetail() {
    }

    public ReturnDetail(String receiptNo, String productNo, int qty, String description) {
        this.receiptNo = receiptNo;
        this.productNo = productNo;
        this.qty = qty;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
