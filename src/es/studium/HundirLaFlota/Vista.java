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
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Vista {
	Font fuente = new Font("Wrecked Ship", Font.BOLD, 24);

	JFrame login = new JFrame("Login");
	JFrame partida = new JFrame("Partida");
	Frame carga = new Frame("Pantalla de Carga");
	JFrame menuPrincipal = new JFrame("Menú Principal");
	JPanel distribucionPanel = new JPanel();
	JPanel panelDisparo = new JPanel();
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



	private final int[] barcosPorColocar = {5, 4, 3, 3, 2};
	private java.util.List<Point> celdasSeleccionadas = new ArrayList<>();

	List<List<Point>> formacionElegidaCPU = new ArrayList();

	// Coordenadas para las 100 celdas (A1 a J10)
	Point[] coordenadasBarco = new Point[100];
	Point[] coordenadasTableroUsuario = new Point[50];
	// Para rellenar las casillas
	// 0-4 (5) 5-8 (4) 9-11 (3) 12-14 (3) 15-16 (2)   POSICIONES EN EL ARRAY DE LOS BARCOS
	List<Point> puntosClicados = new ArrayList<>();

	List<List<Point>> barcosUsuario = new ArrayList<>();  // Lista de barcos (cada uno con sus casillas)
	List<Point> barcoActual = new ArrayList<>();   // Casillas del barco que se está colocando

	int[] tamanos = {5, 4, 3, 3, 2};  // Tamaño de cada barco
	int indiceBarco = 0;             // Índice del barco actual
	// Formaciones predefinidas para la CPU
	private List<List<List<Point>>> formacionesCPU;

	// Conjunto de índices ocupados (celdas seleccionadas)
	Set<Integer> celdasOcupadas = new HashSet<>();

	// Guardar disparosJugador
	private List<Point> disparosJugador = new ArrayList<>();


	// CONTADOR PUNTOS
	int contadorPuntosUsuario = 0;
	int contadorPuntosCPU = 0;

	// Contador turnos
	int turno =0;

	List<Point> disparosCPU = new ArrayList<>();
	Point disparoCPU = new Point(0,0);

	public Vista() {


		inicializarLogin();
		inicializarMenu();
		inicializarNuevaPartida();
 // COORDENADAS DE TOODO EL TABLERO
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

		
		// TABLERO USUARIO

		coordenadasTableroUsuario[0] = new Point(111, 97);   // A1
		coordenadasTableroUsuario[1] = new Point(177, 97);   // A2
		coordenadasTableroUsuario[2] = new Point(243, 97);   // A3
		coordenadasTableroUsuario[3] = new Point(309, 97);   // A4
		coordenadasTableroUsuario[4] = new Point(375, 97);   // A5
		coordenadasTableroUsuario[5] = new Point(441, 97);   // A6
		coordenadasTableroUsuario[6] = new Point(507, 97);   // A7
		coordenadasTableroUsuario[7] = new Point(573, 97);   // A8
		coordenadasTableroUsuario[8] = new Point(639, 97);   // A9
		coordenadasTableroUsuario[9] = new Point(705, 97);   // A10

		coordenadasTableroUsuario[10] = new Point(111, 145);  // B1
		coordenadasTableroUsuario[11] = new Point(177, 145);  // B2
		coordenadasTableroUsuario[12] = new Point(243, 145);  // B3
		coordenadasTableroUsuario[13] = new Point(309, 145);  // B4
		coordenadasTableroUsuario[14] = new Point(375, 145);  // B5
		coordenadasTableroUsuario[15] = new Point(441, 145);  // B6
		coordenadasTableroUsuario[16] = new Point(507, 145);  // B7
		coordenadasTableroUsuario[17] = new Point(573, 145);  // B8
		coordenadasTableroUsuario[18] = new Point(639, 145);  // B9
		coordenadasTableroUsuario[19] = new Point(705, 145);  // B10

		coordenadasTableroUsuario[20] = new Point(111, 193);  // C1
		coordenadasTableroUsuario[21] = new Point(177, 193);  // C2
		coordenadasTableroUsuario[22] = new Point(243, 193);  // C3
		coordenadasTableroUsuario[23] = new Point(309, 193);  // C4
		coordenadasTableroUsuario[24] = new Point(375, 193);  // C5
		coordenadasTableroUsuario[25] = new Point(441, 193);  // C6
		coordenadasTableroUsuario[26] = new Point(507, 193);  // C7
		coordenadasTableroUsuario[27] = new Point(573, 193);  // C8
		coordenadasTableroUsuario[28] = new Point(639, 193);  // C9
		coordenadasTableroUsuario[29] = new Point(705, 193);  // C10

		coordenadasTableroUsuario[30] = new Point(111, 241);  // D1
		coordenadasTableroUsuario[31] = new Point(177, 241);  // D2
		coordenadasTableroUsuario[32] = new Point(243, 241);  // D3
		coordenadasTableroUsuario[33] = new Point(309, 241);  // D4
		coordenadasTableroUsuario[34] = new Point(375, 241);  // D5
		coordenadasTableroUsuario[35] = new Point(441, 241);  // D6
		coordenadasTableroUsuario[36] = new Point(507, 241);  // D7
		coordenadasTableroUsuario[37] = new Point(573, 241);  // D8
		coordenadasTableroUsuario[38] = new Point(639, 241);  // D9
		coordenadasTableroUsuario[39] = new Point(705, 241);  // D10

		coordenadasTableroUsuario[40] = new Point(111, 289);  // E1
		coordenadasTableroUsuario[41] = new Point(177, 289);  // E2
		coordenadasTableroUsuario[42] = new Point(243, 289);  // E3
		coordenadasTableroUsuario[43] = new Point(309, 289);  // E4
		coordenadasTableroUsuario[44] = new Point(375, 289);  // E5
		coordenadasTableroUsuario[45] = new Point(441, 289);  // E6
		coordenadasTableroUsuario[46] = new Point(507, 289);  // E7
		coordenadasTableroUsuario[47] = new Point(573, 289);  // E8
		coordenadasTableroUsuario[48] = new Point(639, 289);  // E9
		coordenadasTableroUsuario[49] = new Point(705, 289);  // E10

	}



	private Point obtenerCeldaDesdeClick(Point click) {
		int anchoCelda = 66;
		int altoCelda = 48;

		for (Point celda : coordenadasBarco) {
			Rectangle area = new Rectangle(celda.x, celda.y, anchoCelda, altoCelda);
			if (area.contains(click)) {
				return celda;
			}
		}

		return null; // No se hizo clic en ninguna celda válida
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
				g.setColor(new Color(29, 122, 207));

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


				g.setColor(Color.BLUE);
				int diameter = 30;

				// Pintar barcos ya colocados
				for (int i = 0; i < barcosUsuario.size(); i++) {
					List<Point> barco = barcosUsuario.get(i);
					for (int j = 0; j < barco.size(); j++) {
						Point p = barco.get(j);
						g.fillOval(p.x - diameter/2, p.y - diameter/2, diameter, diameter);
					}
				}

				// Pintar el barco en curso

				g.setColor(Color.CYAN);
				for (int i = 0; i < barcoActual.size(); i++) {

					Point p = barcoActual.get(i);
					g.fillOval(p.x - diameter/2, p.y - diameter/2, diameter, diameter);

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
				Point clickUsuario = new Point(e.getX(), e.getY());

				for (int i = 0; i <= 99; i++) {
					if (clickUsuario.distance(coordenadasBarco[i]) <= 10) {

						// Evita duplicados
						if (!barcoActual.contains(coordenadasBarco[i])) {
							barcoActual.add(coordenadasBarco[i]);
							panelNP.repaint();
						}

						// Si el barco actual ya tiene todas sus casillas
						if (barcoActual.size() == tamanos[indiceBarco]) {
							barcosUsuario.add(new ArrayList<>(barcoActual));
							JOptionPane.showMessageDialog(panelNP, 
									"¡Has colocado el barco de " + tamanos[indiceBarco] + " casillas!");

							barcoActual.clear();
							indiceBarco++; // Avanza al siguiente barco

							// IMPORTANTE: después de incrementar, comprobar si ya están todos
							if (indiceBarco == tamanos.length) {
								JOptionPane.showMessageDialog(panelNP, "¡Ya colocaste todos los barcos!");
								JOptionPane.showMessageDialog(panelNP, "¡En breve comenzará la partida!");

								try {
									Thread.sleep(3000);
								} catch (InterruptedException tiempo) {
									tiempo.printStackTrace();
								}

								iniciarDisparos();
							}
						}

						break;
					}
				}
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
	public List<List<Point>> obtenerFormacionAleatoria() {
		Random rand = new Random();
		int indice = rand.nextInt(formacionesCPU.size());
		return formacionesCPU.get(indice);
	}
	private void iniciarDisparos()
	{
		inicializarFormacionesCPU();
		List<List<Point>> formacionElegidaCPU = obtenerFormacionAleatoria();
		
		inicializarImpactos();
		// Quita el panel anterior
		nuevaPartida.getContentPane().removeAll();
		System.out.println(formacionElegidaCPU);

		imagen = caja.getImage("./imagenTablero.png");

		// Crea un nuevo panel (puedes usar otra imagen de fondo, otros botones, etc.)
		panelDisparo = new JPanel() {

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				g.drawString("¡Comienza la fase de disparos!", 100, 100);

				// Aquí puedes dibujar tablero enemigo, disparos, etc.
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
				// Dibuja los barcos del usuario
				if (barcosUsuario != null) {
					g.setColor(Color.BLUE);  // Color para los barcos del usuario

					for (List<Point> barco : barcosUsuario) {
						for (Point p : barco) {
							int size = 30; // tamaño de la casilla
							int x = p.x - size / 2;
							int y = p.y - size / 2;
							g.fillRect(x, y, size, size);
						}
					}
				}

				// Dibuja los disparos del jugador
				for (Point puntoDisparo : disparosJugador) {
				    boolean acierto = false;

				    for (List<Point> barcoCPU : formacionElegidaCPU) {
				        for (Point puntoCPU : barcoCPU) {
				            if (estaEnCelda(puntoCPU, puntoDisparo)) {
				                acierto = true;
				                break;
				            }
				        }
				        if (acierto) break;
				    }

				    if(acierto) {
				    	contadorPuntosUsuario+=15;
				        System.out.println("Fogonazo---");
				        g.setColor(Color.RED);
				    } else {
				        System.out.println("Piscinazo---");
				        g.setColor(Color.cyan);
				    }

				    int size = 30;
				    int x = puntoDisparo.x - size / 2;
				    int y = puntoDisparo.y - size / 2;

				    g.fillRect(x, y, size, size);
				}

				// Dibuja disparos de CPU
				for (Point disparo : disparosCPU) {
				    boolean acierto = false;

				    // Comprobar si el disparo ha impactado en algún barco del usuario
				    for (List<Point> barco : barcosUsuario) {
				        for (Point p : barco) {
				            if (estaEnCelda(p, disparo)) {
				                acierto = true;
				                break;
				            }
				        }
				        if (acierto) {
				        	contadorPuntosCPU+=15;
				        	break;
				        }
				    }

				    // Pintar en rojo si acierto, azul si fallo
				    g.setColor(acierto ? Color.RED : Color.cyan);

				    // Ajusta el dibujo para que se centre en la celda (si tus celdas tienen anchoCelda x altoCelda)
				    g.fillRect(disparo.x - 30 / 2, disparo.y - 30 / 2, 30, 30);
				
		

				}

				this.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
					    Point disparo = obtenerCeldaDesdeClick(e.getX(), e.getY());
					    procesarDisparoJugador(disparo);
					}

				});


			}


			@Override
			public Dimension getPreferredSize() {
				return new Dimension(1250, 625);
			}
		};

		panelDisparo.setBounds(0, 0, 1250, 625);
		nuevaPartida.getContentPane().add(panelDisparo);
		nuevaPartida.revalidate(); // actualiza el contenido
		nuevaPartida.repaint();    // repinta con el nuevo panel

	}

	private boolean todosBarcosHundidos() {
		for (int i = 0; i < impactosBarcosCPU.size(); i++) {
			if (!barcoHundido(impactosBarcosCPU.get(i))) {
				System.out.println("El barco " + i + " aún sigue a flote.");
				return false;
			}
		}
		System.out.println("¡Todos los barcos enemigos han sido hundidos!");
		return true;
	}

	private void procesarDisparoJugador(Point celdaDisparo) {
	    if (turno != 0) return;  // Solo puede disparar el jugador en su turno

	    if (celdaDisparo != null && !disparosJugador.contains(celdaDisparo)) {
	        registrarDisparo(celdaDisparo);
	        turno = 1;  // Paso turno a CPU
	        panelDisparo.repaint();
	        turnoCPU(); // La CPU hace su disparo y luego cambia turno a jugador
	        System.out.println("Ha disparado el Jugador");
	    }
	}

	List<List<Boolean>> impactosBarcosCPU = new ArrayList<>();

	// Inicializar impactos (hacerlo al cargar la formación CPU)
	private void inicializarImpactos() {
		impactosBarcosCPU.clear();
		for (List<Point> barco : formacionElegidaCPU) {
			List<Boolean> impactosBarco = new ArrayList<>();
			for (int i = 0; i < barco.size(); i++) {
				impactosBarco.add(false);
			}
			impactosBarcosCPU.add(impactosBarco);
		}
	}
	public void registrarDisparo(Point disparo) {
		boolean acierto = false;

		for (int i = 0; i < formacionElegidaCPU.size(); i++) {
			List<Point> barco = formacionElegidaCPU.get(i);
			List<Boolean> impactos = impactosBarcosCPU.get(i);

			for (int j = 0; j < barco.size(); j++) {
				Point celdaBarco = barco.get(j);
				if (estaEnCelda(celdaBarco, disparo)) {
					acierto = true;
					impactos.set(j, true);  // Marcar esta casilla como impactada

					// Comprobar si barco completo hundido
					if (barcoHundido(impactos)) {
						System.out.println("Barco hundido! Puntos: " + contadorPuntosUsuario);
					}
					break;
				}
			}
			if (acierto) break;
		}

		if (!acierto) {
			System.out.println("Disparo fallado.");
		}

		// Guardar disparo para pintar
		disparosJugador.add(disparo);

		// Aquí podrías añadir la lógica para comprobar fin de partida:
		if (todosBarcosHundidos()) {
			System.out.println("Puntos del Jugador:"+contadorPuntosUsuario);
			System.out.println("Puntos de la CPU: "+contadorPuntosCPU);
			System.out.println("¡Has ganado la partidaaaaaaaaaaaaaaaaaa!");
			// Acción para terminar la partida
		}
	}

	




	private void turnoCPU() {
	    disparoCPU = generarDisparoCPU();
	    disparosCPU.add(disparoCPU); // Añadimos el disparo a la lista de disparos de la CPU

	    boolean impacto = false;

	    for (int i = 0; i < barcosUsuario.size(); i++) {
	        List<Point> barco = barcosUsuario.get(i);
	        for (int j = 0; j < barco.size(); j++) {
	            Point p = barco.get(j);
	            if (estaEnCelda(p, disparoCPU)) {
	                impacto = true;
	                // Actualizar impactos, puntos, etc.
	                break;
	            }
	        }
	        if (impacto) break;
	    }

	    if (impacto) {
	    	 contadorPuntosCPU+=15;
	    	 }

	    System.out.println("Ha disparado la CPUUUUUUUUUUUU");
	    turno = 0;
	    partida.repaint();
	}



