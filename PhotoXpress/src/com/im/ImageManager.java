/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.im;

import com.im.Objects.Image;
import com.im.Objects.PGMImage;
import com.im.Objects.ImagePanelPGM;
import com.im.exceptions.InvalidFormatException;
import com.im.exceptions.InvalidImageSizeException;
import com.im.exceptions.InvalidPGMMaxWhiteException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import javax.swing.JPanel;

/**
 *
 * @author migue
 */
public class ImageManager {
    private Image img;
    private Image resizedImg;
    private HashMap<Double, Integer> factorOfResizing = new HashMap<Double, Integer>();
    private ArrayList<Double> factorOfResizingKeyList = new ArrayList<Double>();
    
    public ImageManager(String path) throws FileNotFoundException, InvalidFormatException, InvalidImageSizeException, InvalidPGMMaxWhiteException{
        try{
            PGMImage img = new PGMImage(path);
            resizedImg = img;
            constructor(img);
        } catch (FileNotFoundException  | 
                InvalidFormatException | 
                InvalidImageSizeException | 
                InvalidPGMMaxWhiteException ex) {
            throw ex;
        }
    }
    
    public ImageManager(File imgFile) throws FileNotFoundException, InvalidFormatException, InvalidImageSizeException, InvalidPGMMaxWhiteException{
        try{
            PGMImage img = new PGMImage(imgFile);
            constructor(img);
        } catch (FileNotFoundException  | 
                InvalidFormatException | 
                InvalidImageSizeException | 
                InvalidPGMMaxWhiteException ex) {
            throw ex;
        }
    }

    public JPanel pintarImagen(int width, int height) throws InvalidFormatException, Exception{
        int n = -1;
        if (this.resizedImg instanceof PGMImage) {
            PGMImage resizeImg;
            resizeImg = (PGMImage) this.resizedImg;
            int iterations = 0;
            if (resizeImg.getHeight() < height && resizeImg.getWidth() < width) {
                if (height - resizeImg.getHeight() < width - resizeImg.getWidth()) {
                    resizeImg.moreResolution((int)(height - resizeImg.getHeight())/14);
                }else{
                    resizeImg.moreResolution((int)(width - resizeImg.getWidth())/7);
                }
            }else{
                while(resizeImg.getHeight() - height > resizeImg.getWidth()- width && resizeImg.getHeight() > height && n == -1){
    //                System.out.println("lower   " + (resizeImg.getHeight() - height));
                    for (Double key : this.factorOfResizingKeyList) {
                        key /= Math.pow(2, iterations);
    //                    System.out.println(key);
                        if (resizeImg.getHeight()*key < height) {
    //                        System.out.println("yes   ");
    //                        System.out.println("key: " + key + " * (1+" + iterations + ") = " + (key*(iterations + 1)));
                            n = this.factorOfResizing.get(key*(iterations + 1));
                            break;
                        }
                    }
                    iterations++;
                }
                while(resizeImg.getWidth()- width > resizeImg.getHeight()- height && resizeImg.getWidth()> width && n == -1){
                    for (Double key : this.factorOfResizingKeyList) {
                        key /= Math.pow(2, iterations);
                        if (resizeImg.getHeight()*key < height) {
                            n = this.factorOfResizing.get(key*(iterations + 1));
                            break;
                        }
                    }
                    iterations++;
                }
                int factor = n;
                resizeImg.moreResolution(n);
                while(factor >= 1){
                    resizeImg.lessResolution();
                    factor /= 2;
                }

                for (int i = 0; i < iterations - 1; i++) {
                    resizeImg.lessResolution();
                }
            }
            resizeImg.turnRight();
            ImagePanelPGM pgmpanel = new ImagePanelPGM(resizeImg, width, height);
            return pgmpanel;
        }else{
            throw new InvalidFormatException();
        }
    }
    
    private void constructor(Image img){
        this.img = img;
        initList();
    }
    
    private void initList(){
        this.factorOfResizing.put(0.875, 7);
        this.factorOfResizing.put(0.750, 3);
        this.factorOfResizing.put(0.625, 5);
        this.factorOfResizing.put(0.500, 1);
        
        this.factorOfResizingKeyList.add(0.875);
        this.factorOfResizingKeyList.add(0.750);
        this.factorOfResizingKeyList.add(0.625);
        this.factorOfResizingKeyList.add(0.500);
    }
    
    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
        resizedImg = img;
    }
}
