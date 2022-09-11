package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.model_Controller.ProductController;
import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.OrderDetail;
import model.Product;
import model.ProductDetail;
import util.ProductDetailsLoadEvent;
import util.ValidationJFX;
import util.ValidationUtil;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

import static javafx.scene.paint.Color.TRANSPARENT;

public class ManageProductFormController implements ProductDetailsLoadEvent {
    public BorderPane addProductContext;
    public ImageView imageView;
    public JFXTextField txtProductID;
    public JFXTextField txtProductName;
    public JFXTextField txtMaterial;
    public JFXComboBox<String> cmbSize;
    public JFXTextField txtQtyForStock;
    public JFXTextField txtUnitPrice;
    public JFXComboBox<String> cmbCategory;
    public JFXComboBox<String> cmbSubCategory;
    public JFXComboBox<String> cmbBrandName;
    public JFXTextField txtColorCode;
    public JFXTextField txtImage;
    public VBox allProductsContext;
    public JFXButton btn_add_update;
    public Label lblQtyProduct;
    public TextField txtSearch;

    FileChooser fileChooser = new FileChooser();
    private List<Product> allProducts;

    public void initialize() {

        setProductNo();

        String[] size = {"S", "M", "L"};
        cmbSize.getItems().addAll(size);
        try {
            cmbBrandName.getItems().addAll(getBrandNames());
            cmbCategory.getItems().addAll(getCategory());
            cmbSubCategory.getItems().addAll(getSubCat());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        try {
            loadProduct(new ProductController().getAllProducts());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        storeValidations();
    }

    private void setProductNo() {
        try {
            txtProductID.setText(new ProductController().getProductNo());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadProduct(ArrayList<Product> productArrayList) throws SQLException, ClassNotFoundException {
        allProductsContext.getChildren().clear();
        allProducts = productArrayList;
        try {
            for (int i = 0; i < allProducts.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../view/Product_ManageForm.fxml"));

                HBox hBox = fxmlLoader.load();
                Product_ManageFormController product_manageFormController = fxmlLoader.getController();
                product_manageFormController.setData(allProducts.get(i));
                product_manageFormController.setEvent(this);
                allProductsContext.getChildren().add(hBox);
                lblQtyProduct.setText(String.valueOf(i+1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addImage(MouseEvent mouseEvent) {
        // Scene scene = new Scene("D:\GDSE\UI Projects\JDBC\Textile-Management-System\src\assets\product_images\");
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            System.out.println(file.getAbsolutePath());
        }
    }

    public void addToStock_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Product product = new Product(
                txtProductID.getText(),
                txtProductName.getText(), txtMaterial.getText(),
                cmbSize.getValue(),
                txtColorCode.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyForStock.getText()),
                getBrandID(cmbBrandName.getValue()),
                getCategoryID(cmbCategory.getValue()),
                getSubCatID(cmbSubCategory.getValue()),
                /*"../assets/product_images/"+*/txtImage.getText()
        );

        if(btn_add_update.getText().equals("Add")) {
            if (new ProductController().saveProduct(product)) {
                //new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                loadProduct(new ProductController().getAllProducts());
                clearData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Try again..").show();
            }
        }else if(btn_add_update.getText().equals("Update")){
            if (new ProductController().updateProduct(product)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
                loadProduct(new ProductController().getAllProducts());
                clearData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Try again..").show();
            }
        }
    }

    private void clearData() {
        txtProductName.clear();
        txtMaterial.clear();
        txtColorCode.clear();
        txtUnitPrice.clear();
        txtQtyForStock.clear();
        txtImage.clear();
        cmbBrandName.getSelectionModel().clearSelection();
        cmbSize.getSelectionModel().clearSelection();
        cmbCategory.getSelectionModel().clearSelection();
        cmbSubCategory.getSelectionModel().clearSelection();
        btn_add_update.setText("Add");
        Image image = new Image(getClass().getResourceAsStream("../assets/icon/icon2 (20).png"));
        imageView.setImage(image);
        setProductNo();
    }


    public void clearTextFields(ActionEvent actionEvent) {
        clearData();
    }

    public void addCategory_OnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../view/ManageCategoryForm.fxml")));
        scene.setFill(TRANSPARENT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.centerOnScreen();
        stage.show();
    }

    public void addBrand_OnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../view/AddBrandForm.fxml")));
        scene.setFill(TRANSPARENT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.centerOnScreen();
        stage.show();
    }

    public List<String> getBrandNames() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Brand").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(rst.getString(2));
        }
        return ids;
    }

    public List<String> getCategory() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Category").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(rst.getString(2));
        }
        return ids;
    }

    public List<String> getSubCat() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM SubCategory").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(rst.getString(2));
        }
        return ids;
    }

    public String getBrandID(String name) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Brand WHERE brandName=?");
        stm.setObject(1, name);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return rst.getString(1);
        } else {
            return null;
        }
    }

    public String getSubCatID(String name) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM SubCategory WHERE subCategoryName=?");
        stm.setObject(1, name);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return rst.getString(1);
        } else {
            return null;
        }
    }

    public String getCategoryID(String name) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Category WHERE categoryName=?");
        stm.setObject(1, name);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return rst.getString(1);
        } else {
            return null;
        }
    }

    @Override
    public void loadData(String id){
        try {
            Product product = new ProductController().getProduct(id);
            txtProductID.setText(id);
            txtProductName.setText(product.getName());
            txtMaterial.setText(product.getMaterial());
            txtColorCode.setText(product.getColorCode());
            txtUnitPrice.setText(String.valueOf(product.getUnitPrice()));
            txtQtyForStock.setText(String.valueOf(product.getQtyOnHand()));
            txtImage.setText(product.getImageSrc());
            cmbSize.getSelectionModel().select(product.getSize());
            cmbBrandName.getSelectionModel().select(new ProductController().getBrandName(product.getBrandID()));
            cmbCategory.getSelectionModel().select(new ProductController().getCategoryName(product.getCategoryID()));
            cmbSubCategory.getSelectionModel().select(new ProductController().getSubCatName(product.getSubCategoryID()));
            Image image = new Image(getClass().getResourceAsStream(("../assets/product_images/"+product.getImageSrc())));
            imageView.setImage(image);
            btn_add_update.setText("Update");

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void refreshDetails() {
        try {
            loadProduct(new ProductController().getAllProducts());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void removeProduct(ProductDetail product) { }

    @Override
    public void setOrderDetail(String productID, int qty, double subTotal, double discount, double total) { }

    @Override
    public void loadReturnData(String id) { }


    public void search_keyReleasedAction(KeyEvent keyEvent) {
        try {
            loadProduct(new ProductController().searchProducts(txtSearch.getText()));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap();
    Pattern namePattern = Pattern.compile("^[A-z .]{3,30}$");
    Pattern materialPattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern colorPattern = Pattern.compile("^[A-z ]{3,10}$");
    Pattern unitPricePattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    Pattern qtyPattern = Pattern.compile("^[1-9][0-9]*$");
    Pattern imageSrcPattern = Pattern.compile("^[A-z.]{2,}([.jpg]|[.png]{3})$");

    private void storeValidations() {
        map.put(txtProductName, namePattern);
        map.put(txtMaterial, materialPattern);
        map.put(txtColorCode, colorPattern);
        map.put(txtUnitPrice, unitPricePattern);
        map.put(txtQtyForStock, qtyPattern);
        map.put(txtImage, imageSrcPattern);
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationJFX.validate(map,btn_add_update);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                //new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }
    }
}
