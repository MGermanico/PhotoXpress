/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.im.Objects;

import com.Utils.utilsGestionString;
import com.im.exceptions.InvalidFormatException;
import com.im.exceptions.InvalidImageSizeException;
import com.im.exceptions.InvalidNumberException;
import com.im.exceptions.InvalidPGMMaxWhiteException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.NoSuchElementException;
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
        super.imgFile = img;
        constructor(img);
    }
    
    public PGMImage(File img) throws FileNotFoundException, InvalidFormatException, InvalidImageSizeException, InvalidPGMMaxWhiteException {
        super.imgFile = img;
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
            pgmToArray(scFile, 255/this.maxWhite);
            this.setMaxWhite(255);
        } catch (FileNotFoundException  | 
                InvalidFormatException | 
                InvalidImageSizeException | 
                InvalidPGMMaxWhiteException |
                NoSuchElementException ex) {
            throw ex;
        }
    }
    private void pgmToArray(Scanner sc, double factor) throws InvalidFormatException{
        this.imgArr = new double[(int)this.height][(int)this.width];
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.imgArr[i][j] = sc.nextInt() * factor;
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
    public void turnRight(){
//        System.out.println(this.height);
        changeColumnsByRows();
        flipColumns();
//        System.out.println(this.height);
    }
    public void turnLeft(){
        changeColumnsByRows();
        flipRows();
    }
    
    public void negativeFilter(){
        for (int h = 0; h < this.imgArr.length; h++) {
            for (int w = 0; w < this.imgArr[h].length; w++) {
                this.imgArr[h][w] = 255 - this.imgArr[h][w];
            }
        }
    }
    
    public void boxFilter(){
        for (int h = 0; h < this.imgArr.length; h++) {
            for (int w = 0; w < this.imgArr[h].length; w++) {
                this.imgArr[h][w] = getAverageColor(this.imgArr, h, w);
            }
        }
    }
    private double getAverageColor(double[][] imgArr, int hCell, int wCell){
        double ret;
        double amount = 0;
        int nCells = 0;
        for (int h = -1; h < 2; h++) {
            for (int w = -1; w < 2; w++) {
                try{
                    amount += imgArr[hCell+h][wCell+w];
                    nCells++;//todo - terminar
                }catch(IndexOutOfBoundsException ex){
                }
            }
        }
        ret = amount/nCells;
        return ret; 
    }
    private void changeColumnsByRows(){
        double[][] arrTmp = new double[(int)this.width][(int)this.height];
        double height = this.height;
        this.height = this.width;
        this.width = height;
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                arrTmp[i][j] = this.imgArr[j][i];
            }
        }
        this.imgArr = arrTmp;
    }
    
    public void flipRows(){
        double[][] arrTmp = new double[(int)this.height][(int)this.width];
        for (int i = 0, iT = (int)this.height - 1; i < this.height; i++, iT--) {
            for (int j = 0, jT = (int)this.width - 1; j < this.width; j++, jT--) {
                arrTmp[i][j] = this.imgArr[iT][j];
            }
        }
        this.imgArr = arrTmp;
    }
    
    public void flipColumns(){
        double[][] arrTmp = new double[(int)this.height][(int)this.width];
        for (int i = 0, iT = (int)this.height - 1; i < this.height; i++, iT--) {
            for (int j = 0, jT = (int)this.width - 1; j < this.width; j++, jT--) {
                arrTmp[i][j] = this.imgArr[i][jT];
            }
        }
        this.imgArr = arrTmp;
    }
    
    public void save(String path)throws Exception{
        if (path == null) {
            path = this.presavedFilePath;
        }
        File file = new File(path);
        FileWriter fw = new FileWriter(file, false);
        int[][] imgArr = this.pgmToIntArray();
        fw.write("P2\n" + this.messege + "\n" + (int)this.width + " " + (int)this.height + "\n" + (int)this.maxWhite + "\n");
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
//                System.out.println(i + " , " + j + " = " + imgArr[i][j]);
                fw.write(utilsGestionString.normalizaTamanyos(imgArr[i][j] + "", 4));
            }
            fw.write("\n");
        }
        fw.close();
    }
    
    public void lessResolution() throws Exception{
        double[][] arrTmp = new double[(int)this.height/2][(int)this.width/2];
        arrTmp = splitSize(arrTmp);
        this.height = Math.floor(this.height/2);
        this.width = Math.floor(this.width/2);
        this.imgArr = arrTmp;
        try {
            this.save(null);
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
    public void moreResolution(double nInput, boolean byPixel) throws Exception{
        int n;
        if (nInput > 0 && nInput < 1) {
            n = 1;
//            System.out.println("por muy poco");
        }else{
            n = (int)nInput;
        }
        double[][] arrTmp = new double[(int)this.height*n][(int)this.width*n];
        arrTmp = multiplySize(arrTmp, n);
        arrTmp = fillVoid(arrTmp, n, byPixel);
        this.height = Math.ceil(this.height*n);
        this.width = Math.ceil(this.width*n);
        this.imgArr = arrTmp;
        try {
            this.save(null);
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
    private double[][] fillVoid(double[][] arrT, int n, boolean byPixel){
        double colorUp, colorDown, colorRight, colorLeft;
        double colorUpRight, colorUpLeft, colorDownLeft, colorDownRight;
        for (int h = 0; h < arrT.length; h = h + n) {
            for (int w = 0; w < arrT[h].length; w = w + n) {
                colorAdderPixel(arrT, n, h, w, arrT[h][w]);
            }
        }
        return arrT;
    }
    private double findColor(double[][] arr, int h, int w, int nh, int nw){
        try{
            return arr[h + nh][w + nw];
        } catch (IndexOutOfBoundsException ex) {
            return arr[h][w];
        }
    }
    private void colorAdderPixel(double[][] arrT, int n, int hCell, int wCell, double color){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                arrT[i+hCell][j+wCell] = color;
            }
        }
    }
    private double getGraddientValue(double v1, double v2, int k, int f){
        return v1 + k * ( ( ( (v1+v2) / 2) - v1) / f);
    }
    private double[][] copy(double[][] arr){
        double[][] arrRet = new double[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arrRet[i][j] = arr[i][j];
            }
        }
        return arrRet;
    }
    // <test>
    public void showArrayOnLogger(){
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                System.out.print(this.imgArr[i][j]);
            }
            System.out.println("");
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
