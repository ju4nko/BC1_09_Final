package terrenouniforme;

import static java.lang.Math.abs;
import java.util.ArrayList;

/**
* @authores Juan Jose Vígara Arcos,
*           Guillermo Bautista Ruiz,
*           Raquel Ramos López.
*           
 */
public class Estado {
    private int tractorx; //posicion x del tractor
    private int tractory; //posicion y del tractor
    private int k; // cantidad deseable en cada casilla del terreno
    private int max; // cantidad máxima que puede contener una casilla del terreno
    private int terreno[][]; //casillas del terreno

    //constructor que inicializa todos los atributos
    public Estado(int tractorx, int tractory, int k, int max, int terreno[][]) {
        this.tractorx = tractorx;
        this.tractory = tractory;
        this.k = k;
        this.max = max;   
        this.terreno=new int[terreno.length][terreno[0].length];
        for (int i=0;i<terreno.length;i++) {
            for(int j=0;j<terreno[i].length;j++)
                this.terreno[i][j]=terreno[i][j];
        }         
    }

    //metodos get/set
    
    public int getTractorx() {
        return tractorx;
    }

    public int getTractory() {
        return tractory;
    }

    public int getK() {
        return k;
    }

    public int getMax() {
        return max;
    }
    
    // metodo para calcular la cantidad máxima de arena que se puede repartir desde la casilla del tractor
    public int getS() {
        return (Math.max(terreno[getTractorx()][getTractory()]-getK(),0));
    }
    
    public int getFilas() {
        return terreno.length;
    }
    
    public int getColumnas() {
        return terreno[0].length;
    }
    
    public int getCantidad(int x, int y) {
        return terreno[x][y];
    }
    
    // Cantidad máxima de arena que puede recibir una celda de la matriz
    public int maxArena(int x,int y) {
          return ((terreno[x][y]<getMax()) ? getMax()-terreno[x][y] : 0);
    }
    
    // para comprobrar si una celda esta dentro del terreno
    public boolean estaDentro(int x,int y) {
        return (x>=0 && x<terreno.length && y>=0 && y<terreno[0].length);
    }
    
    // celdas adyacentes a la posicion del tractor en el terreno
    public Casilla [] celdasAdyacentes() {
        ArrayList<Casilla> celdas = new ArrayList <Casilla>();
        Casilla casilla;
        for (int i=-1;i<=1;i++) {
            for (int j=-1;j<=1;j++) {
                casilla=new Casilla(getTractorx()+i,getTractory()+j);                
                if (estaDentro(casilla.getX(),casilla.getY()) && (abs(i)+abs(j))==1) {                   
                    celdas.add(casilla);
                }
            }
        }
        return celdas.toArray(new Casilla[celdas.size()]);
    }
    
     // metodo que devulve todas las posibles combinaciones de desplazarse el tractor a una de las celdas posibles adyacentes
    public void distribuir(int etapa,int s,Casilla adyacentes [], int sol[],ArrayList soluciones) {
        int i;
        if (etapa==sol.length) {
            if (esSolucion(sol,adyacentes,s)) {
                Casilla aux[]=new Casilla[sol.length];                
                for (i=0;i<sol.length;i++)
                    aux[i]=new Casilla(adyacentes[i].getX(),adyacentes[i].getY(),sol[i]);
                soluciones.add(aux);        
            }
        }
        else {
            for (i=0;i<=s;i++) {
                if (i<=maxArena(adyacentes[etapa].getX(),adyacentes[etapa].getY()) && esPosible(i,etapa,sol,s)) {
                    sol[etapa]=i;
                    distribuir (etapa+1,s,adyacentes,sol,soluciones);
                }                    
            }  
        }
    }
    
    // metodo para comprobar si se puede distribuir la arena
    public boolean esPosible(int i,int etapa,int sol[],int s) {
        int total=0;
        for (int k=0;k<etapa;k++) 
            total+=sol[k];
        
        return ((total+i)<=s);
    }
    
