package config;

import java.sql.*;

public class databaseConnector {

    public Connection connection;
    public PreparedStatement pst;

    public databaseConnector() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/queenz_inventory", "root", "");
        } catch (SQLException e) {
            System.out.println("Cannot connect to database: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public ResultSet getData(String sql) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rst = stmt.executeQuery(sql);

        return rst;
    }

    public void deleteProduct(int item_id) {
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement("DELETE FROM tbl_items WHERE item_SKU = ?");
            stmt.setInt(1, item_id);
            stmt.executeUpdate();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error deleting data: " + e.getMessage());
        }
    }

    public int updateData(String sql) {
        int num = 0;
        try {

            String query = sql;
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Data updated successfully!");
                num = 1;
            } else {
                System.out.println("Data update failed!");
                num = 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return num;
    }
}
