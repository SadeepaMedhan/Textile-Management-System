package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controller.model_Controller.*;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.ProductDetailsLoadEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import static controller.LoginFormController.cashier;
import static javafx.scene.paint.Color.TRANSPARENT;

public class HomePageController implements ProductDetailsLoadEvent {
    final ToggleGroup group = new ToggleGroup();
    public Label lblTime;
    public Label lblDate;
    public StackPane homePageContext;
    public JFXTextField txtProductName;
    public JFXTextField txtMaterial;
    public JFXTextField txtQty;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtBrand;
    public JFXTextField txtCategory;
    public JFXTextField txtSubCategory;
    public JFXTextField txtSize;
    public JFXTextField txtDiscount;
    public TextField txtSearchProductID;
    public JFXTextField txtColor;
    public Label lblOrderID;
    public Label lblItemCount;
    public Label lblDiscount;
    public Label lblDisc;
    public Label lblSubTotal;
    public Label lblTotal;

    public VBox orderDetailsContext;
    public TextField txtSearchCustomerNic;
    public JFXTextField txtCustomerName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public Label lblBalance;
    public Label lblDueAmount;
    public VBox placeOrderContext;
    public StackPane backContext;
    public RadioButton radioBtnCash;
    public RadioButton radioBtnRecipt;
    public TextField txtPayment;
    public JFXButton btnAddToCart;
    public Button btnConfirmOrder;
    public AnchorPane navigationPane;
    public VBox panel2;
    public ImageView hamburger;
    public Label lblCashierName;
    public Button btnBillPrint;
    Customer customer = null;
    Payment payment = null;
    private String paymentMethod;
    ProductInfoForBill productForBill;

    private List<ProductDetail> allProducts = new ArrayList<>();

    public void initialize() {
        btnConfirmOrder.setDisable(true);

        radioBtnRecipt.setToggleGroup(group);
        radioBtnCash.setToggleGroup(group);
        radioBtnCash.setSelected(true);
        radioBtnCash.setUserData("cash");
        radioBtnRecipt.setUserData("recipt");
        lblCashierName.setText(cashier.getCashierName());

        setDateTime();
        setOrderID();

        /*navigationPane.setVisible(false);

        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(0.5),panel2);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition=new TranslateTransition(Duration.seconds(0.5),navigationPane);
        translateTransition.setByX(-600);
        translateTransition.play();

        hamburger.setOnMouseClicked(event -> {

            navigationPane.setVisible(true);

            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),panel2);
            fadeTransition1.setFromValue(0);
            fadeTransition1.setToValue(0.15);
            fadeTransition1.play();

            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),navigationPane);
            translateTransition1.setByX(+70);
            translateTransition1.play();
        });

        navigationPane.setOnMouseClicked(event -> {

            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),navigationPane);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                navigationPane.setVisible(false);
            });

            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),panel2);
            translateTransition1.setByX(-70);
            translateTransition1.play();
            panel2.setVisible(true);

        });*/
    }

