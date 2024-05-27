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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

/**
 *
 * @author migue
 */
public class ImageManager {
    private Image img;
    private PGMImage resizedImg;
    private HashMap<Double, Integer> factorOfResizing = new HashMap<Double, Integer>();
    private ArrayList<Double> factorOfResizingKeyList = new ArrayList<Double>();
    
    public ImageManager(String path) throws FileNotFoundException, InvalidFormatException, InvalidImageSizeException, InvalidPGMMaxWhiteException{
        try{
            PGMImage img = new PGMImage(path);
            PGMImage resizedImg = new PGMImage(path);
//            resizedImg = img;
            constructor(img, resizedImg);
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
            PGMImage resizedImg = new PGMImage(imgFile);
            constructor(img, resizedImg);
        } catch (FileNotFoundException  | 
                InvalidFormatException | 
                InvalidImageSizeException | 
                InvalidPGMMaxWhiteException ex) {
            throw ex;
        }
    }

    private void constructor(Image img, PGMImage resizedImg){
        this.img = img;
        this.resizedImg = resizedImg;
        initList();
    }
    
    public JPanel printImage(int width, int height, boolean byPixel) throws InvalidFormatException, Exception{
        if (this.resizedImg instanceof PGMImage) {
            PGMImage resizeImg;
            resizeImg = (PGMImage) this.resizedImg;
            if (resizeImg.getHeight() < height && resizeImg.getWidth() < width) {
//                System.out.println("LA IMG ES PEQUEÑA");
                if ((resizeImg.getHeight()*1.0/resizeImg.getWidth()) > height*1.0/width) {
//                    System.out.println("de alto " + (height*1.0/resizeImg.getHeight() - 1));
                    resizeImg.moreResolution((height/resizeImg.getHeight() - 1), byPixel);
                }else{
//                    System.out.println("de ancho " + (width/resizeImg.getWidth() - 1));
                    resizeImg.moreResolution((width*1.0/resizeImg.getWidth() - 1), byPixel);
                }
            }else{
//                System.out.println("LA IMG ES GRANDE");
                resizeImg = acotaImagenPGM(width, height, resizeImg, byPixel);
            }
            ImagePanelPGM pgmpanel = new ImagePanelPGM(resizeImg, width, height);
            return pgmpanel;
        }else{
            throw new InvalidFormatException();
        }
    }
    
    public void turnRightImg(){
        if (this.resizedImg instanceof PGMImage) {
            PGMImage resizeImg = (PGMImage) this.resizedImg;
            resizeImg.turnRight();
            this.resizedImg = resizeImg;
            
            PGMImage img = (PGMImage) this.img;
            img.turnRight();
            this.img = img;
        }
    }
    public void turnLeftImg(){
        if (this.resizedImg instanceof PGMImage) {
            PGMImage resizeImg = (PGMImage) this.resizedImg;
            resizeImg.turnLeft();
            this.resizedImg = resizeImg;
            
            PGMImage img = (PGMImage) this.img;
            img.turnLeft();
            this.img = img;
        }
    }
    public void VerticalFlipImg(){
        if (this.resizedImg instanceof PGMImage) {
            PGMImage resizeImg = (PGMImage) this.resizedImg;
            resizeImg.flipRows();
            this.resizedImg = resizeImg;
            
            PGMImage img = (PGMImage) this.img;
            img.flipRows();
            this.img = img;
        }
    }
    public void HorizontalFlipImg(){
        if (this.resizedImg instanceof PGMImage) {
            PGMImage resizeImg = (PGMImage) this.resizedImg;
            resizeImg.flipColumns();
            this.resizedImg = resizeImg;
            
            PGMImage img = (PGMImage) this.img;
            img.flipColumns();
            this.img = img;
        }
    }
    public void negativeFilter(){
        if (this.resizedImg instanceof PGMImage) {
            PGMImage resizeImg = (PGMImage) this.resizedImg;
            resizeImg.negativeFilter();
            this.resizedImg = resizeImg;
            
            PGMImage img = (PGMImage) this.img;
            img.negativeFilter();
            this.img = img;
        }
    }
    public void boxFilter(){
        if (this.resizedImg instanceof PGMImage) {
            PGMImage resizeImg = (PGMImage) this.resizedImg;
            resizeImg.boxFilter();
            this.resizedImg = resizeImg;
            
            PGMImage img = (PGMImage) this.img;
            img.boxFilter();
            this.img = img;
        }
    }
    private PGMImage acotaImagenPGM(int width, int height, PGMImage resizeImg, boolean byPixel) throws Exception{
        int iterations = 0;
        int n = -1;
        while(resizeImg.getHeight() - height > resizeImg.getWidth()- width && resizeImg.getHeight() > height && n == -1){
            for (Double key : this.factorOfResizingKeyList) {
                key /= Math.pow(2, iterations);
                if (resizeImg.getHeight()*key < height) {
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
        resizeImg.moreResolution(n, byPixel);
        while(factor >= 1){
            resizeImg.lessResolution();
            factor /= 2;
        }

        for (int i = 0; i < iterations - 1; i++) {
            resizeImg.lessResolution();
        }
        return resizeImg;
    }
    
    public void save() throws Exception{
        if (this.resizedImg instanceof PGMImage) {
            PGMImage img = (PGMImage) this.img;
            img.save(img.getPath());
            this.img = img;
        }
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
        resizedImg = (PGMImage) img;
    }
}
