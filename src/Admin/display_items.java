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
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
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

            // Create a renderer for the "Stock left" column
            DefaultTableCellRenderer stockRenderer = new DefaultTableCellRenderer() {
                @Override
                public void setValue(Object value) {
                    super.setValue(value);

                    // Reset to default color and font
                    setForeground(java.awt.Color.BLACK);
                    setFont(getFont().deriveFont(Font.BOLD));
                    setHorizontalAlignment(CENTER);

                    // Check if the value is a number for stock levels
                    if (value instanceof Number) {
                        int stock = ((Number) value).intValue();

                        // Determine color based on stock levels
                        if (stock == 0) {
                            setForeground(new java.awt.Color(139, 0, 0)); // Dark Red for 0
                        } else if (stock >= 1 && stock <= 2) {
                            setForeground(new java.awt.Color(255, 140, 0)); // Dark Orange for 1-2
                        } else if (stock >= 3 && stock <= 5) {
                            setForeground(new java.awt.Color(255, 215, 0)); // Dark Yellow for 3-5
                        } else if (stock >= 6) {
                            setForeground(new java.awt.Color(0, 128, 0)); // Dark Green for 6 and above
                        }
                    }
                }
            };

            int stockColumnIndex = table.getColumnModel().getColumnIndex("Stock left");

            if (stockColumnIndex != -1) {
                table.getColumnModel().getColumn(stockColumnIndex).setCellRenderer(stockRenderer);
            }

