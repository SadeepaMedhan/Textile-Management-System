package controller;

import controller.model_Controller.ProductController;
import controller.model_Controller.ReturnController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Product;
import model.ProductDetail;
import model.ReturnDetail;
import util.ProductDetailsLoadEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public class ReturnProductsFormController implements ProductDetailsLoadEvent {
    public StackPane returnOrdersContext;
    public Label lblQtyProduct;
    public VBox allProductsContext;
    public Label lblProductID;
    public Label lblPName;
    public Label lblPCategory;
    public Label lblQty;
    public Label lblUnitPrice;
    public Label lblNoOfDamageProduct;
    public Label lblNoOfSizeChangeProduct;

    public void initialize() {

        try {
            lblNoOfDamageProduct.setText(String.valueOf(new ReturnController().getCount("Damage")));
            lblNoOfSizeChangeProduct.setText(String.valueOf(new ReturnController().getCount("Size varies")));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        try {
            loadReturnProduct(new ReturnController().getAllReturnDetails());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }


    private void loadReturnProduct(ArrayList<ReturnDetail> returnsDetails) {
        allProductsContext.getChildren().clear();

        ArrayList<ReturnDetail> allReturns = returnsDetails;

        try {
            for (int i = 0; i < allReturns.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../view/Product_ReturnForm.fxml"));

                HBox hBox = fxmlLoader.load();
                Product_ReturnFormController controller = fxmlLoader.getController();
                controller.setData(allReturns.get(i));
                controller.setEvent(this);
                allProductsContext.getChildren().add(hBox);
                lblQtyProduct.setText(String.valueOf(i+1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void loadData(String id) { }

    @Override
    public void refreshDetails() {}

    @Override
    public void removeProduct(ProductDetail product) {}

    @Override
    public void setOrderDetail(String productID, int qty, double subTotal, double discount, double total) { }

    @Override
    public void loadReturnData(String id) {
        try {
            ReturnDetail returnDetail = new ReturnController().getReturnProduct(id);
            if(returnDetail!=null){
                Product product = new ProductController().getProduct(returnDetail.getProductNo());
                if(product!=null) {
                    lblProductID.setText(returnDetail.getProductNo());
                    lblPName.setText(product.getName());
                    lblQty.setText(String.valueOf(returnDetail.getQty()));
                    lblPCategory.setText(returnDetail.getDescription());
                    lblUnitPrice.setText(String.valueOf(product.getUnitPrice()));
                }
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void viewSizeChangeProduct(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        loadReturnProduct(new ReturnController().getProductList("Size varies"));
    }

    public void addWasteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(new ReturnController().setWasteAllProducts()){
            new Alert(Alert.AlertType.INFORMATION, "Done..").show();
            loadReturnProduct(new ReturnController().getAllReturnDetails());
        }
    }

    public void viewDamageProduct(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        loadReturnProduct(new ReturnController().getProductList("Damage"));
    }

    public void addStockOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(new ReturnController().addToStockAllProducts()){
            new Alert(Alert.AlertType.INFORMATION, "Done..").show();
            loadReturnProduct(new ReturnController().getAllReturnDetails());
        }
    }

    public void viewAllReturnProduct(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        loadReturnProduct(new ReturnController().getAllReturnDetails());
    }
}
