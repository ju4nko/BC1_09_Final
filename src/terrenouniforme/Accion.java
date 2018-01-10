package terrenouniforme;
/**
* @authores Juan Jose Vígara Arcos,
*           Guillermo Bautista Ruiz,
*           Raquel Ramos López.
*           
 */
public class Accion {
    private int nuevaposicionx; //posicion x del tractor en el terreno
    private int nuevaposiciony; // posicion y del tractor en el terreno
    private Casilla [] adyacentes;  // celdas adyacentes en la accion   

    public Accion(int nuevaposicionx, int nuevaposiciony, Casilla[] adyacentes) {
        this.nuevaposicionx = nuevaposicionx;
        this.nuevaposiciony = nuevaposiciony;
        this.adyacentes = new Casilla [adyacentes.length];          
        System.arraycopy(adyacentes, 0, this.adyacentes, 0, adyacentes.length);
    }

    public int getNuevaposicionx() {
        return nuevaposicionx;
    }

    public int getNuevaposiciony() {
        return nuevaposiciony;
    }

    public Casilla[] getAdyacentes() {
        return adyacentes;
    }    
    
    //metodo para calcular la cantidad total repartida en la accion entre las casillas adyacentes
    public int cantidadRepartida() {
        int total=0;
        for (int i=0;i<adyacentes.length;i++){
            total+=adyacentes[i].getRecibido();
        } 
        return total;
    }
    
    @Override
    public String toString() {
        String cadena="(("+getNuevaposicionx()+","+getNuevaposiciony()+"), [";
        for (int i=0;i<adyacentes.length;i++)                 
            cadena+="("+adyacentes[i].getRecibido()+",("+adyacentes[i].getX()+","+adyacentes[i].getY()+")),";               
                
        cadena+="],"+ (cantidadRepartida()+1)+")";
        
        return cadena;
    }    
}
