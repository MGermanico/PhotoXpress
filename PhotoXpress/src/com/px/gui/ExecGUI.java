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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
    public JButton button;
    public JPanel imagePanel;
    public JMenuBar menuBar;
    public JMenu menuFile;
    
    public ExecGUI(){
        initComponents(null);
        inicializaMenuBar();
        printImage();
    }
    
    public ExecGUI(ImageManager im){
        this.im = im;
        initComponents(null);
        inicializaMenuBar();
        printImage();
    }
    
    public ExecGUI(String path){
        initComponents(path);
        inicializaMenuBar();
        printImage();
    }
    
    private void initComponents(String path){
        if (path == null) {
            path = "./imagenes/imagen.pgm";
        }
        
        backgroundFrame = new JFrame("PHOTOSHOP DE ALIEXPRESS");
        backgroundFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        backgroundFrame.setSize(1280, 720);
        backgroundFrame.setVisible(true);
        
        this.menuBar = new JMenuBar();
        this.menuFile = new JMenu();
        
        menuFile.setText("File");
        menuBar.add(menuFile);
        
        this.backgroundFrame.setJMenuBar(menuBar);
        
        try {
            if (im == null) {
                im = new ImageManager(path);
            }
            image = im.getImg();
            
            button = new JButton("Girar derecha");
            button.setVisible(true);
            button.setBounds(20, 20, 200, 60);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    turnRight();
                }
            });
            backgroundFrame.add(button);
            
            button = new JButton("Girar izquierda");
            button.setVisible(true);
            button.setBounds(20, 80, 200, 60);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    turnLeft();
                }
            });
            backgroundFrame.add(button);
            
            button = new JButton("Flip vertical");
            button.setVisible(true);
            button.setBounds(220, 20, 200, 60);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    flipV();
                }
            });
            backgroundFrame.add(button);
            
            button = new JButton("Flip horizontal");
            button.setVisible(true);
            button.setBounds(220, 80, 200, 60);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    flipH();
                }
            });
            backgroundFrame.add(button);
            
            button = new JButton("Filtro negativo");
            button.setVisible(true);
            button.setBounds(20, 140, 200, 60);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    negativeFilter();
                }
            });
            backgroundFrame.add(button);
            
            button = new JButton("Filtro caja");
            button.setVisible(true);
            button.setBounds(220, 140, 200, 60);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boxFilter();
                }
            });
            backgroundFrame.add(button);
            
        } catch (Exception ex) {
            throwError(ex.getMessage());
        }
    }
    
    private void inicializaMenuBar(){
        JMenu jMenuItemCargar = new JMenu("Cargar");
        JMenuItem jMenuItemFileBrowser = new JMenuItem("Explorador de archivos");
        JMenuItem jMenuItemGuardar = new JMenuItem("Guardar");
        JLabel jLabelImgFolder = new JLabel("      -- Carpeta imágenes --   ");
        
        jMenuItemFileBrowser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarImagen();
            }
        });
        
        jMenuItemGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarImagen();
            }
        });
        
        jMenuItemCargar.add(jMenuItemFileBrowser);
        jMenuItemCargar.add(jLabelImgFolder);
        this.menuFile.add(jMenuItemCargar);
        this.menuFile.add(jMenuItemGuardar);
        
        File imgFolder = new File("imagenes");
        File[] savedImages = imgFolder.listFiles();
        File actualImage;
        String actualPath = "";
        
        for (int i = 0; i < savedImages.length; i++) {
            actualImage = savedImages[i];
            actualPath = actualImage.getPath();
            addImages(actualPath, actualImage, jMenuItemCargar);
        }
    }
    private void addImages(String actualPath, File actualImage, JMenuItem jMenuItemCargar){
        JMenuItem imagenActual;
        imagenActual = new JMenuItem(actualImage.getName());
        imagenActual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarImagen(actualPath);
            }
        });
        jMenuItemCargar.add(imagenActual);
    }
    private void cargarImagen(String path){
        this.backgroundFrame.dispose();
        new ExecGUI(path);
    }
    private void cargarImagen(){
        JFileChooser eligeArchivos = new JFileChooser();
        eligeArchivos.showOpenDialog(this.backgroundFrame);
        File archivo = eligeArchivos.getSelectedFile();
        
        if (archivo != null) {
            this.backgroundFrame.dispose();
            new ExecGUI(archivo.getPath());
        }
    }
    
    private void guardarImagen(){
        try {
            this.im.save();
        } catch (Exception ex) {
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
    
    public void negativeFilter(){
        im.negativeFilter();
        actualizaImg();
    }
    
    public void boxFilter(){
        im.boxFilter();
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
            
//            System.out.println("DONE");
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

