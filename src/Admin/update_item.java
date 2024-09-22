/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;

import static Admin.admin_dashboard.displayAll;
import config.actionLogs;
import config.databaseConnector;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import raven.toast.Notifications;

/**
 *
 * @author ArchieQT
 */
public class update_item {

    public static void status(String status, String successMessage, String actionType, int get_item_id, int admin_id, JTable dashboard_table, JTable archive_table, JTable manage_table, JLabel total_items) {
        try {
            databaseConnector dbc = new databaseConnector();
            String sql = "UPDATE tbl_items SET `item_status`=? WHERE `item_SKU`=?";

            try (PreparedStatement pst = dbc.getConnection().prepareStatement(sql)) {
                pst.setString(1, status);
                pst.setInt(2, get_item_id);

                int rowsUpdated = pst.executeUpdate();

                if (rowsUpdated > 0) {
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, successMessage);
                    displayAll(dashboard_table, manage_table, archive_table, total_items, "available");

                    String action = "archive";
                    String details = "admin " + admin_id + " successfully " + actionType + " an item with SKU number " + get_item_id + "!";
                    actionLogs.recordAdminLogs(admin_id, actionType, details);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update item!");
                }

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL Error updating data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