// Comprueba si todas las casillas de un barco están impactadas
	private boolean barcoHundido(List<Boolean> impactos) {
	    for (int i = 0; i < impactos.size(); i++) {
	        Boolean casillaImpactada = impactos.get(i);
	        if (!casillaImpactada) {
	            return false;
	        }
	    }
	    return true;
	}





	 // private int anchoCelda = 66;
//	private int altoCelda = 48;


	private Point generarDisparoCPU() {
	    int indiceRandom;
	    Point disparo;

	    do {
	        indiceRandom = (int) (Math.random() * coordenadasTableroUsuario.length);
	        disparo = coordenadasBarco[indiceRandom];
	        // Evitar disparar a una celda ya atacada por la CPU
	    } while (disparosCPU.contains(disparo));

	    return disparo;
	}





	public void manejarDisparo(Point click) {
		// SOLO PARA VERIFICAR SI SE HA DISPARADO YA AHI
		for (int i = 0; i < disparosJugador.size(); i++) {
			if (disparosJugador.get(i).equals(click)) return;
		}

		disparosJugador.add(click);

		boolean impacto = false;

		for (int i = 0; i < formacionElegidaCPU.size(); i++) {
			// Ya que formacion.get(i) no devuelve un punto sino una lista de puntos
			List<Point> barco = formacionElegidaCPU.get(i);

			for (int j = 0; j < barco.size(); j++) {
				Point parte = barco.get(j);
				if (estaEnCelda(parte, click)) {
					impacto = true;
					break;
				}
			}
			if (impacto) break;
		}

		panelDisparo.repaint();
	}

	private boolean estaEnCelda(Point celda, Point click) {
		int celdaX = celda.x;
		int celdaY = celda.y;
		int anchoCelda = 66;
		int altoCelda = 48;


		return (click.x >= celdaX && click.x < celdaX + anchoCelda &&
				click.y >= celdaY && click.y < celdaY + altoCelda);

	}
	public Point obtenerCeldaDesdeClick(int x, int y) {
		for (int i = 0; i < coordenadasBarco.length; i++) {
			Point celda = coordenadasBarco[i];
			Rectangle areaCelda = new Rectangle(celda.x - 13, celda.y - 13, 26, 26);
			if (areaCelda.contains(x, y)) {
				return celda;
			}
		}
		return null; // Click fuera de las celdas
	}




	private void inicializarFormacionesCPU() {
		formacionesCPU = new ArrayList<>();

		// Formación 1 (barcos colocados verticalmente y horizontalmente en filas F-J)
		List<List<Point>> form1 = new ArrayList<>();
		// Barco 5 casillas (tamaño 5) horizontal en F1-F5 (índices 50 a 54)
		form1.add(Arrays.asList(
				coordenadasBarco[50], coordenadasBarco[51], coordenadasBarco[52], 
				coordenadasBarco[53], coordenadasBarco[54]
				));
		// Barco 4 casillas vertical en G3-J3 (índices 62, 72, 82, 92)
		form1.add(Arrays.asList(
				coordenadasBarco[62], coordenadasBarco[72], coordenadasBarco[82], coordenadasBarco[92]
				));
		// Barco 3 casillas horizontal en H6-H8 (índices 75, 76, 77)
		form1.add(Arrays.asList(
				coordenadasBarco[75], coordenadasBarco[76], coordenadasBarco[77]
				));
		// Barco 3 casillas vertical en I8-K8 (índices 87, 97, (fuera rango) pero usaremos 97)
		// Para no salirnos del array usaremos solo I8-J8 (87, 97)
		form1.add(Arrays.asList(
				coordenadasBarco[87], coordenadasBarco[97]
				));
		// Barco 2 casillas horizontal en J10-I10 (índices 99, 89)
		form1.add(Arrays.asList(
				coordenadasBarco[99], coordenadasBarco[89]
				));

		formacionesCPU.add(form1);

		// Formación 2 (barcos colocados diferente)
		List<List<Point>> form2 = new ArrayList<>();
		// Barco 5 casillas vertical en F10-J10 (índices 59, 69, 79, 89, 99)
		form2.add(Arrays.asList(
				coordenadasBarco[59], coordenadasBarco[69], coordenadasBarco[79], 
				coordenadasBarco[89], coordenadasBarco[99]
				));
		// Barco 4 casillas horizontal en G1-G4 (índices 60, 61, 62, 63)
		form2.add(Arrays.asList(
				coordenadasBarco[60], coordenadasBarco[61], coordenadasBarco[62], coordenadasBarco[63]
				));
		// Barco 3 casillas vertical en H5-J5 (índices 74, 84, 94)
		form2.add(Arrays.asList(
				coordenadasBarco[74], coordenadasBarco[84], coordenadasBarco[94]
				));
		// Barco 3 casillas horizontal en I7-I9 (índices 86, 87, 88)
		form2.add(Arrays.asList(
				coordenadasBarco[86], coordenadasBarco[87], coordenadasBarco[88]
				));
		// Barco 2 casillas horizontal en J1-J2 (índices 90, 91)
		form2.add(Arrays.asList(
				coordenadasBarco[90], coordenadasBarco[91]
				));

		formacionesCPU.add(form2);
	}


}


