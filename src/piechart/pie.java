/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package piechart;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 *
 * @author rico
 */
public class pie extends javax.swing.JPanel {

    /**
     * Creates new form PieChart
     */
    private int[] values = {25, 30, 45}; // Values for the pie chart
    private String[] labels = {"A", "B", "C"}; // Labels for the segments
    private Color[] colors = {Color.RED, Color.GREEN, Color.BLUE};

    public pie() {
        initComponents();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width, height) / 2 - 20;
        int centerX = width / 2;
        int centerY = height / 2;

        // Draw pie chart
        double startAngle = 0;
        for (int i = 0; i < values.length; i++) {
            double angle = (values[i] / 100.0) * 360;
            g2d.setColor(colors[i]);
            g2d.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2, (int) startAngle, (int) angle);
            startAngle += angle;
        }

        // Draw labels
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        double cumulativeAngle = 0;  // Track cumulative angles for label positioning
        for (int i = 0; i < labels.length; i++) {
            double angle = (values[i] / 100.0) * 360;
            cumulativeAngle += angle / 2;
            int x = centerX + (int) (radius * 0.8 * Math.cos(Math.toRadians(cumulativeAngle)));
            int y = centerY + (int) (radius * 0.8 * Math.sin(Math.toRadians(cumulativeAngle)));
            g2d.drawString(labels[i], x, y);
            cumulativeAngle += angle / 2;  // Adjust for the next segment
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
