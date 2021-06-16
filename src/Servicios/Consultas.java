package Servicios;

import java.lang.reflect.Field;
import java.util.ArrayList;

import Anotaciones.Columna;
import Anotaciones.Id;
import Anotaciones.Tabla;
import Utilidades.UBean;

public class Consultas {
	
	public static String guardar(Object o) {
		ArrayList<Field> fields = UBean.obtenerAtributos(o);
		String query = "insert into ".concat(o.getClass().getAnnotation(Tabla.class).nombre()).concat(" (");
		String nombreCampos = "";
		String contenidoCampos = "";
		
		for(Field f: fields) {
			if(f.getAnnotation(Id.class) == null) {
				nombreCampos = nombreCampos.concat(f.getAnnotation(Columna.class).nombre()).concat(", ");
				contenidoCampos = contenidoCampos.concat(UBean.ejecutarGet(o, f.getName()).toString()).concat(", ");
			}
		}
		nombreCampos = nombreCampos.substring(0, nombreCampos.length() - 2).concat(") values (");
		contenidoCampos = contenidoCampos.substring(0, contenidoCampos.length() - 2).concat(")");
		query = query.concat(nombreCampos).concat(contenidoCampos);
		
		return query;
	}
	
	public static String modificar(Object o) {
		ArrayList<Field> fields = UBean.obtenerAtributos(o);
		String query = "update ".concat(o.getClass().getAnnotation(Tabla.class).nombre()).concat(" set ");
		String camposValor = "";
		String nombreCampoId = "";
		String contenidoCampoId = "";
		
		for(Field f: fields) {
			if(f.getAnnotation(Id.class) == null) {
				camposValor = camposValor.concat(f.getAnnotation(Columna.class).nombre()).concat("=").concat(UBean.ejecutarGet(o, f.getName()).toString()).concat(", ");	
			}
			else {
				nombreCampoId = f.getAnnotation(Columna.class).nombre();
				contenidoCampoId = UBean.ejecutarGet(o, f.getName()).toString();
			}
		}
		camposValor = camposValor.substring(0, camposValor.length() - 2);
		query = query.concat(camposValor).concat(" where ").concat(nombreCampoId).concat("=").concat(contenidoCampoId);
		
		return query;
	}
}
