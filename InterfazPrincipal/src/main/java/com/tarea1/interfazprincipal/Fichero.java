/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tarea1.interfazprincipal;

import java.io.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author kenda
 */
public class Fichero {
    ArrayList<ArrayList<String>> matriz=new ArrayList<>();// codigo asm
    ArrayList<String> codigoBinario=new ArrayList<>();//codigo binario
    ArrayList<String> AC=new ArrayList<>();
    ArrayList<String> AX=new ArrayList<>();
    ArrayList<String> BX=new ArrayList<>();
    ArrayList<String> CX=new ArrayList<>();
    ArrayList<String> DX=new ArrayList<>();
    
    public void leerFichero(String ruta){
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File (ruta);
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            int cont=1;
            boolean bandera=false;
            while((linea=br.readLine())!=null){
                ArrayList<String> lineaMovimiento=new ArrayList<String>();
                String[] movimiento=linea.split(" ");
                if(verificarPrimerOperador(movimiento)){
                    for(String mov:movimiento){
                        String base=removePunctuations(mov);
                        lineaMovimiento.add(base); 
                    }
                    String lineaCodigoBinario=codigoBinarioMovimiento(movimiento)+" "+codigoBinarioRegistro(movimiento)+" "+codigoBinarioDecimal(movimiento);
                    this.matriz.add(lineaMovimiento);
                    this.codigoBinario.add(lineaCodigoBinario);
                    this.obtenerMovimientoLinea(movimiento);
                }else{
                    JOptionPane.showMessageDialog(null, "Hay un problema en la linea " + cont+", por favor revisar.");
                    bandera=true;
                    break;
                }
                cont+=1;
            } 
            if(!bandera){
                JOptionPane.showMessageDialog(null, "Se leyó correctamente el archivo.");
            }
        }
        catch(Exception e){
           e.printStackTrace();
        }finally{
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try{                    
                if( null != fr ){   
                    fr.close();     
                }                  
            }catch (Exception e2){ 
                e2.printStackTrace();
            }
      }
    }
    public static String removePunctuations(String source) {
        return source.replaceAll("[!\"#$%&'()*+,./:;<=>?@\\[\\]^_`{|}~]", "");
    }
    
    public void leerMatriz(){
        for(int i=0;i<matriz.size();i++){
            ArrayList<String> defi=matriz.get(i);
            System.out.println("Linea:"+ i);
            for(int e=0;e<defi.size();e++){
                System.out.println("["+defi.get(e)+"]");
            }
        }
    }
    
    public void leerListaCodigo(){
        for(int i=0;i<codigoBinario.size();i++){
            System.out.println(codigoBinario.get(i));
        }
    }
    public void leerAC(){
        for(int i=0;i<AC.size();i++){
            System.out.println(AC.get(i));
        }
    }
    public void leerAX(){
        for(int i=0;i<AX.size();i++){
            System.out.println(AX.get(i));
        }
    }
    public void leerBX(){
        for(int i=0;i<BX.size();i++){
            System.out.println(BX.get(i));
        }
    }
    public void leerCX(){
        for(int i=0;i<CX.size();i++){
            System.out.println(CX.get(i));
        }
    }
    public void leerDX(){
        for(int i=0;i<DX.size();i++){
            System.out.println(DX.get(i));
        }
    }
    public boolean verificarPrimerOperador(String[] palabra){
        String primero=palabra[0].toUpperCase();
        String segundo=removePunctuations(palabra[1].toUpperCase());
        if(primero.equals("MOV") && (segundo.equals("AX") || segundo.equals("BX") || segundo.equals("CX") || segundo.equals("DX"))){
            if(palabra.length==3){
                String tercero=palabra[2].toUpperCase();
                boolean isNumeric = isNumeric(tercero);
                return isNumeric;
            }else{
                return false;
            }
        }else if(primero.equals("ADD") && (segundo.equals("AX") || segundo.equals("BX") || segundo.equals("CX") || segundo.equals("DX")) && palabra.length==2){
            return true;
        }else if(primero.equals("LOAD") && (segundo.equals("AX") || segundo.equals("BX") || segundo.equals("CX") || segundo.equals("DX"))&& palabra.length==2){
            return true;
        }else if(primero.equals("SUB") && (segundo.equals("AX") || segundo.equals("BX") || segundo.equals("CX") || segundo.equals("DX")) && palabra.length==2){
            return true;
        }else if(primero.equals("STORE") && (segundo.equals("AX") || segundo.equals("BX") || segundo.equals("CX") || segundo.equals("DX"))&& palabra.length==2){
            return true;
        }else{
            return false;
        }
    }
    
    public static boolean isNumeric(String s)
    {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
    public String codigoBinarioMovimiento(String[] registro){
        String primero=removePunctuations(registro[0].toUpperCase());
        if(primero.equals("MOV")){
            return "0011";
        }else if(primero.equals("LOAD")){
            return "0001";
        }else if(primero.equals("ADD")){
            return "0101";
        }else if(primero.equals("SUB")){
            return "0100";
        }else{
            return "0010";
        }
    }
    
    public String codigoBinarioRegistro(String[] registro){
        String segundo=removePunctuations(registro[1].toUpperCase());
        if(segundo.equals("AX")){
            return "0001";
        }else if(segundo.equals("BX")){
            return "0010";
        }else if(segundo.equals("CX")){
            return "0011";
        }else{
            return "0100";
        }
    }
    public String codigoBinarioDecimal(String[] registro){
        if(registro.length==3){
            String tercero=registro[2];
            char negativo=tercero.charAt(0);
            boolean bandera=false;
            if(Character.compare(negativo,'-')==0){
                bandera=true;
                char valor=tercero.charAt(1);
                String binario=convertirDecimalABinario(Character.getNumericValue(valor));
                String ceros=obtenerCerosFaltantes(7-binario.length());
                return "1"+ceros+binario;
            }else{
                String binario=convertirDecimalABinario(Integer.parseInt(tercero));
                String ceros=obtenerCerosFaltantes(8-binario.length());
                return ceros+binario;
            } 
        }else{
            return "00000000";
        }
        
    }
    
    public String convertirDecimalABinario(long decimal) {
		return Long.toBinaryString(decimal);
	}
    public String obtenerCerosFaltantes(int cantidad){
        String ceros="";
        for(int i=0;i<cantidad;i++){
                ceros+="0";
        }
        return ceros;
    }
    public void obtenerMovimientoLinea(String[] linea){
        String primero=linea[0].toUpperCase();
        String segundo=removePunctuations(linea[1].toUpperCase());
        String acMov="";
        String axMov="";
        String bxMov="";
        String cxMov="";
        String dxMov="";
        //=====================MOV==========================
        if(primero.equals("MOV")){
            if(segundo.equals("AX")){
                axMov=linea[2];
                bxMov=!BX.isEmpty()?BX.get(BX.size()-1):"0";
                cxMov=!CX.isEmpty()?CX.get(CX.size()-1):"0";
                dxMov=!DX.isEmpty()?DX.get(DX.size()-1):"0";
                acMov=!AC.isEmpty()?AC.get(AC.size()-1):"0";
            }else if(segundo.equals("BX")){
                bxMov=linea[2];
                axMov=!AX.isEmpty()?AX.get(AX.size()-1):"0";
                cxMov=!CX.isEmpty()?CX.get(CX.size()-1):"0";
                dxMov=!DX.isEmpty()?DX.get(DX.size()-1):"0";
                acMov=!AC.isEmpty()?AC.get(AC.size()-1):"0";
            }else if(segundo.equals("CX")){
                cxMov=linea[2];
                axMov=!AX.isEmpty()?AX.get(AX.size()-1):"0";
                bxMov=!BX.isEmpty()?BX.get(BX.size()-1):"0";
                dxMov=!DX.isEmpty()?DX.get(DX.size()-1):"0";
                acMov=!AC.isEmpty()?AC.get(AC.size()-1):"0";
            }else if(segundo.equals("DX")){
                dxMov=linea[2];
                axMov=!AX.isEmpty()?AX.get(AX.size()-1):"0";
                bxMov=!BX.isEmpty()?BX.get(BX.size()-1):"0";
                cxMov=!CX.isEmpty()?CX.get(CX.size()-1):"0";
                acMov=!AC.isEmpty()?AC.get(AC.size()-1):"0";
            }
        }//=====================LOAD==========================
        else if(primero.equals("LOAD")){
            if(segundo.equals("AX")){
                axMov=!AX.isEmpty()?AX.get(AX.size()-1):"0";
                bxMov=!BX.isEmpty()?BX.get(BX.size()-1):"0";
                cxMov=!CX.isEmpty()?CX.get(CX.size()-1):"0";
                dxMov=!DX.isEmpty()?DX.get(DX.size()-1):"0";
                acMov=!AX.isEmpty()?AX.get(AX.size()-1):"0";
            }else if(segundo.equals("BX")){
                bxMov=!BX.isEmpty()?BX.get(BX.size()-1):"0";
                axMov=!AX.isEmpty()?AX.get(AX.size()-1):"0";
                cxMov=!CX.isEmpty()?CX.get(CX.size()-1):"0";
                dxMov=!DX.isEmpty()?DX.get(DX.size()-1):"0";
                acMov=!BX.isEmpty()?BX.get(BX.size()-1):"0";
            }else if(segundo.equals("CX")){
                cxMov=!CX.isEmpty()?CX.get(CX.size()-1):"0";
                axMov=!AX.isEmpty()?AX.get(AX.size()-1):"0";
                bxMov=!BX.isEmpty()?BX.get(BX.size()-1):"0";
                dxMov=!DX.isEmpty()?DX.get(DX.size()-1):"0";
                acMov=!CX.isEmpty()?CX.get(CX.size()-1):"0";
            }else if(segundo.equals("DX")){
                dxMov=!DX.isEmpty()?DX.get(DX.size()-1):"0";
                axMov=!AX.isEmpty()?AX.get(AX.size()-1):"0";
                bxMov=!BX.isEmpty()?BX.get(BX.size()-1):"0";
                cxMov=!CX.isEmpty()?CX.get(CX.size()-1):"0";
                acMov=!DX.isEmpty()?DX.get(DX.size()-1):"0";
            }
        }
        //=======================ADD=========================
        else if(primero.equals("ADD")){
            if(segundo.equals("AX")){
                int acumulador=Integer.parseInt(AC.get(AC.size()-1));
                int valor=Integer.parseInt(AX.get(AX.size()-1));
                String resultado=Integer.toString(acumulador+valor);
                axMov=!AX.isEmpty()?AX.get(AX.size()-1):"0";
                bxMov=!BX.isEmpty()?BX.get(BX.size()-1):"0";
                cxMov=!CX.isEmpty()?CX.get(CX.size()-1):"0";
                dxMov=!DX.isEmpty()?DX.get(DX.size()-1):"0";
                acMov=resultado;
            }else if(segundo.equals("BX")){
                int acumulador=Integer.parseInt(AC.get(AC.size()-1));
                int valor=Integer.parseInt(BX.get(BX.size()-1));
                String resultado=Integer.toString(acumulador+valor);
                bxMov=!BX.isEmpty()?BX.get(BX.size()-1):"0";
                axMov=!AX.isEmpty()?AX.get(AX.size()-1):"0";
                cxMov=!CX.isEmpty()?CX.get(CX.size()-1):"0";
                dxMov=!DX.isEmpty()?DX.get(DX.size()-1):"0";
                acMov=resultado;
            }else if(segundo.equals("CX")){
                int acumulador=Integer.parseInt(AC.get(AC.size()-1));
                int valor=Integer.parseInt(CX.get(CX.size()-1));
                String resultado=Integer.toString(acumulador+valor);
                cxMov=!CX.isEmpty()?CX.get(CX.size()-1):"0";
                axMov=!AX.isEmpty()?AX.get(AX.size()-1):"0";
                bxMov=!BX.isEmpty()?BX.get(BX.size()-1):"0";
                dxMov=!DX.isEmpty()?DX.get(DX.size()-1):"0";
                acMov=resultado;
            }else if(segundo.equals("DX")){
                int acumulador=Integer.parseInt(AC.get(AC.size()-1));
                int valor=Integer.parseInt(DX.get(DX.size()-1));
                String resultado=Integer.toString(acumulador+valor);
                dxMov=!DX.isEmpty()?DX.get(DX.size()-1):"0";
                axMov=!AX.isEmpty()?AX.get(AX.size()-1):"0";
                bxMov=!BX.isEmpty()?BX.get(BX.size()-1):"0";
                cxMov=!CX.isEmpty()?CX.get(CX.size()-1):"0";
                acMov=resultado;
            }
        }
        //==============================SUB=====================
        else if(primero.equals("SUB")){
            if(segundo.equals("AX")){
                int acumulador=Integer.parseInt(AC.get(AC.size()-1));
                int valor=Integer.parseInt(AX.get(AX.size()-1));
                String resultado=Integer.toString(acumulador-valor);
                axMov=!AX.isEmpty()?AX.get(AX.size()-1):"0";
                bxMov=!BX.isEmpty()?BX.get(BX.size()-1):"0";
                cxMov=!CX.isEmpty()?CX.get(CX.size()-1):"0";
                dxMov=!DX.isEmpty()?DX.get(DX.size()-1):"0";
                acMov=resultado;
            }else if(segundo.equals("BX")){
                int acumulador=Integer.parseInt(AC.get(AC.size()-1));
                int valor=Integer.parseInt(BX.get(BX.size()-1));
                String resultado=Integer.toString(acumulador-valor);
                bxMov=!BX.isEmpty()?BX.get(BX.size()-1):"0";
                axMov=!AX.isEmpty()?AX.get(AX.size()-1):"0";
                cxMov=!CX.isEmpty()?CX.get(CX.size()-1):"0";
                dxMov=!DX.isEmpty()?DX.get(DX.size()-1):"0";
                acMov=resultado;
            }else if(segundo.equals("CX")){
                int acumulador=Integer.parseInt(AC.get(AC.size()-1));
                int valor=Integer.parseInt(CX.get(CX.size()-1));
                String resultado=Integer.toString(acumulador-valor);
                cxMov=!CX.isEmpty()?CX.get(CX.size()-1):"0";
                axMov=!AX.isEmpty()?AX.get(AX.size()-1):"0";
                bxMov=!BX.isEmpty()?BX.get(BX.size()-1):"0";
                dxMov=!DX.isEmpty()?DX.get(DX.size()-1):"0";
                acMov=resultado;
            }else if(segundo.equals("DX")){
                int acumulador=Integer.parseInt(AC.get(AC.size()-1));
                int valor=Integer.parseInt(DX.get(DX.size()-1));
                String resultado=Integer.toString(acumulador-valor);
                dxMov=!DX.isEmpty()?DX.get(DX.size()-1):"0";
                axMov=!AX.isEmpty()?AX.get(AX.size()-1):"0";
                bxMov=!BX.isEmpty()?BX.get(BX.size()-1):"0";
                cxMov=!CX.isEmpty()?CX.get(CX.size()-1):"0";
                acMov=resultado;
            }
        }
        //===========================STORE=============================
        else if(primero.equals("STORE")){
            if(segundo.equals("AX")){
                axMov=!AC.isEmpty()?AC.get(AC.size()-1):"0";
                bxMov=!BX.isEmpty()?BX.get(BX.size()-1):"0";
                cxMov=!CX.isEmpty()?CX.get(CX.size()-1):"0";
                dxMov=!DX.isEmpty()?DX.get(DX.size()-1):"0";
                acMov=!AC.isEmpty()?AC.get(AC.size()-1):"0";
            }else if(segundo.equals("BX")){
                bxMov=!AC.isEmpty()?AC.get(AC.size()-1):"0";
                axMov=!AX.isEmpty()?AX.get(AX.size()-1):"0";
                cxMov=!CX.isEmpty()?CX.get(CX.size()-1):"0";
                dxMov=!DX.isEmpty()?DX.get(DX.size()-1):"0";
                acMov=!AC.isEmpty()?AC.get(AC.size()-1):"0";
            }else if(segundo.equals("CX")){
                cxMov=!AC.isEmpty()?AC.get(AC.size()-1):"0";
                axMov=!AX.isEmpty()?AX.get(AX.size()-1):"0";
                bxMov=!BX.isEmpty()?BX.get(BX.size()-1):"0";
                dxMov=!DX.isEmpty()?DX.get(DX.size()-1):"0";
                acMov=!AC.isEmpty()?AC.get(AC.size()-1):"0";
            }else if(segundo.equals("DX")){
                dxMov=!AC.isEmpty()?AC.get(AC.size()-1):"0";
                axMov=!AX.isEmpty()?AX.get(AX.size()-1):"0";
                bxMov=!BX.isEmpty()?BX.get(BX.size()-1):"0";
                cxMov=!CX.isEmpty()?CX.get(CX.size()-1):"0";
                acMov=!AC.isEmpty()?AC.get(AC.size()-1):"0";
            } 
        }
        AC.add(acMov);
        AX.add(axMov);
        BX.add(bxMov);
        CX.add(cxMov);
        DX.add(dxMov);
        
    }
    
}
