package controller;

import com.jfoenix.controls.JFXButton;
import controller.model_Controller.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.ValidationUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

import static controller.LoginFormController.cashier;
import static javafx.scene.paint.Color.TRANSPARENT;

public class ManageReturnsProductFormController {
    public StackPane manageReturnsContext;
    public TextField txtInvoiceNo;
    public Label lblOID;
    public VBox allProductsContext;
    public Label lblODate;
    public Label lblOTime;
    public Label lblOProductQty;
    public Label lblOState;
    public Label lblOTotal;
    public Label lblOInvoiceNo;
    public Label lblOCusNAme;
    public Label lblOCusContact;
    public TextField txtProductID;
    public Label lblReceiptNo;
    public Label lblROrderID;
    public Label lblRODate;
    public Label lblROInvoiceNo;
    public Label lblReturnProductNo;
    public TextField txtDescription;
    public Label lblReturnDate;
    public Label lblAmount;
    public Button btnReceiptPrint;
    public JFXButton btnConfirm;


    public void initialize(){
        txtProductID.setEditable(false);
        btnReceiptPrint.setDisable(true);
        setReceiptNo();
        storeValidations();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd-MMM-yyyy");
        String dateNow = dateFormat.format(new Date());
        lblReturnDate.setText(dateNow);
    }

    private void setReceiptNo() {
        try {
            lblReceiptNo.setText(new ReturnController().getReceiptNo());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }


    public void openMarkAttendance(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../view/AttendanceMarkForm.fxml")));
        scene.setFill(TRANSPARENT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.centerOnScreen();
        stage.show();
    }

    public void logOutOnAction(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) manageReturnsContext.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml")));
        scene.setFill(TRANSPARENT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    ArrayList<OrderDetail> allProducts;
    ArrayList<OrderDetail> returnList=new ArrayList<>();
    Order order;
    public void searchInvoiceOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Payment payment = new PaymentController().getPayment(txtInvoiceNo.getText());
        if (payment != null) {
            setOrderDetails(new OrderController().getOrder(payment.getOrderID()));
            txtProductID.setEditable(true);
        }
    }

    private void setOrderDetails(Order order) throws SQLException, ClassNotFoundException {
        this.order=order;
        allProducts = new OrderController().getOrdersDetails(order.getOrderID());
        setOrderInfo(order);
        loadProducts(allProducts);
    }

    private void loadProducts(ArrayList<OrderDetail> productList) throws SQLException, ClassNotFoundException {
        allProductsContext.getChildren().clear();

        try {
            for (int i = 0; i < productList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../view/Product_OrderReportsForm.fxml"));

                HBox hBox = fxmlLoader.load();
                Product_OrderReportsFormController controller = fxmlLoader.getController();
                controller.setData(productList.get(i));
                allProductsContext.getChildren().add(hBox);
            }
            lblOProductQty.setText(String.valueOf(allProducts.size()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setOrderInfo(Order order) throws SQLException, ClassNotFoundException {
        lblOID.setText(order.getOrderID());
        lblODate.setText(order.getOrderDate());
        lblOTime.setText(order.getOrderTime());
        lblOState.setText(order.getStatus());
        lblOTotal.setText(String.valueOf(order.getTotalPrice()));
        lblOInvoiceNo.setText(txtInvoiceNo.getText());
        Customer customer = new CustomerController().getCustomer(order.getCusID());
        lblOCusNAme.setText(customer.getCusName());
        lblOCusContact.setText(customer.getContact());

        lblRODate.setText(order.getOrderDate());
        lblROrderID.setText(order.getOrderID());
        lblROInvoiceNo.setText(txtInvoiceNo.getText());
    }

    public void searchProductOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        for (OrderDetail p :allProducts) {
            if(p.getProductID().equals(txtProductID.getText())){
                if(!isExit(p)){
                    returnList.add(p);
                }
            }
        }
        if(!returnList.isEmpty()) {
            loadProducts(returnList);
            setReturnProductIds(returnList);
            txtProductID.clear();
        }
    }

    private boolean isExit(OrderDetail p) {
        for (OrderDetail detail:returnList) {
            if(detail.getProductID().equals(p.getProductID())){
                return true;
            }
        }
        return false;
    }

    private void setReturnProductIds(ArrayList<OrderDetail> returnList) {
        String ids = "";
        double totAmount=0.0;
        for (OrderDetail p:returnList) {
            ids = ids + p.getProductID()+", ";
            totAmount+=p.getTotalPrice();
        }
        lblReturnProductNo.setText(ids);
        lblAmount.setText(String.valueOf(totAmount));
    }

    public void printReceiptOnAction(ActionEvent actionEvent) {
        try {
            ArrayList<ProductInfoForBill> list = new ArrayList<>();
            HashMap map = new HashMap();
            int totQty=0;
            for (OrderDetail orderDetail:returnList) {
                Product product = new ProductController().getProduct(orderDetail.getProductID());
                list.add(new  ProductInfoForBill(
                        product.getProductID(),
                        product.getName(),
                        product.getUnitPrice(),
                        orderDetail.getProductQTY(),
                        orderDetail.getTotalPrice()
                ));
                totQty+=orderDetail.getProductQTY();
            }
            double balance = Double.parseDouble(lblAmount.getText());

            map.put("oID",lblOID.getText());
            map.put("returnID",lblReceiptNo.getText());
            map.put("orderDate",lblODate.getText());
            map.put("returnDate",lblReturnDate.getText());
            map.put("returnBalance",balance);
            map.put("returnQty",totQty);

            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/reports/returnReceipt.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JRBeanArrayDataSource(list.toArray()));
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        allProductsContext.getChildren().clear();
        txtInvoiceNo.clear();
        btnConfirm.setDisable(true);
    }

    public void confirmReturnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<ReturnDetail> returnDetail = new ArrayList<>();
        for (OrderDetail p:returnList) {
            returnDetail.add(new ReturnDetail(
                    lblReceiptNo.getText(),
                    p.getProductID(),
                    p.getProductQTY(),
                    txtDescription.getText()
            ));
        }
        Returns returns = new Returns(
                lblReceiptNo.getText(),
                lblROInvoiceNo.getText(),
                lblReturnDate.getText(),
                Double.parseDouble(lblAmount.getText()),
                returnDetail);
        if(new ReturnController().setReturn(returns)){
            btnReceiptPrint.setDisable(false);
        }
    }

    public void showAllOrdersOnAction(MouseEvent mouseEvent) {

    }

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern invoicePattern = Pattern.compile("^[0-9]{6}$");
    Pattern productIDPattern = Pattern.compile("^(P)[0-9]{3}$");
    Pattern descriptionPattern = Pattern.compile("^[A-z ]{3,20}$");

    private void storeValidations() {
        map.put(txtInvoiceNo, invoicePattern);
        map.put(txtProductID, productIDPattern);
        map.put(txtDescription, descriptionPattern);
    }
    public void txtField_KeyReleased(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,new Button());

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
