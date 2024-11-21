/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;

import config.databaseConnector;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import raven.toast.Notifications;

/**
 *
 * @author Administrator
 */
public class cell_num {
    // Method to handle the Excel file import and database insertion

    public static void importExcelData(File fileToOpen) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try (FileInputStream fis = new FileInputStream(fileToOpen); Workbook workbook = new XSSFWorkbook(fis)) {
            // Assuming the data is in the first sheet
            Sheet sheet = workbook.getSheetAt(0);
            conn = new databaseConnector().getConnection();
            String sql = "INSERT INTO tbl_items (item_name, item_description, item_stocks, item_price, "
                    + "total_sold, item_condition, item_category, item_size, item_color, item_material, "
                    + "item_supplier, added_by, date_added, item_status) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            // Iterate through rows and columns to get data
            for (Row row : sheet) {
                // Assuming the first row is header, start from the second row
                if (row.getRowNum() == 0) {
                    continue; // Skip header row
                }
                pstmt.setString(1, getCellValue(row.getCell(0))); // item_name
                pstmt.setString(2, getCellValue(row.getCell(1))); // item_description
                pstmt.setInt(3, (int) getNumericCellValue(row.getCell(2))); // item_stocks
                pstmt.setDouble(4, getNumericCellValue(row.getCell(3))); // item_price
                pstmt.setInt(5, (int) getNumericCellValue(row.getCell(4))); // total_sold
                pstmt.setString(6, getCellValue(row.getCell(5))); // item_condition
                pstmt.setString(7, getCellValue(row.getCell(6))); // item_category
                pstmt.setString(8, getCellValue(row.getCell(7))); // item_size
                pstmt.setString(9, getCellValue(row.getCell(8))); // item_color
                pstmt.setString(10, getCellValue(row.getCell(9))); // item_material
                pstmt.setString(11, getCellValue(row.getCell(10))); // item_supplier
                pstmt.setInt(12, (int) getNumericCellValue(row.getCell(11))); // added_by
                pstmt.setString(13, getCellValue(row.getCell(12))); // date_added
                pstmt.setString(14, getCellValue(row.getCell(13))); // item_status
                pstmt.addBatch(); // Add to batch
            }

            pstmt.executeBatch(); // Execute batch insert
            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Data imported successfully!");

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error reading Excel file: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

// Helper method to get cell value as a string (for text-based columns)
    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

// Helper method to handle numeric values (for numeric columns)
    private static double getNumericCellValue(Cell cell) {
        if (cell == null) {
            return 0.0;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        }
        return 0.0;
    }

}
