package control;

import java.io.FileNotFoundException;
import java.util.*;
import modelo.Node;
import modelo.Par;

public class Busqueda {

	private static int[][] tablero;
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

	public Node Greedy() {
		PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.costH - b.costH);
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
						int newCostH = calcularCostoHeuristico(pos.getI(), pos.getJ(), n);
						int gas = 0;
						if (n.nave != 0) {
							gas = n.nave - 1;
						}
						Node son = new Node(new Par(pos.getI() + dirI[i], pos.getJ() + dirJ[i]), n, gas,
								n.items.clone(), n.naves.clone(), newCost, newCostH);
						pq.add(son);
					}

				}
			}
		}

		return null;
	}

	public Node AStar() {
		PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> (a.cost + a.costH) - (b.cost + b.costH));
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
						int newCostH = calcularCostoHeuristico(pos.getI(), pos.getJ(), n);
						int gas = 0;
						if (n.nave != 0) {
							gas = n.nave - 1;
						}
						Node son = new Node(new Par(pos.getI() + dirI[i], pos.getJ() + dirJ[i]), n, gas,
								n.items.clone(), n.naves.clone(), newCost, newCostH);
						pq.add(son);
					}

				}
			}
		}

		return null;
	}

	private int calcularCostoHeuristico(int i, int j, Node n) {
		int cost = 0;
		int costA = manhattan(i, j, items[0].getI(), items[0].getJ());
		int costB = manhattan(i, j, items[1].getI(), items[1].getJ());
		int costC = manhattan(items[0].getI(), items[0].getJ(), items[1].getI(), items[1].getJ());
		if (n.items[0])
			cost = costB;
		else if (n.items[1])
			cost = costA;
		else
			cost = Math.min(costA, costB) + costC;
		return cost;
	}

	private int manhattan(int i0, int j0, int i1, int j1) {
		return Math.abs(i0 - i1) + Math.abs(j0 - j1);
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
