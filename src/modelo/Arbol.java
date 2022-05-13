package modelo;

import java.util.ArrayList;
import java.util.List;

public class Arbol {
	
    private Par elemento;
    private List<Arbol> hijos;

    public Arbol(Par elemento) {
    
        this.elemento = elemento;
        this.hijos = new ArrayList<>();
    }

    public Par getElemento() {
    
        return this.elemento;
    }

    public List<Arbol> getHijos() {

    	return this.hijos;
    }

    public Arbol anadirHijos(Par elemento) {

    	Arbol nuevoHijo = new Arbol(elemento);

    	hijos.add(nuevoHijo);
    	return nuevoHijo;
    }


   /* public static Arbol populate(Arbol n, int height){
    	
    	if(height == 0){
    		
    		return n; 
    		
    	}else{
    		n = new Node();
    		for(int i = 0; i < n.nbChilds(); i++){
    			populate(n.getChildAt(i), height - 1);
    		}
    	}
    }*/

}
