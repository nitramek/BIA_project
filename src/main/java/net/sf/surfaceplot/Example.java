/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Example.java
 *
 * Created on Mar 13, 2010, 2:46:54 PM
 */

package net.sf.surfaceplot;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

/**
 * @author siva
 */
public class Example extends javax.swing.JFrame {

    public Example() {
        getContentPane().setLayout(new BorderLayout());

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 5));
        southPanel.add(new JLabel("Rotate: Mouse Click & Drag"));
        southPanel.add(new JLabel("Zoom: Shift Key + Mouse Click & Drag"));
        southPanel.add(new JLabel("Move: Control Key + Mouse Click & Drag"));
        getContentPane().add(southPanel, BorderLayout.SOUTH);

        setSize(1000, 800);
        ExampleSurfaceModel model = new ExampleSurfaceModel();
        SurfaceCanvas canvas = new SurfaceCanvas();
        canvas.setModel(model);
        getContentPane().add(canvas, BorderLayout.CENTER);
        canvas.repaint();
        setVisible(true);
    }

    public static void main(String args[]) {
        new Example().setVisible(true);
    }
}
