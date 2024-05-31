/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.util.Scanner;

/**
 *
 * @author dev
 */
public class utilsCreadorMenus {
    public static void separador(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
    
    public static String creadorDeMenus(String accionesStr){ //Solo las preguntas
        return creadorDeMenus(accionesStr, true, "", -1);
    }
    public static String creadorDeMenus(String accionesStr, String posAPreguntar){ // preguntas + P.Seguridad
        return creadorDeMenus(accionesStr, true, posAPreguntar, -1);
    }
    public static String creadorDeMenus(String accionesStr, boolean salir){//preguntas + salir
        return creadorDeMenus(accionesStr, salir, "", -1);
    }
    public static String creadorDeMenus(String accionesStr, boolean salir, boolean repetir){//preguntas + repetir + salir
        return creadorDeMenus(accionesStr, salir, repetir, "", -1);
    }
    public static String creadorDeMenus(String accionesStr, boolean salir, String posAPreguntar){//preguntas + salir + P.Seguridad
        return creadorDeMenus(accionesStr, salir, posAPreguntar, -1);
    }
    public static String creadorDeMenus(String accionesStr, boolean salir, boolean repetir, String posAPreguntar){//preguntas + salir + repetir + P.Seguridad
        return creadorDeMenus(accionesStr, salir, repetir, posAPreguntar, -1);
    }
    public static String creadorDeMenus(String accionesStr, int posAct, String posAPreguntar){//preguntas + P.Seguridad + posicion actual
        return creadorDeMenus(accionesStr, true, posAPreguntar , posAct);
    }
    public static String creadorDeMenus(String accionesStr, int posAct){//preguntas + posicion actual
        return creadorDeMenus(accionesStr, true, "" , posAct);
    }
    public static String creadorDeMenus(String accionesStr, boolean salir, int posActual){//preguntas + salir + posicion actual
        return creadorDeMenus(accionesStr, salir, "" , posActual);
    }
    public static String creadorDeMenus(String accionesStr, boolean salir, boolean repetir, int posActual){//preguntas + salir + posicion actual + repetir
        return creadorDeMenus(accionesStr, salir, repetir, "" , posActual);
    }
    public static String creadorDeMenus(String accionesStr, boolean salir, boolean repetir, String posAPreguntar, int posActual){
        String str;
        String entradasValidas = "[";
        if (salir) {
            entradasValidas += "s,";
        }
        String[] respuestas = accionesStr.split("#");
        for (int i = 0; i < respuestas.length - 1; i++) {
            entradasValidas += (i+1) + ",";
        }
        entradasValidas = entradasValidas.substring(0, entradasValidas.length()-1) + "]";
        if (repetir) {
            do {
                str = creadorDeMenus(accionesStr, salir, posAPreguntar, posActual);
            } while (str.replaceAll(entradasValidas, "").length() != 0);
        }else{
            str = str = creadorDeMenus(accionesStr, salir, "", -1);
        }
        return str;
    }
    public static String creadorDeMenus(String accionesStr, boolean salir, String posAPreguntar, int posActual){
        Scanner sc = new Scanner(System.in);
        String[] acciones = accionesStr.split("#");
        String entrada;
        String mensaje = "";
        String asterisco = "";
        mensaje += "\n\n\t" + acciones[0] + "\n\n";
        for (int i = 1; i < acciones.length; i++) {
            if (i == posActual) {
                asterisco = "*";
            }else{
                asterisco = "";
            }
            mensaje += "\t\t(" + i + ") - " + acciones[i] + "." + asterisco + "\n";
        }
        if (salir) {
            mensaje +=  "\t\t(S) - Salir.\n";
        }
        boolean repetir;
        do {
            System.out.println(mensaje);
            entrada = sc.nextLine();
            try{
                repetir = !(
                    entrada.equalsIgnoreCase("s") || 
                    (Integer.parseInt(entrada) < acciones.length && Integer.parseInt(entrada) > 0)
                );
                if (repetir) {
                    System.out.println("VALOR NO PERMITIDO");
                }
            }catch(NumberFormatException e){
                System.out.println("VALOR NO PERMITIDO");
                repetir = true;
            }catch(Exception e){
                System.out.println(e.toString());
                repetir = true;
            }
            if (!entrada.equalsIgnoreCase("S")) {
                repetir = preguntaDeSeguridad(posAPreguntar, entrada, acciones);
            }
        } while (repetir);
        return entrada;
    }
    
    public static boolean preguntaDeSeguridad(String posAPreguntar, String entrada, String[] acciones){
        boolean repetir = false;
        if (!posAPreguntar.isEmpty()) {
            String[] posPreguntasConSeguro = posAPreguntar.split(" ");
            String respuestaDeSeguridad;
            int nReturn = Integer.parseInt(entrada);
            for (int i = 0; i < posPreguntasConSeguro.length; i++) {
                if (posPreguntasConSeguro[i].equals(entrada)) {
                    respuestaDeSeguridad = creadorDeMenus(
                            "\t¿ ESTAS SEGURO DE \"" + acciones[nReturn] + "\" ?#"
                            + "SI#"
                            + "NO", false
                    );
                    if (respuestaDeSeguridad.equals("2")) {
                        repetir = true;
                    }
                }
            }
        }
        return repetir;
    }
    public static String pregunta(String str){
        Scanner sc = new Scanner(System.in);
        System.out.print(str + ":\n\t->");
        return sc.nextLine();
    }
}
