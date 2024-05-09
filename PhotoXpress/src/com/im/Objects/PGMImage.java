/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.im.Objects;

import com.im.exceptions.InvalidFormatException;
import com.im.exceptions.InvalidImageSizeException;
import com.im.exceptions.InvalidPGMMaxWhiteException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

/**
 *
 * @author migue
 */
public class PGMImage extends Image{
    private final String presavedFilePath = "./src/com/im/data/presavedImage.pgm";
    private String messege;
    private double maxWhite;
    private double[][] imgArr;

    public PGMImage(String path) throws FileNotFoundException, InvalidFormatException, InvalidImageSizeException, InvalidPGMMaxWhiteException {
        File img = new File(path);
        constructor(img);
    }
    
    public PGMImage(File img) throws FileNotFoundException, InvalidFormatException, InvalidImageSizeException, InvalidPGMMaxWhiteException {
        constructor(img);
    }
    
    private void constructor(File img) throws FileNotFoundException, InvalidFormatException, InvalidImageSizeException, InvalidPGMMaxWhiteException{
        try(Scanner scFile = new Scanner(img)) {
            if (!scFile.nextLine().equals("P2")) {
                throw new InvalidFormatException();
            }
            this.messege = scFile.nextLine();
            this.setWidth(scFile.nextInt());
            this.setHeight(scFile.nextInt());
            this.setMaxWhite(scFile.nextInt());
            pgmToArray(scFile);
        } catch (FileNotFoundException  | 
                InvalidFormatException | 
                InvalidImageSizeException | 
                InvalidPGMMaxWhiteException ex) {
            throw ex;
        }
    }
    
    private void pgmToArray(Scanner sc) throws InvalidFormatException{
        this.imgArr = new double[(int)this.height][(int)this.width];
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.imgArr[i][j] = sc.nextInt();
            }
        }
    }
    
    public int[][] pgmToIntArray(){
        int[][] retArr;
        int height = (int)this.height;
        int width = (int)this.width;
        retArr = new int[height][width];
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                retArr[h][w] = (int)this.imgArr[h][w];
            }
        }
        return retArr;
    }
    
    public void grabar()throws Exception{
        File file = new File(this.presavedFilePath);
        FileWriter fw = new FileWriter(file, false);
        int[][] imgArr = this.pgmToIntArray();
        fw.write("P2\n" + this.messege + "\n" + (int)this.width + " " + (int)this.height + "\n" + (int)this.maxWhite + "\n");
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
//                System.out.println(i + " , " + j + " = " + imgArr[i][j]);
                fw.write(imgArr[i][j] + " ");
            }
            fw.write("\n");
        }
        fw.close();
    }
    
    public void disminuirResolucion() throws Exception{
        double[][] imagenArrTmp = new double[(int)this.height/2][(int)this.width/2];
        imagenArrTmp = divideSize(imagenArrTmp);
        this.height = Math.floor(this.height/2);
        this.width = Math.floor(this.height/2);
        this.imgArr = imagenArrTmp;
        try {
            this.grabar();
        } catch (Exception ex) {
            throw ex;
        }
    }
    private double[][] divideSize(double[][] arrT){
        for (int i = 0, iT = 0; i < this.height; i += 2, iT++) {
            for (int j = 0, jT = 0; j < this.width; j += 2, jT++) {
                if (iT < arrT.length && jT < arrT[iT].length) {
                    arrT[iT][jT] = this.imgArr[i][j];
                }
            }
        }
        return arrT;
    }
    // <test>
    public void showArrayOnLogger(){
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                System.out.println(this.imgArr[i][j]);
            }
        }
    }
    // </test>
    public String getMessege() {
        return messege;
    }

    public void setMessege(String messege) {
        this.messege = messege;
    }

    public double getMaxWhite() {
        return maxWhite;
    }

    public void setMaxWhite(double maxWhite) throws InvalidPGMMaxWhiteException {
        if (maxWhite < 0) {
            throw new InvalidPGMMaxWhiteException();
        }else{
            this.maxWhite = maxWhite;
        }
    }

    public double[][] getImgArr() {
        return imgArr;
    }

    public void setImgArr(double[][] imgArr) {
        this.imgArr = imgArr;
    }
    
    
}
