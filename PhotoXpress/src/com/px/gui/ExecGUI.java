/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.px.gui;

import com.im.ImageManager;
import com.im.Objects.ImagePanelPGM;
import com.im.Objects.PGMImage;
import com.im.exceptions.InvalidFormatException;
import com.im.exceptions.InvalidImageSizeException;
import com.im.exceptions.InvalidPGMMaxWhiteException;
import com.px.gui.testing.ExecTesting2;
import com.px.windows.ErrorWindow;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        try {
            ImageManager im = new ImageManager("./imagenes/imagen_1.pgm");
            PGMImage img = (PGMImage)im.getImg();
//            img.showArrayOnLogger();
            ImagePanelPGM imgPanel = (ImagePanelPGM) im.printImage(640, 360);
//            ImagePanelPGM imgPanel = (ImagePanelPGM) im.printImage(640,360);
            imgPanel.setHeightMove(20);
            imgPanel.setWidthMove(600);
            backgroundFrame.add(imgPanel);
            
            
        } catch (Exception ex) {
            throwError(ex.getMessage());
        }
    }
    
    public static void throwError(String text){
        ErrorWindow error = new ErrorWindow(text);
        error.setVisible(true);
    }
}

