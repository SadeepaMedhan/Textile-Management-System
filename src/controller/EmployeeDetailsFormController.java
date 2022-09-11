package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import model.Employee;
import util.ValidationJFX;
import util.ValidationUtil;
import view.tm.EmployeeTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class EmployeeDetailsFormController {

    public StackPane employeeContext;
    public JFXTextField txtEmpID;
    public JFXTextField txtName;
    public JFXTextField txtContact;
    public JFXTextField txtAddress;
    public JFXTextField txtNIC;
    public JFXComboBox<String> cmbStatus;
    public TableView<EmployeeTM> tblEmpDetail;
    public TableColumn colEmpID;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colNIC;
    public TableColumn colContact;
    public TableColumn colStatus;
    public TableColumn colUpdate;
    public TableColumn colRemove;
    public Label lblCount;
    public JFXButton btnAddUpdate;
    public TextField txtSearchEmp;

    public void initialize() {
        setEmpID();
        btnAddUpdate.setDisable(true);
        storeValidations();
        try {
            loadData(new EmployeeController().getAllEmployees());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        String[] status = {"Salesman", "Security"};
        cmbStatus.getItems().addAll(status);


        colEmpID.setCellValueFactory(new PropertyValueFactory<>("empID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("empNIC"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("empAddress"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("update"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("remove"));
    }

    private void loadData(ArrayList<Employee> employees) throws SQLException, ClassNotFoundException {
        ArrayList<Employee> allEmployee = employees;
        ObservableList<EmployeeTM> obList = FXCollections.observableArrayList();

        for (Employee temp : allEmployee) {

            Button btn = new Button("update");
            btn.setStyle("-fx-background-color: #1e7219; -fx-text-fill: white; -fx-cursor: hand;");
            Button btn2 = new Button("remove");
            btn2.setStyle("-fx-background-color: #a32020; -fx-text-fill: white; -fx-cursor: hand;");

            EmployeeTM tm = new EmployeeTM(
                    temp.getEmployeeID(),
                    temp.getEmployeeName(),
                    temp.getEmployeeNIC(),
                    temp.getEmployeeAddress(),
                    temp.getContact(),
                    temp.getState(),
                    btn,
                    btn2
            );

            obList.add(tm);
            btn.setOnAction((e) -> {
                setDetails(temp);
            });
            btn2.setOnAction((e) -> {
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure whether you want to delete Employee ?", yes, no);
                alert.setTitle("Confirmation Alert");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.orElse(no) == yes) {
                    try {
                        if (new EmployeeController().removeEmp(temp.getEmployeeID())) {
                            loadData(new EmployeeController().getAllEmployees());
                        }
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }

                } else {
                }
            });
        }
        tblEmpDetail.setItems(obList);
        lblCount.setText(String.valueOf(obList.size()));
    }

    private void setDetails(Employee tm) {
        txtEmpID.setText(tm.getEmployeeID());
        txtName.setText(tm.getEmployeeName());
        txtNIC.setText(tm.getEmployeeNIC());
        txtAddress.setText(tm.getEmployeeAddress());
        txtContact.setText(tm.getContact());
        cmbStatus.getSelectionModel().select(tm.getState());
        btnAddUpdate.setText("Update");
    }


    private void setEmpID() {
        try {
            txtEmpID.setText(new EmployeeController().getEmpID());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addOnAction(ActionEvent actionEvent) {
        Employee employee = new Employee(
                txtEmpID.getText(),
                txtName.getText(),
                txtNIC.getText(),
                txtAddress.getText(),
                txtContact.getText(),
                cmbStatus.getValue()
        );

        try {
            if (btnAddUpdate.getText().equals("Add")) {
                if (new EmployeeController().saveEmployee(employee)) {
                    loadData(new EmployeeController().getAllEmployees());
                    clearTextFields();
                }
            } else if (btnAddUpdate.getText().equals("Update")) {
                if (new EmployeeController().updateEmp(employee)) {
                    loadData(new EmployeeController().getAllEmployees());
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
        setEmpID();
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
        if(!txtSearchEmp.getText().equals("")) {
            loadData(new EmployeeController().getEmployees(txtSearchEmp.getText()));
        }else {
            loadData(new EmployeeController().getAllEmployees());
        }
    }
}
