package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import static controller.LoginFormController.cashier;
import static javafx.scene.paint.Color.TRANSPARENT;

public class ConfirmationAlertFormController {
    public Label lblName;
    public StackPane alertContext;
    StackPane loginFormContext;
    public  void initialize(){
        lblName.setText(cashier.getCashierName());
    }
    public void openPOS(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) loginFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/HomePage.fxml"))));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
        Stage window = (Stage) alertContext.getScene().getWindow();
        window.close();
    }

    public void openReturn(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) loginFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageReturnsProductForm.fxml"))));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
        Stage window = (Stage) alertContext.getScene().getWindow();
        window.close();
    }

    public void openAttend(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../view/AttendanceMarkForm.fxml")));
        scene.setFill(TRANSPARENT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.centerOnScreen();
        stage.show();
        Stage window = (Stage) alertContext.getScene().getWindow();
        window.close();
    }

    public void setContext(StackPane loginFormContext) {
        this.loginFormContext=loginFormContext;
    }
}
