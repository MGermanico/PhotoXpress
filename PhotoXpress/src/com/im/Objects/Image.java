/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.im.Objects;

import com.im.exceptions.InvalidImageSizeException;
import com.im.exceptions.InvalidImagePathException;
import java.io.File;

/**
 *
 * @author migue
 */
public abstract class Image {
    protected File imgFile;
    protected double height;
    protected double width;

    public File getImgFile() {
        return imgFile;
    }

    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }

    public String getPath(){
        return this.imgFile.getPath();
    }

    public void setPath(String path) throws InvalidImagePathException {
        File tmpFile = new File(path);
        if (tmpFile == null) {
            throw new InvalidImagePathException();
        }else{
            this.imgFile = tmpFile;
        }
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) throws InvalidImageSizeException {
        if (height <= 0) {
            throw new InvalidImageSizeException();
        }else{
            this.height = height;
        }
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) throws InvalidImageSizeException {
        if (width <= 0) {
            throw new InvalidImageSizeException();
        }else{
            this.width = width;
        }
    }
    
    
}
