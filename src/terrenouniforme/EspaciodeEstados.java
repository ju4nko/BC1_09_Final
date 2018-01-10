package terrenouniforme;

import java.util.ArrayList;
/**
* @authores Juan Jose Vígara Arcos,
*           Guillermo Bautista Ruiz,
*           Raquel Ramos López.
*           
 */
public class EspaciodeEstados {

    public EspaciodeEstados() {
    }  
     // metodo que devuelve una lista de sucesores correspondientes a un estado
    public ArrayList<Sucesor> sucesor (Estado estado) {
        ArrayList<Sucesor> sucesores=new ArrayList<Sucesor>(); // ArrayList para almacenar los sucesores
        // ArrayList que se carga con todas las acciones a partir de un estado dado como parametro
        ArrayList<Accion> acciones=new ArrayList<Accion>(estado.getAcciones());   
        // se crean los sucesores a partir de una accion
        for(Accion accion: acciones)                
            sucesores.add(new Sucesor(accion,estado.getEstado(accion),estado.getCosto(accion)));        
        return sucesores;       
    } 
}
