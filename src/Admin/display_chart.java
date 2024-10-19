/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;

import javax.swing.JPanel;
import raven.chart.bar.HorizontalBarChart;
import raven.chart.data.pie.DefaultPieDataset;
import raven.chart.pie.PieChart;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Color;
import java.awt.Component;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import javax.swing.JLabel;
import raven.chart.ChartLegendRenderer;
import raven.chart.data.category.DefaultCategoryDataset;
import raven.chart.line.LineChart;
import utils.DateCalculator;

/**
 *
 * @author rico
 */
public class display_chart {

    private static PieChart pieChart1;
    private static PieChart pieChart2;
    private static PieChart pieChart3;
    private static LineChart lineChart;
    private static HorizontalBarChart barChart1;
    private static HorizontalBarChart barChart2;

    public static void createPieChart1(JPanel panel) {
        pieChart1 = new PieChart();
        JLabel header1 = new JLabel("Item Income");
        header1.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:Arial bold +2");
        pieChart1.setHeader(header1);
        pieChart1.getChartColor().addColor(
                Color.decode("#f87171"),
                Color.decode("#fb923c"),
                Color.decode("#fbbf24"),
                Color.decode("#a3e635"),
                Color.decode("#34d399"),
                Color.decode("#22d3ee"),
                Color.decode("#818cf8"),
                Color.decode("#c084fc"));

        pieChart1.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:null");
        pieChart1.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5,$Component.borderColor,,20");
        pieChart1.setDataset(createPieData());
        panel.removeAll();
        panel.add(pieChart1);
        panel.validate();
        panel.repaint();
    }

    public static void createPieChart2(JPanel panel) {
        pieChart2 = new PieChart();
        JLabel header1 = new JLabel("Item Cost");
        header1.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:Arial bold +2");
        pieChart2.setHeader(header1);
        pieChart2.getChartColor().addColor(
                Color.decode("#f87171"),
                Color.decode("#fb923c"),
                Color.decode("#fbbf24"),
                Color.decode("#a3e635"),
                Color.decode("#34d399"),
                Color.decode("#22d3ee"),
                Color.decode("#818cf8"),
                Color.decode("#c084fc"));

        pieChart2.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:null");
        pieChart2.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5,$Component.borderColor,,20");
        pieChart2.setDataset(createPieData());
        panel.removeAll();
        panel.add(pieChart2);
        panel.validate();
        panel.repaint();
    }

    public static void createPieChart3(JPanel panel) {
        pieChart3 = new PieChart();
        JLabel header1 = new JLabel("Item Profit");
        header1.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:Arial bold +2");
        pieChart3.setHeader(header1);
        pieChart3.getChartColor().addColor(
                Color.decode("#f87171"),
                Color.decode("#fb923c"),
                Color.decode("#fbbf24"),
                Color.decode("#a3e635"),
                Color.decode("#34d399"),
                Color.decode("#22d3ee"),
                Color.decode("#818cf8"),
                Color.decode("#c084fc"));

        pieChart3.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:null");
        pieChart3.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5,$Component.borderColor,,20");
        pieChart3.setChartType(PieChart.ChartType.DONUT_CHART);

        pieChart3.setDataset(createPieData());
        panel.removeAll();
        panel.add(pieChart3);
        panel.validate();
        panel.repaint();
    }

