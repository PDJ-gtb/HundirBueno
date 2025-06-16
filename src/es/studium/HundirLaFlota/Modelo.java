package es.studium.HundirLaFlota;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Modelo
{
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/tiro_al_barco";
	String login = "adminBueno";
	String password = "Studium2025#";
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
//
	public Connection conectar() {
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);

		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return connection;

	}
	public void desconectar (Connection connection) {
		if (connection!=null) {
			try
			{
				connection.close();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public boolean comprobarCredenciales(Connection conexion, String usuario, String clave) {

		boolean credencialesCorrectas= false;
		
		sentencia = "SELECT * FROM jugadores WHERE nombreJugador= '"+usuario + "' AND passJugador = SHA2('"+ clave + "',256);";

		try
		{
			statement = conexion.createStatement();
			rs = statement.executeQuery(sentencia);
			System.out.println(sentencia);
			if (rs.next()) {
				credencialesCorrectas=true;
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return credencialesCorrectas;
	}
	
	public void sumarVictoria(Connection conexion,String usuario) {
		sentencia= "UPDATE jugadores SET victoriasJugador = victoriasJugador + 1 WHERE nombreJugador ='"+usuario+"';";
		try
		{

			System.out.println(sentencia);
			statement=conexion.createStatement();
			
			statement.executeUpdate(sentencia);
			
		} catch (SQLException e)
		{
			
			e.printStackTrace();
		}

	}
	public ResultSet obtenerRanking(Connection conexion)
	{
	    ResultSet resultado = null;
	    String consulta = "SELECT nombreJugador, victoriasJugador FROM jugadores ORDER BY victoriasJugador DESC";

	    try
	    {
	        statement = conexion.createStatement();
	        resultado = statement.executeQuery(consulta);
	    }
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	    }

	    return resultado;
	}

	
	
}



    

