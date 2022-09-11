package controller;

import controller.model_Controller.ProductController;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.OrderDetail;
import model.Product;

import java.sql.SQLException;

public class Product_OrderReportsFormController {
    public HBox productContext;
    public ImageView productImage;
    public Label lblName;
    public Label lblSize;
    public Label lblColor;
    public Label lblBrand;
    public Label lblCategory;
    public Label lblUnitPrice;
    public Label lblQty;
    public Label lblSubTotal;
    public Label lblDisc;
    public Label lblTotalPrice;

    public void setData(OrderDetail orderDetail) {
        Product product = null;
        try {
            product = new ProductController().getProduct(orderDetail.getProductID());
            lblName.setText(product.getName());
            lblSize.setText(product.getSize());
            lblColor.setText(product.getColorCode());

                ProductController productController = new ProductController();
                lblBrand.setText(productController.getBrandName(product.getBrandID()));
                lblCategory.setText(productController.getSubCatName(product.getSubCategoryID())
                        + ", " + productController.getCategoryName(product.getCategoryID()));
                productController = null;

            lblQty.setText(String.valueOf(orderDetail.getProductQTY()));
            lblDisc.setText(orderDetail.getDiscount()+"%");
            lblTotalPrice.setText(String.valueOf(orderDetail.getTotalPrice()));

            Image image = new Image(getClass().getResourceAsStream(("../assets/product_images/" + product.getImageSrc())));
            productImage.setImage(image);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


    }
}
