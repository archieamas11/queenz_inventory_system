/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author ArchieQT
 */
public class actionLogs {

    public static void recordAdminLogs(int admin_id, String action, String details) {

        String sql = "INSERT INTO tbl_logs (account_id, logs_action, logs_details, logs_timestamp) VALUES (?, ?, ?, NOW())";
        databaseConnector dbc = new databaseConnector();

        try (PreparedStatement pst = dbc.getConnection().prepareStatement(sql)) {
            pst.setInt(1, admin_id);
            pst.setString(2, action);
            pst.setString(3, details);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
