/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.px.gui;

import com.im.ImageManager;
import com.im.Objects.Image;
import com.im.Objects.ImagePanelPGM;
import com.im.Objects.PGMImage;
import com.px.windows.ErrorWindow;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author migue
 */
public class ExecGUI{

    public JFrame backgroundFrame;
    public ImageManager im;
    public Image image;
    public JButton turnRightButton;
    public JPanel imagePanel;
    public ExecGUI(){
        initComponents();
        printImage();
    }
    
    private void initComponents(){
        backgroundFrame = new JFrame("PHOTOSHOP DE ALIEXPRESS");
        backgroundFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        backgroundFrame.setSize(1280, 720);
        backgroundFrame.setVisible(true);
        try {
            im = new ImageManager("./imagenes/perrete.pgm");
            image = im.getImg();
            
            turnRightButton = new JButton("Girar derecha");
            turnRightButton.setVisible(true);
            turnRightButton.setBounds(20, 20, 200, 60);
            turnRightButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    turnRight();
                }
            });
            backgroundFrame.add(turnRightButton);
            
            
        } catch (Exception ex) {
            throwError(ex.getMessage());
        }
    }
    
    public void turnRight(){
//        im.turnRightImg();
//        printImage();
    }
    
    public void printImage(){
        PGMImage img = (PGMImage)image;
        ImagePanelPGM imgPanel;
        try {
            imgPanel = (ImagePanelPGM) im.printImage(640, 360);
            imgPanel.setHeightMove(20);
            imgPanel.setWidthMove(600);
            imagePanel = imgPanel;
            backgroundFrame.add(imagePanel);
            
            System.out.println("DONE");
        } catch (Exception ex) {
            ex.printStackTrace();
            throwError(ex.getMessage());
        }
    }
    
    
    
    public void throwError(String text){
        ErrorWindow error = new ErrorWindow(text);
        error.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new ExecGUI();
            }
        });
    }
}

