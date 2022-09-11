package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controller.model_Controller.EmployeeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import model.Cashier;
import util.ValidationJFX;
import util.ValidationUtil;
import view.tm.CashierTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

import static controller.LoginFormController.cashier;

public class ManageCashierFormController {
    public StackPane manageCashierContext;
    public JFXTextField txtCashierID;
    public JFXTextField txtName;
    public JFXTextField txtContact;
    public JFXTextField txtAddress;
    public JFXTextField txtNIC;
    public JFXPasswordField txtPassword;
    public TextField txtSearch;
    public Label lblNoOfCashier;
    public TableView<CashierTM> tblCashierDetails;
    public TableColumn colCashierID;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colNIC;
    public TableColumn colContact;
    public TableColumn colStatus;
    public TableColumn colUpdate;
    public TableColumn colRemove;
    public JFXButton btnAddUpdate;

    public void initialize() {
        setCashierID();
        try {
            loadData(new EmployeeController().getAllCashier());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        colCashierID.setCellValueFactory(new PropertyValueFactory<>("cashierID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("cashierName"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("cashierNIC"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("cashierAddress"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("update"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("remove"));

        storeValidations();
    }

    private void loadData(ArrayList<Cashier> allCash) throws SQLException, ClassNotFoundException {
        ArrayList<Cashier> allCashiers = allCash;
        ObservableList<CashierTM> obList = FXCollections.observableArrayList();

        for (Cashier temp : allCashiers) {
            Button btn = new Button("update");
            btn.setStyle("-fx-background-color: #1e7219; -fx-text-fill: white; -fx-cursor: hand;");
            Button btn2 = new Button("remove");
            btn2.setStyle("-fx-background-color: #a32020; -fx-text-fill: white; -fx-cursor: hand;");

            obList.add(new CashierTM(
                    temp.getCashierID(),
                    temp.getCashierName(),
                    temp.getCashierNIC(),
                    temp.getCashierAddress(),
                    temp.getContact(),
                    isOnline(temp.getCashierID()),
                    btn,
                    btn2
            ));
            btn.setOnAction((e) -> {
                setDetails(temp);

            });
            btn2.setOnAction((e) -> {
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure whether you want to delete Cashier ?", yes, no);
                alert.setTitle("Confirmation Alert");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.orElse(no) == yes) {
                    try {
                        if (new EmployeeController().removeCashier(temp.getCashierID())) {
                            loadData(new EmployeeController().getAllCashier());

                        }
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                } else {
                }
            });
        }
        tblCashierDetails.setItems(obList);
        lblNoOfCashier.setText(String.valueOf(obList.size()));
    }

    private String isOnline(String cashierID) {
        if(cashier!=null) {
            if (cashier.getCashierID().equals(cashierID)) {
                return "online";
            }
        }
        return "offline";
    }

    private void setDetails(Cashier temp) {
        txtCashierID.setText(temp.getCashierID());
        txtName.setText(temp.getCashierName());
        txtAddress.setText(temp.getCashierAddress());
        txtContact.setText(temp.getContact());
        txtNIC.setText(temp.getCashierNIC());
        txtPassword.setText(temp.getPassword());
        btnAddUpdate.setText("Update");
    }

    private void setCashierID() {
        try {
            txtCashierID.setText(new EmployeeController().getCashierID());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addCustomerOnActon(ActionEvent actionEvent) {
        Cashier cashier = new Cashier(
                txtCashierID.getText(),
                txtName.getText(),
                txtNIC.getText(),
                txtAddress.getText(),
                txtContact.getText(),
                txtPassword.getText()
        );

        try {
            if (btnAddUpdate.getText().equals("Add")) {
                if (new EmployeeController().saveCashier(cashier)) {
                    loadData(new EmployeeController().getAllCashier());
                    clearTextFields();
                }
            } else if (btnAddUpdate.getText().equals("Update")) {
                if (new EmployeeController().updateCashier(cashier)) {
                    loadData(new EmployeeController().getAllCashier());
                    clearTextFields();
                }
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearTextFields();
    }

    private void clearTextFields() {
        setCashierID();
        txtAddress.clear();
        txtContact.clear();
        txtName.clear();
        txtNIC.clear();
        btnAddUpdate.setText("Add");
    }

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap();
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");
    Pattern nicPattern = Pattern.compile("^[0-9]{9}?[v]$");
    Pattern contactPattern = Pattern.compile("^(071|077|078|075|076)[-]?[0-9]{7}$");

    private void storeValidations() {
        map.put(txtName, namePattern);
        map.put(txtAddress, addressPattern);
        map.put(txtNIC, nicPattern);
        map.put(txtContact, contactPattern);
    }
    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationJFX.validate(map,btnAddUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                //new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }
    }

    public void search_KeyReleased(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        if(!txtSearch.getText().equals("")) {
            loadData(new EmployeeController().getCashiers(txtSearch.getText()));
        }else {
            loadData(new EmployeeController().getAllCashier());
        }
    }
}
