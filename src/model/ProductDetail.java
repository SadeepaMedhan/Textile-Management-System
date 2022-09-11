package model;

public class ProductDetail {
    private String productID;
    private String name;
    private String material;
    private String size;
    private String color;
    private double unitPrice;
    private int qtyOnHand;
    private String brandID;
    private String categoryID;
    private String subCategoryID;
    private String imageSrc;
    private int productQTY;
    private double subTotal;
    private double discount;
    private double totalPrice;

    public ProductDetail() {
    }

    public ProductDetail(String productID, String name, String material, String size, String color, double unitPrice, int qtyOnHand, String brandID, String categoryID, String subCategoryID, String imageSrc, int productQTY, double subTotal, double discount, double totalPrice) {
        this.productID = productID;
        this.name = name;
        this.material = material;
        this.size = size;
        this.color = color;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.brandID = brandID;
        this.categoryID = categoryID;
        this.subCategoryID = subCategoryID;
        this.imageSrc = imageSrc;
        this.productQTY = productQTY;
        this.subTotal = subTotal;
        this.discount = discount;
        this.totalPrice = totalPrice;
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

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getSubCategoryID() {
        return subCategoryID;
    }

    public void setSubCategoryID(String subCategoryID) {
        this.subCategoryID = subCategoryID;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public int getProductQTY() {
        return productQTY;
    }

    public void setProductQTY(int productQTY) {
        this.productQTY = productQTY;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
