/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.px.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author migue
 */
public class ExecGUI {

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
    }
    
}

