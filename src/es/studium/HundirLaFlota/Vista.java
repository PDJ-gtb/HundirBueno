package es.studium.HundirLaFlota;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class Vista {
	Font fuente = new Font("./font/Warzone.ttf", Font.BOLD, 24);

	// Ventanas y paneles
	JFrame login = new JFrame("Login");
	JFrame partida = new JFrame("Partida");
	JFrame menuPrincipal = new JFrame("Menú Principal");
	JFrame nuevaPartida = new JFrame("Partida");
	JDialog errorLog = new JDialog(login, "Mensaje", true);

	// Paneles
	JPanel distribucionPanel = new JPanel();
	JPanel panelColocacion = new JPanel();
	JPanel panelDisparo = new JPanel();

	// Etiquetas y campos de texto
	JLabel lblUsuario = new JLabel("Usuario:", SwingConstants.CENTER);
	JTextField txtUsuario = new JTextField(20);

	JLabel lblContrasena = new JLabel("Contraseña:", SwingConstants.CENTER);
	JPasswordField txtContrasena = new JPasswordField(20);

	// Botones
	JButton btnLogear = new JButton("Login");
	JButton btnNuevaPartida = new JButton("Nueva Partida");
	JButton btnRanking = new JButton("Ranking");
	JButton btnAyuda = new JButton("Ayuda");
	JButton btnSalir = new JButton("Salir");

	// Etiquetas adicionales
	JLabel lblTitulo = new JLabel("Hundir La Flota", SwingConstants.CENTER);
	JLabel mensajeFB = new JLabel("¡Error en los credenciales!", SwingConstants.CENTER);

	// Recursos
	Toolkit caja = Toolkit.getDefaultToolkit();
	Image imagen;

	
	

	List<List<Point>> formacionElegidaCPU = new ArrayList();

	// Coordenadas para las 100 celdas (A1 a J10)
	Point[] coordenadasBarco = new Point[100];
	Point[] coordenadasTableroCPU = new Point[100];
	Point[] coordenadasTableroUsuario = new Point[100];


	List<List<Point>> barcosUsuario = new ArrayList<>();  // Lista de barcos (cada uno con sus casillas)
	List<Point> barcoActual = new ArrayList<>();   // Casillas del barco que se está colocando

	int[] tamanos = {5, 4, 3, 3, 2};  // Tamaño de cada barco
	int indiceBarco = 0;             // Índice del barco actual
	// Formaciones predefinidas para la CPU
	private List<List<List<Point>>> formacionesCPU;

	// Conjunto de índices ocupados (celdas seleccionadas)
	Set<Integer> celdasOcupadas = new HashSet<>();


	private Point[][] barcosCPU;
	private int puntosUsuario = 0;
	private int puntosCPU = 0;
	private boolean turnoJugador = true;
	private List<Point> disparosJugador = new ArrayList<>();
	private List<Boolean> aciertosJugador = new ArrayList<>();

	private List<Point> disparosCPU = new ArrayList<>();
	private List<Boolean> aciertosCPU = new ArrayList<>();



	private boolean juegoTerminado = false;


	Point disparoCPU = new Point(0,0);
	// Guardar disparosJugador

	private Point puntoExplosion = null;
	private int radioExplosion = 0;
	private Timer timerExplosion;

	CardLayout layout = new CardLayout();
	JPanel contenedorPaneles = new JPanel(layout);



	List<List<Boolean>> impactosBarcosCPU = new ArrayList<>();



	//  RANKING
	public JFrame rankingFrame = new JFrame("Ranking de Jugadores");
	public JTable tablaRanking;
	public DefaultTableModel modeloRanking;

	public Vista() {


		inicializarLogin();



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


		//TABLERO CPU

		coordenadasTableroCPU[0] = new Point(1085, 97);   // A1
		coordenadasTableroCPU[1] = new Point(1151, 97);   // A2
		coordenadasTableroCPU[2] = new Point(1217, 97);   // A3
		coordenadasTableroCPU[3] = new Point(1283, 97);   // A4
		coordenadasTableroCPU[4] = new Point(1349, 97);   // A5
		coordenadasTableroCPU[5] = new Point(1415, 97);   // A6
		coordenadasTableroCPU[6] = new Point(1481, 97);   // A7
		coordenadasTableroCPU[7] = new Point(1547, 97);   // A8
		coordenadasTableroCPU[8] = new Point(1613, 97);   // A9
		coordenadasTableroCPU[9] = new Point(1679, 97);   // A10

		coordenadasTableroCPU[10] = new Point(1085, 145);  // B1
		coordenadasTableroCPU[11] = new Point(1151, 145);  // B2
		coordenadasTableroCPU[12] = new Point(1217, 145);  // B3
		coordenadasTableroCPU[13] = new Point(1283, 145);  // B4
		coordenadasTableroCPU[14] = new Point(1349, 145);  // B5
		coordenadasTableroCPU[15] = new Point(1415, 145);  // B6
		coordenadasTableroCPU[16] = new Point(1481, 145);  // B7
		coordenadasTableroCPU[17] = new Point(1547, 145);  // B8
		coordenadasTableroCPU[18] = new Point(1613, 145);  // B9
		coordenadasTableroCPU[19] = new Point(1679, 145);  // B10

		coordenadasTableroCPU[20] = new Point(1085, 193);  // C1
		coordenadasTableroCPU[21] = new Point(1151, 193);  // C2
		coordenadasTableroCPU[22] = new Point(1217, 193);  // C3
		coordenadasTableroCPU[23] = new Point(1283, 193);  // C4
		coordenadasTableroCPU[24] = new Point(1349, 193);  // C5
		coordenadasTableroCPU[25] = new Point(1415, 193);  // C6
		coordenadasTableroCPU[26] = new Point(1481, 193);  // C7
		coordenadasTableroCPU[27] = new Point(1547, 193);  // C8
		coordenadasTableroCPU[28] = new Point(1613, 193);  // C9
		coordenadasTableroCPU[29] = new Point(1679, 193);  // C10

		coordenadasTableroCPU[30] = new Point(1085, 241);  // D1
		coordenadasTableroCPU[31] = new Point(1151, 241);  // D2
		coordenadasTableroCPU[32] = new Point(1217, 241);  // D3
		coordenadasTableroCPU[33] = new Point(1283, 241);  // D4
		coordenadasTableroCPU[34] = new Point(1349, 241);  // D5
		coordenadasTableroCPU[35] = new Point(1415, 241);  // D6
		coordenadasTableroCPU[36] = new Point(1481, 241);  // D7
		coordenadasTableroCPU[37] = new Point(1547, 241);  // D8
		coordenadasTableroCPU[38] = new Point(1613, 241);  // D9
		coordenadasTableroCPU[39] = new Point(1679, 241);  // D10

		coordenadasTableroCPU[40] = new Point(1085, 289);  // E1
		coordenadasTableroCPU[41] = new Point(1151, 289);  // E2
		coordenadasTableroCPU[42] = new Point(1217, 289);  // E3
		coordenadasTableroCPU[43] = new Point(1283, 289);  // E4
		coordenadasTableroCPU[44] = new Point(1349, 289);  // E5
		coordenadasTableroCPU[45] = new Point(1415, 289);  // E6
		coordenadasTableroCPU[46] = new Point(1481, 289);  // E7
		coordenadasTableroCPU[47] = new Point(1547, 289);  // E8
		coordenadasTableroCPU[48] = new Point(1613, 289);  // E9
		coordenadasTableroCPU[49] = new Point(1679, 289);  // E10

		coordenadasTableroCPU[50] = new Point(1085, 337);  // F1
		coordenadasTableroCPU[51] = new Point(1151, 337);  // F2
		coordenadasTableroCPU[52] = new Point(1217, 337);  // F3
		coordenadasTableroCPU[53] = new Point(1283, 337);  // F4
		coordenadasTableroCPU[54] = new Point(1349, 337);  // F5
		coordenadasTableroCPU[55] = new Point(1415, 337);  // F6
		coordenadasTableroCPU[56] = new Point(1481, 337);  // F7
		coordenadasTableroCPU[57] = new Point(1547, 337);  // F8
		coordenadasTableroCPU[58] = new Point(1613, 337);  // F9
		coordenadasTableroCPU[59] = new Point(1679, 337);  // F10

		coordenadasTableroCPU[60] = new Point(1085, 385);  // G1
		coordenadasTableroCPU[61] = new Point(1151, 385);  // G2
		coordenadasTableroCPU[62] = new Point(1217, 385);  // G3
		coordenadasTableroCPU[63] = new Point(1283, 385);  // G4
		coordenadasTableroCPU[64] = new Point(1349, 385);  // G5
		coordenadasTableroCPU[65] = new Point(1415, 385);  // G6
		coordenadasTableroCPU[66] = new Point(1481, 385);  // G7
		coordenadasTableroCPU[67] = new Point(1547, 385);  // G8
		coordenadasTableroCPU[68] = new Point(1613, 385);  // G9
		coordenadasTableroCPU[69] = new Point(1679, 385);  // G10

		coordenadasTableroCPU[70] = new Point(1085, 433);  // H1
		coordenadasTableroCPU[71] = new Point(1151, 433);  // H2
		coordenadasTableroCPU[72] = new Point(1217, 433);  // H3
		coordenadasTableroCPU[73] = new Point(1283, 433);  // H4
		coordenadasTableroCPU[74] = new Point(1349, 433);  // H5
		coordenadasTableroCPU[75] = new Point(1415, 433);  // H6
		coordenadasTableroCPU[76] = new Point(1481, 433);  // H7
		coordenadasTableroCPU[77] = new Point(1547, 433);  // H8
		coordenadasTableroCPU[78] = new Point(1613, 433);  // H9
		coordenadasTableroCPU[79] = new Point(1679, 433);  // H10

		coordenadasTableroCPU[80] = new Point(1085, 481);  // I1
		coordenadasTableroCPU[81] = new Point(1151, 481);  // I2
		coordenadasTableroCPU[82] = new Point(1217, 481);  // I3
		coordenadasTableroCPU[83] = new Point(1283, 481);  // I4
		coordenadasTableroCPU[84] = new Point(1349, 481);  // I5
		coordenadasTableroCPU[85] = new Point(1415, 481);  // I6
		coordenadasTableroCPU[86] = new Point(1481, 481);  // I7
		coordenadasTableroCPU[87] = new Point(1547, 481);  // I8
		coordenadasTableroCPU[88] = new Point(1613, 481);  // I9
		coordenadasTableroCPU[89] = new Point(1679, 481);  // I10

		coordenadasTableroCPU[90] = new Point(1085, 529);  // J1
		coordenadasTableroCPU[91] = new Point(1151, 529);  // J2
		coordenadasTableroCPU[92] = new Point(1217, 529);  // J3
		coordenadasTableroCPU[93] = new Point(1283, 529);  // J4
		coordenadasTableroCPU[94] = new Point(1349, 529);  // J5
		coordenadasTableroCPU[95] = new Point(1415, 529);  // J6
		coordenadasTableroCPU[96] = new Point(1481, 529);  // J7
		coordenadasTableroCPU[97] = new Point(1547, 529);  // J8
		coordenadasTableroCPU[98] = new Point(1613, 529);  // J9
		coordenadasTableroCPU[99] = new Point(1679, 529);  // J10




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

		coordenadasTableroUsuario[50] = new Point(111, 337);  // F1
		coordenadasTableroUsuario[51] = new Point(177, 337);  // F2
		coordenadasTableroUsuario[52] = new Point(243, 337);  // F3
		coordenadasTableroUsuario[53] = new Point(309, 337);  // F4
		coordenadasTableroUsuario[54] = new Point(375, 337);  // F5
		coordenadasTableroUsuario[55] = new Point(441, 337);  // F6
		coordenadasTableroUsuario[56] = new Point(507, 337);  // F7
		coordenadasTableroUsuario[57] = new Point(573, 337);  // F8
		coordenadasTableroUsuario[58] = new Point(639, 337);  // F9
		coordenadasTableroUsuario[59] = new Point(705, 337);  // F10

		coordenadasTableroUsuario[60] = new Point(111, 385);  // G1
		coordenadasTableroUsuario[61] = new Point(177, 385);  // G2
		coordenadasTableroUsuario[62] = new Point(243, 385);  // G3
		coordenadasTableroUsuario[63] = new Point(309, 385);  // G4
		coordenadasTableroUsuario[64] = new Point(375, 385);  // G5
		coordenadasTableroUsuario[65] = new Point(441, 385);  // G6
		coordenadasTableroUsuario[66] = new Point(507, 385);  // G7
		coordenadasTableroUsuario[67] = new Point(573, 385);  // G8
		coordenadasTableroUsuario[68] = new Point(639, 385);  // G9
		coordenadasTableroUsuario[69] = new Point(705, 385);  // G10

		coordenadasTableroUsuario[70] = new Point(111, 433);  // H1
		coordenadasTableroUsuario[71] = new Point(177, 433);  // H2
		coordenadasTableroUsuario[72] = new Point(243, 433);  // H3
		coordenadasTableroUsuario[73] = new Point(309, 433);  // H4
		coordenadasTableroUsuario[74] = new Point(375, 433);  // H5
		coordenadasTableroUsuario[75] = new Point(441, 433);  // H6
		coordenadasTableroUsuario[76] = new Point(507, 433);  // H7
		coordenadasTableroUsuario[77] = new Point(573, 433);  // H8
		coordenadasTableroUsuario[78] = new Point(639, 433);  // H9
		coordenadasTableroUsuario[79] = new Point(705, 433);  // H10

		coordenadasTableroUsuario[80] = new Point(111, 481);  // I1
		coordenadasTableroUsuario[81] = new Point(177, 481);  // I2
		coordenadasTableroUsuario[82] = new Point(243, 481);  // I3
		coordenadasTableroUsuario[83] = new Point(309, 481);  // I4
		coordenadasTableroUsuario[84] = new Point(375, 481);  // I5
		coordenadasTableroUsuario[85] = new Point(441, 481);  // I6
		coordenadasTableroUsuario[86] = new Point(507, 481);  // I7
		coordenadasTableroUsuario[87] = new Point(573, 481);  // I8
		coordenadasTableroUsuario[88] = new Point(639, 481);  // I9
		coordenadasTableroUsuario[89] = new Point(705, 481);  // I10

		coordenadasTableroUsuario[90] = new Point(111, 529);  // J1
		coordenadasTableroUsuario[91] = new Point(177, 529);  // J2
		coordenadasTableroUsuario[92] = new Point(243, 529);  // J3
		coordenadasTableroUsuario[93] = new Point(309, 529);  // J4
		coordenadasTableroUsuario[94] = new Point(375, 529);  // J5
		coordenadasTableroUsuario[95] = new Point(441, 529);  // J6
		coordenadasTableroUsuario[96] = new Point(507, 529);  // J7
		coordenadasTableroUsuario[97] = new Point(573, 529);  // J8
		coordenadasTableroUsuario[98] = new Point(639, 529);  // J9
		coordenadasTableroUsuario[99] = new Point(705, 529);  // J10


		contenedorPaneles.add(panelColocacion, "colocacion");
		contenedorPaneles.add(panelDisparo, "disparo");

		nuevaPartida.setContentPane(contenedorPaneles); // Estableces el contenedor como el panel principal

	}

	public void inicializarRanking() {
		// Inicializa el ranking creando y configurando la tabla
		String[] columnas = { "Jugador", "Victorias" };
		modeloRanking = new DefaultTableModel(columnas, 0);
		tablaRanking = new JTable(modeloRanking);

		tablaRanking.setFillsViewportHeight(true);
		tablaRanking.setRowHeight(25);
		tablaRanking.setEnabled(false);

		JScrollPane scroll = new JScrollPane(tablaRanking);
		rankingFrame.setSize(400, 300);
		rankingFrame.setLocationRelativeTo(null);
		rankingFrame.add(scroll);
	}




	public void inicializarLogin() {
		// Panel principal para login
		Image imagenFondoLogin = caja.getImage("fondoLoginMenu.png");

		JPanel panelLogin = new JPanel(new GridBagLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (imagenFondoLogin != null) {
					g.drawImage(imagenFondoLogin, 0, 0, getWidth(), getHeight(), this);
				}
			}
		};

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Usuario
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelLogin.add(lblUsuario, gbc);

		gbc.gridx = 1;
		panelLogin.add(txtUsuario, gbc);

		// Contraseña
		gbc.gridx = 0;
		gbc.gridy = 1;
		panelLogin.add(lblContrasena, gbc);

		gbc.gridx = 1;
		panelLogin.add(txtContrasena, gbc);

		// Botón de login
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		panelLogin.add(btnLogear, gbc);

		// Ventana de login
		login.setContentPane(panelLogin);
		login.setSize(400, 200);
		login.setLocationRelativeTo(null);
		login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Ventana de error login
		errorLog.setLayout(new BorderLayout());
		errorLog.add(mensajeFB, BorderLayout.CENTER);
		errorLog.setSize(250, 100);
		errorLog.setLocationRelativeTo(null);
		errorLog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		login.setVisible(true);
	}

	public void inicializarMenu() {
		// Fuente para título y botones
		Font fuenteTitulo = cargarFuentePersonalizada("./font/Warzone.ttf", 42f, Font.BOLD);
		Font fuenteBoton = cargarFuentePersonalizada("./font/Warzone.ttf", 12f, Font.BOLD);

		// Estilos de botones
		JButton[] botones = {btnNuevaPartida, btnRanking, btnAyuda, btnSalir};
		for (int i = 0; i < botones.length; i++) {
			botones[i].setFont(fuenteBoton);
			botones[i].setBackground(new Color(30, 144, 255)); // Azul moderno
			botones[i].setForeground(Color.WHITE);             // Texto blanco
			botones[i].setPreferredSize(new Dimension(200, 40));
		}

		lblTitulo.setFont(fuenteTitulo);
		lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Panel central con diseño vertical
		Image imagenFondoMenu = caja.getImage("./fondoLoginMenu.png");

		JPanel panelCentral = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (imagenFondoMenu != null) {
					g.drawImage(imagenFondoMenu, 0, 0, getWidth(), getHeight(), this);
				}
			}
		};
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		panelCentral.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

		panelCentral.add(lblTitulo);
		panelCentral.add(Box.createVerticalStrut(40));

		// Recorremos el array de botones utilizando un bucle for tradicional
		for (int i = 0; i < botones.length; i++) {
		    JButton boton = botones[i];

		    // Creamos un contenedor para centrar visualmente el botón
		    JPanel panelBotones = new JPanel();
		    panelBotones.setBackground(Color.WHITE); 

		    // Añadimos el botón al contenedor
		    panelBotones.add(boton);

		    // Añadimos el contenedor al panel central
		    panelCentral.add(panelBotones);

		    // Añadimos un espacio vertical debajo del botón para separarlo del siguiente
		    panelCentral.add(Box.createVerticalStrut(20));
		}


		// Centrado absoluto
		menuPrincipal.getContentPane().setLayout(new GridBagLayout());
		menuPrincipal.getContentPane().setBackground(Color.WHITE);
		menuPrincipal.getContentPane().add(panelCentral);

		menuPrincipal.setSize(800, 500);
		menuPrincipal.setLocationRelativeTo(null);
		menuPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuPrincipal.setVisible(true);

		reproducirSonidoMenu();
	}


	public void inicializarNuevaPartida() {

		Font fuenteNuevaPartida = cargarFuentePersonalizada("./font/Warzone.ttf", 22f, Font.BOLD);

		// Cargar la imagen (usa tu método 'caja.getImage' si funciona correctamente)
		imagen = caja.getImage("./tablero.png");

		// Crear panel personalizado que dibuja la imagen
		panelColocacion = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (imagen != null) {
					// Dibuja la imagen con tamaño 802x572 desde (0, 0)
					g.drawImage(imagen, 0, 0, 1782, 625, this);
				}

				g.setFont(fuenteNuevaPartida);
				g.setColor(Color.black);

				Graphics2D g2 = (Graphics2D) g;
				FontMetrics fm = g2.getFontMetrics();  // Obtiene las métricas de la fuente actual

				String texto1 = "ALMIRANTE";
				int anchoTexto1 = fm.stringWidth(texto1);  // Calcula el ancho en píxeles
				g2.drawString(texto1, 890 - anchoTexto1 / 2, 180);  // Centrado horizontal

				String texto3 = "BARCOS";
				int anchoTexto3 = fm.stringWidth(texto3);
				g2.drawString(texto3, 890 - anchoTexto3 / 2, 270);

				String texto6 = "RESTANTES";
				int anchoTexto6 = fm.stringWidth(texto6);
				g2.drawString(texto6, 890 - anchoTexto6 / 2, 295);

				String textoCPU = "JUGADOR";
				int anchoTextoCPU = fm.stringWidth(textoCPU);
				g2.drawString(textoCPU, 410 - anchoTextoCPU / 2, 585);

				String textoJugador = "CPU";
				int anchoTextoJugador = fm.stringWidth(textoJugador);
				g2.drawString(textoJugador, 1386 - anchoTextoJugador / 2, 585);


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

		panelColocacion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Point clickUsuario = new Point(e.getX(), e.getY());

				for (int i = 0; i <= 99; i++) {
					if (clickUsuario.distance(coordenadasBarco[i]) <= 10) {

						// Evita duplicados
						if (!barcoActual.contains(coordenadasBarco[i])) {
							barcoActual.add(coordenadasBarco[i]);
							panelColocacion.repaint();
						}

						// Si el barco actual ya tiene todas sus casillas
						if (barcoActual.size() == tamanos[indiceBarco]) {
							barcosUsuario.add(new ArrayList<>(barcoActual));
							JOptionPane.showMessageDialog(panelColocacion, 
									"¡Has colocado el barco de " + tamanos[indiceBarco] + " casillas!");

							barcoActual.clear();
							indiceBarco++; // Avanza al siguiente barco

							// IMPORTANTE: después de incrementar, comprobar si ya están todos
							if (indiceBarco == tamanos.length) {
								JOptionPane.showMessageDialog(panelColocacion, "¡Ya colocaste todos los barcos!");
								JOptionPane.showMessageDialog(panelColocacion, "¡En breve comenzará la partida!");

								try {
									Thread.sleep(3000);
								} catch (InterruptedException tiempo) {
									tiempo.printStackTrace();
								}
								inicializarFormacionesCPU();
								inicializarBarcosCPU();
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
		panelColocacion.setBounds(0, 0, 1782, 625); // Usa todo el espacio del JFrame
		// ⬇️ Creamos el contenedor con CardLayout y añadimos los paneles
		layout = new CardLayout();
		contenedorPaneles = new JPanel(layout);
		contenedorPaneles.setBounds(0, 0, 1782, 625);

		contenedorPaneles.add(panelColocacion, "colocacion");
		contenedorPaneles.add(new JPanel(), "disparo"); // temporal, se sustituye en iniciarDisparos()

		nuevaPartida.getContentPane().setLayout(null); // si quieres mantener posicionamiento absoluto
		nuevaPartida.getContentPane().add(contenedorPaneles);

		nuevaPartida.setSize(1798, 664);
		nuevaPartida.setLocationRelativeTo(null); // Centra la ventana
		nuevaPartida.setVisible(true);
	}

	public void iniciarDisparos()
	{
		Font fuenteNuevaPartida = cargarFuentePersonalizada("./font/Warzone.ttf", 22f, Font.BOLD);

		inicializarFormacionesCPU();
		List<List<Point>> formacionElegidaCPU = obtenerFormacionAleatoria();

		
		// Quita el panel anterior

		contenedorPaneles.remove(1); // quitamos el panel anterior en la posición de "disparo"
		contenedorPaneles.add(panelDisparo, "disparo");
		layout.show(contenedorPaneles, "disparo");

		System.out.println(formacionElegidaCPU);

		imagen = caja.getImage("./tablero.png");

		// Crea un nuevo panel (puedes usar otra imagen de fondo, otros botones, etc.)
		panelDisparo = new JPanel() {

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				g.drawString("¡Comienza la fase de disparos!", 100, 100);

				// Aquí puedes dibujar tablero enemigo, disparos, etc.
				if (imagen != null) {
					// Dibuja la imagen con tamaño 802x572 desde (0, 0)
					g.drawImage(imagen, 0, 0, 1782, 625, this);
				}

				g.setFont(fuenteNuevaPartida);
				g.setColor(Color.black);

				Graphics2D g2 = (Graphics2D) g;
				FontMetrics fm = g2.getFontMetrics();  // Obtiene las métricas de la fuente actual

				String texto1 = "ALMIRANTE";
				int anchoTexto1 = fm.stringWidth(texto1);  // Calcula el ancho en píxeles
				g2.drawString(texto1, 890 - anchoTexto1 / 2, 180);  // Centrado horizontal

				String texto3 = "BARCOS";
				int anchoTexto3 = fm.stringWidth(texto3);
				g2.drawString(texto3, 890 - anchoTexto3 / 2, 270);

				String texto6 = "RESTANTES";
				int anchoTexto6 = fm.stringWidth(texto6);
				g2.drawString(texto6, 890 - anchoTexto6 / 2, 295);

				String textoCPU = "JUGADOR";
				int anchoTextoCPU = fm.stringWidth(textoCPU);
				g2.drawString(textoCPU, 410 - anchoTextoCPU / 2, 585);

				String textoJugador = "CPU";
				int anchoTextoJugador = fm.stringWidth(textoJugador);
				g2.drawString(textoJugador, 1386 - anchoTextoJugador / 2, 585);


				String puntosJugadorTexto = "Jugador: " + puntosUsuario;
				int anchoJugador = fm.stringWidth(puntosJugadorTexto);
				g2.drawString(puntosJugadorTexto, 890 - anchoJugador / 2, 555); // un poco más abajo

				String puntosCpuTexto = "CPU: " + puntosCPU;
				int anchoCpu = fm.stringWidth(puntosCpuTexto);
				g2.drawString(puntosCpuTexto, 890 - anchoCpu / 2, 580); // debajo del anterior


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
				// DISPAROS JUGADOR

				for (int i = 0; i < disparosJugador.size(); i++) {
					Point p = disparosJugador.get(i);
					boolean acierto = aciertosJugador.get(i);
					g.setColor(acierto ? Color.RED : Color.CYAN);
					g.fillRect(p.x - 13, p.y - 13, 26, 26);
				}

				// DISPAROS CPU
				for (int i = 0; i < disparosCPU.size(); i++) {
					Point p = disparosCPU.get(i);
					boolean acierto = aciertosCPU.get(i);
					g.setColor(acierto ? Color.RED : Color.CYAN);
					g.fillRect(p.x - 13, p.y - 13, 26, 26);
				}
				// Explosión
				if (puntoExplosion != null) {
					Graphics2D g2d = (Graphics2D) g;
					g2d.setColor(new Color(255, 0, 0, 180)); // rojo semitransparente
					g2d.fillOval(puntoExplosion.x - radioExplosion / 2, puntoExplosion.y - radioExplosion / 2, radioExplosion, radioExplosion);
				}

			}


			@Override
			public Dimension getPreferredSize() {
				return new Dimension(1782, 625);
			}
		};

		panelDisparo.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (!turnoJugador || juegoTerminado) return;

				Point disparo = obtenerCeldaDesdeClickCPU(e.getX(), e.getY());
				if (disparo != null) {
					procesarDisparoJugador(disparo);
				}

			}
		});


		// Añadir o reemplazar el panel "disparo" dentro del contenedor con CardLayout
		contenedorPaneles.remove(1); // Quita el panel anterior "disparo"
		contenedorPaneles.add(panelDisparo, "disparo");
		layout.show(contenedorPaneles, "disparo");


	}

	private void inicializarFormacionesCPU() {
		formacionesCPU = new ArrayList<>();



		// Formación 1
		List<List<Point>> form1 = new ArrayList<>();
		form1.add(Arrays.asList(coordenadasTableroCPU[50], coordenadasTableroCPU[51], coordenadasTableroCPU[52], coordenadasTableroCPU[53], coordenadasTableroCPU[54])); // 5 horizontal
		form1.add(Arrays.asList(coordenadasTableroCPU[62], coordenadasTableroCPU[72], coordenadasTableroCPU[82], coordenadasTableroCPU[92])); // 4 vertical
		form1.add(Arrays.asList(coordenadasTableroCPU[74], coordenadasTableroCPU[75], coordenadasTableroCPU[76])); // 3 horizontal
		form1.add(Arrays.asList(coordenadasTableroCPU[77], coordenadasTableroCPU[87], coordenadasTableroCPU[97])); // 3 horizontal (celdas contiguas)
		form1.add(Arrays.asList(coordenadasTableroCPU[99], coordenadasTableroCPU[89])); // 2 vertical
		formacionesCPU.add(form1);

		// Formación 2
		List<List<Point>> form2 = new ArrayList<>();
		form2.add(Arrays.asList(coordenadasTableroCPU[59], coordenadasTableroCPU[69], coordenadasTableroCPU[79], coordenadasTableroCPU[89], coordenadasTableroCPU[99])); // 5 vertical
		form2.add(Arrays.asList(coordenadasTableroCPU[60], coordenadasTableroCPU[61], coordenadasTableroCPU[62], coordenadasTableroCPU[63])); // 4 horizontal
		form2.add(Arrays.asList(coordenadasTableroCPU[74], coordenadasTableroCPU[84], coordenadasTableroCPU[94])); // 3 vertical
		form2.add(Arrays.asList(coordenadasTableroCPU[86], coordenadasTableroCPU[87], coordenadasTableroCPU[88])); // 3 horizontal
		form2.add(Arrays.asList(coordenadasTableroCPU[90], coordenadasTableroCPU[91])); // 2 horizontal
		formacionesCPU.add(form2);

		// Formación 3
		List<List<Point>> form3 = new ArrayList<>();
		form3.add(Arrays.asList(coordenadasTableroCPU[0], coordenadasTableroCPU[1], coordenadasTableroCPU[2], coordenadasTableroCPU[3], coordenadasTableroCPU[4])); // 5 horizontal
		form3.add(Arrays.asList(coordenadasTableroCPU[10], coordenadasTableroCPU[20], coordenadasTableroCPU[30], coordenadasTableroCPU[40])); // 4 vertical
		form3.add(Arrays.asList(coordenadasTableroCPU[12], coordenadasTableroCPU[13], coordenadasTableroCPU[14])); // 3 horizontal
		form3.add(Arrays.asList(coordenadasTableroCPU[22], coordenadasTableroCPU[32], coordenadasTableroCPU[42])); // 3 vertical
		form3.add(Arrays.asList(coordenadasTableroCPU[99], coordenadasTableroCPU[98])); // 2 horizontal
		formacionesCPU.add(form3);

		// Formación 4
		List<List<Point>> form4 = new ArrayList<>();
		form4.add(Arrays.asList(coordenadasTableroCPU[91], coordenadasTableroCPU[92], coordenadasTableroCPU[93], coordenadasTableroCPU[94], coordenadasTableroCPU[95])); // 5 horizontal
		form4.add(Arrays.asList(coordenadasTableroCPU[50], coordenadasTableroCPU[60], coordenadasTableroCPU[70], coordenadasTableroCPU[80])); // 4 vertical
		form4.add(Arrays.asList(coordenadasTableroCPU[33], coordenadasTableroCPU[34], coordenadasTableroCPU[35])); // 3 horizontal
		form4.add(Arrays.asList(coordenadasTableroCPU[41], coordenadasTableroCPU[51], coordenadasTableroCPU[61])); // 3 vertical
		form4.add(Arrays.asList(coordenadasTableroCPU[5], coordenadasTableroCPU[6])); // 2 horizontal
		formacionesCPU.add(form4);

		// Formación 5
		List<List<Point>> form5 = new ArrayList<>();
		form5.add(Arrays.asList(coordenadasTableroCPU[25], coordenadasTableroCPU[26], coordenadasTableroCPU[27], coordenadasTableroCPU[28], coordenadasTableroCPU[29])); // 5 horizontal
		form5.add(Arrays.asList(coordenadasTableroCPU[70], coordenadasTableroCPU[71], coordenadasTableroCPU[72], coordenadasTableroCPU[73])); // 4 horizontal
		form5.add(Arrays.asList(coordenadasTableroCPU[40], coordenadasTableroCPU[50], coordenadasTableroCPU[60])); // 3 vertical
		form5.add(Arrays.asList(coordenadasTableroCPU[81], coordenadasTableroCPU[82], coordenadasTableroCPU[83])); // 3 horizontal
		form5.add(Arrays.asList(coordenadasTableroCPU[98], coordenadasTableroCPU[99])); // 2 horizontal
		formacionesCPU.add(form5);

		// Formación 6
		List<List<Point>> form6 = new ArrayList<>();
		form6.add(Arrays.asList(coordenadasTableroCPU[55], coordenadasTableroCPU[56], coordenadasTableroCPU[57], coordenadasTableroCPU[58], coordenadasTableroCPU[59])); // 5 horizontal
		form6.add(Arrays.asList(coordenadasTableroCPU[10], coordenadasTableroCPU[20], coordenadasTableroCPU[30], coordenadasTableroCPU[40])); // 4 vertical
		form6.add(Arrays.asList(coordenadasTableroCPU[83], coordenadasTableroCPU[84], coordenadasTableroCPU[85])); // 3 horizontal
		form6.add(Arrays.asList(coordenadasTableroCPU[91], coordenadasTableroCPU[81], coordenadasTableroCPU[71])); // 3 vertical
		form6.add(Arrays.asList(coordenadasTableroCPU[2], coordenadasTableroCPU[3])); // 2 horizontal
		formacionesCPU.add(form6);

		// Formación 7
		List<List<Point>> form7 = new ArrayList<>();
		form7.add(Arrays.asList(coordenadasTableroCPU[65], coordenadasTableroCPU[66], coordenadasTableroCPU[67], coordenadasTableroCPU[68], coordenadasTableroCPU[69])); // 5 horizontal
		form7.add(Arrays.asList(coordenadasTableroCPU[20], coordenadasTableroCPU[30], coordenadasTableroCPU[40], coordenadasTableroCPU[50])); // 4 vertical
		form7.add(Arrays.asList(coordenadasTableroCPU[11], coordenadasTableroCPU[12], coordenadasTableroCPU[13])); // 3 horizontal
		form7.add(Arrays.asList(coordenadasTableroCPU[91], coordenadasTableroCPU[81], coordenadasTableroCPU[71])); // 3 vertical
		form7.add(Arrays.asList(coordenadasTableroCPU[4], coordenadasTableroCPU[5])); // 2 horizontal
		formacionesCPU.add(form7);

		// Formación 8
		List<List<Point>> form8 = new ArrayList<>();
		form8.add(Arrays.asList(coordenadasTableroCPU[35], coordenadasTableroCPU[36], coordenadasTableroCPU[37], coordenadasTableroCPU[38], coordenadasTableroCPU[39])); // 5 horizontal
		form8.add(Arrays.asList(coordenadasTableroCPU[70], coordenadasTableroCPU[80], coordenadasTableroCPU[90], coordenadasTableroCPU[100-1])); // 4 vertical (ajustando índice 99)
		form8.add(Arrays.asList(coordenadasTableroCPU[21], coordenadasTableroCPU[22], coordenadasTableroCPU[23])); // 3 horizontal
		form8.add(Arrays.asList(coordenadasTableroCPU[54], coordenadasTableroCPU[64], coordenadasTableroCPU[74])); // 3 vertical
		form8.add(Arrays.asList(coordenadasTableroCPU[7], coordenadasTableroCPU[8])); // 2 horizontal
		formacionesCPU.add(form8);

		// Formación 9
		List<List<Point>> form9 = new ArrayList<>();
		form9.add(Arrays.asList(coordenadasTableroCPU[85], coordenadasTableroCPU[86], coordenadasTableroCPU[87], coordenadasTableroCPU[88], coordenadasTableroCPU[89])); // 5 horizontal
		form9.add(Arrays.asList(coordenadasTableroCPU[35], coordenadasTableroCPU[45], coordenadasTableroCPU[55], coordenadasTableroCPU[65])); // 4 vertical
		form9.add(Arrays.asList(coordenadasTableroCPU[10], coordenadasTableroCPU[11], coordenadasTableroCPU[12])); // 3 horizontal
		form9.add(Arrays.asList(coordenadasTableroCPU[76], coordenadasTableroCPU[77], coordenadasTableroCPU[78])); // 3 horizontal
		form9.add(Arrays.asList(coordenadasTableroCPU[40], coordenadasTableroCPU[41])); // 2 horizontal
		formacionesCPU.add(form9);

		// Formación 10
		List<List<Point>> form10 = new ArrayList<>();
		form10.add(Arrays.asList(coordenadasTableroCPU[15], coordenadasTableroCPU[16], coordenadasTableroCPU[17], coordenadasTableroCPU[18], coordenadasTableroCPU[19])); // 5 horizontal
		form10.add(Arrays.asList(coordenadasTableroCPU[25], coordenadasTableroCPU[35], coordenadasTableroCPU[45], coordenadasTableroCPU[55])); // 4 vertical
		form10.add(Arrays.asList(coordenadasTableroCPU[70], coordenadasTableroCPU[71], coordenadasTableroCPU[72])); // 3 horizontal
		form10.add(Arrays.asList(coordenadasTableroCPU[85], coordenadasTableroCPU[95], coordenadasTableroCPU[75])); // 3 vertical
		form10.add(Arrays.asList(coordenadasTableroCPU[80], coordenadasTableroCPU[81])); // 2 horizontal
		formacionesCPU.add(form10);

	}

	public List<List<Point>> obtenerFormacionAleatoria() {
		Random rand = new Random();
		int indice = rand.nextInt(formacionesCPU.size());
		return formacionesCPU.get(indice);
	}


	private void inicializarBarcosCPU() {
		Random random = new Random();

		int formacionSeleccionadaCPU = random.nextInt(formacionesCPU.size());

		// Obtiene la formación seleccionada según el índice aleatorio
		List<List<Point>> formacionCPU = formacionesCPU.get(formacionSeleccionadaCPU);

		// Inicializa el array de barcos de la CPU con la cantidad de barcos en la formación
		barcosCPU = new Point[formacionCPU.size()][];

		// Recorre cada barco de la formación y lo convierte a array de Point[]
		for (int i = 0; i < formacionCPU.size(); i++) {
			barcosCPU[i] = formacionCPU.get(i).toArray(new Point[0]);
		}
	}

	private Point obtenerCeldaDesdeClickCPU(int x, int y) {
		for (Point celda : coordenadasTableroCPU) {
			Rectangle areaCelda = new Rectangle(celda.x - 13, celda.y - 13, 26, 26);
			if (areaCelda.contains(x, y)) {
				return celda;
			}
		}
		return null;
	}

	public void generarDisparoCPU() {
		// Generador de números aleatorios
		Random random = new Random();
		Point disparo;

		// Selecciona una coordenada aleatoria del tablero del jugador que no haya sido disparada ya
		do {
			disparo = coordenadasTableroUsuario[random.nextInt(coordenadasTableroUsuario.length)];
		} while (disparosCPU.contains(disparo)); // Evita disparar en la misma posición dos veces

		boolean acierto = false;

		// COMPROBACIÓN DE SI HA ACERTADO
		// Recorre cada barco del jugador para comprobar si el disparo ha acertado
		for (int i = 0; i < barcosUsuario.size(); i++) {
			List<Point> barco = barcosUsuario.get(i); // Obtiene el barco i

			// Recorre las partes del barco
			for (int j = 0; j < barco.size(); j++) {
				Point parte = barco.get(j); // Parte j del barco
				if (parte.equals(disparo)) {
					puntosCPU++; 
					acierto = true; 
					break; // Sale del bucle de partes del barco
				}
			}

			if (acierto) break; // Sale del bucle de barcos si ya ha acertado
		}

		// Añade el disparo a la lista para evitar repetirlo y registrar el resultado
		disparosCPU.add(disparo);
		aciertosCPU.add(acierto);

		// Repinta el tablero del jugador (izquierdo) para mostrar el disparo
		panelDisparo.repaint();

		// Cambia el turno al jugador
		turnoJugador = true;

		// Comprueba si hay un ganador tras este disparo
		comprobarGanador();
	}

	private void procesarDisparoCPU() {
	    Random random = new Random();
	    Point disparo;

	    // Elegir un disparo aleatorio que no se haya realizado antes
	    do {
	        disparo = coordenadasTableroUsuario[random.nextInt(coordenadasTableroUsuario.length)];
	    } while (disparosCPU.contains(disparo));

	    boolean acierto = false;

	    // Recorrer todos los barcos del jugador
	    for (int i = 0; i < barcosUsuario.size(); i++) {
	        List<Point> barco = barcosUsuario.get(i);
	        for (int j = 0; j < barco.size(); j++) {
	            Point parte = barco.get(j);
	            if (parte.equals(disparo)) {
	                puntoExplosion = disparo;
	                iniciarExplosionAnimada();
	                puntosCPU += 5;
	                acierto = true;
	                break;
	            }
	        }
	        if (acierto) break;
	    }

	    disparosCPU.add(disparo);
	    aciertosCPU.add(acierto);

	    panelDisparo.repaint(); // Actualiza el tablero

	    turnoJugador = !acierto; // Si no acierta, pasa el turno al jugador
	    comprobarGanador();      // Verifica si alguien ha ganado

	    // Si la CPU acierta, vuelve a disparar tras una pausa
	    if (!juegoTerminado && !turnoJugador) {
	        Timer timer = new Timer(600, evt -> procesarDisparoCPU());
	        timer.setRepeats(false);
	        timer.start();
	    }
	}




	public void manejarDisparo(Point click) {
		// SOLO PARA VERIFICAR SI SE HA DISPARADO YA AHI
		for (int i = 0; i < disparosJugador.size(); i++) {
			if (disparosJugador.get(i).equals(click)) return;
		}
		// Añade el disparo hecho a la lista
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

		// Devuelve TRUE o FALSE dependiendo si esta o no en la celda

		return (click.x >= celdaX && click.x < celdaX + anchoCelda &&
				click.y >= celdaY && click.y < celdaY + altoCelda);

	}



	public void procesarDisparoJugador(Point disparo) {
		// Verifica que el jugador no haya disparado ya a esta celda
		if (!disparosJugador.contains(disparo)) {
			boolean acierto = false; // Variable para saber si el disparo acertó

			// Recorre todos los barcos de la CPU
			for (int i = 0; i < barcosCPU.length; i++) {
				Point[] barco = barcosCPU[i];
				// Recorre todas las partes de cada barco
				for (int j = 0; j < barco.length; j++) {
					Point parte = barco[j];
					if (parte.equals(disparo)) {
						puntoExplosion = disparo; // ANIMACION
						iniciarExplosionAnimada(); // Inicia la animación de explosión

						puntosUsuario += 5; // Suma puntos al jugador
						acierto = true; // Marca que fue un acierto
						break; // Sale del bucle interno
					}
				}
				if (acierto) break; // Si ya acertó, no sigue buscando
			}

			// Guarda el disparo en la lista de disparos realizados
			disparosJugador.add(disparo);
			// Guarda si fue un acierto o no en la lista de resultados
			aciertosJugador.add(acierto);
			// Redibuja el panel de disparo para mostrar el nuevo disparo
			panelDisparo.repaint();

			// Si acierta, mantiene el turno; si falla, lo pierde
			turnoJugador = acierto;

			comprobarGanador();

			// LANZA EL TURNO DE LA CPU
			if (!juegoTerminado && !turnoJugador) {
				// Espera 600ms antes de que la CPU dispare
				Timer timer = new Timer(600, evt -> procesarDisparoCPU());
				timer.setRepeats(false); // Solo se ejecuta una vez
				timer.start(); // Inicia el temporizador
			}
		}
	}


	private void comprobarGanador() {
		int puntosMaximos = 85; 

		if (puntosUsuario >= puntosMaximos) {
			juegoTerminado = true;
			JOptionPane.showMessageDialog(panelDisparo, "🎉 ¡Has ganado la partida!");
			Modelo modelo = new Modelo();
			Connection conexion = null;
			conexion=	modelo.conectar();
			modelo.sumarVictoria(conexion, txtUsuario.getText());

		} else if (puntosCPU >= puntosMaximos) {
			juegoTerminado = true;
			JOptionPane.showMessageDialog(panelDisparo, "💥 Has perdido contra la CPU.");
		}
	}

	public void reiniciarPartida() {
		// Limpiar todos los datos de juego
		disparosJugador.clear();
		disparosCPU.clear();
		aciertosJugador.clear();
		aciertosCPU.clear();
		barcosUsuario.clear();
		barcosCPU = null;
		barcoActual.clear();

		// Resetear estado del juego
		puntosUsuario = 0;
		puntosCPU = 0;
		juegoTerminado = false;
		turnoJugador = true;
		puntoExplosion = null;
		radioExplosion = 0;
		indiceBarco = 0;

		// Repintar ambos paneles para limpiar lo visible
		panelDisparo.repaint();
		panelColocacion.repaint();

		// Mostrar el panel de colocación (nivel básico)
		layout.show(contenedorPaneles, "colocacion");



		//Informar al jugador
		JOptionPane.showMessageDialog(panelColocacion, "Coloca tus barcos en el tablero izquierdo.");
	}
	private void iniciarExplosionAnimada() {
		radioExplosion = 0;

		if (timerExplosion != null && timerExplosion.isRunning()) {
			timerExplosion.stop();
		}

		timerExplosion = new Timer(30, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radioExplosion += 8;
				if (radioExplosion > 80) {
					timerExplosion.stop();
					puntoExplosion = null;
				}
				panelDisparo.repaint();
			}
		});

		timerExplosion.start();
	}

	private void reproducirSonidoMenu() {
		try {
			File sonido = new File("./musicaMenu.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(sonido);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);

			// Controlar volumen
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			float volumenDeseado = 0.7f; // 70%
			float dB = (float) (20.0 * Math.log10(volumenDeseado));
			gainControl.setValue(dB);

			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Font cargarFuentePersonalizada(String ruta, float tamanio, int estilo) {
		try {
			Font fuente = Font.createFont(Font.TRUETYPE_FONT, new File(ruta));
			return fuente.deriveFont(estilo, tamanio);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			return new Font("Arial", estilo, (int) tamanio); // Fuente de respaldo
		}
	}


}


