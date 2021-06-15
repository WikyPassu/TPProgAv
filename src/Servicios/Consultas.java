package Servicios;

import java.lang.reflect.Field;
import java.util.ArrayList;

import Anotaciones.Columna;
import Anotaciones.Id;
import Anotaciones.Tabla;
import Utilidades.UBean;

public class Consultas {
	
	public static String guardar(Object o) {
		String query = "insert into ".concat(o.getClass().getAnnotation(Tabla.class).nombre()).concat(" (");
		ArrayList<Field> fields = UBean.obtenerAtributos(o);
		
		for(Field f: fields) {
			if(f.getAnnotation(Id.class) == null) {
				query = query.concat(f.getAnnotation(Columna.class).nombre()).concat(", ");
			}
		}
		query = query.substring(0, query.length() - 2).concat(") values(");
		for(Field f: fields) {
			if(f.getAnnotation(Id.class) == null) {
				query = query.concat(UBean.ejecutarGet(o, f.getName()).toString()).concat(", ");	
			}
		}
		query = query.substring(0, query.length() - 2).concat(")");
		
		return query;
	}
}
