package Servicios;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Anotaciones.Columna;
import Anotaciones.Id;
import Anotaciones.Tabla;
import Utilidades.UBean;
import Utilidades.UConexion;

public class Consultas {
	
	public static void guardar(Object o) {
		ArrayList<Field> fields = UBean.obtenerAtributos(o);
		String query = "insert into ".concat(o.getClass().getAnnotation(Tabla.class).nombre()).concat(" (");
		String nombreCampos = "";
		String contenidoCampos = "";
		for(Field f: fields) {
			if(f.getAnnotation(Id.class) == null) {
				nombreCampos = nombreCampos.concat(f.getAnnotation(Columna.class).nombre()).concat(", ");
				if(f.getType() == String.class) {
					contenidoCampos = contenidoCampos.concat("'"+UBean.ejecutarGet(o, f.getName()).toString()+"'").concat(", ");	
				}
				else {
					contenidoCampos = contenidoCampos.concat(UBean.ejecutarGet(o, f.getName()).toString()).concat(", ");
				}
			}
		}
		nombreCampos = nombreCampos.substring(0, nombreCampos.length() - 2).concat(") values (");
		contenidoCampos = contenidoCampos.substring(0, contenidoCampos.length() - 2).concat(")");
		query = query.concat(nombreCampos).concat(contenidoCampos);
		System.out.println(query);
		try {
			UConexion.abrirConexion();
			PreparedStatement pst = UConexion.c.prepareStatement(query);
			pst.execute();
			UConexion.cerrarConexion();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void modificar(Object o) {
		ArrayList<Field> fields = UBean.obtenerAtributos(o);
		String query = "update ".concat(o.getClass().getAnnotation(Tabla.class).nombre()).concat(" set ");
		String camposValor = "";
		String nombreCampoId = "";
		String contenidoCampoId = "";
		for(Field f: fields) {
			if(f.getAnnotation(Id.class) == null) {
				if(f.getType() == String.class) {
					camposValor = camposValor.concat(f.getAnnotation(Columna.class).nombre()).concat("=").concat("'"+UBean.ejecutarGet(o, f.getName()).toString()).concat("', ");	
				}
				else {
					camposValor = camposValor.concat(f.getAnnotation(Columna.class).nombre()).concat("=").concat(UBean.ejecutarGet(o, f.getName()).toString()).concat(", ");	
				}
			}
			else {
				nombreCampoId = f.getAnnotation(Columna.class).nombre();
				contenidoCampoId = UBean.ejecutarGet(o, f.getName()).toString();
			}
		}
		camposValor = camposValor.substring(0, camposValor.length() - 2);
		query = query.concat(camposValor).concat(" where ").concat(nombreCampoId).concat("=").concat(contenidoCampoId);
		System.out.println(query);
		try {
			UConexion.abrirConexion();
			PreparedStatement pst = UConexion.c.prepareStatement(query);
			pst.execute();
			UConexion.cerrarConexion();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void eliminar(Object o) {
		ArrayList<Field> fields = UBean.obtenerAtributos(o);
		String query = "delete from ".concat(o.getClass().getAnnotation(Tabla.class).nombre()).concat(" where ");
		for(Field f: fields) {
			if(f.getAnnotation(Id.class) != null) {
				query = query.concat(f.getAnnotation(Columna.class).nombre()).concat("="+UBean.ejecutarGet(o, f.getName()));
				break;
			}
		}
		System.out.println(query);
		try {
			UConexion.abrirConexion();
			PreparedStatement pst = UConexion.c.prepareStatement(query);
			pst.execute();
			UConexion.cerrarConexion();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Object obtenerPorId(Class<?> c, Object id) {
		Object o = null;
		try {
			Constructor[] constructores = c.getConstructors();
			for(Constructor con: constructores) {
				if(con.getParameterCount() == 0) {
					o = con.newInstance();
					break;
				}
			}
			ArrayList<Field> fields = UBean.obtenerAtributos(o);
			String query = "select * from ".concat(c.getAnnotation(Tabla.class).nombre()).concat(" where ");
			for(Field f: fields) {
				if(f.getAnnotation(Id.class) != null) {
					query = query.concat(f.getAnnotation(Columna.class).nombre()).concat("="+id.toString());
					break;
				}
			}
			System.out.println(query);
			UConexion.abrirConexion();
			PreparedStatement pst = UConexion.c.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				for(Field f: fields) {
					UBean.ejecutarSet(o, f.getName(), rs.getObject(f.getAnnotation(Columna.class).nombre()));
				}
			}
			UConexion.cerrarConexion();
		} catch(SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return o;
	}
	
	public static ArrayList<?> obtenerTodos(Class<?> c){
		ArrayList<Object> objetos = new ArrayList<Object>();
		try {
			Constructor[] constructores = c.getConstructors();
			Object o = null;
			for(Constructor con: constructores) {
				if(con.getParameterCount() == 0) {
					o = con.newInstance();
					break;
				}
			}
			String query = "select * from "+c.getAnnotation(Tabla.class).nombre();
			System.out.println(query);
			UConexion.abrirConexion();
			PreparedStatement pst = UConexion.c.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			ArrayList<Field> fields = UBean.obtenerAtributos(o);
			while(rs.next()) {
				for(Field f: fields) {
					UBean.ejecutarSet(o, f.getName(), rs.getObject(f.getAnnotation(Columna.class).nombre()));
				}
				objetos.add(c.cast(o));
				for(Constructor con: constructores) {
					if(con.getParameterCount() == 0) {
						o = con.newInstance();
						break;
					}
				}
			}
			UConexion.cerrarConexion();
		} catch(SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return objetos;
	}
}
