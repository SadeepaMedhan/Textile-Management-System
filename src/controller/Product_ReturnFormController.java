package controller;

import controller.model_Controller.ProductController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Product;
import model.ReturnDetail;
import model.Returns;
import util.ProductDetailsLoadEvent;

import java.sql.SQLException;

public class Product_ReturnFormController {
    public Label lblName;
    public Label lblSize;
    public Label lblColor;
    public Label lblBrand;
    public Label lblCategory;
    public Label lblQty;
    public Label lblUnitPrice;
    public Label lblDescription;
    public Button btnViewMore;
    public ImageView imageView;
    private String returnID;

    private ProductDetailsLoadEvent event;

    public void viewMoreOnAction(ActionEvent actionEvent) {
        event.loadReturnData(returnID);
    }

    public void setData(ReturnDetail returns)  {
        returnID=returns.getProductNo();
        try {
            Product product = new ProductController().getProduct(returns.getProductNo());
            lblName.setText(product.getName());
            lblSize.setText(product.getSize());
            lblColor.setText(product.getColorCode());
            lblUnitPrice.setText(String.valueOf(product.getUnitPrice()));
            lblDescription.setText(returns.getDescription());
            lblQty.setText(String.valueOf(returns.getQty()));

            ProductController productController = new ProductController();
            lblBrand.setText(productController.getBrandName(product.getBrandID()));
            lblCategory.setText(productController.getSubCatName(product.getSubCategoryID())
                    + ", " + productController.getCategoryName(product.getCategoryID()));
            productController = null;

            Image image = new Image(getClass().getResourceAsStream(("../assets/product_images/" + product.getImageSrc())));
            imageView.setImage(image);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setEvent(ProductDetailsLoadEvent event) {
        this.event=event;
    }
}
