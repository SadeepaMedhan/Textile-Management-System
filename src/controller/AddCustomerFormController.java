package controller;

import controller.model_Controller.CustomerController;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Customer;
import util.ValidationUtil;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddCustomerFormController {
    public TextField txtID;
    public TextField txtNIC;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtContact;
    public StackPane addCustomerContext;
    public Button btnAddCus;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern nicPattern = Pattern.compile("^[0-9]{9}?[v]$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");
    Pattern contactPattern = Pattern.compile("^(071|077|078|075|076)[-]?[0-9]{7}$");


    private void storeValidations() {
        map.put(txtName, namePattern);
        map.put(txtNIC, nicPattern);
        map.put(txtAddress, addressPattern);
        map.put(txtContact, contactPattern);
    }
    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnAddCus);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                //new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }
    }

    public void initialize(){
        btnAddCus.setDisable(true);
        storeValidations();
        setCusID();
    }

    private void setCusID() {
        try {
            txtID.setText(new CustomerController().getCustomerID());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addCustomerOnAction(ActionEvent actionEvent) {
        Customer customer = new Customer(
                txtID.getText(),
                txtName.getText(),
                txtNIC.getText(),
                txtAddress.getText(),
                txtContact.getText()
        );

        try {
            if(new CustomerController().saveCustomer(customer)){
                Stage stage = (Stage) addCustomerContext.getScene().getWindow();
                stage.close();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void closeOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) addCustomerContext.getScene().getWindow();
        stage.close();
    }

}
