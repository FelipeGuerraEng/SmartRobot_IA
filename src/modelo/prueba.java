package modelo;

import control.Busqueda;

public class prueba {
	public static void main(String[]args) {
		Busqueda b = new Busqueda();
		b.init();
		Node n = b.DFS();
		while(n!=null) {
			System.out.println(n);
			n= n.parent;
		}
		
	}
}
