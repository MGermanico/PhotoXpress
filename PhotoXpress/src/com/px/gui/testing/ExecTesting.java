/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.px.gui.testing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author migue
 */
public class ExecTesting {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                initComponents();
            }
        });
    }
    
    private static void initComponents(){
        JFrame backgroundFrame = new JFrame("PHOTOSHOP DE ALIEXPRESS");
        backgroundFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        backgroundFrame.setSize(1280, 720);
        backgroundFrame.setVisible(true);
        JDialog dialogT = new JDialog(backgroundFrame, "ERROR", true);
        dialogT.setBounds(100, 100, 450, 280);
        JButton closeError = new JButton("Cerrar");
        closeError.setBounds( 165, 180, 80, 30);
        JLabel messegeError = new JLabel("ERROR");
        messegeError.setBounds(50, 0, 0, 0);
        dialogT.add(closeError);
        dialogT.add(messegeError);
        dialogT.setVisible(true);
        backgroundFrame.add(new MyPanel());
    }
    
}
class MyPanel extends JPanel {

    public MyPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        // Draw Text
        g.drawRect(20, 20, 50, 50);
    }
}
