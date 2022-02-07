/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoayda;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author migue
 */
public class CCalculoAyDA {
    
    public double funcionAptitud(float x, float y){
        double aDato = -Math.cos(x);
        double bDato = Math.cos(y);
        
        double cDato = Math.pow((x-Math.PI), 2);
        double dDato = Math.pow((y-Math.PI), 2);
        double eDato = (-1)*cDato; 
        double gDato = (-1)*dDato;
        double hDato = eDato+gDato;
        double iDato = Math.exp(hDato);
        
        
        //double Dato = Math.exp(-(Math.pow(x - Math.PI, 2))- ( Math.pow(y - Math.PI, 2) ));
        double f = aDato*bDato*iDato;
        
        return f;
    }
    public int[] generarCromosoma(){
        int[] cromosomaX = new int[42];
        for (int i = 0; i < 42; i++) {
            double randomDbl = Math.random();
            if (randomDbl < 0.5) {
                cromosomaX[i]=0;
            } else {
                cromosomaX[i]=1;
            }
        }
        return cromosomaX;
    }
    
    public String decimalTobinario(int decimal){
        String numero=Integer.toBinaryString(decimal);
        return numero;
    }
    
    public int binarioToDecimal(String binario){
        int numero = Integer.parseInt(binario, 2);
        return numero;
    }
    
    public float normalizar (float a, float b, int cromosoma){
        float z = b-a;
        z = (float) (z/(Math.pow(2, 21) - 1));
        z = z*cromosoma;
        z = a + z;
        return z;
    }
    
    public int[] dividirCromosoma(int[] cromosoma, String opcion){
        int[] x = new int[21];
        if (opcion == "x") {
            for (int i = 0; i < 21; i++) {
                x[i] = cromosoma[i];
            }
        } else {
            for (int i = 21; i < 42; i++) {
                x[i-21] = cromosoma[i];
            }
        }
        return x;
    }
    public String arregloToString(int [] arreglo){
        String palabra ="";
        for (int i = 0; i < arreglo.length; i++) {
            palabra += arreglo[i];
        }
        return palabra;
    }
    
    
    //-------------------------------------------------------------------
    //Seleccion por ruleta
    public int[][] seleccionRuleta( double[]poblacionEvaluada, int[][]poblacion ){
        double f = sumaP(poblacionEvaluada);
        double[] poblacionF = new double[20];
        double[] probaQ = new double[20];
        
        for (int i = 0; i < poblacionF.length; i++) {
            poblacionF[i] = (poblacionEvaluada[i])/f;
        }
        probaQ = probabilidadAcumulada(poblacionF);
        
        //Ruleta
        double []tablaRuleta = new double[20];
        for (int i = 0; i < tablaRuleta.length; i++) {
            double randomDbl = Math.random();
            tablaRuleta[i]=randomDbl;
        }
        
        int []resultados = new int[20];
        for (int i = 0; i < tablaRuleta.length; i++) {
            int j = 0;
            while ( tablaRuleta[i] > probaQ[j]) {                
                j++;
            }
            resultados[i]=j;
        }
        
        int[][] nuevaPoblacion = new int[20][];
        for (int i = 0; i < 20; i++) {
            nuevaPoblacion[i] = poblacion[resultados[i]];
        }
       return nuevaPoblacion;
    }
    
    public double sumaP( double [] poblacionEvaluada ){
        double resultado = 0;
        for (int i = 0; i < poblacionEvaluada.length; i++) {
            if (poblacionEvaluada[i] != 0) {
                 resultado =+ poblacionEvaluada[i];
            }
           
        }
        return resultado;
    }
    
    public double[] probabilidadAcumulada ( double[]poblacionF ){
        double [] probaQ = new double[20];
        
        for (int i = 0; i < probaQ.length; i++) {
            probaQ[i] = 0;
        }
        for (int i = 0; i < probaQ.length; i++) {
            probaQ[i] =+ poblacionF[i];
        }
        return probaQ;
    }

