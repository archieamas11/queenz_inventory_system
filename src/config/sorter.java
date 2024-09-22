/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author ArchieQT
 */
public class sorter {

    public static void searchResult(JTable table, JComboBox<String> filter) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);

        table.setRowSorter(sorter);
        String selectedCategory = (String) filter.getSelectedItem();

        // If "All" or a similar option is selected, show all rows
        if (selectedCategory.equals("All Categories") || selectedCategory.equals("All Status") || selectedCategory.equals("All")) {
            sorter.setRowFilter(null);
        } else {
            // Filter the table rows based on the selected size (S, M, L, XL, XXL)
            sorter.setRowFilter(RowFilter.regexFilter("^" + selectedCategory + "$"));
        }
    }

    public static void toggle(JTable table, String value) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);

        table.setRowSorter(sorter);
        String selectedCategory = value;

        // If "All" or a similar option is selected, show all rows
        if (selectedCategory.equals("All Categories") || selectedCategory.equals("All Status") || selectedCategory.equals("All")) {
            sorter.setRowFilter(null);
        } else {
            // Filter the table rows based on the selected size (S, M, L, XL, XXL)
            sorter.setRowFilter(RowFilter.regexFilter("^" + selectedCategory + "$"));
        }
    }
}