    private void setOrderID() {
        try {
            lblOrderID.setText(new OrderController().getOrderID());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd-MMM-yyyy");
        String dateNow = dateFormat.format(new Date());
        lblDate.setText(dateNow);

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
        }), new KeyFrame(Duration.seconds(1)));

        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void confirmOrderOnAction(ActionEvent actionEvent) {
        if (customer != null) {
            if (payment!=null) {

                ArrayList<OrderDetail> orderDetails = new ArrayList<>();
                for (ProductDetail product : allProducts) {
                    orderDetails.add(new OrderDetail(
                            product.getProductID(),
                            product.getProductQTY(),
                            product.getDiscount(),
                            product.getTotalPrice()
                    ));
                }

                Order order = new Order(
                        lblOrderID.getText(),
                        lblDate.getText(),
                        lblTime.getText(),
                        customer.getCusID(),
                        cashier.getCashierID(),
                        paymentMethod,
                        Double.parseDouble(lblTotal.getText()),
                        orderDetails
                );

                if (new OrderController().placeOrder(order, payment)) {
                    new Alert(Alert.AlertType.INFORMATION, "Successfully..").show();
                    setBill(order,payment);
                    setOrderID();
                    clearAll();
                }
            } else {
                txtPayment.setStyle("-fx-border-color: #ec2047");
                new Alert(Alert.AlertType.WARNING, "Please do the payment..").show();
            }
        } else {
            txtSearchCustomerNic.setStyle("-fx-border-color: #ec2047");
            new Alert(Alert.AlertType.WARNING, "Please add customer information").show();
        }
    }

    private void setBill(Order o, Payment p) {
        int totQty=0;
        ArrayList<ProductInfoForBill> list = new ArrayList<>();
        for (ProductDetail product:allProducts) {
            list.add(new  ProductInfoForBill(
                    product.getProductID(),
                    product.getName(),
                    product.getUnitPrice(),
                    product.getProductQTY(),
                    product.getSubTotal()
            ));
            totQty+=product.getProductQTY();
        }
        String invoiceNo = p.getInvoiceNo();
        double tot = Double.parseDouble(lblTotal.getText());
        double disc = Double.parseDouble(lblDiscount.getText());
        double balance = Double.parseDouble(lblBalance.getText());

        HashMap map = new HashMap();
        map.put("billNo",invoiceNo);
        map.put("total",tot);
        map.put("date",o.getOrderDate());
        map.put("time",o.getOrderTime());
        map.put("cashier",cashier.getCashierName());
        map.put("discount",disc);
        map.put("cash",cashPrice);
        map.put("balance",balance);
        map.put("itemQty",totQty);

        openBill(list, map);
    }

    private void openBill(ArrayList<ProductInfoForBill> list, HashMap map) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/reports/paymentBill.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JRBeanArrayDataSource(list.toArray()));
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void checkPayment_onAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (checkPayment()) {
            txtPayment.clear();
            payment = new Payment(
                    new PaymentController().getInvoiceID(),
                    lblOrderID.getText(),
                    paymentMethod,
                    Double.parseDouble(lblTotal.getText())
            );
            btnConfirmOrder.setDisable(false);
        } else {
            txtPayment.requestFocus();
            payment = null;
        }
    }

    double pay;
    double cashPrice=0;
    private boolean checkPayment() throws SQLException, ClassNotFoundException {
        lblBalance.setText("0.00");
        double amount = Double.parseDouble(lblDueAmount.getText());
        paymentMethod = (String) group.getSelectedToggle().getUserData();

        if (paymentMethod.equals("cash")) {
            pay = Double.parseDouble(txtPayment.getText());
            cashPrice+=pay;
            if (amount < pay) {
                lblBalance.setText(String.valueOf(pay - amount));
                lblDueAmount.setText("0.00");
                return true;
            } else if (amount > pay) {
                lblDueAmount.setText(String.valueOf(amount - pay));
                lblBalance.setText("0.00");
                return false;
            } else return amount == pay;

        } else if (paymentMethod.equals("recipt")) {
            Returns returns = new ReturnController().getReturn(txtPayment.getText());
            if(returns!=null){
                pay=returns.getTotalPrice();
                cashPrice+=pay;
                if (amount < pay) {
                    lblBalance.setText(String.valueOf(pay - amount));
                    return true;
                } else if (amount > pay) {
                    lblDueAmount.setText(String.valueOf(amount - pay));
                    return false;
                } else return amount == pay;
            }
        }
        return false;
    }

    private void clearAll() {
        orderDetailsContext.getChildren().clear();
        allProducts.clear();
        lblItemCount.setText("0");
        lblSubTotal.setText("0.00");
        lblTotal.setText("0.00");
        txtSearchCustomerNic.clear();
        txtCustomerName.clear();
        txtContact.clear();
        txtAddress.clear();
        customer=null;
        payment=null;
        btnConfirmOrder.setDisable(true);
        cashPrice=0;
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearTextFields();
    }


    public void searchProductOnAction(ActionEvent actionEvent) {
        try {
            Product product = new ProductController().getProduct(txtSearchProductID.getText());// .. ENTER_Action
            if(product!=null) {
                addList(product);
                loadProduct();
                txtSearchProductID.setStyle("-fx-border-color: #CBCBCB");
            }else {
                txtSearchProductID.setStyle("-fx-border-color: #b12d2d");
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadProduct() throws SQLException, ClassNotFoundException {
        orderDetailsContext.getChildren().clear();
        try {
            for (int i = 0; i < allProducts.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../view/Product_OrderForm.fxml"));
                HBox hBox = fxmlLoader.load();
                Product_OrderFormController product_orderFormController = fxmlLoader.getController();
                product_orderFormController.setEvent(this);
                product_orderFormController.setData(allProducts.get(i));
                orderDetailsContext.getChildren().add(hBox);
            }
            lblItemCount.setText(String.valueOf(allProducts.size()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        txtSearchProductID.clear();
    }

    private void addList(Product product) {
        if (allProducts.isEmpty()) {
            allProducts.add(new ProductDetail(
                    product.getProductID(),
                    product.getName(),
                    product.getMaterial(),
                    product.getSize(),
                    product.getColorCode(),
                    product.getUnitPrice(),
                    product.getQtyOnHand(),
                    product.getBrandID(),
                    product.getCategoryID(),
                    product.getSubCategoryID(),
                    product.getImageSrc(),
                    1, 00.0,
                    0, 00.0
            ));
        } else {
            if (!isDuplicate(product)) {
                allProducts.add(new ProductDetail(
                        product.getProductID(),
                        product.getName(),
                        product.getMaterial(),
                        product.getSize(),
                        product.getColorCode(),
                        product.getUnitPrice(),
                        product.getQtyOnHand(),
                        product.getBrandID(),
                        product.getCategoryID(),
                        product.getSubCategoryID(),
                        product.getImageSrc(),
                        1, 00.0,
                        0, 00.0
                ));
            }
        }
    }

    private boolean isDuplicate(Product product) {
        for (ProductDetail allProduct : allProducts) {
            if (allProduct.getProductID().equals(product.getProductID())) {
                allProduct.setProductQTY(allProduct.getProductQTY() + 1);
                return true;
            }
        }
        return false;
    }

    public void addToCart_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        for (ProductDetail product : allProducts) {
            if (txtSearchProductID.getText().equals(product.getProductID())) {
                product.setDiscount(Double.parseDouble(txtDiscount.getText()));
                product.setUnitPrice(Double.parseDouble(txtUnitPrice.getText()));
                product.setProductQTY(Integer.parseInt(txtQty.getText()));
                loadProduct();
                clearTextFields();
            }
        }
    }

    private void clearTextFields() {
        btnAddToCart.setDisable(true);
        txtSearchProductID.setDisable(false);
        txtSearchProductID.clear();
        txtProductName.clear();
        txtBrand.clear();
        txtSize.clear();
        txtMaterial.clear();
        txtCategory.clear();
        txtSubCategory.clear();
        txtColor.clear();
        txtUnitPrice.clear();
        txtDiscount.clear();
        txtQty.clear();
    }

    @Override
    public void loadData(String id) {
        for (ProductDetail product : allProducts) {
            if (product.getProductID().equals(id)) {
                txtSearchProductID.setText(id);
                txtSearchProductID.setDisable(true);
                txtProductName.setText(product.getName());
                txtSize.setText(product.getSize());
                txtColor.setText(product.getColor());
                txtMaterial.setText(product.getMaterial());
                try {
                    ProductController productController = new ProductController();
                    txtBrand.setText(productController.getBrandName(product.getBrandID()));
                    txtSubCategory.setText(productController.getSubCatName(product.getSubCategoryID()));
                    txtCategory.setText(productController.getCategoryName(product.getCategoryID()));
                    productController = null;

                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }

                txtUnitPrice.setText(String.valueOf(product.getUnitPrice()));
                txtQty.setText(String.valueOf(product.getProductQTY()));
                txtDiscount.setText(String.valueOf(product.getDiscount()));
                btnAddToCart.setDisable(false);
            }
        }
    }

    @Override
    public void refreshDetails() { }

    @Override
    public void removeProduct(ProductDetail product) {
        allProducts.remove(product);
        try {
            loadProduct();
            setOrderPrice();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void setOrderDetail(String productID, int qty, double subTotal, double discount, double total) {
        for (ProductDetail product : allProducts) {
            if (product.getProductID().equals(productID)) {
                product.setProductQTY(qty);
                product.setSubTotal(subTotal);
                product.setDiscount(discount);
                product.setTotalPrice(total);
            }
        }
        setOrderPrice();
    }

    @Override
    public void loadReturnData(String id) { }

    private void setOrderPrice() {
        double totalAmount = 0.00;
        double totalSubTotal = 0.00;
        double discountPrice = 0.00;
        double discountPercentage = 0;

        for (ProductDetail temp : allProducts) {
            totalAmount += temp.getTotalPrice();
            totalSubTotal += temp.getSubTotal();
        }

        discountPrice = totalSubTotal - totalAmount;
        discountPercentage += 100 / (totalSubTotal / discountPrice);

        lblTotal.setText(String.valueOf(totalAmount));
        lblDueAmount.setText(lblTotal.getText());
        lblSubTotal.setText(String.valueOf(totalSubTotal));
        lblDiscount.setText(String.valueOf(discountPrice));
        lblDisc.setText(String.valueOf(discountPercentage));
    }




    public void paymentClickOnAction(MouseEvent mouseEvent) {
        txtPayment.setStyle("-fx-border-color: #CBCBCB");
    }

    public void customerNicClickOnAction(MouseEvent mouseEvent) {
        txtSearchCustomerNic.setStyle("-fx-border-color: #CBCBCB");
    }

    public void cancelOrderOnAction(ActionEvent actionEvent) {
        clearTextFields();
        clearAll();
    }

    public void openNavigationPanel(MouseEvent mouseEvent) {
    }


    public void searchBarcodeKeyReleased(KeyEvent keyEvent) {
        txtSearchProductID.setStyle("-fx-border-color: #CBCBCB");
        Pattern barcodePattern = Pattern.compile("^(P)[0-9]{3}$");
        if (!barcodePattern.matcher(txtSearchProductID.getText()).matches()) {
            txtSearchProductID.setStyle("-fx-border-color: red");
        }else{
            txtSearchProductID.setStyle("-fx-border-color: green");
        }
    }

    public void searchCustomerNic(ActionEvent keyEvent) {
        if (!txtSearchCustomerNic.getText().equals("")) {
            try {
                customer = new CustomerController().getCustomer(txtSearchCustomerNic.getText());
                if (customer != null) {
                    txtSearchCustomerNic.setStyle("-fx-border-color: green");
                    txtCustomerName.setText(customer.getCusName());
                    txtContact.setText(customer.getContact());
                    txtAddress.setText(customer.getCusAddress());
                }else{
                    txtSearchCustomerNic.setStyle("-fx-border-color: red");
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void searchCusKeyReleased(KeyEvent keyEvent) {
    }


    public void openAllOrders(MouseEvent mouseEvent) throws IOException {
        placeOrderContext.setVisible(false);
        backContext.getChildren().clear();
        backContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/OrderDetailsForm.fxml")));
    }

    public void openAddNewCustomer(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../view/AddCustomerForm.fxml")));
        scene.setFill(TRANSPARENT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.centerOnScreen();
        stage.show();
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

    public void openPlaceOrder(MouseEvent mouseEvent) {
        backContext.getChildren().clear();
        backContext.getChildren().add(placeOrderContext);
        placeOrderContext.setVisible(true);
    }

    public void manageReturnsOnAction(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) homePageContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageReturnsProductForm.fxml"))));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public void logOutOnAction(MouseEvent mouseEvent) throws IOException {
        //System.out.println("logout");
        Stage stage = (Stage) homePageContext.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml")));
        scene.setFill(TRANSPARENT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
}
