package controller;

import controller.model_Controller.ProductController;
import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ManageCategoryFormController {
    public AnchorPane manageCategoryContext;
    public TextField txtCategoryName;
    public TextField txtSubCategoryName;
    public TextField txtCategoryID;
    public TextField txtSubCategoryID;
    public TableView tblCategory;
    public TableView tblSubCategory;
    public StackPane categoryContext;

    public void initialize() {
        try {
            setSubCategoryID();
            setCategoryID();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setSubCategoryID() throws SQLException, ClassNotFoundException {
        txtSubCategoryID.setText(new ProductController().getSubCategoryNo());

    }

    private void setCategoryID() throws SQLException, ClassNotFoundException {
        txtCategoryID.setText(new ProductController().getCategoryNo());
    }

    public void addCategory(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Category VALUES(?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, txtCategoryID.getText());
        stm.setObject(2, txtCategoryName.getText());

        if (stm.executeUpdate() > 0) {
            txtCategoryName.clear();
            setCategoryID();
        }
    }

    public void addSubCategory(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO SubCategory VALUES(?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, txtSubCategoryID.getText());
        stm.setObject(2, txtSubCategoryName.getText());

        if (stm.executeUpdate() > 0) {
            txtSubCategoryName.clear();
            setSubCategoryID();
        }
    }


    public void closeOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) categoryContext.getScene().getWindow();
        stage.close();
    }
}
