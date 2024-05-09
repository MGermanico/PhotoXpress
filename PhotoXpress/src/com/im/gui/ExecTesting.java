/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.im.gui;

import com.im.Objects.PGMImage;
import com.im.ImageManager;
import com.im.exceptions.InvalidFormatException;
import com.im.exceptions.InvalidImageSizeException;
import com.im.exceptions.InvalidPGMMaxWhiteException;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author migue
 */
public class ExecTesting {
    public static void main(String[] args) {
        try {
            ImageManager ti = new ImageManager("./imagenes/gato.pgm");
            PGMImage img = (PGMImage)ti.getImg();
            img.showArrayOnLogger();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExecTesting.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(ExecTesting.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidImageSizeException ex) {
            Logger.getLogger(ExecTesting.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidPGMMaxWhiteException ex) {
            Logger.getLogger(ExecTesting.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
