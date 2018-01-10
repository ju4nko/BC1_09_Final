package terrenouniforme;
/**
* @authores Juan Jose Vígara Arcos,
*           Guillermo Bautista Ruiz,
*           Raquel Ramos López.
*           
 */
public class NodoArbol implements Comparable<NodoArbol> {
    private NodoArbol padre; // enlaza al nodo origen para construir el camino
    private Estado estado; // estado asociado al nodo del arbol
    private Accion accion; // accion asociada al nodo del arbol
    private double coste; // coste acumulado de la secuencia de acciones
    private int profundidad; // profundiad en el arbol generado
    private double valor; //valor f para el caso de A asterisco

    //constructor para todos los atributos de la clase
    public NodoArbol(NodoArbol padre, Estado estado, Accion accion, double coste, int profundidad, double valor) {
        this.padre = padre;
        this.estado = estado;
        this.accion = accion;
        this.coste = coste;
        this.profundidad = profundidad;
        this.valor = valor;
    }
    
    // metodos get/set

    public NodoArbol getPadre() {
        return padre;
    }

    public Estado getEstado() {
        return estado;
    }

    public Accion getAccion() {
        return accion;
    }

    public double getCoste() {
        return coste;
    }

    public int getProfundidad() {
        return profundidad;
    }

    public double getValor() {
        return valor;
    }

    public void setPadre(NodoArbol padre) {
        this.padre = padre;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setAccion(Accion accion) {
        this.accion = accion;
    }

    public void setCoste(double coste) {
        this.coste = coste;
    }

    public void setProfundidad(int profundidad) {
        this.profundidad = profundidad;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "NodoArbol{" + "padre=" + padre + ", estado=" + estado + ", accion=" + accion + ", coste=" + coste + ", profundidad=" + profundidad + ", valor=" + valor + '}';
    }
    
    //método que al insertar un nodo en una priorityqueu los ordena de menor a mayor valor
    public int compareTo(NodoArbol narbol) {
        int r = 0;
        if (narbol.getValor()< getValor())
            r = +1;
        else
        if (narbol.getValor() > getValor())
            r = -1;
        return r;
    }
}
