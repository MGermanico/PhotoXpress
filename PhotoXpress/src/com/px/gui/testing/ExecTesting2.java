/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.px.gui.testing;

import com.im.ImageManager;
import com.im.Objects.Image;
import com.im.Objects.ImagePanelPGM;
import com.im.Objects.PGMImage;
import com.im.exceptions.InvalidFormatException;
import com.im.exceptions.InvalidImageSizeException;
import com.im.exceptions.InvalidPGMMaxWhiteException;
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
public class ExecTesting2 {

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
            backgroundFrame.remove(imgPanel);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(com.im.gui.ExecTesting.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(com.im.gui.ExecTesting.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidImageSizeException ex) {
            Logger.getLogger(com.im.gui.ExecTesting.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidPGMMaxWhiteException ex) {
            Logger.getLogger(com.im.gui.ExecTesting.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ExecTesting2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

