/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.im.Objects;

import com.im.exceptions.InvalidFormatException;
import com.im.exceptions.InvalidImageSizeException;
import com.im.exceptions.InvalidNumberException;
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
//        System.out.println(height);
//        System.out.println(width);
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
//                System.out.println(h + " , " + w);
                retArr[h][w] = (int)this.imgArr[h][w];
            }
        }
        return retArr;
    }
    
    public void earlySave()throws Exception{
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
    
    public void lessResolution() throws Exception{
        double[][] arrTmp = new double[(int)this.height/2][(int)this.width/2];
        arrTmp = splitSize(arrTmp);
        this.height = Math.floor(this.height/2);
        this.width = Math.floor(this.height/2);
        this.imgArr = arrTmp;
        try {
            this.earlySave();
        } catch (Exception ex) {
            throw ex;
        }
    }
    private double[][] splitSize(double[][] arrT){
        for (int i = 0, iT = 0; i < this.height; i += 2, iT++) {
            for (int j = 0, jT = 0; j < this.width; j += 2, jT++) {
                if (iT < arrT.length && jT < arrT[iT].length) {
                    arrT[iT][jT] = this.imgArr[i][j];
                }
            }
        }
        return arrT;
    }
    public void moreResolution(int n) throws Exception{
        double[][] arrTmp = new double[(int)this.height*n][(int)this.width*n];
        arrTmp = multiplySize(arrTmp, n);
        arrTmp = fillVoid(arrTmp, n);
        this.height = Math.ceil(this.height*n);
        this.width = Math.ceil(this.width*n);
        this.imgArr = arrTmp;
        try {
            this.earlySave();
        } catch (Exception ex) {
            throw ex;
        }
    }
    private double[][] multiplySize(double[][] arrT, int n) throws InvalidNumberException{
        if (n <= 0) {
            throw new InvalidNumberException();
        }
        for (int i = 0, iT = 0; i < this.height; iT += n, i++) {
            for (int j = 0, jT = 0; j < this.width; jT += n, j++) {
                if (iT < arrT.length && jT < arrT[iT].length) {
                    arrT[iT][jT] = this.imgArr[i][j];
                    for (int k = 1; k < n; k++) {
                        arrT[iT][jT + k] = -1;
                    }
                }
            }
            for (int k = 1; k < n; k++) {
                for (int j = 0, jT = 0; j < this.width; jT += n, j++) {
                    if (iT < arrT.length && jT < arrT[iT].length) {
                        for (int l = 0; l < n; l++) {
                        arrT[iT+k][jT + l] = -1;
                        }
                    }
                }
            }
        }
        return arrT;
    }
    private double[][] fillVoid(double[][] arrT, int n){
        for (int h = 0; h < arrT.length; h++) {
            for (int w = 0; w < arrT[h].length; w++) {
                if (arrT[h][w] == -1) {
                    arrT[h][w] = colorAdder(arrT, 1, h, w);
                }
            }
        }
        return arrT;
    }
    private double colorAdder(double[][] arrT, int n, int hCell, int wCell){
        double ret;
        double amount = 0;
        double nColoredCells = 0;
        int sideToCheck = 3+2*(n-1);
//        System.out.println(sideToCheck);
        for (int h = -n; h < sideToCheck-1; h++) {
            for (int w = -n; w < sideToCheck-1; w++) {
//                System.out.println(hCell + " , " + wCell + " : " + (hCell+h) + " , " + (wCell+w));
                try{
                    if (arrT[hCell+h][wCell+w] != -1 &&
                            !(h == 0 && w == 0)
                        ) {
                        amount += arrT[hCell+h][wCell+w];
                        nColoredCells++;
                    }
                }catch(IndexOutOfBoundsException ex){
                }
            }
        }
//        System.out.println(amount "/" +);
        ret = amount/nColoredCells;
        return ret;
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
