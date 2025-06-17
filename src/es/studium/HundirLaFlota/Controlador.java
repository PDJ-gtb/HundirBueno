package es.studium.HundirLaFlota;

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
	    
	    System.exit(0); 
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
   



