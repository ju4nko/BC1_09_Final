package terrenouniforme;
/**
* @authores Juan Jose Vígara Arcos,
*           Guillermo Bautista Ruiz,
*           Raquel Ramos López.
*           
 */
public class Sucesor {
    private Accion accion; // accion empleada para el sucesor
    private Estado estado; // estado que almacena el sucesor
    private double coste; // coste asociado a generar el estado a partir de la accion

    //construtor de todos los atributos
    public Sucesor(Accion accion, Estado estado, double coste) {
        this.accion = accion;
        this.estado = estado;
        this.coste = coste;
    }

    // metodos get/set
    
    public Accion getAccion() {
        return accion;
    }

    public Estado getEstado() {
        return estado;
    }

    public double getCoste() {
        return coste;
    }

    public void setAccion(Accion accion) {
        this.accion = accion;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setCoste(double coste) {
        this.coste = coste;
    }

    @Override
    public String toString() {
        return "Sucesor{" + "accion=" + accion + ", estado=" + estado + ", coste=" + coste + '}';
    }   
}
