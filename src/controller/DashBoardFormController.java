package controller;

import com.jfoenix.controls.JFXButton;
import controller.model_Controller.CustomerController;
import controller.model_Controller.IncomeController;
import controller.model_Controller.OrderController;
import controller.model_Controller.ProductController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.DailySales;
import model.Order;
import model.OrderDetail;
import model.Product;
import view.tm.DailySalesTM;
import view.tm.OrdersTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import static javafx.scene.paint.Color.TRANSPARENT;

public class DashBoardFormController {

    public StackPane dashBoardContext;
    public StackPane backContext;
    public VBox summaryContext;
    public Label lblTime;
    public Label lblDate;
    public Label lblProductCount;
    public Label lblMostMoveProduct;
    public Label lblCusCount;
    public Label lblOrderCount;
    public Label lblTotRevenue;
    public TextField txtSearch;
    public Label lblCountOfDailySales;
    public DatePicker datePicker;
    public Label lblTotalIncome;
    public TableView<DailySalesTM> tblSalesProduct;
    public TableColumn colCategory;
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colDiscount;
    public TableColumn colTotal;
    ArrayList<DailySales> allSales;

    public void initialize() {
        setDateTime();

        try {
            allSales = new OrderController().getAllSales();
            loadDailySales(allSales);
            //setSummary();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        colID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totPrice"));

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("E, dd-MMM-yyyy");
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                loadProductList(dateFormat.format(newValue));
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void loadProductList(String date) throws SQLException, ClassNotFoundException {
        ArrayList<DailySales> saleList = new ArrayList<>();
        ArrayList<String> orderIds = new OrderController().getOrderIdsFromDate(date);
        for (String id:orderIds) {
            ArrayList<OrderDetail> ordersDetails = new OrderController().getOrdersDetails(id);
            for (OrderDetail detail:ordersDetails) {
                saleList.add(new DailySales(detail.getProductID(),detail.getProductQTY(),detail.getDiscount(),detail.getTotalPrice(),id));
            }
        }
        loadDailySales(saleList);
    }

    public void searchProductKeyReleased(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        ArrayList<DailySales> saleList = new ArrayList<>();
        ArrayList<OrderDetail> ordersDetails = new OrderController().getOrdersDetailProductID(txtSearch.getText());
        for (OrderDetail detail:ordersDetails) {
            saleList.add(new DailySales(detail.getProductID(),detail.getProductQTY(),detail.getDiscount(),detail.getTotalPrice(),"id"));
        }

        loadDailySales(saleList);
    }

    private void setSummary() throws SQLException, ClassNotFoundException {
        lblProductCount.setText(String.valueOf(new ProductController().getProductCount()));
        lblMostMoveProduct.setText(new ProductController().getProduct(allSales.get(0).getProductID()).getName());
        lblCusCount.setText(String.valueOf(new CustomerController().getCusCount()));
        lblOrderCount.setText(String.valueOf(new OrderController().getOrdersCount()));
        lblTotRevenue.setText("LKR."+new IncomeController().getTotalRevenue());
    }

    private void loadDailySales(ArrayList<DailySales> sales) throws SQLException, ClassNotFoundException {
        ArrayList<DailySales> allSales = sales;
        ObservableList<DailySalesTM> obList = FXCollections.observableArrayList();
        double totIncome=0;
        for (DailySales temp : allSales) {
            ProductController productController = new ProductController();
            Product product = productController.getProduct(temp.getProductID());
            String categoryName = productController.getCategoryName(product.getCategoryID());
            String subCatName = productController.getSubCatName(product.getSubCategoryID());

            totIncome+=temp.getTotPrice();
            obList.add(new DailySalesTM(
                    temp.getProductID(),
                    product.getName(),
                    (subCatName+", "+categoryName),
                    product.getUnitPrice(),
                    temp.getQty(),
                    temp.getDiscount(),
                    temp.getTotPrice()
            ));

        }
        tblSalesProduct.setItems(obList);
        lblCountOfDailySales.setText(String.valueOf(obList.size()));
        lblTotalIncome.setText(totIncome+"/=");
    }

    private void setDateTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd-MMM-yyyy");
        String dateNow = dateFormat.format(date);
        lblDate.setText(dateNow);

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
        }), new KeyFrame(Duration.seconds(1)));

        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }


    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) dashBoardContext.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml")));
        scene.setFill(TRANSPARENT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }


    public void openSummaryOnAction(ActionEvent actionEvent) {
        backContext.getChildren().clear();
        summaryContext.setVisible(true);
    }


    public void openCashierOnAction(ActionEvent actionEvent) throws IOException {
       loadForm("../view/ManageCashierForm.fxml");
    }

    public void openCustomerDetailsOnAction(ActionEvent actionEvent) throws IOException {
      loadForm("../view/CustomerDetailForm.fxml");
    }

    public void openEmployeeDetailsOnAction(ActionEvent actionEvent) throws IOException {
        loadForm("../view/EmployeeDetailsForm.fxml");
    }

    public void openSalaryReportsOnAction(ActionEvent actionEvent) throws IOException {
        loadForm("../view/SalaryReportsForm.fxml");
    }

    public void openIncomeReportsOnAction(ActionEvent actionEvent) throws IOException {
        loadForm("../view/IncomeReportsForm.fxml");
    }

    public void openOrderReportsOnAction(ActionEvent actionEvent) throws IOException {
        loadForm("../view/OrderDetailsForm.fxml");
    }

    public void openReturnOrdersOnAction(ActionEvent actionEvent) throws IOException {
       loadForm("../view/ReturnProductsForm.fxml");
    }

    public void openManageProductOnAction(ActionEvent actionEvent) throws IOException {
      loadForm("../view/ManageProductForm.fxml");
    }

    private void loadForm(String url) throws IOException {
        summaryContext.setVisible(false);
        backContext.getChildren().clear();
        backContext.getChildren().add(FXMLLoader.load(getClass().getResource(url)));

    }

    public void openProductDetails(MouseEvent mouseEvent) throws IOException {
        loadForm("../view/ManageProductForm.fxml");
    }

    public void openCustomerDetails(MouseEvent mouseEvent) throws IOException {
        loadForm("../view/CustomerDetailForm.fxml");
    }

    public void openOrderDetails(MouseEvent mouseEvent) throws IOException {
        loadForm("../view/OrderDetailsForm.fxml");
    }

    public void openIncomeDetails(MouseEvent mouseEvent) throws IOException {
        loadForm("../view/IncomeReportsForm.fxml");
    }

    public void refreshOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        loadDailySales(allSales);
    }

}
