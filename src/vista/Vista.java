package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import control.Busqueda;
import control.Input;
import modelo.Node;



public class Vista extends JFrame {

	private JPanel  pVarios, pBusqueda, pExtra, pInforme;
	private JLabel lTipoBusqueda, lAlgoritmo, lInforme;
	private JComboBox<Object> cbTipoBusqueda, cbAlgoritmo;
	private JButton bIniciar, bCargar;
	private JTextPane textArea;
	private Container contenedor;
	private JMenuBar barra;
	private JMenu opciones;
	private JMenuItem salirItem, reiniciarItem;
	private JLabel[][] grid;
	private int[][] tablero;


	private ManejaEventos eventos;


	public Vista() {

		initGUI();

		setTitle("Smart Robot");
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}


	private void initGUI() {

		tablero = new int [12][12];

		pExtra =  new JPanel();
		pVarios = new JPanel();
		pBusqueda = new JPanel();
		pInforme = new JPanel();

		bIniciar=new JButton("Iniciar");
		bIniciar.setFont(new Font("Tahoma", Font.BOLD, 22)); 
		bIniciar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		bIniciar.setEnabled(false);

		bCargar=new JButton("Cargar Mapa");
		bCargar.setFont(new Font("Tahoma", Font.BOLD, 22)); 
		bCargar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		bCargar.setEnabled(true);
		
		textArea = new JTextPane();
		textArea.setEditable(false);
		textArea.setFont(new Font("Tahoma", Font.BOLD, 22));
		StyledDocument style = textArea.getStyledDocument();
		SimpleAttributeSet align= new SimpleAttributeSet();
		StyleConstants.setAlignment(align, StyleConstants.ALIGN_CENTER);
		style.setParagraphAttributes(0, style.getLength(), align, false);

		lTipoBusqueda = new JLabel("Tipo de Búsqueda ");
		lTipoBusqueda.setFont(new Font("Tahoma", Font.BOLD, 18)); 
		lTipoBusqueda.setForeground(Color.BLACK);
		lTipoBusqueda.setHorizontalAlignment(SwingConstants.CENTER);

		lAlgoritmo = new JLabel("Algoritmo ");
		lAlgoritmo.setFont(new Font("Tahoma", Font.BOLD, 18)); 
		lAlgoritmo.setForeground(Color.BLACK);
		lAlgoritmo.setHorizontalAlignment(SwingConstants.CENTER);
		lAlgoritmo.setEnabled(true);

		lInforme = new JLabel("Informe ");
		lInforme.setFont(new Font("Tahoma", Font.BOLD, 22)); 
		lInforme.setForeground(Color.BLACK);
		lInforme.setHorizontalAlignment(SwingConstants.CENTER);

		opciones = new JMenu("Opciones");
		salirItem = new JMenuItem("Salir");
		reiniciarItem = new JMenuItem("Reiniciar");

		opciones.add(reiniciarItem);
		opciones.addSeparator();
		opciones.add(salirItem);

		barra = new JMenuBar();
		setJMenuBar(barra);
		barra.add(opciones);

		cbTipoBusqueda = new JComboBox();
		cbTipoBusqueda.setFont(new Font("Tahoma", Font.BOLD, 16));
		cbTipoBusqueda.setSelectedItem("");
		cbTipoBusqueda.setEditable(false);
		cbTipoBusqueda.addItem("");
		cbTipoBusqueda.addItem("Busqueda Informada");
		cbTipoBusqueda.addItem("Busqueda No Informada");

		cbAlgoritmo = new JComboBox();
		cbAlgoritmo.setFont(new Font("Tahoma", Font.BOLD, 16));
		cbAlgoritmo.setSelectedItem("");
		cbAlgoritmo.setEditable(false);
		cbAlgoritmo.setEnabled(false);


		pBusqueda.setLayout(new GridLayout(6,1));
		pBusqueda.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		pBusqueda.add(lTipoBusqueda);
		pBusqueda.add(cbTipoBusqueda);
		pBusqueda.add(lAlgoritmo);
		pBusqueda.add(cbAlgoritmo);
		pBusqueda.add(bCargar);
		pBusqueda.add(bIniciar);

		pInforme.setLayout(new BorderLayout());
		pInforme.add(lInforme, BorderLayout.NORTH);
		pInforme.add(textArea, BorderLayout.CENTER);

		pExtra.setLayout(new GridLayout(10,10));

		grid= new JLabel[10][10];
		Dimension size = new Dimension (60,60);
		for (int i = 0; i < 10; i++){
			for (int j = 0; j < 10; j++){
				grid[i][j] = new JLabel();
				grid[i][j].setBorder(new LineBorder(Color.BLACK));
				grid[i][j].setPreferredSize(size);
				grid[i][j].setOpaque(true);
				pExtra.add(grid[i][j]);
			}
		}


		pExtra.setOpaque(false);

		pVarios.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		pVarios.setLayout(new GridLayout(2,1));
		pVarios.add(pBusqueda);
		pVarios.add(pInforme);
		pVarios.setPreferredSize(new Dimension(350, 766));

		contenedor = getContentPane();
		contenedor.setLayout(new BorderLayout());
		contenedor.add(pExtra, BorderLayout.CENTER);
		contenedor.add(pVarios, BorderLayout.EAST);

		eventos = new ManejaEventos();
		bIniciar.addActionListener(eventos);
		bCargar.addActionListener(eventos);
		salirItem.addActionListener(eventos);
		reiniciarItem.addActionListener(eventos);
		cbTipoBusqueda.addItemListener(eventos);
		addWindowListener(eventos);


	}

