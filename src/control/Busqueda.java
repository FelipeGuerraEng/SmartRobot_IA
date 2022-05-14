package control;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.*;
import modelo.Arbol;
import modelo.Node;
import modelo.Par;

public class Busqueda {

	private static int[][] tablero;
	private static int[][] aux;
	private static Arbol raiz;
	private static Par posMeta1;
	private static Par posMeta2;
	private static int cantMetas;
	private static int rowMeta1;
	private static int colMeta1;
	private static int rowMeta2;
	private static int colMeta2;
	int[] dirI = { -1, 1, 0, 0 };
	int[] dirJ = { 0, 0, -1, 1 };
	private Par inicio;
	private Par[] naves;
	private Par[] items;

	public Busqueda() {
		naves = new Par[2];
		items = new Par[2];
	}

	public void init() {

		tablero = new int[10][10];

		try {

			tablero = Input.input();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		int item = 0;
		System.out.println();
		for (int i = 0; i < 10; ++i) {
			for (int j = 0; j < 10; ++j) {
				System.out.print(tablero[i][j] + "  ");
				if (tablero[i][j] == 2) {

					inicio = new Par(i, j);

				} else if (tablero[i][j] == 3) {

					naves[0] = new Par(i, j);

				} else if (tablero[i][j] == 4) {

					naves[1] = new Par(i, j);

				} else if (tablero[i][j] == 5) {

					items[item] = new Par(i, j);
					item++;
				}
			}
			System.out.println();
		}

	}

	public Node BFS() {
		Queue<Node> pq = new LinkedList<>();
		pq.add(new Node(inicio, null, 0, new boolean[2], new boolean[2], 0, 0));
		while (!pq.isEmpty()) {
			Node n = pq.poll();
			if (!n.hasCycles()) {
				estaEnNave(n);
				estaEnItem(n);
				if (n.hasFinished())
					return n;
				for (int i = 0; i < 4; ++i) {
					Par pos = n.pos;
					if (isPossible(pos.getI() + dirI[i], pos.getJ() + dirJ[i])) {
						int newCost = calcularCosto(pos.getI() + dirI[i], pos.getJ() + dirJ[i], n.nave) + n.cost;
						int gas = 0;
						if (n.nave != 0) {
							gas = n.nave - 1;
						}
						Node son = new Node(new Par(pos.getI() + dirI[i], pos.getJ() + dirJ[i]), n, gas,
								n.items.clone(), n.naves.clone(), newCost, 0);
						pq.add(son);
					}

				}
			}
		}

		return null;
	}

	public Node DFS() {
		Stack<Node> pq = new Stack<>();
		pq.add(new Node(inicio, null, 0, new boolean[2], new boolean[2], 0, 0));
		while (!pq.isEmpty()) {
			Node n = pq.pop();
			if (!n.hasCycles()) {
				estaEnNave(n);
				estaEnItem(n);
				if (n.hasFinished())
					return n;
				for (int i = 3; i > -1; --i) {
					Par pos = n.pos;
					if (isPossible(pos.getI() + dirI[i], pos.getJ() + dirJ[i])) {
						int newCost = calcularCosto(pos.getI() + dirI[i], pos.getJ() + dirJ[i], n.nave) + n.cost;
						int gas = 0;
						if (n.nave != 0) {
							gas = n.nave - 1;
						}
						Node son = new Node(new Par(pos.getI() + dirI[i], pos.getJ() + dirJ[i]), n, gas,
								n.items.clone(), n.naves.clone(), newCost, 0);
						pq.add(son);
					}

				}
			}
		}

		return null;
	}

	public Node BCU() {
		PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.cost - b.cost);
		pq.add(new Node(inicio, null, 0, new boolean[2], new boolean[2], 0, 0));
		while (!pq.isEmpty()) {
			Node n = pq.poll();
			if (!n.hasCycles()) {
				estaEnNave(n);
				estaEnItem(n);
				if (n.hasFinished())
					return n;
				for (int i = 0; i < 4; ++i) {
					Par pos = n.pos;
					if (isPossible(pos.getI() + dirI[i], pos.getJ() + dirJ[i])) {
						int newCost = calcularCosto(pos.getI() + dirI[i], pos.getJ() + dirJ[i], n.nave) + n.cost;
						int gas = 0;
						if (n.nave != 0) {
							gas = n.nave - 1;
						}
						Node son = new Node(new Par(pos.getI() + dirI[i], pos.getJ() + dirJ[i]), n, gas,
								n.items.clone(), n.naves.clone(), newCost, 0);
						pq.add(son);
					}

				}
			}
		}

		return null;
	}

	public int calcularCosto(int i, int j, int gas) {
		if (gas > 0) {
			return 1;
		} else {
			if (tablero[i][j] == 6) {
				return 4;
			} else {
				return 1;
			}

		}
	}

	public void estaEnNave(Node n) {
		Par pos = n.pos;
		for (int i = 0; i < 2; ++i) {
			if (pos.getI() == naves[i].getI() && pos.getJ() == naves[i].getJ() && !n.naves[i]) {
				n.naves[i] = true;
				if (i == 0) {
					n.nave = 10;
				} else {
					n.nave = 20;
				}
			}
		}
	}

	public void estaEnItem(Node n) {
		Par pos = n.pos;
		for (int i = 0; i < 2; ++i) {
			if (pos.getI() == items[i].getI() && pos.getJ() == items[i].getJ() && !n.items[i]) {
				n.items[i] = true;

			}
		}
	}

	public boolean isPossible(int i, int j) {
		return !(i < 0 || j < 0 || i >= 10 || j >= 10 || tablero[i][j] == 1);
	}
}
