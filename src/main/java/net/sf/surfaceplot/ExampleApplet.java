package net.sf.surfaceplot;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;

/**
 * @author Siva Alagarsamy
 */
public class ExampleApplet extends JApplet {
    @Override
    public void init() {
        JFrame frame = new Example();
        frame.setVisible(true);
    }

}
