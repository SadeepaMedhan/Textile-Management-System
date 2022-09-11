package controller;

import controller.model_Controller.ProductController;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.Product;
import util.ProductDetailsLoadEvent;

import java.sql.SQLException;
import java.util.Optional;

public class Product_ManageFormController {

    public HBox productContext;
    public ImageView imageView;
    public Label lblName;
    public Label lblSize;
    public Label lblColor;
    public Label lblBrand;
    public Label lblCategory;
    public Label lblQty;
    public Label lblPrice;
    private String currentProductID;

    private ProductDetailsLoadEvent event;


    public void setData(Product product) {

        currentProductID = product.getProductID();
        lblName.setText(product.getName());
        lblSize.setText(product.getSize());
        lblColor.setText(product.getColorCode());
        try {
            ProductController productController = new ProductController();
            lblBrand.setText(productController.getBrandName(product.getBrandID()));
            lblCategory.setText(productController.getSubCatName(product.getSubCategoryID())
                    + ", " + productController.getCategoryName(product.getCategoryID()));
            productController = null;

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        lblQty.setText(String.valueOf(product.getQtyOnHand()));
        lblPrice.setText(String.valueOf(product.getUnitPrice()));

        Image image = new Image(getClass().getResourceAsStream(("../assets/product_images/" + product.getImageSrc())));
        imageView.setImage(image);
    }

    public void details_onAction(ActionEvent actionEvent) {
    }

    public void edit_onAction(ActionEvent actionEvent) {
        event.loadData(currentProductID);
    }

    public void remove_onAction(ActionEvent actionEvent) {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure whether you want to delete this Product ?", yes, no);
        alert.setTitle("Confirmation Alert");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(no) == yes) {
            try {
                if (new ProductController().deleteProduct(currentProductID)) {
                    event.refreshDetails();
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }

        } else {
        }
    }

    public void setEvent(ProductDetailsLoadEvent event) {
        this.event = event;
    }
}
