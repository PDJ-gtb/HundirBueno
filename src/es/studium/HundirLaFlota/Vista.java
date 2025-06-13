package es.studium.HundirLaFlota;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Point;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Vista {
	Font fuente = new Font("Wrecked Ship", Font.BOLD, 24);

	JFrame login = new JFrame("Login");
	Frame carga = new Frame("Pantalla de Carga");
	JFrame menuPrincipal = new JFrame("Menú Principal");
	JPanel distribucionPanel = new JPanel();
	JFrame nuevaPartida = new JFrame("Partida");

	Dimension tamanoFijo = new Dimension(100, 40);

	Dialog errorLog = new Dialog(login, "Mensaje", true);
	Label mensajeFB = new Label("      ¡Error en los credenciales!");

	// Elementos Login
	Label lblUsuario = new Label("Usuario:", Label.CENTER);
	TextField txtUsuario = new TextField(20);
	Label lblContrasena = new Label("Contraseña:", Label.CENTER);
	TextField txtContrasena = new TextField(20);
	Button btnLogear = new Button("Login");

	// Elementos Menu
	Label lblTitulo = new Label("Hundir La Flota", Label.CENTER);
	Button btnNuevaPartida = new Button("Nueva Partida");
	Button btnRanking = new Button("Ranking");
	Button btnAyuda = new Button("Ayuda");
	Button btnSalir = new Button("Salir");

	Toolkit caja = Toolkit.getDefaultToolkit();
	Image imagen;

	private Set<Point> todasLasOcupadas = new HashSet<>();
	private final int TILE_SIZE = 66;
	private final int OFFSET_X = 111;
	private final int OFFSET_Y = 65;

	private final int[] barcosPorColocar = {5, 4, 3, 3, 2};
	private int barcoActual = 0;
	private java.util.List<Point> celdasSeleccionadas = new ArrayList<>();

	// Coordenadas para las 100 celdas (A1 a J10)
	Point[] coordenadasBarco = new Point[100];

	// Conjunto de índices ocupados (celdas seleccionadas)
	Set<Integer> celdasOcupadas = new HashSet<>();
Point lastClick=null;
	public Vista() {
		
	
		inicializarLogin();
		inicializarMenu();
		inicializarNuevaPartida();
		coordenadasBarco[0] = new Point(111, 97);   // A1
		coordenadasBarco[1] = new Point(177, 97);   // A2
		coordenadasBarco[2] = new Point(243, 97);   // A3
		coordenadasBarco[3] = new Point(309, 97);   // A4
		coordenadasBarco[4] = new Point(375, 97);   // A5
		coordenadasBarco[5] = new Point(441, 97);   // A6
		coordenadasBarco[6] = new Point(507, 97);   // A7
		coordenadasBarco[7] = new Point(573, 97);   // A8
		coordenadasBarco[8] = new Point(639, 97);   // A9
		coordenadasBarco[9] = new Point(705, 97);   // A10

		coordenadasBarco[10] = new Point(111, 145);  // B1
		coordenadasBarco[11] = new Point(177, 145);  // B2
		coordenadasBarco[12] = new Point(243, 145);  // B3
		coordenadasBarco[13] = new Point(309, 145);  // B4
		coordenadasBarco[14] = new Point(375, 145);  // B5
		coordenadasBarco[15] = new Point(441, 145);  // B6
		coordenadasBarco[16] = new Point(507, 145);  // B7
		coordenadasBarco[17] = new Point(573, 145);  // B8
		coordenadasBarco[18] = new Point(639, 145);  // B9
		coordenadasBarco[19] = new Point(705, 145);  // B10

		coordenadasBarco[20] = new Point(111, 193);  // C1
		coordenadasBarco[21] = new Point(177, 193);  // C2
		coordenadasBarco[22] = new Point(243, 193);  // C3
		coordenadasBarco[23] = new Point(309, 193);  // C4
		coordenadasBarco[24] = new Point(375, 193);  // C5
		coordenadasBarco[25] = new Point(441, 193);  // C6
		coordenadasBarco[26] = new Point(507, 193);  // C7
		coordenadasBarco[27] = new Point(573, 193);  // C8
		coordenadasBarco[28] = new Point(639, 193);  // C9
		coordenadasBarco[29] = new Point(705, 193);  // C10

		coordenadasBarco[30] = new Point(111, 241);  // D1
		coordenadasBarco[31] = new Point(177, 241);  // D2
		coordenadasBarco[32] = new Point(243, 241);  // D3
		coordenadasBarco[33] = new Point(309, 241);  // D4
		coordenadasBarco[34] = new Point(375, 241);  // D5
		coordenadasBarco[35] = new Point(441, 241);  // D6
		coordenadasBarco[36] = new Point(507, 241);  // D7
		coordenadasBarco[37] = new Point(573, 241);  // D8
		coordenadasBarco[38] = new Point(639, 241);  // D9
		coordenadasBarco[39] = new Point(705, 241);  // D10

		coordenadasBarco[40] = new Point(111, 289);  // E1
		coordenadasBarco[41] = new Point(177, 289);  // E2
		coordenadasBarco[42] = new Point(243, 289);  // E3
		coordenadasBarco[43] = new Point(309, 289);  // E4
		coordenadasBarco[44] = new Point(375, 289);  // E5
		coordenadasBarco[45] = new Point(441, 289);  // E6
		coordenadasBarco[46] = new Point(507, 289);  // E7
		coordenadasBarco[47] = new Point(573, 289);  // E8
		coordenadasBarco[48] = new Point(639, 289);  // E9
		coordenadasBarco[49] = new Point(705, 289);  // E10

		coordenadasBarco[50] = new Point(111, 337);  // F1
		coordenadasBarco[51] = new Point(177, 337);  // F2
		coordenadasBarco[52] = new Point(243, 337);  // F3
		coordenadasBarco[53] = new Point(309, 337);  // F4
		coordenadasBarco[54] = new Point(375, 337);  // F5
		coordenadasBarco[55] = new Point(441, 337);  // F6
		coordenadasBarco[56] = new Point(507, 337);  // F7
		coordenadasBarco[57] = new Point(573, 337);  // F8
		coordenadasBarco[58] = new Point(639, 337);  // F9
		coordenadasBarco[59] = new Point(705, 337);  // F10

		coordenadasBarco[60] = new Point(111, 385);  // G1
		coordenadasBarco[61] = new Point(177, 385);  // G2
		coordenadasBarco[62] = new Point(243, 385);  // G3
		coordenadasBarco[63] = new Point(309, 385);  // G4
		coordenadasBarco[64] = new Point(375, 385);  // G5
		coordenadasBarco[65] = new Point(441, 385);  // G6
		coordenadasBarco[66] = new Point(507, 385);  // G7
		coordenadasBarco[67] = new Point(573, 385);  // G8
		coordenadasBarco[68] = new Point(639, 385);  // G9
		coordenadasBarco[69] = new Point(705, 385);  // G10

		coordenadasBarco[70] = new Point(111, 433);  // H1
		coordenadasBarco[71] = new Point(177, 433);  // H2
		coordenadasBarco[72] = new Point(243, 433);  // H3
		coordenadasBarco[73] = new Point(309, 433);  // H4
		coordenadasBarco[74] = new Point(375, 433);  // H5
		coordenadasBarco[75] = new Point(441, 433);  // H6
		coordenadasBarco[76] = new Point(507, 433);  // H7
		coordenadasBarco[77] = new Point(573, 433);  // H8
		coordenadasBarco[78] = new Point(639, 433);  // H9
		coordenadasBarco[79] = new Point(705, 433);  // H10

		coordenadasBarco[80] = new Point(111, 481);  // I1
		coordenadasBarco[81] = new Point(177, 481);  // I2
		coordenadasBarco[82] = new Point(243, 481);  // I3
		coordenadasBarco[83] = new Point(309, 481);  // I4
		coordenadasBarco[84] = new Point(375, 481);  // I5
		coordenadasBarco[85] = new Point(441, 481);  // I6
		coordenadasBarco[86] = new Point(507, 481);  // I7
		coordenadasBarco[87] = new Point(573, 481);  // I8
		coordenadasBarco[88] = new Point(639, 481);  // I9
		coordenadasBarco[89] = new Point(705, 481);  // I10

		coordenadasBarco[90] = new Point(111, 529);  // J1
		coordenadasBarco[91] = new Point(177, 529);  // J2
		coordenadasBarco[92] = new Point(243, 529);  // J3
		coordenadasBarco[93] = new Point(309, 529);  // J4
		coordenadasBarco[94] = new Point(375, 529);  // J5
		coordenadasBarco[95] = new Point(441, 529);  // J6
		coordenadasBarco[96] = new Point(507, 529);  // J7
		coordenadasBarco[97] = new Point(573, 529);  // J8
		coordenadasBarco[98] = new Point(639, 529);  // J9
		coordenadasBarco[99] = new Point(705, 529);  // J10

	}

	

	private void inicializarLogin() {
		login.setLayout(new GridLayout(5, 5, 5, 5));
		login.add(lblUsuario);
		login.add(txtUsuario);
		login.add(lblContrasena);
		login.add(txtContrasena);
		login.add(btnLogear);

		login.setSize(400, 200);
		login.setBackground(Color.pink);
		login.setVisible(false);

		errorLog.add(mensajeFB);
		errorLog.setSize(200, 200);
		errorLog.setLocationRelativeTo(null);
	}

	private void inicializarMenu() {
		distribucionPanel.setLayout(new BoxLayout(distribucionPanel, BoxLayout.Y_AXIS));
		distribucionPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
		distribucionPanel.add(lblTitulo);
		distribucionPanel.add(btnNuevaPartida);
		distribucionPanel.add(Box.createVerticalStrut(10));
		distribucionPanel.add(btnRanking);
		distribucionPanel.add(Box.createVerticalStrut(10));
		distribucionPanel.add(btnAyuda);
		distribucionPanel.add(Box.createVerticalStrut(10));
		distribucionPanel.add(btnSalir);

		menuPrincipal.add(distribucionPanel);
		menuPrincipal.setSize(800, 400);
		menuPrincipal.setLocationRelativeTo(null);
		menuPrincipal.setVisible(true);
	}

	private void inicializarNuevaPartida() {
		// Cargar la imagen (usa tu método 'caja.getImage' si funciona correctamente)
		imagen = caja.getImage("./imagenTablero.png");

		// Crear panel personalizado que dibuja la imagen
		JPanel panelNP = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (imagen != null) {
					// Dibuja la imagen con tamaño 802x572 desde (0, 0)
					g.drawImage(imagen, 0, 0, 1250, 625, this);
				}
				
				Font font = new Font("Call of Ops Duty II", Font.ITALIC | Font.BOLD, 27); 
				g.setFont(font); 
				g.setColor(new Color(29, 122, 207));; // o el color que prefieras

				Graphics2D g2 = (Graphics2D) g;
				FontMetrics fm = g2.getFontMetrics();  // Obtiene las métricas de la fuente actual

				String texto1 = "Almirante";
				int anchoTexto1 = fm.stringWidth(texto1);  // Calcula el ancho en píxeles
				g2.drawString(texto1, 1100 - anchoTexto1 / 2, 90);  // Centrado horizontal

				String texto2 = "Nombre";
				int anchoTexto2 = fm.stringWidth(texto2);
				g2.drawString(texto2, 1100 - anchoTexto2 / 2, 120);
				
				String texto3 = "Barcos Restantes:";
				int anchoTexto3 = fm.stringWidth(texto3);
				g2.drawString(texto3, 1020 - anchoTexto3 / 2, 225);
				
				String texto4 = "Puntuación:";
				int anchoTexto4 = fm.stringWidth(texto4);
				g2.drawString(texto4, 1020 - anchoTexto4 / 2, 530);
				
				String texto5 = "1000";
				int anchoTexto5 = fm.stringWidth(texto5);
				g2.drawString(texto5, 1020 - anchoTexto5 / 2, 560);

				
				if (lastClick != null) {
		            g.setColor(Color.BLUE);
		            g.fillOval(lastClick.x - 15, lastClick.y - 15, 30, 30); // centrado sobre la coordenada
		        }
				
				

			}
		


			// Opcional: definir el tamaño preferido del panel (puedes omitirlo si usas un layout)
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(802, 572);
			}
		};
		
			panelNP.addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseClicked(MouseEvent e) {
			    	Point clickUsuario =  new Point(e.getX(), e.getY());
			    		
			        for (int i= 0; i <=99;i++) {
			        	if (clickUsuario.distance(coordenadasBarco[i])<=10) {
			        		
			        		lastClick = coordenadasBarco[i];
			        		panelNP.repaint();
			        		break;
			        	}
			        }

			       // System.out.println("Click en: (" + x + ", " + y + ")");

			        
			    }
			});
		// Establecer el layout y añadir el panel al JFrame
		nuevaPartida.getContentPane().setLayout(null); // Usamos layout nulo para posicionar manualmente
		panelNP.setBounds(0, 0, 1250, 625); // Usa todo el espacio del JFrame

		nuevaPartida.getContentPane().add(panelNP);
		nuevaPartida.setSize(1266, 664);
		nuevaPartida.setLocationRelativeTo(null); // Centra la ventana
		nuevaPartida.setVisible(true);
	}

}


