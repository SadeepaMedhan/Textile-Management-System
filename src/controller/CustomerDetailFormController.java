package controller;


import controller.model_Controller.CustomerController;
import controller.model_Controller.OrderController;
import controller.model_Controller.ProductController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Customer;
import view.tm.CustomerTM;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import static javafx.scene.paint.Color.TRANSPARENT;

public class CustomerDetailFormController {
    public StackPane CustomerDetailsContext;
    public TableView<CustomerTM> tblCustomerDetail;
    public TableColumn colCusID;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colNIC;
    public TableColumn colContact;
    public TableColumn colUpdate;
    public TableColumn colRemove;
    public TextField txtSearch;
    public Label lblCountCus;
    public TableColumn colIncome;


    public void initialize(){
        try {
            loadData(new CustomerController().getAllCustomers());

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        colCusID.setCellValueFactory(new PropertyValueFactory<>("cusID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("cusName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("cusAddress"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("cusNIC"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colIncome.setCellValueFactory(new PropertyValueFactory<>("income"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("update"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("remove"));

    }

    private void loadData(ArrayList<Customer> cus) throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers = cus;
        ObservableList<CustomerTM> obList = FXCollections.observableArrayList();

        for (Customer temp:allCustomers) {
            double total = new OrderController().getPriceCustomerWise(temp.getCusID());
            Button btn = new Button("update");
            btn.setStyle("-fx-background-color: #1e7219; -fx-text-fill: white; -fx-cursor: hand;");
            Button btn2 = new Button("remove");
            btn2.setStyle("-fx-background-color: #a32020; -fx-text-fill: white; -fx-cursor: hand;");

            obList.add(new CustomerTM(
                    temp.getCusID(),
                    temp.getCusName(),
                    temp.getCusNIC(),
                    temp.getCusAddress(),
                    temp.getContact(),
                    total,
                    btn,
                    btn2
            ));
            btn.setOnAction((e) ->{
                try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/updateCustomerForm.fxml"));
                Parent load = loader.load();
                UpdateCustomerFormController controller = loader.getController();
                controller.setDetails(temp);
                Stage stage = new Stage();
                Scene scene = new Scene(load);
                scene.setFill(TRANSPARENT);
                stage.setScene(scene);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.centerOnScreen();
                stage.show();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } );
            btn2.setOnAction((e) ->{
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure whether you want to delete this Customer ?", yes, no);
                alert.setTitle("Confirmation Alert");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.orElse(no) == yes) {
                    try {
                        if (new CustomerController().deleteCustomer(temp.getCusID())) {
                            loadData(new CustomerController().getAllCustomers());
                        }
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }

                } else {
                }            } );
        }
        tblCustomerDetail.setItems(obList);
        lblCountCus.setText(String.valueOf(obList.size()));
    }

    public void searchCus_keyReleased(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        if(!txtSearch.getText().equals("")) {
            loadData(new CustomerController().getCustomers(txtSearch.getText()));
        }else {
            loadData(new CustomerController().getAllCustomers());
        }
    }
}
