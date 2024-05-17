/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.px.gui;

import com.im.ImageManager;
import com.im.Objects.Image;
import com.im.Objects.ImagePanelPGM;
import com.im.Objects.PGMImage;
import com.im.exceptions.InvalidFormatException;
import com.im.exceptions.InvalidImageSizeException;
import com.im.exceptions.InvalidPGMMaxWhiteException;
import com.px.windows.ErrorWindow;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author migue
 */
public class ExecGUI{

    public JFrame backgroundFrame;
    public ImageManager im = null;
    public Image image;
    public JButton turnRightButton;
    public JPanel imagePanel;
    public JMenuBar menuBarra;
    public JMenu menuFile;
    
    public ExecGUI(){
        initComponents();
        inicializaMenuBar();
        printImage();
    }
    
    public ExecGUI(ImageManager im){
        this.im = im;
        initComponents();
        inicializaMenuBar();
        printImage();
    }
    
    private void initComponents(){
        backgroundFrame = new JFrame("PHOTOSHOP DE ALIEXPRESS");
        backgroundFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        backgroundFrame.setSize(1280, 720);
        backgroundFrame.setVisible(true);
        
        this.menuBarra = new JMenuBar();
        this.menuFile = new JMenu();
        
        menuFile.setText("File");
        menuBarra.add(menuFile);
        
        this.backgroundFrame.setJMenuBar(menuBarra);
        
        try {
            if (im == null) {
                im = new ImageManager("./imagenes/perrete.pgm");
            }
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
            
            turnRightButton = new JButton("Girar izquierda");
            turnRightButton.setVisible(true);
            turnRightButton.setBounds(20, 80, 200, 60);
            turnRightButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    turnLeft();
                }
            });
            backgroundFrame.add(turnRightButton);
            
            turnRightButton = new JButton("Flip vertical");
            turnRightButton.setVisible(true);
            turnRightButton.setBounds(220, 20, 200, 60);
            turnRightButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    flipV();
                }
            });
            backgroundFrame.add(turnRightButton);
            
            turnRightButton = new JButton("Flip horizontal");
            turnRightButton.setVisible(true);
            turnRightButton.setBounds(220, 80, 200, 60);
            turnRightButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    flipH();
                }
            });
            backgroundFrame.add(turnRightButton);
            
        } catch (Exception ex) {
            throwError(ex.getMessage());
        }
    }
    
    private void inicializaMenuBar(){
        JMenuItem jMenuItemCargar = new JMenuItem("Open");
        this.menuFile.add(jMenuItemCargar);
        jMenuItemCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarImagen();
            }
        });
        JMenuItem jMenuItemGuardar = new JMenuItem("Save");
        this.menuFile.add(jMenuItemGuardar);
        jMenuItemGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                guardarImagen();
            }
        });
    }
    
    private void cargarImagen(){
        System.out.println("hola");
        JFileChooser eligeArchivos = new JFileChooser();
        eligeArchivos.showOpenDialog(this.backgroundFrame);
        File archivo = eligeArchivos.getSelectedFile();
        
        try {
            im = new ImageManager(archivo);
            actualizaImg();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExecGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(ExecGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidImageSizeException ex) {
            Logger.getLogger(ExecGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidPGMMaxWhiteException ex) {
            Logger.getLogger(ExecGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void turnRight(){
        im.turnRightImg();
        actualizaImg();
    }
    
    public void turnLeft(){
        im.turnLeftImg();
        actualizaImg();
    }
    
    public void flipV(){
        im.VerticalFlipImg();
        actualizaImg();
    }
    
    public void flipH(){
        im.HorizontalFlipImg();
        actualizaImg();
    }
    
    private void actualizaImg(){
        this.backgroundFrame.dispose();
        new ExecGUI(im);
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
//            backgroundFrame.remove(imagePanel);
            
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

