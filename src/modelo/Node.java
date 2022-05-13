package modelo;

import java.util.Arrays;

public class Node {
	public Par pos;
	public Node parent;
	public int nave;
	public boolean[]items;
	public boolean[] naves;
	public int cost;
	public int costH;
	
	public Node(Par pos, Node parent, int nave, boolean[] items, boolean[] naves, int cost, int costH) {
		this.pos = pos;
		this.parent = parent;
		this.nave = nave;
		this.items = items;
		this.naves = naves;
		this.cost = cost;
		this.costH = costH;
	}
	public boolean hasFinished() {
		return items[0] && items[1];
	}
	
	public boolean hasCycles() {
		Node aux = parent;
		while(aux != null) {
			if(isEqual(aux)) {
				return true;
			}
			aux = aux.parent;
		}
		return false;
	}
	private boolean isEqual(Node n) {
		if(pos.getI() == n.pos.getI() && pos.getJ() == n.pos.getJ()) {
			if(Arrays.equals(naves, n.naves)) {
				if(Arrays.equals(items, n.items)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public String toString() {
		return pos.getI() + " " + pos.getJ() +  ", Costo: " + cost +
				", Items: " + Arrays.toString(items) + ", Naves: " + Arrays.toString(naves)
				+ "Padre: " + (parent != null ? parent.pos.getI() + ", " + parent.pos.getJ() : "NONE");
	}
	
	
	
	
}