    // metodo para averiguar si hemos hallado una solucion de distribucion válida
    public boolean esSolucion(int sol[], Casilla adyacentes[], int s) {
        int acumulado=0,total=0;        
        for (int k=0;k<sol.length;k++) {
            acumulado+=sol[k];
            total+=maxArena(adyacentes[k].getX(),adyacentes[k].getY());
        }       
        return (acumulado==Integer.min(total, s));
    }
    
    // metodo para generar todas las posibles acciones a partir de la posicion del tractor
    public ArrayList<Accion> getAcciones() {
        ArrayList<Accion> acciones=new ArrayList<Accion>();   
        Casilla [] adyacentes = celdasAdyacentes();       
        int sol[]=new int[adyacentes.length];
        ArrayList soluciones = new ArrayList();       
        distribuir(0,getS(),adyacentes,sol,soluciones);           
        
        for(int k=0;k<adyacentes.length;k++) {
            for (int i=0; i<soluciones.size();i++)             
                acciones.add(new Accion(adyacentes[k].getX(),adyacentes[k].getY(),(Casilla[])soluciones.get(i)));
            
        }        
        return acciones;
    }
    
    // metodo para mostrar todas las acciones
    public void mostrarAcciones(ArrayList<Accion> acciones){
        for (int i=0;i<acciones.size();i++) {            
            System.out.println(acciones.get(i));
        }    
    }
    
    // método que devuelve el estado siguiente generado a partir de una accion dada
    public Estado getEstado(Accion accion)  {   
        int [][] nuevoterreno=new int [terreno.length][terreno[0].length];
        for (int i=0;i<terreno.length;i++) {
            for(int j=0;j<terreno[i].length;j++)
                nuevoterreno[i][j]=terreno[i][j];
        }     
        nuevoterreno[getTractorx()][getTractory()]-=accion.cantidadRepartida();        
        Casilla [] adyacentes=accion.getAdyacentes();         
        for (int i=0;i<adyacentes.length;i++)             
            nuevoterreno[adyacentes[i].getX()][adyacentes[i].getY()]+=adyacentes[i].getRecibido();             
      
        return (new Estado(accion.getNuevaposicionx(),accion.getNuevaposiciony(),getK(),getMax(),nuevoterreno));
    }
    
    // método que devuelve el coste asociado a una accion recibida como parametro,
    // en este caso sera la cantidad de arena repartida entre las adyacentes mas uno
    public int getCosto (Accion accion) {        
        return accion.cantidadRepartida()+1;
    }
    
    // método que comprueba si el estado es el estado objetivo en el que todas las casillas del terreno
    // tienen la cantidad deseada k
    public boolean esObjetivo(){
        boolean objetivo=true;
        for (int i=0;i<terreno.length && objetivo;i++) {
            for (int j=0;j<terreno[0].length && objetivo;j++) {
                if (terreno[i][j]!=getK()) 
                    objetivo=false;
            }
        }    
        return objetivo;
    }
    
    // método que devuelve la heuristica asociada al estado
    // en este caso es la cantidad de casillas que no tienen la cantidad deseada k
    public int getHeuristica(){
        int cuenta=0;
        for (int i=0;i<terreno.length;i++) {
            for (int j=0;j<terreno[0].length;j++) {
                if (terreno[i][j]!=getK()) 
                    cuenta++;
            }
        }    
        return cuenta;
    }
    
    @Override
    public String toString() {
        String mostrar=getTractorx()+ " " + getTractory() + " " + getK() + " " + getMax() + " " + terreno.length + 
                " " + terreno[0].length + "\n";
        for (int i=0;i<terreno.length;i++) {
            for (int j=0;j<terreno[0].length;j++) {
                mostrar+=terreno[i][j] + " ";
            }
            mostrar+="\n";
        }    
        return mostrar;
    }   
}
