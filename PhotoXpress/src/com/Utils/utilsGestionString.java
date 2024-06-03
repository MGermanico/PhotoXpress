/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Utils;

import java.util.Scanner;

/**
 *
 * @author dev
 */
public abstract class utilsGestionString {
    public static String normalizaTamanyos(String str, int n){
        if (str.length() > n) {
            if (str.length() == n - 1) {
                str = str.substring(0, n - 1) + ".";
            }else if (str.length() == n - 2) {
                str = str.substring(0, n - 2) + "..";
            }else{
                str = str.substring(0, n - 3) + "...";
            }
        }
        while(str.length() < n){
            str += " ";
        }
        return str;
    }
    
    public static String normalizaTamanyos(String str, int n, boolean porAtras){
        if (porAtras) {
            str = normalizaTamanyos(str, n);
        }else{
            if (str.length() > n) {
                str = str.substring(str.length() - n);
            }
            while(str.length() < n){
                str = " " + str;
            }
        }
        return str;
    }
    
    public static double dejarNDecimales(double num, int n){
        return ((int)(num*(10*n)))/(10*n);
    }
    public static String dejarNDecimalesStr(double num, int n){
        return ((int)(num*(10*n)))/(10*n) + "";
    }
}
