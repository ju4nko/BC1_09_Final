package Ficheros;

import terrenouniforme.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
/**
* @authores Juan Jose Vígara Arcos,
*           Guillermo Bautista Ruiz,
*           Raquel Ramos López.
*           
 */
public class LeerEscribir {

    // metodo para leer un terreno desde el teclado
    public static Estado LecturaTeclado() {
        int valor=0, tractorx,tractory,k,max,filas,columnas,contador=0;
        Scanner sc = new Scanner(System.in);
        Random rn = new Random();
        System.out.println("Introduce la posicion x del tractor");
        tractorx = sc.nextInt();
        System.out.println("Introduce la posicion y del tractor");
        tractory = sc.nextInt();        
        System.out.println("Introduce el numero deseable K que debe contener cada casilla");
        k = sc.nextInt();
        System.out.println("Introduce el numero maximo que puede contener una casilla");
        max = sc.nextInt();
        System.out.println("Introduce la dimension en filas del tablero");
        filas = sc.nextInt();
        System.out.println("Introduce la dimension en columnas del tablero");
        columnas = sc.nextInt();       
        int terreno[][] = new int[filas][columnas];
        do {
            for (int i = 0; i < filas; i++) {            
                for (int j = 0; j < columnas; j++) {
                    System.out.println("Introduce la cantidad para fila: " + i + " columna: " + j);
                    valor=sc.nextInt();
                    terreno[i][j]=valor; 
                    contador+=valor;
                }
            }
            if (k*filas*columnas!=contador)
                System.out.println("V distinto de k*filas*columnas. Vuelva a introducir terreno.");
        } while(k*filas*columnas!=contador);     
        return new Estado(tractorx,tractory, k, max, terreno);
    }

    // metodo para generar un terreno aleatoriamente
    public static Estado Random() {
        int aleatorio=0, tractorx,tractory,k,max,filas,columnas,contador=0;
        Scanner sc = new Scanner(System.in);
        Random rn = new Random();
        System.out.println("Introduce la posicion x del tractor");
        tractorx = sc.nextInt();
        System.out.println("Introduce la posicion y del tractor");
        tractory = sc.nextInt();        
        System.out.println("Introduce el numero deseable K que debe contener cada casilla");
        k = sc.nextInt();
        System.out.println("Introduce el numero maximo que puede contener una casilla");
        max = sc.nextInt();
        System.out.println("Introduce la dimension en filas del tablero");
        filas = sc.nextInt();
        System.out.println("Introduce la dimension en columnas del tablero");
        columnas = sc.nextInt();       
        int terreno[][] = new int[filas][columnas];
        do {
            for (int i = 0; i < filas; i++) {            
                for (int j = 0; j < columnas; j++) {
                    aleatorio=rn.nextInt(max);
                    terreno[i][j] =aleatorio ; 
                    contador+=aleatorio;
                }
            }
            if (k*filas*columnas!=contador)
                System.out.println("V distinto de k*filas*columnas. Terreno NO VALIDO. Continuamos generando");
        } while(k*filas*columnas!=contador);     
        return new Estado(tractorx,tractory, k, max, terreno);
    }
    
    // metodo para leer un terreno desde un fichero
    public static Estado leerTerreno(String cadena) throws IOException {
        String linea;
        BufferedReader br = new BufferedReader(new FileReader(cadena));
        linea = br.readLine();
        StringTokenizer st = new StringTokenizer(linea);
        int tractorx = Integer.parseInt(st.nextToken());
        int tractory = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int max = Integer.parseInt(st.nextToken());
        int filas = Integer.parseInt(st.nextToken());
        int columnas = Integer.parseInt(st.nextToken());        
        int terreno[][] = new int[filas][columnas];
        int indice = 0;
        while ((linea = br.readLine()) != null) {
            st = new StringTokenizer(linea);
            for (int j = 0; j < columnas; j++) 
                terreno[indice][j] = Integer.parseInt(st.nextToken());   
            indice++;            
        }
        br.close();        
        return new Estado(tractorx,tractory, k, max, terreno);
    }

    // metodo para escribir en un fichero el resultado de la busqueda
    public static void escribirTerreno (Deque<NodoArbol> camino, String estrategia) throws IOException{
        File archivo = new File("solucionTerreno.txt");        

        FileWriter file = new FileWriter(archivo);
        PrintWriter pw = new PrintWriter(file); 
        pw.println(estrategia.toUpperCase());
        pw.println("TOTAL PROFUNDIDAD: " + (camino.getLast().getProfundidad()+1) + " TOTAL COSTE: " + camino.getLast().getCoste());
        int i=0;
        for (NodoArbol nodoarbol: camino) {            
            if (i==0) pw.println("TERRENO INICIO");
            else
                pw.println("Accion: " + nodoarbol.getAccion());
            pw.println("Estado: " + nodoarbol.getEstado().getTractorx() + " " + nodoarbol.getEstado().getTractory() + " " + 
                    nodoarbol.getEstado().getK() + " " + nodoarbol.getEstado().getMax() + " " + nodoarbol.getEstado().getFilas()
                    + " " + nodoarbol.getEstado().getColumnas());
            
            for (int j=0;j<nodoarbol.getEstado().getFilas();j++) {
                for (int k=0;k<nodoarbol.getEstado().getColumnas();k++) {
                    pw.print(nodoarbol.getEstado().getCantidad(j, k)+ " ");
                }
                pw.println(""); 
            }    
            pw.println("F: " + nodoarbol.getValor() + " Profundidad: " + nodoarbol.getProfundidad()
                       + " Cost: " + nodoarbol.getCoste());
            pw.println("");  
            i++;
        }        
        pw.close();        
    }
}
