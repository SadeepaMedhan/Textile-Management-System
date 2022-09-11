package controller;

import controller.model_Controller.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import model.*;
import view.tm.DailyIncomeTM;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class IncomeReportsFormController {
    public StackPane incomeReportsContext;
    public ComboBox<String> cmbIncome;

    public TableView<DailyIncomeTM> tblDayIncome;
    public TableColumn colDate;
    public TableColumn colTotalSale;
    public TableColumn colIncome;
    public TableColumn colExpense;
    public TableColumn colDescription;
    public TableColumn colGrandTotal;


    public TableView<DailyIncomeTM> tblMonthly;
    public TableColumn colMonth;
    public TableColumn colMonthSalesQty;
    public TableColumn colMonthIncome;
    public TableColumn colMonthExpense;
    public TableColumn colMonthDesc;
    public TableColumn colMonthTotal;

    public TextField txtMonth;
    public Group monthSelector;
    public Group yearSelector;
    public TextField txtYear;
    public PieChart pieChartYear;
    public PieChart pieChartMonth;
    public Label lblQty;
    public Label lblTotal;
    String[] monthList = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    String[] years = new String[]{"2021", "2022", "2023", "2024", "2025"};
    private int monthNo = 0;
    private int year = 0;

    public void initialize() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM");
        String dateNow = dateFormat.format(date);
        for (int i = 0; i < monthList.length; i++) {
            if (monthList[i].equals(dateNow)) {
                monthNo = i;
                setMonth();
            }
        }
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        String yearNow = yearFormat.format(date);
        for (int i = 0; i < years.length; i++) {
            if (years[i].equals(yearNow)) {
                year = i;
                setYear();
            }
        }

        cmbIncome.setStyle("-fx-text-fill: white;");
        String[] in = {"Monthly Income", "Annual Income"};
        cmbIncome.getItems().addAll(in);
        cmbIncome.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setTable(newValue);
        });

        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTotalSale.setCellValueFactory(new PropertyValueFactory<>("totalSale"));
        colIncome.setCellValueFactory(new PropertyValueFactory<>("income"));
        colExpense.setCellValueFactory(new PropertyValueFactory<>("expense"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colGrandTotal.setCellValueFactory(new PropertyValueFactory<>("grandTotal"));

        colMonth.setCellValueFactory(new PropertyValueFactory<>("date"));
        colMonthSalesQty.setCellValueFactory(new PropertyValueFactory<>("totalSale"));
        colMonthIncome.setCellValueFactory(new PropertyValueFactory<>("income"));
        colMonthExpense.setCellValueFactory(new PropertyValueFactory<>("expense"));
        colMonthDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colMonthTotal.setCellValueFactory(new PropertyValueFactory<>("grandTotal"));

       setTable("Annual Income");
    }

    private void setTable(String newValue) {
        switch (newValue) {

            case "Monthly Income":
                yearSelector.setVisible(false);
                monthSelector.setVisible(true);
                pieChartYear.setVisible(false);
                pieChartMonth.setVisible(true);
                showTable(tblDayIncome);
                loadData(txtMonth.getText());
                break;
            case "Annual Income":
                monthSelector.setVisible(false);
                yearSelector.setVisible(true);
                pieChartYear.setVisible(true);
                pieChartMonth.setVisible(false);
                showTable(tblMonthly);
                loadDataAnnually(txtYear.getText());
                break;
        }
    }

    private void loadDataAnnually(String year) {
        ObservableList<PieChart.Data> annual = FXCollections.observableArrayList();

        try {
            ArrayList<String> allYearIncome = new IncomeController().getAllMonthIncome(year);

            for (String y : allYearIncome) {
                ArrayList<String> allInvoiceNo = new IncomeController().getAllInvoiceNo(year);
                ArrayList<String> allExpense = new IncomeController().getAllExpense(year);

                int qty = 0;
                double total = 0;
                double expense = 0;
                String sales = "";
                String waste = "";
                String salary = "";

                for (String i : allInvoiceNo) {
                    if (!i.equals("-")) {
                        Payment payment = new PaymentController().getPayment(i);
                        total += payment.getTotalAmount();
                        Order order = new OrderController().getOrder(payment.getOrderID());
                        qty += order.getProducts().size();
                        sales = "Sales, ";
                    }
                }
                for (String e : allExpense) {
                    if (!e.equals("-")) {
                        String de = new IncomeController().getDesc(e);
                        if (de.equals("Waste")) {
                            Returns aReturn = new ReturnController().getReturn(e);
                            expense += aReturn.getTotalPrice();
                            waste = "Waste, ";
                        } else if (de.equals("Salary")) {
                            salary = "Salary";
                        }
                    }
                }
                annual.add(new PieChart.Data("Income", total));
                annual.add(new PieChart.Data("Expense", expense));
                annual.add(new PieChart.Data("Profit", Double.parseDouble(y)));

            }

            pieChartYear.setData(annual);
            loadTable(year);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadTable(String selectYear) {
        ObservableList<DailyIncomeTM> obListMonth = FXCollections.observableArrayList();

        try {
            for (String month : monthList) {

                ArrayList<Income> allMonthIncome = new IncomeController().getAllMonthlyIncome(month, selectYear);
                double grandTotal = new IncomeController().getTotalRevenue(month, selectYear);
                int qty = 0;
                double total = 0;
                double expense = 0;
                String sales = "";
                String waste = "";
                String salary = "";
                for (Income income : allMonthIncome) {

                    if (!income.getInvoiceNo().equals("-")) {
                        Payment payment = new PaymentController().getPayment(income.getInvoiceNo());
                        total += payment.getTotalAmount();
                        Order order = new OrderController().getOrder(payment.getOrderID());
                        qty += order.getProducts().size();
                        sales = "Sales, ";
                    }

                    if (!income.getSalaryID().equals("-")) {
                        String de = new IncomeController().getDesc(income.getSalaryID());
                        if (de.equals("Waste")) {
                            Returns aReturn = new ReturnController().getReturn(income.getSalaryID());
                            expense += aReturn.getTotalPrice();
                            waste = "Waste, ";
                        } else if (de.equals("Salary")) {
                            Salary sal = new SalaryController().getSalary(income.getSalaryID());
                            expense += sal.getSalaryAmount();
                            salary = "Salary";
                        }
                    }
                }
                obListMonth.add(new DailyIncomeTM(month, qty, total, expense, (sales + waste + salary), grandTotal));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        tblMonthly.setItems(obListMonth);
        calculate(obListMonth);
    }

    private void calculate(ObservableList<DailyIncomeTM> obList) {
        double tot=0;
        int qty=0;
        for (DailyIncomeTM tm:obList) {
            tot+=tm.getGrandTotal();
            qty+=tm.getTotalSale();
        }
        lblQty.setText(String.valueOf(qty));
        lblTotal.setText(String.valueOf(tot));
    }


    //======================================================================================daily
    private void loadData(String month) {
        ObservableList<PieChart.Data> monthly = FXCollections.observableArrayList();

        try {

            DateGroup allDays = new IncomeController().getAllDays(month);
            double total = 0;
            double expense = 0;

            ArrayList<String> allInvoiceNo = new IncomeController().getAllInvoiceNo(month);
            ArrayList<String> allExpense = new IncomeController().getAllExpense(month);

            for (String i : allInvoiceNo) {
                if (!i.equals("-")) {
                    Payment payment = new PaymentController().getPayment(i);
                    total += payment.getTotalAmount();
                }
            }
            for (String e : allExpense) {
                if (!e.equals("-")) {
                    String de = new IncomeController().getDesc(e);
                    if (de.equals("Waste")) {
                        Returns aReturn = new ReturnController().getReturn(e);
                        expense += aReturn.getTotalPrice();
                    } else if (de.equals("Salary")) {

                    }
                }
            }
            monthly.add(new PieChart.Data("Income", total));
            monthly.add(new PieChart.Data("Expense", expense));
            monthly.add(new PieChart.Data("Profit", allDays.getBalance()));

            pieChartMonth.setData(monthly);
            loadDailyTable(month);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadDailyTable(String month) {
        ObservableList<DailyIncomeTM> obListMonth = FXCollections.observableArrayList();

        try {
            ArrayList<DateGroup> allDayList = new IncomeController().getAllDayList();

            for (DateGroup day : allDayList) {
                String m = day.getDay().split("-")[1];
                if (m.equals(month)) {
                    ArrayList<Income> allMonthIncome = new IncomeController().getMonthlyIncome(day.getDay());
                    int qty = 0;
                    double total = 0;
                    double expense = 0;
                    String sales = "";
                    String waste = "";
                    String salary = "";
                    for (Income income : allMonthIncome) {

                        if (!income.getInvoiceNo().equals("-")) {
                            Payment payment = new PaymentController().getPayment(income.getInvoiceNo());
                            total += payment.getTotalAmount();
                            Order order = new OrderController().getOrder(payment.getOrderID());
                            qty += order.getProducts().size();
                            sales = "Sales, ";
                        }

                        if (!income.getSalaryID().equals("-")) {
                            String de = new IncomeController().getDesc(income.getSalaryID());
                            if (de.equals("Waste")) {
                                Returns aReturn = new ReturnController().getReturn(income.getSalaryID());
                                expense += aReturn.getTotalPrice();
                                waste = "Waste, ";
                            } else if (de.equals("Salary")) {
                                Salary sal = new SalaryController().getSalary(income.getSalaryID());
                                expense += sal.getSalaryAmount();
                                salary = "Salary";
                            }
                        }

                    }
                    obListMonth.add(new DailyIncomeTM(day.getDay(), qty, total, expense, (sales + waste + salary), day.getBalance()));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        tblDayIncome.setItems(obListMonth);
        calculate(obListMonth);
    }

    private void showTable(TableView tbl) {
        tblDayIncome.setVisible(false);
        tblMonthly.setVisible(false);
        tbl.setVisible(true);
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
        loadData(txtMonth.getText());
    }

    private void setYear() {
        txtYear.setText(years[year]);
        loadDataAnnually(txtYear.getText());
    }

    public void downYear(ActionEvent actionEvent) {
        if (year > 0) {
            year -= 1;
        } else {
            year = 4;
        }
        setYear();
    }

    public void upYear(ActionEvent actionEvent) {
        if (year < 4) {
            year += 1;
        } else {
            year = 0;
        }
        setYear();
    }
}
