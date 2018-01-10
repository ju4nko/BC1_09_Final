package terrenouniforme;

import Ficheros.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
/**
* @authores Juan Jose Vígara Arcos,
*           Guillermo Bautista Ruiz,
*           Raquel Ramos López.
*           
 */
public class TerrenoUniforme {

    public static void main(String[] args) throws IOException {
        String opcion;
        boolean valido,conPoda,solucion=false;
        Estado inicio=null;
        int Prof_Max=20, Inc_Prof=1;
        Scanner sc=new Scanner(System.in);
        
        // pedimos al usuario que elija una opcion de creación del terreno, por fichero, teclado o aleatoriamente
        do {
            System.out.print("1. Lectura Fichero\n2. Lectura Teclado\n3. Lectura Random\n Elige una opcion: ");
            opcion=sc.next();
            valido=opcion.equals("1") || opcion.equals("2") || opcion.equals("3");
            if (!valido)
                System.out.println("Opcion no válida. Vuelva a Intentarlo.\n");
        } while (!valido);    
        
        switch (opcion) {
            case "1":
                inicio=LeerEscribir.leerTerreno("terreno3.txt");  // necesario que la V=filas*columnas*k
                break;
            case "2":
                inicio=LeerEscribir.LecturaTeclado(); // necesario que la V=filas*columnas*k
                break;
            case "3":
                inicio=LeerEscribir.Random(); // necesario que la V=filas*columnas*k
                break;
        }
        
        System.out.println("ESTADO INICIO:\n" + inicio);        
        Problema prob = new Problema (new EspaciodeEstados(),inicio); //creamos un objeto problema 
        
        // pedimos al usuario si lo quiere con poda de estados repetidos o no
        do {
            System.out.print("1. Con Poda\n2. Sin Poda\n Elige una opcion: ");
            opcion=sc.next();
            valido=opcion.equals("1") || opcion.equals("2");
            if (!valido)
                System.out.println("Opcion no válida. Vuelva a Intentarlo.\n");
        } while (!valido);    
        
        conPoda=opcion.equals("1") ? true:false;        
        
        // pedimos al usuario la estrategia de búsqueda
        do {
            System.out.print("\n1. Anchura\n2. Profundidad simple\n3. Profundidad Acotada\n4. Profundidad Iterativa"
                    + "\n5. Coste Uniforme\n6. A Asterisco\n Elige una opcion: ");
            opcion=sc.next();
            valido=opcion.equals("1") || opcion.equals("2") || opcion.equals("3") || opcion.equals("4") || opcion.equals("5")
                    || opcion.equals("6");
            if (!valido)
                System.out.println("Opcion no válida. Vuelva a Intentarlo.\n");
        } while (!valido);        
        
        // Para cualquier opcion distinta de la profundidad simple elegimos la profundidad máxima acotada
        if (!opcion.equals("2")) {
            do {
                valido=true;
                System.out.print ("Introduce profundida máxima: ");
                try {
                    Prof_Max=Integer.parseInt(sc.next());
                } catch (Exception e) {
                    System.out.println("Valor no númerico");
                    valido=false;
                }
            } while(!valido);
        }
        
        // Si la opcion es la profuncidad iterativa pedimos al usuario el incremento por iteración
        if (opcion.equals("4")) {
            do {
                valido=true;
                System.out.print ("Introduce Incremento Profundidad Iterativa: ");
                try {
                    Inc_Prof=Integer.parseInt(sc.next());
                } catch (Exception e) {
                    System.out.println("Valor no númerico");
                    valido=false;
                }
            } while(!valido);
        }
        
        switch (opcion) {
            case "1":
                solucion=Busqueda_Acotada (prob, "Anchura", Prof_Max, conPoda); //anchura
                break;
            case "2":
                solucion=Busqueda_Acotada (prob, "Profundidad", 10000, conPoda); //Profundidad simple, utilizada prof infinita
                break;
            case "3":
                solucion=Busqueda_Acotada (prob, "Profundidad", Prof_Max, conPoda); // Profundidad acotada
                break;
            case "4":
                solucion=Busqueda (prob, "Profundidad", Prof_Max,Inc_Prof, conPoda); // Profundidad iterativa
                break;
            case "5":
                solucion=Busqueda_Acotada (prob, "CosteUniforme", Prof_Max, conPoda); // Coste uniforme
                break;
            case "6":
                solucion=Busqueda_Acotada (prob, "A", Prof_Max, conPoda); // A asterisco
                break;
        }
        
        if (!solucion)
            System.out.println("No ha sido posible encontrar solución en la profundidad dada");
        
    }
    
