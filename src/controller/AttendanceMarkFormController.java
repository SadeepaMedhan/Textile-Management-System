package controller;

import controller.model_Controller.AttendanceController;
import controller.model_Controller.EmployeeController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Attendance;
import model.Cashier;
import model.Employee;


import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;


public class AttendanceMarkFormController {
    public TextField txtEmpID;
    public Label lblName;
    public Label lblTime;
    public Label lblDate;
    public StackPane attendanceContext;
    public Button btnAdd;
    private String attID;
    boolean isNewAttend=false;

    public void initialize(){
        setDateTime();
    }

    private void setDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dateNow = dateFormat.format(new Date());
        lblDate.setText(dateNow);

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
        }), new KeyFrame(Duration.seconds(1)));

        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void markAttOnAction(ActionEvent actionEvent) {

        try {

            if(isNewAttend){
                if(new AttendanceController().markAttend(new Attendance(
                        attID,
                        txtEmpID.getText(),
                        lblTime.getText(),
                        "-",
                        lblDate.getText()
                ))){
                    btnAdd.setDisable(true);
                    txtEmpID.clear();
                    lblName.setText("");
                }
            }else{
                if (new AttendanceController().markLeaveTime(attID,lblTime.getText())) {
                    btnAdd.setDisable(true);
                    txtEmpID.clear();
                    lblName.setText("");
                }
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void empIDSearch_keyRelease(ActionEvent keyEvent) {
        try {
            ArrayList<Attendance> todayAllAttend = new AttendanceController().getTodayAllAttend(lblDate.getText());
            Employee employee = new EmployeeController().getEmployee(txtEmpID.getText());
            Cashier cashier = new EmployeeController().getCashier(txtEmpID.getText());
            if(employee!=null) {
                txtEmpID.setStyle("-fx-border-color: green");
                btnAdd.setDisable(false);
                if (isExit(todayAllAttend)) {
                    lblName.setText("Good bye,\n" + employee.getEmployeeName());
                    isNewAttend=false;
                }else{
                    lblName.setText("Good Morning,\n" + employee.getEmployeeName());
                    attID=new AttendanceController().getAttendNo();
                    isNewAttend=true;
                }
            }else if(cashier!=null) {
                txtEmpID.setStyle("-fx-border-color: green");
                btnAdd.setDisable(false);
                if (isExit(todayAllAttend)) {
                    lblName.setText("Good bye,\n" + cashier.getCashierName());
                    isNewAttend=false;
                }else{
                    lblName.setText("Good Morning,\n" + cashier.getCashierName());
                    attID=new AttendanceController().getAttendNo();
                    isNewAttend=true;
                }
            }else{
                txtEmpID.setStyle("-fx-border-color: red");
                btnAdd.setDisable(true);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private boolean isExit(ArrayList<Attendance> todayAllAttend) throws SQLException, ClassNotFoundException {
        for (Attendance a : todayAllAttend) {
            if (a.getEmployeeID().equals(txtEmpID.getText())) {
                attID = a.getAttID();
                return true;
            }
        }
        return false;
    }

    public void closeOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) attendanceContext.getScene().getWindow();
        stage.close();
    }
}