	public void cargarMatriz (){

		 try {
         	
				tablero = Input.input();
				
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
         
         for(int i = 0; i < 10; ++i){
         	
 			for(int j = 0; j < 10; ++j){
 				
 				if ( tablero[i][j] == 1){

 					grid[i][j].setBackground(Color.darkGray);

     			}else if ( tablero[i][j]  == 2){

     				grid[i][j].setBackground(Color.blue);
     				grid[i][j].setIcon(new ImageIcon(getClass().getResource("../imagenes/robot.png")));

     			}else if (tablero[i][j]  == 3){

     				grid[i][j].setBackground(Color.green);

     			}else if ( tablero[i][j]  == 4){

     				grid[i][j].setBackground(Color.pink);

     			}else if ( tablero[i][j]  == 5){

     				grid[i][j].setBackground(Color.yellow);

     			}else if ( tablero[i][j]  == 6){

     				grid[i][j].setBackground(Color.red);

     			}else {
     				
     				grid[i][j].setBackground(Color.WHITE);
     				
     			}
 			}
 		}
	}


	public class ManejaEventos extends WindowAdapter implements ActionListener, ItemListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			
			if (event.getSource() == bCargar) {

				cargarMatriz();
				bCargar.setEnabled(false);
				bIniciar.setEnabled(true);
	            

			}
			
			if (event.getSource() == bIniciar) {

				if ( cbAlgoritmo.getSelectedItem().toString().equalsIgnoreCase("Amplitud")) {
					
					Busqueda b = new Busqueda();
					b.init();
					Node n = b.BFS();
					PrintMatrix r = new PrintMatrix(n, grid);
					Thread t = new Thread(r);
					t.start();
					
				}else if ( cbAlgoritmo.getSelectedItem().toString().equalsIgnoreCase("Costo uniforme")) {
					Busqueda b = new Busqueda();
					b.init();
					Node n = b.BCU();
					PrintMatrix r = new PrintMatrix(n, grid);
					Thread t = new Thread(r);
					t.start();
					System.out.print("Costo uniforme");
					
				}else if ( cbAlgoritmo.getSelectedItem().toString().equalsIgnoreCase("Profundidad evitando ciclos")) {
					Busqueda b = new Busqueda();
					b.init();
					Node n = b.DFS();
					PrintMatrix r = new PrintMatrix(n, grid);
					Thread t = new Thread(r);
					t.start();
					System.out.print("Profundidad evitando ciclos");
					
				}else if ( cbAlgoritmo.getSelectedItem().toString().equalsIgnoreCase("Avara")) {
					
					System.out.print("Avara");
					
				}else if ( cbAlgoritmo.getSelectedItem().toString().equalsIgnoreCase("A*")) {
					
					System.out.print("A*");
					
				}
	          

			}
			
			
			

			if (event.getSource() == salirItem) {

				int respuestaBox = JOptionPane.showConfirmDialog(null,
						"Desea salir?",
						"Salir de Smart Robot?",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);

				if (respuestaBox == JOptionPane.YES_OPTION) {

					System.exit(0);
				}

			}

			if(event.getSource() == reiniciarItem){

				int respuestaCaja = JOptionPane.showConfirmDialog(null,
						"Desea reiniciar?",
						"Nuevo Intento",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);

				if (respuestaCaja == JOptionPane.YES_OPTION) {

					EventQueue.invokeLater(new Runnable() {
						@Override
						public void run() {

							Vista myView =	new Vista();

						}
					});

					dispose();
				}

			}


		}



		public void windowClosing(WindowEvent e) {

			int respuestaBox = JOptionPane.showConfirmDialog(null,
					"Desea abandonar Smart Robot?",
					"Abandonar?",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);

			if (respuestaBox == JOptionPane.YES_OPTION) {

				System.exit(0);
			}

		}



		@Override
		public void itemStateChanged(ItemEvent e) {
			
			if (e.getStateChange() == ItemEvent.SELECTED) {

				Object item = e.getItem();

				if (item.toString().equalsIgnoreCase("Busqueda No Informada")) {

					cbAlgoritmo.removeAllItems();
					cbAlgoritmo.addItem("");
					cbAlgoritmo.addItem("Amplitud");
					cbAlgoritmo.addItem("Costo uniforme");
					cbAlgoritmo.addItem("Profundidad evitando ciclos");

				}else if (item.toString().equalsIgnoreCase("Busqueda Informada")) {

					cbAlgoritmo.removeAllItems();
					cbAlgoritmo.addItem("");
					cbAlgoritmo.addItem("Avara");
					cbAlgoritmo.addItem("A*");

				}

				cbAlgoritmo.setEnabled(true);

				 
			       
			    } else {
			    	
			    	cbAlgoritmo.removeAllItems();
			    	cbAlgoritmo.setEnabled(false);
			    }
			
		}
	}
	static class PrintMatrix implements Runnable{
		Node n;
		JLabel[][] grid;
		public PrintMatrix(Node n, JLabel[][] grid) {
			this.n = n;
			this.grid = grid;
		}
		@Override
		public void run() {
			
			try {
				Stack<Node> s = new Stack<>();
				while(n!=null) {
					s.add(n);
					n= n.parent;
				}
				
				Node aux = null;
				
				while(!s.isEmpty()) {
					
					 aux = s.pop();
					
					grid[aux.pos.getI()][aux.pos.getJ()].setBackground(Color.CYAN);
					grid[aux.pos.getI()][aux.pos.getJ()].setIcon(new ImageIcon(getClass().getResource("../imagenes/robot.png")));
					Thread.sleep(400);
					grid[aux.pos.getI()][aux.pos.getJ()].setIcon(null);
				}
				
				grid[aux.pos.getI()][aux.pos.getJ()].setIcon(new ImageIcon(getClass().getResource("../imagenes/robot.png")));
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}






}

