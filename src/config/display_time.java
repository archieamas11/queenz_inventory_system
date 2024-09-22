/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JLabel;

/**
 *
 * @author rico
 */
public class display_time {

    public static void now(JLabel date_today) {
        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM dd yyyy");
        String formattedDate = currentDate.format(formatter);

        date_today.setText(formattedDate);
    }
}
