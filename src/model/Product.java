package model;

public class Product {
    private String productID;
    private String name;
    private String material;
    private String size;
    private String colorCode;
    private double unitPrice;
    private int qtyOnHand;
    private String brandID;
    private String categoryID;
    private String subCategoryID;
    private String imageSrc;

    public Product() {
    }

    public Product(String productID, String name, String material, String size, String colorCode, double unitPrice, int qtyOnHand, String brandID, String categoryID, String subCategoryID, String imageSrc) {
        this.productID = productID;
        this.material = material;
        this.setName(name);
        this.size = size;
        this.colorCode = colorCode;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.brandID = brandID;
        this.categoryID = categoryID;
        this.subCategoryID = subCategoryID;
        this.imageSrc = imageSrc;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
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

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
