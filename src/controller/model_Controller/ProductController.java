package controller.model_Controller;

import db.DbConnection;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductController {
    public String getProductNo() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT productID FROM Product ORDER BY productID DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempID = Integer.parseInt(rst.getString(1).split("P")[1]);
            tempID = tempID + 1;
            if (tempID <= 9) {
                return "P00" + tempID;
            } else if (tempID <= 99) {
                return "P0" + tempID;
            } else {
                return "P" + tempID;
            }
        } else {
            return "P001";
        }
    }

    public boolean saveProduct(Product p) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Product VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, p.getProductID());
        stm.setObject(2, p.getName());
        stm.setObject(3, p.getMaterial());
        stm.setObject(4, p.getSize());
        stm.setObject(5, p.getColorCode());
        stm.setObject(6, p.getUnitPrice());
        stm.setObject(7, p.getQtyOnHand());
        stm.setObject(8, p.getBrandID());
        stm.setObject(9, p.getCategoryID());
        stm.setObject(10, p.getSubCategoryID());
        stm.setObject(11, p.getImageSrc());

        return stm.executeUpdate() > 0;
    }

    public ArrayList<Product> getAllProducts() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Product").executeQuery();
        ArrayList<Product> productArrayList = new ArrayList<>();
        while (rst.next()) {
            productArrayList.add(new Product(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getString(5),
                            rst.getDouble(6),
                            rst.getInt(7),
                            rst.getString(8),
                            rst.getString(9),
                            rst.getString(10),
                            rst.getString(11)
                    )
            );
        }
        return productArrayList;
    }

    public ArrayList<Product> searchProducts(String value) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Product WHERE name LIKE '%" + value + "%' || productID LIKE '%" + value + "%'").executeQuery();
        ArrayList<Product> productList = new ArrayList<>();
        while (rst.next()) {
            productList.add(new Product(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getString(5),
                            rst.getDouble(6),
                            rst.getInt(7),
                            rst.getString(8),
                            rst.getString(9),
                            rst.getString(10),
                            rst.getString(11)
                    )
            );
        }
        return productList;
    }


    public Product getProduct(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Product WHERE productID='" + id + "'").executeQuery();

        if (rst.next()) {
            return new Product(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(6),
                    rst.getInt(7),
                    rst.getString(8),
                    rst.getString(9),
                    rst.getString(10),
                    rst.getString(11)
            );
        } else {
            return null;
        }
    }

    public boolean updateProduct(Product product) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("UPDATE Product SET name=?, material=?, size=?, colorCode=?, unitPrice=?, qtyOnHand=?, brandID=?, categoryID=?, subCategoryID=?, imageSrc=? WHERE productID=?");
        stm.setObject(1, product.getName());
        stm.setObject(2, product.getMaterial());
        stm.setObject(3, product.getSize());
        stm.setObject(4, product.getColorCode());
        stm.setObject(5, product.getUnitPrice());
        stm.setObject(6, product.getQtyOnHand());
        stm.setObject(7, product.getBrandID());
        stm.setObject(8, product.getCategoryID());
        stm.setObject(9, product.getSubCategoryID());
        stm.setObject(10, product.getImageSrc());
        stm.setObject(11, product.getProductID());

        return stm.executeUpdate() > 0;
    }

    public boolean deleteProduct(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Product WHERE productID='" + id + "'").executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }
    }

    //===============================================================Brand==============================================
    public String getBrandNo() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT brandID FROM Brand ORDER BY brandID DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempID = Integer.parseInt(rst.getString(1).split("B")[1]);
            tempID = tempID + 1;
            if (tempID <= 9) {
                return "B00" + tempID;
            } else if (tempID <= 99) {
                return "B0" + tempID;
            } else {
                return "B" + tempID;
            }
        } else {
            return "B001";
        }
    }

    public String getBrandName(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Brand WHERE brandID=?");
        stm.setObject(1, id);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return rst.getString(2);
        } else {
            return null;
        }
    }

    public String getCategoryNo() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT categoryID FROM Category ORDER BY categoryID DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempID = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempID = tempID + 1;
            if (tempID <= 9) {
                return "Ca-00" + tempID;
            } else if (tempID <= 99) {
                return "Ca-0" + tempID;
            } else {
                return "Ca-" + tempID;
            }
        } else {
            return "Ca-001";
        }
    }

    public String getCategoryName(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Category WHERE categoryID=?");
        stm.setObject(1, id);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return rst.getString(2);
        } else {
            return null;
        }
    }

    public String getSubCategoryNo() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT subCategoryID FROM SubCategory ORDER BY subCategoryID DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempID = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempID = tempID + 1;
            if (tempID <= 9) {
                return "Sc-00" + tempID;
            } else if (tempID <= 99) {
                return "Sc-0" + tempID;
            } else {
                return "Sc-" + tempID;
            }
        } else {
            return "Sc-001";
        }
    }

    public String getSubCatName(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM SubCategory WHERE subCategoryID=?");
        stm.setObject(1, id);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return rst.getString(2);
        } else {
            return null;
        }
    }

    public int getProductCount() throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(*) FROM Product";
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement(query).executeQuery();
        if (rst.next()) {
            return rst.getInt(1);
        }
        return 0;
    }

    public boolean addStockFromReturn(String productNo, int qty) throws SQLException, ClassNotFoundException {
        Product product = getProduct(productNo);
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Product SET qtyOnHand=? WHERE productID=?");
        stm.setObject(1, (product.getQtyOnHand() + qty));
        stm.setObject(2, productNo);

        return stm.executeUpdate() > 0;
    }
}
