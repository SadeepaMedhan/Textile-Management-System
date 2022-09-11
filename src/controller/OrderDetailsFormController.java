package controller;

import controller.model_Controller.CustomerController;
import controller.model_Controller.OrderController;
import controller.model_Controller.PaymentController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Customer;
import model.Order;
import model.OrderDetail;
import view.tm.OrdersTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsFormController {
    public StackPane ordersReportsContext;
    public TextField txtSearchOrder;
    public TableView<OrdersTM> tblOrders;
    public TableColumn colOrderID;
    public TableColumn colDate;
    public TableColumn colCusName;
    public TableColumn colCashierID;
    public TableColumn colProductsQty;
    public TableColumn colViewProducts;
    public TableColumn colTotalPrice;
    public TableColumn colStatus;
    public VBox allProductsContext;
    public Label lblOrderCount;
    public Label lblOID;
    public Label lblODate;
    public Label lblOTime;
    public Label lblOProductQty;
    public Label lblOState;
    public Label lblOTotal;
    public Label lblOInvoiceNo;
    public Label lblOCusNAme;
    public Label lblOCusContact;
    private ArrayList<OrderDetail> allProducts;

    public void initialize() {
        try {
            loadData(new OrderController().getAllOrders());

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        colOrderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("cusName"));
        colCashierID.setCellValueFactory(new PropertyValueFactory<>("cashierID"));
        colProductsQty.setCellValueFactory(new PropertyValueFactory<>("productsQty"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colViewProducts.setCellValueFactory(new PropertyValueFactory<>("viewProducts"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

    }

    private void loadData(ArrayList<Order> orders) throws SQLException, ClassNotFoundException {
        ArrayList<Order> allOrders = orders;
        ObservableList<OrdersTM> obList = FXCollections.observableArrayList();
        //System.out.println("load");

        for (Order temp : allOrders) {
            int qty = new OrderController().getAllProductQTY(temp.getOrderID());
            Button btn = new Button("view");

            btn.setStyle("-fx-background-color: #1e7219; -fx-text-fill: white; -fx-cursor: hand;");
            //System.out.print(temp.getOrderID());
            String cusName = new CustomerController().getCustomer(temp.getCusID()).getCusName();
            //System.out.println(" "+cusName);
            obList.add(new OrdersTM(
                    temp.getOrderID(),
                    temp.getOrderDate(),
                    cusName,
                    temp.getCashierID(),
                    qty,
                    temp.getStatus(),
                    btn,
                    temp.getTotalPrice()
            ));
            btn.setOnAction((e) -> {
                try {
                    loadProducts(temp);
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            });
        }
        tblOrders.setItems(obList);
        lblOrderCount.setText(String.valueOf(obList.size()));
    }

    private void loadProducts(Order order) throws SQLException, ClassNotFoundException {
        setInfo(order);

        allProductsContext.getChildren().clear();
        try {
            allProducts = new OrderController().getOrdersDetails(order.getOrderID());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        try {
            for (int i = 0; i < allProducts.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../view/Product_OrderReportsForm.fxml"));

                HBox hBox = fxmlLoader.load();
                Product_OrderReportsFormController controller = fxmlLoader.getController();
                controller.setData(allProducts.get(i));

                allProductsContext.getChildren().add(hBox);
            }
            lblOProductQty.setText(String.valueOf(allProducts.size()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setInfo(Order order) throws SQLException, ClassNotFoundException {
        lblOID.setText(order.getOrderID());
        lblODate.setText(order.getOrderDate());
        lblOTime.setText(order.getOrderTime());
        lblOState.setText(order.getStatus());
        lblOTotal.setText(String.valueOf(order.getTotalPrice()));
        lblOInvoiceNo.setText(new PaymentController().getInvoiceNo(order.getOrderID()));
        Customer customer = new CustomerController().getCustomer(order.getCusID());
        lblOCusNAme.setText(customer.getCusName());
        lblOCusContact.setText(customer.getContact());
    }


    public void searchOrder_keyReleased(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        loadData(new OrderController().searchOrders(txtSearchOrder.getText()));
    }
}
