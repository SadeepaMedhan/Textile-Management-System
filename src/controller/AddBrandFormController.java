package controller;

import controller.model_Controller.ProductController;
import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddBrandFormController {
    public TextField txtBrandID;
    public TextField txtName;
    public TextField txtDesc;
    public StackPane brandAddContext;

    public void initialize(){
        setBrandID();
    }

    private void setBrandID() {
        try {
            txtBrandID.setText(new ProductController().getBrandNo());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addBrandOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Brand VALUES(?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, txtBrandID.getText());
        stm.setObject(2, txtName.getText());
        stm.setObject(3, txtDesc.getText());

        if (stm.executeUpdate() > 0) {
            setBrandID();
            System.out.println("saved");
        }
    }

    public void clearOnAction(ActionEvent actionEvent) {

    }

    public void closeOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) brandAddContext.getScene().getWindow();
        stage.close();
    }
}
