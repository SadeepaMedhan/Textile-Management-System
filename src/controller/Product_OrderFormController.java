package controller;

import controller.model_Controller.ProductController;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.OrderDetail;
import model.Product;
import model.ProductDetail;
import util.ProductDetailsLoadEvent;

import java.sql.SQLException;
import java.util.Optional;

public class Product_OrderFormController {
    public HBox productContext;

    public Label lblName;
    public Label lblSize;
    public Label lblColor;
    public Label lblBrand;
    public Label lblCategory;
    public Label lblUnitPrice;
    public Label lblTotalPrice;
    public TextField txtQty;
    public Label lblSubTotal;
    public TextField txtDiscount;
    public ImageView productImage;
    private ProductDetail product;
    private int qty = 1;

    private ProductDetailsLoadEvent event;


    public void setData(ProductDetail product) {
        this.product = product;
        lblName.setText(product.getName());
        lblSize.setText(product.getSize());
        lblColor.setText(product.getColor());
        try {
            ProductController productController = new ProductController();
            lblBrand.setText(productController.getBrandName(product.getBrandID()));
            lblCategory.setText(productController.getSubCatName(product.getSubCategoryID())
                    + ", " + productController.getCategoryName(product.getCategoryID()));
            productController = null;

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        lblUnitPrice.setText(String.valueOf(product.getUnitPrice()));
        txtQty.setText(String.valueOf(product.getProductQTY()));
        txtDiscount.setText(String.valueOf(product.getDiscount()));
        Image image = new Image(getClass().getResourceAsStream(("../assets/product_images/" + product.getImageSrc())));
        productImage.setImage(image);

        setPrices();
    }

    private void setPrices() {


        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        qty = Integer.parseInt(txtQty.getText());
        double subTotal = unitPrice * qty;
        lblSubTotal.setText(String.valueOf(subTotal));

        double discount = Double.parseDouble(txtDiscount.getText());
        double total = subTotal - (discount * unitPrice) / 100;
        lblTotalPrice.setText(String.valueOf(discount > 0.0 ? total : subTotal));


       event.setOrderDetail(product.getProductID(), qty, subTotal, discount, total);

    }

    public void minus_clickEvent(MouseEvent mouseEvent) {
        if (qty > 1) {
            qty--;
            txtQty.setText(String.valueOf(qty));
            setPrices();
        } else {
            System.out.println("qty err");
        }
    }

    public void plus_clickEvent(MouseEvent mouseEvent) {
        if (qty < product.getQtyOnHand()) {
            qty++;
            txtQty.setText(String.valueOf(qty));
            setPrices();
        } else {
            System.out.println("qty err");
        }
    }

    public void edit_onAction(ActionEvent actionEvent) {
        event.loadData(product.getProductID());
    }

    public void remove_onAction(ActionEvent actionEvent) {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure whether you want to delete this Product ?", yes, no);
        alert.setTitle("Confirmation Alert");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(no) == yes) {
            event.removeProduct(product);
        } else {
        }
    }

    public void setEvent(HomePageController event) {
        this.event = event;
    }

    public void qty_keyRelease(KeyEvent keyEvent) {
        if(!txtQty.getText().equals("")) {
            qty = Integer.parseInt(txtQty.getText());
            if (qty > 1 && qty <= product.getQtyOnHand()) {
                setPrices();
            } else {
                System.out.println("qty err");
            }
        }else{
            txtQty.setText(String.valueOf(1));
        }
    }

    public void discount_keyReleased(KeyEvent keyEvent) {
        if(!txtDiscount.getText().equals("")) {
            int discount = Integer.parseInt(txtDiscount.getText());
            if (discount >= 0 && discount < 100) {
                setPrices();
            } else {
                System.out.println("discount err");
            }
        }else{
            txtDiscount.setText("00");
        }
    }


}
