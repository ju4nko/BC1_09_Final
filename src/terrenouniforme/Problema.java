package terrenouniforme;
/**
* @authores Juan Jose Vígara Arcos,
*           Guillermo Bautista Ruiz,
*           Raquel Ramos López.
*           
 */
public class Problema {
    private EspaciodeEstados espaciodeestados; // espacio de estados con todos los posibles estados en el problema
    private Estado inicio; // estado de inicio para el problema

    //constructor de los atributos
    public Problema(EspaciodeEstados espaciodeestados, Estado inicio) {
        this.espaciodeestados = espaciodeestados;
        this.inicio = inicio;
    }

    public EspaciodeEstados getEspaciodeestados() {
        return espaciodeestados;
    }

    public Estado getInicio() {
        return inicio;
    }
    
    // metodo booleano que devuelve true en caso de alcanzar el estado objetivo pasado como parametro
    public boolean EstadoObjetivo(Estado estado) {
        return estado.esObjetivo();
    }
    
    // método que devuelve la heuristica para un estado dado
    public int Heuristic (Estado estado) {
        return estado.getHeuristica();
    }
    
}
