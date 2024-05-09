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
import javax.swing.JPanel;

/**
 *
 * @author migue
 */
public class ImageManager {
    private Image img;
    private Image resizeImg;
    
    public ImageManager(String path) throws FileNotFoundException, InvalidFormatException, InvalidImageSizeException, InvalidPGMMaxWhiteException{
        try{
            PGMImage img = new PGMImage(path);
            resizeImg = img;
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

    public JPanel pintarImagen() throws InvalidFormatException, Exception{
        if (this.resizeImg instanceof PGMImage) {
            PGMImage resizeImg;
            resizeImg = (PGMImage) this.resizeImg;
            resizeImg.grabar();
            ImagePanelPGM pgmpanel = new ImagePanelPGM(resizeImg);
            while(pgmpanel.HEIGHT > pgmpanel.getImg().getHeight()){
                if (pgmpanel.HEIGHT < pgmpanel.getImg().getHeight() * 0.25) {
                    resizeImg.disminuirResolucion();
                    resizeImg.disminuirResolucion();
                    pgmpanel = new ImagePanelPGM(resizeImg);
                } else if (pgmpanel.HEIGHT < pgmpanel.getImg().getHeight() * 0.50) {
                    resizeImg.disminuirResolucion();
                    pgmpanel = new ImagePanelPGM(resizeImg);
                } else if (pgmpanel.HEIGHT < pgmpanel.getImg().getHeight() * 0.75) {
                    resizeImg.disminuirResolucion();
                    resizeImg.disminuirResolucion();
                    pgmpanel = new ImagePanelPGM(resizeImg);
                    pgmpanel.setResolution(3);
                }
            }
            return pgmpanel;
        }else{
            throw new InvalidFormatException();
        }
    }
    
    private void constructor(Image img){
        this.img = img;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
        resizeImg = img;
    }
}
