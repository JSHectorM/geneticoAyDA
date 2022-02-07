/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoayda;

import java.util.Arrays;

/**
 *
 * @author migue
 */
public class ProyectoAyDA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        float limIn = -100;
        float limSup = 100;
        String inicial = "000101111100001111011000010000001001010101";
        String[] aux = inicial.split("");
        int[] cromosoma = new int[42];
        int[] cromosomaA = new int[21];
        int[] cromosomaB = new int[21];
        int[][] poblacion = new int[20][];
        double[] poblacionEvaluada = new double[20];
        
         float xSave = 0;
         float ySave = 0;
        
        int[] mejorCromosoma = new int[42];
        double mejorCosto = 0;
        
        for (int i = 0; i < aux.length; i++) {
            cromosoma[i] = Integer.valueOf(aux[i]);
        }

        CCalculoAyDA oCalculo = new CCalculoAyDA();

        for (int i = 0; i < 20; i++) {
                poblacion[i] = oCalculo.generarCromosoma();
        }
        

        for (int z = 0; z < 1000 /*1000*/; z++) {
            cromosomaA = oCalculo.dividirCromosoma(cromosoma, "x");
            cromosomaB = oCalculo.dividirCromosoma(cromosoma, "y");

            float x = oCalculo.normalizar(limIn, limSup, (oCalculo.binarioToDecimal(oCalculo.arregloToString(cromosomaA))));
            float y = oCalculo.normalizar(limIn, limSup, (oCalculo.binarioToDecimal(oCalculo.arregloToString(cromosomaB))));

            double f = oCalculo.funcionAptitud(x, y);
            
            if (mejorCosto > f) {
                mejorCosto = f;
                mejorCromosoma = cromosoma;
                xSave = x;
                ySave = y;
            }
            
            for (int i = 0; i < 20; i++) {
                System.out.print("v" + i + "= ");
                for (int j = 0; j < 42; j++) {
                    System.out.print(poblacion[i][j]);
                }
                System.out.println("");
            }
            System.out.println("Eval---------------------------------------");
            for (int i = 0; i < 20; i++) {
                cromosomaA = oCalculo.dividirCromosoma(poblacion[i], "x");
                cromosomaB = oCalculo.dividirCromosoma(poblacion[i], "y");
                x = oCalculo.normalizar(limIn, limSup, (oCalculo.binarioToDecimal(oCalculo.arregloToString(cromosomaA))));
                y = oCalculo.normalizar(limIn, limSup, (oCalculo.binarioToDecimal(oCalculo.arregloToString(cromosomaB))));
                f = oCalculo.funcionAptitud(x, y);
                poblacionEvaluada[i] = f;
                System.out.println("eval V" + i + " " + x + "," + y + "\t=" + f);

            }
            int[][] nuevaPoblacion;
//__________________________________________________________________________________________________________________________________________________________________
            //nuevaPoblacion = oCalculo.seleccionRuleta(poblacionEvaluada, poblacion);
            nuevaPoblacion = oCalculo.seleccionTorneo(poblacionEvaluada, poblacion);
//__________________________________________________________________________________________________________________________________________________________________
            System.out.println("-----------------Ruleta");
            for (int i = 0; i < 20; i++) {
                System.out.print("v" + i + " =\t");
                for (int j = 0; j < 42; j++) {
                    System.out.print(nuevaPoblacion[i][j]);
                }
                System.out.println("");
            }
//__________________________________________________________________________________________________________________________________________________________________
            //nuevaPoblacion = oCalculo.cruzaUno(nuevaPoblacion, poblacion, 0.25);
            nuevaPoblacion = oCalculo.cruzaDosPuntos(nuevaPoblacion, poblacion, 0.25);
//__________________________________________________________________________________________________________________________________________________________________

            System.out.println("-----------------Cruza");
            for (int i = 0; i < 20; i++) {
                System.out.print("v" + i + " =\t");
                for (int j = 0; j < 42; j++) {
                    System.out.print(nuevaPoblacion[i][j]);
                }
                System.out.println("");
            }

            nuevaPoblacion = oCalculo.mutacion(nuevaPoblacion);

            System.out.println("-----------------Muta");
            for (int i = 0; i < 20; i++) {
                System.out.print("v" + i + " =\t");
                for (int j = 0; j < 42; j++) {
                    System.out.print(nuevaPoblacion[i][j]);
                }
                System.out.println("");
            }

            //System.out.println("Despues de 1000-------------------");
            poblacion = nuevaPoblacion;
            
            cromosoma = oCalculo.extraerMejor(poblacion, x, y, limIn, limSup);
            cromosomaA = oCalculo.dividirCromosoma(cromosoma, "x");
            cromosomaB = oCalculo.dividirCromosoma(cromosoma, "y");

            x = oCalculo.normalizar(limIn, limSup, (oCalculo.binarioToDecimal(oCalculo.arregloToString(cromosomaA))));
            y = oCalculo.normalizar(limIn, limSup, (oCalculo.binarioToDecimal(oCalculo.arregloToString(cromosomaB))));

            f = oCalculo.funcionAptitud(x, y);
            
            if (mejorCosto > f) {
                mejorCosto = f;
                mejorCromosoma = cromosoma;
                xSave = x;
                ySave = y;
            }

            System.out.println("Mejor costo: "+mejorCosto);
            System.out.println("Para el vector x: "+ xSave + " y: " + ySave);
        }

    }

}
