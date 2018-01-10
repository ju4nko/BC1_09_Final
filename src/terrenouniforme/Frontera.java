package terrenouniforme;

import java.util.PriorityQueue;

/**
* @authores Juan Jose Vígara Arcos,
*           Guillermo Bautista Ruiz,
*           Raquel Ramos López.
*           
 */
public class Frontera {
    PriorityQueue<NodoArbol> cola;
    //private int contador_nodos;

    public Frontera() { // constructor de la Frontera
        cola = new PriorityQueue<NodoArbol>();
        //contador_nodos = 0;
    }    
    
//    public int getContador(){
//        return contador_nodos;
//    }
    // método para inicializar la cola de la frontera
    public void CrearFrontera(){
        cola.clear();
    }
    // método que inserta un nuevo nodo en la cola de la frontera
    public void Inserta (NodoArbol nodoarbol){
        cola.add(nodoarbol);
        //contador_nodos++;
        //System.out.println("Insertado el nodo: "+contador_nodos);
    }
    // método que quita el nodo de la cabeza de la cola
    public NodoArbol Elimina() {
        return (NodoArbol) cola.poll();
    }
    // método booleano que devuelve true si la cola esta vacía
    public boolean EsVacia() {
        return cola.isEmpty();
    }
}
