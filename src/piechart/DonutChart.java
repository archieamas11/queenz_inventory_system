package piechart;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author 1BestCsharp
 */
public class DonutChart extends JFrame {

    private DonutChartPanel donutChartPanel;

    public DonutChart() {
        setTitle("Donut Chart");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 400);
        setLocationRelativeTo(null);

        donutChartPanel = new DonutChartPanel();
        donutChartPanel.setBackground(Color.white);
        add(donutChartPanel);
        setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        new DonutChart();

    }

}

// Panel class for displaying the pie chart
class DonutChartPanel extends JPanel {

    //private Color[] sliceColors = {Color.decode("#3498db"), Color.decode("#e74c3c"), Color.decode("#2ecc71")}; // Modern slice colors
    //private Color[] sliceColors = {Color.decode("#FF6B6B"), Color.decode("#74B9FF"), Color.decode("#55E6C1")}; // Custom slice colors
    private Color[] sliceColors = {Color.decode("#FF5733"), Color.decode("#33FFB2"), Color.decode("#3360FF")}; // Updated colors

    // Custom slice colors for the pie chart    
    /*private Color[] silceColors = {Color.decode("#FEC107"),
                                   Color.decode("#2196F3"),
                                   Color.decode("#4CAF50")
                                  };
     */
    // Initial data values representing percentages
    private int[] data = {75, 20, 5};

    // Override the paintComponent method to customize the drawing of the panel
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        // Call the method to draw the pie chart
        drawDonutChart(g);

    }

    // Method to draw the pie chart
    private void drawDonutChart(Graphics g) {
        // Create a Graphics2D object
        Graphics2D g2d = (Graphics2D) g;
        // Get the width of the panel
        int width = getWidth();
        // Get the height of the panel
        int height = getHeight();
        // Determine the diameter of the pie chart
        int outerDiameter = Math.min(width, height) - 20;
        int innerDiameter = outerDiameter / 2;
        // Calculate the x-coordinate for the pie chart
        int x = (width - outerDiameter) / 2;
        // Calculate the y-coordinate for the pie chart
        int y = (height - outerDiameter) / 2;
        // Initialize the starting angle for the first slice of the pie chart
        int startAngle = 0;

        for (int i = 0; i < data.length; i++) {
            // Calculate the arc angle for the current slice
            int arcAngle = (int) ((double) data[i] / 100 * 360);
            // Set the color for the current slice
            g2d.setColor(sliceColors[i]);
            // Fill the arc representing the slice
            g2d.fillArc(x, y, outerDiameter, outerDiameter, startAngle, arcAngle);
            g2d.setColor(getBackground());
            g2d.fillArc(x + (outerDiameter - innerDiameter) / 2, y + (outerDiameter - innerDiameter) / 2, innerDiameter, innerDiameter, 0, 360);
            // Update the starting angle for the next slice
            startAngle += arcAngle;
        }

        // Draw labels or legends for each slice
        // Set the x-coordinate for the legend
        int legendX = width - 110;
        // Set the initial y-coordinate for the legend
        int legendY = 20;

        for (int i = 0; i < data.length; i++) {
            // Set the color for the legend box
            g2d.setColor(sliceColors[i]);
            // Fill the legend box with color
            g2d.fillRect(legendX, legendY, 20, 20);
            // Set the text color for the legend
            g2d.setColor(Color.black);
            // Draw the legend text with slice number and percentage
            g2d.drawString("Slice " + (i + 1) + ": " + data[i] + "%", legendX + 30, legendY + 15);
            // Update the y-coordinate for the next legend entry
            legendY += 30;
        }

    }

}
