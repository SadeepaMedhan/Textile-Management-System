package controller;

import controller.model_Controller.AttendanceController;
import controller.model_Controller.CustomerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Attendance;
import model.Customer;
import view.tm.CustomerTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class AttendanceListFormController {
    public StackPane attendListContext;
    public TableView<Attendance> tblAttend;
    public TableColumn colDate;
    public TableColumn colName;
    public TableColumn colInTile;
    public TableColumn colOutTime;
    public TextField txtSearch;

    public void initialize(){
        try {
            loadData(new AttendanceController().getAllAttend());

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colName.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        colInTile.setCellValueFactory(new PropertyValueFactory<>("inTime"));
        colOutTime.setCellValueFactory(new PropertyValueFactory<>("outTime"));

    }

    private void loadData(ArrayList<Attendance> todayAllAttend) {
        ArrayList<Attendance> attendList = todayAllAttend;
        ObservableList<Attendance> obList = FXCollections.observableArrayList();

        for (Attendance temp:attendList) {

            obList.add(new Attendance("", temp.getEmployeeID(), temp.getInTime(), temp.getOutTime(), temp.getDate()));

        }
        tblAttend.setItems(obList);
    }

    public void closeOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) attendListContext.getScene().getWindow();
        stage.close();
    }

    public void searchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        loadData(new AttendanceController().getTodayAllAttend(txtSearch.getText()));
    }
}
