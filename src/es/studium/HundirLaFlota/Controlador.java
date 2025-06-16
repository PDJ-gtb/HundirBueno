package es.studium.HundirLaFlota;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Controlador implements ActionListener, WindowListener {
	
	Vista vista;
	Modelo modelo;

public  Controlador (Vista v, Modelo m){
    this.vista = v;
    this.modelo = m;
    Point[] coordenadasBarco = new Point[100];

    coordenadasBarco[0] = new Point(111, 65);   // A1
    coordenadasBarco[1] = new Point(177, 65);   // A2
    coordenadasBarco[2] = new Point(243, 65);   // A3
    coordenadasBarco[3] = new Point(309, 65);   // A4
    coordenadasBarco[4] = new Point(375, 65);   // A5
    coordenadasBarco[5] = new Point(441, 65);   // A6
    coordenadasBarco[6] = new Point(507, 65);   // A7
    coordenadasBarco[7] = new Point(573, 65);   // A8
    coordenadasBarco[8] = new Point(639, 65);   // A9
    coordenadasBarco[9] = new Point(705, 65);   // A10

    coordenadasBarco[10] = new Point(111, 113);  // B1
    coordenadasBarco[11] = new Point(177, 113);  // B2
    coordenadasBarco[12] = new Point(243, 113);  // B3
    coordenadasBarco[13] = new Point(309, 113);  // B4
    coordenadasBarco[14] = new Point(375, 113);  // B5
    coordenadasBarco[15] = new Point(441, 113);  // B6
    coordenadasBarco[16] = new Point(507, 113);  // B7
    coordenadasBarco[17] = new Point(573, 113);  // B8
    coordenadasBarco[18] = new Point(639, 113);  // B9
    coordenadasBarco[19] = new Point(705, 113);  // B10

    coordenadasBarco[20] = new Point(111, 161);  // C1
    coordenadasBarco[21] = new Point(177, 161);  // C2
    coordenadasBarco[22] = new Point(243, 161);  // C3
    coordenadasBarco[23] = new Point(309, 161);  // C4
    coordenadasBarco[24] = new Point(375, 161);  // C5
    coordenadasBarco[25] = new Point(441, 161);  // C6
    coordenadasBarco[26] = new Point(507, 161);  // C7
    coordenadasBarco[27] = new Point(573, 161);  // C8
    coordenadasBarco[28] = new Point(639, 161);  // C9
    coordenadasBarco[29] = new Point(705, 161);  // C10

    coordenadasBarco[30] = new Point(111, 209);  // D1
    coordenadasBarco[31] = new Point(177, 209);  // D2
    coordenadasBarco[32] = new Point(243, 209);  // D3
    coordenadasBarco[33] = new Point(309, 209);  // D4
    coordenadasBarco[34] = new Point(375, 209);  // D5
    coordenadasBarco[35] = new Point(441, 209);  // D6
    coordenadasBarco[36] = new Point(507, 209);  // D7
    coordenadasBarco[37] = new Point(573, 209);  // D8
    coordenadasBarco[38] = new Point(639, 209);  // D9
    coordenadasBarco[39] = new Point(705, 209);  // D10

    coordenadasBarco[40] = new Point(111, 257);  // E1
    coordenadasBarco[41] = new Point(177, 257);  // E2
    coordenadasBarco[42] = new Point(243, 257);  // E3
    coordenadasBarco[43] = new Point(309, 257);  // E4
    coordenadasBarco[44] = new Point(375, 257);  // E5
    coordenadasBarco[45] = new Point(441, 257);  // E6
    coordenadasBarco[46] = new Point(507, 257);  // E7
    coordenadasBarco[47] = new Point(573, 257);  // E8
    coordenadasBarco[48] = new Point(639, 257);  // E9
    coordenadasBarco[49] = new Point(705, 257);  // E10

    coordenadasBarco[50] = new Point(111, 305);  // F1
    coordenadasBarco[51] = new Point(177, 305);  // F2
    coordenadasBarco[52] = new Point(243, 305);  // F3
    coordenadasBarco[53] = new Point(309, 305);  // F4
    coordenadasBarco[54] = new Point(375, 305);  // F5
    coordenadasBarco[55] = new Point(441, 305);  // F6
    coordenadasBarco[56] = new Point(507, 305);  // F7
    coordenadasBarco[57] = new Point(573, 305);  // F8
    coordenadasBarco[58] = new Point(639, 305);  // F9
    coordenadasBarco[59] = new Point(705, 305);  // F10

    coordenadasBarco[60] = new Point(111, 353);  // G1
    coordenadasBarco[61] = new Point(177, 353);  // G2
    coordenadasBarco[62] = new Point(243, 353);  // G3
    coordenadasBarco[63] = new Point(309, 353);  // G4
    coordenadasBarco[64] = new Point(375, 353);  // G5
    coordenadasBarco[65] = new Point(441, 353);  // G6
    coordenadasBarco[66] = new Point(507, 353);  // G7
    coordenadasBarco[67] = new Point(573, 353);  // G8
    coordenadasBarco[68] = new Point(639, 353);  // G9
    coordenadasBarco[69] = new Point(705, 353);  // G10

    coordenadasBarco[70] = new Point(111, 401);  // H1
    coordenadasBarco[71] = new Point(177, 401);  // H2
    coordenadasBarco[72] = new Point(243, 401);  // H3
    coordenadasBarco[73] = new Point(309, 401);  // H4
    coordenadasBarco[74] = new Point(375, 401);  // H5
    coordenadasBarco[75] = new Point(441, 401);  // H6
    coordenadasBarco[76] = new Point(507, 401);  // H7
    coordenadasBarco[77] = new Point(573, 401);  // H8
    coordenadasBarco[78] = new Point(639, 401);  // H9
    coordenadasBarco[79] = new Point(705, 401);  // H10

    coordenadasBarco[80] = new Point(111, 449);  // I1
    coordenadasBarco[81] = new Point(177, 449);  // I2
    coordenadasBarco[82] = new Point(243, 449);  // I3
    coordenadasBarco[83] = new Point(309, 449);  // I4
    coordenadasBarco[84] = new Point(375, 449);  // I5
    coordenadasBarco[85] = new Point(441, 449);  // I6
    coordenadasBarco[86] = new Point(507, 449);  // I7
    coordenadasBarco[87] = new Point(573, 449);  // I8
    coordenadasBarco[88] = new Point(639, 449);  // I9
    coordenadasBarco[89] = new Point(705, 449);  // I10

    coordenadasBarco[90] = new Point(111, 497);  // J1
    coordenadasBarco[91] = new Point(177, 497);  // J2
    coordenadasBarco[92] = new Point(243, 497);  // J3
    coordenadasBarco[93] = new Point(309, 497);  // J4
    coordenadasBarco[94] = new Point(375, 497);  // J5
    coordenadasBarco[95] = new Point(441, 497);  // J6
    coordenadasBarco[96] = new Point(507, 497);  // J7
    coordenadasBarco[97] = new Point(573, 497);  // J8
    coordenadasBarco[98] = new Point(639, 497);  // J9
    coordenadasBarco[99] = new Point(705, 497);  // J10
    // Lista que no permite repetidos
  /*  Set<Integer> celdasOcupadas = new HashSet<>();

    JPanel panelTablero = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(imagen, 0, 0, this);

            // Pintamos círculos en las casillas ocupadas
            g.setColor(Color.RED);
            for (int index : celdasOcupadas) {
                Point p = coordenadasBarco[index];
                g.fillOval(p.x + 10, p.y + 10, 20, 20); // Puedes ajustar el tamaño y posición
            }
        }
    };

    panelTablero.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            for (int i = 0; i < coordenadasBarco.length; i++) {
                Point p = coordenadasBarco[i];
                Rectangle celda = new Rectangle(p.x, p.y, 66, 48);
                if (celda.contains(e.getPoint()) && !celdasOcupadas.contains(i)) {
                    celdasOcupadas.add(i);
                    panelTablero.repaint();
                    break;
                }
            }
        }
    });*/
   vista.btnLogear.addActionListener(this);
   vista.errorLog.addWindowListener(this);
   vista.login.addWindowListener(this);
   vista.btnNuevaPartida.addActionListener(this);
   vista.btnRanking.addActionListener(this);
   vista.btnSalir.addActionListener(this);
   vista.menuPrincipal.addWindowListener(this);
   vista.btnAyuda.addActionListener(this);


    }


@Override
public void windowOpened(WindowEvent e)
{
	// TODO Auto-generated method stub
	
}

@Override
public void windowClosing(WindowEvent e)
{
	if (e.getSource().equals(vista.login)) {
		vista.login.setVisible(false);
	}
	else if (e.getSource().equals(vista.errorLog)) {
		vista.errorLog.setVisible(false);
	}
	else if (e.getSource().equals(vista.menuPrincipal)) {
	    // Aquí puedes guardar estado si quieres, o simplemente cerrar
	    System.exit(0); // O usar vista.menuPrincipal.dispose(); si no quieres terminar la app
	}
}

@Override
public void windowClosed(WindowEvent e)
{
	// TODO Auto-generated method stub
	
}

@Override
public void windowIconified(WindowEvent e)
{
	// TODO Auto-generated method stub
	
}

@Override
public void windowDeiconified(WindowEvent e)
{
	// TODO Auto-generated method stub
	
}

@Override
public void windowActivated(WindowEvent e)
{
	// TODO Auto-generated method stub
	
}

@Override
public void windowDeactivated(WindowEvent e)
{
	// TODO Auto-generated method stub
	
}

@Override
public void actionPerformed(ActionEvent e)
{
	
	if(e.getSource().equals(vista.btnLogear)) {
		Connection connection=	modelo.conectar();
		if(modelo.comprobarCredenciales(connection,vista.txtUsuario.getText(),vista.txtContrasena.getText())) {
			vista.login.setVisible(false);
			vista.menuPrincipal.setVisible(true);
			System.out.println("Datos correctos");
			vista.inicializarMenu();
		} 
		else {
			vista.errorLog.setVisible(true);
			System.out.println("Comprobación");
		}
		modelo.desconectar(connection);
	}
	else if (e.getSource().equals(vista.btnNuevaPartida)) {
		vista.reiniciarPartida();
		vista.nuevaPartida.setLocationRelativeTo(null);
		vista.nuevaPartida.toFront();
		vista.nuevaPartida.setVisible(true);
		vista.inicializarNuevaPartida();
		

		System.out.println("Nueva partida");
	}
	else if (e.getSource().equals(vista.btnRanking)) {
		 if (vista.modeloRanking == null) {
		        vista.inicializarRanking(); // ✅ Solo la primera vez
		    }
	    Connection conexion = modelo.conectar();
	    vista.modeloRanking.setRowCount(0); // Limpiar tabla por si acaso

	    try {
	        ResultSet rs = modelo.obtenerRanking(conexion);

	        while (rs != null && rs.next()) {
	            String nombre = rs.getString("nombreJugador");
	            int victorias = rs.getInt("victoriasJugador");
	            vista.modeloRanking.addRow(new Object[]{ nombre, victorias });
	        }

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }

	    modelo.desconectar(conexion);
	    vista.rankingFrame.setVisible(true);
	}
	else if (e.getSource().equals(vista.btnAyuda)) {
	    abrirAyuda();
	}
	else if (e.getSource().equals(vista.btnSalir)) {
	    int opcion = javax.swing.JOptionPane.showConfirmDialog(
	        vista.menuPrincipal,
	        "¿Estás seguro de que quieres salir?",
	        "Confirmar salida",
	        javax.swing.JOptionPane.YES_NO_OPTION
	    );

	    if (opcion == javax.swing.JOptionPane.YES_OPTION) {
	        vista.menuPrincipal.dispatchEvent(new WindowEvent(vista.menuPrincipal, WindowEvent.WINDOW_CLOSING));
	    }
	}


}
private void abrirAyuda()
{
    try
    {
        ProcessBuilder pb = new ProcessBuilder("hh.exe", "./AyudaHundir/Hundir.chm");
        pb.start();
        System.out.println("Abriendo el archivo CHM...");
    }
    catch (IOException e)
    {
        System.err.println("Error al intentar abrir el archivo CHM: " + e.getMessage());
        javax.swing.JOptionPane.showMessageDialog(null,
            "No se pudo abrir el archivo de ayuda.",
            "Error",
            javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}
}
   



