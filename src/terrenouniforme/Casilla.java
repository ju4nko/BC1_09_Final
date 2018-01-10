package terrenouniforme;
/**
* @authores Juan Jose Vígara Arcos,
*           Guillermo Bautista Ruiz,
*           Raquel Ramos López.
*           
 */
public class Casilla {
    private int x; //posicion x
    private int y; //posicion y
    private int recibido; //cantidad de arena recibida

    // constructor solo para las coordenadas de la casilla
    public Casilla(int x, int y) {
        this.x = x;
        this.y = y;
        this.recibido=0;
    }

    // constructor para las coordenadas de la casilla y la cantidad de arena reciba
    public Casilla(int x, int y, int recibido) {
        this.x = x;
        this.y = y;
        this.recibido = recibido;
    }

    //metodos get/set para los atributos
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRecibido() {
        return recibido;
    }

    public void setRecibido(int recibido) {
        this.recibido = recibido;
    }

    
    
    @Override
    public String toString() {
        return "Casilla{" + "x=" + x + ", y=" + y + '}';
    }
    
    
}
