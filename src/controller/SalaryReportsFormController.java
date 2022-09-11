package controller;

import com.jfoenix.controls.JFXTextField;
import controller.model_Controller.AttendanceController;
import controller.model_Controller.EmployeeController;
import controller.model_Controller.SalaryController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Cashier;
import model.Employee;
import model.Salary;
import model.SalaryDetail;
import view.tm.SalaryTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static javafx.scene.paint.Color.TRANSPARENT;

public class SalaryReportsFormController {
    public StackPane salaryReportsContext;
    public Label lblCount;
    public ComboBox cmbJobStatus;
    public JFXTextField txtBasicSal;
    public JFXTextField txtEPF;
    public Button btnChange;
    public TableColumn colEmpID;
    public TableColumn colName;
    public TableColumn colStatus;
    public TableColumn colWorkDays;
    public TableColumn colBasicsal;
    public TableColumn colEpf;
    public TableColumn colSalary;
    public TableView<SalaryTM> tblSalary;
    public TextField txtMonth;
    public JFXTextField txtBasicSal1;
    public Label lblTotalSalary;
    public Button btnPaySal;
    String[] monthList = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    ObservableList<SalaryTM> obList = FXCollections.observableArrayList();
    private int monthNo = 0;
    private String salDate;
    private String monthNow;

    public void initialize() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd-MMM-yyyy");
        salDate = dateFormat.format(new Date());

        Date date = new Date();
        SimpleDateFormat month = new SimpleDateFormat("MMM");
        monthNow = month.format(date);
        for (int i = 0; i < monthList.length; i++) {
            if (monthList[i].equals(monthNow)) {
                monthNo = i;
                setMonth();
            }
        }


        String[] in = {"Cashier", "Salesman", "Security"};
        cmbJobStatus.getItems().addAll(in);

        colEmpID.setCellValueFactory(new PropertyValueFactory<>("empID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colWorkDays.setCellValueFactory(new PropertyValueFactory<>("workDays"));
        colBasicsal.setCellValueFactory(new PropertyValueFactory<>("basicSal"));
        colEpf.setCellValueFactory(new PropertyValueFactory<>("epf"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("Salary"));
    }

    private void loadAllEmployees(ArrayList<Cashier> allCashier, ArrayList<Employee> allEmployees) throws SQLException, ClassNotFoundException {
        ArrayList<SalaryDetail> allEmp = new ArrayList<>();
        int selectedMonthNo = 1;
        for (int i = 0; i < monthList.length; i++) {
            if (monthList[i].equals(txtMonth.getText())) {
                selectedMonthNo = i + 1;
                //System.out.println("select mon "+selectedMonthNo);
            }
        }
        for (Cashier c : allCashier) {
            int count = new AttendanceController().getWorkDaysCount(c.getCashierID(), selectedMonthNo);
            allEmp.add(new SalaryDetail(c.getCashierID(), c.getCashierName(), txtMonth.getText(), "Cashier", count, 2000, 15000.00));
        }
        for (Employee e : allEmployees) {
            int count = new AttendanceController().getWorkDaysCount(e.getEmployeeID(), selectedMonthNo);
            allEmp.add(new SalaryDetail(e.getEmployeeID(), e.getEmployeeName(), txtMonth.getText(), e.getState(), count, 1500, 10000.00));
        }

        for (SalaryDetail temp : allEmp) {
            double totalSal = temp.getBasicSal() + (temp.getWorkDays() * 1000);
            totalSal -= temp.getEpf();
            obList.add(new SalaryTM(temp.getEmpID(), temp.getName(), temp.getStatus(), temp.getWorkDays(), temp.getBasicSal(), temp.getEpf(), totalSal));

        }
        tblSalary.setItems(obList);
        lblCount.setText(String.valueOf(obList.size()));
        setTotal(obList);
    }

    private void setTotal(ObservableList<SalaryTM> obList) {
        double tot = 0;
        for (SalaryTM tm : obList) {
            tot += tm.getSalary();
        }
        lblTotalSalary.setText(String.valueOf(tot));
    }

    public void changeSalaryOnAction(ActionEvent actionEvent) {

    }

    public void payAllSalaryOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (monthNow.equals(txtMonth.getText())) {
            Salary salary = new Salary(
                    new SalaryController().getSalaryNo(),
                    salDate,
                    Double.parseDouble(lblTotalSalary.getText())
            );

            ArrayList<SalaryDetail> salaryDetails = new ArrayList<>();
            for (SalaryTM tm : obList) {
                salaryDetails.add(new SalaryDetail(
                        tm.getEmpID(),
                        tm.getName(),
                        salDate,
                        tm.getStatus(),
                        tm.getWorkDays(),
                        tm.getEpf(),
                        tm.getBasicSal()
                ));
            }

            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure whether you want to Pay salary from today ?", yes, no);
            alert.setTitle("Confirmation Alert");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(no) == yes) {
                try {
                    if (new SalaryController().setSalary(salary)) {
                        if (new SalaryController().setSalaryDetail(salaryDetails)) {
                            new Alert(Alert.AlertType.INFORMATION, "Successfully..").show();
                        }
                    }
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            } else {
            }
        } else {
        }
    }

    public void downMonth(ActionEvent actionEvent) {
        if (monthNo > 0) {
            monthNo -= 1;
        } else {
            monthNo = 11;
        }
        setMonth();
    }

    public void upMonth(ActionEvent actionEvent) {
        if (monthNo < 11) {
            monthNo += 1;
        } else {
            monthNo = 0;
        }
        setMonth();
    }

    private void setMonth() {
        txtMonth.setText(monthList[monthNo]);
        try {
            if (new SalaryController().isExit(txtMonth.getText())) {
                btnPaySal.setDisable(true);
                ArrayList<SalaryDetail> salaryDetail = new SalaryController().getSalaryDetail(txtMonth.getText());
                obList.clear();
                loadSalary(salaryDetail);
            } else {
                btnPaySal.setDisable(false);
                obList.clear();
                ArrayList<Cashier> allCashier = new EmployeeController().getAllCashier();
                ArrayList<Employee> allEmployees = new EmployeeController().getAllEmployees();
                loadAllEmployees(allCashier, allEmployees);
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    private void loadSalary(ArrayList<SalaryDetail> salaryDetail) {
        for (SalaryDetail temp : salaryDetail) {
            double totalSal = temp.getBasicSal() + (temp.getWorkDays() * 1000);
            totalSal -= temp.getEpf();
            obList.add(new SalaryTM(temp.getEmpID(), temp.getName(), temp.getStatus(), temp.getWorkDays(), temp.getBasicSal(), temp.getEpf(), totalSal));

        }
        tblSalary.setItems(obList);
        lblCount.setText(String.valueOf(obList.size()));
        setTotal(obList);
    }


    public void openAttendanceList(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../view/AttendanceListForm.fxml")));
        scene.setFill(TRANSPARENT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.centerOnScreen();
        stage.show();
    }
}
