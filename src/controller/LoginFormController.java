package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controller.model_Controller.EmployeeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Cashier;
import model.Payment;
import util.ValidationUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

import static javafx.scene.paint.Color.TRANSPARENT;

public class LoginFormController {
    public static Cashier cashier;
    public StackPane loginFormContext;
    public JFXTextField txtUsername;
    public JFXPasswordField txtPassword;

    public void loginOnAction(ActionEvent actionEvent) throws IOException {

        if (txtUsername.getText().equalsIgnoreCase("admin")) {//  <<<<<<<<<<
            if (txtPassword.getText().equalsIgnoreCase("1234")) {//  <<<<<<<<<
                Stage stage = (Stage) loginFormContext.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
                stage.setResizable(false);
                stage.centerOnScreen();
                stage.show();
            }else{
                if (txtPassword.isFocused()) {
                    txtPassword.setFocusColor(Paint.valueOf("#ff0000"));
                }else{
                    txtPassword.setUnFocusColor(Paint.valueOf("#ff0000"));
                }
            }
        }
        else if (isValid(txtUsername.getText())) {//  <<<<<<<<<<
            if(passwordCheck(txtPassword.getText())) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ConfirmationAlertForm.fxml"));
                Parent load = loader.load();
                ConfirmationAlertFormController controller = loader.getController();
                controller.setContext(loginFormContext);
                Stage stage = new Stage();
                Scene scene = new Scene(load);
                scene.setFill(TRANSPARENT);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.centerOnScreen();
                stage.show();
                txtPassword.clear();
                /*
                ButtonType pos = new ButtonType("POS", ButtonBar.ButtonData.OK_DONE);
                ButtonType returns = new ButtonType("RETURNS", ButtonBar.ButtonData.OK_DONE);
                ButtonType attend = new ButtonType("ATTEND", ButtonBar.ButtonData.OK_DONE);

                Alert alert = new Alert(Alert.AlertType.NONE, "Welcome, "+cashier.getCashierName()+"\n\nWhat do you want to do ?", pos, returns, attend);
                Optional<ButtonType> result = alert.showAndWait();

                if (result.orElse(returns) == pos && result.orElse(attend) == pos) {
                    Stage stage = (Stage) loginFormContext.getScene().getWindow();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/HomePage.fxml"))));
                    stage.setResizable(false);
                    stage.centerOnScreen();
                    stage.show();
                } else if (result.orElse(pos) == returns && result.orElse(attend) == returns) {
                    Stage stage = (Stage) loginFormContext.getScene().getWindow();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageReturnsProductForm.fxml"))));
                    stage.setResizable(false);
                    stage.centerOnScreen();
                    stage.show();
                } else if (result.orElse(pos) == attend && result.orElse(returns) == attend) {
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../view/AttendanceMarkForm.fxml")));
                    scene.setFill(TRANSPARENT);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.centerOnScreen();
                    stage.show();
                } else {
                }*/
            }else{
                if (txtPassword.isFocused()) {
                    txtPassword.setFocusColor(Paint.valueOf("#ff0000"));
                }else{
                    txtPassword.setUnFocusColor(Paint.valueOf("#ff0000"));
                }
            }
        }else {
            if (txtUsername.isFocused()) {
                txtUsername.setFocusColor(Paint.valueOf("#ff0000"));
            }else{
                txtUsername.setUnFocusColor(Paint.valueOf("#ff0000"));
            }
        }
    }

    private boolean passwordCheck(String text) {
        return cashier.getPassword().equals(text);
    }

    private boolean isValid(String text) {
        try {
            ArrayList<Cashier> allCashier = new EmployeeController().getAllCashier();
            for (Cashier c:allCashier) {
                if(c.getCashierID().equalsIgnoreCase(text)){
                    cashier=c;
                    return true;
                }
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void txtUserNameMouseClicked(MouseEvent mouseEvent) {
        txtUsername.setFocusColor(Paint.valueOf("#0000ff"));
        txtUsername.setUnFocusColor(Paint.valueOf("#0000ff"));
    }

    public void txtPasswordMouseClicked(MouseEvent mouseEvent) {
        txtPassword.setFocusColor(Paint.valueOf("#0000ff"));
        txtPassword.setUnFocusColor(Paint.valueOf("#0000ff"));
    }

    public void closeOnAction(MouseEvent mouseEvent) {
        System.exit(0);
    }
}