// Create a renderer for the "Status" column
            DefaultTableCellRenderer statusRenderer = new DefaultTableCellRenderer() {
                @Override
                public void setValue(Object value) {
                    super.setValue(value);

                    // Reset to default color and font
                    setForeground(java.awt.Color.BLACK);
                    setFont(getFont().deriveFont(Font.BOLD));
                    setHorizontalAlignment(CENTER);

                    // Check if the value is a status string
                    if (value instanceof String) {
                        String status = (String) value;

                        if ("available".equals(status)) {
                            setForeground(new java.awt.Color(0, 128, 0));  // Green
                        } else if ("archived".equals(status)) {
                            setForeground(new java.awt.Color(128, 128, 128));  // Gray
                        } else if ("soldout".equals(status)) {
                            setForeground(new java.awt.Color(255, 0, 0));  // Red
                        } else if ("discontinued".equals(status)) {
                            setForeground(new java.awt.Color(255, 165, 0));  // Orange
                        }
                    }
                }
            };

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
                + "item_stocks as `Stock left` "
                + "FROM tbl_items ";

        display_query(table, query);
    }

    public static void accounts(JTable table) {
        String query = "SELECT "
                + "account_id as `Account #`, "
                + "first_name as `First name`, "
                + "last_name as `Last name`, "
                + "address as `Address`, "
                + "email_address as `Email address`, "
                + "phone_number as `Phone number`, "
                + "username as `Username`, "
                + "role as `Role`, "
                + "date_created as `Date created`, "
                + "status as `Status` "
                + "FROM tbl_accounts ";

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

    public static void best_item(JTable table) {
        String query
                = "SELECT item_SKU AS `SKU`, "
                + "item_name AS `Name`, "
                + "total_sold AS `Sold` "
                + "FROM tbl_items "
                + "WHERE total_sold > 0 "
                + "ORDER BY total_sold DESC";

        display_query(table, query);

        if (table.getColumnCount() > 0) {
            TableColumn column;
            column = table.getColumnModel().getColumn(0);
            column.setPreferredWidth(10);
            column = table.getColumnModel().getColumn(1);
            column.setPreferredWidth(150);
            column = table.getColumnModel().getColumn(2);
            column.setPreferredWidth(20);
        } else {
            System.out.println("No columns available to set widths.");
        }
    }

    public static void not_best_item(JTable table) {
        String query
                = "SELECT item_SKU AS `SKU`, "
                + "item_name AS `Name`, "
                + "total_sold AS `Sold` "
                + "FROM tbl_items "
                + "WHERE total_sold < 1 "
                + "ORDER BY total_sold ASC";

        display_query(table, query);

        if (table.getColumnCount() > 0) {
            TableColumn column;
            column = table.getColumnModel().getColumn(0);
            column.setPreferredWidth(10);
            column = table.getColumnModel().getColumn(1);
            column.setPreferredWidth(150);
            column = table.getColumnModel().getColumn(2);
            column.setPreferredWidth(20);
        } else {
            System.out.println("No columns available to set widths.");
        }
    }

    public static int total_solds;

    public static void total_sold(JLabel total_sold) {
        try {
            databaseConnector dbc = new databaseConnector();
            String query = "SELECT SUM(total_sold) as total_profit FROM tbl_items WHERE total_sold > 0";
            try (PreparedStatement pst = dbc.getConnection().prepareStatement(query)) {
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        total_solds = rs.getInt("total_profit");
                        NumberFormat numberFormat = NumberFormat.getNumberInstance();
                        String formattedSales = numberFormat.format(total_solds);
                        total_sold.setText(String.format("%s Items", formattedSales));
                    } else {
                        total_sold.setText("0 Items");
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    public static int total_profits;

    public static void total_sales(JLabel total_profit) {
        try {
            databaseConnector dbc = new databaseConnector();
            String query = "SELECT SUM(item_price * total_sold) as total_profit FROM tbl_items WHERE total_sold > 0";
            try (PreparedStatement pst = dbc.getConnection().prepareStatement(query)) {
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        total_profits = rs.getInt("total_profit");
                        NumberFormat numberFormat = NumberFormat.getNumberInstance();
                        String formattedSales = numberFormat.format(total_profits);
                        total_profit.setText(String.format("₱ %s", formattedSales));
                    } else {
                        total_profit.setText("₱ 0");
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public static int total_expenses;

    public static void total_expense(JLabel total_expense) {
        try {
            databaseConnector dbc = new databaseConnector();
            String query = "SELECT SUM(item_price) * SUM(item_stocks + total_sold) as total_profit FROM tbl_items";
            try (PreparedStatement pst = dbc.getConnection().prepareStatement(query)) {
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        total_expenses = rs.getInt("total_profit");
                        NumberFormat numberFormat = NumberFormat.getNumberInstance();
                        String formattedSales = numberFormat.format(total_expenses);
                        total_expense.setText(String.format("₱ %s", formattedSales));
                    } else {
                        total_expense.setText("₱ 0");
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public static void precentage_bar_for_total_profit(JProgressBar progressBar, JLabel percentage) {
        if (total_expenses == 0) {
            progressBar.setValue(0); // No expenses means 0% profit
            return;
        }
        double profitPercentage = ((double) total_profits / total_expenses) * 100;
        percentage.setText(String.format("%.2f%%", profitPercentage));
        if (profitPercentage < 0) {
            profitPercentage = 0; // Set to 0 if negative
        } else if (profitPercentage > 100) {
            profitPercentage = 100; // Set to 100 if greater than 100
        }

        progressBar.setValue((int) profitPercentage);
        progressBar.repaint();
    }

    public static void percentage_bar_for_total_expense(JProgressBar progressBar, JLabel percentage) {
        if (total_expenses == 0) {
            progressBar.setValue(0); // No expenses means 0% expense
            percentage.setText("0.00%"); // Show 0% in label
            return;
        }

        // Assuming you want to calculate the expense percentage against a total amount (like budget or a goal)
        // For now, let's use a placeholder for maximum expenses
        double maxExpenses = 100000; // Set this to whatever your max expense target is
        double expensePercentage = ((double) total_expenses / maxExpenses) * 100;

        // Ensure expense percentage is within bounds
        if (expensePercentage < 0) {
            expensePercentage = 0; // Set to 0 if negative
        } else if (expensePercentage > 100) {
            expensePercentage = 100; // Set to 100 if greater than 100
        }

        // Update the JProgressBar value
        progressBar.setValue((int) expensePercentage);
        progressBar.repaint(); // Ensure the progress bar is repainted

        // Update the percentage label with formatted value
        percentage.setText(String.format("%.2f%%", expensePercentage));
    }

    public static void percentage_bar_for_total_items(JProgressBar progressBar, JLabel percentage) {
        try {
            databaseConnector dbc = new databaseConnector();
            String query = "SELECT SUM(item_stocks + total_sold) as total_items FROM tbl_items";

            try (PreparedStatement pst = dbc.getConnection().prepareStatement(query)) {
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        int total_items = rs.getInt("total_items");

                        // Get the total sold items from your existing logic or database
                        if (total_items == 0) {
                            progressBar.setValue(0); // No items means 0% 
                            percentage.setText("0.00%"); // Show 0% in label
                            return;
                        }

                        if (total_solds == 0) {
                            progressBar.setValue(0); // No items sold means 0%
                            percentage.setText("0.00%"); // Show 0% in label
                            return;
                        }

                        // Calculate the percentage of total items sold
                        double soldPercentage = ((double) total_solds / total_items) * 100;

                        // Ensure percentage is within bounds
                        if (soldPercentage < 0) {
                            soldPercentage = 0; // Set to 0 if negative
                        } else if (soldPercentage > 100) {
                            soldPercentage = 100; // Set to 100 if greater than 100
                        }

                        // Update the JProgressBar value
                        progressBar.setValue((int) soldPercentage);
                        progressBar.repaint(); // Ensure the progress bar is repainted

                        // Update the percentage label with formatted value
                        percentage.setText(String.format("%.2f%%", soldPercentage));

                    } else {
                        percentage.setText("0.00%"); // Handle no result case
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

}