    //-----------------------------------------------
    //Cruza en un punto
     public int[][] cruzaUno ( int[][]nuevaPoblacion, int[][]poblacion, double probabilidadCruza ){
         int[][]resultadoCruza = new int[20][];
         resultadoCruza = poblacion;
         for (int i = 0; i < 20; i++) {
             double randomDbl = Math.random();
             if ( randomDbl < probabilidadCruza) {
                 int puntoCruza = (int) (Math.random()*42);
                 int[]cruza = new int[42];
                 cruza =  poblacion[i];
                 for (int j = 0; j < puntoCruza; j++) {
                     cruza[j]=nuevaPoblacion[i][j];
                 }
                 resultadoCruza[i]=cruza;
             }
         }
         return resultadoCruza;
     
     }
    //-------------------------------------------------
     //Mutacion
    public int[][] mutacion ( int[][]nuevaPoblacion ){
        double probabilidad = 0.01;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 42; j++) {
                double randomDbl = Math.random();
                if (randomDbl<probabilidad){
                    if (nuevaPoblacion[i][j] == 0) {
                        nuevaPoblacion[i][j] = 1;
                    }else {
                        nuevaPoblacion[i][j] = 0;
                    }
                }
            }
            double randomDbl = Math.random();
            if (randomDbl<probabilidad) {
                for (int j = 0; j < 42; j++) {
                    if (nuevaPoblacion[i][j] == 0) {
                        nuevaPoblacion[i][j] = 1;
                    }else {
                        nuevaPoblacion[i][j] = 0;
                    }
                    
                }
            }
        }
        return nuevaPoblacion;
    }
    
    public int[] extraerMejor( int[][]poblacion, float x, float y, float limIn, float limSup ){
        int[] cromosomaA = new int[21];
        int[] cromosomaB = new int[21];
        double f =funcionAptitud(x, y);
        double k =funcionAptitud(x, y);
        int mejor = 0;
        for (int i = 0; i < 20; i++) {
            cromosomaA = dividirCromosoma(poblacion[i], "x");
            cromosomaB = dividirCromosoma(poblacion[i], "y");
            x = normalizar(limIn, limSup,(binarioToDecimal(arregloToString(cromosomaA))));
            y = normalizar(limIn, limSup,(binarioToDecimal(arregloToString(cromosomaB))));
            k = funcionAptitud(x, y);
            if (k < f) {
                mejor = i;
            }
        }
        return poblacion[mejor];
    }
    
    public int[][] cruzaDosPuntos( int[][]nuevaPoblacion, int[][]poblacion, double probabilidadCruza ){
        int[][]resultadoCruza = new int[20][];
         resultadoCruza = poblacion;
         for (int i = 0; i < 20; i++) {
             double randomDbl = Math.random();
             
             if ( randomDbl < probabilidadCruza) {
                 int puntoCruza = (int) (Math.random()*42);
                 int puntoCruzaDos = (int) (Math.random()*42);
                 
                 if (puntoCruza > puntoCruzaDos) {
                     int aux = puntoCruza;
                     puntoCruza = puntoCruzaDos;
                     puntoCruzaDos = aux;
                 }
                 
                 int[]cruza = new int[42];
                 cruza =  poblacion[i];
                 
                 for (int j = 0; j < puntoCruza; j++) {
                     cruza[j]=nuevaPoblacion[i][j];
                 }
                 for (int j = puntoCruzaDos; j < cruza.length; j++) {
                     cruza[j] = nuevaPoblacion[i][j];
                 }
                 resultadoCruza[i]=cruza;
             }
         }
         return resultadoCruza;
    }

    public int[][] seleccionTorneo(double[] poblacionEvaluada, int[][] poblacion) {
        float p = (float) 0.8;
        int tamPoblacion = 20;
        int elegidosTotal[] = new int[tamPoblacion];
        int elegidosA[] = new int[tamPoblacion / 2];
        int elegidosB[] = new int[tamPoblacion / 2];
        ArrayList<Integer> sorteo = new ArrayList<>();
        int minimo;
        int maximo;
        for (int i = 0; i < 20; ++i) {
             sorteo.add(i);
         }
         Collections.shuffle(sorteo);
        
         for (int i = 0; i < tamPoblacion/2; i++) {
             int j = sorteo.get(2*i);
             int k = sorteo.get(2*i+1);
             
             if ( poblacionEvaluada[j] < poblacionEvaluada[k] ){
                 minimo = j;
                 maximo = k;
             }else {
                 minimo = k;
                 maximo = j;
             }
             if ( Math.random() < p ) {
                 elegidosA[i] = minimo;
             } else {
                 elegidosA[i] = maximo;
             }
         }
         Collections.shuffle(sorteo);
         
         for (int i = 0; i < tamPoblacion/2; i++) {
             int j = sorteo.get(2*i);
             int k = sorteo.get(2*i+1);
             
             if ( poblacionEvaluada[j] < poblacionEvaluada[k] ){
                 minimo = j;
                 maximo = k;
             }else {
                 minimo = k;
                 maximo = j;
             }
             if ( Math.random() < p ) {
                 elegidosB[i] = minimo;
             } else {
                 elegidosB[i] = maximo;
             }
         }
         
         //juntar elejidos
         for (int i = 0; i < elegidosTotal.length; i++) {
             if ( i < elegidosTotal.length/2) {
                 elegidosTotal[i] = elegidosA[i];
             }else{
                 elegidosTotal[i] = elegidosB[i-10];
             }
         }
         
         int[][] nuevaPoblacion = new int[20][];
        for (int i = 0; i < 20; i++) {
            nuevaPoblacion[i] = poblacion[elegidosTotal[i]];
        }
       return nuevaPoblacion;
         
    }
    

}