    // método para realizar la busqueda dependiendo de la estrategia elegida, la profundidad máxima y si es con poda o no
    public static boolean Busqueda_Acotada (Problema prob, String estrategia, int Prof_Max, boolean conPoda) throws IOException {        
        Frontera frontera = new Frontera();
        frontera.CrearFrontera();
        NodoArbol n_inicial= new NodoArbol(null,prob.getInicio(),null,0,0,0);
        frontera.Inserta(n_inicial);
        boolean solucion=false;
        NodoArbol n_actual=null;
        Map<String,Double> nodosVisitados=new HashMap<String,Double>(); // HashMap para la poda de estados repetidos
        Deque<NodoArbol> nodosSolucion = new LinkedList<NodoArbol>(); // cola doble para almacenar la solución generada
        
        while (!solucion && !frontera.EsVacia()) {
            n_actual=frontera.Elimina();            
            if (prob.EstadoObjetivo(n_actual.getEstado()))
                solucion=true;
            else {
                List<Sucesor> LS = prob.getEspaciodeestados().sucesor(n_actual.getEstado());               
                List<NodoArbol> LN= CreaListaNodosArbol(LS,n_actual,Prof_Max,estrategia);                
                for (NodoArbol nodo:LN) {    
                    if (conPoda) { // si se ha elegido poda no se insertan en la frontera los estados repetidos                   
                        String nodoString=Metodos.getMD5(nodo.getEstado().toString());
                        if (nodosVisitados.containsKey(nodoString)) {                         
                            if (nodo.getValor()<nodosVisitados.get(nodoString).intValue()){                           
                                 frontera.Inserta(nodo);
                                 nodosVisitados.replace(nodoString, nodo.getValor());//                                                          
                            }
                        }
                        else {                       
                            nodosVisitados.put(nodoString, nodo.getValor());
                            frontera.Inserta(nodo);
                        }                
                    }
                    else frontera.Inserta(nodo);
                }
            }            
        }
        if (solucion) {
            // si encontramos solucion la introducimos en la cola doble nodosSolucion
            while (n_actual.getPadre()!=null) {
                nodosSolucion.addFirst(n_actual);
                n_actual=n_actual.getPadre();
            }
            // se inserta el nodo inicial y se muestra por consola y tambien se genera el fichero en la carpeta de la solución
            nodosSolucion.addFirst(n_inicial);
            LeerEscribir.escribirTerreno(nodosSolucion, estrategia);           
            solucionenConsola (nodosSolucion, estrategia);
            System.out.println("\nFICHERO DISPONIBLE EN LA CARPETA DE LA SOLUCIÓN");  
        }        
        return solucion;
    }
    
    public static List<NodoArbol> CreaListaNodosArbol(List<Sucesor> LS, NodoArbol n_actual, int Prof_Max, String estrategia) {
        List<NodoArbol> LN = new ArrayList<NodoArbol>();
        if (n_actual.getProfundidad()<Prof_Max) { // si aún podemos seguir iterando por no alcanzar la profundidad máxima
            NodoArbol aux=null;            
            for (Sucesor sucesor:LS) {
               //dependiendo de la estrategia generamos los nodos
               switch (estrategia) {
                   case "Anchura":
                        aux= new NodoArbol(n_actual, sucesor.getEstado(), sucesor.getAccion(),n_actual.getCoste()+sucesor.getCoste(),
                          n_actual.getProfundidad()+1, n_actual.getProfundidad()+1); 
                        break;
                   case "Profundidad":
                        aux= new NodoArbol(n_actual, sucesor.getEstado(), sucesor.getAccion(),n_actual.getCoste()+sucesor.getCoste(),
                          n_actual.getProfundidad()+1, Prof_Max-(n_actual.getProfundidad()+1));
                        break;                
                   case "CosteUniforme":
                        aux= new NodoArbol(n_actual, sucesor.getEstado(), sucesor.getAccion(),n_actual.getCoste()+sucesor.getCoste(),
                          n_actual.getProfundidad()+1, n_actual.getCoste()+sucesor.getCoste());
                        break;
                   case "A":
                        aux= new NodoArbol(n_actual, sucesor.getEstado(), sucesor.getAccion(),n_actual.getCoste()+sucesor.getCoste(),
                          n_actual.getProfundidad()+1, n_actual.getCoste()+sucesor.getCoste()+sucesor.getEstado().getHeuristica());   
                        break;
               }
               LN.add(aux);
            }
        }
        return LN;
    }
    
    // metodo utilizado para la busqueda de profundidad iterativa, que recibe ademas el incremento de profundidad 
    public static boolean Busqueda (Problema prob, String estrategia, int Prof_Max, int Inc_Prof, boolean conPoda) throws IOException {        
        int Prof_Actual=Inc_Prof;
        boolean solucion = false;
        while (!solucion && Prof_Actual<=Prof_Max) {
            solucion=Busqueda_Acotada(prob,estrategia,Prof_Actual,conPoda);
            Prof_Actual+=Inc_Prof;
        }
        return solucion;        
    }  
    
    // método para mostrar la solución por consola
    public static void solucionenConsola (Deque<NodoArbol> camino, String estrategia) {        
        System.out.print("\n"+estrategia.toUpperCase() +"\nTOTAL PROFUNDIDAD: " + (camino.getLast().getProfundidad()+1) + 
                " TOTAL COSTE: " + camino.getLast().getCoste()+"\n");           
        int i=0;
        for (NodoArbol nodoarbol: camino) {            
            if (i==0) 
                System.out.println("TERRENO INICIO");
            else
                System.out.println(nodoarbol.getAccion());
            System.out.print(nodoarbol.getEstado());
            System.out.println("Profundidad: "+nodoarbol.getProfundidad()+ " Coste: " + nodoarbol.getCoste() 
                    + " Valor: " + nodoarbol.getValor()+"\n");
            i++;
        }
    }
}
