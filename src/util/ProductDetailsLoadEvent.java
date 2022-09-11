package util;

import model.OrderDetail;
import model.Product;
import model.ProductDetail;

public interface ProductDetailsLoadEvent {
    void loadData(String id);
    void refreshDetails();
    void removeProduct(ProductDetail product);
    void setOrderDetail(String productID, int qty, double subTotal, double discount, double total);
    void loadReturnData(String id);
}