    public static DefaultPieDataset createPieData() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        Random random = new Random();
        dataset.addValue("Classic T-shirt", random.nextInt(100) + 50);
        dataset.addValue("Denim", random.nextInt(100) + 50);
        dataset.addValue("Jacket", random.nextInt(100) + 50);
        dataset.addValue("Pakigol", random.nextInt(100) + 50);
        dataset.addValue("Panty", random.nextInt(100) + 50);
        return dataset;
    }

    public static void createLineChart(JPanel panel) {
        lineChart = new LineChart();
        lineChart.setChartType(LineChart.ChartType.CURVE);
        lineChart.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5,$Component.borderColor,,20");

        // Set values format for line chart
        createLineChartData();
        panel.removeAll();
        panel.add(lineChart);
        panel.validate();
        panel.repaint();
    }

    public static void createLineChartData() {
        DefaultCategoryDataset<String, String> categoryDataset = new DefaultCategoryDataset<>();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");

        // Example specific data arrays
        int[] incomeData = {500, 600, 450, 700, 620, 580, 610};
        int[] expenseData = {300, 400, 350, 500, 450, 420, 390};
        int[] profitData = {200, 200, 100, 200, 170, 160, 220};

        int dataSize = incomeData.length;
        for (int i = 0; i < dataSize; i++) {
            String date = df.format(cal.getTime());
            categoryDataset.addValue(incomeData[i], "Income", date + " (" + currencyFormat.format(incomeData[i]) + ")");
            categoryDataset.addValue(expenseData[i], "Expense", date + " (" + currencyFormat.format(expenseData[i]) + ")");
            categoryDataset.addValue(profitData[i], "Profit", date + " (" + currencyFormat.format(profitData[i]) + ")");

            cal.add(Calendar.DATE, 1);
        }
        

        /**
         * Control the legend we do not show all legend
         */
        try {
            Date date = df.parse(categoryDataset.getColumnKey(0));
            Date dateEnd = df.parse(categoryDataset.getColumnKey(categoryDataset.getColumnCount() - 1));

            DateCalculator dcal = new DateCalculator(date, dateEnd);
            long diff = dcal.getDifferenceDays();

            double d = Math.ceil((diff / 10f));
            lineChart.setLegendRenderer(new ChartLegendRenderer() {
                @Override
                public Component getLegendComponent(Object legend, int index) {
                    if (index % d == 0) {
                        return super.getLegendComponent(legend, index);
                    } else {
                        return null;
                    }
                }
            });
        } catch (ParseException e) {
            System.err.println(e);
        }

        lineChart.setCategoryDataset(categoryDataset);
        lineChart.getChartColor().addColor(Color.decode("#38bdf8"), Color.decode("#fb7185"), Color.decode("#34d399"));
        JLabel header = new JLabel("Income Data");
        header.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:Arial bold +2"
                + "border:0,0,5,0");
        lineChart.setHeader(header);
    }

    public static void createBarChart(JPanel panel1, JPanel panel2) {
        // BarChart 1
        barChart1 = new HorizontalBarChart();
        JLabel header1 = new JLabel("Monthly Income");
        header1.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:Arial bold +2"
                + "border:0,0,5,0");
        barChart1.setHeader(header1);
        barChart1.setBarColor(Color.decode("#f97316"));
        barChart1.setDataset(createData());
        panel1.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5,$Component.borderColor,,20");
        panel1.add(barChart1);
        barChart1.setDataset(createData());
        panel1.removeAll();
        panel1.add(barChart1);
        panel1.validate();
        panel1.repaint();

        // BarChart 2
        barChart2 = new HorizontalBarChart();
        JLabel header2 = new JLabel("Monthly Expense");
        header2.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:Arial bold +2"
                + "border:0,0,5,0");
        barChart2.setHeader(header2);
        barChart2.setBarColor(Color.decode("#10b981"));
        panel2.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5,$Component.borderColor,,20");
        panel2.add(barChart2);
        barChart2.setDataset(createData());
        panel2.removeAll();
        panel2.add(barChart2);
        panel2.validate();
        panel2.repaint();
    }

    public static DefaultPieDataset createData() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        Random random = new Random();
        dataset.addValue("July (ongoing)", random.nextInt(100));
        dataset.addValue("June", random.nextInt(100));
        dataset.addValue("May", random.nextInt(100));
        dataset.addValue("April", random.nextInt(100));
        dataset.addValue("March", random.nextInt(100));
        dataset.addValue("February", random.nextInt(100));
        return dataset;
    }

}
