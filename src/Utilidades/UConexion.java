package Utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UConexion {
	
	private static UConexion cn;
	public static Connection c;
	
	private UConexion() {}
	
	public static UConexion generarObjeto() {
		if(cn == null) {
			cn = new UConexion();
		}
		return cn;
	}
	
	public static void abrirConexion() {
		try {
			Properties p = Properties.generarObjeto("Utilidades.framework");
			Class.forName(p.getDriver());
			c = DriverManager.getConnection(p.getUrl(), p.getUsername(), p.getPassword());
			System.out.println("Se conecto!!!");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void cerrarConexion() {
		try {
			c.close();
			c = null;
			System.out.println("Se desconecto!!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
