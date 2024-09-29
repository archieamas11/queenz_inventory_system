/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;

import config.databaseConnector;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author ArchieQT
 */
public class display_items {

    private static void display_query(JTable table, String query) {
        try {
            databaseConnector dbc = new databaseConnector();
            PreparedStatement statement = dbc.getConnection().prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            // Use DbUtils to set the model
            table.setModel(DbUtils.resultSetToTableModel(rs));

            // Set all table headers to bold
            JTableHeader tableHeader = table.getTableHeader();
            tableHeader.setFont(new Font("Arial", Font.BOLD, 13));

            // Set up cell renderers
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

            // Apply center alignment to all columns
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            // Create a specific renderer for the Status column
            DefaultTableCellRenderer statusRenderer = new DefaultTableCellRenderer() {
                @Override
                public void setValue(Object value) {
                    super.setValue(value);
                    if ("available".equals(value)) {
                        setForeground(new java.awt.Color(0, 128, 0));  // Green
                    } else if ("archived".equals(value)) {
                        setForeground(new java.awt.Color(128, 128, 128));  // Gray
                    } else if ("soldout".equals(value)) {
                        setForeground(new java.awt.Color(255, 0, 0));  // Red
                    } else if ("discontinued".equals(value)) {
                        setForeground(new java.awt.Color(255, 165, 0));  // Orange
                    } else {
                        setForeground(java.awt.Color.BLACK);  // Default color
                    }
                    setFont(getFont().deriveFont(Font.BOLD));
                    setHorizontalAlignment(CENTER);
                }
            };

            // Apply the renderer to the Status column
            //table.getColumnModel().getColumn(table.getColumnModel().getColumnIndex("Status")).setCellRenderer(statusRenderer);
            int statusColumnIndex = table.getColumnModel().getColumnIndex("Status");
            if (statusColumnIndex != -1) {
                table.getColumnModel().getColumn(statusColumnIndex).setCellRenderer(statusRenderer);
            }

        } catch (SQLException ex) {
            System.err.println("SQL Error while executing query: " + query);
            System.err.println("Exception: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Unexpected error: " + ex.getMessage());
        }
    }

    public static void archive(JTable archiveTable) {
        String query = "SELECT "
                + "i.item_SKU as `SKU`, "
                + "i.item_name as `Item name`, "
                + "i.item_stocks as `Stocks`, "
                + "i.item_price as `Price`, "
                + "i.item_condition as `Condition`, "
                + "i.total_sold as `Sold`, "
                + "i.item_category as `Category`, "
                + "i.item_size as `Size`, "
                + "i.item_color as `Color`, "
                + "i.item_material as `Material`, "
                + "i.item_supplier as `Supplier`, "
                + "a.first_name as `Added by`, "
                + "i.date_added as `Date added`, "
                + "i.item_status as `Status` "
                + "FROM tbl_items i "
                + "JOIN tbl_accounts a ON a.account_id = i.added_by "
                + "WHERE i.item_status = 'archived'";

        display_query(archiveTable, query);
    }

    public static void manage(JTable table, String status) {
        String query = "SELECT "
                + "i.item_SKU as `SKU`, "
                + "i.item_name as `Name`, "
                + "i.item_stocks as `Stocks`, "
                + "i.item_price as `Price`, "
                + "i.item_condition as `Condition`, "
                + "i.item_size as `Sizes`, "
                + "i.item_color as `Colors`, "
                + "i.total_sold as `Sold`, "
                + "i.item_category as `Category`, "
                + "i.item_supplier as `Supplier`, "
                + "a.first_name as `Added by`, "
                + "i.item_status as `Status` "
                + "FROM tbl_items i "
                + "JOIN tbl_accounts a ON a.account_id = i.added_by ";

        // If status is not "all", add a WHERE clause
        if (!"all".equalsIgnoreCase(status)) {
            query += "WHERE i.item_status = '" + status + "'";
        }

        display_query(table, query);
    }

    public static void dashboard(JTable table) {
        String query = "SELECT "
                + "item_SKU as `SKU`, "
                + "item_name as `Item name`, "
                + "item_stocks as `Stocks left`, "
                + "total_sold as `Total Sold`, "
                + "item_supplier as `Supplier`, "
                + "item_status as `Status` "
                + "FROM tbl_items ";

        display_query(table, query);
    }

    public static void to_staff_table(JTable table) {
        String query = "SELECT "
                + "item_SKU as `SKU`, "
                + "item_name as `Item name`, "
                + "item_price as `Price`, "
                + "item_stocks as `Stocks`, "
                + "item_size as `Sizes`, "
                + "item_color as `Color`, "
                + "item_condition as `Condition`, "
                + "item_category as `Category`, "
                + "item_material as `Material`, "
                + "item_supplier as `Supplier` "
                + "FROM tbl_items "
                + "WHERE item_status = 'available'";

        display_query(table, query);
    }

    public static void updateTotalItems(JLabel totalItemsLabel) {
        try {
            databaseConnector dbc = new databaseConnector();
            String query = "SELECT COUNT(*) as TotalItems FROM tbl_items";
            try (PreparedStatement pst = dbc.getConnection().prepareStatement(query); ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int totalItems = rs.getInt("TotalItems");
                    totalItemsLabel.setText(String.format("%d", totalItems));
                } else {
                    totalItemsLabel.setText("0");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error while updating total items: " + ex.getMessage());
        }
    }

    public static void displayStatus(JLabel totalItemsLabel, String status) {
        try {
            databaseConnector dbc = new databaseConnector();
            String query = "SELECT COUNT(*) as TotalItems FROM tbl_items WHERE item_status = '" + status + "'";
            try (PreparedStatement pst = dbc.getConnection().prepareStatement(query); ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int totalItems = rs.getInt("TotalItems");
                    totalItemsLabel.setText(String.format("%d", totalItems));
                } else {
                    totalItemsLabel.setText("0");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error while updating total items: " + ex.getMessage());
        }
    }

    public static void logs(JTable table) {
        try {
            String query = "SELECT "
                    + "l.logs_id as `Logs ID`, "
                    + "a.first_name as `Maker`, "
                    + "l.logs_action as `Action`, "
                    + "l.logs_details as `Details`, "
                    + "l.logs_timestamp as `Timestamp` "
                    + "FROM tbl_logs l "
                    + "JOIN tbl_accounts a ON a.account_id = l.account_id";

            // Execute query and display results
            display_query(table, query);

            // Check if the table has columns before setting preferred widths
            if (table.getColumnCount() > 0) {
                TableColumn column;
                column = table.getColumnModel().getColumn(0);
                column.setPreferredWidth(20);
                column = table.getColumnModel().getColumn(1);
                column.setPreferredWidth(20);
                column = table.getColumnModel().getColumn(2);
                column.setPreferredWidth(100);
                column = table.getColumnModel().getColumn(3);
                column.setPreferredWidth(400);
                column = table.getColumnModel().getColumn(4);
                column.setPreferredWidth(100);
            } else {
                System.out.println("No columns available to set widths.");
            }

        } catch (Exception ex) {
            System.out.println("Errors: " + ex.getMessage());
        }
    }

}
